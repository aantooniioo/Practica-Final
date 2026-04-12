<template>
  <v-container class="mt-4">

    <!-- Tarjeta principal -->
    <v-card class="pa-4 elevation-4" data-aos="fade-up">

      <!-- Cabecera -->
      <v-row align="center" justify="space-between" class="mb-3">

        <h2 class="text-h6 text-md-h5">
          {{ $t('estadisticas.titulo') }}
        </h2>

        <!-- BOTÓN -->
        <v-btn
          size="default"
          color="primary"
          class="btn-animated btn-updated"
          @click="generarEstadisticas"
        >
          {{ $t('estadisticas.actualizar') }}
        </v-btn>
      </v-row>

      <!-- KPIs -->
      <v-row dense class="mb-3">

        <v-col cols="4" data-aos="fade-up" data-aos-delay="100">
          <v-card class="kpi-card card-animated">
            <div class="kpi-title">{{ $t('estadisticas.empleados') }}</div>
            <div class="kpi-value">{{ empleadosAnim }}</div>
          </v-card>
        </v-col>

        <v-col cols="4" data-aos="fade-up" data-aos-delay="200">
          <v-card class="kpi-card card-animated">
            <div class="kpi-title">{{ $t('estadisticas.proyectos') }}</div>
            <div class="kpi-value">{{ proyectosAnim }}</div>
          </v-card>
        </v-col>

        <v-col cols="4" data-aos="fade-up" data-aos-delay="300">
          <v-card class="kpi-card card-animated">
            <div class="kpi-title">{{ $t('estadisticas.asignaciones') }}</div>
            <div class="kpi-value">{{ asignacionesAnim }}</div>
          </v-card>
        </v-col>

      </v-row>

      <!-- GRÁFICAS -->
      <v-row dense>

        <v-col
          cols="12"
          md="6"
          v-for="(grafica, index) in graficas"
          :key="grafica.src"
          :data-aos="'fade-up'"
          :data-aos-delay="(index + 1) * 100"
        >
          <v-card class="grafica-card card-animated">

            <v-card-title class="grafica-title">
              {{ $t(grafica.titulo) }}
            </v-card-title>

            <v-img
              :src="`${grafica.src}?t=${timestamp}`"
              contain
              max-height="220"
            />

          </v-card>
        </v-col>

      </v-row>

    </v-card>

  </v-container>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
import AOS from "aos";
import "aos/dist/aos.css";

export default {
  data() {
    return {
      timestamp: new Date().getTime(),

      empleados: 0,
      proyectos: 0,
      asignaciones: 0,

      empleadosAnim: 0,
      proyectosAnim: 0,
      asignacionesAnim: 0,

      graficas: [
        { titulo: "estadisticas.graficas.anios", src: "/graficas/anios_empresa.png" },
        { titulo: "estadisticas.graficas.altas", src: "/graficas/altas_anio.png" },
        { titulo: "estadisticas.graficas.estado", src: "/graficas/proyectos_estado.png" },
        { titulo: "estadisticas.graficas.duracion", src: "/graficas/duracion_proyectos.png" },
        { titulo: "estadisticas.graficas.proyectos_empleado", src: "/graficas/proyectos_por_empleado.png" },
        { titulo: "estadisticas.graficas.top", src: "/graficas/top_proyectos.png" },
        { titulo: "estadisticas.graficas.sin_asignacion", src: "/graficas/sin_asignacion.png" }
      ]
    };
  },

  methods: {

    generarEstadisticas() {

      let progreso = 0;

      Swal.fire({
        title: this.$t('estadisticas.generando'),
        html: `
          <div style="margin-top:10px;">
            <div style="
              width:100%;
              height:10px;
              background:#1e293b;
              border-radius:5px;
              overflow:hidden;
            ">
              <div id="barraProgreso" style="
                width:0%;
                height:100%;
                background:#028CF5;
                transition: width 0.2s;
              "></div>
            </div>
            <p id="textoProgreso" style="margin-top:8px; font-size:12px;">0%</p>
          </div>
        `,
        showConfirmButton: false,
        allowOutsideClick: false,

        didOpen: () => {

          const barra = document.getElementById("barraProgreso");
          const texto = document.getElementById("textoProgreso");

          const intervalo = setInterval(() => {
            progreso += Math.random() * 15;

            if (progreso >= 100) {
              progreso = 100;
              clearInterval(intervalo);
            }

            barra.style.width = progreso + "%";
            texto.innerText = Math.floor(progreso) + "%";

          }, 200);

          axios.get("http://localhost:8080/estadisticas/generar")
            .then(() => {

              clearInterval(intervalo);

              barra.style.width = "100%";
              texto.innerText = "100%";

              setTimeout(() => {

                Swal.fire({
                  icon: "success",
                  title: this.$t('estadisticas.ok'),
                  text: this.$t('estadisticas.actualizado')
                });

                this.timestamp = new Date().getTime();
                this.cargarKPIs();

              }, 500);

            })
            .catch(() => {

              clearInterval(intervalo);

              Swal.fire({
                icon: "error",
                title: this.$t('estadisticas.error'),
                text: this.$t('estadisticas.error_texto')
              });

            });

        }
      });

    },

    animarContador(prop, valorFinal) {
      let actual = 0;
      const incremento = Math.ceil(valorFinal / 30);

      const intervalo = setInterval(() => {

        actual += incremento;

        if (actual >= valorFinal) {
          actual = valorFinal;
          clearInterval(intervalo);
        }

        this[prop] = actual;

      }, 20);
    },

    cargarKPIs() {

      axios.get("http://localhost:8080/empleados")
        .then(res => {
          this.empleados = res.data.length;
          this.animarContador('empleadosAnim', this.empleados);
        });

      axios.get("http://localhost:8080/proyectos")
        .then(res => {
          this.proyectos = res.data.length;
          this.animarContador('proyectosAnim', this.proyectos);
        });

      axios.get("http://localhost:8080/asignaciones")
        .then(res => {
          this.asignaciones = res.data.length;
          this.animarContador('asignacionesAnim', this.asignaciones);
        });
    }

  },

  mounted() {
    this.cargarKPIs();

    // Inicializa animaciones
    AOS.init({
      duration: 800,
      once: true
    });
  }
};
</script>

<style scoped>

.v-card {
  background-color: #012033;
  color: #D4F1FF;

  border-radius: 16px;
  border: 1px solid rgba(255,255,255,0.05);

  box-shadow:
    0 10px 25px rgba(0,0,0,0.5),
    0 2px 8px rgba(0,0,0,0.3);

  transition: all 0.25s ease;
}

.v-card:hover {
  transform: translateY(-10px);
  box-shadow:
    0 18px 40px rgba(0,0,0,0.7),
    0 0 15px rgba(2,111,193,0.4);
}

/* =========================
   BOTÓN ANIMADO 
========================= */

.btn-updated {
  padding: 6px 16px;
  font-size: 14px;
  border-radius: 8px;
}

.btn-animated {
  transition: all 0.2s ease;
}

.btn-animated:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 15px rgba(0,0,0,0.4);
}

.btn-animated:active {
  transform: scale(0.95);
}

/* =========================
   KPI AJUSTADO
========================= */

.kpi-card {
  transition: all 0.2s ease;
}

.kpi-card:hover {
  transform: translateY(-5px);
  box-shadow:
    0 14px 30px rgba(0,0,0,0.6),
    0 0 10px rgba(2,111,193,0.3);
}

/* =========================
   KPI TEXTO
========================= */

.kpi-value {
  font-size: 22px;
  font-weight: bold;
}

</style>
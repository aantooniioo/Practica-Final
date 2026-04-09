<template>
  <v-container class="mt-4">

    <v-card class="pa-4 elevation-4">

      <!-- HEADER -->
      <v-row align="center" justify="space-between" class="mb-3">
        <h2 class="text-h6 text-md-h5">Estadísticas</h2>

        <v-btn
          size="small"
          color="primary"
          @click="generarEstadisticas"
        >
          Actualizar
        </v-btn>
      </v-row>

      <!-- KPIs -->
      <v-row dense class="mb-3">

        <v-col cols="4">
          <v-card class="kpi-card">
            <div class="kpi-title">Emp.</div>
            <div class="kpi-value">{{ empleados }}</div>
          </v-card>
        </v-col>

        <v-col cols="4">
          <v-card class="kpi-card">
            <div class="kpi-title">Proy.</div>
            <div class="kpi-value">{{ proyectos }}</div>
          </v-card>
        </v-col>

        <v-col cols="4">
          <v-card class="kpi-card">
            <div class="kpi-title">Asig.</div>
            <div class="kpi-value">{{ asignaciones }}</div>
          </v-card>
        </v-col>

      </v-row>

      <!-- GRÁFICAS -->
      <v-row dense>

        <v-col cols="12" md="6" v-for="grafica in graficas" :key="grafica.src">
          <v-card class="grafica-card card-animated">
            <v-card-title class="grafica-title">
              {{ grafica.titulo }}
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

export default {
  data() {
    return {
      timestamp: new Date().getTime(),

      empleados: 0,
      proyectos: 0,
      asignaciones: 0,

      graficas: [
        { titulo: "Años empresa", src: "/graficas/anios_empresa.png" },
        { titulo: "Altas", src: "/graficas/altas_anio.png" },
        { titulo: "Estado proyectos", src: "/graficas/proyectos_estado.png" },
        { titulo: "Duración", src: "/graficas/duracion_proyectos.png" },
        { titulo: "Proyectos/empleado", src: "/graficas/proyectos_por_empleado.png" },
        { titulo: "Top proyectos", src: "/graficas/top_proyectos.png" },
        { titulo: "Sin asignación", src: "/graficas/sin_asignacion.png" }
      ]
    };
  },

  methods: {

    generarEstadisticas() {

      Swal.fire({
        title: "Generando...",
        allowOutsideClick: false,
        didOpen: () => Swal.showLoading()
      });

      axios.get("http://localhost:8080/estadisticas/generar")
        .then(() => {

          Swal.fire("OK", "Actualizado", "success");

          this.timestamp = new Date().getTime();
          this.cargarKPIs();

        })
        .catch(() => {
          Swal.fire("Error", "No se pudo generar", "error");
        });
    },

    cargarKPIs() {

      axios.get("http://localhost:8080/empleados")
        .then(res => this.empleados = res.data.length);

      axios.get("http://localhost:8080/proyectos")
        .then(res => this.proyectos = res.data.length);

      axios.get("http://localhost:8080/asignaciones")
        .then(res => this.asignaciones = res.data.length);
    }

  },

  mounted() {
    this.cargarKPIs();
  }
};
</script>

<style scoped>

/* KPI */
.kpi-card {
  padding: 12px;
  text-align: center;
  background: #1e293b;
  border-radius: 10px;
}

.kpi-title {
  font-size: 11px;
  color: #94a3b8;
}

.kpi-value {
  font-size: 18px;
  font-weight: bold;
  color: #38bdf8;
}

/* GRÁFICAS */
.grafica-card {
  padding: 10px;
  border-radius: 10px;
  background-color: #1e293b;
}

.grafica-title {
  font-size: 12px;
  color: #e2e8f0;
  padding-bottom: 6px;
}

/* RESPONSIVE */
@media (min-width: 960px) {

  .kpi-value {
    font-size: 26px;
  }

  .grafica-title {
    font-size: 14px;
  }

}

.card-animated {
    opacity: 0;
    transform: translateY(15px);
    animation: fadeUp 0.4s ease forwards;
}

@keyframes fadeUp {
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

/* Efecto en cascada */
.card-animated:nth-child(1) { animation-delay: 0.05s; }
.card-animated:nth-child(2) { animation-delay: 0.1s; }
.card-animated:nth-child(3) { animation-delay: 0.15s; }
.card-animated:nth-child(4) { animation-delay: 0.2s; }
.card-animated:nth-child(5) { animation-delay: 0.25s; }
</style>
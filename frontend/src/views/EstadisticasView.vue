<template>
  <v-container class="mt-4">

    <!-- Tarjeta principal -->
    <v-card class="pa-4 elevation-4">

      <!-- Cabecera con título y botón -->
      <v-row align="center" justify="space-between" class="mb-3">

        <!-- Título -->
        <h2 class="text-h6 text-md-h5">
          {{ $t('estadisticas.titulo') }}
        </h2>

        <!-- Botón para generar estadísticas -->
        <v-btn
          size="small"
          color="primary"
          @click="generarEstadisticas"
        >
          {{ $t('estadisticas.actualizar') }}
        </v-btn>
      </v-row>

      <!-- KPIs -->
      <v-row dense class="mb-3">

        <!-- Total empleados -->
        <v-col cols="4">
          <v-card class="kpi-card">
            <div class="kpi-title">{{ $t('estadisticas.empleados') }}</div>
            <div class="kpi-value">{{ empleados }}</div>
          </v-card>
        </v-col>

        <!-- Total proyectos -->
        <v-col cols="4">
          <v-card class="kpi-card">
            <div class="kpi-title">{{ $t('estadisticas.proyectos') }}</div>
            <div class="kpi-value">{{ proyectos }}</div>
          </v-card>
        </v-col>

        <!-- Total asignaciones -->
        <v-col cols="4">
          <v-card class="kpi-card">
            <div class="kpi-title">{{ $t('estadisticas.asignaciones') }}</div>
            <div class="kpi-value">{{ asignaciones }}</div>
          </v-card>
        </v-col>

      </v-row>

      <!-- Gráficas -->
      <v-row dense>

        <!-- Renderizado dinámico de gráficas -->
        <v-col cols="12" md="6" v-for="grafica in graficas" :key="grafica.src">
          <v-card class="grafica-card card-animated">

            <!-- Título de la gráfica -->
            <v-card-title class="grafica-title">
              {{ $t(grafica.titulo) }}
            </v-card-title>

            <!-- Imagen de la gráfica con cache busting -->
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
      // Timestamp para forzar recarga de imágenes
      timestamp: new Date().getTime(),

      // KPIs
      empleados: 0,
      proyectos: 0,
      asignaciones: 0,

      // Configuración de gráficas
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

    // Llama al backend para regenerar las estadísticas
    generarEstadisticas() {

      // Loader mientras se generan
      Swal.fire({
        title: this.$t('estadisticas.generando'),
        allowOutsideClick: false,
        didOpen: () => Swal.showLoading()
      });

      axios.get("http://localhost:8080/estadisticas/generar")
        .then(() => {

          // Mensaje de éxito
          Swal.fire(
            this.$t('estadisticas.ok'),
            this.$t('estadisticas.actualizado'),
            "success"
          );

          // Fuerza recarga de imágenes
          this.timestamp = new Date().getTime();

          // Recarga KPIs
          this.cargarKPIs();

        })
        .catch(() => {

          // Mensaje de error
          Swal.fire(
            this.$t('estadisticas.error'),
            this.$t('estadisticas.error_texto'),
            "error"
          );
        });
    },

    // Carga los valores de KPIs desde el backend
    cargarKPIs() {

      axios.get("http://localhost:8080/empleados")
        .then(res => this.empleados = res.data.length);

      axios.get("http://localhost:8080/proyectos")
        .then(res => this.proyectos = res.data.length);

      axios.get("http://localhost:8080/asignaciones")
        .then(res => this.asignaciones = res.data.length);
    }

  },

  // Se ejecuta al montar la vista
  mounted() {
    this.cargarKPIs();
  }
};
</script>
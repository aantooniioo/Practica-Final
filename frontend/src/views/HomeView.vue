<template>
  <v-container>

    <!-- HERO -->
    <v-row class="mb-8">
      <v-col cols="12">
        <v-card class="pa-6 hero-card card-pro">

          <h1 class="text-h4 font-weight-bold mb-2">
            Bienvenido
          </h1>

          <p class="text-grey mb-4">
            Sistema de gestión interna de Future Space
          </p>

          <v-btn color="primary" @click="$router.push('/empleados')">
            Empezar
          </v-btn>

        </v-card>
      </v-col>
    </v-row>

    <!-- DASHBOARD -->
    <v-row class="mb-8">

      <v-col cols="12" md="4">
        <v-card class="pa-5 card-pro">
          <div class="text-grey">Empleados</div>
          <div class="text-h4 font-weight-bold">{{ empleados.length }}</div>
        </v-card>
      </v-col>

      <v-col cols="12" md="4">
        <v-card class="pa-5 card-pro">
          <div class="text-grey">Proyectos</div>
          <div class="text-h4 font-weight-bold">{{ proyectos.length }}</div>
        </v-card>
      </v-col>

      <v-col cols="12" md="4">
        <v-card class="pa-5 card-pro">
          <div class="text-grey">Asignaciones</div>
          <div class="text-h4 font-weight-bold">{{ asignaciones.length }}</div>
        </v-card>
      </v-col>

    </v-row>

    <!-- CARRUSEL -->
    <v-row class="mb-8">
      <v-col cols="12">

        <v-carousel height="250" cycle interval="4000">

          <v-carousel-item v-for="(item, i) in slides" :key="i">
            <v-sheet class="carousel-item d-flex align-center justify-center">

              <div class="text-center">
                <h2>{{ item.titulo }}</h2>
                <p>{{ item.texto }}</p>
              </div>

            </v-sheet>
          </v-carousel-item>

        </v-carousel>

      </v-col>
    </v-row>

    <!-- SOBRE NOSOTROS -->
    <v-row class="mb-8">

      <v-col cols="12" md="6">
        <v-img
          src="https://images.unsplash.com/photo-1552664730-d307ca884978"
          height="280"
          cover
        />
      </v-col>

      <v-col cols="12" md="6">

        <h2 class="text-h5 text-white mb-3">
          Future Space S.A.
        </h2>

        <p class="text-grey">
          Future Space es una empresa tecnológica orientada al desarrollo de soluciones digitales.
        </p>

        <p class="text-grey mt-3">
          Este sistema permite gestionar empleados, proyectos y asignaciones de forma clara.
        </p>

        <p class="text-grey mt-3">
          Diseñado con una interfaz moderna para mejorar la eficiencia diaria.
        </p>

      </v-col>

    </v-row>

    <!-- VENTANITAS (IMPORTANTE) -->
    <v-row class="mb-8">

      <v-col cols="12" md="4">
        <v-card class="card-pro hover-card">
          <v-img src="https://images.unsplash.com/photo-1521737604893-d14cc237f11d" height="180" cover />
          <v-card-text>
            <h3>Gestión de empleados</h3>
            <p class="text-grey text-body-2">
              Administración completa del personal.
            </p>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" md="4">
        <v-card class="card-pro hover-card">
          <v-img src="https://images.unsplash.com/photo-1504384308090-c894fdcc538d" height="180" cover />
          <v-card-text>
            <h3>Gestión de proyectos</h3>
            <p class="text-grey text-body-2">
              Organización y seguimiento de proyectos.
            </p>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" md="4">
        <v-card class="card-pro hover-card">
          <v-img src="https://images.unsplash.com/photo-1556761175-b413da4baf72" height="180" cover />
          <v-card-text>
            <h3>Asignaciones</h3>
            <p class="text-grey text-body-2">
              Relación eficiente entre empleados y proyectos.
            </p>
          </v-card-text>
        </v-card>
      </v-col>

    </v-row>

  </v-container>
</template>

<script>
import axios from "axios";
import { getEmpleados } from "../services/empleadoService";
import { getProyectos } from "../services/proyectoService";

export default {
  name: "HomeView",

  data() {
    return {
      empleados: [],
      proyectos: [],
      asignaciones: [],
      slides: [
        { titulo: "Gestión eficiente", texto: "Control total del sistema" },
        { titulo: "Centralización", texto: "Todo en un mismo lugar" },
        { titulo: "Entorno profesional", texto: "Diseñado para empresa" }
      ]
    };
  },

  mounted() {
    axios.get("http://localhost:8080/asignaciones")
      .then(res => this.asignaciones = res.data);

    getEmpleados().then(res => this.empleados = res.data);
    getProyectos().then(res => this.proyectos = res.data);
  }
};
</script>

<style scoped>
.hero-card {
  background: linear-gradient(135deg, #1e293b, #334155);
}

.carousel-item {
  height: 100%;
  background: #0f172a;
  color: white;
}

/* Hover cards */
.hover-card:hover {
  transform: translateY(-6px);
  transition: 0.2s;
}
</style>
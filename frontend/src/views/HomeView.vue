<template>
  <v-container class="mt-5">

    <!-- Tarjeta principal -->
    <v-card>

      <!-- Título -->
      <v-card-title class="text-h5">
        Panel principal
      </v-card-title>

      <v-card-text>
        <v-row>

          <!-- Botón empleados -->
          <v-col cols="12" md="3">
            <v-btn block color="primary" @click="$router.push('/empleados')">
              Empleados
            </v-btn>
          </v-col>

          <!-- Botón proyectos -->
          <v-col cols="12" md="3">
            <v-btn block color="secondary" @click="$router.push('/proyectos')">
              Proyectos
            </v-btn>
          </v-col>

          <!-- Botón asignar -->
          <v-col cols="12" md="3">
            <v-btn block color="success" @click="$router.push('/asignacion')">
              Asignar
            </v-btn>
          </v-col>

          <!-- Botón ver asignaciones -->
          <v-col cols="12" md="3">
            <v-btn block color="black" @click="$router.push('/asignaciones')">
              Ver asignaciones
            </v-btn>
          </v-col>

        </v-row>
      </v-card-text>

    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "HomeView"
}
</script>
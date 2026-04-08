<template>
  <v-container class="mt-8">

    <!-- Título -->
    <div class="mb-8">
      <h1 class="text-h4 font-weight-bold">Panel de gestión</h1>
      <p class="text-grey">
        Acceso rápido a las secciones principales
      </p>
    </div>

    <!-- Tarjetas principales -->
    <v-row class="mb-6" dense>

      <!-- Empleados -->
      <v-col cols="12" md="4">
        <v-card class="pa-5 dashboard-card" @click="$router.push('/empleados')">
          <v-row align="center">
            <v-col cols="auto">
              <v-icon size="36" class="text-blue">mdi-account-group</v-icon>
            </v-col>
            <v-col>
              <h3 class="text-h6">Empleados</h3>
              <p class="text-grey text-caption">Gestión de empleados</p>
            </v-col>
          </v-row>
        </v-card>
      </v-col>

      <!-- Proyectos -->
      <v-col cols="12" md="4">
        <v-card class="pa-5 dashboard-card" @click="$router.push('/proyectos')">
          <v-row align="center">
            <v-col cols="auto">
              <v-icon size="36" class="text-green">mdi-briefcase</v-icon>
            </v-col>
            <v-col>
              <h3 class="text-h6">Proyectos</h3>
              <p class="text-grey text-caption">Gestión de proyectos</p>
            </v-col>
          </v-row>
        </v-card>
      </v-col>

      <!-- Ver asignaciones -->
      <v-col cols="12" md="4">
        <v-card class="pa-5 dashboard-card" @click="$router.push('/asignaciones')">
          <v-row align="center">
            <v-col cols="auto">
              <v-icon size="36" class="text-orange">mdi-link</v-icon>
            </v-col>
            <v-col>
              <h3 class="text-h6">Asignaciones</h3>
              <p class="text-grey text-caption">Relación empleados-proyectos</p>
            </v-col>
          </v-row>
        </v-card>
      </v-col>

    </v-row>

    <!-- SEGUNDA FILA -->
    <v-row dense>

      <!-- Asignar -->
      <v-col cols="12" md="6">
        <v-card class="pa-5 dashboard-card" @click="$router.push('/asignacion')">
          <v-row align="center">
            <v-col cols="auto">
              <v-icon size="36" class="text-deep-orange">mdi-account-switch</v-icon>
            </v-col>
            <v-col>
              <h3 class="text-h6">Asignar</h3>
              <p class="text-grey text-caption">
                Asignar empleados a proyectos
              </p>
            </v-col>
          </v-row>
        </v-card>
      </v-col>

      <!-- Acciones rápidas -->
      <v-col cols="12" md="6">
        <v-card class="pa-5 dashboard-card">

          <!-- Cabecera con icono -->
          <v-row align="center" class="mb-3">
            <v-col cols="auto">
              <v-icon size="28" class="text-blue">mdi-flash</v-icon>
            </v-col>
            <v-col>
              <h3 class="text-h6">Acciones rápidas</h3>
            </v-col>
          </v-row>

          <!-- Botones -->
          <v-btn
            block
            class="mb-2"
            variant="tonal"
            prepend-icon="mdi-account-plus"
            @click="$router.push('/alta-empleado')"
          >
            Crear empleado
          </v-btn>

          <v-btn
            block
            variant="tonal"
            prepend-icon="mdi-briefcase-plus"
            @click="$router.push('/alta-proyecto')"
          >
            Crear proyecto
          </v-btn>

        </v-card>
      </v-col>

    </v-row>

  </v-container>
</template>

<script>
export default {
  name: "HomeView"
};
</script>

<style scoped>
.dashboard-card {
  cursor: pointer;
  transition: all 0.2s ease;
}

.dashboard-card:hover {
  transform: translateY(-4px);
  background-color: rgba(255,255,255,0.03);
}
</style>
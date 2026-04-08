<template>
  <v-app>

    <!-- Barra superior de navegación -->
    <v-app-bar color="black" dark>
      <v-toolbar-title>Práctica Final - Gestión Future Space</v-toolbar-title>

      <v-spacer></v-spacer>

      <!-- Navegación -->
      <v-btn text @click="$router.push('/')">Home</v-btn>
      <v-btn text @click="$router.push('/empleados')">Empleados</v-btn>
      <v-btn text @click="$router.push('/proyectos')">Proyectos</v-btn>
      <v-btn text @click="$router.push('/asignaciones')">Asignaciones</v-btn>
    </v-app-bar>

    <!-- Contenido principal -->
    <v-main>
      <router-view />
    </v-main>

  </v-app>
</template>

<script>
export default {
  name: "App"
}
</script>
<template>
  <v-app>

    <!-- NAVBAR -->
    <v-app-bar
      color="secondary"
      elevation="2"
      class="navbar-pro"
    >

      <!-- LOGO -->
      <div class="logo-wrapper" @click="$router.push('/')">
        <img
          src="/src/assets/images/Logo_blanco_Future-2.png"
          alt="Future Space Logo"
          class="logo-img"
        />
      </div>

      <v-spacer></v-spacer>

      <!-- LINKS -->
      <div class="nav-links">

        <v-btn
          variant="text"
          :class="getActiveClass('/')"
          @click="$router.push('/')"
        >
          Inicio
        </v-btn>

        <v-btn
          variant="text"
          :class="getActiveClass('/empleados')"
          @click="$router.push('/empleados')"
        >
          Empleados
        </v-btn>

        <v-btn
          variant="text"
          :class="getActiveClass('/proyectos')"
          @click="$router.push('/proyectos')"
        >
          Proyectos
        </v-btn>

        <v-btn
          variant="text"
          :class="getActiveClass('/asignacion')"
          @click="$router.push('/asignacion')"
        >
          Asignar
        </v-btn>

        <v-btn
          variant="text"
          :class="getActiveClass('/asignaciones')"
          @click="$router.push('/asignaciones')"
        >
          Ver Asignaciones
        </v-btn>

      </div>

    </v-app-bar>

    <!-- CONTENIDO -->
    <v-main class="app-background">
      <router-view />
    </v-main>

  </v-app>
</template>

<script>
export default {
  methods: {
    // Marca el botón activo según la ruta actual
    getActiveClass(route) {
      return this.$route.path === route ? 'nav-active' : '';
    }
  }
}
</script>

<style>
/* ===== NAVBAR ===== */

.navbar-pro {
  padding: 0 16px;
}

/* LINKS */
.nav-links {
  display: flex;
  gap: 8px;
}

/* BOTONES */
.nav-links .v-btn {
  text-transform: none;
  font-weight: 500;
  letter-spacing: 0.3px;
  border-radius: 6px;
  transition: 0.2s;
}

/* HOVER */
.nav-links .v-btn:hover {
  background-color: rgba(255,255,255,0.08);
}

/* ACTIVO */
.nav-active {
  background-color: rgba(255,255,255,0.15);
  font-weight: 600;
}

/* ===== LOGO ===== */

.logo-wrapper {
  display: flex;
  align-items: center;
  cursor: pointer;
  height: 64px;
}

.logo-wrapper:hover {
  opacity: 0.85;
}

.logo-img {
  height: auto;
  max-height: 55px;
  width: auto;
}
</style>
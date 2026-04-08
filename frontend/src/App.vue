<template>
  <v-app class="d-flex">

    <!-- SIDEBAR -->
    <v-navigation-drawer
      v-model="drawer"
      app
      width="250"
      class="sidebar-pro"
    >
      <div class="sidebar-header">
        <img
          src="/src/assets/images/Logo_blanco_Future-2.png"
          class="sidebar-logo"
        />
      </div>

      <v-divider />

      <v-list nav density="comfortable">

        <v-list-item
          prepend-icon="mdi-view-dashboard"
          title="Inicio"
          @click="$router.push('/')"
          :active="$route.path === '/'"
        />

        <v-list-item
          prepend-icon="mdi-account-group"
          title="Empleados"
          @click="$router.push('/empleados')"
          :active="$route.path === '/empleados'"
        />

        <v-list-item
          prepend-icon="mdi-briefcase"
          title="Proyectos"
          @click="$router.push('/proyectos')"
          :active="$route.path === '/proyectos'"
        />

        <v-list-item
          prepend-icon="mdi-link-variant"
          title="Asignar"
          @click="$router.push('/asignacion')"
          :active="$route.path === '/asignacion'"
        />

        <v-list-item
          prepend-icon="mdi-format-list-bulleted"
          title="Asignaciones"
          @click="$router.push('/asignaciones')"
          :active="$route.path === '/asignaciones'"
        />

      </v-list>
    </v-navigation-drawer>

    <!-- MAIN -->
    <v-main class="app-background">

      <!-- TOPBAR -->
      <v-app-bar elevation="0" class="topbar-pro">

        <v-btn icon @click="drawer = !drawer">
          <v-icon>mdi-menu</v-icon>
        </v-btn>

        <span class="topbar-title ml-3">
          Future Space
        </span>

        <v-spacer />

        <div class="topbar-right">
          <v-icon size="20">mdi-account-circle</v-icon>
        </div>

      </v-app-bar>

      <v-container fluid class="mt-4">
        <router-view />
      </v-container>

    </v-main>

    <!-- FOOTER -->
    <FooterComponent />

  </v-app>
</template>

<script>
import FooterComponent from './components/FooterComponent.vue';

export default {
  components: { FooterComponent },

  data() {
    return {
      drawer: true
    };
  }
};
</script>

<style>
html, body, #app {
  height: 100%;
}

/* FONDO */
.app-background {
  background: linear-gradient(180deg, #0f172a, #111827);
  min-height: 100vh;
}

/* SIDEBAR */
.sidebar-pro {
  background-color: #020617;
  color: #e2e8f0;
}

.sidebar-header {
  display: flex;
  justify-content: center;
  padding: 16px;
}

.sidebar-logo {
  max-height: 50px;
}

/* TOPBAR */
.topbar-pro {
  background-color: #020617;
  color: white;
  border-bottom: 1px solid rgba(255,255,255,0.08);
}

.topbar-title {
  font-weight: 600;
  font-size: 14px;
}

.topbar-right {
  display: flex;
  align-items: center;
  padding-right: 12px;
}

/* CARDS */
.card-pro {
  border-radius: 14px;
  background-color: #1e293b;
  color: #e2e8f0;
  transition: 0.25s;
}

.card-pro:hover {
  transform: translateY(-4px);
}

/* BOTONES */
.v-btn {
  border-radius: 8px;
  text-transform: none;
}

/* TABLAS */
tbody tr:hover {
  background-color: rgba(255,255,255,0.05);
}
</style>
<template>
  <v-app>

    <!-- SIDEBAR -->
    <v-navigation-drawer
      v-model="drawer"
      :permanent="!isMobile"
      :temporary="isMobile"
      :rail="mini"
      app
      width="250"
      class="sidebar-pro"
    >

      <!-- LOGO -->
      <div class="sidebar-header">
        <img
          src="/src/assets/images/Logo_blanco_Future-2.png"
          :class="mini ? 'logo-mini' : 'logo-full'"
        />
      </div>

      <v-divider />

      <!-- MENU -->
      <v-list nav density="comfortable">

        <v-list-item
          v-for="item in menu"
          :key="item.path"
          :prepend-icon="item.icon"
          :title="mini ? '' : item.title"
          @click="ir(item.path)"
          :active="$route.path === item.path"
          class="menu-item"
        />

      </v-list>

    </v-navigation-drawer>

    <!-- MAIN -->
    <v-main class="app-background">

      <!-- TOPBAR -->
      <v-app-bar elevation="0" class="topbar-pro">

        <!-- BOTÓN INTELIGENTE -->
        <v-btn icon @click="accionMenu">
          <v-icon>
            {{ !isMobile && mini ? 'mdi-chevron-right' : 'mdi-menu' }}
          </v-icon>
        </v-btn>

        <!-- LOGO TOP -->
        <img
          src="/src/assets/images/Logo_blanco_Future-2.png"
          class="topbar-logo"
        />

        <v-spacer />

        <!-- MENÚ USUARIO -->
        <v-menu>

          <template v-slot:activator="{ props }">
            <v-btn icon v-bind="props">
              <v-icon>mdi-account-circle</v-icon>
            </v-btn>
          </template>

          <v-list>
            <v-list-item title="Mi perfil" />
            <v-list-item title="Configuración" />
            <v-divider />
            <v-list-item title="Cerrar sesión" />
          </v-list>

        </v-menu>

      </v-app-bar>

      <!-- CONTENIDO -->
      <div class="main-content">

        <v-container fluid class="mt-4">

          <!-- ANIMACIÓN GLOBAL -->
          <transition name="fade-slide" mode="out-in">
            <router-view />
          </transition>

        </v-container>

        <FooterComponent />

      </div>

    </v-main>

  </v-app>
</template>

<script>
import FooterComponent from './components/FooterComponent.vue';

export default {
  components: { FooterComponent },

  data() {
    return {
      drawer: true,
      mini: false,
      isMobile: false,

      menu: [
        { title: "Inicio", path: "/", icon: "mdi-view-dashboard" },
        { title: "Empleados", path: "/empleados", icon: "mdi-account-group" },
        { title: "Proyectos", path: "/proyectos", icon: "mdi-briefcase" },
        { title: "Asignar", path: "/asignacion", icon: "mdi-link-variant" },
        { title: "Asignaciones", path: "/asignaciones", icon: "mdi-format-list-bulleted" },
        { title: "Estadísticas", path: "/estadisticas", icon: "mdi-chart-bar" }
      ]
    };
  },

  methods: {

    detectarPantalla() {
      this.isMobile = window.innerWidth < 960;

      if (this.isMobile) {
        this.drawer = false;
        this.mini = false;
      } else {
        this.drawer = true;
      }
    },

    accionMenu() {
      if (this.isMobile) {
        // móvil → abrir/cerrar sidebar
        this.drawer = !this.drawer;
      } else {
        // PC → mini sidebar
        this.mini = !this.mini;
      }
    },

    ir(ruta) {
      this.$router.push(ruta);

      if (this.isMobile) {
        this.drawer = false;
      }
    }

  },

  mounted() {
    this.detectarPantalla();
    window.addEventListener("resize", this.detectarPantalla);
  }
};
</script>

<style>

/* ===== ANIMACIÓN GLOBAL ===== */

.fade-slide-enter-active {
  transition: all 0.25s ease;
}

.fade-slide-leave-active {
  transition: all 0.15s ease;
}

.fade-slide-enter-from {
  opacity: 0;
  transform: translateY(10px);
}

.fade-slide-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}

/* ===== BASE ===== */

.app-background {
  background: linear-gradient(180deg, #001021, #012033);
}

.main-content {
  display: flex;
  flex-direction: column;
  min-height: calc(100vh - 64px);
}

.main-content > .v-container {
  flex: 1;
}

/* ===== SIDEBAR ===== */

.sidebar-pro {
  background-color: #020617;
  color: #e2e8f0;
  display: flex;
  flex-direction: column;
}

.sidebar-header {
  display: flex;
  justify-content: center;
  padding: 16px;
}

.logo-full {
  height: 40px;
  transition: 0.2s;
}

.logo-mini {
  height: 36px;
  width: 36px;
  object-fit: cover;
  object-position: left;
  border-radius: 6px;
  overflow: hidden;
}

/* ===== MENU ===== */

.menu-item {
  transition: all 0.2s ease;
}

.menu-item:hover {
  background-color: rgba(255,255,255,0.08);
}

.menu-item.v-list-item--active {
  background-color: rgba(59,130,246,0.25);
  border-left: 3px solid #3b82f6;
}

/* ===== TOPBAR ===== */

.topbar-pro {
  background-color: #020617;
  color: white;
}

.topbar-logo {
  height: 35px;
  margin-left: 10px;
}

/* ===== ANIMACIÓN CARDS ===== */

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

/* efecto cascada */
.card-animated:nth-child(1) { animation-delay: 0.05s; }
.card-animated:nth-child(2) { animation-delay: 0.1s; }
.card-animated:nth-child(3) { animation-delay: 0.15s; }
.card-animated:nth-child(4) { animation-delay: 0.2s; }
.card-animated:nth-child(5) { animation-delay: 0.25s; }
.card-animated:nth-child(6) { animation-delay: 0.3s; }

</style>
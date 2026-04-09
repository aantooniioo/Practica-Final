<template>
  <v-container fluid class="home-container">

    <!-- HERO -->
    <v-card class="pa-6 mb-6 hero-card" data-aos="fade-up">
      <h1 class="text-h4 font-weight-bold mb-2">Bienvenido</h1>
      <p class="text-subtitle-1 mb-4">
        Sistema de gestión interna de Future Space
      </p>

      <div class="hero-buttons">
        <v-btn color="primary" @click="$router.push('/empleados')">
          Empezar
        </v-btn>

        <v-btn variant="outlined" @click="$router.push('/estadisticas')">
          Ver estadísticas
        </v-btn>
      </div>
    </v-card>

    <!-- KPIs -->
    <v-row class="mb-6">
      <v-col cols="12" md="4" data-aos="fade-up" data-aos-delay="100">
        <v-card class="pa-4 kpi-card">
          <span class="kpi-title">Empleados</span>
          <h2>{{ empleados }}</h2>
        </v-card>
      </v-col>

      <v-col cols="12" md="4" data-aos="fade-up" data-aos-delay="200">
        <v-card class="pa-4 kpi-card">
          <span class="kpi-title">Proyectos</span>
          <h2>{{ proyectos }}</h2>
        </v-card>
      </v-col>

      <v-col cols="12" md="4" data-aos="fade-up" data-aos-delay="300">
        <v-card class="pa-4 kpi-card">
          <span class="kpi-title">Asignaciones</span>
          <h2>{{ asignaciones }}</h2>
        </v-card>
      </v-col>
    </v-row>

    <!-- ACCIONES -->
    <v-row class="mb-6">
      <v-col cols="12" md="4" data-aos="zoom-in">
        <v-card class="pa-4 action-card" @click="$router.push('/alta-empleado')">
          <v-icon size="32" class="mb-2">mdi-account-plus</v-icon>
          <div class="action-title">Nuevo empleado</div>
        </v-card>
      </v-col>

      <v-col cols="12" md="4" data-aos="zoom-in" data-aos-delay="100">
        <v-card class="pa-4 action-card" @click="$router.push('/alta-proyecto')">
          <v-icon size="32" class="mb-2">mdi-briefcase-plus</v-icon>
          <div class="action-title">Nuevo proyecto</div>
        </v-card>
      </v-col>

      <v-col cols="12" md="4" data-aos="zoom-in" data-aos-delay="200">
        <v-card class="pa-4 action-card" @click="$router.push('/asignacion')">
          <v-icon size="32" class="mb-2">mdi-link-variant</v-icon>
          <div class="action-title">Asignar empleado</div>
        </v-card>
      </v-col>
    </v-row>

    <!-- CARRUSEL -->
    <v-card class="mb-6" data-aos="fade-up">
      <v-carousel
        height="260"
        cycle
        interval="4000"
        hide-delimiter-background
        show-arrows="hover"
      >
        <v-carousel-item v-for="item in slides" :key="item.title">
          <div class="carousel-slide">
            <img :src="item.image" class="carousel-img"/>
            <div class="carousel-overlay">
              <h2>{{ item.title }}</h2>
              <p>{{ item.text }}</p>
            </div>
          </div>
        </v-carousel-item>
      </v-carousel>
    </v-card>

    <!-- SOBRE NOSOTROS -->
    <v-row class="mb-6">
      <v-col cols="12" data-aos="fade-up">
        <v-card class="about-card">

          <div class="about-img-wrapper">
            <img
              src="https://images.unsplash.com/photo-1498050108023-c5249f4df085"
              class="about-img"
            />
          </div>

          <div class="about-text pa-6">
            <h2 class="text-h5 mb-3">Sobre nosotros</h2>

            <p class="mb-2">
              Future Space es una empresa tecnológica especializada en soluciones innovadoras.
            </p>

            <p class="mb-2">
              Gestiona empleados, proyectos y asignaciones de forma centralizada.
            </p>

            <p>
              Plataforma moderna, intuitiva y profesional.
            </p>
          </div>

        </v-card>
      </v-col>
    </v-row>

    <!-- BLOQUES -->
    <v-row>
      <v-col cols="12" md="4" data-aos="fade-up" data-aos-delay="100">
        <v-card class="info-card">
          <img src="https://images.unsplash.com/photo-1519389950473-47ba0277781c" />
          <div class="pa-4">
            <h3>Trabajo en equipo</h3>
            <p>Colaboración eficiente entre empleados.</p>
          </div>
        </v-card>
      </v-col>

      <v-col cols="12" md="4" data-aos="fade-up" data-aos-delay="200">
        <v-card class="info-card">
          <img src="https://images.unsplash.com/photo-1553877522-43269d4ea984" />
          <div class="pa-4">
            <h3>Gestión centralizada</h3>
            <p>Control total desde un único sistema.</p>
          </div>
        </v-card>
      </v-col>

      <v-col cols="12" md="4" data-aos="fade-up" data-aos-delay="300">
        <v-card class="info-card">
          <img src="https://images.unsplash.com/photo-1504384308090-c894fdcc538d" />
          <div class="pa-4">
            <h3>Innovación</h3>
            <p>Herramientas modernas adaptadas al futuro.</p>
          </div>
        </v-card>
      </v-col>
    </v-row>

  </v-container>
</template>

<script>
import AOS from 'aos'
import 'aos/dist/aos.css'

export default {
  data() {
    return {
      empleados: 1,
      proyectos: 1,
      asignaciones: 1,

      slides: [
        {
          title: "Centralización",
          text: "Todo en un mismo lugar",
          image: "https://images.unsplash.com/photo-1551434678-e076c223a692"
        },
        {
          title: "Entorno profesional",
          text: "Diseñado para empresa",
          image: "https://images.unsplash.com/photo-1521737604893-d14cc237f11d"
        },
        {
          title: "Gestión eficiente",
          text: "Control total de recursos",
          image: "https://images.unsplash.com/photo-1552664730-d307ca884978"
        }
      ]
    };
  },

  mounted() {
    AOS.init({
      duration: 800,
      once: true
    })
  }
};
</script>

<style scoped>

/* ===== CARDS ===== */
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

/* HERO */
.hero-card {
  background: linear-gradient(135deg, #026FC1, #028CF5);
  color: white;
}

/* BOTONES */
.hero-buttons {
  display: flex;
  gap: 16px;
  flex-wrap: wrap;
}

.hero-buttons .v-btn {
  min-width: 140px;
}

/* KPI */
.kpi-title {
  color: #F3F7FB;
}

.kpi-card::after {
  content: "";
  height: 4px;
  width: 100%;
  position: absolute;
  bottom: 0;
  left: 0;
  background: linear-gradient(90deg, #026FC1, #028CF5);
}

/* ACCIONES */
.action-card {
  cursor: pointer;
  text-align: center;
}

.action-card:hover {
  transform: translateY(-12px) scale(1.03);
}

/* ABOUT */
.about-card {
  display: flex;
  overflow: hidden;
}

.about-img-wrapper {
  flex: 1;
}

.about-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.about-text {
  flex: 1;
}

/* INFO */
.info-card img {
  width: 100%;
  height: 180px;
  object-fit: cover;
}

/* CARRUSEL */
.carousel-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  filter: brightness(0.55) contrast(1.1);
}

.carousel-overlay {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  text-align: center;
}

/* RESPONSIVE */
@media (max-width: 960px) {

  .hero-buttons {
    flex-direction: column;
    gap: 12px;
  }

  .about-card {
    flex-direction: column;
  }

  .about-img {
    max-height: 200px;
  }

}

</style>
<template>
  <!-- ===== FOOTER PRINCIPAL ===== -->
  <v-footer class="footer-clean">

    <v-container>

      <v-row>

        <!-- ===== BLOQUE LOGO + FRASE ===== -->
        <v-col cols="12" md="3">

          <img
            :src="logo"
            alt="Future Space"
            class="footer-logo-img">

          <p class="footer-slogan">
            {{ $t("footer.slogan") }}
          </p>

          <div class="footer-icons">
            <a href="https://www.linkedin.com" target="_blank">
            <v-icon class="footer-icon">mdi-linkedin</v-icon>
            </a>

            <a href="https://twitter.com" target="_blank">
            <v-icon class="footer-icon">mdi-twitter</v-icon>
            </a>

            <a href="https://www.youtube.com" target="_blank">
            <v-icon class="footer-icon">mdi-youtube</v-icon>
            </a>
          </div>

        </v-col>

        <!-- ===== SOBRE FUTURE ===== -->
        <v-col cols="12" md="3">

          <router-link to="/coming-soon" class="footer-title">
            {{ $t("footer.sobre") }}
          </router-link>

          <router-link to="/coming-soon" class="footer-link">
            {{ $t("footer.quienes") }}
          </router-link>


          <router-link to="/coming-soon" class="footer-link">
            {{ $t("footer.casos") }}
          </router-link>

          <router-link to="/coming-soon" class="footer-link">
            {{ $t("footer.internacional") }}
          </router-link>

          <router-link to="/coming-soon" class="footer-link">
            {{ $t("footer.contacto") }}
          </router-link>

        </v-col>

        <!-- ===== MUNDO FUTURE ===== -->
        <v-col cols="12" md="3">

          <router-link to="/coming-soon" class="footer-title ">
            {{ $t("footer.mundo") }}
          </router-link>

          <router-link to="/coming-soon" class="footer-link">
            {{ $t("footer.conocenos") }}
          </router-link>

          <router-link to="/coming-soon" class="footer-link">
            {{ $t("footer.cv") }}
          </router-link>

          <router-link to="/coming-soon" class="footer-link">
            {{ $t("footer.empleo") }}
          </router-link>

        </v-col>

        <!-- ===== TENDENCIAS / CANAL ===== -->
        <v-col cols="12" md="3">

          <router-link to="/coming-soon" class="footer-title">
            {{ $t("footer.tendencias") }}
          </router-link>
          <router-link to="/coming-soon" class="footer-link">
            {{ $t("footer.blog") }}
          </router-link>

          <router-link to="/coming-soon" class="footer-link">
            {{ $t("footer.webinars") }}
          </router-link>

          <router-link to="/coming-soon" class="footer-link">
            {{ $t("footer.charlas") }}
          </router-link>

          <router-link to="/coming-soon" class="footer-link mt-4">
            {{ $t("footer.canal") }}
          </router-link>

          <router-link to="/coming-soon" class="footer-link">
            {{ $t("footer.acceso") }}
          </router-link>

          <router-link to="/coming-soon" class="footer-link">
            {{ $t("footer.politica") }}
          </router-link>


          <router-link to="/coming-soon" class="footer-link">
            {{ $t("footer.procedimiento") }}
          </router-link>

        </v-col>

      </v-row>

      <!-- ===== PARTE INFERIOR ===== -->
      <v-divider class="my-4"></v-divider>

      <v-row justify="space-between" align="center">

        <span class="subtle">
          © 2026 Future Space
        </span>

        <span class="author">
          Antonio Ángeles
        </span>

      </v-row>

    </v-container>

    <!-- ===== BOTÓN VOLVER ARRIBA ===== -->
    <v-btn
      v-if="mostrarBoton"
      icon
      class="scroll-top-btn"
      @click="scrollTop"
    >
      <v-icon>mdi-chevron-up</v-icon>
    </v-btn>

  </v-footer>
</template>

<script>
import logo from '@/assets/images/Logo_blanco_Future-2.png'
export default {

  data() {
    return {
      logo,
      // Controla si el botón aparece o no
      mostrarBoton: false
    };
  },

  methods: {

    /**
     * Hace scroll suave hacia arriba
     */
    scrollTop() {
      window.scrollTo({
        top: 0,
        behavior: "smooth"
      });
    },

    /**
     * Detecta scroll y muestra/oculta botón
     */
    handleScroll() {
      this.mostrarBoton = window.scrollY > 200;
    }

  },

  mounted() {
    // Listener de scroll
    window.addEventListener("scroll", this.handleScroll);
  },

  beforeUnmount() {
    // Limpieza del listener (clean code)
    window.removeEventListener("scroll", this.handleScroll);
  }

};
</script>

<style scoped>

/* ===== FOOTER BASE ===== */
.footer-clean {
  background-color: #020617;
  border-top: 1px solid rgba(255,255,255,0.08);
  padding: 30px 20px;
  color: #cbd5f5;

  animation: fadeUp 0.6s ease;
}

/* ===== LOGO ===== */
.footer-logo {
  font-weight: bold;
  font-size: 20px;
  margin-bottom: 10px;
}

/* ===== LOGO IMAGEN ===== */
.footer-logo-img {
    height: 40px;
    margin-bottom: 10px;
    object-fit: contain;
    filter: drop-shadow(0 0 6px rgba(56,189,248,0.3));
}

/* ===== SLOGAN ===== */
.footer-slogan {
  font-size: 14px;
  color: #94a3b8;
  margin-bottom: 10px;
}

/* ===== ICONOS ===== */
.footer-icons i {
  font-size: 18px;
  margin-right: 10px;
  color: #94a3b8;
  transition: all 0.25s ease;
  cursor: pointer;
}

.footer-icons i:hover {
  color: #38bdf8;
  transform: scale(1.2);
}

.footer-icon {
  font-size: 18px;
  margin-right: 10px;
  color: #94a3b8;
  transition: all 0.25s ease;
  cursor: pointer;
}

.footer-icon:hover {
  color: #38bdf8;
  transform: scale(1.2);
}

/* ===== TITULOS ===== */
.footer-title {
  text-decoration: none;
  font-weight: 600;
  margin-bottom: 10px;
  color: #e2e8f0;
  position: relative;
}

.footer-title::after {
  text-decoration: none;
  content: "";
  display: block;
  width: 0;
  height: 2px;
  background: #38bdf8;
  transition: width 0.3s ease;
  margin-top: 4px;
}

.footer-title:hover::after {
  text-decoration: none;
  width: 40px;
}

/* ===== LINKS ===== */
.footer-link {
  text-decoration: none;
  display: block;
  color: #94a3b8;
  margin: 6px 0;
  font-size: 14px;
  transition: all 0.25s ease;
  cursor: pointer;
}

.footer-link:hover {
  text-decoration: none;
  color: #38bdf8;
  transform: translateX(6px);
}

/* ===== TEXTO INFERIOR ===== */
.subtle {
  color: #64748b;
}

.author {
  color: #e2e8f0;
  font-weight: 500;
}

/* ===== BOTÓN SCROLL ===== */
.scroll-top-btn {
  position: fixed;
  bottom: 20px;
  right: 20px;
  background: #028CF5;
  color: white;
  border-radius: 50%;
  box-shadow: 0 5px 15px rgba(0,0,0,0.4);

  transition: all 0.3s ease;

  /* Animación de aparición */
  animation: fadeScale 0.3s ease;
}

.scroll-top-btn:hover {
  background: #0ea5e9;
  transform: translateY(-5px) scale(1.05);
}

/* ===== ANIMACIONES ===== */
@keyframes fadeUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Animación PRO del botón */
@keyframes fadeScale {
  from {
    opacity: 0;
    transform: scale(0.7) translateY(10px);
  }
  to {
    opacity: 1;
    transform: scale(1) translateY(0);
  }
}



/* ===== RESPONSIVE ===== */
@media (max-width: 600px) {
  .footer-clean {
    text-align: center;
  }
}

</style>
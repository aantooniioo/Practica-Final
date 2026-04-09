<template>
  <v-footer class="footer-clean">

    <v-container class="footer-content">

      <span class="subtle">© 2026 Future Space S.A.</span>

      <span class="author">Antonio Ángeles</span>

    </v-container>

  </v-footer>
</template>

<style scoped>

.footer-clean {
  background-color: #020617;
  border-top: 1px solid rgba(255,255,255,0.08);
  padding: 10px 16px;
  font-size: 13px;
  color: #aaa;
}

/* CONTENEDOR FLEX */
.footer-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap; /* ESTO ES CLAVE PARA QUE EL TEXTO SE ALINEE A LA DERECHA EN MÓVIL */
  gap: 6px;
}

/* TEXTO */
.subtle {
  color: #64748b;
}

.author {
  color: #e2e8f0;
  font-weight: 500;
}

/* MÓVIL */
@media (max-width: 600px) {

  .footer-content {
    flex-direction: column;
    text-align: center;
  }

}

</style>
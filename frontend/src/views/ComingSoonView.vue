<template>
  <v-container fluid class="coming-container">

    <v-row
      class="fill-height"
      align="center"
      justify="center"
    >

      <v-col cols="12" md="6" lg="4">

        <v-card class="pa-8 text-center coming-card">

          <!-- ICONO -->
          <v-icon size="60" class="mb-4 icon-main">
            mdi-alert-circle-outline
          </v-icon>

          <!-- TÍTULO -->
          <h1 class="mb-2">
            {{ $t('coming.titulo') }}
          </h1>

          <!-- TEXTO -->
          <p class="mb-6">
            {{ $t('coming.descripcion') }}
          </p>

          <!-- BOTÓN -->
          <v-btn
            color="primary"
            class="btn-main"
            @click="$router.push('/')"
          >
            {{ $t('coming.volver') }}
          </v-btn>

        </v-card>

      </v-col>

    </v-row>

  </v-container>
</template>

<style scoped>

/* ===== CONTENEDOR ===== */
.coming-container {
  min-height: 100vh;
  background: linear-gradient(180deg, #001021, #012033);
}

/* ===== CARD CENTRAL ===== */
.coming-card {
  background-color: #012033;
  color: #D4F1FF;

  border-radius: 16px;
  border: 1px solid rgba(255,255,255,0.05);

  box-shadow:
    0 10px 25px rgba(0,0,0,0.5),
    0 2px 8px rgba(0,0,0,0.3);

  transition: all 0.25s ease;
}

/* Hover dashboard */
.coming-card:hover {
  transform: translateY(-10px);
  box-shadow:
    0 18px 40px rgba(0,0,0,0.7),
    0 0 15px rgba(2,111,193,0.4);
}

/* ===== ICONO ===== */
.icon-main {
  color: #38bdf8;
  opacity: 0.9;
}

/* ===== BOTÓN ===== */
.btn-main {
  transition: all 0.2s ease;
}

.btn-main:hover {
  filter: brightness(1.1);
  box-shadow: 0 4px 10px rgba(0,0,0,0.25);
}

.btn-main:active {
  transform: scale(0.97);
}

</style>
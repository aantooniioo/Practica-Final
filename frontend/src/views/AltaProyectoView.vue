<template>
  <v-container>

    <!-- Contenedor principal del formulario -->
    <v-card
      class="pa-4 formulario-card card-pro card-animated"
      data-aos="fade-up"
    >

      <!-- Título -->
      <v-card-title>{{ $t("alta_proyecto.titulo") }}</v-card-title>

      <!-- Formulario -->
      <v-form v-model="formValido" ref="form">

        <v-row>

          <!-- Descripción -->
          <v-col cols="12" md="8" data-aos="fade-up" data-aos-delay="100">
            <v-text-field
              :label="$t('alta_proyecto.descripcion')"
              v-model="proyecto.descripcion"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Fecha inicio -->
          <v-col cols="12" md="5" data-aos="fade-up" data-aos-delay="150">
            <v-text-field
              :label="$t('alta_proyecto.fecha_inicio')"
              type="date"
              v-model="proyecto.fechaInicio"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Lugar -->
          <v-col cols="12" md="5" data-aos="fade-up" data-aos-delay="200">
            <v-text-field
              :label="$t('alta_proyecto.lugar')"
              v-model="proyecto.lugar"
              :rules="[rules.requerido]"
            />
          </v-col>

        </v-row>

        <!-- Botones -->
        <v-card-actions class="mt-4">

          <!-- Guardar -->
          <v-btn
            color="green"
            class="btn-main"
            :disabled="!formValido"
            @click="guardar"
          >
            {{ $t("alta_proyecto.guardar") }}
          </v-btn>

          <!-- Cancelar -->
          <v-btn
            color="grey"
            class="btn-main"
            @click="$router.push('/proyectos')"
          >
            {{ $t("alta_proyecto.cancelar") }}
          </v-btn>

        </v-card-actions>

      </v-form>
    </v-card>

  </v-container>
</template>

<script>
import { crearProyecto } from "../services/proyectoService";
import Swal from "sweetalert2";
import AOS from "aos";
import "aos/dist/aos.css";

export default {
  data(){
    return{
      proyecto:{},
      formValido:false,

      rules:{
        requerido: v => !!v || this.$t("validaciones.requerido")
      }
    }
  },

  methods:{

    /**
     * Guarda proyecto tras validar formulario
     */
    guardar(){

      if (!this.$refs.form.validate()) return;

      crearProyecto(this.proyecto)
        .then(()=>{

          Swal.fire({
            icon: "success",
            title: this.$t("alta_proyecto.exito"),
            text: this.$t("alta_proyecto.exito_texto"),
            timer: 1500,
            showConfirmButton: false
          });

          this.$router.push("/proyectos");

        })
        .catch(error=>{

          Swal.fire({
            icon: "error",
            title: this.$t("alertas.error"),
            text: error.response?.data || this.$t("alertas.error_generico")
          });

        });

    }
  },

  mounted(){

    /**
     * Inicializa animaciones tipo dashboard
     */
    AOS.init({
      duration: 800,
      once: true
    });
  }
}
</script>

<style scoped>

/* Contenedor centrado */
.formulario-card {
  max-width: 900px;
  margin: 0 auto;
}

/* Hover tipo dashboard */
.v-card:hover {
  transform: translateY(-10px);
  box-shadow:
    0 18px 40px rgba(0,0,0,0.7),
    0 0 15px rgba(2,111,193,0.4);
}

/* Botones suaves */
.btn-main {
  transition: all 0.2s ease;
}

.btn-main:hover {
  filter: brightness(1.08);
  box-shadow: 0 8px 20px rgba(2,111,193,0.4);
}

.btn-main:active {
  transform: scale(0.97);
}

</style>
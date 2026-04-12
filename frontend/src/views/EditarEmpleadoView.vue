<template>
  <v-container>

    <!-- Contenedor principal del formulario -->
    <v-card
      class="pa-4 formulario-card card-pro card-animated"
      data-aos="fade-up"
    >

      <!-- Título -->
      <v-card-title>
        {{ $t("editar_empleado.titulo") }}
      </v-card-title>

      <!-- Formulario -->
      <v-form v-model="formValido" ref="form">

        <v-row>

          <!-- Nombre -->
          <v-col cols="12" md="6" data-aos="fade-up" data-aos-delay="100">
            <v-text-field
              :label="$t('editar_empleado.nombre')"
              v-model="empleado.nombre"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Apellido 1 -->
          <v-col cols="12" md="6" data-aos="fade-up" data-aos-delay="150">
            <v-text-field
              :label="$t('editar_empleado.apellido1')"
              v-model="empleado.apellido1"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Apellido 2 -->
          <v-col cols="12" md="6" data-aos="fade-up" data-aos-delay="200">
            <v-text-field
              :label="$t('editar_empleado.apellido2')"
              v-model="empleado.apellido2"
            />
          </v-col>

          <!-- Email -->
          <v-col cols="12" md="6" data-aos="fade-up" data-aos-delay="250">
            <v-text-field
              :label="$t('editar_empleado.email')"
              v-model="empleado.email"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Teléfono 1 -->
          <v-col cols="12" md="6" data-aos="fade-up" data-aos-delay="300">
            <v-text-field
              :label="$t('editar_empleado.telefono1')"
              v-model="empleado.telefono1"
            />
          </v-col>

          <!-- Teléfono 2 -->
          <v-col cols="12" md="6" data-aos="fade-up" data-aos-delay="350">
            <v-text-field
              :label="$t('editar_empleado.telefono2')"
              v-model="empleado.telefono2"
            />
          </v-col>

          <!-- Estado civil -->
          <v-col cols="12" md="6" data-aos="fade-up" data-aos-delay="400">
            <v-select
              :label="$t('editar_empleado.estado')"
              v-model="empleado.estadoCivil"
              :items="estados"
              item-title="title"
              item-value="value"
            />
          </v-col>

          <!-- Formación -->
          <v-col cols="12" md="6" data-aos="fade-up" data-aos-delay="450">
            <v-select
              :label="$t('editar_empleado.formacion')"
              v-model="empleado.formacionUniversitaria"
              :items="formaciones"
              item-title="title"
              item-value="value"
            />
          </v-col>

        </v-row>

        <!-- Botones -->
        <v-card-actions class="mt-4">

          <!-- Guardar -->
          <v-btn
            color="green"
            class="btn-main"
            @click="guardar"
          >
            {{ $t("editar_empleado.guardar") }}
          </v-btn>

          <!-- Cancelar -->
          <v-btn
            color="grey"
            class="btn-main"
            @click="$router.push('/empleados')"
          >
            {{ $t("editar_empleado.cancelar") }}
          </v-btn>

        </v-card-actions>

      </v-form>

    </v-card>

  </v-container>
</template>

<script>
import { getEmpleadoById, editarEmpleado } from "../services/empleadoService";
import AOS from "aos";
import "aos/dist/aos.css";

export default {
  data(){
    return{
      empleado: {},
      formValido: false,

      estados: [
        { title: this.$t('valores.soltero'), value: "S" },
        { title: this.$t('valores.casado'), value: "C" }
      ],

      formaciones: [
        { title: this.$t('valores.si'), value: "S" },
        { title: this.$t('valores.no'), value: "N" }
      ],

      rules: {
        requerido: v => !!v || this.$t("validaciones.requerido")
      }
    }
  },

  methods: {

    /**
     * Carga datos del empleado desde backend
     */
    cargarEmpleado(){

      const id = this.$route.params.idEmpleado;

      getEmpleadoById(id)
        .then(res => {
          this.empleado = res.data;
        });
    },

    /**
     * Guarda cambios del empleado
     */
    guardar(){

      if (!this.$refs.form.validate()) return;

      editarEmpleado(this.$route.params.idEmpleado, this.empleado)
        .then(() => {
          this.$router.push("/empleados");
        });
    }

  },

  mounted(){

    this.cargarEmpleado();

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

/* Botones con animación suave */
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
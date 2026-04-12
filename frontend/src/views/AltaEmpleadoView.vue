<template>
  <v-container>

    <!-- Contenedor principal del formulario -->
    <v-card
      class="pa-4 formulario-card card-pro card-animated"
      data-aos="fade-up"
    >

      <v-card-title>{{ $t('alta_empleado.titulo') }}</v-card-title>

      <v-form v-model="formValido" ref="form">

        <v-row>

          <v-col cols="12" md="6" data-aos="fade-up" data-aos-delay="100">
            <v-text-field
              :label="$t('alta_empleado.nombre')"
              v-model="empleado.nombre"
              :rules="[rules.requerido]"
            />
          </v-col>

          <v-col cols="12" md="6" data-aos="fade-up" data-aos-delay="150">
            <v-text-field
              :label="$t('alta_empleado.apellido1')"
              v-model="empleado.apellido1"
              :rules="[rules.requerido]"
            />
          </v-col>

          <v-col cols="12" md="6" data-aos="fade-up" data-aos-delay="200">
            <v-text-field
              :label="$t('alta_empleado.apellido2')"
              v-model="empleado.apellido2"
              :rules="[rules.requerido]"
            />
          </v-col>

          <v-col cols="12" md="6" data-aos="fade-up" data-aos-delay="250">
            <v-text-field
              :label="$t('alta_empleado.email')"
              v-model="empleado.email"
              :rules="[rules.requerido, rules.email]"
            />
          </v-col>

          <v-col cols="12" md="6" data-aos="fade-up" data-aos-delay="300">
            <v-text-field
              :label="$t('alta_empleado.telefono1')"
              v-model="empleado.telefono1"
              :rules="[rules.requerido, rules.telefono]"
            />
          </v-col>

          <v-col cols="12" md="6" data-aos="fade-up" data-aos-delay="350">
            <v-text-field
              :label="$t('alta_empleado.telefono2')"
              v-model="empleado.telefono2"
              :rules="[rules.requerido, rules.telefono]"
            />
          </v-col>

          <v-col cols="12" md="6" data-aos="fade-up" data-aos-delay="400">
            <v-select
              :label="$t('alta_empleado.estado')"
              v-model="empleado.estadoCivil"
              :items="estados"
              item-title="title"
              item-value="value"
              :rules="[rules.requerido]"
            />
          </v-col>

          <v-col cols="12" md="6" data-aos="fade-up" data-aos-delay="450">
            <v-select
              :label="$t('alta_empleado.formacion')"
              v-model="empleado.formacionUniversitaria"
              :items="formaciones"
              item-title="title"
              item-value="value"
              :rules="[rules.requerido]"
            />
          </v-col>

        </v-row>

        <v-card-actions class="mt-4">

          <v-btn
            color="green"
            class="btn-main"
            :disabled="!formValido"
            @click="guardar"
          >
            {{ $t('alta_empleado.guardar') }}
          </v-btn>

          <v-btn color="grey" class="btn-main" @click="$router.push('/empleados')">
            {{ $t('alta_empleado.cancelar') }}
          </v-btn>

        </v-card-actions>

      </v-form>
    </v-card>

  </v-container>
</template>

<script>
import { crearEmpleado } from "../services/empleadoService";
import Swal from "sweetalert2";
import AOS from "aos";
import "aos/dist/aos.css";

export default {
  data(){
    return{
      empleado:{},
      formValido:false,

      estados:[
        { title: this.$t('valores.soltero'), value:"S" },
        { title: this.$t('valores.casado'), value:"C" }
      ],

      formaciones:[
        { title: this.$t('valores.si'), value:"S" },
        { title: this.$t('valores.no'), value:"N" }
      ],

      rules:{
        requerido: v => !!v || this.$t('validaciones.requerido'),
        email: v => /.+@.+\..+/.test(v) || this.$t('validaciones.email'),
        telefono: v => /^[0-9]{9}$/.test(v) || this.$t('validaciones.telefono')
      }
    }
  },

  methods:{
    guardar(){
      if (!this.$refs.form.validate()) return;

      crearEmpleado(this.empleado)
        .then(()=>{
          Swal.fire({
            icon: "success",
            title: this.$t('alta_empleado.exito'),
            text: this.$t('alta_empleado.exito_texto'),
            timer: 1500,
            showConfirmButton: false
          });

          this.$router.push("/empleados");
        })
        .catch(error=>{
          Swal.fire({
            icon: "error",
            title: this.$t('alertas.error'),
            text: error.response?.data || this.$t('alertas.error_inesperado')
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

/* Botones */
.btn-main {
  transition: all 0.2s ease;
}

.btn-main:hover {
  filter: brightness(1.08);
  box-shadow: 0 8px 20px rgba(2,111,193,0.4);
}

/* Click */
.btn-main:active {
  transform: scale(0.97);
}
</style>
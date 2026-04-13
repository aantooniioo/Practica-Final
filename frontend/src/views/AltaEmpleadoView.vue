<template>
  <v-container>

    <v-card
      class="pa-4 formulario-card card-pro card-animated"
      data-aos="fade-up"
    >

      <v-card-title>{{ $t('alta_empleado.titulo') }}</v-card-title>

      <v-form v-model="formValido" ref="form">

        <v-row>

          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('alta_empleado.nombre')"
              v-model="empleado.nombre"
              :rules="[rules.requerido]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('alta_empleado.apellido1')"
              v-model="empleado.apellido1"
              :rules="[rules.requerido]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('alta_empleado.apellido2')"
              v-model="empleado.apellido2"
              :rules="[rules.requerido]"
            />
          </v-col>

          <v-col cols="7" md="6">
            <v-text-field
              :label="$t('alta_empleado.dni')"
              v-model="empleado.nif"
              :rules="[rules.requerido, rules.dni]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('alta_empleado.email')"
              v-model="empleado.email"
              :rules="[rules.requerido, rules.email]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('alta_empleado.telefono1')"
              v-model="empleado.telefono1"
              :rules="[rules.requerido, rules.telefono]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('alta_empleado.telefono2')"
              v-model="empleado.telefono2"
            />
          </v-col>

          <!-- Estado civil -->
          <v-col cols="12" md="6">
            <v-select
              :label="$t('alta_empleado.estado')"
              v-model="empleado.estadoCivil"
              :items="estados"
              item-title="title"
              item-value="value"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Formación -->
          <v-col cols="12" md="6">
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

          <v-btn 
            color="red" 
            class="btn-main" 
            @click="$router.push('/empleados')"
          >
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

      rules:{
        requerido: v => !!v || this.$t('validaciones.requerido'),

        email: v => {
          if (!v) return this.$t('validaciones.email_requerido');
          return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(v) || this.$t('validaciones.email');
        },

        telefono: v => /^[0-9]{9}$/.test(v) || this.$t('validaciones.telefono'),

        // Valida correctamente el DNI incluyendo la letra
        dni: v => {
          if (!v) return true;

          const regex = /^[0-9]{8}[A-Z]$/;
          if (!regex.test(v)) return this.$t('validaciones.dni_formato');

          const letras = "TRWAGMYFPDXBNJZSQVHLCKE";
          const numero = parseInt(v.substring(0, 8), 10);
          const letra = v.charAt(8);

          return letras[numero % 23] === letra || this.$t('validaciones.dni_incorrecto');
        }
      }
    }
  },

  computed:{

    // Genera estados de forma reactiva para permitir cambio de idioma
    estados(){
      return [
        { title: this.$t('valores.soltero'), value:"S" },
        { title: this.$t('valores.casado'), value:"C" }
      ];
    },

    // Genera formaciones de forma reactiva
    formaciones(){
      return [
        { title: this.$t('valores.si'), value:"S" },
        { title: this.$t('valores.no'), value:"N" }
      ];
    }

  },

  methods:{
    guardar(){

      if (!this.$refs.form.validate()) return;

      Swal.fire({
        title: this.$t('alertas.confirmar'),
        text: this.$t('alta_empleado.confirmar_creacion'),
        icon: "question",
        showCancelButton: true,
        confirmButtonText: this.$t('alertas.confirmar'),
        cancelButtonText: this.$t('alertas.cancelar')
      }).then(result => {

        if (!result.isConfirmed) return;

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
          .catch(error => {

            const mensaje =
                error.response?.data?.message ||
                error.response?.data ||
                this.$t('alertas.error_inesperado');

            Swal.fire({
                icon: "error",
                title: this.$t('alertas.error'),
                text: typeof mensaje === "string"
                    ? mensaje
                    : this.$t('alertas.error_inesperado')
            });

          });

      });
    }
  },

  mounted(){
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
  overflow: visible;
}

.v-row {
  margin: 0 !important;
}

.v-col {
  padding-bottom: 16px !important;
}

</style>
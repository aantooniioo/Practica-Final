<template>
  <v-container>

    <!-- Contenedor principal del formulario -->
    <v-card class="pa-4 formulario-card card-pro card-animated">

      <!-- Título del formulario -->
      <v-card-title>{{ $t('alta_empleado.titulo') }}</v-card-title>

      <!-- Formulario con validación -->
      <v-form v-model="formValido" ref="form">

        <v-row>

          <!-- Campo nombre -->
          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('alta_empleado.nombre')"
              v-model="empleado.nombre"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Campo primer apellido -->
          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('alta_empleado.apellido1')"
              v-model="empleado.apellido1"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Campo segundo apellido -->
          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('alta_empleado.apellido2')"
              v-model="empleado.apellido2"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Campo email -->
          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('alta_empleado.email')"
              v-model="empleado.email"
              :rules="[rules.requerido, rules.email]"
            />
          </v-col>

          <!-- Campo teléfono principal -->
          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('alta_empleado.telefono1')"
              v-model="empleado.telefono1"
              :rules="[rules.requerido, rules.telefono]"
            />
          </v-col>

          <!-- Campo teléfono secundario -->
          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('alta_empleado.telefono2')"
              v-model="empleado.telefono2"
              :rules="[rules.requerido, rules.telefono]"
            />
          </v-col>

          <!-- Selector de estado civil -->
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

          <!-- Selector de formación -->
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

        <!-- Botones de acción -->
        <v-card-actions class="mt-4">

          <!-- Botón guardar -->
          <v-btn color="green" :disabled="!formValido" @click="guardar">
            {{ $t('alta_empleado.guardar') }}
          </v-btn>

          <!-- Botón cancelar -->
          <v-btn color="grey" @click="$router.push('/empleados')">
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

export default {
  data(){
    return{
      // Objeto que contiene los datos del nuevo empleado
      empleado:{},

      // Estado de validación del formulario
      formValido:false,

      // Opciones para el selector de estado civil
      estados:[
        { title: this.$t('valores.soltero'), value:"S" },
        { title: this.$t('valores.casado'), value:"C" }
      ],

      // Opciones para el selector de formación
      formaciones:[
        { title: this.$t('valores.si'), value:"S" },
        { title: this.$t('valores.no'), value:"N" }
      ],

      // Reglas de validación reutilizables
      rules:{
        requerido: v => !!v || this.$t('validaciones.requerido'),
        email: v => /.+@.+\..+/.test(v) || this.$t('validaciones.email'),
        telefono: v => /^[0-9]{9}$/.test(v) || this.$t('validaciones.telefono')
      }
    }
  },

  methods:{

    // Valida el formulario y envía los datos al backend
    guardar(){

      // Valida todos los campos antes de continuar
      if (!this.$refs.form.validate()) return;

      // Llamada al servicio para crear el empleado
      crearEmpleado(this.empleado)
        .then(()=>{

          // Muestra mensaje de éxito
          Swal.fire({
            icon: "success",
            title: this.$t('alta_empleado.exito'),
            text: this.$t('alta_empleado.exito_texto'),
            timer: 1500,
            showConfirmButton: false
          });

          // Redirige al listado de empleados
          this.$router.push("/empleados");

        })
        .catch(error=>{

          // Muestra mensaje de error
          Swal.fire({
            icon: "error",
            title: this.$t('alertas.error'),
            text: error.response?.data || this.$t('alertas.error_inesperado')
          });

        });

    }
  }
}
</script>

<style scoped>
/* Estilo del contenedor del formulario */
.formulario-card {
  max-width: 900px;
  margin: 0 auto;
}
</style>
<template>
  <v-container>

    <!-- Contenedor principal del formulario -->
    <v-card class="pa-4 formulario-card card-pro card-animated">

      <!-- Título del formulario -->
      <v-card-title>
        {{ $t("editar_empleado.titulo") }}
      </v-card-title>

      <!-- Formulario con validación -->
      <v-form v-model="formValido" ref="form">

        <v-row>

          <!-- Campo nombre -->
          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('editar_empleado.nombre')"
              v-model="empleado.nombre"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Campo primer apellido -->
          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('editar_empleado.apellido1')"
              v-model="empleado.apellido1"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Campo segundo apellido -->
          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('editar_empleado.apellido2')"
              v-model="empleado.apellido2"
            />
          </v-col>

          <!-- Campo email -->
          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('editar_empleado.email')"
              v-model="empleado.email"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Campo teléfono principal -->
          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('editar_empleado.telefono1')"
              v-model="empleado.telefono1"
            />
          </v-col>

          <!-- Campo teléfono secundario -->
          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('editar_empleado.telefono2')"
              v-model="empleado.telefono2"
            />
          </v-col>

          <!-- Selector de estado civil -->
          <v-col cols="12" md="6">
            <v-select
              :label="$t('editar_empleado.estado')"
              v-model="empleado.estadoCivil"
              :items="estados"
              item-title="title"
              item-value="value"
            />
          </v-col>

          <!-- Selector de formación -->
          <v-col cols="12" md="6">
            <v-select
              :label="$t('editar_empleado.formacion')"
              v-model="empleado.formacionUniversitaria"
              :items="formaciones"
              item-title="title"
              item-value="value"
            />
          </v-col>

        </v-row>

        <!-- Botones de acción -->
        <v-card-actions class="mt-4">

          <!-- Botón guardar -->
          <v-btn color="green" @click="guardar">
            {{ $t("editar_empleado.guardar") }}
          </v-btn>

          <!-- Botón cancelar -->
          <v-btn color="grey" @click="$router.push('/empleados')">
            {{ $t("editar_empleado.cancelar") }}
          </v-btn>

        </v-card-actions>

      </v-form>

    </v-card>

  </v-container>
</template>

<script>
import { getEmpleadoById, editarEmpleado } from "../services/empleadoService";

export default {
  data(){
    return{
      // Objeto que contiene los datos del empleado
      empleado: {},

      // Estado de validación del formulario
      formValido: false,

      // Opciones para el selector de estado civil
      estados: [
        { title: this.$t('valores.soltero'), value: "S" },
        { title: this.$t('valores.casado'), value: "C" }
      ],

      // Opciones para el selector de formación
      formaciones: [
        { title: this.$t('valores.si'), value: "S" },
        { title: this.$t('valores.no'), value: "N" }
      ],

      // Reglas de validación reutilizables
      rules: {
        requerido: v => !!v || this.$t("validaciones.requerido")
      }
    }
  },

  methods: {

    // Carga los datos del empleado desde el backend
    cargarEmpleado(){

      const id = this.$route.params.idEmpleado;

      getEmpleadoById(id)
        .then(res => {

          // Asigna los datos recibidos al objeto del formulario
          this.empleado = res.data;

          // Mensaje de depuración opcional
          console.log(this.empleado);
        });
    },

    // Valida el formulario y envía los datos al backend
    guardar(){

      // Valida todos los campos del formulario
      if (!this.$refs.form.validate()) return;

      // Llama al servicio para actualizar el empleado
      editarEmpleado(this.$route.params.idEmpleado, this.empleado)
        .then(() => {

          // Redirige al listado tras guardar correctamente
          this.$router.push("/empleados");
        });
    }

  },

  // Se ejecuta al montar la vista
  mounted(){
    this.cargarEmpleado();
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
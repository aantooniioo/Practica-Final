<template>
  <v-container>

    <!-- Contenedor principal del formulario -->
    <v-card class="pa-4 formulario-card card-pro card-animated">

      <!-- Título del formulario -->
      <v-card-title>{{ $t("alta_proyecto.titulo") }}</v-card-title>

      <!-- Formulario con validación -->
      <v-form v-model="formValido" ref="form">

        <v-row>

          <!-- Campo descripción -->
          <v-col cols="12" md="8">
            <v-text-field
              :label="$t('alta_proyecto.descripcion')"
              v-model="proyecto.descripcion"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Campo fecha de inicio -->
          <v-col cols="12" md="5">
            <v-text-field
              :label="$t('alta_proyecto.fecha_inicio')"
              type="date"
              v-model="proyecto.fechaInicio"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Campo lugar -->
          <v-col cols="12" md="5">
            <v-text-field
              :label="$t('alta_proyecto.lugar')"
              v-model="proyecto.lugar"
              :rules="[rules.requerido]"
            />
          </v-col>

        </v-row>

        <!-- Botones de acción -->
        <v-card-actions class="mt-4">

          <!-- Botón guardar -->
          <v-btn color="green" :disabled="!formValido" @click="guardar">
            {{ $t("alta_proyecto.guardar") }}
          </v-btn>

          <!-- Botón cancelar -->
          <v-btn color="grey" @click="$router.push('/proyectos')">
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

export default {
  data(){
    return{
      // Objeto que contiene los datos del nuevo proyecto
      proyecto:{},

      // Estado de validación del formulario
      formValido:false,

      // Reglas de validación reutilizables
      rules:{
        requerido: v => !!v || this.$t("validaciones.requerido")
      }
    }
  },

  methods:{

    // Valida el formulario y envía los datos al backend
    guardar(){

      // Valida todos los campos antes de continuar
      if (!this.$refs.form.validate()) return;

      // Llamada al servicio para crear el proyecto
      crearProyecto(this.proyecto)
        .then(()=>{

          // Muestra mensaje de éxito
          Swal.fire({
            icon: "success",
            title: this.$t("alta_proyecto.exito"),
            text: this.$t("alta_proyecto.exito_texto"),
            timer: 1500,
            showConfirmButton: false
          });

          // Redirige al listado de proyectos
          this.$router.push("/proyectos");

        })
        .catch(error=>{

          // Muestra mensaje de error
          Swal.fire({
            icon: "error",
            title: this.$t("alertas.error"),
            text: error.response?.data || this.$t("alertas.error_generico")
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
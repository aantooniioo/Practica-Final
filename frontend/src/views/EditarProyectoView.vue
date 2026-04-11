<template>
  <v-container>

    <!-- Contenedor principal del formulario -->
    <v-card class="pa-4 formulario-card card-pro card-animated">

      <!-- Título del formulario -->
      <v-card-title>{{ $t("editar_proyecto.titulo") }}</v-card-title>

      <!-- Formulario con validación -->
      <v-form v-model="formValido" ref="form">

        <v-row>

          <!-- Campo descripción -->
          <v-col cols="12">
            <v-text-field
              :label="$t('editar_proyecto.descripcion')"
              v-model="proyecto.descripcion"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Campo fecha de inicio -->
          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('editar_proyecto.fecha_inicio')"
              type="date"
              v-model="proyecto.fechaInicio"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Campo fecha de fin -->
          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('editar_proyecto.fecha_fin')"
              type="date"
              v-model="proyecto.fechaFin"
            />
          </v-col>

          <!-- Campo lugar -->
          <v-col cols="12">
            <v-text-field
              :label="$t('editar_proyecto.lugar')"
              v-model="proyecto.lugar"
              :rules="[rules.requerido]"
            />
          </v-col>

        </v-row>

        <!-- Botones de acción -->
        <v-card-actions class="mt-4">

          <!-- Botón guardar -->
          <v-btn color="green" :disabled="!formValido" @click="guardar">
            {{ $t("editar_proyecto.guardar") }}
          </v-btn>

          <!-- Botón cancelar -->
          <v-btn color="grey" @click="$router.push('/proyectos')">
            {{ $t("editar_proyecto.cancelar") }}
          </v-btn>

        </v-card-actions>

      </v-form>
    </v-card>

  </v-container>
</template>

<script>
import { getProyectoById, editarProyecto } from "../services/proyectoService";

export default {
  data(){
    return{
      // Objeto que contiene los datos del proyecto
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

    // Carga los datos del proyecto desde el backend
    cargarProyecto(){

      const id = this.$route.params.idProyecto;

      getProyectoById(id)
        .then(res=>{

          // Asigna los datos recibidos al objeto del formulario
          this.proyecto = res.data;
        });
    },

    // Valida el formulario y envía los datos al backend
    guardar(){

      // Valida todos los campos antes de guardar
      if (!this.$refs.form.validate()) return;

      // Llama al servicio para actualizar el proyecto
      editarProyecto(this.$route.params.idProyecto, this.proyecto)
        .then(()=>{

          // Redirige al listado tras guardar correctamente
          this.$router.push("/proyectos");
        });
    }

  },

  // Se ejecuta al montar la vista
  mounted(){
    this.cargarProyecto();
  }
};
</script>

<style scoped>
/* Estilo del contenedor del formulario */
.formulario-card {
  max-width: 900px;
  margin: 0 auto;
}
</style>
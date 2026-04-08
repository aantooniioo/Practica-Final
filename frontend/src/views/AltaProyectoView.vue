<template>
  <v-container>

    <v-card class="pa-4 formulario-card">
      <v-card-title>Alta de Proyecto</v-card-title>

      <!-- Formulario -->
      <v-form v-model="formValido" ref="form">

        <v-row>

          <v-col cols="12">
            <v-text-field
              label="Descripción"
              v-model="proyecto.descripcion"
              :rules="[rules.requerido]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              label="Fecha Inicio"
              type="date"
              v-model="proyecto.fechaInicio"
              :rules="[rules.requerido]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              label="Fecha Fin"
              type="date"
              v-model="proyecto.fechaFin"
            />
          </v-col>

          <v-col cols="12">
            <v-text-field
              label="Lugar"
              v-model="proyecto.lugar"
              :rules="[rules.requerido]"
            />
          </v-col>

        </v-row>

        <v-card-actions class="mt-4">
          <v-btn color="green" :disabled="!formValido" @click="guardar">
            Guardar
          </v-btn>

          <v-btn color="grey" @click="$router.push('/proyectos')">
            Cancelar
          </v-btn>
        </v-card-actions>

      </v-form>
    </v-card>

    <!-- Dialog error -->
    <v-dialog v-model="errorDialog" max-width="400">
      <v-card>
        <v-card-title>Error</v-card-title>
        <v-card-text>{{ errorMensaje }}</v-card-text>
        <v-card-actions>
          <v-btn color="primary" @click="errorDialog = false">Aceptar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-container>
</template>

<script>
import { crearProyecto } from "../services/proyectoService";

export default {
  data(){
    return{
      proyecto:{},
      formValido:false,

      errorDialog:false,
      errorMensaje:"",

      rules:{
        requerido: v => !!v || "Campo obligatorio"
      }
    }
  },

  methods:{
    guardar(){

      // Validar antes de enviar
      if (!this.$refs.form.validate()) return;

      crearProyecto(this.proyecto)
        .then(()=>{
          this.$router.push("/proyectos");
        })
        .catch(error=>{
          this.errorMensaje = error.response?.data || "Error al guardar";
          this.errorDialog = true;
        });
    }
  }
}
</script>

<style scoped>
.formulario-card {
  max-width: 900px;
  margin: 0 auto;
}
</style>
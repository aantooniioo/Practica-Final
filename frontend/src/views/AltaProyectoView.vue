<template>
  <v-container>

    <v-card>
      <v-card-title>Alta de Proyecto</v-card-title>

      <!-- Formulario -->
      <v-form v-model="formValido">

        <v-text-field
          label="Descripción"
          v-model="proyecto.descripcion"
          :rules="[rules.requerido]"
        />

        <v-text-field
          label="Fecha Inicio"
          type="date"
          v-model="proyecto.fechaInicio"
          :rules="[rules.requerido]"
        />

        <v-text-field
          label="Lugar"
          v-model="proyecto.lugar"
          :rules="[rules.requerido]"
        />

        <v-card-actions>
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

      // Controlar errores mostrados
      errorDialog:false,
      errorMensaje:"",

      // Reglas de validación
      rules:{
        requerido: v => !!v || "Campo obligatorio"
      }
    }
  },

  methods:{
    // Guardar proyecto
    guardar(){
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
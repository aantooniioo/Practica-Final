<template>
  <v-container>

    <v-card class="pa-4">

      <!-- Título -->
      <v-card-title class="text-h5">
        Alta de Proyecto
      </v-card-title>

      <v-card-text>

        <v-form @submit.prevent="guardar">

          <v-text-field
            label="Descripción"
            v-model="proyecto.descripcion"
            :rules="[rules.required]"
          ></v-text-field>

          <v-text-field
            label="Fecha Inicio"
            type="date"
            v-model="proyecto.fechaInicio"
            :rules="[rules.required]"
          ></v-text-field>

          <v-text-field
            label="Fecha Fin"
            type="date"
            v-model="proyecto.fechaFin"
          ></v-text-field>

          <v-text-field
            label="Lugar"
            v-model="proyecto.lugar"
          ></v-text-field>

          <!-- Botones -->
          <v-btn color="success" type="submit" class="mt-3 mr-2">
            Guardar
          </v-btn>

          <v-btn class="mt-3" @click="$router.push('/proyectos')">
            Volver
          </v-btn>

        </v-form>

      </v-card-text>

    </v-card>

    <!-- Mensajes -->
    <v-snackbar v-model="snackbar" :color="snackbarColor">
      {{ snackbarText }}
    </v-snackbar>

  </v-container>
</template>

<script>
import { crearProyecto } from "../services/proyectoService";

export default {
  data(){
    return{
      proyecto:{
        descripcion:"",
        fechaInicio:"",
        fechaFin:"",
        lugar:""
      },

      rules:{
        required: v => !!v || 'Campo obligatorio'
      },

      snackbar:false,
      snackbarText:"",
      snackbarColor:"success"
    }
  },

  methods:{

    mostrarMensaje(texto, color='success'){
      this.snackbarText = texto;
      this.snackbarColor = color;
      this.snackbar = true;
    },

    guardar(){
      crearProyecto(this.proyecto)
        .then(()=>{
          this.mostrarMensaje("Proyecto creado correctamente");
          this.$router.push('/proyectos');
        })
        .catch(()=>{
          this.mostrarMensaje("Error al crear proyecto","error");
        });
    }

  }
}
</script>
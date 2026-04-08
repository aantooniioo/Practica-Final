<template>
  <v-container>

    <v-card class="pa-4">

      <!-- Título -->
      <v-card-title class="text-h5">
        Alta de Empleado
      </v-card-title>

      <v-card-text>

        <v-form @submit.prevent="guardar">

          <!-- Campos -->
          <v-text-field
            label="Nombre"
            v-model="empleado.nombre"
            :rules="[rules.required]"
          ></v-text-field>

          <v-text-field
            label="Apellido 1"
            v-model="empleado.apellido1"
            :rules="[rules.required]"
          ></v-text-field>

          <v-text-field
            label="Apellido 2"
            v-model="empleado.apellido2"
          ></v-text-field>

          <v-text-field
            label="Email"
            v-model="empleado.email"
            :rules="[rules.required, rules.email]"
          ></v-text-field>

          <v-text-field label="Teléfono 1" v-model="empleado.telefono1"></v-text-field>
          <v-text-field label="Teléfono 2" v-model="empleado.telefono2"></v-text-field>

          <!-- Selects -->
          <v-select
            label="Estado Civil"
            :items="['S','C']"
            v-model="empleado.estadoCivil"
          ></v-select>

          <v-select
            label="Formación Universitaria"
            :items="['S','N']"
            v-model="empleado.formacionUniversitaria"
          ></v-select>

          <!-- Botones -->
          <v-btn color="success" type="submit" class="mt-3 mr-2">
            Guardar
          </v-btn>

          <v-btn class="mt-3" @click="$router.push('/empleados')">
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
import { crearEmpleado } from "../services/empleadoService";

export default {
  data(){
    return{
      empleado:{
        nombre:"",
        apellido1:"",
        apellido2:"",
        email:"",
        telefono1:"",
        telefono2:"",
        estadoCivil:"",
        formacionUniversitaria:""
      },

      // Reglas básicas de validación
      rules:{
        required: v => !!v || 'Campo obligatorio',
        email: v => /.+@.+\..+/.test(v) || 'Email no válido'
      },

      // Control de mensajes
      snackbar:false,
      snackbarText:"",
      snackbarColor:"success"
    }
  },

  methods:{

    // Muestra mensaje
    mostrarMensaje(texto, color='success'){
      this.snackbarText = texto;
      this.snackbarColor = color;
      this.snackbar = true;
    },

    // Guarda empleado
    guardar(){
      crearEmpleado(this.empleado)
        .then(()=>{
          this.mostrarMensaje("Empleado creado correctamente");
          this.$router.push('/empleados');
        })
        .catch(()=>{
          this.mostrarMensaje("Error al crear empleado","error");
        });
    }

  }
}
</script>
<template>
  <v-container>

    <v-card>
      <v-card-title>Alta de Empleado</v-card-title>

      <!-- Formulario controlado -->
      <v-form v-model="formValido">

        <v-text-field
          label="Nombre"
          v-model="empleado.nombre"
          :rules="[rules.requerido]"
        />

        <v-text-field
          label="Apellido 1"
          v-model="empleado.apellido1"
          :rules="[rules.requerido]"
        />

        <v-text-field
          label="Email"
          v-model="empleado.email"
          :rules="[rules.requerido, rules.email]"
        />

        <v-card-actions>
          <v-btn color="green" :disabled="!formValido" @click="guardar">
            Guardar
          </v-btn>

          <v-btn color="grey" @click="$router.push('/empleados')">
            Cancelar
          </v-btn>
        </v-card-actions>

      </v-form>
    </v-card>

    <!-- Dialog de error -->
    <v-dialog v-model="errorDialog" max-width="400">
      <v-card>
        <v-card-title>Error</v-card-title>
        <v-card-text>{{ errorMensaje }}</v-card-text>
        <v-card-actions>
          <v-btn color="#3b82f6" @click="errorDialog = false">Aceptar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-container>
</template>

<script>
import { crearEmpleado } from "../services/empleadoService";

export default {
  data(){
    return{
      empleado:{},
      formValido:false,

      // Controlar errores mostrados
      errorDialog:false,
      errorMensaje:"",

      // Reglas de validación
      rules:{
        requerido: v => !!v || "Campo obligatorio",
        email: v => /.+@.+\..+/.test(v) || "Email inválido"
      }
    }
  },

  methods:{
    // Guardar empleado en backend
    guardar(){
      crearEmpleado(this.empleado)
        .then(()=>{
          this.$router.push("/empleados");
        })
        .catch(error=>{
          this.errorMensaje = error.response?.data || "Error al guardar";
          this.errorDialog = true;
        });
    }
  }
}
</script>
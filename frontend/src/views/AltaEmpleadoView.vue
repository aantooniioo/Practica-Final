<template>
  <v-container>

    <v-card class="pa-4 formulario-card">
      <v-card-title>Alta de Empleado</v-card-title>

      <!-- Formulario controlado -->
      <v-form v-model="formValido" ref="form">

        <v-row>

          <v-col cols="12" md="6">
            <v-text-field
              label="Nombre"
              v-model="empleado.nombre"
              :rules="[rules.requerido]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              label="Apellido 1"
              v-model="empleado.apellido1"
              :rules="[rules.requerido]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              label="Apellido 2"
              v-model="empleado.apellido2"
              :rules="[rules.requerido]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              label="Email"
              v-model="empleado.email"
              :rules="[rules.requerido, rules.email]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              label="Teléfono 1"
              v-model="empleado.telefono1"
              :rules="[rules.requerido, rules.telefono]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              label="Teléfono 2"
              v-model="empleado.telefono2"
              :rules="[rules.requerido, rules.telefono]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-select
              label="Estado civil"
              v-model="empleado.estadoCivil"
              :items="estados"
              :rules="[rules.requerido]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-select
              label="Formación universitaria"
              v-model="empleado.formacionUniversitaria"
              :items="formaciones"
              :rules="[rules.requerido]"
            />
          </v-col>

        </v-row>

        <v-card-actions class="mt-4">
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

      errorDialog:false,
      errorMensaje:"",

      estados:[
        { title:"Soltero", value:"S" },
        { title:"Casado", value:"C" }
      ],

      formaciones:[
        { title:"Sí", value:"S" },
        { title:"No", value:"N" }
      ],

      rules:{
        requerido: v => !!v || "Campo obligatorio",
        email: v => /.+@.+\..+/.test(v) || "Email inválido",
        telefono: v => /^[0-9]{9}$/.test(v) || "Teléfono inválido"
      }
    }
  },

  methods:{
    guardar(){
      if (!this.$refs.form.validate()) return;

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

<style scoped>
.formulario-card {
  max-width: 900px;
  margin: 0 auto;
}
</style>
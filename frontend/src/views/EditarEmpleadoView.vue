<template>
  <v-container>

    <v-card class="pa-4 formulario-card card-pro card-animated">
      <v-card-title>Editar Empleado</v-card-title>

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
            Guardar cambios
          </v-btn>

          <v-btn color="grey" @click="$router.push('/empleados')">
            Cancelar
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
      empleado:{},
      formValido:false,

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

  methods: {

    // Cargar empleado por id
    cargarEmpleado() {
      const id = this.$route.params.id;

      getEmpleadoById(id)
        .then(res => {
          this.empleado = res.data;
        });
    },

    // Guardar cambios
    guardar() {

      if (!this.$refs.form.validate()) return;

      editarEmpleado(this.$route.params.id, this.empleado)
        .then(() => {
          this.$router.push("/empleados");
        });
    }

  },

  mounted() {
    this.cargarEmpleado();
  }
}
</script>

<style scoped>
.formulario-card {
  max-width: 900px;
  margin: 0 auto;
}
</style>
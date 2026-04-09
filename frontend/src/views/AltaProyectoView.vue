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
              label="Fecha inicio"
              type="date"
              v-model="proyecto.fechaInicio"
              :rules="[rules.requerido]"
            />
          </v-col>

          <v-col cols="12" md="6">
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

  </v-container>
</template>

<script>
import { crearProyecto } from "../services/proyectoService";
import Swal from "sweetalert2";

export default {
  data(){
    return{
      proyecto:{},
      formValido:false,

      rules:{
        requerido: v => !!v || "Campo obligatorio"
      }
    }
  },

  methods:{
    guardar(){

      if (!this.$refs.form.validate()) return;

      crearProyecto(this.proyecto)
        .then(()=>{

          Swal.fire({
            icon: "success",
            title: "Proyecto creado",
            text: "Se ha registrado correctamente",
            timer: 1500,
            showConfirmButton: false
          });

          this.$router.push("/proyectos");

        })
        .catch(error=>{

          Swal.fire({
            icon: "error",
            title: "Error",
            text: error.response?.data || "Error al guardar"
          });

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
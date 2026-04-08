<template>
  <v-container>

    <v-card>
      <v-card-title>Editar Proyecto</v-card-title>

      <v-form v-model="formValido" ref="form">

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
          label="Fecha Fin"
          type="date"
          v-model="proyecto.fechaFin"
        />

        <v-text-field
          label="Lugar"
          v-model="proyecto.lugar"
          :rules="[rules.requerido]"
        />

        <v-card-actions>
          <v-btn color="green" :disabled="!formValido" @click="guardar">
            Guardar cambios
          </v-btn>

          <v-btn color="grey" @click="$router.push('/proyectos')">
            Cancelar
          </v-btn>
        </v-card-actions>

      </v-form>
    </v-card>

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
import { getProyectoById, actualizarProyecto } from "../services/proyectoService";

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

    cargarProyecto(){
      const id = this.$route.params.id;

      getProyectoById(id)
        .then(res=>{
          this.proyecto = res.data;
        });
    },

    guardar(){
      if (!this.$refs.form.validate()) return;

      actualizarProyecto(this.$route.params.id, this.proyecto)
        .then(()=>{
          this.$router.push("/proyectos");
        })
        .catch(error=>{
          this.errorMensaje = error.response?.data || "Error al actualizar";
          this.errorDialog = true;
        });
    }

  },

  mounted(){
    this.cargarProyecto();
  }
};
</script>
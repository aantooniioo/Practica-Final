<template>
  <v-container>

    <v-card class="pa-4 formulario-card card-pro card-animated">
      <v-card-title>{{ $t("editar_proyecto.titulo") }}</v-card-title>

      <v-form v-model="formValido" ref="form">

        <v-row>

          <v-col cols="12">
            <v-text-field
              :label="$t('editar_proyecto.descripcion')"
              v-model="proyecto.descripcion"
              :rules="[rules.requerido]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('editar_proyecto.fecha_inicio')"
              type="date"
              v-model="proyecto.fechaInicio"
              :rules="[rules.requerido]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('editar_proyecto.fecha_fin')"
              type="date"
              v-model="proyecto.fechaFin"
            />
          </v-col>

          <v-col cols="12">
            <v-text-field
              :label="$t('editar_proyecto.lugar')"
              v-model="proyecto.lugar"
              :rules="[rules.requerido]"
            />
          </v-col>

        </v-row>

        <v-card-actions class="mt-4">
          <v-btn color="green" :disabled="!formValido" @click="guardar">
            {{ $t("editar_proyecto.guardar") }}
          </v-btn>

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
      proyecto:{},
      formValido:false,

      rules:{
        requerido: v => !!v || this.$t("validaciones.requerido")
      }
    }
  },

  methods:{

    cargarProyecto(){
      const id = this.$route.params.idProyecto;

      getProyectoById(id)
        .then(res=>{
          this.proyecto = res.data;
        });
    },

    guardar(){
      if (!this.$refs.form.validate()) return;

      editarProyecto(this.$route.params.idProyecto, this.proyecto)
        .then(()=>{
          this.$router.push("/proyectos");
        });
    }

  },

  mounted(){
    this.cargarProyecto();
  }
};
</script>

<style scoped>
.formulario-card {
  max-width: 900px;
  margin: 0 auto;
}
</style>
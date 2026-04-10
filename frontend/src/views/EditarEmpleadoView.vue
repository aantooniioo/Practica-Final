<template>
  <v-container>

    <v-card class="pa-4 formulario-card card-pro card-animated">
      <v-card-title>{{ $t("editar_empleado.titulo") }}</v-card-title>

      <v-form v-model="formValido" ref="form">

        <v-row>

          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('editar_empleado.nombre')"
              v-model="empleado.nombre"
              :rules="[rules.requerido]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('editar_empleado.apellido1')"
              v-model="empleado.apellido1"
              :rules="[rules.requerido]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('editar_empleado.apellido2')"
              v-model="empleado.apellido2"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('editar_empleado.email')"
              v-model="empleado.email"
              :rules="[rules.requerido, rules.email]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('editar_empleado.telefono1')"
              v-model="empleado.telefono1"
              :rules="[rules.telefono]"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('editar_empleado.telefono2')"
              v-model="empleado.telefono2"
            />
          </v-col>

        </v-row>

        <v-card-actions class="mt-4">
          <v-btn color="green" :disabled="!formValido" @click="guardar">
            {{ $t("editar_empleado.guardar") }}
          </v-btn>

          <v-btn color="grey" @click="$router.push('/empleados')">
            {{ $t("editar_empleado.cancelar") }}
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

      rules:{
        requerido: v => !!v || this.$t("validaciones.requerido"),
        email: v => /.+@.+\..+/.test(v) || this.$t("validaciones.email"),
        telefono: v => /^[0-9]{9}$/.test(v) || this.$t("validaciones.telefono")
      }
    }
  },

  methods:{

    cargarEmpleado(){
      const id = this.$route.params.idEmpleado;

      getEmpleadoById(id)
        .then(res=>{
          this.empleado = res.data;
        });
    },

    guardar(){
      if (!this.$refs.form.validate()) return;

      editarEmpleado(this.$route.params.idEmpleado, this.empleado)
        .then(()=>{
          this.$router.push("/empleados");
        });
    }

  },

  mounted(){
    this.cargarEmpleado();
  }
};
</script>

<style scoped>
.formulario-card {
  max-width: 900px;
  margin: 0 auto;
}
</style>
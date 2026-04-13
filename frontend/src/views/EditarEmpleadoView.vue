<template>
  <v-container>

    <v-card
      class="pa-4 formulario-card card-pro card-animated"
      data-aos="fade-up"
    >

      <v-card-title>
        {{ $t("editar_empleado.titulo") }}
      </v-card-title>

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
              :label="$t('alta_empleado.dni')"
              v-model="empleado.nif"
              :rules="[rules.dni]"
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
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('editar_empleado.telefono2')"
              v-model="empleado.telefono2"
            />
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field
              :label="$t('editar_empleado.fecha_nacimiento')"
              type="date"
              v-model="empleado.fechaNacimiento"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Estado civil -->
          <v-col cols="12" md="6">
            <v-select
              :label="$t('editar_empleado.estado')"
              v-model="empleado.estado"
              :items="estadosCiviles"
              item-title="title"
              item-value="value"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Formación -->
          <v-col cols="12" md="6">
            <v-select
              :label="$t('editar_empleado.formacion')"
              v-model="empleado.formacion"
              :items="formaciones"
              item-title="title"
              item-value="value"
              :rules="[rules.requerido]"
            />
          </v-col>

        </v-row>

        <v-card-actions class="mt-4">

          <v-btn 
            color="green" 
            class="btn-main" 
            @click="guardar"
          >
            {{ $t("editar_empleado.guardar") }}
          </v-btn>

          <v-btn 
            color="red" 
            class="btn-main" 
            @click="$router.push('/empleados')"
          >
            {{ $t("editar_empleado.cancelar") }}
          </v-btn>

        </v-card-actions>

      </v-form>

    </v-card>

  </v-container>
</template>

<script>
import { getEmpleadoById, editarEmpleado } from "../services/empleadoService";
import Swal from "sweetalert2";
import AOS from "aos";
import "aos/dist/aos.css";

export default {
  data(){
    return{
      empleado: {},
      formValido: false,

      rules: {
        requerido: v => !!v || this.$t("validaciones.requerido"),

        email: v => {
          if (!v) return this.$t('validaciones.email_requerido');
          return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(v) || this.$t('validaciones.email');
        },

        dni: v => {
          if (!v) return true;

          const regex = /^[0-9]{8}[A-Z]$/;
          if (!regex.test(v)) return this.$t('validaciones.dni_formato');

          const letras = "TRWAGMYFPDXBNJZSQVHLCKE";
          const numero = parseInt(v.substring(0, 8), 10);
          const letra = v.charAt(8);

          return letras[numero % 23] === letra || this.$t('validaciones.dni_incorrecto');
        }
      }
    }
  },

  computed:{

    // Genera estados de forma reactiva para permitir cambio de idioma
    estadosCiviles(){
      return [
        { title: this.$t('valores.soltero'), value:"S" },
        { title: this.$t('valores.casado'), value:"C" }
      ];
    },

    // Genera formaciones de forma reactiva
    formaciones(){
      return [
        { title: this.$t('valores.si'), value:"S" },
        { title: this.$t('valores.no'), value:"N" }
      ];
    }

  },

  methods: {

    cargarEmpleado(){
      const id = this.$route.params.idEmpleado;

      getEmpleadoById(id)
        .then(res => {
          this.empleado = res.data;
        });
    },

    guardar(){

      if (!this.$refs.form.validate()) return;

      Swal.fire({
        title: this.$t('alertas.confirmar'),
        text: this.$t('editar_empleado.confirmar_edicion'),
        icon: "question",
        showCancelButton: true,
        confirmButtonText: this.$t('alertas.confirmar'),
        cancelButtonText: this.$t('alertas.cancelar')
      }).then(result => {

        if (!result.isConfirmed) return;

        editarEmpleado(this.$route.params.idEmpleado, this.empleado)
          .then(() => {

            Swal.fire({
              icon: "success",
              title: this.$t("alertas.exito"),
              text: this.$t("empleados.editado"),
              timer: 1500,
              showConfirmButton: false
            });

            this.$router.push("/empleados");
          });

      });
    }

  },

  mounted(){
    this.cargarEmpleado();

    AOS.init({
      duration: 800,
      once: true
    });
  }
}
</script>

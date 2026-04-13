<template>
  <v-container>

    <v-card
      class="pa-4 formulario-card card-pro card-animated"
      data-aos="fade-up"
    >

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

          <v-col cols="8" md="6">
            <v-text-field
              :label="$t('editar_proyecto.fecha_inicio')"
              type="date"
              v-model="proyecto.fechaInicio"
              :rules="[rules.requerido]"
            />
          </v-col>

          <!-- Fecha fin + botón limpiar -->
          <v-col cols="8" md="6">

            <div style="display:flex; gap:8px; align-items:center;">

              <v-text-field
                style="flex:1"
                :label="$t('editar_proyecto.fecha_fin')"
                type="date"
                v-model="proyecto.fechaFin"
              />

              <v-btn
                icon
                size="small"
                color="red"
                @click="limpiarFechaFin"
              >
                <v-icon>mdi-close</v-icon>
              </v-btn>

            </div>

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

          <v-btn
            color="green"
            class="btn-main"
            :disabled="!formValido"
            @click="guardar"
          >
            {{ $t("editar_proyecto.guardar") }}
          </v-btn>

          <v-btn
            color="red"
            class="btn-main"
            @click="$router.push('/proyectos')"
          >
            {{ $t("editar_proyecto.cancelar") }}
          </v-btn>

        </v-card-actions>

      </v-form>
    </v-card>

  </v-container>
</template>

<script>
import { getProyectoById, editarProyecto } from "../services/proyectoService";
import Swal from "sweetalert2";
import AOS from "aos";
import "aos/dist/aos.css";

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

    // Limpia la fecha de fin del proyecto
    limpiarFechaFin(){
      this.proyecto.fechaFin = null;
    },

    cargarProyecto(){

      const id = this.$route.params.idProyecto;

      getProyectoById(id)
        .then(res=>{
          this.proyecto = res.data;
        });
    },

    guardar(){

      if (!this.$refs.form.validate()) return;

      Swal.fire({
        title: this.$t("alertas.confirmar"),
        text: this.$t("editar_proyecto.confirmacion"),
        icon: "question",
        showCancelButton: true,
        confirmButtonColor: "#22c55e",
        cancelButtonText: this.$t("alertas.cancelar"),
        confirmButtonText: this.$t("alertas.confirmar")
      }).then(result => {

        if (result.isConfirmed) {

          editarProyecto(this.$route.params.idProyecto, this.proyecto)
            .then(()=>{

              Swal.fire({
                icon: "success",
                title: this.$t("alertas.exito"),
                text: this.$t("proyectos.editado_ok"),
                timer: 1500,
                showConfirmButton: false
              });

              this.$router.push("/proyectos");

            });

        }

      });

    }

  },

  mounted(){
    this.cargarProyecto();

    AOS.init({
      duration: 800,
      once: true
    });
  }
};
</script>

<style scoped>

/* Contenedor centrado */
.formulario-card {
  max-width: 900px;
  margin: 0 auto;
}

/* Hover tipo dashboard */
.v-card:hover {
  transform: translateY(-10px);
  box-shadow:
    0 18px 40px rgba(0,0,0,0.7),
    0 0 15px rgba(2,111,193,0.4);
}

/* Botones con animación suave */
.btn-main {
  transition: all 0.2s ease;
}

.btn-main:hover {
  filter: brightness(1.08);
  box-shadow: 0 8px 20px rgba(2,111,193,0.4);
}

.btn-main:active {
  transform: scale(0.97);
}

</style>
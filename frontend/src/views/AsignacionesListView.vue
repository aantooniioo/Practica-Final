<template>
  <v-container class="mt-6">

    <v-row justify="center">
      <v-col cols="12" md="10">

        <v-card
          class="pa-4 formulario-card elevation-4 card-pro card-animated"
          data-aos="fade-up"
        >

          <v-card-title>
            <h2 class="text-h5">{{ $t('asignaciones.titulo') }}</h2>
          </v-card-title>

          <v-text-field
            v-model="busqueda"
            :label="$t('asignaciones.buscar')"
            prepend-inner-icon="mdi-magnify"
            variant="outlined"
            clearable
            class="mb-4"
          />

          <v-table density="comfortable">

            <thead class="bg-grey-darken-3">
              <tr>
                <th>{{ $t('asignaciones.empleado') }}</th>
                <th>{{ $t('asignaciones.proyecto') }}</th>
                <th>{{ $t('asignaciones.fecha') }}</th>
                <th class="text-center">{{ $t('asignaciones.acciones') }}</th>
              </tr>
            </thead>

            <tbody>

              <tr v-if="asignacionesFiltradas.length === 0">
                <td colspan="4" class="text-center py-6">
                  {{ $t('asignaciones.sin_datos') }}
                </td>
              </tr>

              <tr
                v-for="a in asignacionesFiltradas"
                :key="a.idEmpleado + '-' + a.idProyecto"
                class="row-hover"
              >
                <td>{{ nombreEmpleado(a.idEmpleado) }}</td>
                <td>{{ nombreProyecto(a.idProyecto) }}</td>
                <td>{{ formatearFecha(a.fechaAlta) }}</td>

                <td class="text-center">

                  <v-btn
                    variant="text"
                    class="text-blue-lighten-2 text-caption btn-action"
                    @click="abrirEditar(a)"
                  >
                    <v-icon size="18">mdi-pencil</v-icon>
                    {{ $t('asignaciones.editar') }}
                  </v-btn>

                  <v-btn
                    variant="text"
                    class="text-red-lighten-2 text-caption btn-action"
                    @click="confirmarEliminar(a)"
                  >
                    <v-icon size="18">mdi-delete</v-icon>
                    {{ $t('asignaciones.eliminar') }}
                  </v-btn>

                </td>

              </tr>

            </tbody>

          </v-table>

        </v-card>

      </v-col>
    </v-row>

  </v-container>
</template>

<script>
import { getEmpleados } from "../services/empleadoService";
import { getProyectos } from "../services/proyectoService";
import { getAsignaciones, eliminarAsignacion, editarAsignacion } from "../services/empleadoProyectoService.js";
import Swal from "sweetalert2";
import AOS from "aos";
import "aos/dist/aos.css";

export default {
  data(){
    return{
      asignaciones: [],
      empleados: [],
      proyectos: [],
      busqueda: ""
    }
  },

  computed: {

    asignacionesFiltradas(){

      if(!this.busqueda) return this.asignaciones;

      const texto = this.busqueda.toLowerCase();

      return this.asignaciones.filter(a => {

        const contenido = `
          ${this.nombreEmpleado(a.idEmpleado)}
          ${this.nombreProyecto(a.idProyecto)}
          ${a.fechaAlta}
        `.toLowerCase();

        return contenido.includes(texto);
      });
    }
  },

  mounted(){

    this.cargarAsignaciones();

    getEmpleados().then(res => this.empleados = res.data);
    getProyectos().then(res => this.proyectos = res.data);

    AOS.init({
      duration: 800,
      once: true
    });
  },

  methods:{

    nombreEmpleado(id){
      const emp = this.empleados.find(e => e.idEmpleado === id);
      return emp ? emp.nombre + " " + emp.apellido1 : id;
    },

    nombreProyecto(id){
      const proy = this.proyectos.find(p => p.idProyecto === id);
      return proy ? proy.descripcion : id;
    },

    cargarAsignaciones(){
      getAsignaciones().then(res => this.asignaciones = res.data);
    },

    // Formatea la fecha para que no falle el input date
    formatearFecha(fecha){
      return fecha ? fecha.split("T")[0] : "";
    },

    abrirEditar(a){

      Swal.fire({
        title: this.$t('asignaciones.editar'),
        input: "date",
        inputValue: this.formatearFecha(a.fechaAlta),
        showCancelButton: true,
        confirmButtonText: this.$t('alertas.confirmar'),
        cancelButtonText: this.$t('alertas.cancelar')
      }).then(result => {

        if(result.isConfirmed){

          editarAsignacion(a.idEmpleado, a.idProyecto, {
            fechaAlta: result.value
          })
          .then(()=>{

            Swal.fire({
              icon: "success",
              title: this.$t("alertas.exito"),
              timer: 1500,
              showConfirmButton: false
            });

            this.cargarAsignaciones();
          });

        }

      });
    },

    confirmarEliminar(a){

      Swal.fire({
        title: this.$t("alertas.confirmar"),
        text: this.$t("asignacion.eliminar_confirmacion"),
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#ef4444",
        cancelButtonText: this.$t("alertas.cancelar"),
        confirmButtonText: this.$t("alertas.confirmar")
      }).then(result => {

        if(result.isConfirmed){

          eliminarAsignacion(a.idEmpleado, a.idProyecto)
            .then(()=>{

              Swal.fire({
                icon: "success",
                title: this.$t("alertas.exito"),
                text: this.$t("asignacion.eliminado_ok"),
                timer: 1500,
                showConfirmButton: false
              });

              this.cargarAsignaciones();
            });

        }

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

.row-hover {
  transition: all 0.2s ease;
}

.row-hover:hover {
  background-color: rgba(2,111,193,0.1);
  transform: scale(1.01);
}

.v-card:hover {
  transform: translateY(-10px);
  box-shadow:
    0 18px 40px rgba(0,0,0,0.7),
    0 0 15px rgba(2,111,193,0.4);
}

/* Elimina el scroll horizontal correctamente */
:deep(.v-table__wrapper) {
  overflow-x: hidden !important;
}

</style>
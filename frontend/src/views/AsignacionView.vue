<template>
  <v-container class="mt-6">

    <!-- Tarjeta principal -->
    <v-card class="pa-5 elevation-4 card-pro card-animated">

      <!-- TÍTULO RESPONSIVE PRO -->
      <v-card-title class="titulo-responsive">
        {{ $t('asignacion.titulo') }}
      </v-card-title>

      <v-card-text>

        <v-form>

          <v-row justify="center">

            <!-- Empleado -->
            <v-col cols="12" md="5">
              <v-select
                :label="$t('asignacion.empleado')"
                :items="empleados"
                item-title="nombreCompleto"
                item-value="idEmpleado"
                v-model="idEmpleado"
              >
                <template #item="{ props, item }">
                  <v-list-item
                    v-bind="props"
                    :disabled="yaAsignado(item?.raw?.idEmpleado, idProyecto)"
                  >
                    <v-list-item-title>
                      {{ item?.raw?.nombreCompleto }}
                      <span v-if="yaAsignado(item?.raw?.idEmpleado, idProyecto)">
                        ({{ $t('asignacion.ya_asignado') }})
                      </span>
                    </v-list-item-title>
                  </v-list-item>
                </template>
              </v-select>
            </v-col>

            <!-- Proyecto -->
            <v-col cols="12" md="5">
              <v-select
                :label="$t('asignacion.proyecto')"
                :items="proyectos"
                item-title="descripcion"
                item-value="idProyecto"
                v-model="idProyecto"
              />
            </v-col>

            <!-- Fecha -->
            <v-col cols="12" md="5">
              <v-text-field
                :label="$t('asignacion.fecha')"
                type="date"
                v-model="fechaAlta"
              />
            </v-col>

          </v-row>

          <!-- Botón -->
          <v-card-actions class="mt-4 justify-center">
            <v-btn color="primary" @click="asignar">
              {{ $t('asignacion.boton') }}
            </v-btn>
          </v-card-actions>

        </v-form>

      </v-card-text>

    </v-card>

  </v-container>
</template>

<script>
import { getEmpleados } from "../services/empleadoService";
import { getProyectos } from "../services/proyectoService";
import { asignarEmpleadoProyecto, getAsignaciones } from "../services/empleadoProyectoService";
import Swal from "sweetalert2";

export default {
  name: "AsignacionView",

  data(){
    return{
      empleados: [],        // Lista de empleados disponibles
      proyectos: [],        // Lista de proyectos disponibles
      asignaciones: [],     // Relación de asignaciones existentes
      idEmpleado: null,     // Empleado seleccionado
      idProyecto: null,     // Proyecto seleccionado
      fechaAlta: null       // Fecha de asignación
    }
  },

  methods:{
    // Carga los empleados desde el backend
    cargarEmpleados(){
      getEmpleados().then(res => {
        this.empleados = res.data.map(e => ({
          ...e,
          // Se construye el nombre completo para mostrarlo en el select
          nombreCompleto: (e.nombre || '') + " " + (e.apellido1 || '')
        }));
      });
    },

    // Carga los proyectos desde el backend
    cargarProyectos(){
      getProyectos().then(res => {
        this.proyectos = res.data;
      });
    },

    // Carga las asignaciones existentes
    cargarAsignaciones(){
      getAsignaciones().then(res => {
        this.asignaciones = res.data;
      });
    },

    // Comprueba si un empleado ya ha sido asignado a ese proyecto
    yaAsignado(idEmpleado, idProyecto){
      if(!idEmpleado || !idProyecto) return false;

      return this.asignaciones.some(a =>
        a.idEmpleado === idEmpleado && a.idProyecto === idProyecto
      );
    },

    // Envía la asignación al backend
    asignar(){

      // Validación de campos obligatorios
      if(!this.idEmpleado || !this.idProyecto || !this.fechaAlta){
        Swal.fire({
          icon: "warning",
          title: this.$t('asignacion.alertas.campos'),
          text: this.$t('asignacion.alertas.campos_texto')
        });
        return;
      }
      
      // Evita duplicados
      if(this.yaAsignado(this.idEmpleado, this.idProyecto)){
        Swal.fire({
          icon: "error",
          title: this.$t('asignacion.alertas.duplicado'),
          text: this.$t('asignacion.alertas.duplicado_texto')
        });
        return;
      }

      const datos = {
        idEmpleado: this.idEmpleado,
        idProyecto: this.idProyecto,
        fechaAlta: this.fechaAlta
      };

      // Petición al backend
      asignarEmpleadoProyecto(datos)
        .then(()=>{

          // Mensaje de éxito
          Swal.fire({
            icon: "success",
            title: this.$t('asignacion.alertas.exito'),
            text: this.$t('asignacion.alertas.exito_texto'),
            timer: 1500,
            showConfirmButton: false
          });

          // Limpiar el formulario
          this.idEmpleado = null;
          this.idProyecto = null;
          this.fechaAlta = null;

          // Recarga datos
          this.cargarAsignaciones();
        })
        .catch(error=>{
          Swal.fire({
            icon: "error",
            title: this.$t('alertas.error'),
            text: error.response?.data || this.$t('alertas.error_generico')
          });
        });
    }

  },

  mounted(){
    // Cargar inicial de datos
    this.cargarAsignaciones();
    this.cargarProyectos();
    this.cargarEmpleados();
  }
}
</script>

<style scoped>

/* ===== TÍTULO RESPONSIVE ===== */
.titulo-responsive {
  font-weight: 700;
  font-size: 20px;
  line-height: 1.3;
  white-space: normal;
  word-break: break-word;
}

@media (min-width: 960px) {
  .titulo-responsive {
    font-size: 26px;
  }
}

</style>
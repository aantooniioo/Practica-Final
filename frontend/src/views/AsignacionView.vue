<template>
  <v-container class="mt-6">

    <!-- Tarjeta principal -->
    <v-card
      class="pa-5 elevation-4 card-pro card-animated"
      data-aos="fade-up"
    >

      <!-- TÍTULO -->
      <v-card-title class="titulo-responsive">
        {{ $t('asignacion.titulo') }}
      </v-card-title>

      <v-card-text>

        <v-form>

          <v-row justify="center">

            <!-- Empleado -->
            <v-col cols="12" md="5" data-aos="fade-up" data-aos-delay="100">
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
            <v-col cols="12" md="5" data-aos="fade-up" data-aos-delay="150">
              <v-select
                :label="$t('asignacion.proyecto')"
                :items="proyectos"
                item-title="descripcion"
                item-value="idProyecto"
                v-model="idProyecto"
              />
            </v-col>

            <!-- Fecha -->
            <v-col cols="12" md="5" data-aos="fade-up" data-aos-delay="200">
              <v-text-field
                :label="$t('asignacion.fecha')"
                type="date"
                v-model="fechaAlta"
              />
            </v-col>

          </v-row>

          <!-- Botón -->
          <v-card-actions class="mt-4 justify-center">
            <v-btn
              color="green"
              class="btn-main"
              @click="asignar"
            >
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
import AOS from "aos";
import "aos/dist/aos.css";

export default {
  name: "AsignacionView",

  data(){
    return{
      empleados: [],
      proyectos: [],
      asignaciones: [],
      idEmpleado: null,
      idProyecto: null,
      fechaAlta: null
    }
  },

  methods:{
    cargarEmpleados(){
      getEmpleados().then(res => {
        this.empleados = res.data.map(e => ({
          ...e,
          nombreCompleto: (e.nombre || '') + " " + (e.apellido1 || '')
        }));
      });
    },

    cargarProyectos(){
      getProyectos().then(res => {
        this.proyectos = res.data;
      });
    },

    cargarAsignaciones(){
      getAsignaciones().then(res => {
        this.asignaciones = res.data;
      });
    },

    yaAsignado(idEmpleado, idProyecto){
      if(!idEmpleado || !idProyecto) return false;

      return this.asignaciones.some(a =>
        a.idEmpleado === idEmpleado && a.idProyecto === idProyecto
      );
    },

    asignar(){

      if(!this.idEmpleado || !this.idProyecto || !this.fechaAlta){
        Swal.fire({
          icon: "warning",
          title: this.$t('asignacion.alertas.campos'),
          text: this.$t('asignacion.alertas.campos_texto')
        });
        return;
      }
      
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

      asignarEmpleadoProyecto(datos)
        .then(()=>{

          Swal.fire({
            icon: "success",
            title: this.$t('asignacion.alertas.exito'),
            text: this.$t('asignacion.alertas.exito_texto'),
            timer: 1500,
            showConfirmButton: false
          });

          this.idEmpleado = null;
          this.idProyecto = null;
          this.fechaAlta = null;

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
    this.cargarAsignaciones();
    this.cargarProyectos();
    this.cargarEmpleados();

    AOS.init({
      duration: 800,
      once: true
    });
  }
}
</script>

<style scoped>

.titulo-responsive {
  font-weight: 700;
  font-size: 20px;
  line-height: 1.3;
}

@media (min-width: 960px) {
  .titulo-responsive {
    font-size: 26px;
  }
}

/* Hover card tipo dashboard */
.v-card:hover {
  transform: translateY(-10px);
  box-shadow:
    0 18px 40px rgba(0,0,0,0.7),
    0 0 15px rgba(2,111,193,0.4);
}

/* Botón suave */
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
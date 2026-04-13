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

            <!-- PROYECTO -->
            <v-col cols="12" md="6" data-aos="fade-up">
              <v-select
                :label="$t('asignacion.proyecto')"
                :items="proyectos"
                item-title="descripcion"
                item-value="idProyecto"
                v-model="idProyecto"
                @update:modelValue="onProyectoChange"
              />
            </v-col>

            <!-- FECHA -->
            <v-col cols="12" md="6" data-aos="fade-up">
              <v-text-field
                :label="$t('asignacion.fecha')"
                type="date"
                v-model="fechaAlta"
              />
            </v-col>

          </v-row>

          <!-- LISTA EMPLEADOS -->
          <v-row v-if="idProyecto" class="mt-4">

            <v-col cols="12">

              <!-- SOLO AÑADIDO card-empleados -->
              <v-card class="pa-3 card-empleados">

                <!-- CABECERA CON CONTADOR -->
                <div class="d-flex justify-space-between align-center mb-2">

                  <div class="text-subtitle-1">
                    {{ $t('asignacion.empleados') }}
                  </div>

                  <div class="text-caption contador-asignados">
                    {{ totalAsignados }} {{ $t('asignacion.seleccionados') }}
                  </div>

                </div>

                <v-list>

                  <v-list-item
                    v-for="emp in empleadosConEstado"
                    :key="emp.idEmpleado"
                  >

                    <v-checkbox
                      v-model="emp.asignado"
                      :label="emp.nombreCompleto"
                      @change="animarCambio($event)"
                      :class="{ 'checkbox-activo': emp.asignado }"
                    />

                  </v-list-item>

                </v-list>

              </v-card>

            </v-col>

          </v-row>

          <!-- BOTÓN -->
          <v-card-actions class="mt-4 justify-center">

            <v-btn
              color="green"
              class="btn-main"
              @click="guardarCambios"
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
import {
  asignarEmpleadoProyecto,
  eliminarAsignacion,
  getAsignaciones
} from "../services/empleadoProyectoService";

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
      empleadosConEstado: [],
      idProyecto: null,
      fechaAlta: null
    }
  },

  computed:{

    /**
     * Calcula el número de empleados seleccionados
     */
    totalAsignados(){
      return this.empleadosConEstado.filter(e => e.asignado).length;
    }

  },

  methods:{

    /**
     * Carga la lista de empleados desde el backend
     */
    cargarEmpleados(){
      getEmpleados().then(res => {
        this.empleados = res.data.map(e => ({
          ...e,
          nombreCompleto: (e.nombre || '') + " " + (e.apellido1 || '')
        }));
      });
    },

    /**
     * Carga la lista de proyectos desde el backend
     */
    cargarProyectos(){
      getProyectos().then(res => {
        this.proyectos = res.data;
      });
    },

    /**
     * Carga las asignaciones existentes
     */
    cargarAsignaciones(){
      getAsignaciones().then(res => {
        this.asignaciones = res.data;
      });
    },

    /**
     * Se ejecuta al cambiar el proyecto seleccionado
     */
    onProyectoChange(){

      // Genera lista de empleados con estado asignado o no
      this.empleadosConEstado = this.empleados.map(emp => {

        const asignado = this.asignaciones.some(a =>
          a.idEmpleado === emp.idEmpleado &&
          a.idProyecto === this.idProyecto
        );

        return {
          ...emp,
          asignado
        };
      });
    },

    /**
     * Aplica una animación visual al cambiar un checkbox
     */
    animarCambio(el){

      if(!el?.target) return;

      const elemento = el.target.closest(".v-selection-control");

      if(!elemento) return;

      elemento.classList.add("anim-checkbox");

      setTimeout(() => {
        elemento.classList.remove("anim-checkbox");
      }, 300);
    },

    /**
     * Guarda cambios masivos de asignaciones
     */
    guardarCambios(){

      // Validación de campos obligatorios
      if(!this.idProyecto || !this.fechaAlta){
        Swal.fire({
          icon: "warning",
          title: this.$t('asignacion.alertas.campos'),
          text: this.$t('asignacion.alertas.campos_texto')
        });
        return;
      }

      // Confirmación antes de aplicar cambios
      Swal.fire({
        title: this.$t('alertas.confirmar'),
        text: this.$t('asignacion.confirmacion'),
        icon: "question",
        showCancelButton: true,
        confirmButtonColor: "#22c55e",
        cancelButtonText: this.$t('alertas.cancelar'),
        confirmButtonText: this.$t('alertas.confirmar')
      }).then(async result => {

        if(result.isConfirmed){

          try {

            for(const emp of this.empleadosConEstado){

              const yaEstaba = this.asignaciones.some(a =>
                a.idEmpleado === emp.idEmpleado &&
                a.idProyecto === this.idProyecto
              );

              // Crear nueva asignación
              if(emp.asignado && !yaEstaba){

                await asignarEmpleadoProyecto({
                  idEmpleado: emp.idEmpleado,
                  idProyecto: this.idProyecto,
                  fechaAlta: this.fechaAlta
                });
              }

              // Eliminar asignación existente
              if(!emp.asignado && yaEstaba){

                await eliminarAsignacion(emp.idEmpleado, this.idProyecto);
              }

            }

            // Muestra mensaje de éxito
            Swal.fire({
              icon: "success",
              title: this.$t('asignacion.alertas.exito'),
              text: this.$t('asignacion.alertas.exito_texto'),
              timer: 1500,
              showConfirmButton: false
            });

            // Recarga datos
            this.cargarAsignaciones();
            this.onProyectoChange();

          } catch (error){

            const mensaje =
              error.response?.data?.message ||
              error.response?.data ||
              this.$t('alertas.error_generico');

            Swal.fire({
              icon: "error",
              title: this.$t('alertas.error'),
              text: typeof mensaje === "string"
                ? mensaje
                : this.$t('alertas.error_generico')
            });

          }

        }

      });

    }

  },

  mounted(){

    // Carga inicial de datos
    this.cargarAsignaciones();
    this.cargarProyectos();
    this.cargarEmpleados();

    /**
     * Inicializa animaciones tipo dashboard
     */
    AOS.init({
      duration: 800,
      once: true
    });
  }
}
</script>

<style scoped>

/* Contador de empleados seleccionados */
.contador-asignados {
  color: #94a3b8;
  font-weight: 500;
}

/* Checkbox activo */
.checkbox-activo {
  transition: all 0.2s ease;
}

/* Animación al cambiar estado */
.anim-checkbox {
  animation: pulseCheck 0.3s ease;
}

@keyframes pulseCheck {
  0% { transform: scale(1); }
  50% { transform: scale(1.15); }
  100% { transform: scale(1); }
}

/* Título responsive */
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

/* AÑADIDO: Hover específico para bloque de empleados */
.card-empleados {
  transition: all 0.25s ease;
}

.card-empleados:hover {
  transform: translateY(-5px);
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
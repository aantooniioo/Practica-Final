<template>
  <v-container class="mt-6">

    <!-- Tarjeta principal -->
    <v-card class="pa-5 elevation-4 card-pro card-animated">

      <v-card-title>
        <h2 class="text-h5">Asignar empleado a proyecto</h2>
      </v-card-title>

      <v-card-text>

        <v-form>

          <!-- Select empleados -->
          <v-select
            label="Empleado"
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
                    (ya asignado)
                  </span>

                </v-list-item-title>
              </v-list-item>
            </template>
          </v-select>

          <!-- Select proyectos -->
          <v-select
            label="Proyecto"
            :items="proyectos"
            item-title="descripcion"
            item-value="idProyecto"
            v-model="idProyecto"
          />

          <!-- Fecha -->
          <v-text-field
            label="Fecha"
            type="date"
            v-model="fechaAlta"
          />

          <!-- Botón -->
          <v-card-actions>
            <v-btn color="primary" @click="asignar">
              Asignar
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
      empleados: [],
      proyectos: [],
      asignaciones: [],
      idEmpleado: null,
      idProyecto: null,
      fechaAlta: null
    }
  },

  methods:{

    // Cargar empleados
    cargarEmpleados(){
      getEmpleados().then(res => {
        this.empleados = res.data.map(e => ({
          ...e,
          nombreCompleto: (e.nombre || '') + " " + (e.apellido1 || '')
        }));
      });
    },

    // Cargar proyectos
    cargarProyectos(){
      getProyectos().then(res => {
        this.proyectos = res.data;
      });
    },

    // Cargar asignaciones
    cargarAsignaciones(){
      getAsignaciones().then(res => {
        this.asignaciones = res.data;
      });
    },

    // Comprobar si ya está asignado
    yaAsignado(idEmpleado, idProyecto){

      if(!idEmpleado || !idProyecto) return false;

      return this.asignaciones.some(a =>
        a.idEmpleado === idEmpleado && a.idProyecto === idProyecto
      );
    },

    // Asignar con SweetAlert
    asignar(){

      // Validación campos
      if(!this.idEmpleado || !this.idProyecto || !this.fechaAlta){
        Swal.fire({
          icon: "warning",
          title: "Campos incompletos",
          text: "Debes rellenar todos los campos"
        });
        return;
      }

      // Validación duplicado
      if(this.yaAsignado(this.idEmpleado, this.idProyecto)){
        Swal.fire({
          icon: "error",
          title: "Asignación duplicada",
          text: "El empleado ya está asignado a este proyecto"
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
            title: "Asignación realizada",
            text: "El empleado ha sido asignado correctamente",
            timer: 1500,
            showConfirmButton: false
          });

          // Reset formulario
          this.idEmpleado = null;
          this.idProyecto = null;
          this.fechaAlta = null;

          this.cargarAsignaciones();

        })
        .catch(error=>{
          Swal.fire({
            icon: "error",
            title: "Error",
            text: error.response?.data || "Error al asignar"
          });
        });
    }

  },

  mounted(){
    this.cargarAsignaciones();
    this.cargarProyectos();
    this.cargarEmpleados();
  }
}
</script>
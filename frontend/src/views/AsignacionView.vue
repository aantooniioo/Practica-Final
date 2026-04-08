<template>
  <v-container class="mt-6">

    <!-- Tarjeta principal -->
    <v-card class="pa-4 formulario-card elevation-4">

      <!-- Título -->
      <v-card-title>
        Asignar empleado a proyecto
      </v-card-title>

      <v-card-text>

        <v-form>

          <v-row>

            <!-- Select de empleados -->
            <v-col cols="12" md="6">
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
            </v-col>

            <!-- Select de proyectos -->
            <v-col cols="12" md="6">
              <v-select
                label="Proyecto"
                :items="proyectos"
                item-title="descripcion"
                item-value="idProyecto"
                v-model="idProyecto"
              />
            </v-col>

            <!-- Fecha -->
            <v-col cols="12" md="6">
              <v-text-field
                label="Fecha"
                type="date"
                v-model="fechaAlta"
              />
            </v-col>

          </v-row>

          <!-- Botón -->
          <v-card-actions class="mt-4">
            <v-btn color="primary" @click="asignar">
              Asignar
            </v-btn>
          </v-card-actions>

        </v-form>

      </v-card-text>

    </v-card>

    <!-- Dialog error -->
    <v-dialog v-model="dialogError" max-width="400">
      <v-card>

        <v-card-title>
          Error
        </v-card-title>

        <v-card-text>
          {{ mensajeError }}
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn color="primary" @click="dialogError = false">
            Aceptar
          </v-btn>
        </v-card-actions>

      </v-card>
    </v-dialog>

    <!-- Dialog de éxito -->
    <v-dialog v-model="dialogOk" max-width="400">
      <v-card>

        <v-card-title>
          Correcto
        </v-card-title>

        <v-card-text>
          Asignación realizada correctamente
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn color="primary" @click="dialogOk = false">
            Aceptar
          </v-btn>
        </v-card-actions>

      </v-card>
    </v-dialog>

  </v-container>
</template>

<script>
import { getEmpleados } from "../services/empleadoService";
import { getProyectos } from "../services/proyectoService";
import { asignarEmpleadoProyecto, getAsignaciones } from "../services/empleadoProyectoService";

export default {
  name: "AsignacionView",

  data(){
    return{
      empleados: [],
      proyectos: [],
      asignaciones: [],

      idEmpleado: null,
      idProyecto: null,
      fechaAlta: null,

      dialogError: false,
      mensajeError: "",
      dialogOk: false
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
        this.mensajeError = "Debes rellenar todos los campos";
        this.dialogError = true;
        return;
      }

      if(this.yaAsignado(this.idEmpleado, this.idProyecto)){
        this.mensajeError = "El empleado ya está asignado a este proyecto";
        this.dialogError = true;
        return;
      }

      const datos = {
        idEmpleado: this.idEmpleado,
        idProyecto: this.idProyecto,
        fechaAlta: this.fechaAlta
      };

      asignarEmpleadoProyecto(datos)
        .then(()=>{

          this.dialogOk = true;

          this.idEmpleado = null;
          this.idProyecto = null;
          this.fechaAlta = null;

          this.cargarAsignaciones();

        })
        .catch(error=>{
          this.mensajeError = error.response?.data || "Error al asignar";
          this.dialogError = true;
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

<style scoped>
.formulario-card {
  max-width: 900px;
  margin: 0 auto;
}
</style>
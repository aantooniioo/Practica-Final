<template>
  <v-container class="mt-6">

    <!-- Tarjeta principal -->
    <v-card class="pa-5 elevation-4">

      <!-- Título -->
      <v-card-title>
        Asignar empleado a proyecto
      </v-card-title>

      <v-card-text>

        <v-form>

          <!-- Select de empleados -->
          <v-select
            label="Empleado"
            :items="empleados"
            item-title="nombreCompleto"
            item-value="idEmpleado"
            v-model="idEmpleado"
          >
            <!-- Personalizar cada opción -->
            <template #item="{ props, item }">

              <v-list-item
                v-bind="props"
                :disabled="yaAsignado(item?.raw?.idEmpleado, idProyecto)"
              >
                <v-list-item-title>

                  {{ item?.raw?.nombreCompleto }}

                  <!-- Mostrar si ya está asignado -->
                  <span v-if="yaAsignado(item?.raw?.idEmpleado, idProyecto)">
                    (ya asignado)
                  </span>

                </v-list-item-title>
              </v-list-item>

            </template>
          </v-select>

          <!-- Select de proyectos -->
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

    <!-- Dialog de error -->
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

    // Cargar empleados activos
    cargarEmpleados(){
      getEmpleados().then(res => {

        // Se construye nombre completo para mostrar en el select
        this.empleados = res.data.map(e => ({
          ...e,
          nombreCompleto: (e.nombre || '') + " " + (e.apellido1 || '')
        }));

      });
    },

    // Cargar proyectos activos
    cargarProyectos(){
      getProyectos().then(res => {
        this.proyectos = res.data;
      });
    },

    // Cargar asignaciones existentes
    cargarAsignaciones(){
      getAsignaciones().then(res => {
        this.asignaciones = res.data;
      });
    },

    // Comprobar si ya existe la relación empleado-proyecto
    yaAsignado(idEmpleado, idProyecto){

      if(!idEmpleado || !idProyecto) return false;

      return this.asignaciones.some(a =>
        a.idEmpleado === idEmpleado && a.idProyecto === idProyecto
      );
    },

    // Ejecutar asignación
    asignar(){

      // Validar campos obligatorios
      if(!this.idEmpleado || !this.idProyecto || !this.fechaAlta){
        this.mensajeError = "Debes rellenar todos los campos";
        this.dialogError = true;
        return;
      }

      // Evitar duplicados
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

          // Limpiar formulario
          this.idEmpleado = null;
          this.idProyecto = null;
          this.fechaAlta = null;

          // Recargar datos
          this.cargarAsignaciones();

        })
        .catch(error=>{
          this.mensajeError = error.response?.data || "Error al asignar";
          this.dialogError = true;
        });
    }

  },

  // Carga inicial de datos
  mounted(){
    this.cargarAsignaciones();
    this.cargarProyectos();
    this.cargarEmpleados();
  }
}
</script>
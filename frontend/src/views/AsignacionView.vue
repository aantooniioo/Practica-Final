<template>
  <v-container class="mt-5">
    <v-card>
      <v-card-title class="text-h5">
        Asignar Empleado a Proyecto
      </v-card-title>

      <v-card-text>

        <v-form @submit.prevent="guardar">

          <!-- EMPLEADO -->
          <v-select
            label="Empleado"
            :items="empleados"
            item-title="nombreCompleto"
            item-value="idEmpleado"
            v-model="form.idEmpleado"
          ></v-select>

          <!-- PROYECTO -->
          <v-select
            label="Proyecto"
            :items="proyectos"
            item-title="descripcion"
            item-value="idProyecto"
            v-model="form.idProyecto"
          ></v-select>

          <!-- FECHA -->
          <v-text-field
            label="Fecha Alta"
            type="date"
            v-model="form.fechaAlta"
          ></v-text-field>

          <v-btn color="success" type="submit" class="mt-3">
            Asignar
          </v-btn>

        </v-form>

      </v-card-text>
    </v-card>

    <!-- SNACKBAR (modal que sustituye alert) -->
    <v-snackbar v-model="snackbar" :color="snackbarColor">
      {{ snackbarText }}
    </v-snackbar>
  </v-container>
</template>

<script>
import { getEmpleados } from "../services/empleadoService";
import { getProyectos } from "../services/proyectoService";
import { asignarEmpleado } from "../services/empleadoProyectoService";

export default {
  data(){
    return{
      empleados: [],
      proyectos: [],
      form: {
        idEmpleado: "",
        idProyecto: "",
        fechaAlta: ""
      },

      snackbar: false,
      snackbarText: "",
      snackbarColor: "success"
    }
  },

  mounted(){
    getEmpleados().then(res => {
      this.empleados = res.data.map(e => ({
        ...e,
        nombreCompleto: e.nombre + " " + e.apellido1
      }));
    });

    getProyectos().then(res => this.proyectos = res.data);
  },

  methods:{
    mostrarMensaje(texto, color="success"){
      this.snackbarText = texto;
      this.snackbarColor = color;
      this.snackbar = true;
    },

    guardar(){

      if(!this.form.idEmpleado || !this.form.idProyecto){
        this.mostrarMensaje("Debes seleccionar empleado y proyecto", "error");
        return;
      }

      asignarEmpleado(this.form)
        .then(()=>{
          this.mostrarMensaje("Asignación realizada correctamente");

          this.form = {
            idEmpleado: "",
            idProyecto: "",
            fechaAlta: ""
          };
        })
        .catch(err=>{
          this.mostrarMensaje(err.response?.data || "Error en la asignación", "error");
        });
    }
  }
}
</script>
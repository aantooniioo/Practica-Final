<template>
  <div class="container mt-4">
    <h2>Asignar Empleado a Proyecto</h2>

    <form @submit.prevent="guardar">

      <!-- EMPLEADO -->
      <div class="mb-3">
        <label>Empleado</label>
        <select v-model="form.idEmpleado" class="form-control">
          <option value="">Selecciona empleado</option>
          <option v-for="e in empleados" :key="e.idEmpleado" :value="e.idEmpleado">
            {{ e.nombre }} {{ e.apellido1 }}
          </option>
        </select>
      </div>

      <!-- PROYECTO -->
      <div class="mb-3">
        <label>Proyecto</label>
        <select v-model="form.idProyecto" class="form-control">
          <option value="">Selecciona proyecto</option>
          <option v-for="p in proyectos" :key="p.idProyecto" :value="p.idProyecto">
            {{ p.descripcion }}
          </option>
        </select>
      </div>

      <!-- FECHA -->
      <div class="mb-3">
        <label>Fecha Alta</label>
        <input type="date" v-model="form.fechaAlta" class="form-control" />
      </div>

      <button class="btn btn-success">Asignar</button>

    </form>
  </div>
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
      }
    }
  },

  mounted(){
    getEmpleados().then(res => this.empleados = res.data);
    getProyectos().then(res => this.proyectos = res.data);
  },

  methods:{
    guardar(){

      if(!this.form.idEmpleado || !this.form.idProyecto){
        alert("Debes seleccionar empleado y proyecto");
        return;
      }

      asignarEmpleado(this.form)
        .then(()=>{
          alert("Asignación realizada correctamente");
          this.form = {
            idEmpleado: "",
            idProyecto: "",
            fechaAlta: ""
          };
        })
        .catch(err=>{
          console.error(err);
          alert("Error en la asignación");
        });
    }
  }
}
</script>
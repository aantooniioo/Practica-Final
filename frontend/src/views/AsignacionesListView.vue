<template>
  <v-container class="mt-6">

    <v-card class="pa-4 formulario-card elevation-4 card-pro card-animated">

      <!-- Título -->
      <v-card-title class="text-h5">
        <h2 class="text-h5">Asignaciones</h2>
      </v-card-title>

      <!-- Buscador -->
      <v-text-field
        v-model="busqueda"
        label="Buscar asignación..."
        prepend-inner-icon="mdi-magnify"
        variant="outlined"
        clearable
        class="mb-4"
      />

      <!-- Tabla -->
      <v-table density="comfortable">

        <thead class="bg-grey-darken-3">
          <tr>
            <th>Empleado</th>
            <th>Proyecto</th>
            <th>Fecha</th>
          </tr>
        </thead>

        <tbody>

          <!-- Sin datos -->
          <tr v-if="asignacionesFiltradas.length === 0">
            <td colspan="3" class="text-center py-6">
              No hay asignaciones
            </td>
          </tr>

          <!-- Lista -->
          <tr v-for="a in asignacionesFiltradas" :key="a.idEmpleado + '-' + a.idProyecto">

            <td>
              {{ nombreEmpleado(a.idEmpleado) }}
            </td>

            <td>
              {{ nombreProyecto(a.idProyecto) }}
            </td>

            <td>
              {{ a.fechaAlta }}
            </td>

          </tr>

        </tbody>

      </v-table>

    </v-card>

  </v-container>
</template>

<script>
import axios from "axios";
import { getEmpleados } from "../services/empleadoService";
import { getProyectos } from "../services/proyectoService";

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

    axios.get("http://localhost:8080/asignaciones")
      .then(res => this.asignaciones = res.data);

    getEmpleados().then(res => this.empleados = res.data);
    getProyectos().then(res => this.proyectos = res.data);
  },

  methods:{

    nombreEmpleado(id){
      const emp = this.empleados.find(e => e.idEmpleado === id);
      return emp ? emp.nombre + " " + emp.apellido1 : id;
    },

    nombreProyecto(id){
      const proy = this.proyectos.find(p => p.idProyecto === id);
      return proy ? proy.descripcion : id;
    }

  }
}
</script>

<style scoped>
.formulario-card {
  max-width: 900px;
  margin: 0 auto;
}

tbody tr:hover {
  background-color: rgba(255, 255, 255, 0.03);
}
</style>
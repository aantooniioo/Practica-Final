<template>
  <v-container>

    <v-card>

      <!-- Título -->
      <v-card-title class="text-h5">
        Asignaciones
      </v-card-title>

      <!-- Tabla -->
      <v-table>

        <thead>
          <tr>
            <th>Empleado</th>
            <th>Proyecto</th>
            <th>Fecha</th>
          </tr>
        </thead>

        <tbody>

          <!-- Sin datos -->
          <tr v-if="asignaciones.length === 0">
            <td colspan="3" class="text-center">
              No hay asignaciones
            </td>
          </tr>

          <!-- Lista -->
          <tr v-for="a in asignaciones" :key="a.idEmpleado + '-' + a.idProyecto">
            <td>{{ nombreEmpleado(a.idEmpleado) }}</td>
            <td>{{ nombreProyecto(a.idProyecto) }}</td>
            <td>{{ a.fechaAlta }}</td>
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
      proyectos: []
    }
  },

  mounted(){

    // Carga asignaciones
    axios.get("http://localhost:8080/asignaciones")
      .then(res => this.asignaciones = res.data);

    // Carga empleados y proyectos
    getEmpleados().then(res => this.empleados = res.data);
    getProyectos().then(res => this.proyectos = res.data);
  },

  methods:{

    // Devuelve nombre completo del empleado
    nombreEmpleado(id){
      const emp = this.empleados.find(e => e.idEmpleado === id);
      return emp ? emp.nombre + " " + emp.apellido1 : id;
    },

    // Devuelve descripción del proyecto
    nombreProyecto(id){
      const proy = this.proyectos.find(p => p.idProyecto === id);
      return proy ? proy.descripcion : id;
    }

  }
}
</script>
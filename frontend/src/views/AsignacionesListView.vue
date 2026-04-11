<template>
  <v-container class="mt-6">

    <!-- Contenedor centrado -->
    <v-row justify="center">
      <v-col cols="12" md="10">

        <!-- Tarjeta principal -->
        <v-card class="pa-4 formulario-card elevation-4 card-pro card-animated">

          <!-- Título -->
          <v-card-title>
            <h2 class="text-h5">{{ $t('asignaciones.titulo') }}</h2>
          </v-card-title>

          <!-- Campo de búsqueda -->
          <v-text-field
            v-model="busqueda"
            :label="$t('asignaciones.buscar')"
            prepend-inner-icon="mdi-magnify"
            variant="outlined"
            clearable
            class="mb-4"
          />

          <!-- Tabla de asignaciones -->
          <v-table density="comfortable">

            <thead class="bg-grey-darken-3">
              <tr>
                <th>{{ $t('asignaciones.empleado') }}</th>
                <th>{{ $t('asignaciones.proyecto') }}</th>
                <th>{{ $t('asignaciones.fecha') }}</th>
              </tr>
            </thead>

            <tbody>

              <!-- Mensaje cuando no hay datos -->
              <tr v-if="asignacionesFiltradas.length === 0">
                <td colspan="3" class="text-center py-6">
                  {{ $t('asignaciones.sin_datos') }}
                </td>
              </tr>

              <!-- Lista de asignaciones -->
              <tr v-for="a in asignacionesFiltradas" :key="a.idEmpleado + '-' + a.idProyecto">

                <td>{{ nombreEmpleado(a.idEmpleado) }}</td>
                <td>{{ nombreProyecto(a.idProyecto) }}</td>
                <td>{{ a.fechaAlta }}</td>

              </tr>

            </tbody>

          </v-table>

        </v-card>

      </v-col>
    </v-row>

  </v-container>
</template>

<script>
import axios from "axios";
import { getEmpleados } from "../services/empleadoService";
import { getProyectos } from "../services/proyectoService";

export default {
  data(){
    return{
      // Lista de asignaciones obtenida del backend
      asignaciones: [],

      // Lista de empleados para mostrar nombres
      empleados: [],

      // Lista de proyectos para mostrar descripciones
      proyectos: [],

      // Texto introducido en el buscador
      busqueda: ""
    }
  },

  computed: {

    // Filtra las asignaciones según el texto de búsqueda
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

    // Carga las asignaciones desde el backend
    axios.get("http://localhost:8080/asignaciones")
      .then(res => this.asignaciones = res.data);

    // Carga los empleados
    getEmpleados().then(res => this.empleados = res.data);

    // Carga los proyectos
    getProyectos().then(res => this.proyectos = res.data);
  },

  methods:{

    // Devuelve el nombre completo del empleado a partir del id
    nombreEmpleado(id){
      const emp = this.empleados.find(e => e.idEmpleado === id);
      return emp ? emp.nombre + " " + emp.apellido1 : id;
    },

    // Devuelve la descripción del proyecto a partir del id
    nombreProyecto(id){
      const proy = this.proyectos.find(p => p.idProyecto === id);
      return proy ? proy.descripcion : id;
    }
  }
}
</script>

<style scoped>
/* Estilo del contenedor */
.formulario-card {
  max-width: 900px;
  margin: 0 auto;
}

/* Efecto hover en filas */
tbody tr:hover {
  background-color: rgba(255, 255, 255, 0.03);
}
</style>
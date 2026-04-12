<template>
  <v-container class="mt-6">

    <v-row justify="center">
      <v-col cols="12" md="10">

        <v-card
          class="pa-4 formulario-card elevation-4 card-pro card-animated"
          data-aos="fade-up"
        >

          <!-- Título -->
          <v-card-title>
            <h2 class="text-h5">{{ $t('asignaciones.titulo') }}</h2>
          </v-card-title>

          <!-- Buscador -->
          <v-text-field
            v-model="busqueda"
            :label="$t('asignaciones.buscar')"
            prepend-inner-icon="mdi-magnify"
            variant="outlined"
            clearable
            class="mb-4"
          />

          <!-- Tabla -->
          <v-table density="comfortable">

            <thead class="bg-grey-darken-3">
              <tr>
                <th>{{ $t('asignaciones.empleado') }}</th>
                <th>{{ $t('asignaciones.proyecto') }}</th>
                <th>{{ $t('asignaciones.fecha') }}</th>
              </tr>
            </thead>

            <tbody>

              <tr v-if="asignacionesFiltradas.length === 0">
                <td colspan="3" class="text-center py-6">
                  {{ $t('asignaciones.sin_datos') }}
                </td>
              </tr>

              <tr
                v-for="a in asignacionesFiltradas"
                :key="a.idEmpleado + '-' + a.idProyecto"
                class="row-hover"
              >
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
import AOS from "aos";
import "aos/dist/aos.css";

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

    AOS.init({
      duration: 800,
      once: true
    });
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

/* Hover filas estilo empleados */
.row-hover {
  transition: all 0.2s ease;
}

.row-hover:hover {
  background-color: rgba(2,111,193,0.1);
  transform: scale(1.01);
}

/* Hover card dashboard */
.v-card:hover {
  transform: translateY(-10px);
  box-shadow:
    0 18px 40px rgba(0,0,0,0.7),
    0 0 15px rgba(2,111,193,0.4);
}

</style>
<template>
  <div class="container mt-4">
    <h2 class="mb-4">Listado de Proyectos</h2>

    <!-- Botón para ir al alta de proyecto -->
    <button 
      class="btn btn-primary mb-3"
      @click="$router.push('/alta-proyecto')">
      Alta proyecto
    </button>

    <table class="table table-bordered table-hover">
      <thead class="table-dark">
        <tr>
          <th>ID</th>
          <th>Descripción</th>
          <th>Fecha Inicio</th>
          <th>Fecha Fin</th>
          <th>Lugar</th>
          <th>Acciones</th>
        </tr>
      </thead>

      <tbody>
        <!-- Si no hay proyectos -->
        <tr v-if="proyectos.length === 0">
          <td colspan="6" class="text-center">No hay proyectos</td>
        </tr>

        <!-- Listado de proyectos -->
        <tr v-for="p in proyectos" :key="p.idProyecto">
          <td>{{ p.idProyecto }}</td>
          <td>{{ p.descripcion }}</td>
          <td>{{ p.fechaInicio }}</td>

          <!-- Si no tiene fecha fin → proyecto en curso -->
          <td>
            <span v-if="p.fechaFin">
              {{ p.fechaFin }}
            </span>
            <span v-else class="badge bg-info">
              En curso
            </span>
          </td>

          <td>{{ p.lugar }}</td>

          <!-- Acciones -->
          <td>
            <button 
              class="btn btn-danger btn-sm"
              @click="baja(p.idProyecto)">
              Dar de baja
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { getProyectos, bajaProyecto } from "../services/proyectoService";

export default {
  name: "ProyectosView",

  data() {
    return {
      // Array donde se guardan los proyectos
      proyectos: []
    };
  },

  methods: {
    // Cargar proyectos desde backend
    cargarProyectos() {
      getProyectos()
        .then(res => {
          this.proyectos = res.data;
        })
        .catch(error => {
          console.error("Error al cargar proyectos:", error);
        });
    },

    // Dar de baja un proyecto (baja lógica)
    baja(id) {
      if (confirm("¿Seguro que quieres dar de baja este proyecto?")) {
        bajaProyecto(id)
          .then(() => {
            // Recargar tabla tras la baja
            this.cargarProyectos();
          })
          .catch(error => {
            console.error("Error al dar de baja:", error);
          });
      }
    }
  },

  // Al montar la vista → cargar datos
  mounted() {
    this.cargarProyectos();
  }
};
</script>
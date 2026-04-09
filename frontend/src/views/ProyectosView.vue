<template>
  <v-container class="mt-6">

    <!-- Tarjeta principal -->
    <v-card class="pa-5 elevation-4 card-pro card-animated">

      <!-- Cabecera -->
      <v-row align="center" justify="space-between" class="mb-4">

        <v-col cols="auto">
          <h2 class="text-h5">Proyectos</h2>
        </v-col>

        <v-col cols="auto">
          <v-btn
            color="#3b82f6"
            elevation="2"
            @click="$router.push('/alta-proyecto')">
            Nuevo proyecto
          </v-btn>
        </v-col>

      </v-row>

      <!-- Buscador -->
      <v-text-field
        v-model="busqueda"
        label="Buscar proyecto..."
        prepend-inner-icon="mdi-magnify"
        variant="outlined"
        clearable
        class="mb-4"
      />

      <!-- Tabla -->
      <v-table density="comfortable">

        <thead class="bg-grey-darken-3">
          <tr>
            <th>ID</th>
            <th>Descripción</th>
            <th>Inicio</th>
            <th>Estado</th>
            <th>Lugar</th>
            <th class="text-center">Acciones</th>
          </tr>
        </thead>

        <tbody>

          <!-- Sin datos -->
          <tr v-if="proyectosFiltrados.length === 0">
            <td colspan="6" class="text-center py-6">
              No hay proyectos registrados
            </td>
          </tr>

          <!-- Lista -->
          <tr v-for="p in proyectosFiltrados" :key="p.idProyecto">

            <td>{{ p.idProyecto }}</td>

            <td>{{ p.descripcion }}</td>

            <td>{{ p.fechaInicio }}</td>

            <td>
              <v-chip
                size="small"
                :color="p.fechaFin ? 'blue' : 'green'">
                {{ p.fechaFin ? 'Finalizado' : 'En curso' }}
              </v-chip>
            </td>

            <td>{{ p.lugar }}</td>

            <!-- Acciones -->
            <td class="text-center">

              <v-btn
                variant="text"
                class="text-blue text-caption"
                @click="$router.push(`/editar-proyecto/${p.idProyecto}`)">
                <v-icon start size="18">mdi-pencil</v-icon>
                Editar
              </v-btn>

              <v-btn
                variant="text"
                class="text-red-lighten-2 text-caption"
                @click="confirmarBaja(p.idProyecto)">
                <v-icon start size="18">mdi-delete</v-icon>
                Baja
              </v-btn>

            </td>

          </tr>

        </tbody>

      </v-table>

    </v-card>

  </v-container>
</template>

<script>
import { getProyectos, bajaProyecto } from "../services/proyectoService";
import Swal from "sweetalert2";

export default {
  data() {
    return {
      proyectos: [],
      busqueda: ""
    };
  },

  computed: {
    proyectosFiltrados() {

      if (!this.busqueda) return this.proyectos;

      const texto = this.busqueda.toLowerCase();

      return this.proyectos.filter(p => {

        const contenido = `
          ${p.descripcion || ""}
          ${p.lugar || ""}
        `.toLowerCase();

        return contenido.includes(texto);
      });
    }
  },

  methods: {

    // Cargar proyectos
    cargarProyectos() {
      getProyectos().then(res => {
        this.proyectos = res.data;
      });
    },

    // SweetAlert confirmación
    confirmarBaja(id) {

      Swal.fire({
        title: "¿Dar de baja?",
        text: "El proyecto dejará de estar activo",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#ef4444",
        cancelButtonText: "Cancelar",
        confirmButtonText: "Sí, dar de baja"
      }).then(result => {

        if (result.isConfirmed) {

          bajaProyecto(id)
            .then(() => {

              Swal.fire({
                icon: "success",
                title: "Proyecto dado de baja",
                timer: 1500,
                showConfirmButton: false
              });

              this.cargarProyectos();
            })
            .catch(error => {

              Swal.fire({
                icon: "error",
                title: "Error",
                text: error.response?.data || "Error inesperado"
              });

            });

        }

      });
    }

  },

  mounted() {
    this.cargarProyectos();
  }
};
</script>
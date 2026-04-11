<template>
  <v-container class="mt-6">

    <!-- Tarjeta principal del listado -->
    <v-card class="pa-5 elevation-4 card-pro card-animated">

      <!-- Cabecera con título y botón -->
      <v-row align="center" justify="space-between" class="mb-4">

        <!-- Título -->
        <v-col cols="auto">
          <h2 class="text-h5">{{ $t("proyectos.titulo") }}</h2>
        </v-col>

        <!-- Botón crear nuevo proyecto -->
        <v-col cols="auto">
          <v-btn
            color="#3b82f6"
            elevation="2"
            @click="$router.push('/alta-proyecto')">
            {{ $t("proyectos.nuevo") }}
          </v-btn>
        </v-col>

      </v-row>

      <!-- Campo de búsqueda -->
      <v-text-field
        v-model="busqueda"
        :label="$t('proyectos.buscar')"
        prepend-inner-icon="mdi-magnify"
        variant="outlined"
        clearable
        class="mb-4"
      />

      <!-- Tabla de proyectos -->
      <v-table density="comfortable">

        <thead class="bg-grey-darken-3">
          <tr>
            <th>ID</th>
            <th>{{ $t("proyectos.descripcion") }}</th>
            <th>{{ $t("proyectos.inicio") }}</th>
            <th>{{ $t("proyectos.estado") }}</th>
            <th>{{ $t("proyectos.lugar") }}</th>
            <th class="text-center">{{ $t("proyectos.acciones") }}</th>
          </tr>
        </thead>

        <tbody>

          <!-- Mensaje cuando no hay datos -->
          <tr v-if="proyectosFiltrados.length === 0">
            <td colspan="6" class="text-center py-6">
              {{ $t("proyectos.sin_datos") }}
            </td>
          </tr>

          <!-- Lista de proyectos -->
          <tr v-for="p in proyectosFiltrados" :key="p.idProyecto">

            <td>{{ p.idProyecto }}</td>

            <td>{{ p.descripcion }}</td>

            <td>{{ p.fechaInicio }}</td>

            <!-- Estado del proyecto -->
            <td>
              <v-chip
                size="small"
                :color="p.fechaFin ? 'blue' : 'green'">
                {{ p.fechaFin ? $t("proyectos.finalizado") : $t("proyectos.en_curso") }}
              </v-chip>
            </td>

            <td>{{ p.lugar }}</td>

            <!-- Acciones -->
            <td class="text-center">

              <!-- Botón editar -->
              <v-btn
                variant="text"
                class="text-blue text-caption"
                @click="$router.push(`/editar-proyecto/${p.idProyecto}`)">
                <v-icon start size="18">mdi-pencil</v-icon>
                {{ $t("proyectos.editar") }}
              </v-btn>

              <!-- Botón baja -->
              <v-btn
                variant="text"
                class="text-red-lighten-2 text-caption"
                @click="confirmarBaja(p.idProyecto)">
                <v-icon start size="18">mdi-delete</v-icon>
                {{ $t("proyectos.baja") }}
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
      // Lista de proyectos obtenida del backend
      proyectos: [],

      // Texto introducido en el buscador
      busqueda: ""
    };
  },

  computed: {

    // Filtra proyectos según el texto de búsqueda
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

    // Carga todos los proyectos desde el backend
    cargarProyectos() {
      getProyectos().then(res => {
        this.proyectos = res.data;
      });
    },

    // Muestra confirmación antes de dar de baja
    confirmarBaja(id) {

      Swal.fire({
        title: this.$t("alertas.confirmar_baja_proyecto"),
        text: this.$t("alertas.proyecto_inactivo"),
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#ef4444",
        cancelButtonText: this.$t("botones.cancelar"),
        confirmButtonText: this.$t("botones.confirmar_baja")
      }).then(result => {

        if (result.isConfirmed) {

          // Llamada al backend para dar de baja el proyecto
          bajaProyecto(id)
            .then(() => {

              // Mensaje de éxito
              Swal.fire({
                icon: "success",
                title: this.$t("alertas.proyecto_baja_ok"),
                timer: 1500,
                showConfirmButton: false
              });

              // Recarga la lista
              this.cargarProyectos();
            })
            .catch(error => {

              // Mensaje de error
              Swal.fire({
                icon: "error",
                title: this.$t("alertas.error"),
                text: error.response?.data || this.$t("alertas.error_generico")
              });

            });

        }

      });
    }

  },

  // Se ejecuta al montar la vista
  mounted() {
    this.cargarProyectos();
  }
};
</script>
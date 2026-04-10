<template>
  <v-container class="mt-6">

    <!-- Tarjeta principal -->
    <v-card class="pa-5 elevation-4 card-pro card-animated">

      <!-- Cabecera -->
      <v-row align="center" justify="space-between" class="mb-4">

        <v-col cols="auto">
          <h2 class="text-h5">{{ $t("proyectos.titulo") }}</h2>
        </v-col>

        <v-col cols="auto">
          <v-btn
            color="#3b82f6"
            elevation="2"
            @click="$router.push('/alta-proyecto')">
            {{ $t("proyectos.nuevo") }}
          </v-btn>
        </v-col>

      </v-row>

      <!-- Buscador -->
      <v-text-field
        v-model="busqueda"
        :label="$t('proyectos.buscar')"
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
            <th>{{ $t("proyectos.descripcion") }}</th>
            <th>{{ $t("proyectos.inicio") }}</th>
            <th>{{ $t("proyectos.estado") }}</th>
            <th>{{ $t("proyectos.lugar") }}</th>
            <th class="text-center">{{ $t("proyectos.acciones") }}</th>
          </tr>
        </thead>

        <tbody>

          <!-- Sin datos -->
          <tr v-if="proyectosFiltrados.length === 0">
            <td colspan="6" class="text-center py-6">
              {{ $t("proyectos.sin_datos") }}
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
                {{ p.fechaFin ? $t("proyectos.finalizado") : $t("proyectos.en_curso") }}
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
                {{ $t("proyectos.editar") }}
              </v-btn>

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

    cargarProyectos() {
      getProyectos().then(res => {
        this.proyectos = res.data;
      });
    },

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

          bajaProyecto(id)
            .then(() => {

              Swal.fire({
                icon: "success",
                title: this.$t("alertas.proyecto_baja_ok"),
                timer: 1500,
                showConfirmButton: false
              });

              this.cargarProyectos();
            })
            .catch(error => {

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

  mounted() {
    this.cargarProyectos();
  }
};
</script>
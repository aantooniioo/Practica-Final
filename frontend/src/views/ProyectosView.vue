<template>
  <v-container class="mt-6">

    <!-- Tarjeta principal del listado -->
    <v-card class="pa-5 elevation-4 card-pro card-animated" data-aos="fade-up">

      <!-- Cabecera -->
      <v-row align="center" justify="space-between" class="mb-4">

        <v-col cols="auto">
          <h2 class="text-h5">{{ $t("proyectos.titulo") }}</h2>
        </v-col>

        <v-col cols="auto">
          <v-btn
            color="#3b82f6"
            elevation="2"
            class="btn-main"
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

      <!-- TABLA -->
      <v-table density="comfortable">

        <thead class="bg-grey-darken-3">
          <tr>
            <th style="width: 60px;">ID</th>
            <th>{{ $t("proyectos.descripcion") }}</th>
            <th style="width: 120px;">{{ $t("proyectos.inicio") }}</th>
            <th style="width: 120px;">{{ $t("proyectos.estado") }}</th>
            <th style="width: 140px;">{{ $t("proyectos.lugar") }}</th>
            <th class="text-center" style="width: 180px;">
              {{ $t("proyectos.acciones") }}
            </th>
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
          <tr
            v-for="p in proyectosFiltrados"
            :key="p.idProyecto"
            class="row-hover"
          >

            <td>{{ p.idProyecto }}</td>
            <td class="descripcion">{{ p.descripcion }}</td>
            <td>{{ p.fechaInicio }}</td>

            <td>
              <v-chip
                size="small"
                :color="p.fechaFin ? 'blue' : 'green'">
                {{ p.fechaFin ? $t("proyectos.finalizado") : $t("proyectos.en_curso") }}
              </v-chip>
            </td>

            <td>{{ p.lugar }}</td>

            <!-- ACCIONES -->
            <td class="text-center d-flex justify-center align-center gap-2">

              <v-btn
                variant="text"
                class="text-blue text-caption btn-action"
                @click="$router.push(`/editar-proyecto/${p.idProyecto}`)">
                <v-icon start size="18">mdi-pencil</v-icon>
                {{ $t("proyectos.editar") }}
              </v-btn>

              <v-btn
                variant="text"
                class="text-red-lighten-2 text-caption btn-action"
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
import AOS from "aos";
import "aos/dist/aos.css";

export default {
  data() {
    return {
      proyectos: [],
      busqueda: ""
    };
  },

  computed: {

    /**
     * Filtrado dinámico de proyectos
     */
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

    /**
     * Carga proyectos desde backend
     */
    cargarProyectos() {
      getProyectos().then(res => {
        this.proyectos = res.data;
      });
    },

    /**
     * Confirmación de baja de proyecto
     */
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

    AOS.init({
      duration: 800,
      once: true
    });
  }
};
</script>

<style scoped>

/* =========================
   FILAS HOVER
========================= */

.row-hover {
  transition: all 0.2s ease;
}

.row-hover:hover {
  background-color: rgba(2,111,193,0.1);
  transform: scale(1.01);
}


.btn-main {
  transition: all 0.25s ease;
}

.btn-main:hover {
  transform: translateY(-3px) scale(1.02);
  box-shadow: 0 8px 20px rgba(2,111,193,0.4);
}

.btn-main:active {
  transform: scale(0.96);
}

/* =========================
   BOTONES ACCIÓN
========================= */

.btn-action {
  transition: all 0.2s ease;
}

.btn-action:hover {
  transform: translateY(-2px);
}

/* =========================
   TABLA SIN SCROLL
========================= */

.v-table {
  width: 100%;
  table-layout: fixed;
}

.v-table th,
.v-table td {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.descripcion {
  white-space: normal;
}

/* ===== ELIMINAR SCROLL DE VUETIFY ===== */
:deep(.v-table__wrapper) {
  overflow-x: hidden !important;
}

/* por si acaso también este */
:deep(.v-table) {
  overflow: hidden !important;
}

/* =========================
   HOVER CARD GLOBAL
========================= */

.v-card {
  transition: all 0.25s ease;
}

.v-card:hover {
  transform: translateY(-10px);

  box-shadow:
    0 18px 40px rgba(0,0,0,0.7),
    0 0 15px rgba(2,111,193,0.4);
}

</style>
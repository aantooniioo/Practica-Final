<template>
  <v-container class="mt-6">

    <v-card class="pa-5 elevation-4 card-pro card-animated" data-aos="fade-up">

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

      <v-text-field
        v-model="busqueda"
        :label="$t('proyectos.buscar')"
        prepend-inner-icon="mdi-magnify"
        variant="outlined"
        clearable
        class="mb-4"
      />

      <v-table density="comfortable">

        <thead class="bg-grey-darken-3">
          <tr>
            <th>{{ $t("proyectos.descripcion") }}</th>
            <th>{{ $t("proyectos.inicio") }}</th>
            <th>{{ $t("proyectos.estado") }}</th>
            <th>{{ $t("proyectos.lugar") }}</th>
            <th class="text-center">{{ $t("proyectos.acciones") }}</th>
          </tr>
        </thead>

        <tbody>

          <tr v-if="proyectosFiltrados.length === 0">
            <td colspan="5" class="text-center py-6">
              {{ $t("proyectos.sin_datos") }}
            </td>
          </tr>

          <tr
            v-for="p in proyectosFiltrados"
            :key="p.idProyecto"
            class="row-hover"
          >


            <td>{{ p.descripcion }}</td>
            <td>{{ p.fechaInicio }}</td>

            <!-- Estado corregido -->
            <td>
              <v-chip
                size="small"
                :color="esFinalizado(p) ? 'blue' : 'green'">
                {{ esFinalizado(p) ? $t("proyectos.finalizado") : $t("proyectos.en_curso") }}
              </v-chip>
            </td>

            <td>{{ p.lugar }}</td>

            <td class="text-center d-flex justify-center gap-2">

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

    // Determina correctamente si el proyecto ha finalizado
    esFinalizado(p){
      if(!p.fechaFin) return false;

      const hoy = new Date();
      const fin = new Date(p.fechaFin);

      return fin < hoy;
    },

    confirmarBaja(id) {

      Swal.fire({
        title: this.$t("alertas.baja_titulo"),
        text: this.$t("proyectos.baja_confirmacion"),
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#ef4444",
        cancelButtonText: this.$t("alertas.cancelar"),
        confirmButtonText: this.$t("alertas.confirmar")
      }).then(result => {

        if (result.isConfirmed) {

          bajaProyecto(id)
            .then(() => {

              Swal.fire({
                icon: "success",
                title: this.$t("alertas.exito"),
                timer: 1500,
                showConfirmButton: false
              });

              this.cargarProyectos();
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
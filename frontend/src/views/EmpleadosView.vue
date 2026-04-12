<template>
  <v-container class="mt-6">

    <!-- Tarjeta principal del listado -->
    <v-card class="pa-5 elevation-4 card-pro card-animated" data-aos="fade-up">

      <!-- Cabecera con título y botón -->
      <v-row align="center" justify="space-between" class="mb-4">

        <v-col cols="auto">
          <h2 class="text-h5">{{ $t('empleados.titulo') }}</h2>
        </v-col>

        <v-col cols="auto">
          <v-btn
            color="#3b82f6"
            elevation="2"
            class="btn-main"
            @click="$router.push('/alta-empleado')">
            {{ $t('empleados.nuevo') }}
          </v-btn>
        </v-col>

      </v-row>

      <!-- Campo de búsqueda -->
      <v-text-field
        v-model="busqueda"
        :label="$t('empleados.buscar')"
        prepend-inner-icon="mdi-magnify"
        variant="outlined"
        clearable
        class="mb-4"
      />

      <!-- Tabla -->
      <v-table density="comfortable">

        <thead class="bg-grey-darken-3">
          <tr>
            <th style="white-space: nowrap;">{{ $t('empleados.id') }}</th>
            <th>{{ $t('empleados.nombre') }}</th>
            <th style="white-space: nowrap;">{{ $t('empleados.apellido1') }}</th>
            <th style="white-space: nowrap;">{{ $t('empleados.apellido2') }}</th>
            <th style="min-width: 220px;">{{ $t('empleados.email') }}</th>
            <th>{{ $t('empleados.telefono1') }}</th>
            <th>{{ $t('empleados.telefono2') }}</th>
            <th>{{ $t('empleados.estado') }}</th>
            <th>{{ $t('empleados.formacion') }}</th>
            <th class="text-center">{{ $t('empleados.acciones') }}</th>
          </tr>
        </thead>

        <tbody>

          <tr v-if="empleadosFiltrados.length === 0">
            <td colspan="10" class="text-center py-6">
              {{ $t('empleados.no_datos') }}
            </td>
          </tr>

          <tr
            v-for="emp in empleadosFiltrados"
            :key="emp.idEmpleado"
            class="row-hover"
          >

            <td>{{ emp.idEmpleado }}</td>
            <td>{{ emp.nombre }}</td>
            <td>{{ emp.apellido1 }}</td>
            <td>{{ emp.apellido2 }}</td>
            <td>{{ emp.email }}</td>
            <td>{{ emp.telefono1 }}</td>
            <td>{{ emp.telefono2 }}</td>

            <td>
              <v-chip
                size="small"
                :color="emp.estadoCivil === 'S' ? 'primary' : 'orange'">
                {{ formatearEstadoCivil(emp.estadoCivil) }}
              </v-chip>
            </td>

            <td>
              <v-chip
                size="small"
                :color="emp.formacionUniversitaria === 'S' ? 'green' : 'grey'">
                {{ formatearFormacion(emp.formacionUniversitaria) }}
              </v-chip>
            </td>

            <!-- ACCIONES -->
            <td class="text-center d-flex justify-center align-center gap-2">

              <v-btn
                variant="text"
                class="text-blue text-caption btn-action"
                @click="$router.push(`/editar-empleado/${emp.idEmpleado}`)">
                <v-icon start size="18">mdi-pencil</v-icon>
                {{ $t('empleados.editar') }}
              </v-btn>

              <v-btn
                variant="text"
                class="text-red-lighten-2 text-caption btn-action"
                @click="confirmarBaja(emp.idEmpleado)">
                <v-icon start size="18">mdi-delete</v-icon>
                {{ $t('empleados.baja') }}
              </v-btn>

            </td>

          </tr>

        </tbody>

      </v-table>

    </v-card>

  </v-container>
</template>

<script>
import { getEmpleados, bajaEmpleado } from "../services/empleadoService";
import Swal from "sweetalert2";
import AOS from "aos";
import "aos/dist/aos.css";

export default {
  data() {
    return {
      empleados: [],
      busqueda: ""
    };
  },

  computed: {
    empleadosFiltrados() {
      if (!this.busqueda) return this.empleados;

      const texto = this.busqueda.toLowerCase();

      return this.empleados.filter(emp => {
        const contenido = `
          ${emp.nombre || ""}
          ${emp.apellido1 || ""}
          ${emp.apellido2 || ""}
          ${emp.email || ""}
          ${emp.telefono1 || ""}
          ${emp.telefono2 || ""}
        `.toLowerCase();

        return contenido.includes(texto);
      });
    }
  },

  methods: {

    cargarEmpleados() {
      getEmpleados().then(res => {
        this.empleados = res.data;
      });
    },

    confirmarBaja(id) {

      Swal.fire({
        title: this.$t('alertas.baja_titulo'),
        text: this.$t('alertas.baja_texto'),
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#ef4444",
        cancelButtonText: this.$t('alertas.cancelar'),
        confirmButtonText: this.$t('alertas.confirmar')
      }).then(result => {

        if (result.isConfirmed) {

          bajaEmpleado(id)
            .then(() => {

              Swal.fire({
                icon: "success",
                title: this.$t('alertas.exito'),
                timer: 1500,
                showConfirmButton: false
              });

              this.cargarEmpleados();
            })
            .catch(error => {

              Swal.fire({
                icon: "error",
                title: this.$t('alertas.error'),
                text: error.response?.data || this.$t('alertas.error_inesperado')
              });

            });

        }

      });
    },

    formatearEstadoCivil(v) {
      if (v === "S") return "Soltero";
      if (v === "C") return "Casado";
      return v;
    },

    formatearFormacion(v) {
      if (v === "S") return "Sí";
      if (v === "N") return "No";
      return v;
    }

  },

  mounted() {
    this.cargarEmpleados();

    AOS.init({
      duration: 800,
      once: true
    });
  }
};
</script>

<style scoped>

.row-hover {
  transition: all 0.2s ease;
}

.row-hover:hover {
  background-color: rgba(2,111,193,0.1);
  transform: scale(1.01);
}

/* =========================
   BOTÓN PRINCIPAL
========================= */

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
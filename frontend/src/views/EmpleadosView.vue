<template>
  <v-container class="mt-6">

    <!-- Tarjeta principal del listado -->
    <v-card class="pa-5 elevation-4 card-pro card-animated">

      <!-- Cabecera con título y botón -->
      <v-row align="center" justify="space-between" class="mb-4">

        <!-- Título -->
        <v-col cols="auto">
          <h2 class="text-h5">{{ $t('empleados.titulo') }}</h2>
        </v-col>

        <!-- Botón crear nuevo empleado -->
        <v-col cols="auto">
          <v-btn
            color="#3b82f6"
            elevation="2"
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

      <!-- Tabla de empleados -->
      <v-table density="comfortable">

        <thead class="bg-grey-darken-3">
          <tr>
            <th>{{ $t('empleados.id') }}</th>
            <th>{{ $t('empleados.nombre') }}</th>
            <th>{{ $t('empleados.apellido1') }}</th>
            <th>{{ $t('empleados.apellido2') }}</th>
            <th>{{ $t('empleados.email') }}</th>
            <th>{{ $t('empleados.telefono1') }}</th>
            <th>{{ $t('empleados.telefono2') }}</th>
            <th>{{ $t('empleados.estado') }}</th>
            <th>{{ $t('empleados.formacion') }}</th>
            <th class="text-center">{{ $t('empleados.acciones') }}</th>
          </tr>
        </thead>

        <tbody>

          <!-- Mensaje cuando no hay datos -->
          <tr v-if="empleadosFiltrados.length === 0">
            <td colspan="10" class="text-center py-6">
              {{ $t('empleados.no_datos') }}
            </td>
          </tr>

          <!-- Lista de empleados -->
          <tr v-for="emp in empleadosFiltrados" :key="emp.idEmpleado">

            <td>{{ emp.idEmpleado }}</td>
            <td>{{ emp.nombre }}</td>
            <td>{{ emp.apellido1 }}</td>
            <td>{{ emp.apellido2 }}</td>
            <td>{{ emp.email }}</td>
            <td>{{ emp.telefono1 }}</td>
            <td>{{ emp.telefono2 }}</td>

            <!-- Estado civil formateado -->
            <td>
              <v-chip
                size="small"
                :color="emp.estadoCivil === 'S' ? 'primary' : 'orange'">
                {{ formatearEstadoCivil(emp.estadoCivil) }}
              </v-chip>
            </td>

            <!-- Formación universitaria -->
            <td>
              <v-chip
                size="small"
                :color="emp.formacionUniversitaria === 'S' ? 'green' : 'grey'">
                {{ formatearFormacion(emp.formacionUniversitaria) }}
              </v-chip>
            </td>

            <!-- Acciones -->
            <td class="text-center">

              <!-- Botón editar -->
              <v-btn
                variant="text"
                class="text-blue text-caption"
                @click="$router.push(`/editar-empleado/${emp.idEmpleado}`)">
                <v-icon start size="18">mdi-pencil</v-icon>
                {{ $t('empleados.editar') }}
              </v-btn>

              <!-- Botón baja -->
              <v-btn
                variant="text"
                class="text-red-lighten-2 text-caption"
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

export default {
  data() {
    return {
      // Lista de empleados obtenida del backend
      empleados: [],

      // Texto introducido en el buscador
      busqueda: ""
    };
  },

  computed: {

    // Filtra empleados en base al texto de búsqueda
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

    // Carga todos los empleados desde el backend
    cargarEmpleados() {
      getEmpleados().then(res => {
        this.empleados = res.data;
      });
    },

    // Muestra confirmación antes de dar de baja
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

          // Llama al backend para dar de baja
          bajaEmpleado(id)
            .then(() => {

              // Mensaje de éxito
              Swal.fire({
                icon: "success",
                title: this.$t('alertas.exito'),
                timer: 1500,
                showConfirmButton: false
              });

              // Recarga la lista
              this.cargarEmpleados();
            })
            .catch(error => {

              // Mensaje de error
              Swal.fire({
                icon: "error",
                title: this.$t('alertas.error'),
                text: error.response?.data || this.$t('alertas.error_inesperado')
              });

            });

        }

      });
    },

    // Convierte el código de estado civil en texto
    formatearEstadoCivil(v) {
      if (v === "S") return "Soltero";
      if (v === "C") return "Casado";
      return v;
    },

    // Convierte el código de formación en texto
    formatearFormacion(v) {
      if (v === "S") return "Sí";
      if (v === "N") return "No";
      return v;
    }

  },

  // Se ejecuta al montar la vista
  mounted() {
    this.cargarEmpleados();
  }
};
</script>
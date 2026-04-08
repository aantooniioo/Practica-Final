<template>
  <v-container class="mt-6">

    <!-- Tarjeta principal -->
    <v-card class="pa-5 elevation-4">

      <!-- Cabecera -->
      <v-row align="center" justify="space-between" class="mb-4">

        <v-col cols="auto">
          <h2 class="text-h5">Empleados</h2>
        </v-col>

        <v-col cols="auto">
          <v-btn
            color="#3b82f6"
            elevation="2"
            @click="$router.push('/alta-empleado')">
            Nuevo empleado
          </v-btn>
        </v-col>

      </v-row>

      <!-- Tabla -->
      <v-table density="comfortable">

        <thead class="bg-grey-darken-3">
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Apellido 1</th>
            <th>Apellido 2</th>
            <th>Correo electrónico</th>
            <th>Teléfono 1</th>
            <th>Telefono 2</th>
            <th>Estado</th>
            <th>Formación</th>
            <th class="text-center">Acciones</th>
          </tr>
        </thead>

        <tbody>

          <!-- Sin datos -->
          <tr v-if="empleados.length === 0">
            <td colspan="7" class="text-center py-6">
              No hay empleados registrados
            </td>
          </tr>

          <!-- Lista -->
          <tr v-for="emp in empleados" :key="emp.idEmpleado">

            <td class="text-grey">{{ emp.idEmpleado }}</td>

            <td>{{ emp.nombre }}</td>

            <td>{{ emp.apellido1 }}</td>

            <td>{{ emp.apellido2 }}</td>

            <td>{{ emp.email }}</td>

            <td>{{ emp.telefono1 }}</td>

            <td>{{ emp.telefono2 }}</td>

            <!-- Estado -->
            <td>
              <v-chip
                size="small"
                :color="emp.estadoCivil === 'S' ? 'primary' : 'orange'">
                {{ formatearEstadoCivil(emp.estadoCivil) }}
              </v-chip>
            </td>

            <!-- Formación -->
            <td>
              <v-chip
                size="small"
                :color="emp.formacionUniversitaria === 'S' ? 'green' : 'grey'">
                {{ formatearFormacion(emp.formacionUniversitaria) }}
              </v-chip>
            </td>

            <!-- Acción -->
            <td class="text-center">
              <v-btn
                variant="text"
                class="text-red-lighten-2 text-caption"
                @click="abrirDialog(emp.idEmpleado)">
                <v-icon start size="18">mdi-delete</v-icon>
                Baja
              </v-btn>
            </td>

          </tr>

        </tbody>

      </v-table>

    </v-card>

    <!-- Dialog confirmación -->
    <v-dialog v-model="dialog" max-width="400">
      <v-card>
        <v-card-title>Confirmación</v-card-title>

        <v-card-text>
          ¿Seguro que quieres dar de baja este empleado?
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn @click="dialog = false">
            Cancelar
          </v-btn>

          <v-btn color="red" @click="bajaConfirmada">
            Aceptar
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Dialog error -->
    <v-dialog v-model="errorDialog" max-width="400">
      <v-card>
        <v-card-title>Error</v-card-title>

        <v-card-text>
          {{ errorMensaje }}
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn color="#3b82f6" @click="errorDialog = false">
            Aceptar
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-container>
</template>

<script>
import { getEmpleados, bajaEmpleado } from "../services/empleadoService";

export default {
  data() {
    return {
      empleados: [],
      dialog: false,
      idSeleccionado: null,
      errorDialog: false,
      errorMensaje: ""
    };
  },

  methods: {

    // Cargar empleados activos
    cargarEmpleados() {
      getEmpleados()
        .then(res => {
          this.empleados = res.data;
        });
    },

    // Abrir confirmación
    abrirDialog(id) {
      this.idSeleccionado = id;
      this.dialog = true;
    },

    // Ejecutar baja
    bajaConfirmada() {
      bajaEmpleado(this.idSeleccionado)
        .then(() => {
          this.dialog = false;
          this.cargarEmpleados();
        })
        .catch(error => {
          this.dialog = false;
          this.errorMensaje = error.response?.data || "Error";
          this.errorDialog = true;
        });
    },

    // Formatear estado civil
    formatearEstadoCivil(v) {
      if (v === "S") return "Soltero";
      if (v === "C") return "Casado";
      return v;
    },

    // Formatear formación
    formatearFormacion(v) {
      if (v === "S") return "Sí";
      if (v === "N") return "No";
      return v;
    }

  },

  mounted() {
    this.cargarEmpleados();
  }
};
</script>
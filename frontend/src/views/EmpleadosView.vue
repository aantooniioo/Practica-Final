<template>
  <v-container>

    <v-card>

      <!-- Título -->
      <v-card-title class="text-h5">
        Listado de Empleados
      </v-card-title>

      <!-- Botón -->
      <v-card-actions>
        <v-btn color="primary" @click="$router.push('/alta-empleado')">
          Alta empleado
        </v-btn>
      </v-card-actions>

      <!-- Tabla -->
      <v-table density="comfortable">

        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Apellido 1</th>
            <th>Apellido 2</th>
            <th>Email</th>
            <th>Teléfono 1</th>
            <th>Teléfono 2</th>
            <th>Estado Civil</th>
            <th>Formación</th>
            <th>Acciones</th>
          </tr>
        </thead>

        <tbody>

          <!-- Mostrar mensaje si no hay empleados -->
          <tr v-if="empleados.length === 0">
            <td colspan="10" class="text-center">
              No hay empleados
            </td>
          </tr>

          <!-- Mostrar lista de empleados -->
          <tr v-for="emp in empleados" :key="emp.idEmpleado">
            <td>{{ emp.idEmpleado }}</td>
            <td>{{ emp.nombre }}</td>
            <td>{{ emp.apellido1 }}</td>
            <td>{{ emp.apellido2 }}</td>
            <td>{{ emp.email }}</td>
            <td>{{ emp.telefono1 }}</td>
            <td>{{ emp.telefono2 }}</td>

            <!-- Mostrar estado civil -->
            <td>
              <v-chip
                :color="emp.estadoCivil === 'S' ? 'primary' : 'orange'"
                size="small">
                {{ formatearEstadoCivil(emp.estadoCivil) }}
              </v-chip>
            </td>

            <!-- Mostrar formación -->
            <td>
              <v-chip
                :color="emp.formacionUniversitaria === 'S' ? 'green' : 'grey'"
                size="small">
                {{ formatearFormacion(emp.formacionUniversitaria) }}
              </v-chip>
            </td>

            <!-- Acción de baja -->
            <td>
              <v-btn
                color="red"
                size="small"
                @click="abrirDialog(emp.idEmpleado)">
                Dar de baja
              </v-btn>
            </td>
          </tr>

        </tbody>

      </v-table>

    </v-card>

    <!-- Dialog de confirmación -->
    <v-dialog v-model="dialog" max-width="400">
      <v-card>

        <v-card-title>
          Confirmación
        </v-card-title>

        <v-card-text>
          ¿Seguro que quieres dar de baja este empleado?
        </v-card-text>

        <v-card-actions>
          <v-btn color="grey" @click="dialog = false">
            Cancelar
          </v-btn>

          <v-btn color="red" @click="bajaConfirmada">
            Aceptar
          </v-btn>
        </v-card-actions>

      </v-card>
    </v-dialog>

    <!-- Dialog de error -->
    <v-dialog v-model="errorDialog" max-width="400">
      <v-card>

        <v-card-title>
          Error
        </v-card-title>

        <v-card-text>
          {{ errorMensaje }}
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" @click="errorDialog = false">
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
  name: "EmpleadosView",

  data() {
    return {
      empleados: [],
      dialog: false,
      idSeleccionado: null,

      // Controlar errores mostrados al usuario
      errorDialog: false,
      errorMensaje: ""
    };
  },

  methods: {

    // Cargar empleados desde el backend
    cargarEmpleados() {
      getEmpleados()
        .then(res => {
          this.empleados = res.data;
        })
        .catch(error => {
          console.error("Error al cargar empleados:", error);
        });
    },

    // Abrir dialog y guardar el id seleccionado
    abrirDialog(id) {
      this.idSeleccionado = id;
      this.dialog = true;
    },

    // Ejecutar la baja del empleado
    bajaConfirmada() {
      bajaEmpleado(this.idSeleccionado)
        .then(() => {
          this.dialog = false;
          this.cargarEmpleados();
        })
        .catch(error => {
          this.dialog = false;

          this.errorMensaje = error.response?.data || "Error al dar de baja";
          this.errorDialog = true;
        });
    },

    // Convertir estado civil a texto
    formatearEstadoCivil(valor) {
      if (valor === "S") return "Soltero";
      if (valor === "C") return "Casado";
      return valor;
    },

    // Convertir formación a texto
    formatearFormacion(valor) {
      if (valor === "S") return "Sí";
      if (valor === "N") return "No";
      return valor;
    }
  },

  // Ejecutar carga inicial
  mounted() {
    this.cargarEmpleados();
  }
};
</script>
<template>
  <v-container class="mt-6">

    <!-- Tarjeta principal -->
    <v-card class="pa-5 elevation-4">

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
          <tr v-if="proyectos.length === 0">
            <td colspan="6" class="text-center py-6">
              No hay proyectos registrados
            </td>
          </tr>

          <!-- Lista -->
          <tr v-for="p in proyectos" :key="p.idProyecto">

            <td class="text-grey">{{ p.idProyecto }}</td>

            <td>
              <div>{{ p.descripcion }}</div>
            </td>

            <td>{{ p.fechaInicio }}</td>

            <!-- Estado del proyecto -->
            <td>
              <v-chip
                size="small"
                :color="p.fechaFin ? 'blue' : 'green'">
                {{ p.fechaFin ? 'Finalizado' : 'En curso' }}
              </v-chip>
            </td>

            <td>{{ p.lugar }}</td>

            <!-- Acción -->
            <td class="text-center">
              <v-btn
                variant="text"
                class="text-red-lighten-2 text-caption"
                @click="abrirDialog(emp.idProyecto)">
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

        <v-card-title>
          Confirmación
        </v-card-title>

        <v-card-text>
          ¿Seguro que quieres dar de baja este proyecto?
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
import { getProyectos, bajaProyecto } from "../services/proyectoService";

export default {
  data() {
    return {
      proyectos: [],
      dialog: false,
      idSeleccionado: null,
      errorDialog: false,
      errorMensaje: ""
    };
  },

  methods: {

    // Cargar proyectos activos
    cargarProyectos() {
      getProyectos()
        .then(res => {
          this.proyectos = res.data;
        });
    },

    // Abrir confirmación
    abrirDialog(id) {
      this.idSeleccionado = id;
      this.dialog = true;
    },

    // Ejecutar baja
    bajaConfirmada() {
      bajaProyecto(this.idSeleccionado)
        .then(() => {
          this.dialog = false;
          this.cargarProyectos();
        })
        .catch(error => {
          this.dialog = false;
          this.errorMensaje = error.response?.data || "Error";
          this.errorDialog = true;
        });
    }

  },

  mounted() {
    this.cargarProyectos();
  }
};
</script>
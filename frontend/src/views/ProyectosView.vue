<template>
  <v-container>

    <v-card>

      <!-- Título -->
      <v-card-title class="text-h5">
        Listado de Proyectos
      </v-card-title>

      <!-- Botón -->
      <v-card-actions>
        <v-btn color="primary" @click="$router.push('/alta-proyecto')">
          Alta proyecto
        </v-btn>
      </v-card-actions>

      <!-- Tabla -->
      <v-table density="comfortable">

        <thead>
          <tr>
            <th>ID</th>
            <th>Descripción</th>
            <th>Fecha Inicio</th>
            <th>Fecha Fin</th>
            <th>Lugar</th>
            <th>Acciones</th>
          </tr>
        </thead>

        <tbody>

          <!-- Mostrar mensaje si no hay proyectos -->
          <tr v-if="proyectos.length === 0">
            <td colspan="6" class="text-center">
              No hay proyectos
            </td>
          </tr>

          <!-- Mostrar lista -->
          <tr v-for="p in proyectos" :key="p.idProyecto">
            <td>{{ p.idProyecto }}</td>
            <td>{{ p.descripcion }}</td>
            <td>{{ p.fechaInicio }}</td>

            <!-- Mostrar estado del proyecto -->
            <td>
              <v-chip v-if="p.fechaFin" color="blue" size="small">
                {{ p.fechaFin }}
              </v-chip>

              <v-chip v-else color="green" size="small">
                En curso
              </v-chip>
            </td>

            <td>{{ p.lugar }}</td>

            <!-- Acción de baja -->
            <td>
              <v-btn
                color="red"
                size="small"
                @click="abrirDialog(p.idProyecto)">
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
          ¿Seguro que quieres dar de baja este proyecto?
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
import { getProyectos, bajaProyecto } from "../services/proyectoService";

export default {
  name: "ProyectosView",

  data() {
    return {
      proyectos: [],
      dialog: false,
      idSeleccionado: null,

      // Controlar errores mostrados al usuario
      errorDialog: false,
      errorMensaje: ""
    };
  },

  methods: {

    // Cargar proyectos desde backend
    cargarProyectos() {
      getProyectos()
        .then(res => {
          this.proyectos = res.data;
        })
        .catch(error => {
          console.error("Error al cargar proyectos:", error);
        });
    },

    // Abrir dialog y guardar el id seleccionado
    abrirDialog(id) {
      this.idSeleccionado = id;
      this.dialog = true;
    },

    // Ejecutar la baja del proyecto
    bajaConfirmada() {
      bajaProyecto(this.idSeleccionado)
        .then(() => {
          this.dialog = false;
          this.cargarProyectos();
        })
        .catch(error => {
          this.dialog = false;

          this.errorMensaje = error.response?.data || "Error al dar de baja";
          this.errorDialog = true;
        });
    }

  },

  // Ejecutar carga inicial
  mounted() {
    this.cargarProyectos();
  }
};
</script>
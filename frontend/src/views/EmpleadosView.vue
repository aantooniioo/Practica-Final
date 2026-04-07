<template>
    <div class="container mt-4">
        <h2 class="mb-4">Listado de Empleados</h2>

        <!-- Botón alta -->
        <button 
            class="btn btn-primary mb-3"
            @click="$router.push('/alta-empleado')">
            Alta empleado
        </button>

        <table class="table table-bordered table-hover">
            <thead class="table-dark">
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
                <!-- Si no hay empleados -->
                <tr v-if="empleados.length === 0">
                    <td colspan="10" class="text-center">No hay empleados</td>
                </tr>

                <!-- Lista -->
                <tr v-for="emp in empleados" :key="emp.idEmpleado">
                    <td>{{ emp.idEmpleado }}</td>
                    <td>{{ emp.nombre }}</td>
                    <td>{{ emp.apellido1 }}</td>
                    <td>{{ emp.apellido2 }}</td>
                    <td>{{ emp.email }}</td>
                    <td>{{ emp.telefono1 }}</td>
                    <td>{{ emp.telefono2 }}</td>

                    <!-- Estado civil con Badge -->
                     <td>
                        <span
                            :class="emp.estadoCivil === 'S' ? 'badge bg-primary' : 'badge bg-warning text-dark'">
                            {{ formatearEstadoCivil(emp.estadoCivil) }}
                        </span>
                     </td>

                     <!-- Formación con Badge -->
                     <td>
                        <span
                            :class="emp.formacionUniversitaria === 'S' ? 'badge bg-success' : 'badge bg-secondary'">
                            {{ formatearFormacion(emp.formacionUniversitaria) }}
                        </span>
                     </td>

                    <td>
                        <button 
                            class="btn btn-danger btn-sm"
                            @click="baja(emp.idEmpleado)">
                            Dar de baja
                        </button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import { getEmpleados, bajaEmpleado } from "../services/empleadoService";

export default {
    name: "EmpleadosView",

    data() {
        return {
            empleados: []
        };
    },

    methods: {
        cargarEmpleados() {
            getEmpleados()
                .then(res => {
                    console.log("DATOS BACK:", res.data);
                    this.empleados = res.data;
                })
                .catch(error => {
                    console.error("Error al cargar empleados:", error);
                });
        },

        baja(id) {
            if (confirm("¿Seguro que quieres dar de baja este empleado?")) {
                bajaEmpleado(id)
                    .then(() => {
                        this.cargarEmpleados();
                    })
                    .catch(error => {
                        console.error("Error al dar de baja:", error);
                    });
            }
        },

        formatearEstadoCivil(valor){
            if(valor === "S") return "Soltero";
            if(valor === "C") return "Casado";
            return valor;
        },

        formatearFormacion(valor){
            if(valor === "S") return "Sí";
            if(valor === "N") return "No";
            return valor;
        }
    },

    mounted() {
        console.log("Cargando empleados...");
        this.cargarEmpleados();
    }
};
</script>
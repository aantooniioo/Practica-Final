<template>
    <div class="container mt-4">
        <h2>Alta de Empleado</h2>

        <form @submit.prevent="guardar">

            <div class="mb-3">
                <label>Nombre</label>
                <input v-model="empleado.nombre" class="form-control" required />
            </div>

            <div class="mb-3">
                <label>Apellido 1</label>
                <input v-model="empleado.apellido1" class="form-control" required />
            </div>

            <div class="mb-3">
                <label>Apellido 2</label>
                <input v-model="empleado.apellido2" class="form-control" required />
            </div>

            <div class="mb-3">
                <label>Email</label>
                <input v-model="empleado.email" class="form-control" required />
            </div>

            <div class="mb-3">
                <label>Teléfono 1</label>
                <input v-model="empleado.telefono1" class="form-control" required />
            </div>

            <div class="mb-3">
                <label>Teléfono 2</label>
                <input v-model="empleado.telefono2" class="form-control" required />
            </div>

            <!-- ESTADO CIVIL -->
            <div class="mb-3">
                <label>Estado Civil</label>
                <select v-model="empleado.estadoCivil" class="form-control">
                    <option value="S">Soltero</option>
                    <option value="C">Casado</option>
                </select>
            </div>

            <!-- FORMACIÓN -->
            <div class="mb-3">
                <label>Formación Universitaria</label>
                <select v-model="empleado.formacionUniversitaria" class="form-control">
                    <option value="S">Sí</option>
                    <option value="N">No</option>
                </select>
            </div>

            <button class="btn btn-success">Guardar</button>

            <button 
                type="button" 
                class="btn btn-secondary ms-2"
                @click="$router.push('/empleados')">
                Cancelar
            </button>

        </form>
    </div>
</template>

<script>
import { crearEmpleado } from "../services/empleadoService";

export default {
    name: "AltaEmpleadoView",

    data(){
        return{
            empleado:{
                nombre:"",
                apellido1:"",
                apellido2:"",
                email:"",
                telefono1:"",
                telefono2:"",
                fechaAlta: new Date().toISOString().split("T")[0],
                fechaBaja: null,
                estadoCivil: "S",
                formacionUniversitaria: "N"
            }
        }
    },

    methods:{
        guardar(){

            // VALIDACIÓN
            if(
                !this.empleado.nombre ||
                !this.empleado.apellido1 ||
                !this.empleado.apellido2 ||
                !this.empleado.email ||
                !this.empleado.telefono1 ||
                !this.empleado.telefono2
            ){
                alert("Es obligatorio introducir todos los datos para dar de alta un nuevo empleado");
                return;
            }

            crearEmpleado(this.empleado)
                .then(() => {
                    alert("Empleado creado correctamente");
                    this.$router.push('/empleados');
                })
                .catch(error => {
                    console.error(error);
                    alert("Error al crear empleado");
                });
        }
    }
}
</script>
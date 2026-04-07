<template>
    <div class="container mt-4">
        <h2>Listado de Proyectos</h2>

        <table class="table table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Descripción</th>
                    <th>Fecha Inicio</th>
                    <th>Fecha Fin</th>
                    <th>Lugar</th>
                </tr>
            </thead>

            <tbody>
                <tr v-for="p in proyectos" :key="p.idProyecto">
                    <td>{{ p.idProyecto }}</td>
                    <td>{{ p.descripcion }}</td>
                    <td>{{ p.fechaInicio }}</td>
                    <td>
                        <span v-if="p.fechaFin">
                            {{ p.fechaFin }}
                        </span>
                        <span v-else class="badge bg-info">
                            En curso
                        </span>
                    </td>
                    <td>{{ p.lugar }}</td>

                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import { getProyectos } from "../services/proyectoService";

export default {
    data(){
        return{
            proyectos: []
        }
    },
    mounted(){
        getProyectos().then(res=>{
            this.proyectos = res.data
        })
    }
}
</script>
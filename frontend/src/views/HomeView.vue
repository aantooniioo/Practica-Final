<template>
    <div class="container mt-4">
    <h2>Panel principal</h2>

    <div class="d-flex gap-2 mt-3">

        <button 
        class="btn btn-primary"
        @click="$router.push('/empleados')">
        Empleados
        </button>

        <button 
        class="btn btn-secondary"
        @click="$router.push('/proyectos')">
        Proyectos
        </button>

        <button 
        class="btn btn-success"
        @click="$router.push('/asignacion')">
        Asignar empleados
        </button>

    </div>
</div>
</template>

<script>
export default {
    name: 'HomeView'
};
</script>
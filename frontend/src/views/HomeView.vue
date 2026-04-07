<template>
  <div class="container mt-4">
    <h2 class="mb-4">Panel principal</h2>

    <div class="d-flex flex-wrap gap-3">

      <!-- Empleados -->
      <button 
        class="btn btn-primary"
        @click="$router.push('/empleados')">
        Gestión de empleados
      </button>

      <!-- Proyectos -->
      <button 
        class="btn btn-secondary"
        @click="$router.push('/proyectos')">
        Gestión de proyectos
      </button>

      <!-- Asignar -->
      <button 
        class="btn btn-success"
        @click="$router.push('/asignacion')">
        Asignar empleados a proyectos
      </button>

      <!-- Ver asignaciones -->
      <button 
        class="btn btn-dark"
        @click="$router.push('/asignaciones')">
        Ver asignaciones
      </button>

    </div>
  </div>
</template>

<script>
export default {
  name: "HomeView"
}
</script>
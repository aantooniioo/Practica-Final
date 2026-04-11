// Importa Vue Router
import { createRouter, createWebHistory } from 'vue-router'

// Importa las vistas
import HomeView from '@/views/HomeView.vue'
import EmpleadosView from '@/views/EmpleadosView.vue'
import EditarEmpleadoView from '@/views/EditarEmpleadoView.vue'
import AltaEmpleadoView from '@/views/AltaEmpleadoView.vue'
import ProyectosView from '@/views/ProyectosView.vue'
import EditarProyectoView from '@/views/EditarProyectoView.vue'
import AltaProyectoView from '@/views/AltaProyectoView.vue'
import AsignacionView from '@/views/AsignacionView.vue'
import AsignacionesListView from '@/views/AsignacionesListView.vue'
import EstadisticasView from '@/views/EstadisticasView.vue'

// Define las rutas de la aplicación
const routes = [

  // Redirección inicial
  {
    path: '/',
    redirect: '/empleados'
  },

  // Empleados
  {
    path: '/empleados',
    name: 'empleados',
    component: EmpleadosView
  },

  // Alta de empleados
  {
    path: '/alta-empleado',
    name: 'altaEmpleado',
    component: AltaEmpleadoView
  },

  // Edición de empleados

  {
    path: '/editar-empleado/:idEmpleado',
    name: 'editarEmpleado',
    component: EditarEmpleadoView
  },

  // Proyectos
  {
    path: '/proyectos',
    name: 'proyectos',
    component: ProyectosView
  },

  // Alta de proyectos
  {
    path: '/alta-proyecto',
    name: 'altaProyecto',
    component: AltaProyectoView
  },

  // Edición de proyectos
  {
    path: '/editar-proyecto/:idProyecto',
    name: 'editarProyecto',
    component: EditarProyectoView
  },

  // Asignaciones
  {
    path: '/asignacion',
    name: 'asignacion',
    component: AsignacionView
  },

  {
    path: '/asignaciones',
    name: 'asignaciones',
    component: AsignacionesListView
  },

  // Estadísticas
  {
    path: '/estadisticas',
    name: 'estadisticas',
    component: EstadisticasView
  }

]

// Crea el router
const router = createRouter({
  history: createWebHistory(),
  routes
})

// Exporta el router
export default router
import { createRouter, createWebHistory } from 'vue-router'

// Vistas
import EmpleadosView from '../views/EmpleadosView.vue'
import AltaEmpleadoView from '../views/AltaEmpleadoView.vue'
import ProyectosView from '@/views/ProyectosView.vue'

const routes = [
  {
    path: '/',
    redirect: '/empleados'
  },
  {
    path: '/empleados',
    name: 'empleados',
    component: EmpleadosView
  },
  {
    path: '/alta-empleado',
    name: 'altaEmpleado',
    component: AltaEmpleadoView
  },
  {
    path: '/proyectos',
    component: ProyectosView
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
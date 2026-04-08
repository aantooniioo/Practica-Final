import { createRouter, createWebHistory } from 'vue-router'

// Vistas
import HomeView from '@/views/HomeView.vue'
import EmpleadosView from '../views/EmpleadosView.vue'
import EditarEmpleadoView from '@/views/EditarEmpleadoView.vue'
import AltaEmpleadoView from '../views/AltaEmpleadoView.vue'
import ProyectosView from '@/views/ProyectosView.vue'
import AltaProyectoView from '../views/AltaProyectoView.vue'
import AsignacionView from '@/views/AsignacionView.vue'
import AsignacionesListView from '@/views/AsignacionesListView.vue'
import FooterComponent from '@/components/FooterComponent.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
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
    path: '/editar-empleado/:idEmpleado',
    name: 'editarEmpleado',
    component: EditarEmpleadoView
  },
  {
    path: '/alta-empleado',
    name: 'altaEmpleado',
    component: AltaEmpleadoView
  },
  {
    path: '/proyectos',
    component: ProyectosView
  },
  {
    path: '/editar-proyecto/:idProyecto',
    name: 'editarProyecto',
    component: AltaProyectoView
  },
  {
    path: '/alta-proyecto',
    component: AltaProyectoView
  },
  {
    path: '/asignacion',
    component: AsignacionView
  },
  {
    path: '/asignaciones',
    component: AsignacionesListView
  },
  {
    path: '/footer',
    component: FooterComponent
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
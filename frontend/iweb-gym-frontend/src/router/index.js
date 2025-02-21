import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/politica-privacidad',
      name: 'politica-privacidad',
      component: () => import('../views/Politica-privacidad.vue'),
    },
    {
      path: '/socio-profile',
      name: 'socio-profile',
      component: () => import('../views/MiPerfil-socio.vue'),
    },
    {
      path: '/login',
      name: 'login',

      component: () => import('../views/formLogin.vue'),
    },
    {
      path: '/mis-reservas',
      name: 'mis-reservas',
      component: () => import('../views/MisReservas-socio.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/monitor-profile',
      name: 'monitor_profile',
      component: () => import('../views/MiPerfil-monitor.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/webmaster-profile',
      name: 'webmaster-profile',
      component: () => import('../views/MiPerfil-webmaster.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/all-actividades',
      name: 'all-actividades',
      component: () => import('../views/CalendarioAllActividades.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/modificar-cuota',
      name: 'ModificarCuota',
      component: () => import('@/views/ModificarCuota.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/solicitud-socio',
      name: 'SolicitudSocio',
      component: () => import('@/views/SolicitudSocio.vue')
    },
    {
      path: '/monitor/mis-clases',
      name: 'mis-clases',
      component: () => import('../views/MisClases-monitor.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/ver-informes',
      name: 'ver-informes',
      component: () => import('../views/VerInformes.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/tienda',
      name: 'tienda',
      component: () => import('../views/OnlineStore.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: "/ver-detalles",
      name: "VerDetalles",
      component: () => import("@/views/ConfirmarReserva.vue"),
    },
    {
      path: '/ver-listado-morosos',
      name: 'listado-morosos',
      component: () => import('../views/ListadoUsuariosSinPagar.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/ver-nuevas-peticiones',
      name: 'listado-peticiones',
      component: () => import('../views/ListadoSolicitudesSocio.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/activar-desactivar-perfiles',
      name: 'listado-socios',
      component: () => import('../views/GestionUusarios.vue'),
      meta: { requiresAuth: true }
    },

    {
      path: '/crear-actividad',
      name: 'crear-actividad',
      component: () => import('../views/FormularioActividad.vue'),
      meta: { requiresAuth: true }
    },
    {
      path: '/cuotas-mensuales',
      name: 'cuotas-mensuales',
      component: () => import('../views/CuotasMensuales.vue'),
      meta: { requiresAuth: true }
    }


  ],
})

export default router

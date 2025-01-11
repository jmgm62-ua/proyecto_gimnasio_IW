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
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
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
    
    
  ],
})

export default router

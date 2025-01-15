<template>
    <div class="container my-4">
      <div class="row">
        <div class="col-md-6 offset-md-3">
          <div class="card">
            <div class="card-header">
              <h3>Perfil del Web Master</h3>
            </div>
            <div class="card-body">
              <div v-if="loading" class="text-center">
                <div class="spinner-border" role="status">
                  <span class="sr-only">Cargando...</span>
                </div>
              </div>
              <div v-else>
                <h5>Información del Web Master</h5>
                <div class="row mb-3">
                  <div class="col-md-4"><strong>Nombre:</strong></div>
                  <div class="col-md-8">{{ userData.name }}</div>
                </div>
                <div class="row mb-3">
                  <div class="col-md-4"><strong>Email:</strong></div>
                  <div class="col-md-8">{{ userStore.email }}</div>
                </div>

                <div class="row mb-3">
                  <div class="col-md-4"><strong>Direccion:</strong></div>
                  <div class="col-md-8">{{ userData.direccion }}</div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-4"><strong>Telefono:</strong></div>
                    <div class="col-md-8">{{ userData.telefono }}</div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-4"><strong>Fecha Nacimiento:</strong></div>
                    <div class="col-md-8">{{ userData.fechaNacimiento }}</div>
                </div>


                <div class="text-center mt-4">
                  <router-link to="/ver-nuevas-peticiones" class="btn btn-primary">
                    Ver Nuevas Peticiones de Socios
                  </router-link>
                </div>

                <div class="text-center mt-4">
                  <router-link to="/activar-desactivar-perfiles" class="btn btn-primary">
                    Activar y Descativar Perfiles
                  </router-link>
                </div>

                <div class="text-center mt-4">
                  <router-link to="/crear-actividad" class="btn btn-primary">
                    Añadir Nueva Actividad
                  </router-link>
                </div>

                <div class="text-center mt-4">
                  <router-link to="/ver-informes" class="btn btn-primary">
                    Visualizar informes
                  </router-link>
                </div>
                <div class="text-center mt-4">
                  <router-link to="/ver-listado-morosos" class="btn btn-primary">
                    Ver Listado  de Usuarios sin Pagar
                  </router-link>
                </div>
                <div class="text-center mt-4">
                  <router-link to="/cuotas-mensuales" class="btn btn-primary">
                    Gestionar cuotas mensuales
                  </router-link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>

  <script>
  import { useUserStore } from '@/stores/userStore';
  import axios from 'axios';

  export default {
    data() {
      return {
        userData: {},
        loading: true,
      };
    },
    computed: {
      email() {
        const userStore = useUserStore();
        return userStore.email; // Use the email from the Pinia store
      },
      userStore() {
        return useUserStore(); // Access the entire Pinia store
      },
    },
    methods: {
      async fetchUserData() {
        try {
          const response = await axios.get(`http://localhost:8080/users/find_webmaster/${this.email}`);
          this.userData = response.data;
          const userStore = useUserStore();
          userStore.tipo_suscripcion = this.userData.tipoCuota
          userStore.direccion = this.userData.direccion
          userStore.activo = this.userData.activo
          userStore.telefono = this.userData.telefono
          userStore.fecha_nacimiento = this.userData.fechaNacimiento

        } catch (error) {
          console.error('Error al obtener los datos del socio:', error);
          this.userData = null;
        } finally {
          this.loading = false;
        }
      },
    },
    mounted() {
      this.fetchUserData();
    },
  };
  </script>

  <style scoped>
  .card {
    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  }

  .card-header {
    background-color: #1d1d1d;
    color: white;
    text-align: center;
  }

  .card-body {
    padding: 1.5rem;
  }

  .row {
    margin-bottom: 1rem;
  }

  .btn-primary{
    color: white;
    background-color: #1d1d1d;
    border: 2px solid #000000;
  }

  strong {
    font-weight: 600;
  }

  .currency {
    font-weight: bold;
  }
  </style>
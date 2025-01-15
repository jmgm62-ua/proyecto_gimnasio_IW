<template>
  <div class="morosos">
    <h1 class="text-center mb-4">Lista de Socios Morosos</h1>
    <div v-if="loading" class="text-center">
      <div class="spinner-border" role="status">
        <span class="sr-only">Cargando...</span>
      </div>
    </div>
    <ul v-else-if="activeUsers.length > 0" class="list-unstyled">
      <li v-for="user in activeUsers" :key="user.id" class="card mb-3">
        <div class="card-body">
          <p><strong>ID:</strong> {{ user.socioId }}</p>
          <p><strong>Nombre:</strong> {{ user.name }}</p>
          <p><strong>Email:</strong> {{ user.email }}</p>
          <p><strong>Dirección:</strong> {{ user.direccion }}</p>
          <p><strong>Teléfono:</strong> {{ user.telefono }}</p>
          <p><strong>Fecha de Nacimiento:</strong> {{ formatDate(user.fechaNacimiento) }}</p>
          <p><strong>Mensualidad No Pagada:</strong> {{ user.mensualidadNoPagada }}</p>
          <button
              @click="desactivarUsuario(user.socioId)"
              class="btn btn-danger"
          >
            Desactivar Usuario
          </button>
        </div>
      </li>
    </ul>
    <div v-else class="alert alert-info text-center">
      No hay socios morosos activos actualmente.
    </div>
  </div>
</template>

<script>
import { defineComponent, ref, computed, onMounted } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';
import axios from 'axios';

export default defineComponent({
  name: 'ListaMorosos',
  setup() {
    const users = ref([]);
    const userStore = useUserStore();
    const router = useRouter();
    const loading = ref(true);

    // Computed property para filtrar solo usuarios activos
    const activeUsers = computed(() => {
      return users.value.filter(user => user.activo);
    });

    const fetchMorosos = async () => {
      loading.value = true;
      try {
        const response = await axios.get('http://localhost:8080/api/morosos', {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
          }
        });
        users.value = response.data;
        console.log(users.value)
      } catch (error) {
        console.error('Error al obtener la lista de morosos:', error);
        alert('No se pudo obtener la lista de morosos.');
      } finally {
        loading.value = false;
      }
    };

    const desactivarUsuario = async (socioId) => {
      try {
        const response = await axios.put(
            `http://localhost:8080/api/morosos/${socioId}/desactivar`,
            {},
            {
              headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
              }
            }
        );

        if (response.status === 200) {
          // Actualizar la lista local filtrando el usuario desactivado
          users.value = users.value.filter(user => user.socioId !== socioId);
          alert('Usuario desactivado con éxito');
        }
      } catch (error) {
        console.error('Error al desactivar el usuario:', error);
        alert('Hubo un error al desactivar el usuario');
      }
    };

    const formatDate = (date) => {
      if (!date) return 'No disponible';
      return new Date(date).toLocaleDateString();
    };

    onMounted(async () => {
      if (userStore.userType !== 'LOGIN_OK_WEBMASTER') {
        alert('No tienes permisos para acceder a esta página.');
        router.push('/');
      } else {
        await fetchMorosos();
      }
    });

    return {
      activeUsers,
      loading,
      desactivarUsuario,
      formatDate
    };
  }
});
</script>

<style scoped>
.morosos {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.card {
  border: 1px solid #ddd;
  margin-bottom: 1rem;
  border-radius: 5px;
}

.card-body {
  padding: 1.5rem;
}

.btn-danger {
  width: 100%;
  background-color: #dc3545;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  font-size: 1rem;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 1rem;
}

.btn-danger:hover {
  background-color: #c82333;
}

p {
  margin: 0.5rem 0;
}

.spinner-border {
  width: 3rem;
  height: 3rem;
  color: #1d1d1d;
}
</style>
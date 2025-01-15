<template>
  <div class="no-activados">
    <h1 class="text-center mb-4">Lista de Socios Aceptados</h1>
    <div v-if="loading" class="text-center">
      <div class="spinner-border" role="status">
        <span class="sr-only">Cargando...</span>
      </div>
    </div>
    <ul v-else-if="pendingUsers.length > 0" class="list-unstyled">
      <li v-for="user in pendingUsers" :key="user.id" class="card mb-3">
        <div class="card-body">
          <p><strong>ID:</strong> {{ user.id || 'No asignado' }}</p>
          <p><strong>Nombre:</strong> {{ user.name || 'No asignado' }}</p>
          <p><strong>Email:</strong> {{ user.email || 'No asignado' }}</p>
          <p><strong>Tipo de Cuota:</strong> {{ user.tipoCuota || 'No asignado' }}</p>
          <p><strong>Fecha de Alta:</strong> {{ user.fechaAlta ? user.fechaAlta : 'No asignada' }}</p>
          <p><strong>Fecha de Baja:</strong> {{ user.fechaBaja ? user.fechaBaja : 'No asignada' }}</p>
          <p><strong>Saldo:</strong> {{ user.saldo || 0 }}</p>
          <p><strong>Tipo de Suscripción:</strong> {{ user.inscripcion ? user.inscripcion.tipoSuscripcion : 'No asignado' }}</p>
          <p><strong>Dirección:</strong> {{ user.direccion || 'No asignada' }}</p>
          <p><strong>Fecha de Nacimiento:</strong> {{ user.fechaNacimiento || 'No asignada' }}</p>
          <p><strong>Teléfono:</strong> {{ user.telefono || 'No asignado' }}</p>
          <p><strong>Activo:</strong> {{ user.activo ? 'Sí' : 'No' }}</p>

          <div class="d-flex justify-content-between mt-3">
            <button
                v-if="user.activo"
                @click="desactivarUsuario(user.id)"
                class="btn btn-danger">
              Desactivar Usuario
            </button>
            <button
                v-else
                @click="activarUsuario(user.id)"
                class="btn btn-success">
              Activar Usuario
            </button>
          </div>
        </div>
      </li>
    </ul>
    <div v-else class="alert alert-info text-center">
      No hay socios.
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';

export default {
  name: 'SociosPendientes',
  setup() {
    const pendingUsers = ref([]);
    const loading = ref(true);

    const fetchPendingUsers = async () => {
      loading.value = true;
      try {
        const response = await axios.get('http://localhost:8080/users/socios', {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
        });
        pendingUsers.value = response.data;
      } catch (error) {
        console.error('Error al obtener los usuarios pendientes:', error);
        alert('No se pudieron cargar los usuarios pendientes.');
      } finally {
        loading.value = false;
      }
    };

    const activarUsuario = async (id) => {
      console.log('ID enviado para activar:', id); // Verificar el ID
      try {
        await axios.put(`http://localhost:8080/users/${id}/activar`, {}, {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
        });
        // Solo actualizamos el estado del usuario sin eliminarlo de la lista
        const user = pendingUsers.value.find(user => user.id === id);
        if (user) user.activo = true;
        alert('Usuario activado con éxito');
      } catch (error) {
        console.error('Error al activar usuario:', error);
        alert('No se pudo activar el usuario.');
      }
    };

    const desactivarUsuario = async (id) => {
      try {
        await axios.put(`http://localhost:8080/users/${id}/desactivar`, {}, {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
        });
        // Solo actualizamos el estado del usuario sin eliminarlo de la lista
        const user = pendingUsers.value.find(user => user.id === id);
        if (user) user.activo = false;
        alert('Usuario desactivado con éxito');
      } catch (error) {
        console.error('Error al desactivar usuario:', error);
        alert('No se pudo desactivar el usuario.');
      }
    };

    onMounted(fetchPendingUsers);

    return {
      pendingUsers,
      loading,
      activarUsuario,
      desactivarUsuario,
    };
  },
};
</script>

<style scoped>
.no-activados {
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

.btn-success,
.btn-danger {
  width: 48%;
  padding: 0.5rem 1rem;
  font-size: 1rem;
  border-radius: 5px;
}

.btn-success:hover {
  background-color: #28a745;
}

.btn-danger:hover {
  background-color: #c82333;
}
</style>

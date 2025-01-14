<template>
  <div class="inactive-users">
    <h1>Solicitudes de Nuevos Socios</h1>

    <ul v-if="users.length > 0">
      <li v-for="user in users" :key="user.id">
        <p><strong>ID:</strong> {{ user.id }}</p>
        <p><strong>Nombre:</strong> {{ user.name }}</p>
        <p><strong>Email:</strong> {{ user.email }}</p>
        <p><strong>Dirección:</strong> {{ user.direccion }}</p>
        <p><strong>Teléfono:</strong> {{ user.telefono }}</p>
        <p><strong>Fecha de Nacimiento:</strong> {{ user.fecha_nacimiento }}</p>
        <button @click="approveUser(user.id)">Aprobar</button>
        <button @click="rejectUser(user.id)">Rechazar</button>
      </li>
    </ul>

    <p v-else>No hay solicitudes pendientes de nuevos socios.</p>
  </div>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';

export default defineComponent({
  name: 'InactiveUsers',
  setup() {
    const users = ref([]);
    const userStore = useUserStore();
    const router = useRouter();

    const fetchNewMemberRequests = async () => {
      try {
        const response = await fetch('http://localhost:8080/users/nuevos', {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
          }
        });
        if (response.ok) {
          users.value = await response.json();
        } else {
          throw new Error('Error al obtener solicitudes de nuevos socios');
        }
      } catch (error) {
        console.error(error);
        alert('No se pudo obtener la lista de solicitudes de nuevos socios.');
      }
    };

    const approveUser = async (userId) => {
      try {
        const response = await fetch(`http://localhost:8080/users/${userId}/aprobar`, {
          method: 'PUT',
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
          }
        });

        if (response.ok) {
          users.value = users.value.filter(user => user.id !== userId);
          alert('Usuario aprobado con éxito');
        } else {
          throw new Error('Error al aprobar el usuario');
        }
      } catch (error) {
        console.error(error);
        alert('Hubo un error al aprobar el usuario');
      }
    };

    const rejectUser = async (userId) => {
      try {
        const response = await fetch(`http://localhost:8080/users/${userId}/rechazar`, {
          method: 'DELETE',
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
          }
        });

        if (response.ok) {
          users.value = users.value.filter(user => user.id !== userId);
          alert('Usuario rechazado y eliminado con éxito');
        } else {
          throw new Error('Error al rechazar el usuario');
        }
      } catch (error) {
        console.error(error);
        alert('Hubo un error al rechazar el usuario');
      }
    };

    onMounted(() => {
      if (userStore.userType !== 'LOGIN_OK_WEBMASTER') {
        alert('No tienes permisos para acceder a esta página.');
        router.push('/'); // Redirigir a la página de inicio
      } else {
        fetchNewMemberRequests();
      }
    });

    return {
      users,
      approveUser,
      rejectUser
    };
  }
});
</script>

<style scoped>
.inactive-users {
  max-width: 800px;
  margin: 0 auto;
  text-align: left;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  border: 1px solid #ddd;
  padding: 1rem;
  margin-bottom: 1rem;
  border-radius: 5px;
}
button {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  font-size: 1rem;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 0.5rem;
}
button:hover {
  background-color: #45a049;
}
button:last-child {
  background-color: #f44336;
}
button:last-child:hover {
  background-color: #e53935;
}
p {
  margin: 0.5rem 0;
}
</style>

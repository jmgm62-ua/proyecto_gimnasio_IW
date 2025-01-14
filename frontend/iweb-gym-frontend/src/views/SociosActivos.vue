<template>
  <div class="active-users">
    <h1>Socios Activos</h1>

    <ul v-if="users.length > 0">
      <li v-for="user in users" :key="user.id">
        <p><strong>ID:</strong> {{ user.id }}</p>
        <p><strong>Nombre:</strong> {{ user.name }}</p>
        <p><strong>Email:</strong> {{ user.email }}</p>
        <p><strong>Dirección:</strong> {{ user.direccion }}</p>
        <p><strong>Teléfono:</strong> {{ user.telefono }}</p>
        <p><strong>Fecha de Nacimiento:</strong> {{ user.fecha_nacimiento }}</p>
        <p><strong>Fecha de Alta:</strong> {{ user.fecha_alta }}</p>
        <button @click="deactivateUser(user.id)">Desactivar</button>
      </li>
    </ul>

    <p v-else>No hay socios activos.</p>
  </div>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';

export default defineComponent({
  name: 'ActiveUsers',
  setup() {
    const users = ref([]);
    const userStore = useUserStore();
    const router = useRouter();

    const fetchActiveMembers = async () => {
      try {
        const response = await fetch('http://localhost:8080/users/activos', {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
          }
        });
        if (response.ok) {
          users.value = await response.json();
        } else {
          throw new Error('Error al obtener los socios activos');
        }
      } catch (error) {
        console.error(error);
        alert('No se pudo obtener la lista de socios activos.');
      }
    };

    const deactivateUser = async (userId) => {
      try {
        const response = await fetch(`http://localhost:8080/users/${userId}/desactivar`, {
          method: 'PUT',
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
          }
        });

        if (response.ok) {
          users.value = users.value.filter(user => user.id !== userId);
          alert('Socio desactivado con éxito');
        } else {
          throw new Error('Error al desactivar el socio');
        }
      } catch (error) {
        console.error(error);
        alert('Hubo un error al desactivar el socio');
      }
    };

    onMounted(() => {
      if (userStore.userType !== 'LOGIN_OK_WEBMASTER') {
        alert('No tienes permisos para acceder a esta página.');
        router.push('/'); // Redirigir a la página de inicio
      } else {
        fetchActiveMembers();
      }
    });

    return {
      users,
      deactivateUser
    };
  }
});
</script>

<style scoped>
/* Contenedor principal */
.active-users {
  padding: 20px;
  font-family: 'Arial', sans-serif;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

/* Título */
h1 {
  color: #333;
  text-align: center;
  font-size: 2rem;
  margin-bottom: 20px;
}

/* Lista de usuarios */
.user-list {
  list-style-type: none;
  padding: 0;
}

.user-item {
  background-color: #fff;
  border: 1px solid #ddd;
  margin-bottom: 15px;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.user-item:hover {
  background-color: #f1f1f1;
}

.user-details {
  flex-grow: 1;
}

.user-details p {
  margin: 5px 0;
  color: #555;
}

/* Botón de desactivación */
.deactivate-btn {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 10px 15px;
  font-size: 1rem;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.deactivate-btn:hover {
  background-color: #d32f2f;
}

.deactivate-btn:focus {
  outline: none;
}

/* Mensaje si no hay usuarios */
.no-users {
  text-align: center;
  color: #777;
  font-size: 1.2rem;
  margin-top: 20px;
}
</style>
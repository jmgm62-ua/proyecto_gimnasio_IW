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
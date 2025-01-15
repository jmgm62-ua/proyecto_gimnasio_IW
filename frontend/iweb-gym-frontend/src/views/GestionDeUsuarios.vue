<template>
  <template>
    <div class="users">
      <h1>Gestión de Socios</h1>

      <section class="active-users">
        <h2>Socios Activos</h2>
        <ul v-if="activeUsers.length > 0" class="user-list">
          <li v-for="user in activeUsers" :key="user.id" class="user-item">
            <div class="user-details">
              <p><strong>ID:</strong> {{ user.id }}</p>
              <p><strong>Nombre:</strong> {{ user.name }}</p>
              <p><strong>Email:</strong> {{ user.email }}</p>
              <p><strong>Dirección:</strong> {{ user.direccion }}</p>
              <p><strong>Teléfono:</strong> {{ user.telefono }}</p>
              <p><strong>Fecha de Alta:</strong> {{ user.fecha_alta }}</p>
            </div>
            <button @click="deactivateUser(user.id)" class="deactivate-btn">Desactivar</button>
          </li>
        </ul>
        <p v-else>No hay socios activos.</p>
      </section>

      <section class="inactive-users">
        <h2>Socios Inactivos</h2>
        <ul v-if="inactiveUsers.length > 0" class="user-list">
          <li v-for="user in inactiveUsers" :key="user.id" class="user-item">
            <div class="user-details">
              <p><strong>ID:</strong> {{ user.id }}</p>
              <p><strong>Nombre:</strong> {{ user.name }}</p>
              <p><strong>Email:</strong> {{ user.email }}</p>
              <p><strong>Dirección:</strong> {{ user.direccion }}</p>
              <p><strong>Teléfono:</strong> {{ user.telefono }}</p>
              <p><strong>Fecha de Alta:</strong> {{ user.fecha_alta }}</p>
            </div>
            <button @click="activateUser(user.id)" class="activate-btn">Activar</button>
          </li>
        </ul>
        <p v-else>No hay socios inactivos.</p>
      </section>
    </div>
  </template>

  <script>
    import { defineComponent, ref, onMounted } from 'vue';

    export default defineComponent({
      name: 'ActiveUsers',
      setup() {
        const activeUsers = ref([]);
        const inactiveUsers = ref([]);

        const fetchUsers = async () => {
          try {
            const response = await fetch('http://localhost:8080/users');
            const users = await response.json();

            activeUsers.value = users.filter(user => user.activo === true);
            inactiveUsers.value = users.filter(
                user => user.activo === false && user.fecha_alta !== null
            );
          } catch (error) {
            console.error('Error al obtener la lista de usuarios:', error);
            alert('No se pudo obtener la lista de usuarios.');
          }
        };

        const deactivateUser = async (userId) => {
          try {
            const response = await fetch(`http://localhost:8080/users/${userId}/desactivar`, {
              method: 'PUT',
              headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`,
              },
            });

            if (response.ok) {
              const userIndex = activeUsers.value.findIndex(user => user.id === userId);
              if (userIndex !== -1) {
                const user = activeUsers.value.splice(userIndex, 1)[0];
                user.activo = false;
                user.fecha_baja = new Date().toISOString();
                inactiveUsers.value.push(user);
              }
              alert('Usuario desactivado con éxito.');
            } else {
              throw new Error('Error al desactivar el usuario.');
            }
          } catch (error) {
            console.error(error);
            alert('Hubo un error al desactivar el usuario.');
          }
        };

        const activateUser = async (userId) => {
          try {
            const response = await fetch(`http://localhost:8080/users/${userId}/activar`, {
              method: 'PUT',
              headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`,
              },
            });

            if (response.ok) {
              const userIndex = inactiveUsers.value.findIndex(user => user.id === userId);
              if (userIndex !== -1) {
                const user = inactiveUsers.value.splice(userIndex, 1)[0];
                user.activo = true;
                user.fecha_baja = null;
                activeUsers.value.push(user);
              }
              alert('Usuario activado con éxito.');
            } else {
              throw new Error('Error al activar el usuario.');
            }
          } catch (error) {
            console.error(error);
            alert('Hubo un error al activar el usuario.');
          }
        };

        onMounted(() => {
          fetchUsers();
        });

        return {
          activeUsers,
          inactiveUsers,
          deactivateUser,
          activateUser,
        };
      },
    });
  </script>

  <style scoped>
    .users {
      max-width: 800px;
      margin: 0 auto;
      padding: 20px;
    }

    h1 {
      text-align: center;
      margin-bottom: 20px;
    }

    section {
      margin-bottom: 40px;
    }

    .user-list {
      list-style-type: none;
      padding: 0;
    }

    .user-item {
      display: flex;
      justify-content: space-between;
      align-items: center;
      background: #fff;
      margin-bottom: 15px;
      padding: 15px;
      border: 1px solid #ddd;
      border-radius: 8px;
    }

    .user-details p {
      margin: 0 0 5px;
    }

    .deactivate-btn {
      background-color: #f44336;
      color: white;
      border: none;
      padding: 10px 15px;
      border-radius: 5px;
      cursor: pointer;
    }

    .activate-btn {
      background-color: #4caf50;
      color: white;
      border: none;
      padding: 10px 15px;
      border-radius: 5px;
      cursor: pointer;
    }

    .deactivate-btn:hover {
      background-color: #d32f2f;
    }

    .activate-btn:hover {
      background-color: #388e3c;
    }

    p {
      color: #555;
    }
  </style>

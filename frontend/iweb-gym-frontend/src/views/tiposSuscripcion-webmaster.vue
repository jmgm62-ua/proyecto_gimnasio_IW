<template>
  <div class="subscriptions-management">
    <h1>Gestión de Tipos de Suscripción</h1>

    <!-- Formulario para añadir un nuevo tipo de suscripción -->
    <form @submit.prevent="addSubscription">
      <div class="form-group">
        <label for="name">Nombre</label>
        <input type="text" id="name" v-model="newSubscription.name" required />
      </div>
      <div class="form-group">
        <label for="price">Precio</label>
        <input type="number" id="price" v-model="newSubscription.price" required />
      </div>
      <button type="submit">Añadir Suscripción</button>
    </form>

    <!-- Lista de tipos de suscripción -->
    <h2>Tipos de Suscripción</h2>
    <ul v-if="subscriptions.length > 0">
      <li v-for="subscription in subscriptions" :key="subscription.id">
        <p><strong>Nombre:</strong> {{ subscription.name }}</p>
        <p><strong>Precio:</strong> {{ subscription.price }} €</p>
        <button @click="deleteSubscription(subscription.id)">Eliminar</button>
      </li>
    </ul>
    <p v-else>No hay tipos de suscripción disponibles.</p>
  </div>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';

export default defineComponent({
  name: 'SubscriptionsManagement',
  setup() {
    const subscriptions = ref([]);
    const newSubscription = ref({ name: '', price: 0 });
    const userStore = useUserStore();
    const router = useRouter();

    const fetchSubscriptions = async () => {
      try {
        const response = await fetch('http://localhost:8080/subscriptions', {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
          },
        });
        if (response.ok) {
          subscriptions.value = await response.json();
        } else {
          throw new Error('Error al obtener los tipos de suscripción');
        }
      } catch (error) {
        console.error(error);
        alert('No se pudo obtener la lista de tipos de suscripción.');
      }
    };

    const addSubscription = async () => {
      try {
        const response = await fetch('http://localhost:8080/subscriptions', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${localStorage.getItem('token')}`,
          },
          body: JSON.stringify(newSubscription.value),
        });

        if (response.ok) {
          alert('Tipo de suscripción añadido con éxito');
          newSubscription.value = { name: '', price: 0 };
          fetchSubscriptions();
        } else {
          throw new Error('Error al añadir el tipo de suscripción');
        }
      } catch (error) {
        console.error(error);
        alert('Hubo un error al añadir el tipo de suscripción.');
      }
    };

    const deleteSubscription = async (id) => {
      try {
        const response = await fetch(`http://localhost:8080/subscriptions/${id}`, {
          method: 'DELETE',
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
          },
        });

        if (response.ok) {
          alert('Tipo de suscripción eliminado con éxito');
          fetchSubscriptions();
        } else {
          throw new Error('Error al eliminar el tipo de suscripción');
        }
      } catch (error) {
        console.error(error);
        alert('Hubo un error al eliminar el tipo de suscripción.');
      }
    };

    onMounted(() => {
      if (userStore.userType !== 'LOGIN_OK_WEBMASTER') {
        alert('No tienes permisos para acceder a esta página.');
        router.push('/');
      } else {
        fetchSubscriptions();
      }
    });

    return {
      subscriptions,
      newSubscription,
      addSubscription,
      deleteSubscription,
    };
  },
});
</script>

<style scoped>
.subscriptions-management {
  max-width: 800px;
  margin: 0 auto;
  text-align: left;
}
.form-group {
  margin-bottom: 1rem;
}
label {
  display: block;
  font-weight: bold;
  margin-bottom: 0.5rem;
}
input {
  width: 100%;
  padding: 0.5rem;
  margin-bottom: 1rem;
  border: 1px solid #ddd;
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
</style>

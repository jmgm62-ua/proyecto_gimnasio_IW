<template>
  <div class="subscription-types">
    <h1>Gestión de Tipos de Suscripción</h1>

    <form @submit.prevent="createSubscriptionType">
      <div class="form-group">
        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" v-model="newSubscription.nombre" required />
      </div>
      <div class="form-group">
        <label for="precio">Precio</label>
        <input type="number" id="precio" v-model="newSubscription.precio" step="0.01" required />
      </div>
      <button type="submit">Crear Tipo de Suscripción</button>
    </form>

    <ol v-if="subscriptionTypes.length > 0">
      <li v-for="type in subscriptionTypes" :key="type.id">
        <p><strong>Nombre:</strong> {{ type.nombre }}</p>
        <p><strong>Precio:</strong> {{ type.precio }} €</p>
      </li>
    </ol>
    <p v-else>No hay tipos de suscripción disponibles.</p>
  </div>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';

export default defineComponent({
  name: 'SubscriptionTypes',
  setup() {
    const subscriptionTypes = ref([]);
    const newSubscription = ref({
      nombre: '',
      precio: 0,
    });
    const userStore = useUserStore();
    const router = useRouter();

    const fetchSubscriptionTypes = async () => {
      try {
        const response = await fetch('http://localhost:8080/subscriptions', {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
          },
        });
        if (response.ok) {
          subscriptionTypes.value = await response.json();
        } else {
          throw new Error('Error al obtener los tipos de suscripción');
        }
      } catch (error) {
        console.error(error);
        alert('No se pudo obtener los tipos de suscripción.');
      }
    };

    const createSubscriptionType = async () => {
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
          alert('Tipo de suscripción creado con éxito.');
          fetchSubscriptionTypes(); // Actualizar la lista
          newSubscription.value = { nombre: '', precio: 0 }; // Resetear el formulario
        } else {
          throw new Error('Error al crear el tipo de suscripción');
        }
      } catch (error) {
        console.error(error);
        alert('Hubo un error al crear el tipo de suscripción.');
      }
    };

    onMounted(() => {
      if (userStore.userType !== 'LOGIN_OK_WEBMASTER') {
        alert('No tienes permisos para acceder a esta página.');
        router.push('/'); // Redirigir a la página de inicio
      } else {
        fetchSubscriptionTypes();
      }
    });

    return {
      subscriptionTypes,
      newSubscription,
      createSubscriptionType,
    };
  },
});
</script>

<style scoped>
.subscription-types {
  max-width: 800px;
  margin: 0 auto;
  text-align: left;
}
form {
  margin-bottom: 2rem;
}
.form-group {
  margin-bottom: 1rem;
}
form label {
  display: block;
  font-weight: bold;
}
form input {
  width: 100%;
  padding: 0.5rem;
  margin-top: 0.5rem;
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
}
button:hover {
  background-color: #45a049;
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
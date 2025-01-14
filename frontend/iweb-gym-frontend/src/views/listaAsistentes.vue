<template>
  <div class="activity-attendees">
    <h1>Asistentes a la Actividad</h1>
    <ul v-if="attendees.length > 0">
      <li v-for="attendee in attendees" :key="attendee.id">
        <p><strong>Nombre:</strong> {{ attendee.name }}</p>
        <p><strong>Apellidos:</strong> {{ attendee.apellidos }}</p>
        <button @click="viewDetails(attendee)">Ver Detalles</button>
      </li>
    </ul>
    <p v-else>No hay asistentes registrados para esta actividad.</p>

    <div v-if="selectedAttendee" class="attendee-details">
      <h2>Detalles del Asistente</h2>
      <p><strong>ID:</strong> {{ selectedAttendee.id }}</p>
      <p><strong>Nombre:</strong> {{ selectedAttendee.name }}</p>
      <p><strong>Apellidos:</strong> {{ selectedAttendee.apellidos }}</p>
      <p><strong>Email:</strong> {{ selectedAttendee.email }}</p>
      <p><strong>Dirección:</strong> {{ selectedAttendee.direccion }}</p>
      <p><strong>Teléfono:</strong> {{ selectedAttendee.telefono }}</p>
      <p><strong>Fecha de Nacimiento:</strong> {{ selectedAttendee.fecha_nacimiento }}</p>
      <button @click="closeDetails">Cerrar Detalles</button>
    </div>
  </div>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

export default defineComponent({
  name: 'ActivityAttendees',
  setup() {
    const route = useRoute();
    const attendees = ref([]);
    const selectedAttendee = ref(null);

    const fetchAttendees = async () => {
      const actividadId = route.params.id; // ID de la actividad
      try {
        const response = await fetch(`http://localhost:8080/actividad/${actividadId}/asistentes`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`, // Token para autenticar
          },
        });
        if (response.ok) {
          attendees.value = await response.json();
        } else {
          throw new Error('Error al obtener los asistentes.');
        }
      } catch (error) {
        console.error(error);
        alert('No se pudo obtener la lista de asistentes.');
      }
    };

    const viewDetails = (attendee) => {
      selectedAttendee.value = attendee; // Mostrar los detalles del asistente seleccionado
    };

    const closeDetails = () => {
      selectedAttendee.value = null; // Ocultar los detalles
    };

    onMounted(() => {
      fetchAttendees();
    });

    return {
      attendees,
      selectedAttendee,
      viewDetails,
      closeDetails,
    };
  },
});
</script>

<style scoped>
.activity-attendees {
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
.attendee-details {
  border: 1px solid #ddd;
  padding: 1rem;
  margin-top: 1rem;
  border-radius: 5px;
  background-color: #f9f9f9;
}
</style>

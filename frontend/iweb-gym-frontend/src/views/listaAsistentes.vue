<template>
  <div class="view-assistants">
    <h1>Asistentes a la Actividad</h1>

    <ul v-if="assistants.length > 0">
      <li v-for="assistant in assistants" :key="assistant.id">
        <p><strong>Nombre:</strong> {{ assistant.name }}</p>
        <p><strong>Email:</strong> {{ assistant.email }}</p>
      </li>
    </ul>

    <p v-else>No hay asistentes activos para esta actividad.</p>
  </div>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

export default defineComponent({
  name: 'ViewAssistants',
  setup() {
    const assistants = ref([]);
    const route = useRoute();
    const activityId = ref(route.params.activityId || "");

    const fetchAssistants = async () => {
      if (!activityId.value) return;

      const response = await fetch(`http://localhost:8080/api/actividades/${activityId.value}/asistentes`);
      assistants.value = await response.json();
    };

    onMounted(() => {
      fetchAssistants();
    });

    return {
      assistants
    };
  }
});
</script>

<style scoped>
.view-assistants {
  max-width: 600px;
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
p {
  margin: 0.5rem 0;
}
</style>
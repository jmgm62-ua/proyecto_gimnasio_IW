<template>
  <div class="add-activity">
    <h1>Añadir Actividad</h1>
    <form @submit.prevent="submitForm">
      <div>
        <label for="nombre">Nombre:</label>
        <input v-model="form.nombre" id="nombre" type="text" required />
      </div>

      <div>
        <label for="dia_semana">Día de la Semana:</label>
        <select v-model="form.dia_semana" id="dia_semana" required>
          <option value="">Selecciona un día</option>
          <option value="Lunes">Lunes</option>
          <option value="Martes">Martes</option>
          <option value="Miércoles">Miércoles</option>
          <option value="Jueves">Jueves</option>
          <option value="Viernes">Viernes</option>
          <option value="Sábado">Sábado</option>
          <option value="Domingo">Domingo</option>
        </select>
      </div>

      <div>
        <label for="fecha_inicio">Fecha Inicio:</label>
        <input v-model="form.fecha_inicio" id="fecha_inicio" type="datetime-local" required />
      </div>

      <div>
        <label for="fecha_fin">Fecha Fin:</label>
        <input v-model="form.fecha_fin" id="fecha_fin" type="datetime-local" required />
      </div>

      <div>
        <label for="hora_inicio">Hora Inicio:</label>
        <input v-model="form.hora_inicio" id="hora_inicio" type="time" required />
      </div>

      <div>
        <label for="hora_fin">Hora Fin:</label>
        <input v-model="form.hora_fin" id="hora_fin" type="time" required />
      </div>

      <div>
        <label for="tipo_actividad">Tipo de Actividad:</label>
        <select v-model="form.tipo_actividad_id" id="tipo_actividad" required>
          <option value="" disabled>Selecciona un tipo</option>
          <option v-for="tipo in tiposActividad" :key="tipo.id" :value="tipo.id">
            {{ tipo.nombre }}
          </option>
        </select>
      </div>

      <div>
        <label for="monitor">Monitor:</label>
        <select v-model="form.monitor_id" id="monitor" required>
          <option value="" disabled>Selecciona un monitor</option>
          <option v-for="monitor in monitores" :key="monitor.id" :value="monitor.id">
            {{ monitor.name }}
          </option>
        </select>
      </div>

      <button type="submit">Añadir Actividad</button>
    </form>
  </div>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';

export default defineComponent({
  name: 'AddActivity',
  setup() {
    const form = ref({
      nombre: '',
      dia_semana: '',
      fecha_inicio: '',
      fecha_fin: '',
      hora_inicio: '',
      hora_fin: '',
      tipo_actividad_id: '',
      monitor_id: ''
    });

    const tiposActividad = ref([]);
    const monitores = ref([]);

    const fetchTiposActividad = async () => {
      const response = await fetch('http://localhost:8080/api/get-tipos-actividad');
      tiposActividad.value = await response.json();
    };

    const fetchMonitores = async () => {
      const response = await fetch('http://localhost:8080/api/get-monitores');
      monitores.value = await response.json();
    };

    const submitForm = async () => {
      const response = await fetch('http://localhost:8080/api/add-actividad', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(form.value)
      });

      if (response.ok) {
        alert('Actividad añadida con éxito');
      } else {
        alert('Error al añadir la actividad');
      }
    };

    onMounted(() => {
      fetchTiposActividad();
      fetchMonitores();
    });

    return {
      form,
      tiposActividad,
      monitores,
      submitForm
    };
  }
});
</script>

<style scoped>
.add-activity {
  max-width: 600px;
  margin: 0 auto;
}
form div {
  margin-bottom: 1rem;
}
label {
  display: block;
  margin-bottom: 0.5rem;
}
input, select, button {
  width: 100%;
  padding: 0.5rem;
  font-size: 1rem;
}
button {
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
}
button:hover {
  background-color: #0056b3;
}
</style>
<template>
    <div class="container mx-auto p-4">
      <h1 class="text-2xl font-bold mb-4">Mis Clases</h1>
      
      <div class="mb-4">
        <button 
          @click="viewMode = 'list'"
          :class="['mr-2 px-4 py-2 rounded', viewMode === 'list' ? 'bg-blue-500 text-white' : 'bg-gray-200']"
        >
          Lista
        </button>
        <button 
          @click="viewMode = 'calendar'"
          :class="['px-4 py-2 rounded', viewMode === 'calendar' ? 'bg-blue-500 text-white' : 'bg-gray-200']"
        >
          Calendario
        </button>
      </div>
  
      <!-- List View -->
      <div v-if="viewMode === 'list'">
        <div class="mb-8">
          <h2 class="text-xl font-semibold mb-4">Clases Pendientes</h2>
          <div v-if="clasesPendientes.length" class="space-y-4">
            <div v-for="clase in clasesPendientes" :key="clase.id" 
                 class="border p-4 rounded shadow hover:shadow-lg transition-shadow">
              <div class="flex justify-between items-start">
                <div>
                  <h3 class="font-medium text-lg">{{ clase.nombre }}</h3>
                  <p class="text-gray-600">Fecha: {{ formatDate(clase.fechaInicio) }}</p>
                  <p class="text-gray-600">Hora: {{ clase.horaInicio }} - {{ clase.horaFin }}</p>
                  <p class="text-gray-600">Asistentes: {{ clase.numAsistentes }}</p>
                </div>
                <button 
                  @click="verAsistentes(clase)"
                  class="bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-600"
                >
                  Ver Asistentes
                </button>
              </div>
            </div>
          </div>
          <p v-else class="text-gray-500">No hay clases pendientes</p>
        </div>
  
        <div>
          <h2 class="text-xl font-semibold mb-4">Historial de Clases</h2>
          <div v-if="clasesHistoricas.length" class="space-y-4">
            <div v-for="clase in clasesHistoricas" :key="clase.id" 
                 class="border p-4 rounded shadow bg-gray-50 hover:shadow-lg transition-shadow">
              <div class="flex justify-between items-start">
                <div>
                  <h3 class="font-medium text-lg">{{ clase.nombre }}</h3>
                  <p class="text-gray-600">Fecha: {{ formatDate(clase.fechaInicio) }}</p>
                  <p class="text-gray-600">Hora: {{ clase.horaInicio }} - {{ clase.horaFin }}</p>
                  <p class="text-gray-600">Asistentes: {{ clase.numAsistentes }}</p>
                </div>
                <button 
                  @click="verAsistentes(clase)"
                  class="bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-600"
                >
                  Ver Asistentes
                </button>
              </div>
            </div>
          </div>
          <p v-else class="text-gray-500">No hay clases históricas</p>
        </div>
      </div>
  
      <!-- Calendar View -->
      <div v-else class="border rounded p-4">
        <FullCalendar 
          :options="calendarOptions"
          class="h-screen"
        />
      </div>
  
      <!-- Modal de Asistentes -->
      <div v-if="showModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
        <div class="bg-white p-6 rounded-lg max-w-lg w-full max-h-[80vh] overflow-y-auto">
          <div class="flex justify-between items-center mb-4">
            <h3 class="text-xl font-bold">Asistentes - {{ selectedClase?.nombre }}</h3>
            <button @click="showModal = false" class="text-gray-500 hover:text-gray-700">
              <span class="text-2xl">&times;</span>
            </button>
          </div>
          <div class="space-y-2">
            <div v-if="asistentes.length" v-for="asistente in asistentes" :key="asistente.id"
                 class="p-3 border rounded">
              <p class="font-medium">{{ asistente.name }}</p>
              <p class="text-sm text-gray-600">{{ asistente.email }}</p>
              <p class="text-sm text-gray-600">Tel: {{ asistente.telefono }}</p>
            </div>
            <p v-else class="text-gray-500">No hay asistentes registrados</p>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, computed } from 'vue';
  import { useUserStore } from '@/stores/userStore';
  import axios from 'axios';
  import FullCalendar from '@fullcalendar/vue3';
  import dayGridPlugin from '@fullcalendar/daygrid';
  import esLocale from '@fullcalendar/core/locales/es';
  
  const userStore = useUserStore();
  const email = userStore.email;
  const viewMode = ref('list');
  const clasesPendientes = ref([]);
  const clasesHistoricas = ref([]);
  const showModal = ref(false);
  const selectedClase = ref(null);
  const asistentes = ref([]);
  
  const calendarEvents = computed(() => {
    const pendientes = clasesPendientes.value.map(clase => ({
      id: clase.id,
      title: clase.nombre,
      start: clase.fechaInicio,
      end: clase.fechaFin,
      backgroundColor: '#EAB308',
      borderColor: '#EAB308',
      extendedProps: {
        numAsistentes: clase.numAsistentes
      }
    }));
  
    const historicas = clasesHistoricas.value.map(clase => ({
      id: clase.id,
      title: clase.nombre,
      start: clase.fechaInicio,
      end: clase.fechaFin,
      backgroundColor: '#16A34A',
      borderColor: '#16A34A',
      extendedProps: {
        numAsistentes: clase.numAsistentes
      }
    }));
  
    return [...pendientes, ...historicas];
  });
  
  const calendarOptions = computed(() => ({
    plugins: [dayGridPlugin],
    initialView: 'dayGridMonth',
    locale: esLocale,
    events: calendarEvents.value,
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'dayGridMonth,dayGridWeek'
    },
    firstDay: 1,
    height: 'auto',
    eventClick: (info) => {
      const clase = [...clasesPendientes.value, ...clasesHistoricas.value]
        .find(c => c.id === parseInt(info.event.id));
      if (clase) {
        verAsistentes(clase);
      }
    },
    eventContent: (arg) => {
      return {
        html: `
          <div class="p-1">
            <div class="font-bold">${arg.event.title}</div>
            <div class="text-xs">Asistentes: ${arg.event.extendedProps.numAsistentes}</div>
          </div>
        `
      };
    }
  }));
  
  const fetchClases = async () => {
    try {
      const [pendientesRes, historicasRes] = await Promise.all([
        axios.get(`http://localhost:8080/api/monitor/actividades/${email}/pendientes`),
        axios.get(`http://localhost:8080/api/monitor/actividades/${email}/historicas`)
      ]);
      
      clasesPendientes.value = pendientesRes.data;
      clasesHistoricas.value = historicasRes.data;
    } catch (error) {
      console.error('Error al obtener las clases:', error);
    }
  };
  
  const verAsistentes = async (clase) => {
    try {
      selectedClase.value = clase;
      const response = await axios.get(`http://localhost:8080/api/monitor/actividades/${clase.id}/asistentes`);
      asistentes.value = response.data;
      showModal.value = true;
    } catch (error) {
      console.error('Error al obtener los asistentes:', error);
    }
  };
  
  const formatDate = (date) => {
    return new Date(date).toLocaleDateString('es-ES', {
      weekday: 'long',
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  };
  
  onMounted(() => {
    fetchClases();
  });
  </script>
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
          <div v-if="reservasPendientes.length" class="space-y-4">
            <div v-for="reserva in reservasPendientes" :key="reserva.id" 
                 class="border p-4 rounded shadow">
              <h3 class="font-medium">{{ reserva.titulo }}</h3>
              <p>Fecha: {{ formatDate(reserva.fecha) }}</p>
              <p>Estado: <span class="text-yellow-600">Pendiente</span></p>
            </div>
          </div>
          <p v-else class="text-gray-500">No hay clases pendientes</p>
        </div>
  
        <div>
          <h2 class="text-xl font-semibold mb-4">Historial de Clases</h2>
          <div v-if="reservasHistoricas.length" class="space-y-4">
            <div v-for="reserva in reservasHistoricas" :key="reserva.id" 
                 class="border p-4 rounded shadow bg-gray-50">
              <h3 class="font-medium">{{ reserva.nombreActividad }}</h3>
              <p>Fecha: {{ formatDate(reserva.fecha) }}</p>
              <p>Estado: <span class="text-green-600">Completada</span></p>
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
  const reservasPendientes = ref([]);
  const reservasHistoricas = ref([]);
  
  const calendarEvents = computed(() => {
    const pendientes = reservasPendientes.value.map(reserva => ({
      title: reserva.nombreActividad,
      start: reserva.fecha,
      backgroundColor: '#EAB308', // yellow-600
      borderColor: '#EAB308'
    }));
  
    const historicas = reservasHistoricas.value.map(reserva => ({
      title: reserva.nombreActividad,
      start: reserva.fecha,
      backgroundColor: '#16A34A', // green-600
      borderColor: '#16A34A'
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
      alert(`Reserva: ${info.event.title}`);
    }
  }));
  
  const fetchReservas = async () => {
    try {
      const [pendientesRes, historicasRes] = await Promise.all([
        axios.get(`http://localhost:8080/reservas/pendientes/${email}`),
        axios.get(`http://localhost:8080/reservas/historicas/${email}`)
      ]);
      
      reservasPendientes.value = pendientesRes.data;
      reservasHistoricas.value = historicasRes.data;
    } catch (error) {
      console.error('Error al obtener las reservas:', error);
    }
  };
  
  const formatDate = (date) => {
    return new Date(date).toLocaleDateString('es-ES');
  };
  
  onMounted(() => {
    fetchReservas();
  });
</script>
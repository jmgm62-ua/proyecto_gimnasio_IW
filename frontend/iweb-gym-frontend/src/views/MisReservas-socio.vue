<template>
  <div class="container py-4">
    <div class="row">
      <div class="col-12">
        <h1 class="display-5 mb-4 text-center">
          <i class="bi bi-calendar-check"></i> Mis Reservas
        </h1>
        
        <div class="d-flex justify-content-center gap-3 mb-4">
          <button 
            @click="viewMode = 'list'"
            class="btn btn-lg"
            :class="viewMode === 'list' ? 'btn-primary' : 'btn-outline-primary'"
          >
            <i class="bi bi-list-ul"></i> Lista
          </button>
          <button 
            @click="viewMode = 'calendar'"
            class="btn btn-lg"
            :class="viewMode === 'calendar' ? 'btn-primary' : 'btn-outline-primary'"
          >
            <i class="bi bi-calendar3"></i> Calendario
          </button>
        </div>
      </div>
    </div>

    <!-- List View -->
    <div v-if="viewMode === 'list'" class="row">
      <div class="col-12 col-lg-6 mb-4">
        <div class="card h-100">
          <div class="card-header bg-warning bg-opacity-10">
            <h2 class="card-title mb-0">
              <i class="bi bi-clock text-warning"></i> Reservas Pendientes
            </h2>
          </div>
          <div class="card-body">
            <div v-if="reservasPendientes.length" class="d-flex flex-column gap-3">
              <div v-for="reserva in reservasPendientes" :key="reserva.id" 
                   class="card shadow-sm">
                <div class="card-body">
                  <h3 class="h5 mb-3">
                    <i class="bi bi-bookmark"></i> {{ reserva.titulo }}
                  </h3>
                  <div class="d-flex justify-content-between align-items-center">
                    <div>
                      <p class="mb-1">
                        <i class="bi bi-calendar3"></i> {{ formatDate(reserva.fecha) }}
                      </p>
                      <span class="badge bg-warning">Pendiente</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <p v-else class="text-muted text-center py-4">
              <i class="bi bi-inbox fs-1 d-block mb-2"></i>
              No hay reservas pendientes
            </p>
          </div>
        </div>
      </div>

      <div class="col-12 col-lg-6 mb-4">
        <div class="card h-100">
          <div class="card-header bg-success bg-opacity-10">
            <h2 class="card-title mb-0">
              <i class="bi bi-check-circle text-success"></i> Historial de Reservas
            </h2>
          </div>
          <div class="card-body">
            <div v-if="reservasHistoricas.length" class="d-flex flex-column gap-3">
              <div v-for="reserva in reservasHistoricas" :key="reserva.id" 
                   class="card shadow-sm">
                <div class="card-body">
                  <h3 class="h5 mb-3">
                    <i class="bi bi-bookmark"></i> {{ reserva.nombreActividad }}
                  </h3>
                  <div class="d-flex justify-content-between align-items-center">
                    <div>
                      <p class="mb-1">
                        <i class="bi bi-calendar3"></i> {{ formatDate(reserva.fecha) }}
                      </p>
                      <span class="badge bg-success">Completada</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <p v-else class="text-muted text-center py-4">
              <i class="bi bi-inbox fs-1 d-block mb-2"></i>
              No hay reservas históricas
            </p>
          </div>
        </div>
      </div>
    </div>

    <!-- Calendar View -->
    <div v-else class="row">
      <div class="col-12">
        <div class="card">
          <div class="card-body">
            <FullCalendar 
              :options="calendarOptions"
              class="fc-theme-bootstrap"
            />
          </div>
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
import bootstrap5Plugin from '@fullcalendar/bootstrap5';
import esLocale from '@fullcalendar/core/locales/es';

const userStore = useUserStore();
const email = userStore.email;
const viewMode = ref('list');
const reservasPendientes = ref([]);
const reservasHistoricas = ref([]);

const calendarEvents = computed(() => {
  const pendientes = reservasPendientes.value.map(reserva => ({
    title: reserva.nombreActividad || reserva.titulo,
    start: reserva.fecha,
    className: 'bg-warning border-warning',
    extendedProps: {
      status: 'pendiente'
    }
  }));

  const historicas = reservasHistoricas.value.map(reserva => ({
    title: reserva.nombreActividad,
    start: reserva.fecha,
    className: 'bg-success border-success',
    extendedProps: {
      status: 'completada'
    }
  }));

  return [...pendientes, ...historicas];
});

const calendarOptions = computed(() => ({
  plugins: [dayGridPlugin, bootstrap5Plugin],
  initialView: 'dayGridMonth',
  locale: esLocale,
  events: calendarEvents.value,
  themeSystem: 'bootstrap5',
  headerToolbar: {
    left: 'prev,next today',
    center: 'title',
    right: 'dayGridMonth,dayGridWeek'
  },
  firstDay: 1,
  height: 'auto',
  eventClick: (info) => {
    const status = info.event.extendedProps.status;
    const statusText = status === 'pendiente' ? 'Pendiente' : 'Completada';
    const statusClass = status === 'pendiente' ? 'warning' : 'success';
    
    const toast = new bootstrap.Toast(document.createElement('div'));
    toast._element.className = `toast align-items-center text-white bg-${statusClass} border-0`;
    toast._element.innerHTML = `
      <div class="d-flex">
        <div class="toast-body">
          <strong>${info.event.title}</strong><br>
          Estado: ${statusText}
        </div>
        <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
      </div>
    `;
    document.body.appendChild(toast._element);
    toast.show();
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
    const toast = new bootstrap.Toast(document.createElement('div'));
    toast._element.className = 'toast align-items-center text-white bg-danger border-0';
    toast._element.innerHTML = `
      <div class="d-flex">
        <div class="toast-body">
          Error al cargar las reservas. Por favor, inténtelo de nuevo.
        </div>
        <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
      </div>
    `;
    document.body.appendChild(toast._element);
    toast.show();
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
  fetchReservas();
});
</script>

<style>
.fc-theme-bootstrap a {
  color: inherit;
}

.toast {
  position: fixed;
  top: 1rem;
  right: 1rem;
  z-index: 1050;
}
</style>
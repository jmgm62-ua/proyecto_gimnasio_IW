<template>
  <div class="container py-4">
    <h1 class="text-center mb-4">Clases que imparto</h1>
    
    <div class="d-flex justify-content-center gap-3 mb-4">
      <button class="btn btn-lg" 
        :class="currentView === 'calendar' ? 'btn-primary' : 'btn-outline-primary'"
        @click="switchToCalendar">
        <i class="bi bi-calendar3"></i> Calendario
      </button>
      <button class="btn btn-lg" 
        :class="currentView === 'list' ? 'btn-primary' : 'btn-outline-primary'"
        @click="currentView = 'list'">
        <i class="bi bi-list-ul"></i> Lista
      </button>
    </div>

    <div v-if="currentView === 'calendar'" class="mb-4">
      <div id="calendar"></div>
      <p class="text-center mt-3 text-muted">
        <i class="bi bi-info-circle"></i> 
        Cambie al modo listado para ver los asistentes.
      </p>
    </div>

    <div v-else class="card">
      <div class="card-body">
        <div class="table-responsive">
          <table class="table table-hover">
            <thead class="table-light">
              <tr>
                <th scope="col">Nombre</th>
                <th scope="col">Fecha y Hora</th>
                <th scope="col">Acciones</th>
              </tr>
            </thead>
            <tbody>
              <template v-for="occurrence in allOccurrences" :key="occurrence.activityId + occurrence.start.getTime()">
                <tr>
                  <td>{{ occurrence.nombre }}</td>
                  <td>
                    <i class="bi bi-clock"></i>
                    {{ formatDateTime(occurrence.start) }} - {{ formatDateTime(occurrence.end) }}
                  </td>
                  <td>
                    <button @click="toggleAsistentes(occurrence)" 
                      class="btn btn-sm"
                      :class="isExpanded(occurrence) ? 'btn-secondary' : 'btn-primary'">
                      <i class="bi" :class="isExpanded(occurrence) ? 'bi-chevron-up' : 'bi-chevron-down'"></i>
                      {{ isExpanded(occurrence) ? 'Ocultar' : 'Ver' }} asistentes
                    </button>
                  </td>
                </tr>
                <tr v-if="isExpanded(occurrence)">
                  <td colspan="3" class="border-0">
                    <div class="card ms-4 mb-3">
                      <div class="card-body">
                        <h6 class="card-subtitle mb-3 text-muted">
                          <i class="bi bi-people"></i> Asistentes
                        </h6>
                        <div v-if="loadingAttendees[occurrence.activityId]" class="text-center">
                          <div class="spinner-border text-primary" role="status">
                            <span class="visually-hidden">Cargando...</span>
                          </div>
                        </div>
                        <ul v-else-if="getAttendees(occurrence).length" class="list-group">
                          <li v-for="attendee in getAttendees(occurrence)" 
                              :key="attendee.id" 
                              class="list-group-item">
                            <i class="bi bi-person"></i> {{ attendee.name }}
                          </li>
                        </ul>
                        <p v-else class="text-muted mb-0">
                          <i class="bi bi-exclamation-circle"></i> 
                          No hay asistentes registrados.
                        </p>
                      </div>
                    </div>
                  </td>
                </tr>
              </template>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from "vue";
import axios from "axios";
import { Calendar } from "@fullcalendar/core";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import { useUserStore } from "@/stores/userStore";

export default {
  name: "MisClasesMonitor",
  setup() {
    const currentView = ref('calendar');
    const calendar = ref(null);
    const userStore = useUserStore();
    const actividades = ref([]);
    const allOccurrences = ref([]);
    const expandedRows = ref(new Set());
    const attendeesMap = ref({});
    const loadingAttendees = ref({});

    const fetchActividades = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/users/getTareasMonitor?email=${userStore.email}`);
        actividades.value = response.data;
        return response.data;
      } catch (error) {
        console.error("Error al obtener actividades:", error);
        return [];
      }
    };

    const getDayIndex = (diaSemana) => {
      const days = {
        'Domingo': 0,
        'Lunes': 1,
        'Martes': 2,
        'Miércoles': 3,
        'Jueves': 4,
        'Viernes': 5,
        'Sábado': 6,
      };
      return days[diaSemana];
    };

    const getOccurrences = (activity) => {
      const start = new Date(activity.fechaInicio);
      const end = new Date(activity.fechaFin);
      const dayIndex = getDayIndex(activity.diaSemana);
      
      let current = new Date(start);
      while (current.getDay() !== dayIndex) {
        current.setDate(current.getDate() + 1);
      }
      
      const occurrences = [];
      while (current <= end) {
        const occurrenceStart = new Date(current);
        occurrenceStart.setHours(activity.horaInicio.split(':')[0]);
        occurrenceStart.setMinutes(activity.horaInicio.split(':')[1]);
        
        const occurrenceEnd = new Date(current);
        occurrenceEnd.setHours(activity.horaFin.split(':')[0]);
        occurrenceEnd.setMinutes(activity.horaFin.split(':')[1]);
        
        occurrences.push({
          activityId: activity.id,
          nombre: activity.nombre,
          start: occurrenceStart,
          end: occurrenceEnd,
        });
        
        current.setDate(current.getDate() + 7);
      }
      
      return occurrences;
    };

    const formatDateTime = (date) => {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      return `${day}/${month}/${year} ${hours}:${minutes}`;
    };

    const initCalendar = async () => {
      const actividadesData = await fetchActividades();
      const eventos = actividadesData.flatMap(activity => {
        const occurrences = getOccurrences(activity);
        return occurrences.map(occurrence => ({
          title: activity.nombre,
          start: occurrence.start.toISOString(),
          end: occurrence.end.toISOString(),
          extendedProps: activity,
        }));
      });

      if (calendar.value) {
        calendar.value.destroy();
      }
      
      const calendarEl = document.getElementById("calendar");
      if (calendarEl) {
        calendar.value = new Calendar(calendarEl, {
          plugins: [dayGridPlugin, timeGridPlugin, interactionPlugin],
          initialView: "dayGridMonth",
          headerToolbar: {
            left: "prev,next today",
            center: "title",
            right: "dayGridMonth,timeGridWeek,timeGridDay",
          },
          events: eventos,
          firstDay: 1,
          height: "auto",
          eventClick: function (info) {
            alert("Por favor, cambie al modo listado para ver los asistentes.");
          },
        });
        calendar.value.render();
      }
    };

    const switchToCalendar = () => {
      currentView.value = 'calendar';
      setTimeout(() => {
        initCalendar();
      }, 0);
    };

    const isExpanded = (occurrence) => {
      return expandedRows.value.has(`${occurrence.activityId}-${occurrence.start.getTime()}`);
    };

    const toggleAsistentes = async (occurrence) => {
      const key = `${occurrence.activityId}-${occurrence.start.getTime()}`;
      if (expandedRows.value.has(key)) {
        expandedRows.value.delete(key);
      } else {
        expandedRows.value.add(key);
        await fetchAsistentes(occurrence);
      }
    };

    const fetchAsistentes = async (occurrence) => {
      const key = `${occurrence.activityId}-${occurrence.start.getTime()}`;
      if (!attendeesMap.value[key]) {
        loadingAttendees.value[occurrence.activityId] = true;
        try {
          const response = await axios.get(
            `http://localhost:8080/reservas/actividades/${occurrence.activityId}/asistentes?date=${occurrence.start.toISOString().split('T')[0]}`
          );
          attendeesMap.value[key] = response.data;
        } catch (error) {
          console.error("Error al obtener asistentes:", error);
          attendeesMap.value[key] = [];
        } finally {
          loadingAttendees.value[occurrence.activityId] = false;
        }
      }
    };

    const getAttendees = (occurrence) => {
      const key = `${occurrence.activityId}-${occurrence.start.getTime()}`;
      return attendeesMap.value[key] || [];
    };

    onMounted(async () => {
      const actividadesData = await fetchActividades();
      allOccurrences.value = actividadesData.flatMap(getOccurrences);
      allOccurrences.value.sort((a, b) => a.start.getTime() - b.start.getTime());
      initCalendar();
    });

    return {
      currentView,
      allOccurrences,
      formatDateTime,
      toggleAsistentes,
      isExpanded,
      getAttendees,
      loadingAttendees,
      switchToCalendar
    };
  },
};
</script>

<style scoped>
#calendar {
  max-width: 900px;
  margin: 0 auto;
}

.table th {
  background-color: #f8f9fa;
}

.btn-outline-primary:hover {
  background-color: #0d6efd;
  color: white;
}
</style>
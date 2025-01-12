<template>
  <div>
    <h1>Calendario de Actividades</h1>
    <div class="buttons">
      <button @click="verTodasActividades">Ver todos los tipos de tareas</button>
      <button
        v-for="tipo in tiposActividades"
        :key="tipo"
        @click="filtrarActividades(tipo)"
      >
        {{ tipo }}
      </button>
    </div>
    <div id="calendar"></div>
  </div>
</template>

<script>
import { ref, onMounted } from "vue";
import axios from "axios";
import { Calendar } from "@fullcalendar/core";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import { useRouter } from "vue-router";
import interactionPlugin from "@fullcalendar/interaction";
import { useUserStore } from "@/stores/userStore";

export default {
  name: "CalendarioActividades",
  setup() {
    const calendar = ref(null);
    const tiposActividades = ref([]);
    const baseUrl = "http://localhost:8080";

    // Obtener tipos de actividades
    const fetchTiposActividades = async () => {
      try {
        const response = await axios.get(`${baseUrl}/reservas/tipos_actividades`);
        tiposActividades.value = response.data;
      } catch (error) {
        console.error("Error al obtener tipos de actividades:", error);
      }
    };

    // Obtener todas las actividades sin filtrar
    const fetchTodasActividades = async () => {
      try {
        const response = await axios.get(`${baseUrl}/users/getAllTareas`);
        return response.data;
      } catch (error) {
        console.error("Error al obtener todas las actividades:", error);
        return [];
      }
    };

    // Obtener actividades por tipo
    const fetchActividadesPorTipo = async (tipo) => {
      try {
        const response = await axios.get(`${baseUrl}/users/getAllTareas/${tipo}`);
        return response.data;
      } catch (error) {
        console.error(`Error al obtener actividades del tipo ${tipo}:`, error);
        return [];
      }
    };

    // Generar eventos repetitivos
    const generarEventos = (actividades) => {
      const eventos = [];
      const diasSemana = { Lunes: 1, Martes: 2, Miércoles: 3, Jueves: 4, Viernes: 5, Sábado: 6, Domingo: 0 };

      actividades.forEach((actividad) => {
        const { nombre, diaSemana, horaInicio, horaFin, fechaInicio, fechaFin } = actividad;

        // Convertir las fechas a hora local de Madrid
        const start = new Date(fechaInicio);
        const end = new Date(fechaFin);

        // Establecer la zona horaria de Madrid
        start.setHours(start.getHours() + 1); // Ajuste para Madrid (UTC +1 en invierno)
        end.setHours(end.getHours() + 1); // Ajuste para Madrid (UTC +1 en invierno)

        // Iterar desde fechaInicio hasta fechaFin para generar eventos en los días de la semana correspondientes
        for (let fecha = new Date(start); fecha <= end; fecha.setDate(fecha.getDate() + 1)) {
          if (fecha.getDay() === diasSemana[diaSemana]) {
            eventos.push({
              title: nombre,
              start: `${fecha.toISOString().split("T")[0]}T${horaInicio}:00`,
              end: `${fecha.toISOString().split("T")[0]}T${horaFin}:00`,
              extendedProps: actividad,
            });
          }
        }
      });

      return eventos;
    };



    // Inicializar calendario
    const initCalendar = (eventos) => {
      const calendarEl = document.getElementById("calendar");
      if (calendar.value) calendar.value.destroy(); // Destruir el calendario anterior si existe

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
          const actividad = info.event.extendedProps;
          useUserStore().setActividadAReservar(actividad)

          // Crear un cuadro de diálogo personalizado
          const iniciarReserva = confirm(
            `¿Deseas iniciar la reserva para: ${actividad.nombre}?`
          );

          if (iniciarReserva) {
            router.push("reservar")
          } else {
            console.log("Reserva cancelada.");
          }
        }

      });

      calendar.value.render();
    };

    // Ver todas las actividades
    const verTodasActividades = async () => {
      const actividades = await fetchTodasActividades();
      const eventos = generarEventos(actividades);
      initCalendar(eventos);
    };

    // Filtrar actividades según el tipo
    const filtrarActividades = async (tipo) => {
      const actividades = await fetchActividadesPorTipo(tipo);
      const eventos = generarEventos(actividades);
      initCalendar(eventos);
    };

    onMounted(async () => {
      await fetchTiposActividades();
      await verTodasActividades(); // Mostrar todas las actividades al cargar la página
    });

    return {
      tiposActividades,
      filtrarActividades,
      verTodasActividades,
    };
  },
};
</script>

<style scoped>
#calendar {
  max-width: 900px;
  margin: 0 auto;
}

.buttons {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-bottom: 20px;
}

button {
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>

<template>
    <div>
      <h1>Calendario de Actividades</h1>
      <div id="calendar"></div>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from "vue";
  import axios from "axios";
  import { Calendar } from "@fullcalendar/core";
  import dayGridPlugin from "@fullcalendar/daygrid";
  import timeGridPlugin from "@fullcalendar/timegrid";
  import interactionPlugin from "@fullcalendar/interaction";
  
  export default {
    name: "CalendarioActividades",
    setup() {
      const calendar = ref(null);
  
      // Obtener actividades del endpoint
      const fetchActividades = async () => {
        try {
          const response = await axios.get("http://localhost:8080/users/getAllTareas");
          console.log(response)
          return response.data;
        } catch (error) {
          console.error("Error al obtener actividades:", error);
          return [];
        }
      };
  
      // Generar eventos repetitivos
      const generarEventos = (actividades) => {
        const eventos = [];
        const diasSemana = { Lunes: 1, Martes: 2, Miércoles: 3, Jueves: 4, Viernes: 5, Sábado: 6, Domingo: 0 };
  
        actividades.forEach((actividad) => {
          const { nombre, diaSemana, horaInicio, horaFin, fechaInicio, fechaFin } = actividad;
  
          const start = new Date(fechaInicio);
          const end = new Date(fechaFin);
  
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
      const initCalendar = async () => {
        const actividades = await fetchActividades();
        const eventos = generarEventos(actividades);
  
        const calendarEl = document.getElementById("calendar");
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
            height: "auto", // Ajusta la altura automáticamente
            eventClick: function (info) {
                const actividad = info.event.extendedProps;
                alert(`Iniciar reserva para: ${actividad.nombre}`);
                console.log("Datos de actividad seleccionada:", actividad);
            },
            });

  
        calendar.value.render();
      };
  
      onMounted(() => {
        initCalendar();
      });
  
      return {};
    },
  };
  </script>
  
  <style scoped>
  #calendar {
    max-width: 900px;
    margin: 0 auto;
  }
  </style>
  
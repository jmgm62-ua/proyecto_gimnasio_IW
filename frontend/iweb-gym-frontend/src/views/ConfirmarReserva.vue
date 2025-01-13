<template>
  <div class="container">
    <h1>Confirmar Detalles de la Reserva</h1>
    <div v-if="actividad" class="detalles-actividad">
      <p><strong>Nombre de la Actividad:</strong> {{ actividad.nombre }}</p>
      <p><strong>Día de la Semana:</strong> {{ actividad.diaSemana }}</p>
      <p><strong>Horario:</strong> {{ actividad.horaInicio }} - {{ actividad.horaFin }}</p>
      <p><strong>Fecha Seleccionada:</strong> {{ userStore.fecha_seleccionada }}</p>
      <p><strong>Tipo de Actividad:</strong> {{ actividad.tipo_de_actividad }}</p>
      <p><strong>Precio:</strong> €{{ actividad.precio_extra_actividad }}</p>
    </div>
    <div v-else class="loading">
      <p>Cargando detalles de la actividad...</p>
    </div>
    <div class="buttons">
      <button class="confirm-button" @click="confirmarReserva">Confirmar Reserva</button>
      <button class="cancel-button" @click="cancelarReserva">Cancelar</button>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/userStore";

export default {
  name: "ConfirmarDetallesReserva",
  setup() {
    const actividad = ref(null);
    const router = useRouter();
    const baseUrl = "http://localhost:8080";
    const userStore = useUserStore();

    const fetchDetallesActividad = async () => {
      try {
        const idActividad = userStore.actividad_a_reservar?.id;
        if (!idActividad) {
          throw new Error("No se ha seleccionado ninguna actividad.");
        }
        const response = await axios.get(`${baseUrl}/reservas/actividad/${idActividad}`);
        actividad.value = response.data;
      } catch (error) {
        console.error("Error al obtener los detalles de la actividad:", error);
      }
    };

    const confirmarReserva = () => {
      alert(`Reserva confirmada para la actividad: ${actividad.value.nombre}`);
      router.push("/");
    };

    const cancelarReserva = () => {
      alert("Reserva cancelada.");
      router.push("/");
    };

    onMounted(() => {
      fetchDetallesActividad();
    });

    return {
      actividad,
      userStore,
      confirmarReserva,
      cancelarReserva,
    };
  },
};
</script>

<style scoped>
.container {
  max-width: 700px;
  margin: 50px auto;
  padding: 30px;
  background-color: #ffffff;
  border: 1px solid #ddd;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

h1 {
  font-size: 28px;
  color: #333;
  margin-bottom: 20px;
  font-weight: bold;
}

.detalles-actividad p {
  font-size: 18px;
  color: #555;
  margin: 10px 0;
  line-height: 1.5;
}

.loading p {
  font-size: 20px;
  color: #888;
  margin-top: 20px;
}

.buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 30px;
}

button {
  padding: 12px 20px;
  font-size: 16px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-weight: bold;
}

.confirm-button {
  background-color: #28a745;
  color: #fff;
  transition: background-color 0.3s ease;
}

.confirm-button:hover {
  background-color: #218838;
}

.cancel-button {
  background-color: #dc3545;
  color: #fff;
  transition: background-color 0.3s ease;
}

.cancel-button:hover {
  background-color: #c82333;
}

@media (max-width: 768px) {
  .container {
    margin: 20px;
    padding: 20px;
  }

  h1 {
    font-size: 24px;
  }

  .buttons {
    flex-direction: column;
    gap: 15px;
  }

  button {
    width: 100%;
  }
}
</style>

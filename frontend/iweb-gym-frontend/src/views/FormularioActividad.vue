<template>
  <div class="container my-4">
    <div class="row">
      <div class="col-md-8 offset-md-2">
        <div class="card">
          <div class="card-header">
            <h3>Nueva Actividad</h3>
          </div>
          <div class="card-body">
            <form @submit.prevent="crearActividad" class="needs-validation">
              <!-- Nombre y Día de la semana -->
              <div class="row">
                <div class="col-md-6 mb-3">
                  <label for="nombre" class="form-label">Nombre de la actividad *</label>
                  <input
                      type="text"
                      class="form-control"
                      id="nombre"
                      v-model="formData.nombre"
                      required
                  >
                </div>
                <div class="col-md-6 mb-3">
                  <label for="diaSemana" class="form-label">Día de la semana *</label>
                  <select
                      class="form-select"
                      id="diaSemana"
                      v-model="formData.diaSemana"
                      required
                  >
                    <option value="Lunes">Lunes</option>
                    <option value="Martes">Martes</option>
                    <option value="Miércoles">Miércoles</option>
                    <option value="Jueves">Jueves</option>
                    <option value="Viernes">Viernes</option>
                    <option value="Sábado">Sábado</option>
                    <option value="Domingo">Domingo</option>
                  </select>
                </div>
              </div>

              <!-- Horarios -->
              <div class="row">
                <div class="col-md-6 mb-3">
                  <label for="horaInicio" class="form-label">Hora de inicio *</label>
                  <input
                      type="time"
                      class="form-control"
                      id="horaInicio"
                      v-model="formData.horaInicio"
                      required
                  >
                </div>
                <div class="col-md-6 mb-3">
                  <label for="horaFin" class="form-label">Hora de fin *</label>
                  <input
                      type="time"
                      class="form-control"
                      id="horaFin"
                      v-model="formData.horaFin"
                      required
                  >
                </div>
              </div>

              <!-- Fechas -->
              <div class="row">
                <div class="col-md-6 mb-3">
                  <label for="fechaInicio" class="form-label">Fecha de inicio *</label>
                  <input
                      type="date"
                      class="form-control"
                      id="fechaInicio"
                      v-model="formData.fechaInicio"
                      required
                  >
                </div>
                <div class="col-md-6 mb-3">
                  <label for="fechaFin" class="form-label">Fecha de fin *</label>
                  <input
                      type="date"
                      class="form-control"
                      id="fechaFin"
                      v-model="formData.fechaFin"
                      required
                  >
                </div>
              </div>

              <!-- Tipo de actividad e Instalación -->
              <div class="row">
                <div class="col-md-4 mb-3">
                  <label for="tipoActividad" class="form-label">Tipo de actividad *</label>
                  <select
                      class="form-select"
                      id="tipoActividad"
                      v-model="formData.tipoActividadId"
                      required
                  >
                    <option value="">Seleccione un tipo</option>
                    <option v-for="tipo in tiposActividad" :key="tipo.id" :value="tipo.id">
                      {{ tipo.nombre }}
                    </option>
                  </select>
                </div>
                <div class="col-md-4 mb-3">
                  <label for="instalacion" class="form-label">Instalación *</label>
                  <select
                      class="form-select"
                      id="instalacion"
                      v-model="formData.instalacionId"
                      required
                  >
                    <option value="">Seleccione una instalación</option>
                    <option v-for="instalacion in instalaciones" :key="instalacion.id" :value="instalacion.id">
                      {{ instalacion.nombre }}
                    </option>
                  </select>
                </div>
                <div class="col-md-4 mb-3">
                  <label for="monitor" class="form-label">Monitor *</label>
                  <select
                      class="form-select"
                      id="monitor"
                      v-model="formData.monitorId"
                      required
                  >
                    <option value="">Seleccione un monitor</option>
                    <option v-for="monitor in monitores" :key="monitor.id" :value="monitor.id">
                      {{ monitor.name }}
                    </option>
                  </select>
                </div>
              </div>

              <!-- Mensaje de error/éxito -->
              <div v-if="mensaje" :class="['alert', mensaje.tipo === 'error' ? 'alert-danger' : 'alert-success', 'mt-3']">
                <i :class="mensaje.tipo === 'error' ? 'bi bi-exclamation-triangle-fill' : 'bi bi-check-circle-fill'"></i>
                {{ mensaje.texto }}
              </div>


              <!-- Botón de submit -->
              <div class="text-center mt-4">
                <button type="submit" class="btn btn-primary">
                  Crear Actividad
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      formData: {
        nombre: '',
        diaSemana: '',
        horaInicio: '',
        horaFin: '',
        fechaInicio: '',
        fechaFin: '',
        tipoActividadId: '',
        instalacionId: '',
        monitorId: ''
      },
      tiposActividad: [
        { id: 1, nombre: 'Spinning' },
        { id: 2, nombre: 'Crossfit' },
        { id: 3, nombre: 'Pilates' },
        { id: 4, nombre: 'HIIT' }
      ],
      instalaciones: [],
      monitores: [],
      mensaje: null
    };
  },

  async created() {
    await this.cargarInstalaciones();
    await this.cargarMonitores();
  },

  methods: {

    async cargarInstalaciones() {
      try {
        const response = await axios.get('http://localhost:8080/instalaciones');
        this.instalaciones = response.data;
      } catch (error) {
        console.error('Error al cargar instalaciones:', error);
        this.mensaje = {
          tipo: 'error',
          texto: 'Error al cargar las instalaciones'
        };
      }
    },

    async cargarMonitores() {
      try {
        const response = await axios.get('http://localhost:8080/monitores');
        this.monitores = response.data;
      } catch (error) {
        console.error('Error al cargar monitores:', error);
        this.mensaje = {
          tipo: 'error',
          texto: 'Error al cargar los monitores'
        };
      }
    },

    async crearActividad() {
      try {
        // Crear una copia del formData para formatear las fechas
        const formDataToSend = {
          ...this.formData,
          fechaInicio: new Date(this.formData.fechaInicio).toISOString(),
          fechaFin: new Date(this.formData.fechaFin).toISOString()
        };

        console.log('Enviando datos:', formDataToSend);

        const response = await axios.post('http://localhost:8080/actividades/crear', formDataToSend);

        if (response.status === 201) {
          this.mensaje = {
            tipo: 'success',
            texto: 'Actividad creada correctamente.'
          };
          this.limpiarFormulario();
        }
      } catch (error) {
        console.error('Error details:', error.response?.data);
        const errorMessage = error.response?.data?.message || 'Error desconocido al crear la actividad';
        this.mensaje = {
          tipo: 'error',
          texto: errorMessage
        };
      }
    },

    limpiarFormulario() {
      this.formData = {
        nombre: '',
        diaSemana: '',
        horaInicio: '',
        horaFin: '',
        fechaInicio: '',
        fechaFin: '',
        tipoActividadId: '',
        instalacionId: '',
        monitorId: ''
      };
    }
  }
};
</script>

<style scoped>
.card {
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}

.card-header {
  background-color: #1d1d1d;
  color: white;
  text-align: center;
}

.btn-primary {
  color: white;
  background-color: #1d1d1d;
  border: 2px solid #000000;
}

.btn-primary:hover {
  background-color: #000000;
  border-color: #1d1d1d;
}

.form-label {
  font-weight: 500;
}

.alert {
  margin-top: 1rem;
}
</style>
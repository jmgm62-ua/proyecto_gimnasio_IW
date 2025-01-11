<template>
  <div class="container my-4">
    <div class="row">
      <div class="col-md-6 offset-md-3">
        <div class="card">
          <div class="card-header">
            <h3>Modificar Tipo de Cuota</h3>
          </div>
          <div class="card-body">
            <div v-if="loading" class="text-center">
              <div class="spinner-border" role="status">
                <span class="sr-only">Cargando...</span>
              </div>
            </div>
            <div v-else>
              <div class="mb-4">
                <p><strong>Tipo de Cuota Actual:</strong> {{ userData.tipoCuota }}</p>
              </div>

              <div class="form-group mb-4">
                <label for="nuevaCuota">Seleccionar Nueva Cuota:</label>
                <select
                    id="nuevaCuota"
                    v-model="nuevaCuota"
                    class="form-control mt-2"
                >
                  <option value="Individual">Individual</option>
                  <option value="Familiar">Familiar</option>
                </select>
              </div>

              <div class="alert alert-info" v-if="nuevaCuota === 'Familiar'">
                Deberá presentar el libro de familia presencialmente.
              </div>

              <div class="text-center mt-4">
                <button
                    @click="actualizarCuota"
                    class="btn btn-primary"
                    :disabled="userData.tipoCuota === nuevaCuota"
                >
                  Actualizar Tipo de Cuota
                </button>
              </div>

              <div v-if="mensaje" :class="['alert', mensaje.tipo === 'error' ? 'alert-danger' : 'alert-success', 'mt-3']">
                {{ mensaje.texto }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useUserStore } from '@/stores/userStore';
import axios from 'axios';

export default {
  data() {
    return {
      userData: {},
      nuevaCuota: '',
      loading: true,
      mensaje: null
    };
  },
  computed: {
    email() {
      const userStore = useUserStore();
      return userStore.email;
    }
  },
  methods: {
    async fetchUserData() {
      try {
        const response = await axios.get(`http://localhost:8080/users/find_socio/${this.email}`);
        this.userData = response.data;
        this.nuevaCuota = this.userData.tipoCuota;
      } catch (error) {
        console.error('Error al obtener los datos del socio:', error);
        this.mensaje = {
          tipo: 'error',
          texto: 'Error al cargar los datos del usuario'
        };
      } finally {
        this.loading = false;
      }
    },
    async actualizarCuota() {
      try {
        const response = await axios.put(`http://localhost:8080/users/actualizar-cuota/${this.email}`, {
          tipoCuota: this.nuevaCuota
        });

        if (response.status === 200) {
          this.mensaje = {
            tipo: 'success',
            texto: 'Tipo de cuota actualizado correctamente'
          };
          this.userData.tipoCuota = this.nuevaCuota;

          // Actualizar el store
          const userStore = useUserStore();
          userStore.tipo_suscripcion = this.nuevaCuota;
        }
      } catch (error) {
        console.error('Error al actualizar la cuota:', error);
        this.mensaje = {
          tipo: 'error',
          texto: 'Error al actualizar el tipo de cuota'
        };
      }
    }
  },
  mounted() {
    this.fetchUserData();
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

.btn-primary:disabled {
  background-color: #6c757d;
  border-color: #6c757d;
}
</style>
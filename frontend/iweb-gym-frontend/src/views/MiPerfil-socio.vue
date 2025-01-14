<template>
  <div class="container my-4">
    <div class="row">
      <div class="col-md-6 offset-md-3">
        <div class="card">
          <div class="card-header">
            <h3>Perfil del Socio</h3>
          </div>
          <div class="card-body">
            <div v-if="loading" class="text-center">
              <div class="spinner-border" role="status">
                <span class="sr-only">Cargando...</span>
              </div>
            </div>
            <div v-else>
              <h5>Información del Socio</h5>
              <div class="row mb-3">
                <div class="col-md-4"><strong>Nombre:</strong></div>
                <div class="col-md-8">{{ userData.name }}</div>
              </div>
              <div class="row mb-3">
                <div class="col-md-4"><strong>Email:</strong></div>
                <div class="col-md-8">{{ userData.email }}</div>
              </div>
              <div class="row mb-3">
                <div class="col-md-4"><strong>Tipo de Cuota:</strong></div>
                <div class="col-md-8">{{ userData.tipoCuota }}</div>
              </div>
              <div class="row mb-3">
                <div class="col-md-4"><strong>Fecha de Alta:</strong></div>
                <div class="col-md-8">{{ userData.fechaAlta }}</div>
              </div>
              <div class="row mb-3" v-if="userData.fechaBaja">
                <div class="col-md-4"><strong>Fecha de Baja:</strong></div>
                <div class="col-md-8">{{ userData.fechaBaja }}</div>
              </div>
              
              <div class="row mb-3">
                <div class="col-md-4"><strong>Direccion:</strong></div>
                <div class="col-md-8">{{ userData.direccion }}</div>
              </div>
              <div class="row mb-3">
                <div class="col-md-4"><strong>Saldo:</strong></div>
                <div class="col-md-8">{{ userData.saldo | currency }}</div>
              </div>

              <!-- Cargar Saldo Form -->
              <div v-if="showSaldoForm" class="mt-4">
                <h5>Cargar Saldo</h5>
                <form @submit.prevent="handleSaldoSubmit">
                  <div class="form-group">
                    <label for="saldo">Saldo a cargar en €</label>
                    <input
                      type="number"
                      class="form-control"
                      id="saldo"
                      v-model="saldoToAdd"
                      required
                      min="1"
                    />
                  </div>
                  <button type="submit" class="btn btn-primary mt-3">Confirmar Carga</button>
                </form>
              </div>

              <!-- Action buttons -->
              <div class="text-center mt-4" v-if="!showSaldoForm">
                <router-link to="/mis-reservas" class="btn btn-primary">
                  Mis Reservas
                </router-link>
              </div>

              <div class="text-center mt-4">
                <button @click="toggleSaldoForm" class="btn btn-primary">
                  Cargar Saldo
                </button>
              </div>

              <div class="text-center mt-4">
                <router-link to="/modificar-cuota" class="btn btn-primary">
                  Modificar tipo de suscripción
                </router-link>
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
import { useRouter } from 'vue-router';

export default {
  data() {
    return {
      userData: {},
      loading: true,
      saldoToAdd: 0,  // Amount to be added to the balance
      showSaldoForm: false,  // Flag to show/hide the form
    };
  },
  computed: {
    email() {
      const userStore = useUserStore();
      return userStore.email; // Use the email from the Pinia store
    },
    userStore() {
      return useUserStore(); // Access the entire Pinia store
    },
  },
  methods: {
    async fetchUserData() {
      try {
        const response = await axios.get(`http://localhost:8080/users/find_socio/${this.email}`);
        this.userData = response.data;
        const userStore = useUserStore();
        userStore.tipo_suscripcion = this.userData.tipoCuota;
        userStore.direccion = this.userData.direccion;
      } catch (error) {
        console.error('Error al obtener los datos del socio:', error);
        this.userData = null;
      } finally {
        this.loading = false;
      }
    },

    toggleSaldoForm() {
      this.showSaldoForm = !this.showSaldoForm;
    },

    async handleSaldoSubmit() {
      if (this.saldoToAdd > 0) {
        try {
          const now = new Date().toLocaleString("es-ES", { timeZone: "Europe/Madrid" });
          const fechaHoraReferencia = now.replace(/[^\d]/g, "");
          const nombreReferencia = `${fechaHoraReferencia}_${this.saldoToAdd}_${this.email}`;
          
          // Registrar referencia
          await axios.get(`http://localhost:8080/users/registrar_referencia/${nombreReferencia}`);
          
          // Hacer la solicitud a Green-Sys
          const response = await axios.post(
            "https://api.green-sys.es/sales",
            {
              amount: this.saldoToAdd,
              currency: "EUR",
              description: "Carga de saldo",
              reference: nombreReferencia,
              url_callback: `http://localhost:8080/users/cargar_saldo/${nombreReferencia}/${this.saldoToAdd}/${this.email}`,
            },
            {
              headers: {
                "x-api-key": "sk_4y59mqxk26m5wokhcm",
                "Content-Type": "application/json",
              },
            }
          );
          
          // Redirigir al usuario a la URL de pago proporcionada por Green-Sys
          window.location.href = response.data.url;  // Redirige al TPV

        } catch (error) {
          console.error("Error al cargar el saldo:", error);
        }
      }
    },


  },
  mounted() {
    this.fetchUserData();
  },
  setup() {
    const router = useRouter(); // Inicializa el router aquí y lo haces accesible
    return { router };
  },
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

.card-body {
  padding: 1.5rem;
}

.row {
  margin-bottom: 1rem;
}

.btn-primary{
  color: white;
  background-color: #1d1d1d;
  border: 2px solid #000000;
}

strong {
  font-weight: 600;
}

.currency {
  font-weight: bold;
}
</style>

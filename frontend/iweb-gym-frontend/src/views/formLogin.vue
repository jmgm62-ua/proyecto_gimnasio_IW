<template>
  <div class="login-container">
    <h1>Inicio de Sesión</h1>
    <form @submit.prevent="submitLogin">
      <div class="form-group">
        <label for="email">Correo Electrónico</label>
        <input
            type="email"
            id="email"
            v-model="loginData.email"
            required
        />
      </div>
      <div class="form-group">
        <label for="password">Contraseña</label>
        <input
            type="password"
            id="password"
            v-model="loginData.password"
            required
        />
      </div>
      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>
      <button type="submit" class="btn" :disabled="isLoading">
        {{ isLoading ? 'Cargando...' : 'Iniciar Sesión' }}
      </button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      loginData: {
        email: '',
        password: '',
      },
      errorMessage: '',
      isLoading: false
    };
  },
  methods: {
    async submitLogin() {
      this.isLoading = true;
      this.errorMessage = '';

      try {
        const response = await axios.post('http://localhost:8080/api/login', this.loginData);

        // Manejar la respuesta exitosa
        if (response.data.status === 'success') {
          // Guardar el tipo de usuario si es necesario
          localStorage.setItem('userType', response.data.userType);

          // Redirigir según el tipo de usuario
          switch(response.data.userType) {
            case 'LOGIN_OK_SOCIO':
              this.$router.push('/socio-dashboard');
              break;
            case 'LOGIN_OK_MONITOR':
              this.$router.push('/monitor-dashboard');
              break;
            case 'LOGIN_OK_WEBMASTER':
              this.$router.push('/admin-dashboard');
              break;
          }
        }
      } catch (error) {
        if (error.response) {
          // El servidor respondió con un status fuera del rango 2xx
          this.errorMessage = error.response.data.error || 'Error en la autenticación';
        } else if (error.request) {
          // La petición fue hecha pero no se recibió respuesta
          this.errorMessage = 'No se pudo conectar con el servidor';
        } else {
          // Error en la configuración de la petición
          this.errorMessage = 'Error al procesar la solicitud';
        }
        console.error('Error:', error);
      } finally {
        this.isLoading = false;
      }
    },
  },
};
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: auto;
  padding: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
}

.form-group input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.error-message {
  color: red;
  margin-bottom: 1rem;
}

.btn {
  background-color: #007bff;
  color: white;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  width: 100%;
}

.btn:hover {
  background-color: #0056b3;
}

.btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>
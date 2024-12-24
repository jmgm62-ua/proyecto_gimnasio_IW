<template>
  <div class="login-container">
    <h1>Inicio de Sesión</h1>
    <form @submit.prevent="submitLogin">
      <div class="form-group">
        <label for="email">Correo Electrónico</label>
        <input
            type="email"
            id="email"
            v-model="loginData.eMail"
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
export default {
  data() {
    return {
      loginData: {
        eMail: '',
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
        const response = await fetch('/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'X-Requested-With': 'XMLHttpRequest' // Importante para tu backend
          },
          body: JSON.stringify(this.loginData),
        });

        // Si hay redirección, manejarla
        if (response.redirected) {
          window.location.href = response.url;
          return;
        }

        const data = await response.text();
        let result;
        try {
          result = JSON.parse(data);
        } catch (e) {
          // Si la respuesta no es JSON, podría ser una redirección
          if (data.includes('redirect:')) {
            const redirectUrl = data.match(/redirect:(.+)}/)[1].replace(/"/g, '');
            window.location.href = redirectUrl;
            return;
          }
          throw new Error('Formato de respuesta no válido');
        }

        if (result.error) {
          this.errorMessage = result.error;
        } else if (result.redirect) {
          window.location.href = result.redirect;
        }
      } catch (error) {
        this.errorMessage = 'Error de conexión con el servidor.';
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
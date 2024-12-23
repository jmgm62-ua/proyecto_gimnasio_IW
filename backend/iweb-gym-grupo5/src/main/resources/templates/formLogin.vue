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
      <button type="submit" class="btn">Iniciar Sesión</button>
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
    };
  },
  methods: {
    async submitLogin() {
      try {
        const response = await fetch('/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(this.loginData),
        });

        if (response.redirected) {
          // Redirigir a la URL proporcionada por el backend
          window.location.href = response.url;
        } else {
          const result = await response.json();
          if (result.error) {
            this.errorMessage = result.error;
          }
        }
      } catch (error) {
        this.errorMessage = 'Error de conexión con el servidor.';
        console.error(error);
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
}

.btn:hover {
  background-color: #0056b3;
}
</style>

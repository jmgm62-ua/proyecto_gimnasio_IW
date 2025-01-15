<template>
  <div class="app-container">
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
      <div class="container">
        <a class="navbar-brand" href="/">Gimnasio FIT</a>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav ms-auto">
            <li class="nav-item" >
              <button class="nav-link" v-if="!isLoggedIn" @click="goToRequest">Hazte Socio</button>
            </li>
            <li class="nav-item" >
              <button class="nav-link" v-if="!isLoggedIn" @click="goToLogin">Inicia sesión</button>
            </li>

            <li class="nav-item" >
              <button class="nav-link" v-if="isLoggedIn" @click="goToReservar">Realizar reserva</button>
            </li>

            <li class="nav-item" >
              <button class="nav-link" v-if="isLoggedIn" @click="goToMiPerfil">Mi perfil</button>
            </li>
            
            <li class="nav-item" >
              <button class="nav-link" v-if="isLoggedIn" @click="goToLogOut">Cierra sesion</button>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <!-- Main Content -->
    <main class="content-wrapper">
      <RouterView />
    </main>
    <!-- Footer -->
    <footer class="bg-dark text-white py-4">
      <div class="container text-center">
        <p>&copy; 2024 Gimnasio FIT. Todos los derechos reservados.</p>
        <a href="/politica-privacidad" class="text-white">Política de Privacidad</a>
      </div>
    </footer>
  </div>
</template>

<script>
import { useUserStore } from "@/stores/userStore";
import { useRouter } from "vue-router";
import { computed } from "vue";

export default {
  name: "App",
  setup() {
    const router = useRouter();
    const userStore = useUserStore();
    const isLoggedIn = computed(() => userStore.isLoggedIn);

    const goToLogin = () => {
      router.push("/login");
    };

    const goToLogOut = () => {
      userStore.logOut();
      router.push("/")
    };
    
    const goToReservar = () => {
      router.push("/all-actividades")
    };

    const goToMiPerfil = () => {
      switch (userStore.userType) {
        case 'LOGIN_OK_SOCIO':
          router.push('/socio-profile')
          break;
        case 'LOGIN_OK_WEBMASTER':
          router.push('/webmaster-profile')
          break;
        case 'LOGIN_OK_MONITOR':
          router.push('/monitor-profile')
          break;
      }
    };
    const goToRequest = () => {
      userStore.logOut();
      router.push("/solicitud-socio")
    };

    return {
      isLoggedIn,
      goToLogin,
      goToLogOut,
      goToMiPerfil,
      goToRequest,
      goToReservar,
    };
  },
};
</script>

<style scoped>
/* Layout styles */
.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.content-wrapper {
  flex: 1 0 auto;
}

/* Navbar styles */
.navbar-dark .navbar-brand {
  font-size: 1.5rem;
  font-weight: bold;
}

.navbar-dark .nav-link {
  color: rgba(255, 255, 255, 0.8);
  font-size: 1rem;
  transition: color 0.3s;
}

.navbar-dark .nav-link:hover {
  color: white;
}

.navbar-dark .btn-primary {
  border-radius: 25px;
}

/* Hero styles */
.hero {
  height: 100vh;
  position: relative;
  overflow: hidden;
}

.hero-overlay {
  z-index: 1;
}

.hero .container {
  z-index: 2;
  position: relative;
}

.card {
  border: none;
}

.card-body {
  padding: 2rem;
}
</style>
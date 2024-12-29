<template>
  <div>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
      <div class="container">
        <a class="navbar-brand" href="#">Gimnasio FIT</a>
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
            <li class="nav-item">
              <a class="nav-link" href="#instalaciones">Instalaciones</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#tarifas">Tarifas</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#hazte-socio">Hazte Socio</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <!-- Main Content -->
    <div class="content-wrapper">
      <RouterView />
    </div>

    <!-- Footer -->
    <footer class="bg-dark text-white py-4 mt-auto">
      <div class="container text-center">
        <p>&copy; 2024 Gimnasio FIT. Todos los derechos reservados.</p>
        <a href="/politica-privacidad" class="text-white">Política de Privacidad</a>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>


<style scoped>
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
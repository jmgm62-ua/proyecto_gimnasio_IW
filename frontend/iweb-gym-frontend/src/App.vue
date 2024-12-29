<template>
  <div id="app">
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm fixed-top">
      <div class="container-fluid">
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
.navbar {
  z-index: 1000;
  border-bottom: 2px solid #333;
}

.container-fluid {
  max-width: 100%;
  padding-left: 0;
  padding-right: 0;
}

.navbar-brand {
  font-size: 1.5rem;
  font-weight: bold;
}

.nav-link {
  font-size: 1.2rem;
}

/* Content wrapper style */
.content-wrapper {
  flex: 1;
  background-color: #f8f9fa; /* Light background color for content */
  padding-top: 80px; /* Give some space under navbar */
  padding-bottom: 60px; /* Space above footer */
  min-height: 100vh;
  border: 2px solid #007bff; /* Border to distinguish the content */
  margin: 0 20px;
}

/* Footer styles */
footer {
  width: 100%;
  position: relative;
  bottom: 0;
  left: 0;
}

body {
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

footer {
  border-top: 2px solid #333;
}

footer a {
  color: white;
  text-decoration: none;
}

footer a:hover {
  text-decoration: underline;
}
</style>

<template>
  <div>
    <!-- Hero Section -->
    <header class="hero bg-primary text-white text-center py-5">
      <div class="container">
        <h1>Bienvenidos a Gimnasio FIT</h1>
        <p class="lead">Tu salud, nuestro compromiso.</p>
      </div>
    </header>

    <!-- Instalaciones -->
    <section id="instalaciones" class="py-5">
      <div class="container">
        <h2 class="text-center mb-4">Nuestras Instalaciones</h2>
        <div class="row justify-content-center">
          <div class="col-md-4 mb-3">
            <div class="placeholder bg-secondary text-white d-flex align-items-center justify-content-center" style="height: 200px;">
              Sala de Pesas
            </div>
            <p class="text-center mt-2">Sala de Pesas</p>
          </div>
          <div class="col-md-4 mb-3">
            <div class="placeholder bg-secondary text-white d-flex align-items-center justify-content-center" style="height: 200px;">
              Zona de Cardio
            </div>
            <p class="text-center mt-2">Zona de Cardio</p>
          </div>
          <div class="col-md-4 mb-3">
            <div class="placeholder bg-secondary text-white d-flex align-items-center justify-content-center" style="height: 200px;">
              Piscina
            </div>
            <p class="text-center mt-2">Piscina</p>
          </div>
        </div>
      </div>
    </section>

    <!-- Tarifas -->
    <section id="tarifas" class="bg-light py-5">
      <div class="container">
        <h2 class="text-center mb-4">Tipos de Tarifas</h2>
        <div class="row justify-content-center">
          <div class="col-md-4">
            <div class="card">
              <div class="card-body text-center">
                <h5 class="card-title">Básica</h5>
                <p class="card-text">Acceso a zona de pesas y cardio.</p>
                <p class="card-text"><strong>$30/mes</strong></p>
              </div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="card">
              <div class="card-body text-center">
                <h5 class="card-title">Premium</h5>
                <p class="card-text">Incluye acceso a todas las áreas e instalaciones.</p>
                <p class="card-text"><strong>$50/mes</strong></p>
              </div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="card">
              <div class="card-body text-center">
                <h5 class="card-title">Familiar</h5>
                <p class="card-text">Acceso para hasta 4 miembros de la familia.</p>
                <p class="card-text"><strong>$80/mes</strong></p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Hazte Socio -->
    <section id="hazte-socio" class="py-5">
      <div class="container text-center">
        <h2 class="mb-4">Hazte Socio</h2>
        <p>Únete a nuestra comunidad y mejora tu calidad de vida.</p>
        <a href="/formulario-alta" class="btn btn-primary">Formulario de Inscripción</a>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: "HomeView",
};
</script>

<style scoped>
.hero {
  background-color: #007bff;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  height: 300px;
}

.placeholder {
  font-size: 1.5rem;
  font-weight: bold;
  text-transform: uppercase;
}

.card {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
}

.card-body {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.row.justify-content-center {
  justify-content: center;
}
</style>

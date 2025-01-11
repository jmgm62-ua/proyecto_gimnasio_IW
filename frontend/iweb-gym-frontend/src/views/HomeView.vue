<template>
  <div>
    <!-- Hero Section -->
    <header class="hero position-relative text-center text-white d-flex align-items-center justify-content-center">
      <div class="hero-overlay position-absolute w-100 h-100" style="background: rgba(0, 0, 0, 0.5);"></div>
      <img 
        src="/images.jpeg" 
        alt="Hero background" 
        class="position-absolute w-100 h-100 object-fit-cover">
      <div class="container position-relative">
        <h1 class="display-3">Bienvenidos a Gimnasio FIT</h1>
        <p class="lead">Tu salud, nuestro compromiso.</p>
        <a href="#hazte-socio" class="btn btn-primary btn-lg mt-3">Únete Ahora</a>
      </div>
    </header>

    <!-- Instalaciones -->
    <section id="instalaciones" class="py-5 bg-light">
      <div class="container">
        <h2 class="text-center mb-5">Nuestras Instalaciones</h2>
        <div class="row">
          <div class="col-md-4">
            <div class="card shadow-sm">
              <img 
                src="/salamaquinas.jpg" 
                class="card-img-top uniform-image" 
                alt="Sala de Pesas">
              <div class="card-body text-center">
                <h5 class="card-title">Sala de máquinas</h5>
              </div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="card shadow-sm">
              <img 
                src="/salaciclos.jpg" 
                class="card-img-top uniform-image" 
                alt="Zona de Cardio">
              <div class="card-body text-center">
                <h5 class="card-title">Sala de ciclos</h5>
              </div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="card shadow-sm">
              <img 
                src="/personaltrainer.jpg" 
                class="card-img-top uniform-image" 
                alt="Piscina">
              <div class="card-body text-center">
                <h5 class="card-title">Salas con personal trainers</h5>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    

    <!-- Tarifas -->
    <section id="tarifas" class="py-5">
      <div class="container">
        <h2 class="text-center mb-5">Tipos de Tarifas</h2>
        <div class="row justify-content-center">
          <div class="col-md-4 d-flex">
            <div class="card shadow-sm w-100 text-center">
              <div class="card-body">
                <h5 class="card-title">Individual</h5>
                <p class="card-text">Acceso a todas las instalaciones</p>
                <p class="card-text text-primary"><strong>$30/mes</strong></p>
              </div>
            </div>
          </div>
    
          <div class="col-md-4 d-flex">
            <div class="card shadow-sm w-100 text-center">
              <div class="card-body">
                <h5 class="card-title">Familiar</h5>
                <p class="card-text">Accede a todas las instalaciones acompañado de hasta 3 familiares</p>
                <p class="card-text text-primary"><strong>$60/mes</strong></p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    

    <!-- Hazte Socio -->
    <section id="hazte-socio" class="bg-primary text-white py-5">
      <div class="container text-center">
        <h2 class="mb-4">Hazte Socio</h2>
        <p>Únete a nuestra comunidad y mejora tu calidad de vida.</p>
        <a href="/solicitud-socio" class="btn btn-light btn-lg">Formulario de Inscripción</a>
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
  height: 100%; /* Makes all cards stretch equally */
  display: flex;
  flex-direction: column;
  justify-content: center; /* Ensures content is vertically centered */
}

.card-body {
  padding: 2rem; /* Adds some spacing for better aesthetics */
}


.uniform-image {
  height: 200px;
  object-fit: cover;
  width: 100%; 
  border-radius: 0.25rem;
}
</style>

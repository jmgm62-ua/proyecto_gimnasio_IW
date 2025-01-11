<template>
  <div class="container my-4">
    <div class="row">
      <div class="col-md-8 offset-md-2">
        <div class="card">
          <div class="card-header">
            <h3>Solicitu de Nuevo Socio</h3>
          </div>
          <div class="card-body">
            <form @submit.prevent="registrarSocio" class="needs-validation">
              <div class="row">
                <div class="col-md-6 mb-3">
                  <label for="nombre" class="form-label">Nombre completo *</label>
                  <input
                      type="text"
                      class="form-control"
                      id="nombre"
                      v-model="formData.name"
                      required
                  >
                </div>

                <div class="col-md-6 mb-3">
                  <label for="email" class="form-label">Email *</label>
                  <input
                      type="email"
                      class="form-control"
                      id="email"
                      v-model="formData.email"
                      required
                  >
                </div>
              </div>

              <div class="row">
                <div class="col-md-6 mb-3">
                  <label for="password" class="form-label">Contraseña *</label>
                  <input
                      type="password"
                      class="form-control"
                      id="password"
                      v-model="formData.password"
                      required
                  >
                </div>

                <div class="col-md-6 mb-3">
                  <label for="fechaNacimiento" class="form-label">Fecha de Nacimiento *</label>
                  <input
                      type="date"
                      class="form-control"
                      id="fechaNacimiento"
                      v-model="formData.fechaNacimiento"
                      required
                  >
                </div>
              </div>

              <div class="row">
                <div class="col-md-6 mb-3">
                  <label for="telefono" class="form-label">Teléfono *</label>
                  <input
                      type="tel"
                      class="form-control"
                      id="telefono"
                      v-model="formData.telefono"
                      required
                  >
                </div>

                <div class="col-md-6 mb-3">
                  <label for="direccion" class="form-label">Dirección *</label>
                  <input
                      type="text"
                      class="form-control"
                      id="direccion"
                      v-model="formData.direccion"
                      required
                  >
                </div>
              </div>

              <div class="row">
                <div class="col-md-6 mb-3">
                  <label for="tipoCuota" class="form-label">Tipo de Cuota *</label>
                  <select
                      class="form-select"
                      id="tipoCuota"
                      v-model="formData.tipoCuota"
                      required
                  >
                    <option value="Individual">Individual</option>
                    <option value="Familiar">Familiar</option>
                  </select>
                </div>
              </div>

              <div v-if="mensaje" :class="['alert', mensaje.tipo === 'error' ? 'alert-danger' : 'alert-success', 'mt-3']">
                {{ mensaje.texto }}
              </div>

              <div class="text-center mt-4">
                <button type="submit" class="btn btn-primary">
                  Registrar Solicitud
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      formData: {
        name: '',
        email: '',
        password: '',
        fechaNacimiento: '',
        telefono: '',
        direccion: '',
        tipoCuota: 'Individual',
      },
      mensaje: null
    };
  },
  methods: {
    async registrarSocio() {
      try {
        const fechaActual = new Date().toISOString().split('T')[0];

        const socioData = {
          ...this.formData,
          activo: false,
          fechaAlta: fechaActual,
          fechaBaja: null,
          saldo: 0
        };
        console.log(socioData);

        const response = await axios.post('http://localhost:8080/users/registro-socio', socioData);

        if (response.status === 201) {
          this.mensaje = {
            tipo: 'success',
            texto: 'Solicitud enviada correctamente. Un administrador revisará tu solicitud.'
          };
          // Limpiar formulario
          this.formData = {
            name: '',
            email: '',
            password: '',
            fechaNacimiento: '',
            telefono: '',
            direccion: '',
            tipoCuota: 'Individual',
          };
        }
      } catch (error) {
        this.mensaje = {
          tipo: 'error',
          texto: 'Error al enviar la solicitud: ' + (error.response?.data || error.message)
        };
      }
    }
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

.form-label {
  font-weight: 500;
}
</style>
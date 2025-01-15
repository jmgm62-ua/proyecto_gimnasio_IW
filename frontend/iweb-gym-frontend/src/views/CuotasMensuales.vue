<template>
    <div class="container py-4">
      <div class="row justify-content-center">
        <div class="col-12 col-lg-8">
          <!-- Header Section -->
          <div class="d-flex justify-content-between align-items-center mb-4">
            <h1 class="h2 mb-0">
              <i class="bi bi-currency-euro"></i> Gestión de Cuotas
            </h1>
            <button 
              class="btn btn-primary" 
              @click="showNewQuotaModal = true"
            >
              <i class="bi bi-plus-lg"></i> Nueva Cuota
            </button>
          </div>
  
          <!-- Cuotas List -->
          <div class="card shadow-sm">
            <div class="card-body">
              <div v-if="loading" class="text-center py-4">
                <div class="spinner-border text-primary" role="status">
                  <span class="visually-hidden">Cargando...</span>
                </div>
              </div>
  
              <div v-else-if="cuotas.length === 0" class="text-center py-4">
                <i class="bi bi-inbox display-1 text-muted"></i>
                <p class="text-muted mt-3">No hay cuotas registradas</p>
              </div>
  
              <div v-else class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                  <thead class="table-light">
                    <tr>
                      <th>Nombre</th>
                      <th class="text-end">Precio</th>
                      <th class="text-center">Acciones</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="cuota in cuotas" :key="cuota.id">
                      <td>{{ cuota.tipoSuscripcion }}</td>
                      <td class="text-end">{{ formatPrice(cuota.price) }} €</td>
                      <td class="text-center">
                        <button 
                          class="btn btn-danger btn-sm"
                          @click="confirmarEliminacion(cuota)"
                        >
                          Eliminar
                        </button>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
  
      <!-- Modal Nueva Cuota -->
      <div 
        class="modal fade" 
        :class="{ 'show': showNewQuotaModal }" 
        tabindex="-1"
        :style="{ display: showNewQuotaModal ? 'block' : 'none' }"
      >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">
                <i class="bi bi-plus-circle"></i> Nueva Cuota
              </h5>
              <button 
                type="button" 
                class="btn-close" 
                @click="closeNewQuotaModal"
              ></button>
            </div>
            <form @submit.prevent="agregarCuota">
              <div class="modal-body">
                <div class="mb-3">
                  <label for="nombreCuota" class="form-label">Nombre</label>
                  <input
                    type="text"
                    class="form-control"
                    id="nombreCuota"
                    v-model="nuevaCuota.tipoSuscripcion"
                    required
                    :class="{ 'is-invalid': validationErrors.nombre }"
                  >
                  <div class="invalid-feedback">{{ validationErrors.nombre }}</div>
                </div>
                <div class="mb-3">
                  <label for="precioCuota" class="form-label">Precio (€)</label>
                  <input
                    type="number"
                    class="form-control"
                    id="precioCuota"
                    v-model="nuevaCuota.price"
                    step="0.01"
                    min="0"
                    required
                    :class="{ 'is-invalid': validationErrors.precio }"
                  >
                  <div class="invalid-feedback">{{ validationErrors.precio }}</div>
                </div>
              </div>
              <div class="modal-footer">
                <button 
                  type="button" 
                  class="btn btn-secondary" 
                  @click="closeNewQuotaModal"
                >
                  Cancelar
                </button>
                <button 
                  type="submit" 
                  class="btn btn-primary"
                  :disabled="submitting"
                >
                  <span 
                    v-if="submitting" 
                    class="spinner-border spinner-border-sm me-1"
                  ></span>
                  Guardar
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
  
      <!-- Modal Confirmar Eliminación -->
      <div 
        class="modal fade" 
        :class="{ 'show': showDeleteModal }" 
        tabindex="-1"
        :style="{ display: showDeleteModal ? 'block' : 'none' }"
      >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title text-danger">
                <i class="bi bi-exclamation-triangle"></i> Confirmar Eliminación
              </h5>
              <button 
                type="button" 
                class="btn-close" 
                @click="showDeleteModal = false"
              ></button>
            </div>
            <div class="modal-body">
              ¿Está seguro de que desea eliminar la cuota 
              <strong>{{ cuotaAEliminar?.nombre }}</strong>?
              <br>
              Esta acción no se puede deshacer.
            </div>
            <div class="modal-footer">
              <button 
                type="button" 
                class="btn btn-secondary" 
                @click="showDeleteModal = false"
              >
                Cancelar
              </button>
              <button 
                type="button" 
                class="btn btn-danger"
                @click="eliminarCuota"
                :disabled="submitting"
              >
                <span 
                  v-if="submitting" 
                  class="spinner-border spinner-border-sm me-1"
                ></span>
                Eliminar
              </button>
            </div>
          </div>
        </div>
      </div>
  
      <!-- Backdrop for modals -->
      <div 
        class="modal-backdrop fade show" 
        v-if="showNewQuotaModal || showDeleteModal"
      ></div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  
  // Estado
  const cuotas = ref([]);
  const loading = ref(true);
  const submitting = ref(false);
  const showNewQuotaModal = ref(false);
  const showDeleteModal = ref(false);
  const cuotaAEliminar = ref(null);
  const nuevaCuota = ref({
    nombre: '',
    precio: ''
  });
  const validationErrors = ref({});
  
  // Funciones
  const fetchCuotas = async () => {
    try {
      loading.value = true;
      const response = await axios.get('http://localhost:8080/suscripciones');
      cuotas.value = response.data;
    } catch (error) {
      mostrarError('Error al cargar las cuotas');
    } finally {
      loading.value = false;
    }
  };
  
  const agregarCuota = async () => {
    try {
      validationErrors.value = {};
      
      // Validaciones
      if (!nuevaCuota.value.tipoSuscripcion?.trim()) {
        validationErrors.value.nombre = 'El nombre es requerido';
        return;
      }
      if (!nuevaCuota.value.price || nuevaCuota.value.price <= 0) {
        validationErrors.value.price = 'El precio debe ser mayor que 0';
        return;
      }
  
      submitting.value = true;
      await axios.post('http://localhost:8080/suscripciones', nuevaCuota.value);
      await fetchCuotas();
      closeNewQuotaModal();
      mostrarExito('Cuota añadida correctamente');
    } catch (error) {
      mostrarError('Error al añadir la cuota');
    } finally {
      submitting.value = false;
    }
  };
  
  const confirmarEliminacion = (cuota) => {
    cuotaAEliminar.value = cuota;
    showDeleteModal.value = true;
  };
  
  const eliminarCuota = async () => {
    try {
      submitting.value = true;
      await axios.delete(`http://localhost:8080/suscripciones/${cuotaAEliminar.value.id}`);
      await fetchCuotas();
      showDeleteModal.value = false;
      mostrarExito('Cuota eliminada correctamente');
    } catch (error) {
      mostrarError('Error al eliminar la cuota');
    } finally {
      submitting.value = false;
    }
  };
  
  const closeNewQuotaModal = () => {
    showNewQuotaModal.value = false;
    nuevaCuota.value = { nombre: '', precio: '' };
    validationErrors.value = {};
  };
  
  const formatPrice = (price) => {
    return Number(price).toLocaleString('es-ES', {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    });
  };
  
  const mostrarExito = (mensaje) => {
    const toast = new bootstrap.Toast(document.createElement('div'));
    toast._element.className = 'toast align-items-center text-white bg-success border-0';
    toast._element.innerHTML = `
      <div class="d-flex">
        <div class="toast-body">
          <i class="bi bi-check-circle me-2"></i>${mensaje}
        </div>
        <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
      </div>
    `;
    document.body.appendChild(toast._element);
    toast.show();
  };
  
  const mostrarError = (mensaje) => {
    const toast = new bootstrap.Toast(document.createElement('div'));
    toast._element.className = 'toast align-items-center text-white bg-danger border-0';
    toast._element.innerHTML = `
      <div class="d-flex">
        <div class="toast-body">
          <i class="bi bi-exclamation-circle me-2"></i>${mensaje}
        </div>
        <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
      </div>
    `;
    document.body.appendChild(toast._element);
    toast.show();
  };
  
  // Inicialización
  onMounted(() => {
    fetchCuotas();
  });
  </script>
  
  <style scoped>
  .modal.show {
    background-color: rgba(0, 0, 0, 0.5);
  }
  
  .toast {
    position: fixed;
    top: 1rem;
    right: 1rem;
    z-index: 1050;
  }
  </style>
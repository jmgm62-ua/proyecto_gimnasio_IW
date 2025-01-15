<template>
  <div class="container p-4">
    <h1 class="display-4 mb-4">Informes</h1>
    
    <div class="card p-3">
      <!-- Selección de tipo de informe -->
      <div class="mb-3">
        <label class="form-label">Tipo de Informe</label>
        <select v-model="tipoInforme" class="form-select">
          <option value="asistencia">Asistencia a actividades</option>
          <option value="socios">Socios totales</option>
          <option value="altas">Nuevas altas</option>
          <option value="ingresos">Ingresos</option>
        </select>
      </div>
      
      <!-- Selección de periodo -->
      <div class="mb-3">
        <label class="form-label">Periodo</label>
        <select v-model="tipoPeriodo" class="form-select">
          <option value="mensual">Mensual</option>
          <option value="anual">Anual</option>
        </select>
      </div>
      
      <!-- Selección de mes (si es mensual) -->
      <div v-if="tipoPeriodo === 'mensual'" class="mb-3">
        <label class="form-label">Mes</label>
        <select v-model="mes" class="form-select">
          <option v-for="(mes, index) in meses" :key="index" :value="index + 1">
            {{ mes }}
          </option>
        </select>
      </div>
      
      <!-- Selección de año -->
      <div class="mb-3">
        <label class="form-label">Año</label>
        <select v-model="año" class="form-select">
          <option v-for="año in años" :key="año" :value="año">
            {{ año }}
          </option>
        </select>
      </div>
      
      <button @click="generarInforme" class="btn btn-primary">Generar Informe</button>
    </div>
    
    <!-- Resultados del informe -->
    <div v-if="informe" class="mt-4">
      <h2 class="h4 mt-4 mb-3">Resultados del Informe</h2>
      
      <!-- Información del periodo -->
      <div class="mb-4">
        <h3 class="h5 mb-2">Periodo:</h3>
        <p>{{ tipoPeriodo === 'mensual' ? 
          `${meses[mes - 1]} ${año}` : 
          `Año ${año}` }}</p>
      </div>
      
      <!-- Informe de Asistencia -->
      <div v-if="tipoInforme === 'asistencia'" class="mb-4">
        <div class="card p-3 mb-3">
          <h3 class="h5 mb-3">Resumen de Asistencia</h3>
          <p class="mb-3">Total de Asistentes: {{ informe.totalAsistentes }}</p>
          
          <!-- Reservas por actividad -->
          <div class="mb-3">
            <h4 class="h6 mb-2">Reservas por Actividad:</h4>
            <div class="bg-light p-3 rounded">
              <div v-for="(cantidad, actividad) in informe.reservasPorActividad" 
                   :key="actividad" 
                   class="mb-2">
                <span class="fw-bold">{{ actividad }}:</span> {{ cantidad }} reservas
              </div>
            </div>
          </div>
          
          <!-- Tabla de asistentes -->
          <div>
            <h4 class="h6 mb-2">Detalles de Asistentes:</h4>
            <div class="overflow-x-auto">
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Nombre</th>
                    <th scope="col">Email</th>
                    <th scope="col">Número de Reservas</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="asistente in informe.detallesAsistentes" :key="asistente.id">
                    <td>{{ asistente.id }}</td>
                    <td>{{ asistente.nombre }}</td>
                    <td>{{ asistente.email }}</td>
                    <td class="text-center">{{ asistente.numeroReservas }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      
      <!-- Informe de Socios -->
      <div v-if="tipoInforme === 'socios'" class="mb-4">
        <div class="card p-3 mb-3">
          <h3 class="h5 mb-3">Resumen de Socios</h3>
          <p class="mb-3">Total de Socios: {{ informe.totalSocios }}</p>
          
          <!-- Distribución por tipo de cuota -->
          <div class="mb-3">
            <h4 class="h6 mb-2">Distribución por Tipo de Cuota:</h4>
            <div class="bg-light p-3 rounded">
              <div v-for="(cantidad, tipo) in informe.sociosPorCuota" 
                   :key="tipo" 
                   class="mb-2">
                <span class="fw-bold">{{ tipo }}:</span> {{ cantidad }} socios
              </div>
            </div>
          </div>
          
          <!-- Tabla de socios -->
          <div>
            <h4 class="h6 mb-2">Listado de Socios:</h4>
            <div class="overflow-x-auto">
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Nombre</th>
                    <th scope="col">Email</th>
                    <th scope="col">Fecha Alta</th>
                    <th scope="col">Tipo Cuota</th>
                    <th scope="col">Saldo</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="socio in informe.detallesSocios" :key="socio.id">
                    <td>{{ socio.id }}</td>
                    <td>{{ socio.nombre }}</td>
                    <td>{{ socio.email }}</td>
                    <td>{{ socio.fechaAlta }}</td>
                    <td>{{ socio.tipoCuota }}</td>
                    <td>{{ socio.saldo }}€</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      
      <!-- Informe de Altas -->
      <div v-if="tipoInforme === 'altas'" class="mb-4">
        <div class="card p-3 mb-3">
          <h3 class="h5 mb-3">Resumen de Nuevas Altas</h3>
          <p class="mb-3">Total de Nuevas Altas: {{ informe.totalNuevasAltas }}</p>
          
          <!-- Tabla de nuevas altas -->
          <div>
            <h4 class="h6 mb-2">Detalle de Nuevas Altas:</h4>
            <div class="overflow-x-auto">
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Nombre</th>
                    <th scope="col">Email</th>
                    <th scope="col">Teléfono</th>
                    <th scope="col">Fecha Alta</th>
                    <th scope="col">Tipo Cuota</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="alta in informe.detallesAltas" :key="alta.id">
                    <td>{{ alta.id }}</td>
                    <td>{{ alta.nombre }}</td>
                    <td>{{ alta.email }}</td>
                    <td>{{ alta.telefono }}</td>
                    <td>{{ alta.fechaAlta }}</td>
                    <td>{{ alta.tipoCuota }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      
      <!-- Informe de Ingresos -->
      <div v-if="tipoInforme === 'ingresos'" class="mb-4">
        <div class="card p-3 mb-3">
          <h3 class="h5 mb-3">Resumen de Ingresos</h3>
          <p class="mb-3">Total de Ingresos: {{ informe.totalIngresos }}€</p>
          
          <!-- Tabla de ingresos -->
          <div>
            <h4 class="h6 mb-2">Detalle de Ingresos:</h4>
            <div class="overflow-x-auto">
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Concepto</th>
                    <th scope="col">Fecha</th>
                    <th scope="col">Importe</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="ingreso in informe.ingresos" :key="ingreso.id">
                    <td>{{ ingreso.id }}</td>
                    <td>{{ ingreso.referencia }}</td>
                    <td>{{ ingreso.fecha }}</td>
                    <td>{{ ingreso.cantidad }}€</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import axios from 'axios';

export default {
  name: 'VerInformes',
  setup() {
    const tipoInforme = ref('asistencia');
    const tipoPeriodo = ref('mensual');
    const mes = ref(new Date().getMonth() + 1);
    const año = ref(new Date().getFullYear());
    const informe = ref(null);

    const meses = [
      'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
      'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
    ];

    const años = Array.from(
      { length: 5 },
      (_, i) => new Date().getFullYear() - 2 + i
    );

    const generarInforme = async () => {
      try {
        const params = {
          tipo: tipoPeriodo.value,
          año: año.value
        };
        
        if (tipoPeriodo.value === 'mensual') {
          params.mes = mes.value;
        }

        const response = await axios.get(
          `http://localhost:8080/informes/${tipoInforme.value}`,
          { params }
        );

        informe.value = response.data;
      } catch (error) {
        console.error('Error al generar informe:', error);
      }
    };

    return {
      tipoInforme,
      tipoPeriodo,
      mes,
      año,
      meses,
      años,
      informe,
      generarInforme
    };
  }
};
</script>
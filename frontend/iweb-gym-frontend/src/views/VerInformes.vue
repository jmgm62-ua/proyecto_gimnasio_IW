<template>
  <div class="container mx-auto p-4">
    <h1 class="text-2xl font-bold mb-6">Informes</h1>
    
    <div class="bg-white rounded-lg shadow p-6">
      <!-- Selección de tipo de informe -->
      <div class="mb-4">
        <label class="block text-sm font-medium mb-2">Tipo de Informe</label>
        <select v-model="tipoInforme" class="w-full p-2 border rounded">
          <option value="asistencia">Asistencia a actividades</option>
          <option value="socios">Socios totales</option>
          <option value="altas">Nuevas altas</option>
          <option value="ingresos">Ingresos</option>
        </select>
      </div>

      <!-- Selección de periodo -->
      <div class="mb-4">
        <label class="block text-sm font-medium mb-2">Periodo</label>
        <select v-model="tipoPeriodo" class="w-full p-2 border rounded">
          <option value="mensual">Mensual</option>
          <option value="anual">Anual</option>
        </select>
      </div>

      <!-- Selección de mes (si es mensual) -->
      <div v-if="tipoPeriodo === 'mensual'" class="mb-4">
        <label class="block text-sm font-medium mb-2">Mes</label>
        <select v-model="mes" class="w-full p-2 border rounded">
          <option v-for="(mes, index) in meses" :key="index" :value="index + 1">
            {{ mes }}
          </option>
        </select>
      </div>

      <!-- Selección de año -->
      <div class="mb-4">
        <label class="block text-sm font-medium mb-2">Año</label>
        <select v-model="año" class="w-full p-2 border rounded">
          <option v-for="año in años" :key="año" :value="año">
            {{ año }}
          </option>
        </select>
      </div>

      <button 
        @click="generarInforme"
        class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
        Generar Informe
      </button>
    </div>

    <!-- Resultados del informe -->
    <div v-if="informe" class="mt-6 bg-white rounded-lg shadow p-6">
      <h2 class="text-xl font-bold mb-4">Resultados del Informe</h2>
      
      <!-- Información del periodo -->
      <div class="mb-6">
        <h3 class="font-bold text-lg mb-2">Periodo:</h3>
        <p>{{ tipoPeriodo === 'mensual' ? 
          `${meses[mes - 1]} ${año}` : 
          `Año ${año}` }}</p>
      </div>

      <!-- Informe de Asistencia -->
      <div v-if="tipoInforme === 'asistencia'" class="space-y-6">
        <div>
          <h3 class="font-bold text-lg mb-2">Resumen de Asistencia</h3>
          <p class="mb-2">Total de Asistentes: {{ informe.totalAsistentes }}</p>
          
          <!-- Reservas por actividad -->
          <div class="mt-4">
            <h4 class="font-semibold mb-2">Reservas por Actividad:</h4>
            <div class="bg-gray-50 p-4 rounded">
              <div v-for="(cantidad, actividad) in informe.reservasPorActividad" 
                   :key="actividad" 
                   class="mb-2">
                <span class="font-medium">{{ actividad }}:</span> {{ cantidad }} reservas
              </div>
            </div>
          </div>

          <!-- Tabla de asistentes -->
          <div class="mt-6">
            <h4 class="font-semibold mb-2">Detalles de Asistentes:</h4>
            <div class="overflow-x-auto">
              <table class="min-w-full bg-white border">
                <thead>
                  <tr class="bg-gray-100">
                    <th class="p-2 border">ID</th>
                    <th class="p-2 border">Nombre</th>
                    <th class="p-2 border">Email</th>
                    <th class="p-2 border">Número de Reservas</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="asistente in informe.detallesAsistentes" :key="asistente.id">
                    <td class="p-2 border">{{ asistente.id }}</td>
                    <td class="p-2 border">{{ asistente.nombre }}</td>
                    <td class="p-2 border">{{ asistente.email }}</td>
                    <td class="p-2 border text-center">{{ asistente.numeroReservas }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>

      <!-- Informe de Socios -->
      <div v-if="tipoInforme === 'socios'" class="space-y-6">
        <div>
          <h3 class="font-bold text-lg mb-2">Resumen de Socios</h3>
          <p class="mb-2">Total de Socios: {{ informe.totalSocios }}</p>

          <!-- Distribución por tipo de cuota -->
          <div class="mt-4">
            <h4 class="font-semibold mb-2">Distribución por Tipo de Cuota:</h4>
            <div class="bg-gray-50 p-4 rounded">
              <div v-for="(cantidad, tipo) in informe.sociosPorCuota" 
                   :key="tipo" 
                   class="mb-2">
                <span class="font-medium">{{ tipo }}:</span> {{ cantidad }} socios
              </div>
            </div>
          </div>

          <!-- Tabla de socios -->
          <div class="mt-6">
            <h4 class="font-semibold mb-2">Listado de Socios:</h4>
            <div class="overflow-x-auto">
              <table class="min-w-full bg-white border">
                <thead>
                  <tr class="bg-gray-100">
                    <th class="p-2 border">ID</th>
                    <th class="p-2 border">Nombre</th>
                    <th class="p-2 border">Email</th>
                    <th class="p-2 border">Fecha Alta</th>
                    <th class="p-2 border">Tipo Cuota</th>
                    <th class="p-2 border">Saldo</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="socio in informe.detallesSocios" :key="socio.id">
                    <td class="p-2 border">{{ socio.id }}</td>
                    <td class="p-2 border">{{ socio.nombre }}</td>
                    <td class="p-2 border">{{ socio.email }}</td>
                    <td class="p-2 border">{{ socio.fechaAlta }}</td>
                    <td class="p-2 border">{{ socio.tipoCuota }}</td>
                    <td class="p-2 border">{{ socio.saldo }}€</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>

      <!-- Informe de Altas -->
      <div v-if="tipoInforme === 'altas'" class="space-y-6">
        <div>
          <h3 class="font-bold text-lg mb-2">Resumen de Nuevas Altas</h3>
          <p class="mb-2">Total de Nuevas Altas: {{ informe.totalNuevasAltas }}</p>

          <!-- Tabla de nuevas altas -->
          <div class="mt-6">
            <h4 class="font-semibold mb-2">Detalle de Nuevas Altas:</h4>
            <div class="overflow-x-auto">
              <table class="min-w-full bg-white border">
                <thead>
                  <tr class="bg-gray-100">
                    <th class="p-2 border">ID</th>
                    <th class="p-2 border">Nombre</th>
                    <th class="p-2 border">Email</th>
                    <th class="p-2 border">Teléfono</th>
                    <th class="p-2 border">Fecha Alta</th>
                    <th class="p-2 border">Tipo Cuota</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="alta in informe.detallesAltas" :key="alta.id">
                    <td class="p-2 border">{{ alta.id }}</td>
                    <td class="p-2 border">{{ alta.nombre }}</td>
                    <td class="p-2 border">{{ alta.email }}</td>
                    <td class="p-2 border">{{ alta.telefono }}</td>
                    <td class="p-2 border">{{ alta.fechaAlta }}</td>
                    <td class="p-2 border">{{ alta.tipoCuota }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>

      <!-- Informe de Ingresos -->
      <div v-if="tipoInforme === 'ingresos'" class="space-y-6">
        <div>
          <h3 class="font-bold text-lg mb-2">Resumen de Ingresos</h3>
          <p class="mb-2">Total de Ingresos: {{ informe.totalIngresos }}€</p>

          <!-- Tabla de ingresos -->
          <div class="mt-6">
            <h4 class="font-semibold mb-2">Detalle de Ingresos:</h4>
            <div class="overflow-x-auto">
              <table class="min-w-full bg-white border">
                <thead>
                  <tr class="bg-gray-100">
                    <th class="p-2 border">ID</th>
                    <th class="p-2 border">Concepto</th>
                    <th class="p-2 border">Fecha</th>
                    <th class="p-2 border">Importe</th>
                    <th class="p-2 border">Método de Pago</th>
                    <th class="p-2 border">Socio</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="ingreso in informe.ingresos" :key="ingreso.id">
                    <td class="p-2 border">{{ ingreso.id }}</td>
                    <td class="p-2 border">{{ ingreso.concepto }}</td>
                    <td class="p-2 border">{{ ingreso.fecha }}</td>
                    <td class="p-2 border">{{ ingreso.importe }}€</td>
                    <td class="p-2 border">{{ ingreso.metodoPago }}</td>
                    <td class="p-2 border">{{ ingreso.socio?.nombre || 'N/A' }}</td>
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
import { ref, computed } from 'vue';
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
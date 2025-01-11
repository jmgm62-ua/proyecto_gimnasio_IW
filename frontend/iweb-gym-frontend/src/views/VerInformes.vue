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
        <h2 class="text-xl font-bold mb-4">Resultados</h2>
        <div v-html="informeHTML"></div>
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
  
      const informeHTML = computed(() => {
        if (!informe.value) return '';
  
        let html = `
          <div class="space-y-4">
            <div>
              <h3 class="font-bold">Periodo:</h3>
              <p>${tipoPeriodo.value === 'mensual' ? 
                `${meses[mes.value - 1]} ${año.value}` : 
                `Año ${año.value}`}</p>
            </div>
        `;
  
        switch (tipoInforme.value) {
            case 'asistencia':
                html += `
                <div>
                    <h3 class="font-bold">Total Asistencias:</h3>
                    <p>${informe.value.totalAsistentes}</p>
                </div>
                `;
                break;
            case 'socios':
                html += `
                <div>
                    <h3 class="font-bold">Total Socios:</h3>
                    <p>${informe.value.totalSocios}</p>
                </div>
                `;
                break;
            case 'altas':
                html += `
                <div>
                    <h3 class="font-bold">Total Altas:</h3>
                    <p>${informe.value.nuevasAltas}</p>
                </div>
                `;
                break;
        }
  
        html += '</div>';
        return html;
      });
  
      return {
        tipoInforme,
        tipoPeriodo,
        mes,
        año,
        meses,
        años,
        informe,
        informeHTML,
        generarInforme
      };
    }
  };
  </script>
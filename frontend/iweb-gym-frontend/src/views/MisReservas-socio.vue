<template>
    <div class="container mx-auto p-4">
    <h1 class="text-2xl font-bold mb-4">Mis Reservas</h1>
    
    <!-- Toggle View -->
    <div class="mb-4">
        <button 
        @click="viewMode = 'list'"
        :class="['mr-2 px-4 py-2 rounded', viewMode === 'list' ? 'bg-blue-500 text-white' : 'bg-gray-200']"
        >
        Lista
        </button>
        <button 
        @click="viewMode = 'calendar'"
        :class="['px-4 py-2 rounded', viewMode === 'calendar' ? 'bg-blue-500 text-white' : 'bg-gray-200']"
        >
        Calendario
        </button>
    </div>

    <!-- List View -->
    <div v-if="viewMode === 'list'">
        <div class="mb-8">
        <h2 class="text-xl font-semibold mb-4">Reservas Pendientes</h2>
        <div v-if="reservasPendientes.length" class="space-y-4">
            <div v-for="reserva in reservasPendientes" :key="reserva.id" 
                class="border p-4 rounded shadow">
            <h3 class="font-medium">{{ reserva.titulo }}</h3>
            <p>Fecha: {{ formatDate(reserva.fecha) }}</p>
            <p>Estado: <span class="text-yellow-600">Pendiente</span></p>
            </div>
        </div>
        <p v-else class="text-gray-500">No hay reservas pendientes</p>
        </div>

        <div>
        <h2 class="text-xl font-semibold mb-4">Historial de Reservas</h2>
        <div v-if="reservasHistoricas.length" class="space-y-4">
            <div v-for="reserva in reservasHistoricas" :key="reserva.id" 
                class="border p-4 rounded shadow bg-gray-50">
            <h3 class="font-medium">{{ reserva.nombreActividad }}</h3>
            <p>Fecha: {{ formatDate(reserva.fecha) }}</p>
            </div>
        </div>
        <p v-else class="text-gray-500">No hay reservas históricas</p>
        </div>
    </div>

    <!-- Calendar View -->
    <div v-else class="border rounded p-4">
        <!-- Aquí puedes integrar un componente de calendario como v-calendar -->
        <div class="grid grid-cols-7 gap-2">
        <!-- Calendar implementation here -->
        </div>
    </div>
    </div>
</template>
  
<script setup>
    import { ref, onMounted } from 'vue';
    import { useUserStore } from '@/stores/userStore';
    import axios from 'axios';

    const userStore = useUserStore();
    const email = userStore.email;
    const viewMode = ref('list');
    const reservasPendientes = ref([]);
    const reservasHistoricas = ref([]);

    const fetchReservas = async () => {
        try {
            const [pendientesRes, historicasRes] = await Promise.all([
            axios.get(`http://localhost:8080/reservas/pendientes/${email}`),
            axios.get(`http://localhost:8080/reservas/historicas/${email}`)
            ]);
            
            reservasPendientes.value = pendientesRes.data;
            reservasHistoricas.value = historicasRes.data;
        } catch (error) {
            console.error('Error al obtener las reservas:', error);
        }
    };

    const formatDate = (date) => {
        return new Date(date).toLocaleDateString();
    };

    onMounted(() => {
        fetchReservas();
    });
</script>
<template>
    <!-- El template se mantiene igual -->
    <div class="container mx-auto p-4">
      <!-- Categorías -->
      <div class="mb-8">
        <h2 class="text-2xl font-bold mb-4">Categorías</h2>
        <div class="flex flex-wrap gap-4">
          <button
            v-for="category in categories"
            :key="category.id"
            @click="selectCategory(category)"
            :class="['px-4 py-2 rounded', 
              selectedCategory?.id === category.id 
                ? 'bg-blue-600 text-white' 
                : 'bg-gray-200 hover:bg-gray-300'
            ]"
          >
            {{ category.nombre }}
          </button>
        </div>
      </div>
  
      <!-- Subcategorías -->
      <div v-if="subcategories.length" class="mb-8">
        <h3 class="text-xl font-bold mb-4">Subcategorías</h3>
        <div class="flex flex-wrap gap-4">
          <button
            v-for="subcategory in subcategories"
            :key="subcategory.id"
            @click="selectSubcategory(subcategory)"
            :class="['px-4 py-2 rounded', 
              selectedSubcategory?.id === subcategory.id 
                ? 'bg-blue-600 text-white' 
                : 'bg-gray-200 hover:bg-gray-300'
            ]"
          >
            {{ subcategory.nombre }}
          </button>
        </div>
      </div>
  
      <!-- Productos -->
      <div>
        <h3 class="text-xl font-bold mb-4">Productos</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
          <div 
            v-for="product in products" 
            :key="product.cod"
            class="border rounded-lg overflow-hidden shadow-lg"
          >
            <img 
              :src="product.foto" 
              :alt="product.nombre"
              class="w-full h-48 object-cover"
            >
            <div class="p-4">
              <h4 class="font-bold text-lg mb-2">{{ product.nombre }}</h4>
              <p class="text-gray-600 mb-2">{{ product.descripcion_corta }}</p>
              <div class="flex justify-between items-center">
                <div>
                  <span class="font-bold text-lg">
                    {{ formatPrice(product.precio * (1 - product.descuento)) }}
                  </span>
                  <span v-if="product.descuento > 0" class="ml-2 text-sm line-through text-gray-500">
                    {{ formatPrice(product.precio) }}
                  </span>
                </div>
                <button
                  @click="redirectToStore(product)"
                  class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
                >
                  Comprar
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  
  // Configuración de API
  const API_CONFIG = {
    // Cambiar a true para usar la API real
    useRealAPI: false,
    baseURL: 'https://api-ejemplo.com'
  }
  
  // Datos mock expandidos
  const mockCategories = [
    { id: 1, nombre: "Fútbol" },
    { id: 2, nombre: "Tenis" },
    { id: 3, nombre: "Pádel" }
  ]
  
  const mockSubcategories = {
    1: [
      { id: 1, nombre: "Botas" },
      { id: 2, nombre: "Balones" },
      { id: 3, nombre: "Equipaciones" }
    ],
    2: [
      { id: 4, nombre: "Raquetas" },
      { id: 5, nombre: "Pelotas" },
      { id: 6, nombre: "Complementos" }
    ],
    3: [
      { id: 7, nombre: "Palas" },
      { id: 8, nombre: "Pelotas" },
      { id: 9, nombre: "Accesorios" }
    ]
  }
  
  const mockProducts = {
    "Fútbol": [
      {
        cod: "F001",
        nombre: "Balón Oficial Liga",
        descripcion_corta: "Balón oficial temporada 2023/24",
        foto: "https://via.placeholder.com/400x300",
        precio: 89.99,
        descuento: 0.1,
        marca: "Adidas"
      },
      {
        cod: "F002",
        nombre: "Botas de Fútbol Pro",
        descripcion_corta: "Botas profesionales para césped natural",
        foto: "https://via.placeholder.com/400x300",
        precio: 129.99,
        descuento: 0,
        marca: "Nike"
      }
    ],
    1: [ // Botas
      {
        cod: "FB001",
        nombre: "Botas Predator",
        descripcion_corta: "Botas de gama alta para futbol",
        foto: "https://via.placeholder.com/400x300",
        precio: 199.99,
        descuento: 0.15,
        marca: "Adidas"
      },
      {
        cod: "FB002",
        nombre: "Botas Tiempo Legend",
        descripcion_corta: "Botas clásicas de piel",
        foto: "https://via.placeholder.com/400x300",
        precio: 179.99,
        descuento: 0,
        marca: "Nike"
      }
    ],
    2: [ // Balones
      {
        cod: "BL001",
        nombre: "Balón Champions League",
        descripcion_corta: "Balón oficial Champions League 2024",
        foto: "https://via.placeholder.com/400x300",
        precio: 139.99,
        descuento: 0.2,
        marca: "Adidas"
      },
      {
        cod: "BL002",
        nombre: "Balón Training Pro",
        descripcion_corta: "Balón de entrenamiento profesional",
        foto: "https://via.placeholder.com/400x300",
        precio: 49.99,
        descuento: 0,
        marca: "Nike"
      }
    ],
    4: [ // Raquetas Tenis
      {
        cod: "RT001",
        nombre: "Raqueta Pro Staff",
        descripcion_corta: "Raqueta profesional de alta precisión",
        foto: "https://via.placeholder.com/400x300",
        precio: 249.99,
        descuento: 0.1,
        marca: "Wilson"
      },
      {
        cod: "RT002",
        nombre: "Raqueta Speed Pro",
        descripcion_corta: "Raqueta para jugadores avanzados",
        foto: "https://via.placeholder.com/400x300",
        precio: 219.99,
        descuento: 0.05,
        marca: "Head"
      }
    ],
    7: [ // Palas Pádel
      {
        cod: "PP001",
        nombre: "Pala Elite Pro",
        descripcion_corta: "Pala de alto rendimiento",
        foto: "https://via.placeholder.com/400x300",
        precio: 299.99,
        descuento: 0.15,
        marca: "Bullpadel"
      },
      {
        cod: "PP002",
        nombre: "Pala Control Advanced",
        descripcion_corta: "Pala para jugadores técnicos",
        foto: "https://via.placeholder.com/400x300",
        precio: 259.99,
        descuento: 0,
        marca: "Nox"
      }
    ]
  }
  
  export default {
    name: 'ProductsView',
    setup() {
      const categories = ref([])
      const subcategories = ref([])
      const products = ref([])
      const selectedCategory = ref(null)
      const selectedSubcategory = ref(null)
  
      // Funciones para la API real
      const fetchCategoriesAPI = async () => {
        try {
          const response = await axios.get(`${API_CONFIG.baseURL}/categories`)
          return response.data
        } catch (error) {
          console.error('Error fetching categories:', error)
          return []
        }
      }
  
      const fetchSubcategoriesAPI = async (categoryId) => {
        try {
          const response = await axios.get(`${API_CONFIG.baseURL}/subcategories/search?id=${categoryId}`)
          return response.data
        } catch (error) {
          console.error('Error fetching subcategories:', error)
          return []
        }
      }
  
      const fetchProductsAPI = async (type, value) => {
        try {
          let url
          if (type === 'category') {
            url = `${API_CONFIG.baseURL}/categories/search?q=${value}`
          } else if (type === 'subcategory') {
            url = `${API_CONFIG.baseURL}/subcategories/search?id=${value}`
          }
          const response = await axios.get(url)
          return response.data
        } catch (error) {
          console.error('Error fetching products:', error)
          return []
        }
      }
  
      // Funciones principales que manejan tanto mock como API real
      const fetchCategories = async () => {
        if (API_CONFIG.useRealAPI) {
          categories.value = await fetchCategoriesAPI()
        } else {
          categories.value = mockCategories
        }
      }
  
      const fetchSubcategories = async (categoryId) => {
        if (API_CONFIG.useRealAPI) {
          subcategories.value = await fetchSubcategoriesAPI(categoryId)
        } else {
          subcategories.value = mockSubcategories[categoryId] || []
        }
      }
  
      const fetchProducts = async (type, value) => {
        if (API_CONFIG.useRealAPI) {
          products.value = await fetchProductsAPI(type, value)
        } else {
          products.value = mockProducts[type === 'category' ? value : value] || []
        }
      }
  
      const selectCategory = async (category) => {
        selectedCategory.value = category
        selectedSubcategory.value = null
        await fetchSubcategories(category.id)
        await fetchProducts('category', category.nombre)
      }
  
      const selectSubcategory = async (subcategory) => {
        selectedSubcategory.value = subcategory
        await fetchProducts('subcategory', subcategory.id)
      }
  
      const formatPrice = (price) => {
        return new Intl.NumberFormat('es-ES', {
          style: 'currency',
          currency: 'EUR'
        }).format(price)
      }
  
      const redirectToStore = (product) => {
        const storeUrl = `${API_CONFIG.baseURL}/producto/${product.cod}`
        window.location.href = storeUrl
      }
  
      onMounted(fetchCategories)
  
      return {
        categories,
        subcategories,
        products,
        selectedCategory,
        selectedSubcategory,
        selectCategory,
        selectSubcategory,
        formatPrice,
        redirectToStore
      }
    }
  }
  </script>
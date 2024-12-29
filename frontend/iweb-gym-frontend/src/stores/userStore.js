// stores/useUserStore.js
import { defineStore } from 'pinia';
import piniaPersist from 'pinia-plugin-persistedstate';

export const useUserStore = defineStore('user', {
  state: () => ({
    userType: null, // Tipo de usuario
    isLoggedIn: false, // Indica si hay una sesión activa
    id: null, // ID del usuario
    email: null, // Email del usuario
    nombre: null, // Nombre del usuario
    fechaNacimiento: null, // Fecha de nacimiento del usuario
  }),
  actions: {
    // Establecer el tipo de usuario
    setUserType(type) {
      this.userType = type;
    },
    // Marcar la sesión como activa
    setIsLoggedIn() {
      this.isLoggedIn = true;
    },
    // Cerrar sesión
    logOut() {
      this.isLoggedIn = false;
      this.userType = null;
      this.id = null;
      this.email = null;
      this.nombre = null;
      this.fechaNacimiento = null;
    },
    // Establecer los datos del usuario
    setUserData({ id, email, nombre, fechaNacimiento }) {
      this.id = id;
      this.email = email;
      this.nombre = nombre;
      this.fechaNacimiento = fechaNacimiento;
    },
  },
  persist: true,
});

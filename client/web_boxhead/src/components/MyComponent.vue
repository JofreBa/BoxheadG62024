<template>
    <div>
      <v-btn @click="imprimirUsuarios">Imprimir Usuarios</v-btn>
      <v-list v-if="usuarios.length" dense style="max-height: 400px; overflow-y: auto;">
        <v-subheader>Usuarios:</v-subheader>
        <v-list-item v-for="(usuario, index) in usuarios" :key="index">
          <v-list-item-content>
            <v-list-item-title>{{ usuario.name }}</v-list-item-title>
            <v-list-item-subtitle>{{ usuario.email }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-list>
      <div v-else>No hay usuarios para mostrar</div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        usuarios: [],
      };
    },
    methods: {
      async imprimirUsuarios() {
        try {
          const response = await axios.get('http://localhost:3100/print-collection');
          console.log(response.data.data); // Verifica el formato de los datos
          this.usuarios = response.data.data[0].users;
        } catch (error) {
          console.error('Error al imprimir usuarios:', error);
          // Manejo de errores si la solicitud falla
        }
      },
    },
  };
  </script>
  
  <style scoped>
  /* Estilos para la lista */
  .v-list-item {
    padding-top: 8px; /* Ajusta el espacio entre los elementos */
    padding-bottom: 8px; /* Ajusta el espacio entre los elementos */
  }
  </style>
  
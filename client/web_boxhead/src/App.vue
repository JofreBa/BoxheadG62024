<template>
  <v-app>
    <v-container>
      <InitialMenu></InitialMenu>
      <router-view></router-view>
    </v-container>
  </v-app>
</template>

<script setup>
import InitialMenu from './components/InitialMenu.vue'; // Asegúrate de importar InitialMenu correctamente
</script>

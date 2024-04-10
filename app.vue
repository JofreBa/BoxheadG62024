<template>
  <div>
    
    <NuxtLayout />
    
  </div>
</template>

<script>
// Import the GameStats component
import GameStats from './components/gameStats.vue';
import Navigation from './components/navigation.vue';
import Users from './components/users.vue';

export default {
  name: 'App',
  components: {
    // Register the GameStats component
    GameStats
  },
  
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

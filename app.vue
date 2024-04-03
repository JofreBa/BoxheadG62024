<template>
  <div id="app">
    <GameStats />
  </div>
</template>

<script>
// Import the GameStats component
import GameStats from './gameStats.vue';

export default {
  name: 'App',
  components: {
    // Register the GameStats component
    GameStats
  }
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

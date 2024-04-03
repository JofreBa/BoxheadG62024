<template>
    <div class="game-stats">
      <div class="section">
        <h2>Player Stats</h2>
        <div class="input-group">
          <label for="playerBaseHealth">Player Base Health:</label>
          <input type="number" id="playerBaseHealth" v-model="playerStats.baseHealth">
        </div>
        <div class="input-group">
          <label for="playerBaseDamage">Player Base Damage:</label>
          <input type="number" id="playerBaseDamage" v-model="playerStats.baseDamage">
        </div>
        <div class="input-group">
          <label for="playerBaseSpeed">Player Base Speed:</label>
          <input type="number" id="playerBaseSpeed" v-model="playerStats.baseSpeed">
        </div>
        <div class="input-group">
          <label for="playerHealthIncrement">Player Health Increment:</label>
          <input type="number" id="playerHealthIncrement" v-model="playerStats.healthIncrement">
        </div>
        <div class="input-group">
          <label for="playerDamageIncrement">Player Damage Increment:</label>
          <input type="number" id="playerDamageIncrement" v-model="playerStats.damageIncrement">
        </div>
        <div class="input-group">
          <label for="playerSpeedIncrement">Player Speed Increment:</label>
          <input type="number" id="playerSpeedIncrement" v-model="playerStats.speedIncrement">
        </div>
      </div>
      <div class="section">
        <h2>Enemy Stats</h2>
        <div class="input-group">
          <label for="enemyBaseHealth">Enemy Base Health:</label>
          <input type="number" id="enemyBaseHealth" v-model="enemyStats.baseHealth">
        </div>
        <div class="input-group">
          <label for="enemyBaseDamage">Enemy Base Damage:</label>
          <input type="number" id="enemyBaseDamage" v-model="enemyStats.baseDamage">
        </div>
        <div class="input-group">
          <label for="enemyBaseSpeed">Enemy Base Speed:</label>
          <input type="number" id="enemyBaseSpeed" v-model="enemyStats.baseSpeed">
        </div>
        <div class="input-group">
          <label for="enemyHealthIncrement">Enemy Health Increment:</label>
          <input type="number" id="enemyHealthIncrement" v-model="enemyStats.healthIncrement">
        </div>
        <div class="input-group">
          <label for="enemyDamageIncrement">Enemy Damage Increment:</label>
          <input type="number" id="enemyDamageIncrement" v-model="enemyStats.damageIncrement">
        </div>
        <div class="input-group">
          <label for="enemySpeedIncrement">Enemy Speed Increment:</label>
          <input type="number" id="enemySpeedIncrement" v-model="enemyStats.speedIncrement">
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        playerStats: {
          baseHealth: 100,
          baseDamage: 10,
          baseSpeed: 5,
          healthIncrement: 20,
          damageIncrement: 5,
          speedIncrement: 1
        },
        enemyStats: {
          baseHealth: 200,
          baseDamage: 15,
          baseSpeed: 3,
          healthIncrement: 30,
          damageIncrement: 8,
          speedIncrement: 2
        }
      };
    }
  };
  </script>
  
  <style scoped>
  .game-stats {
    margin: 20px;
    display: flex; /* Use flexbox */
  }
  
  .section {
    width: 50%; /* Each section takes 50% of the container width */
    margin-bottom: 20px;
  }
  
  .input-group {
    margin-bottom: 10px;
  }
  
  label {
    color: darkslategray;
    display: block;
  }
  
  input[type="number"] {
    padding: 5px;
    max-width: 80px;
  }
  </style>
  
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
        
        <button @click="saveGameData">Save Game Data</button>
      
      </div>
      

    </div>
    <div>
    <input type="file" @change="handleFileChange">
    <button @click="uploadImage">Upload Image</button>
    <img v-if="imageUrl" :src="imageUrl" alt="Uploaded Image">
  </div>
  </template>
  
  <script>
  import { fetchGameData, updateGameData, getImage, saveImage } from '../commsManager';
  export default {
  data() {
    return {
      playerStats: {
    baseHealth: null,
    baseDamage: null,
    baseSpeed: null,
  },
  enemyStats: {
    baseHealth: null,
    baseDamage: null,
    baseSpeed: null,
  },
  file: null,
  imageUrl: null
    };
  },
  async mounted() {
    
    const gameData = await fetchGameData();
    if (gameData) {
      this.playerStats = gameData.gameStats.player;
      this.enemyStats = gameData.gameStats.enemy;
      
    }
    this.imageUrl = await getImage()
  },
  methods: {
    async saveGameData() {
      await updateGameData({
        player: this.playerStats,
        enemy: this.enemyStats
      });
    },
    handleFileChange(event) {
      this.file = event.target.files[0];
      if (this.file) {
        this.convertToBase64();
      }
    },
    convertToBase64() {
      const reader = new FileReader();
      reader.readAsDataURL(this.file);
      reader.onload = () => {
        this.imageUrl = reader.result;
      };
    },
    
    async uploadImage() {
      await saveImage(this.imageUrl)
      console.log("encoded base64 image::::: ", this.imageUrl);
    },
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
  
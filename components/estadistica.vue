<template>
    <div class="game-stats">
      
    <button @click="fetchEstadistica">Generar Estadistiques</button>
    
  </div>
  <div><img v-if="imageUrl" :src="imageUrl" alt="Uploaded Image"></div>
  <div><img v-if="imageUrl2" :src="imageUrl2" alt="Uploaded Image2"></div>
  
    
  </template>
  
  <script>
  import { getEstadistiques } from '../commsManager';
  export default {
  data() {
    return {
        imageUrl:null,
        imageUrl2: null
    };
  },
  async mounted() {
    
    
  },
  methods: {
   async fetchEstadistica(){
    console.log("aaaaaaaaa")
    const data = await getEstadistiques();
    console.log(data.image2)
    this.imageUrl = data.image1
    this.imageUrl2 = data.image2
   }
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
  img{
    max-width: 700px;
  }
  </style>
  
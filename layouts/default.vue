<template>
    <div>
      <Navigation />
      
      <NuxtPage />
    </div>
  </template>
  
  <script>
  import Navigation from '@/components/navigation.vue';
  
  export default {
    components: {
      Navigation
    }
  };
  </script>
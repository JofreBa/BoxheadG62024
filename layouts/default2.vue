<template>
    <div>
      <Navigation />
      this is the ANOTHER layout
      <NuxtPage />
      <slot /> <!-- Render the content of the page component here -->
  
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
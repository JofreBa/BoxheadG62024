<template>
    <div>
      this is index.vue
    </div>
  </template>
  
  <script>
  import GameStats from '@/components/gameStats.vue';
  import Navigation from '@/components/navigation.vue';
  import Users from '@/components/users.vue';

  export default {
    components: {
      GameStats
    },
    layout: 'default'
  };
  </script>
  
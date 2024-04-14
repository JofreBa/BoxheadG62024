<template>
    <div>
     <Estadistica />
    </div>
  </template>
  
  <script>
  import Estadistica from '@/components/estadistica.vue';
  import Navigation from '@/components/navigation.vue';
  import Users from '@/components/users.vue';

  export default {
    components: {
      Estadistica
    },
    layout: 'default'
  };
  </script>
  
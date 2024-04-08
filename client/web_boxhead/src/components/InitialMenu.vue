<template>
  <div>
    <v-btn @click="irAUsers">Ir a Users</v-btn>
  </div>
</template>

<script>
export default {
  methods: {
    irAUsers() {
      this.$router.push({ name: "Users" });
    },
  },
};
</script>

<style scoped>
</style>

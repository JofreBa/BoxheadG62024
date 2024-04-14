<template>
  <nav>
    <ul>
      <li v-for="(item, index) in items" :key="index">
        <a :href="item.link">{{ item.text }}</a>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  data() {
    return {
      items: [
        { text: 'Game Values', link: '/changeGameStats' },
        { text: 'Estadistiques', link: '/estadistica' },
        { text: 'Users', link: '/users' },
        
      ]
    };
  }
};
</script>

<style scoped>
nav {
  background-color: #333;
}

ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}

li {
  display: inline;
}

a {
  color: white;
  text-decoration: none;
  padding: 14px 16px;
}

a:hover {
  background-color: #ddd;
  color: black;
}
</style>
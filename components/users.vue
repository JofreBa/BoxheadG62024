<template>
  <div class="users-panel">
    <h2>Users Panel</h2>
    <div v-if="loading">Loading...</div>
    <div v-else>
      <table v-if="users.length > 0">
        <thead>
          <tr>
            <th>Email</th>
            <th>Username</th>
            <th>Last Played</th>
            <th>Status</th>
            <th>Creation Date</th>
            <th>Stats</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(user, index) in users" :key="index">
            <td>{{ user.email }}</td>
            <td>{{ user.username }}</td>
            <td>{{ user.lastPlayed }}</td>
            <td>{{ user.status }}</td>
            <td>{{ user.creationDate }}</td>
            <td>
              <div class="stats-container">
                <button @click="toggleStats(index)">Toggle Stats</button>
                <div v-if="user.showStats" class="stats">
                  <div class="stat-bar">
                    <div class="bar" :style="{ width: gameTimeBar(user.gameTime) }"></div>
                    <div class="label">Game Time: {{ user.gameTime }} hours</div>
                  </div>
                  <div class="stat-bar">
                    <div class="bar" :style="{ width: killsBar(user.kills) }"></div>
                    <div class="label">Kills: {{ user.kills }}</div>
                  </div>
                </div>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
      <div v-else>
        <p>No users found.</p>
      </div>
    </div>
  </div>
</template>

<script>
import { fetchUsers } from '../server/mongoDBFunctions'; 

export default {
  data() {
    return {
      users: [
        {
          email: 'user1@example.com',
          username: 'user1',
          lastPlayed: '2024-04-01',
          status: 'Active',
          creationDate: '2023-01-15',
          gameTime: 50, // Hardcoded game time
          kills: 20, // Hardcoded kills
          showStats: false // Initially hide stats
        },
        {
          email: 'user2@example.com',
          username: 'user2',
          lastPlayed: '2024-03-30',
          status: 'Inactive',
          creationDate: '2022-11-05',
          gameTime: 30,
          kills: 10,
          showStats: false
        },
        {
          email: 'user3@example.com',
          username: 'user3',
          lastPlayed: '2024-04-03',
          status: 'Active',
          creationDate: '2024-02-20',
          gameTime: 70,
          kills: 15,
          showStats: false
        }
      ],
      loading: true,
      error: false
    };
  },
  async created() {
    try {
      this.users = await fetchUsers(); // Fetch users when the component is created
      this.loading = false;
    } catch (error) {
      console.error('Error fetching users in UsersPanel:', error);
      this.loading = false;
      this.error = true;
      setTimeout(() => {
        this.error = false;
      }, 2000); // Show error message for 2 seconds
    }
  },
  methods: {
    toggleStats(index) {
      this.users[index].showStats = !this.users[index].showStats;
    },
    // Calculate the width of the bar based on game time
    gameTimeBar(gameTime) {
      const maxWidth = 200; // Maximum width of the bar
      const maxGameTime = 150; // Maximum game time (for example)
      const width = (gameTime / maxGameTime) * maxWidth;
      return `${width}px`;
    },
    // Calculate the width of the bar based on kills
    killsBar(kills) {
      const maxWidth = 200; // Maximum width of the bar
      const maxKills = 100; // Maximum kills (for example)
      const width = (kills / maxKills) * maxWidth;
      return `${width}px`;
    }
  }
};
</script>

<style scoped>
.users-panel {
  border: 1px solid #ccc;
  padding: 20px;
  border-radius: 5px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

th {
  background-color: #f2f2f2;
}

.stats-container {
  margin-top: 10px;
}

.stats {
  margin-top: 5px;
}

.stat-bar {
  margin-bottom: 5px;
}

.bar {
  background-color: #007bff;
  height: 20px;
}

.label {
  margin-left: 5px;
  font-size: 14px;
  color: #333;
}
</style>

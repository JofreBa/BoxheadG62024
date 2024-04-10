<template>
    <div class="users-panel">
      <h2>Users Panel</h2>
      <table>
        <thead>
          <tr>
            <th>Email</th>
            <th>Username</th>
            <th>Last Played</th>
            <th>Status</th>
            <th>Creation Date</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(user, index) in users" :key="index">
            <td>{{ user.email }}</td>
            <td>{{ user.username }}</td>
            <td>{{ user.lastPlayed }}</td>
            <td>{{ user.status }}</td>
            <td>{{ user.creationDate }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        users: [
          {
            email: 'user1@example.com',
            username: 'user1',
            lastPlayed: '2024-04-01',
            status: 'Active',
            creationDate: '2023-01-15'
          },
          {
            email: 'user2@example.com',
            username: 'user2',
            lastPlayed: '2024-03-30',
            status: 'Inactive',
            creationDate: '2022-11-05'
          },
          {
            email: 'user3@example.com',
            username: 'user3',
            lastPlayed: '2024-04-03',
            status: 'Active',
            creationDate: '2024-02-20'
          }
        ]
      };
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
  </style>
  
const express = require('express');
const http = require('http');
const socketIO = require('socket.io');
const cors = require('cors');
const bodyParser = require('body-parser');
const path = require('path');
const { exec } = require('child_process');
const fs = require('fs');

const { connectToMongoDB, closeMongoDB } = require('./mongoDBConection');
const { fetchUsers, uploadImage, getImage, printCollection, updateUser, generateUniqueId, createUser, deleteUser, updateGameStats } = require('./mongoDBFunctions');

const app = express();
const server = http.createServer(app);
const io = socketIO(server);

app.use(cors());
app.use(bodyParser.json());

app.get('/', (req, res) => {
    res.send('Servidor en funcionamiento');
});

app.get('/users', async (req, res) => {
  try {
    const users = await fetchUsers();
    res.json({ success: true, data: users });
  } catch (error) {
    console.error('Error fetching users:', error);
    res.status(500).json({ success: false, message: 'Error fetching users' });
  }
});

app.route('/print-users')
  .get(async (req, res) => {
    try {
      const collection = await connectToMongoDB("Users");
      const result = await printCollection(collection);
      res.json({ success: true, data: result });
    } catch (error) {
      console.error('Error al imprimir la colección:', error);
      res.status(500).json({ success: false, message: 'Error al imprimir la colección' });
    }
  })
  .post(async (req, res) => {
    try {
      const collection = await connectToMongoDB("Users");
      const result = await printCollection(collection);
      res.json({ success: true, data: result });
    } catch (error) {
      console.error('Error al imprimir la colección:', error);
      res.status(500).json({ success: false, message: 'Error al imprimir la colección' });
    }
  });

// Ruta para actualizar un usuario
app.put('/users/:id', async (req, res) => {
  const userId = req.params.id;
  const updatedUserData = req.body;

  try {
    const collection = await connectToMongoDB("Users");
    const result = await updateUser(collection, userId, updatedUserData);
    res.json({ success: true, data: result });
  } catch (error) {
    console.error('Error al actualizar usuario:', error);
    res.status(500).json({ success: false, message: 'Error al actualizar usuario' });
  }
});

// Ruta para crear un nuevo usuario
app.post('/users', async (req, res) => {
  const { name, email } = req.body;
  const newUser = {
    _id: generateUniqueId(), // Genera un _id único
    name,
    email,
  };

  try {
    const collection = await connectToMongoDB("Users");
    const result = await createUser(collection, newUser);
    res.json({ success: true, data: result });
  } catch (error) {
    console.error('Error al crear usuario:', error);
    res.status(500).json({ success: false, message: 'Error al crear usuario' });
  }
});

// Ruta para eliminar un usuario
app.delete('/users/:id', async (req, res) => {
  const userId = req.params.id;

  try {
    const collection = await connectToMongoDB("Users");
    const result = await deleteUser(collection, userId);
    if (result.deletedCount === 0) {
      return res.status(404).json({ success: false, message: 'Usuario no encontrado' });
    }
    res.json({ success: true, message: 'Usuario eliminado correctamente' });
  } catch (error) {
    console.error('Error al eliminar usuario:', error);
    res.status(500).json({ success: false, message: 'Error al eliminar usuario' });
  }
});

app.get('/gameStats', async (req, res) => {
  try {
      const collection = await connectToMongoDB("gameStats");
      const gameData = await collection.findOne({});
      
      if (!gameData) {
          return res.status(404).json({ success: false, message: 'Game data not found' });
      }

      res.status(200).json({ success: true, data: gameData });
  } catch (error) {
      console.error('Error fetching game data:', error);
      res.status(500).json({ success: false, message: 'Error fetching game data' });
  }
});

app.post('/updateGameStats', async (req, res) => {
  const gameStatsData = req.body.gameStats;

  try {
      const collection = await connectToMongoDB("gameStats");
      await updateGameStats(collection, gameStatsData);
      res.status(200).json({ success: true, message: 'Game data updated successfully' });
  } catch (error) {
      console.error('Error updating game data:', error);
      res.status(500).json({ success: false, message: 'Error updating game data' });
  }
});

app.get('/getImage', async (req, res) => {
  try {
    const collection = await connectToMongoDB("image");
      const image = await getImage(collection);
      console.log(image)
      res.send(image);
  } catch (error) {
      res.status(500).json({ success: false, message: 'Error fetching image' });
  }
});

app.post('/uploadImage', async (req, res) => {
  try {
    const collection = await connectToMongoDB("image");
      const newImageData = req.body.image;
      
      await uploadImage(collection,newImageData);
      res.json({ success: true, message: 'Image loaded successfully' });
  } catch (error) {
      res.status(500).json({ success: false, message: 'Error loading image' });
  }
});

app.get('/getEstadisticas', (req, res) => {
  // Define paths to Python scripts
  const gamesScriptPath = path.join(__dirname, 'estadistica', 'games.py');
  const dailyGamesScriptPath = path.join(__dirname, 'estadistica', 'daily_games.py');

  // Define the working directory
  const cwd = path.join(__dirname, 'estadistica');

  // Execute games.py script
  exec(`python games.py`, { cwd }, (error1, stdout1, stderr1) => {
      if (error1) {
          console.error(`Error executing games.py: ${error1}`);
          return res.status(500).json({ error: 'Internal Server Error' });
      }

      // Execute daily_games.py script
      exec(`python daily_games.py`, { cwd }, (error2, stdout2, stderr2) => {
          if (error2) {
              console.error(`Error executing daily_games.py: ${error2}`);
              return res.status(500).json({ error: 'Internal Server Error' });
          }

          // Read images and encode to Base64
          const averageGameTableImage = fs.readFileSync(path.join(cwd, 'overall_average_game_table.png'), { encoding: 'base64' });
          const gamesPlayedBarChartImage = fs.readFileSync(path.join(cwd, 'games_played_bar_chart.png'), { encoding: 'base64' });

          // Return the images encoded as Base64 in the response
          res.json({
              
              image1: `data:image/png;base64,${averageGameTableImage}`,
              image2: `data:image/png;base64,${gamesPlayedBarChartImage}`
          });
      });
  });
});

// Inicia el servidor
const PORT = process.env.PORT || 3100;
server.listen(PORT, () => {
    console.log(`Servidor corriendo en el puerto ${PORT}`);
});

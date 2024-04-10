const express = require('express');
const http = require('http');
const socketIO = require('socket.io');
const cors = require('cors');
const bodyParser = require('body-parser');

const { connectToMongoDB, closeMongoDB } = require('./mongoDBConection');
const { printCollection, updateUser, generateUniqueId, createUser, deleteUser } = require('./mongoDBFunctions');

const app = express();
const server = http.createServer(app);
const io = socketIO(server);

app.use(cors());
app.use(bodyParser.json());

app.get('/', (req, res) => {
    res.send('Servidor en funcionamiento');
});

app.get('/connect-mongodb', async (req, res) => {
    try {
        const collection = await connectToMongoDB();
        res.send('Conexión exitosa a MongoDB');
    } catch (error) {
        console.error('Error al conectar a MongoDB:', error);
        res.status(500).send('Error al conectar a MongoDB');
    }
});

app.get('/disconnect-mongodb', async (req, res) => {
    try {
        await closeMongoDB();
        res.send('Desconexión exitosa de MongoDB');
    } catch (error) {
        console.error('Error al cerrar la conexión a MongoDB:', error);
        res.status(500).send('Error al cerrar la conexión a MongoDB');
    }
});

app.route('/print-collection')
  .get(async (req, res) => {
    try {
      const collection = await connectToMongoDB();
      const result = await printCollection(collection);
      res.json({ success: true, data: result });
    } catch (error) {
      console.error('Error al imprimir la colección:', error);
      res.status(500).json({ success: false, message: 'Error al imprimir la colección' });
    }
  })
  .post(async (req, res) => {
    try {
      const collection = await connectToMongoDB();
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
    const collection = await connectToMongoDB();
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
    const collection = await connectToMongoDB();
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
    const collection = await connectToMongoDB();
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

// Inicia el servidor
const PORT = process.env.PORT || 3100;
server.listen(PORT, () => {
    console.log(`Servidor corriendo en el puerto ${PORT}`);
});

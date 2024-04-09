const express = require('express');
const http = require('http');
const socketIO = require('socket.io');
const cors = require('cors');
const bodyParser = require('body-parser');
const { connectToMongoDB, closeMongoDB } = require('./mongoDBConection');

const app = express();
const server = http.createServer(app);
const io = socketIO(server);

/*const corsOptions = {
  origin: 'http://127.0.0.1:3100', // URL de tu aplicación Vue.js
  optionsSuccessStatus: 200, // some legacy browsers (IE11, various SmartTVs) choke on 204
};*/

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

async function printCollection(collection) {
  if (!collection) {
    throw new Error('La colección no está definida');
  }
  const users = await collection.find().toArray();
  console.log('Colección de usuarios:', users);
  return users;
}

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

async function updateUser(collection, userId, updatedUserData) {
  if (!collection) {
    throw new Error('La colección no está definida');
  }

  const result = await collection.updateOne(
    { _id: userId },
    { $set: updatedUserData }
  );

  console.log('Usuario actualizado:', result);
  return result;
}

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

function generateUniqueId() {
  // Genera un _id aleatorio único
  return Date.now().toString(36) + Math.random().toString(36).substr(2, 5);
}

async function createUser(collection, newUser) {
  if (!collection) {
    throw new Error('La colección no está definida');
  }

  const result = await collection.insertOne(newUser);
  console.log('Nuevo usuario creado:', result);
  return result;
}

// Inicia el servidor
const PORT = process.env.PORT || 3100;
server.listen(PORT, () => {
    console.log(`Servidor corriendo en el puerto ${PORT}`);
});

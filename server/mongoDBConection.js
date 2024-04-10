const { MongoClient } = require('mongodb');

const dbName = 'Boxhead2024';
//const collectionName = 'Users'; // Define el nombre de tu colección aquí
const password = 'a21jofbalgon';

const connectionString = `mongodb://a21jofbalgon:${password}@ac-kzggp6d-shard-00-00.qubkbf0.mongodb.net:27017,
  ac-kzggp6d-shard-00-01.qubkbf0.mongodb.net:27017,
  ac-kzggp6d-shard-00-02.qubkbf0.mongodb.net:27017/?replicaSet=atlas-yzd5wo-shard-0&ssl=true&authSource=admin`;

const client = new MongoClient(connectionString, { useNewUrlParser: true, useUnifiedTopology: true });

async function connectToMongoDB(collectionName) {
    try {
        await client.connect();
        console.log('Conectado a MongoDB');
        return client.db(dbName).collection(collectionName);
    } catch (error) {
        console.error('Error al conectar a MongoDB:', error);
    }
}

async function closeMongoDB() {
    try {
        await client.close();
        console.log('Conexión cerrada a MongoDB');
    } catch (error) {
        console.error('Error al cerrar la conexión a MongoDB:', error);
    }
}

module.exports = { connectToMongoDB, closeMongoDB};

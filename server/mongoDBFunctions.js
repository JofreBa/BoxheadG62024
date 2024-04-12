const API_URL = 'http://your-backend-api-url'; // Update with your actual API URL

export async function fetchUsers() {
  try {
    const response = await axios.get(`${API_URL}/users`);
    return response.data;
  } catch (error) {
    console.error('Error fetching users:', error);
    throw error;
  }
}

async function printCollection(collection) {
    if (!collection) {
        throw new Error('La colección no está definida');
    }
    const users = await collection.find().toArray();
    console.log('Colección:', users);
    return users;
}

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

async function deleteUser(collection, userId) {
    if (!collection) {
        throw new Error('La colección no está definida');
    }

    const result = await collection.deleteOne({ _id: userId });
    console.log('Usuario eliminado:', result);
    return result;
}

async function updateGameStats(collection, gameStatsData) {
    if (!collection) {
        throw new Error('La colección no está definida');
    }

    const existingGameStats = await collection.findOne({});

    if (existingGameStats) {
        // If game stats already exist, update them
        const result = await collection.updateOne(
            {},
            { $set: { gameStats: gameStatsData } }
        );
        console.log('Estadísticas del juego actualizadas:', result);
        return result;
    } else {
        // If no game stats exist, insert them
        const result = await collection.insertOne({ gameStats: gameStatsData });
        console.log('Nuevas estadísticas del juego creadas:', result);
        return result;
    }
    
}
async function uploadImage(collection, image) {
    if (!collection) {
        throw new Error('La colección no está definida');
    }
    const existingImage = await collection.findOne({ type: 'image' });

    if (existingImage) {
        // If an image already exists, update it
        const result = await collection.updateOne(
            { type: 'image' },
            { $set: { data: image } }
        );
        console.log('Imagen actualizada:', result);
        return result;
    } else {
        // If no image exists, insert it
        const result = await collection.insertOne({ type: 'image', data: image });
        console.log('Nueva imagen creada:', result);
        return result;
    }
}

async function getImage(collection) {
    if (!collection) {
        throw new Error('La colección no está definida');
    }
    const imageDoc = await collection.findOne({ type: 'image' });
    if (imageDoc) {
        return imageDoc.data;
    } else {
        console.log('No se encontró ninguna imagen en la colección.');
        return null;
    }
}

module.exports = { getImage, uploadImage, printCollection, updateUser, generateUniqueId, createUser, deleteUser, updateGameStats, fetchUsers };
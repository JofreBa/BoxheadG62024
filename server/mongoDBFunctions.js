async function printCollection(collection) {
    if (!collection) {
        throw new Error('La colección no está definida');
    }
    const users = await collection.find().toArray();
    console.log('Colección de usuarios:', users);
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

module.exports = { printCollection, updateUser, generateUniqueId, createUser, deleteUser };

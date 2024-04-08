<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div>
    <h1>Lista de Usuarios</h1>
    <v-list
      v-if="usuarios.length"
      dense
      style="max-height: 400px; overflow-y: auto"
    >
      <v-subheader>Usuarios:</v-subheader>
      <v-list-item v-for="(usuario, index) in usuarios" :key="index">
        <v-row v-if="!usuario.editando">
          <v-col>
            <!-- Contenido del usuario -->
            <v-list-item-content>
              <v-list-item-title>{{ usuario.name }}</v-list-item-title>
              <v-list-item-subtitle>{{ usuario.email }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-col>
          <v-col cols="auto">
            <!-- Botones de acciones -->
            <v-list-item-action>
              <!-- Botón de editar -->
              <v-btn icon @click="mostrarEdicion(usuario)">
                <v-icon>mdi-pencil</v-icon>
              </v-btn>
            </v-list-item-action>
            <v-list-item-action>
              <!-- Botón de eliminar -->
              <v-btn icon @click="eliminarUsuario(usuario)">
                <v-icon>mdi-delete</v-icon>
              </v-btn>
            </v-list-item-action>
          </v-col>
        </v-row>
        <!-- Menú de edición -->
        <v-row v-else>
          <v-col>
            <v-text-field
              v-model="usuario.nombreEditado"
              label="Nombre"
            ></v-text-field>
          </v-col>
          <v-col>
            <v-text-field
              v-model="usuario.emailEditado"
              label="Email"
            ></v-text-field>
          </v-col>
          <v-col cols="auto">
            <v-btn @click="guardarEdicion(usuario)">Guardar</v-btn>
            <v-btn @click="cancelarEdicion(usuario)">Cancelar</v-btn>
          </v-col>
        </v-row>
      </v-list-item>
    </v-list>
    <div v-else>No hay usuarios para mostrar</div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      usuarios: [],
      usuarioSeleccionado: null,
    };
  },
  mounted() {
    this.imprimirUsuarios();
  },
  methods: {
    async imprimirUsuarios() {
      try {
        const response = await axios.get(
          "http://localhost:3100/print-collection"
        );
        console.log(response.data.data); // Verifica el formato de los datos
        this.usuarios = response.data.data[0].users.map((usuario) => ({
          ...usuario,
          editando: false,
          nombreEditado: usuario.name,
          emailEditado: usuario.email,
        }));
      } catch (error) {
        console.error("Error al imprimir usuarios:", error);
        // Manejo de errores si la solicitud falla
      }
    },
    mostrarEdicion(usuario) {
      // Marca el usuario como editando
      usuario.editando = true;
    },
    async guardarEdicion(usuario) {
      try {
        const response = await axios.put(
          `http://localhost:3100/users/${usuario._id}`,
          {
            name: usuario.nombreEditado,
            email: usuario.emailEditado,
          },
          {
            headers: {
              "Content-Type": "application/json",
            },
          }
        );
        console.log(
          "Respuesta del servidor al actualizar usuario:",
          response.data
        );
        // Actualizar localmente si la solicitud fue exitosa
        usuario.name = usuario.nombreEditado;
        usuario.email = usuario.emailEditado;
        usuario.editando = false;
      } catch (error) {
        console.error("Error al actualizar usuario:", error);
        // Manejo de errores si la solicitud falla
      }
    },
    

    cancelarEdicion(usuario) {
      // Cancela la edición, restaura los valores originales
      usuario.nombreEditado = usuario.name;
      usuario.emailEditado = usuario.email;
      usuario.editando = false;
    },
    async eliminarUsuario(usuario) {
      console.log("Eliminar usuario:", usuario);
      // Implementa la lógica para eliminar un usuario
    },
  },
};
</script>

<style scoped>
/* Estilos para la lista */
.v-list-item {
  padding-top: 8px; /* Ajusta el espacio entre los elementos */
  padding-bottom: 8px; /* Ajusta el espacio entre los elementos */
}

.v-col {
  display: flex;
  align-items: center;
}
</style>

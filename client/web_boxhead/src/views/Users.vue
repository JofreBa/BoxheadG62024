<template>
  <div>
    <h1>Lista de Usuarios</h1>
    <v-list v-if="usuarios.length" dense style="max-height: 400px; overflow-y: auto">
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
            <v-text-field v-model="usuario.nombreEditado" label="Nombre"></v-text-field>
          </v-col>
          <v-col>
            <v-text-field v-model="usuario.emailEditado" label="Email"></v-text-field>
          </v-col>
          <v-col cols="auto">
            <v-btn @click="guardarEdicion(usuario)">Guardar</v-btn>
            <v-btn @click="cancelarEdicion(usuario)">Cancelar</v-btn>
          </v-col>
        </v-row>
      </v-list-item>
    </v-list>
    <div v-else>No hay usuarios para mostrar</div>
    <!-- Botón para crear nuevo usuario -->
    <v-btn @click="mostrarFormularioCrear">Crear Nuevo Usuario</v-btn>

    <!-- Formulario para crear usuario -->
    <v-row v-if="mostrarCrearFormulario">
      <v-col>
        <v-text-field v-model="nombreNuevoUsuario" label="Nombre"></v-text-field>
      </v-col>
      <v-col>
        <v-text-field v-model="emailNuevoUsuario" label="Email"></v-text-field>
      </v-col>
      <v-col cols="auto">
        <v-btn @click="crearUsuarioNuevo">Crear</v-btn>
        <v-btn @click="cancelarCrearUsuario">Cancelar</v-btn>
      </v-col>
    </v-row>
  </div>
  <div>
    <v-dialog v-model="dialogConfirm" max-width="500">
      <v-card>
        <v-card-title>Confirmar Eliminación</v-card-title>
        <v-card-text>
          ¿Estás seguro que deseas eliminar a {{usuarioEliminarName}}?
        </v-card-text>
        <v-card-actions>
          <v-btn color="error" @click="eliminarConfirmado">Eliminar</v-btn>
          <v-btn @click="cancelarEliminacion">Cancelar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      usuarios: [],
      usuarioSeleccionado: null,
      nombreNuevoUsuario: "",
      emailNuevoUsuario: "",
      mostrarCrearFormulario: false,
      dialogConfirm: false, 
      usuarioEliminar: null,
      usuarioEliminarName:''
    };
  },
  mounted() {
    this.imprimirUsuarios();
  },
  methods: {
    async imprimirUsuarios() {
      try {
        const response = await axios.get("http://localhost:3100/print-collection");
        console.log(response.data); // Verifica el formato de los datos
        if (response.data.success && Array.isArray(response.data.data)) {
          // Verificar si la respuesta es exitosa y si 'data' es un array
          this.usuarios = response.data.data.map((usuario) => {
            if (usuario.users) {
              // Si hay un campo 'users', asumimos que es el formato correcto
              return {
                _id: usuario._id,
                name: usuario.users.name,
                email: usuario.users.email,
                editando: false,
                nombreEditado: usuario.users.name,
                emailEditado: usuario.users.email,
              };
            } else {
              // Si no, asumimos que es un objeto de usuario directamente
              return {
                _id: usuario._id,
                name: usuario.name,
                email: usuario.email,
                editando: false,
                nombreEditado: usuario.name,
                emailEditado: usuario.email,
              };
            }
          });
        } else {
          console.error("Error al imprimir usuarios: Formato de datos incorrecto");
        }
      } catch (error) {
        console.error("Error al imprimir usuarios:", error);
      }
    },


    mostrarEdicion(usuario) {
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
        const index = this.usuarios.findIndex((u) => u._id === usuario._id);
        if (index !== -1) {
          this.usuarios[index].name = usuario.nombreEditado;
          this.usuarios[index].email = usuario.emailEditado;
          this.usuarios[index].editando = false;
        }
      } catch (error) {
        console.error("Error al actualizar usuario:", error);
      }
    },
    cancelarEdicion(usuario) {
      usuario.nombreEditado = usuario.name;
      usuario.emailEditado = usuario.email;
      usuario.editando = false;
    },
    async eliminarUsuario(usuario) {

      console.log("Eliminar usuario:", usuario);
    },
    mostrarFormularioCrear() {
      this.mostrarCrearFormulario = true;
    },
    cancelarCrearUsuario() {
      this.mostrarCrearFormulario = false;
      this.nombreNuevoUsuario = "";
      this.emailNuevoUsuario = "";
    },
    async crearUsuarioNuevo() {
      try {
        const response = await axios.post(
          "http://localhost:3100/users",
          {
            name: this.nombreNuevoUsuario,
            email: this.emailNuevoUsuario,
          },
          {
            headers: {
              "Content-Type": "application/json",
            },
          }
        );

        console.log("Respuesta del servidor al crear nuevo usuario:", response.data);

        // Actualizar la lista local de usuarios si la solicitud fue exitosa
        if (response.data.success) {
          this.usuarios.push({
            _id: response.data.data.insertedId,
            name: this.nombreNuevoUsuario,
            email: this.emailNuevoUsuario,
          });

          this.cancelarCrearUsuario();
        }
      } catch (error) {
        console.error("Error al crear nuevo usuario:", error);
      }
    },

     async eliminarUsuario(usuario) {
      // Mostrar diálogo de confirmación y guardar el usuario a eliminar
      this.dialogConfirm = true;
      this.usuarioEliminar = usuario;
      this.usuarioEliminarName = usuario.name;
      
    },

    async eliminarConfirmado() {
      try {
        const response = await axios.delete(
          `http://localhost:3100/users/${this.usuarioEliminar._id}`
        );
        console.log(
          "Respuesta del servidor al eliminar usuario:",
          response.data
        );
        // Eliminar localmente si la solicitud fue exitosa
        this.usuarios = this.usuarios.filter(
          (usuario) => usuario._id !== this.usuarioEliminar._id
        );
        // Cerrar el diálogo y limpiar el usuario a eliminar
        this.dialogConfirm = false;
        this.usuarioEliminar = null;
      } catch (error) {
        console.error("Error al eliminar usuario:", error);
      }
    },

    cancelarEliminacion() {
      // Cancelar la eliminación, cerrar el diálogo y limpiar el usuario a eliminar
      this.dialogConfirm = false;
      this.usuarioEliminar = null;
      this.usuarioEliminarName = '';
    },
  },
};
</script>

<style scoped>
.v-list-item {
  padding-top: 8px;
  padding-bottom: 8px;
}

.v-col {
  display: flex;
  align-items: center;
}
</style>

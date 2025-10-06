package model;

import java.io.*;

public class GestorUsuarios {
    private static final String RUTA_USUARIOS = "data/";

    // Guarda un usuario en un archivo .dat
    public static void guardarUsuario(Usuario usuario) {
        try {
            File dir = new File(RUTA_USUARIOS);
            if (!dir.exists()) dir.mkdirs();

            File archivo = new File(RUTA_USUARIOS + "user_" + usuario.getIdUsuario() + ".dat");
            FileOutputStream fileOut = new FileOutputStream(archivo);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(usuario);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.err.println("❌ Error al guardar el usuario \"" + usuario.getIdUsuario() + "\":");
            e.printStackTrace();
        }
    }

    // Carga un usuario a partir de su ID
    public static Usuario cargarUsuario(String idUsuario) {
        try {
            File archivo = new File(RUTA_USUARIOS + "user_" + idUsuario + ".dat");
            if (!archivo.exists()) return null;

            FileInputStream fileIn = new FileInputStream(archivo);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Usuario usuario = (Usuario) in.readObject();
            in.close();
            fileIn.close();
            return usuario;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Error al cargar el usuario \"" + idUsuario + "\":");
            e.printStackTrace();
            return null;
        }
    }

    // Comprueba si un usuario ya existe
    public static boolean existeUsuario(String idUsuario) {
        File archivo = new File(RUTA_USUARIOS + "user_" + idUsuario + ".dat");
        return archivo.exists();
    }

    // 🔥 Borra todos los archivos de usuarios
    public static void borrarTodosLosUsuarios() {
        File dir = new File(RUTA_USUARIOS);
        if (dir.exists() && dir.isDirectory()) {
            File[] archivos = dir.listFiles((d, name) -> name.startsWith("user_") && name.endsWith(".dat"));
            if (archivos != null) {
                for (File f : archivos) {
                    if (f.delete()) {
                        System.out.println("✅ Usuario eliminado: " + f.getName());
                    } else {
                        System.err.println("❌ No se pudo eliminar: " + f.getName());
                    }
                }
            }
        }
    }
}
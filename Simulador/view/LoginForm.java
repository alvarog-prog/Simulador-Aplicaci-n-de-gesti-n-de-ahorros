package view;

import model.Usuario;
import model.GestorUsuarios;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {

    private JTextField campoID;
    private JPasswordField campoPassword;

    public LoginForm() {
        setTitle("Iniciar sesión");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        campoID = new JTextField();
        campoPassword = new JPasswordField();

        panel.add(new JLabel("ID de usuario:"));
        panel.add(campoID);
        panel.add(new JLabel("Contraseña:"));
        panel.add(campoPassword);

        JButton btnLogin = new JButton("Iniciar sesión");
        btnLogin.addActionListener(e -> iniciarSesion());

        add(panel, BorderLayout.CENTER);
        add(btnLogin, BorderLayout.SOUTH);
    }

    private void iniciarSesion() {
        String id = campoID.getText().trim();
        String password = new String(campoPassword.getPassword()).trim();

        if (id.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduce tu ID y contraseña.");
            return;
        }

        if (!GestorUsuarios.existeUsuario(id)) {
            JOptionPane.showMessageDialog(this, "El usuario no existe.");
            return;
        }

        try {
            Usuario usuario = GestorUsuarios.cargarUsuario(id);
            if (usuario.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(this, "Bienvenido/a, " + usuario.getIdUsuario() + "!");
                dispose();
                new mainWindow(usuario);
            } else {
                JOptionPane.showMessageDialog(this, "Contraseña incorrecta.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar el usuario.");
            ex.printStackTrace();
        }
    }
}
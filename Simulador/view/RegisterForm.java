package view;

import model.Usuario;
import model.GestorUsuarios;

import javax.swing.*;
import java.awt.*;

public class RegisterForm extends JFrame {

    private JTextField campoID;
    private JPasswordField campoPassword;

    public RegisterForm() {
        setTitle("Registro de nuevo usuario");
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

        panel.add(new JLabel("Crea tu ID de usuario:"));
        panel.add(campoID);
        panel.add(new JLabel("Crea una contraseña:"));
        panel.add(campoPassword);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrarUsuario());

        add(panel, BorderLayout.CENTER);
        add(btnRegistrar, BorderLayout.SOUTH);
    }

    private void registrarUsuario() {
        String id = campoID.getText().trim();
        String password = new String(campoPassword.getPassword()).trim();

        if (id.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa ambos campos.");
            return;
        }

        if (GestorUsuarios.existeUsuario(id)) {
            JOptionPane.showMessageDialog(this, "Ese ID ya existe. Elige otro.");
            return;
        }

        Usuario nuevo = new Usuario(id, password);
        GestorUsuarios.guardarUsuario(nuevo);
        JOptionPane.showMessageDialog(this, "Usuario registrado con éxito. Ya puedes iniciar sesión.");
        dispose();
        new LoginWindow();
    }
}
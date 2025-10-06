package view;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {

    public LoginWindow() {
        setTitle("Bienvenido");
        setSize(300, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titulo = new JLabel("Gestor de Economía del Hogar", JLabel.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(titulo);

        JButton btnLogin = new JButton("Iniciar sesión");
        JButton btnRegister = new JButton("Registrarse");

        btnLogin.addActionListener(e -> {
            dispose();
            new LoginForm();
        });

        btnRegister.addActionListener(e -> {
            dispose();
            new RegisterForm();
        });

        panel.add(btnLogin);
        panel.add(btnRegister);

        add(panel);
    }
}
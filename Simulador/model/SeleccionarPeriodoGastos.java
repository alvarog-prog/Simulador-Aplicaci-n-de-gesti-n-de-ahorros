package model;

import javax.swing.*;
import java.awt.*;

public class SeleccionarPeriodoGastos extends JFrame {

    private Usuario usuario;

    public SeleccionarPeriodoGastos(Usuario usuario) {
        this.usuario = usuario;
        initGUI();
    }

    private void initGUI() {
        setTitle("Seleccionar periodo");
        setSize(400, 150);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("¿Qué periodo quieres visualizar?");
        JComboBox<String> combo = new JComboBox<>(new String[]{
            "Ver todos los gastos",
            "Ver gastos del último mes",
            "Ver gastos del último año"
        });

        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.addActionListener(e -> {
            String seleccion = (String) combo.getSelectedItem();
            dispose(); // Cerramos esta ventana
            new TablaGastosFiltrada(usuario, seleccion); // Pasamos el usuario y el filtro
        });

        panel.add(label, BorderLayout.NORTH);
        panel.add(combo, BorderLayout.CENTER);
        panel.add(btnContinuar, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}
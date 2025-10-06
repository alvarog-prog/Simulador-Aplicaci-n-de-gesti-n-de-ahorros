package model;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VerObjetivosCompletados extends JFrame {

    public VerObjetivosCompletados(Usuario usuario) {
        setTitle("Objetivos Completados");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        List<Objetivo> completados = usuario.getObjetivosCompletados();
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        if (completados.isEmpty()) {
            JLabel lblVacio = new JLabel("Aún no has completado ningún objetivo.");
            lblVacio.setFont(new Font("SansSerif", Font.ITALIC, 16));
            lblVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelContenido.add(lblVacio);
        } else {
            for (Objetivo obj : completados) {
                JLabel lbl = new JLabel("✅ " + obj.getDescripcion() + " - " +
                        String.format("%.2f €", obj.getCantidadObjetivo()));
                lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
                panelContenido.add(lbl);
                panelContenido.add(Box.createVerticalStrut(10));
            }
        }

        JScrollPane scrollPane = new JScrollPane(panelContenido);
        add(scrollPane);
        setVisible(true);
    }
}
package model;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class VerObjetivos extends JFrame {

    private Usuario usuario;

    public VerObjetivos(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Mis Objetivos");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<Objetivo> listaObjetivos = usuario.getObjetivos();

        if (listaObjetivos.isEmpty()) {
            JLabel lblVacio = new JLabel("No has registrado ningún objetivo todavía.");
            lblVacio.setFont(new Font("SansSerif", Font.ITALIC, 16));
            lblVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelContenido.add(lblVacio);
        } else {
            Iterator<Objetivo> iterator = listaObjetivos.iterator();
            while (iterator.hasNext()) {
                Objetivo objetivo = iterator.next();

                JPanel fila = new JPanel(new BorderLayout(10, 10));
                fila.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                fila.setBackground(new Color(245, 250, 250));

                String texto = objetivo.getDescripcion() + " - " + objetivo.getCantidadObjetivo() + "€";
                JLabel lblDescripcion = new JLabel(texto);
                lblDescripcion.setPreferredSize(new Dimension(200, 25));

                JProgressBar barra = new JProgressBar(0, 100);
                int porcentaje = (int) objetivo.getPorcentajeCompletado();
                barra.setValue(porcentaje);
                barra.setStringPainted(true);
                barra.setString(porcentaje + "%");

                JPanel panelIzquierdo = new JPanel(new BorderLayout());
                panelIzquierdo.add(lblDescripcion, BorderLayout.NORTH);

                JButton btnAñadir = new JButton("➕ Añadir ahorro");
                btnAñadir.setFont(new Font("SansSerif", Font.PLAIN, 11));
                btnAñadir.addActionListener(e -> {
                    String input = JOptionPane.showInputDialog(this,
                            "¿Cuánto quieres añadir al objetivo \"" + objetivo.getDescripcion() + "\"?");
                    if (input != null) {
                        try {
                            double cantidad = Double.parseDouble(input);
                            if (cantidad <= 0) throw new NumberFormatException();
                            objetivo.setCantidadActual(objetivo.getCantidadActual() + cantidad);

                            // Si se completa, mover a lista de completados
                            if (objetivo.getPorcentajeCompletado() >= 100.0) {
                                usuario.moverACompletados(objetivo);
                                JOptionPane.showMessageDialog(this, "🎯 ¡Objetivo completado!");
                            }

                            dispose();
                            new VerObjetivos(usuario);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Introduce una cantidad válida.");
                        }
                    }
                });

                panelIzquierdo.add(btnAñadir, BorderLayout.SOUTH);
                fila.add(panelIzquierdo, BorderLayout.WEST);
                fila.add(barra, BorderLayout.CENTER);

                panelContenido.add(fila);
                panelContenido.add(Box.createVerticalStrut(10));
            }
        }

        // Botón para añadir nuevo objetivo
        JButton btnNuevo = new JButton("➕ Crear nuevo objetivo");
        btnNuevo.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnNuevo.addActionListener(e -> {
            dispose();
            new NuevoObjetivo(usuario);
        });

        // Botón para ver objetivos completados
        JButton btnCompletados = new JButton("✅ Ver objetivos completados");
        btnCompletados.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCompletados.addActionListener(e -> new VerObjetivosCompletados(usuario));

        panelContenido.add(Box.createVerticalStrut(20));
        panelContenido.add(btnNuevo);
        panelContenido.add(Box.createVerticalStrut(10));
        panelContenido.add(btnCompletados);

        JScrollPane scroll = new JScrollPane(panelContenido);
        add(scroll);
        setVisible(true);
    }
}
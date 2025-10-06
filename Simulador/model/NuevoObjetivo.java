package model;

import javax.swing.*;
import java.awt.*;

public class NuevoObjetivo extends JFrame {

    private Usuario usuario;

    public NuevoObjetivo(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Nuevo Objetivo");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblNombre = new JLabel("Nombre del objetivo:");
        JTextField txtNombre = new JTextField();

        JLabel lblCantidad = new JLabel("Cantidad a ahorrar (€):");
        JTextField txtCantidad = new JTextField();

        JLabel lblDescripcion = new JLabel("Descripción (opcional):");
        JTextField txtDescripcion = new JTextField();

        JButton btnGuardar = new JButton("Guardar objetivo");

        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String cantidadTexto = txtCantidad.getText().trim();
            String descripcion = txtDescripcion.getText().trim();

            if (nombre.isEmpty() || cantidadTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, rellena los campos obligatorios.");
                return;
            }

            try {
                double cantidad = Double.parseDouble(cantidadTexto);
                if (cantidad <= 0) throw new NumberFormatException();

                Objetivo nuevo = new Objetivo(nombre, cantidad, descripcion);
                usuario.agregarObjetivo(nuevo);

                JOptionPane.showMessageDialog(this, "Objetivo guardado correctamente.");
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Introduce una cantidad válida mayor que 0.");
            }
        });

        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(lblCantidad);
        panel.add(txtCantidad);
        panel.add(lblDescripcion);
        panel.add(txtDescripcion);
        panel.add(new JLabel());  // espacio vacío
        panel.add(btnGuardar);

        add(panel);
        setVisible(true);
    }
}
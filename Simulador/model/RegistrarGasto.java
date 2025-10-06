package model;

import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.*;

public class RegistrarGasto extends JFrame {

    private JSpinner spinnerFecha;
    private Usuario usuario;

    public RegistrarGasto(Usuario usuario) {
        this.usuario = usuario;
        initGUI();
    }

    private void initGUI() {
        setTitle("Registrar Gasto");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblCategoria = new JLabel("Categoría:");
        JComboBox<String> comboCategoria = new JComboBox<>(new String[]{"Comida", "Ocio", "Transporte", "Otros"});

        JLabel lblCantidad = new JLabel("Cantidad:");
        JTextField txtCantidad = new JTextField();

        JLabel lblFecha = new JLabel("Fecha:");
        Date hoy = new Date();
        SpinnerDateModel dateModel = new SpinnerDateModel(hoy, null, hoy, java.util.Calendar.DAY_OF_MONTH);
        spinnerFecha = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy");
        spinnerFecha.setEditor(dateEditor);

        JLabel lblDescripcion = new JLabel("Descripción:");
        JTextField txtDescripcion = new JTextField();

        JButton btnGuardar = new JButton("Guardar");

        btnGuardar.addActionListener(e -> {
            String categoria = (String) comboCategoria.getSelectedItem();
            String cantidadTexto = txtCantidad.getText();
            String descripcion = txtDescripcion.getText();

            try {
                double cantidad = Double.parseDouble(cantidadTexto);
                if (cantidad <= 0) throw new NumberFormatException();

                Date fechaSeleccionada = (Date) spinnerFecha.getValue();
                LocalDate fechaLocal = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (fechaLocal.isAfter(LocalDate.now())) {
                    JOptionPane.showMessageDialog(this, "No puedes registrar un gasto en el futuro.");
                    return;
                }

                String fecha = fechaLocal.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                // Crear gasto y guardar en el usuario
                Gasto nuevo = new Gasto(categoria, cantidad, fecha, descripcion);
                usuario.agregarGasto(nuevo);

                JOptionPane.showMessageDialog(this, "Gasto guardado correctamente.");
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Introduce una cantidad válida.");
            }
        });

        panel.add(lblCategoria);
        panel.add(comboCategoria);
        panel.add(lblCantidad);
        panel.add(txtCantidad);
        panel.add(lblFecha);
        panel.add(spinnerFecha);
        panel.add(lblDescripcion);
        panel.add(txtDescripcion);
        panel.add(new JLabel()); // celda vacía
        panel.add(btnGuardar);

        add(panel);
        setVisible(true);
    }
}
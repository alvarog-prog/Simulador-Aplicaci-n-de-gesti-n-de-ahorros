package model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TablaGastosFiltrada extends JFrame {
    private JTable tablaGastos;
    private JTable tablaTotales;
    private JLabel totalGeneralLabel;
    private String periodoSeleccionado;
    private Usuario usuario;

    public TablaGastosFiltrada(Usuario usuario, String periodoSeleccionado) {
        this.usuario = usuario;
        this.periodoSeleccionado = periodoSeleccionado;
        initGUI();
    }

    private void initGUI() {
        setTitle("Tabla de Gastos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        // Tabla de gastos detallados
        String[] columnas = {"Fecha", "Descripción", "Categoría", "Cantidad"};
        DefaultTableModel modeloGastos = new DefaultTableModel(columnas, 0);
        tablaGastos = new JTable(modeloGastos);
        JScrollPane scrollGastos = new JScrollPane(tablaGastos);
        cargarGastosFiltrados(modeloGastos);

        // Tabla de totales por categoría
        String[] categorias = {"Comida", "Ocio", "Transporte", "Otros"};
        DefaultTableModel modeloTotales = new DefaultTableModel(categorias, 0);
        tablaTotales = new JTable(modeloTotales);
        JScrollPane scrollTotales = new JScrollPane(tablaTotales);

        // Total general
        totalGeneralLabel = new JLabel("Total gastado: 0,00 €");
        totalGeneralLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalGeneralLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        calcularTotales(modeloTotales);

        panelPrincipal.add(scrollGastos);
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(scrollTotales);
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(totalGeneralLabel);

        add(panelPrincipal);
        setVisible(true);
    }

    private void cargarGastosFiltrados(DefaultTableModel modelo) {
        List<Gasto> listaGastos = usuario.getGastos();
        LocalDate hoy = LocalDate.now();

        for (Gasto g : listaGastos) {
            LocalDate fechaGasto = g.getFecha();
            boolean incluir = switch (periodoSeleccionado) {
                case "Ver gastos del último mes" ->
                    fechaGasto.getMonth() == hoy.getMonth() && fechaGasto.getYear() == hoy.getYear();
                case "Ver gastos del último año" ->
                    fechaGasto.isAfter(hoy.minusYears(1));
                default -> true;
            };
            if (incluir) {
                modelo.addRow(new Object[]{
                    g.getFechaFormateada(),
                    g.getDescripcion(),
                    g.getCategoria(),
                    String.format("%.2f €", g.getCantidad())
                });
            }
        }
    }

    private void calcularTotales(DefaultTableModel modeloTotales) {
        List<Gasto> listaGastos = usuario.getGastos();
        LocalDate hoy = LocalDate.now();
        Map<String, Double> totales = new HashMap<>();
        double totalGeneral = 0.0;

        for (Gasto g : listaGastos) {
            LocalDate fechaGasto = g.getFecha();
            boolean incluir = switch (periodoSeleccionado) {
                case "Ver gastos del último mes" ->
                    fechaGasto.getMonth() == hoy.getMonth() && fechaGasto.getYear() == hoy.getYear();
                case "Ver gastos del último año" ->
                    fechaGasto.isAfter(hoy.minusYears(1));
                default -> true;
            };
            if (incluir) {
                totales.put(g.getCategoria(),
                    totales.getOrDefault(g.getCategoria(), 0.0) + g.getCantidad());
                totalGeneral += g.getCantidad();
            }
        }

        Object[] filaTotales = new Object[]{
            String.format("%.2f €", totales.getOrDefault("Comida", 0.0)),
            String.format("%.2f €", totales.getOrDefault("Ocio", 0.0)),
            String.format("%.2f €", totales.getOrDefault("Transporte", 0.0)),
            String.format("%.2f €", totales.getOrDefault("Otros", 0.0))
        };

        modeloTotales.addRow(filaTotales);
        totalGeneralLabel.setText("Total gastado: " + String.format("%.2f €", totalGeneral));
    }
}
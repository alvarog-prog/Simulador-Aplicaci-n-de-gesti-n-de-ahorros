package model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class VerTablaGastos extends JFrame {

    private JComboBox<String> comboPeriodo;
    private JTable tabla;
    private JPanel panelTotales;
    private JLabel lblTotalGeneral;

    public VerTablaGastos() {
        setTitle("Tabla de Gastos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 🔽 Filtro superior
        comboPeriodo = new JComboBox<>(new String[]{"Todo", "Este mes", "Último año"});
        comboPeriodo.addActionListener(e -> actualizarTabla());

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Mostrar:"));
        panelSuperior.add(comboPeriodo);
        add(panelSuperior, BorderLayout.NORTH);

        // 🧾 Tabla de gastos
        tabla = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // 🔢 Panel inferior para totales
        panelTotales = new JPanel(new BorderLayout());
        lblTotalGeneral = new JLabel("Total gastado: 0 €", JLabel.RIGHT);
        lblTotalGeneral.setFont(new Font("SansSerif", Font.BOLD, 14));
        panelTotales.add(lblTotalGeneral, BorderLayout.EAST);
        add(panelTotales, BorderLayout.SOUTH);

        actualizarTabla(); // cargar tabla inicial
        setVisible(true);
    }

    private void actualizarTabla() {
        // 1. Obtener lista de gastos
        List<Gasto> listaGastos = BaseDatosTemporal.obtenerGastos();

        // 2. Filtrar según el periodo (en esta versión se muestran todos)
        String periodoSeleccionado = (String) comboPeriodo.getSelectedItem();
        // (Pendiente implementar filtro real por fecha)

        // 3. Agrupar gastos por categoría
        Map<String, List<Gasto>> mapaPorCategoria = new LinkedHashMap<>();
        Set<String> categorias = new LinkedHashSet<>();

        
        List<Gasto> filtrados = new ArrayList<>();
        LocalDate hoy = LocalDate.now();

        switch (periodoSeleccionado) {
            case "Este mes":
                for (Gasto g : listaGastos) {
                    if (g.getFecha().getMonth() == hoy.getMonth() && g.getFecha().getYear() == hoy.getYear()) {
                        filtrados.add(g);
                    }
                }
                break;
            case "Último año":
                for (Gasto g : listaGastos) {
                    if (g.getFecha().isAfter(hoy.minusYears(1))) {
                        filtrados.add(g);
                    }
                }
                break;
            default:
                filtrados = listaGastos;
        }
        
        
        for (Gasto gasto : listaGastos) {
            String categoria = gasto.getCategoria();
            categorias.add(categoria);
            mapaPorCategoria.putIfAbsent(categoria, new ArrayList<>());
            mapaPorCategoria.get(categoria).add(gasto);
        }

        // 4. Crear modelo de tabla
        List<String> columnas = new ArrayList<>(categorias);
        columnas.add(0, "Fecha"); // primera columna = Fecha

        DefaultTableModel modelo = new DefaultTableModel(columnas.toArray(), 0);

        // 5. Crear filas con gastos por fecha
        Map<String, Double> totalPorCategoria = new HashMap<>();
        double totalGeneral = 0;

        for (Gasto gasto : listaGastos) {
            Object[] fila = new Object[columnas.size()];
            fila[0] = gasto.getFecha();

            for (int i = 1; i < columnas.size(); i++) {
                String cat = columnas.get(i);
                if (cat.equals(gasto.getCategoria())) {
                    fila[i] = gasto.getCantidad() + " €";
                    totalPorCategoria.put(cat, totalPorCategoria.getOrDefault(cat, 0.0) + gasto.getCantidad());
                    totalGeneral += gasto.getCantidad();
                } else {
                	fila[i] = "—";
                }
            }

            modelo.addRow(fila);
        }

        // 6. Añadir fila de totales por categoría
        Object[] filaTotales = new Object[columnas.size()];
        filaTotales[0] = "Total por categoría:";
        for (int i = 1; i < columnas.size(); i++) {
            String cat = columnas.get(i);
            double totalCat = totalPorCategoria.getOrDefault(cat, 0.0);
            filaTotales[i] = totalCat == 0 ? "—" : String.format("%.2f €", totalCat);
        }
        modelo.addRow(filaTotales);

        tabla.setModel(modelo);
        lblTotalGeneral.setText("Total gastado: " + String.format("%.2f €", totalGeneral));
    }
}

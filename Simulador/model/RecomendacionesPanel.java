package model;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class RecomendacionesPanel extends JFrame {

    private Usuario usuario;
    private JTextArea areaGastos;
    private JComboBox<String> comboObjetivos;
    private JComboBox<String> comboCategoria;
    private JTextArea areaRecomendacion;

    public RecomendacionesPanel(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Recomendaciones Personalizadas");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panelTop = new JPanel(new BorderLayout(10, 10));

        areaGastos = new JTextArea(8, 40);
        areaGastos.setEditable(false);
        areaGastos.setBorder(BorderFactory.createTitledBorder("Gastos por Categoría (mayor a menor)"));
        mostrarGastosOrdenados();

        panelTop.add(new JScrollPane(areaGastos), BorderLayout.CENTER);

        JPanel panelOpciones = new JPanel(new GridLayout(3, 2, 10, 10));
        panelOpciones.setBorder(BorderFactory.createTitledBorder("Selecciona tus preferencias"));

        panelOpciones.add(new JLabel("Objetivo:"));
        comboObjetivos = new JComboBox<>(usuario.getObjetivos().stream()
                .map(Objetivo::getDescripcion)
                .toArray(String[]::new));
        panelOpciones.add(comboObjetivos);

        panelOpciones.add(new JLabel("Categoría a recortar:"));
        comboCategoria = new JComboBox<>(new String[]{"Comida", "Ocio", "Transporte", "Otros"});
        panelOpciones.add(comboCategoria);

        JButton btnGenerar = new JButton("Generar Recomendación");
        btnGenerar.addActionListener(e -> generarRecomendacion());

        panelOpciones.add(new JLabel());
        panelOpciones.add(btnGenerar);

        areaRecomendacion = new JTextArea(6, 40);
        areaRecomendacion.setLineWrap(true);
        areaRecomendacion.setWrapStyleWord(true);
        areaRecomendacion.setEditable(false);
        areaRecomendacion.setBorder(BorderFactory.createTitledBorder("Recomendación personalizada"));

        add(panelTop, BorderLayout.NORTH);
        add(panelOpciones, BorderLayout.CENTER);
        add(new JScrollPane(areaRecomendacion), BorderLayout.SOUTH);
    }

    private void mostrarGastosOrdenados() {
        Map<String, Double> mapa = new HashMap<>();

        for (Gasto g : usuario.getGastos()) {
            mapa.put(g.getCategoria(), mapa.getOrDefault(g.getCategoria(), 0.0) + g.getCantidad());
        }

        List<Map.Entry<String, Double>> lista = new ArrayList<>(mapa.entrySet());
        lista.sort((a, b) -> Double.compare(b.getValue(), a.getValue())); // De mayor a menor

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Double> entry : lista) {
            sb.append("• ").append(entry.getKey()).append(": ")
              .append(String.format("%.2f €", entry.getValue())).append("\n");
        }

        areaGastos.setText(sb.toString());
    }

    private void generarRecomendacion() {
        String objetivoDescripcion = (String) comboObjetivos.getSelectedItem();
        String categoria = (String) comboCategoria.getSelectedItem();

        // Buscar el objetivo seleccionado
        Objetivo objetivo = usuario.getObjetivos().stream()
                .filter(o -> o.getDescripcion().equals(objetivoDescripcion))
                .findFirst()
                .orElse(null);

        if (objetivo == null) {
            areaRecomendacion.setText("No se ha encontrado el objetivo seleccionado.");
            return;
        }

        double gastoCategoria = usuario.getGastos().stream()
                .filter(g -> g.getCategoria().equals(categoria))
                .mapToDouble(Gasto::getCantidad)
                .sum();

        if (gastoCategoria == 0) {
            areaRecomendacion.setText("No has registrado gastos en la categoría seleccionada.");
            return;
        }

        double cantidadObjetivo = objetivo.getCantidadObjetivo();

        double ahorroMensual = gastoCategoria * 0.20;
        int meses = (int) Math.ceil(cantidadObjetivo / ahorroMensual);

        String recomendacion = "📌 Objetivo: " + objetivoDescripcion + " (" + String.format("%.2f €", cantidadObjetivo) + ")\n" +
                "💡 Si reduces un 20% tus gastos en \"" + categoria + "\", podrías ahorrar aproximadamente " +
                String.format("%.2f €", ahorroMensual) + " al mes.\n" +
                "⏳ Alcanzarías tu objetivo en unos " + meses + " meses.";

        areaRecomendacion.setText(recomendacion);
    }
}
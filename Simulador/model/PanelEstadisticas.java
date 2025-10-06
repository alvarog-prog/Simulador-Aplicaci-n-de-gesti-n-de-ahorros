package model;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PanelEstadisticas extends JFrame {

    private Usuario usuario;

    public PanelEstadisticas(Usuario usuario) {
        this.usuario = usuario;

        setTitle("📊 Estadísticas - Gastos por Categoría");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(new GraficoBarras(usuario.getGastos()));
        setVisible(true);
    }

    // Panel interno para dibujar el gráfico
    private static class GraficoBarras extends JPanel {
        private Map<String, Double> gastosPorCategoria;

        public GraficoBarras(List<Gasto> gastos) {
            gastosPorCategoria = new HashMap<>();
            for (Gasto g : gastos) {
                gastosPorCategoria.put(g.getCategoria(),
                    gastosPorCategoria.getOrDefault(g.getCategoria(), 0.0) + g.getCantidad());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int x = 100, y = 50;
            int anchoMax = getWidth() - 200;
            int alturaBarra = 25;
            int separacion = 40;

            // calcular el gasto máximo
            double gastoMax = gastosPorCategoria.values().stream().mapToDouble(Double::doubleValue).max().orElse(1);

            for (Map.Entry<String, Double> entry : gastosPorCategoria.entrySet()) {
                String categoria = entry.getKey();
                double gasto = entry.getValue();
                int largoBarra = (int) ((gasto / gastoMax) * anchoMax);

                g2.setColor(new Color(100, 180, 255));
                g2.fillRect(x, y, largoBarra, alturaBarra);

                g2.setColor(Color.BLACK);
                g2.drawString(categoria + ": " + String.format("%.2f €", gasto), 10, y + 18);

                y += separacion;
            }
        }
    }
}
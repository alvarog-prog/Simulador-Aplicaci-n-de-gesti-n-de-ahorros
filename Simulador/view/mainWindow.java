package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import model.Usuario;
import model.VerObjetivos;
import model.GestorUsuarios;
import model.RecomendacionesPanel;
import model.RegistrarGasto;
import model.SeleccionarPeriodoGastos;
import model.VerTablaGastos;
import model.PanelEstadisticas;

public class mainWindow extends JFrame implements ActionListener {

    private JButton btnGasto;
    private JButton btnTabla;
    private JButton btnObjetivos;
    private JButton btnRecomendaciones;
    private JButton btnEstadisticas;        // NUEVO
    private JButton btnBorrarUsuarios;      // SOLO PARA DESARROLLO

    private Usuario usuario;

    public mainWindow(Usuario usuario) {
        this.usuario = usuario;
        initGUI();
    }

    private void initGUI() {
        this.setTitle("Controlador de gastos - Usuario: " + usuario.getIdUsuario());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel principal = new JPanel();
        this.add(principal);
        principal.setLayout(new BorderLayout(20, 20));
        principal.setBackground(new Color(240, 250, 250));

        JLabel titulo = new JLabel("MENU PRINCIPAL", JLabel.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        principal.add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(3, 3, 15, 15)); // Ampliado para 6 botones
        botones.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
        botones.setBackground(new Color(240, 250, 250));

        btnGasto = new JButton("Registrar Gasto");
        btnTabla = new JButton("Ver Tabla de Gastos");
        btnObjetivos = new JButton("Objetivos");
        btnRecomendaciones = new JButton("Recomendaciones");
        btnEstadisticas = new JButton("📊 Estadísticas");

        btnGasto.setFocusable(false);
        btnTabla.setFocusable(false);
        btnObjetivos.setFocusable(false);
        btnRecomendaciones.setFocusable(false);
        btnEstadisticas.setFocusable(false);

        btnGasto.addActionListener(this);
        btnTabla.addActionListener(this);
        btnObjetivos.addActionListener(this);
        btnRecomendaciones.addActionListener(this);
        btnEstadisticas.addActionListener(this);

        botones.add(btnGasto);
        botones.add(btnTabla);
        botones.add(btnObjetivos);
        botones.add(btnRecomendaciones);
        botones.add(btnEstadisticas);

        // SOLO EN MODO DESARROLLO
        boolean modoDesarrollador = true;
        if (modoDesarrollador) {
            btnBorrarUsuarios = new JButton("🗑️ Borrar todos los usuarios");
            btnBorrarUsuarios.setFocusable(false);
            btnBorrarUsuarios.setForeground(Color.RED);
            btnBorrarUsuarios.addActionListener(this);
            botones.add(btnBorrarUsuarios);
        }

        principal.add(botones, BorderLayout.CENTER);

        // Guardar usuario al cerrar
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    GestorUsuarios.guardarUsuario(usuario);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al guardar los datos del usuario.");
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGasto) {
            new RegistrarGasto(usuario);
        } else if (e.getSource() == btnTabla) {
            new SeleccionarPeriodoGastos(usuario);
        } else if (e.getSource() == btnObjetivos) {
            new VerObjetivos(usuario);
        } else if (e.getSource() == btnRecomendaciones) {
            new RecomendacionesPanel(usuario);
        } else if (e.getSource() == btnEstadisticas) {
            new PanelEstadisticas(usuario);
        } else if (e.getSource() == btnBorrarUsuarios) {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Seguro que quieres borrar todos los usuarios?\nEsta acción no se puede deshacer.",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                GestorUsuarios.borrarTodosLosUsuarios();
                JOptionPane.showMessageDialog(this, "Todos los usuarios han sido eliminados.");
            }
        }
    }
}
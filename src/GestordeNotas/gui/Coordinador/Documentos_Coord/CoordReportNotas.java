package GestordeNotas.gui.Coordinador.Documentos_Coord;

import GestordeNotas.database.CleverDB;
import GestordeNotas.gui.Principal.PrincipalCoordinador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CoordReportNotas extends JFrame {
    private JPanel CoordReportNotas;
    private JTable tablaCoordRepotes;
    private JButton reportePorEstudianteButton;
    private JButton reportePorMateriaButton;
    private JButton reportePorPeriodoButton;
    private JButton regresarButton;
    private JButton salirButton;

    public CoordReportNotas() {
        setTitle("Coordinador Reportes");
        setContentPane(CoordReportNotas);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Acción para mostrar reportes por estudiante
        reportePorEstudianteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarReportes("estudiante");
            }
        });

        // Acción para mostrar reportes por materia
        reportePorMateriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarReportes("materia");
            }
        });

        // Acción para mostrar reportes por periodo
        reportePorPeriodoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarReportes("periodo");
            }
        });

        // Regresar al menú principal
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PrincipalCoordinador().setVisible(true);
            }
        });

        // Cerrar
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    // Método para cargar reportes según tipo
    private void cargarReportes(String tipo) {
        try {
            Connection conexion = CleverDB.getConexion();
            String query = "SELECT r.id_reporte, u.nombre AS coordinador, r.tipo, r.fecha_generado, r.descripcion, r.ruta_archivo " +
                    "FROM reportes r " +
                    "JOIN usuarios u ON r.generado_por = u.id " +
                    "WHERE r.tipo = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setString(1, tipo);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID Reporte");
            model.addColumn("Generado Por");
            model.addColumn("Tipo");
            model.addColumn("Fecha");
            model.addColumn("Descripción");
            model.addColumn("Ruta Archivo");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id_reporte"),
                        rs.getString("coordinador"),
                        rs.getString("tipo"),
                        rs.getTimestamp("fecha_generado"),
                        rs.getString("descripcion"),
                        rs.getString("ruta_archivo")
                });
            }

            tablaCoordRepotes.setModel(model);

            rs.close();
            stmt.close();
            conexion.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar reportes: " + ex.getMessage());
        }
    }
}

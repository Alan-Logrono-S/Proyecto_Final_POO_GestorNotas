package GestordeNotas.gui.Estudiante.Documentacion_Estudiante;

import GestordeNotas.database.CleverDB;
import GestordeNotas.gui.Principal.PrincipalEstudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PrincipalDocumentacion_Estudiante extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JButton regresarButton;
    private JButton salirButton;
    private JPanel EstudianteCertificados;
    private JTable tablaCertificadosEstudiantes;
    private JButton CARGARCERTIFICACIONESButton;
    private JPanel EstudiantesNotificaciones;
    private JTable tablaCorreoEstudiantes;
    private JButton CARGARCORREOButton;

    private int idEstudiante;

    public PrincipalDocumentacion_Estudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;

        setContentPane(panel1);
        setTitle("Documentación - Estudiante");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cargar certificados
        CARGARCERTIFICACIONESButton.addActionListener(e -> cargarCertificados());

        // Cargar notificaciones/correo
        CARGARCORREOButton.addActionListener(e -> cargarNotificaciones());

        regresarButton.addActionListener(e -> {
            dispose();
            new PrincipalEstudiante().setVisible(true);
        });

        salirButton.addActionListener(e -> dispose());
    }

    private void cargarCertificados() {
        try (Connection con = CleverDB.getConexion()) {
            String query = "SELECT a.nombre AS asignatura, c.tipo, c.fecha_emision " +
                    "FROM certificados c " +
                    "LEFT JOIN asignaturas a ON c.id_asignatura = a.id_asignatura " +
                    "WHERE c.id_estudiante = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Asignatura");
            model.addColumn("Tipo");
            model.addColumn("Fecha Emisión");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("asignatura") != null ? rs.getString("asignatura") : "General",
                        rs.getString("tipo"),
                        rs.getDate("fecha_emision")
                });
            }
            tablaCertificadosEstudiantes.setModel(model);

            rs.close();
            stmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar certificados: " + ex.getMessage());
        }
    }

    private void cargarNotificaciones() {
        try (Connection con = CleverDB.getConexion()) {
            String query = "SELECT mensaje, fecha_envio, leido FROM notificaciones WHERE id_destinatario = ? ORDER BY fecha_envio DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Mensaje");
            model.addColumn("Fecha");
            model.addColumn("Leído");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("mensaje"),
                        rs.getTimestamp("fecha_envio"),
                        rs.getBoolean("leido") ? "Sí" : "No"
                });
            }
            tablaCorreoEstudiantes.setModel(model);

            rs.close();
            stmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar notificaciones: " + ex.getMessage());
        }
    }
}

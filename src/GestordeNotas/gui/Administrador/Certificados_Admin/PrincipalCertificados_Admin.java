package GestordeNotas.gui.Administrador.Certificados_Admin;

import GestordeNotas.gui.Principal.PrincipalAdmin;
import GestordeNotas.database.CleverDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;

public class PrincipalCertificados_Admin extends JFrame {

    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JPanel AdminCertificados;
    private JTable tablaAdminCertificados;
    private JButton VISUALIZARButton;
    private JButton DESCARGARButton;
    private JPanel AdminReportesSistem;
    private JTable tablaReportesAdmin;
    private JButton reportePorMateriaButton;
    private JButton reportePorEstudianteButton;
    private JButton reportePorPeriodoButton;
    private JButton regresarButton;
    private JButton salirDelSistemaButton;

    public PrincipalCertificados_Admin() {
        setContentPane(panel1); // este panel debe estar definido en tu .form
        setTitle("Certificados - Administrador");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Botón: Visualizar certificados
        VISUALIZARButton.addActionListener(e -> cargarCertificados());

        // Botón: Descargar certificados
        DESCARGARButton.addActionListener(e -> descargarCertificadoSeleccionado());

        // Botones de reportes
        reportePorMateriaButton.addActionListener(e -> generarReportePorMateria());
        reportePorEstudianteButton.addActionListener(e -> generarReportePorEstudiante());
        reportePorPeriodoButton.addActionListener(e -> generarReportePorPeriodo());

        // Botón: Regresar al panel de administración
        regresarButton.addActionListener(e -> {
            dispose();
            new PrincipalAdmin().setVisible(true);
        });

        // Botón: Salir del sistema
        salirDelSistemaButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro que quieres salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private void cargarCertificados() {
        try (Connection conexion = CleverDB.getConexion()) {
            String query = "SELECT c.id_certificado, u.nombre AS estudiante, a.nombre AS asignatura, c.tipo, c.ruta_archivo " +
                    "FROM certificados c " +
                    "JOIN usuarios u ON c.id_estudiante = u.id " +
                    "JOIN asignaturas a ON c.id_asignatura = a.id_asignatura";

            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID Certificado");
            model.addColumn("Estudiante");
            model.addColumn("Asignatura");
            model.addColumn("Tipo");
            model.addColumn("Ruta del Archivo");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id_certificado"),
                        rs.getString("estudiante"),
                        rs.getString("asignatura"),
                        rs.getString("tipo"),
                        rs.getString("ruta_archivo")
                });
            }

            tablaAdminCertificados.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar certificados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void descargarCertificadoSeleccionado() {
        int selectedRow = tablaAdminCertificados.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un certificado para descargar.");
            return;
        }

        String rutaArchivo = (String) tablaAdminCertificados.getValueAt(selectedRow, 4);

        if (rutaArchivo == null || rutaArchivo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La ruta del certificado es inválida.");
            return;
        }

        File archivoOriginal = new File(rutaArchivo);
        if (!archivoOriginal.exists()) {
            JOptionPane.showMessageDialog(this, "El archivo no existe en la ruta especificada.");
            return;
        }

        try {
            String rutaDestino = System.getProperty("user.home") + "\\Desktop\\" + archivoOriginal.getName();
            Files.copy(archivoOriginal.toPath(), new File(rutaDestino).toPath(), StandardCopyOption.REPLACE_EXISTING);
            JOptionPane.showMessageDialog(this, "El certificado ha sido descargado en el escritorio.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al copiar el archivo: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void generarReportePorMateria() {
        try (Connection conexion = CleverDB.getConexion()) {
            String sql = "SELECT a.nombre AS asignatura, u.nombre AS estudiante, c.tipo, c.ruta_archivo " +
                    "FROM certificados c " +
                    "JOIN usuarios u ON c.id_estudiante = u.id " +
                    "JOIN asignaturas a ON c.id_asignatura = a.id_asignatura";

            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println("Materia: " + rs.getString("asignatura") +
                        " | Estudiante: " + rs.getString("estudiante") +
                        " | Tipo: " + rs.getString("tipo"));
            }

            JOptionPane.showMessageDialog(this, "Reporte por materia generado con éxito.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al generar reporte: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void generarReportePorEstudiante() {
        String nombreEstudiante = JOptionPane.showInputDialog(this, "Ingrese el nombre del estudiante:");
        if (nombreEstudiante == null || nombreEstudiante.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
            return;
        }

        try (Connection conexion = CleverDB.getConexion()) {
            String sqlEstudiante = "SELECT id FROM usuarios WHERE nombre = ?";
            PreparedStatement stmt = conexion.prepareStatement(sqlEstudiante);
            stmt.setString(1, nombreEstudiante);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Estudiante no encontrado.");
                return;
            }

            int idEstudiante = rs.getInt("id");

            String sql = "SELECT a.nombre AS asignatura, c.tipo, c.ruta_archivo " +
                    "FROM certificados c " +
                    "JOIN asignaturas a ON c.id_asignatura = a.id_asignatura " +
                    "WHERE c.id_estudiante = ?";

            PreparedStatement stmtCerts = conexion.prepareStatement(sql);
            stmtCerts.setInt(1, idEstudiante);
            ResultSet rsCerts = stmtCerts.executeQuery();

            while (rsCerts.next()) {
                System.out.println("Asignatura: " + rsCerts.getString("asignatura") +
                        " | Tipo: " + rsCerts.getString("tipo"));
            }

            JOptionPane.showMessageDialog(this, "Reporte por estudiante generado con éxito.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al generar reporte: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void generarReportePorPeriodo() {
        String fechaInicio = JOptionPane.showInputDialog(this, "Ingrese la fecha de inicio (YYYY-MM-DD):");
        String fechaFin = JOptionPane.showInputDialog(this, "Ingrese la fecha de fin (YYYY-MM-DD):");

        if (fechaInicio == null || fechaFin == null || fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Las fechas no pueden estar vacías.");
            return;
        }

        try (Connection conexion = CleverDB.getConexion()) {
            String sql = "SELECT a.nombre AS asignatura, u.nombre AS estudiante, c.tipo, c.ruta_archivo " +
                    "FROM certificados c " +
                    "JOIN usuarios u ON c.id_estudiante = u.id " +
                    "JOIN asignaturas a ON c.id_asignatura = a.id_asignatura " +
                    "WHERE c.fecha_emision BETWEEN ? AND ?";

            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, fechaInicio);
            stmt.setString(2, fechaFin);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Asignatura: " + rs.getString("asignatura") +
                        " | Estudiante: " + rs.getString("estudiante") +
                        " | Tipo: " + rs.getString("tipo"));
            }

            JOptionPane.showMessageDialog(this, "Reporte por periodo generado con éxito.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al generar reporte: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PrincipalCertificados_Admin().setVisible(true));
    }
}

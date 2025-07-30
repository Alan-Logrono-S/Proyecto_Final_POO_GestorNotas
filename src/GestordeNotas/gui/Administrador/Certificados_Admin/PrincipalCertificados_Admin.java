package GestordeNotas.gui.Administrador.Certificados_Admin;

import GestordeNotas.gui.Principal.PrincipalAdmin;
import GestordeNotas.database.CleverDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

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
        setContentPane(panel1);
        setTitle("Certificados - Administrador");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Acción para visualizar certificados
        VISUALIZARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarCertificados();  // Cargar los certificados al hacer clic en Visualizar
            }
        });

        // Acción para descargar certificados
        DESCARGARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tablaAdminCertificados.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un certificado para descargar.");
                    return;
                }

                // Obtenemos la ruta del archivo desde la tabla cargada
                String rutaArchivo = (String) tablaAdminCertificados.getValueAt(selectedRow, 4); // Ruta del archivo es la columna 4
                System.out.println("Ruta obtenida: " + rutaArchivo); // Para depurar

                // Verificamos si la ruta es nula o vacía
                if (rutaArchivo == null || rutaArchivo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El certificado seleccionado no tiene una ruta válida.");
                    return;
                }

                // Verificamos si el archivo original existe
                File archivoOriginal = new File(rutaArchivo);
                if (archivoOriginal.exists()) {
                    try {
                        // Definir la ruta de destino en el escritorio del usuario
                        String rutaDestino = "C:\\Users\\Usuario\\Desktop\\" + archivoOriginal.getName();

                        // Crear el archivo de destino
                        File archivoDestino = new File(rutaDestino);

                        // Copiar el archivo al escritorio
                        Files.copy(archivoOriginal.toPath(), archivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                        // Mostrar mensaje de éxito
                        JOptionPane.showMessageDialog(null, "El certificado ha sido descargado en el escritorio.");

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error al copiar el archivo: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El archivo no existe en la ruta especificada.");
                }
            }
        });

        // Acciones para generar reportes
        reportePorMateriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReportePorMateria();  // Llamar a la función para generar el reporte por materia
            }
        });

        reportePorEstudianteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReportePorEstudiante();  // Llamar a la función para generar el reporte por estudiante
            }
        });

        reportePorPeriodoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReportePorPeriodo();  // Llamar a la función para generar el reporte por periodo
            }
        });

        // Volver al panel principal
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PrincipalAdmin().setVisible(true);
            }
        });

        // Salir del sistema
        salirDelSistemaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro que quieres salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    // Cargar los certificados
    private void cargarCertificados() {
        try {
            Connection conexion = CleverDB.getConexion();
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT c.id_certificado, u.nombre AS estudiante, a.nombre AS asignatura, c.tipo, c.ruta_archivo " +
                    "FROM certificados c " +
                    "JOIN usuarios u ON c.id_estudiante = u.id " +
                    "JOIN asignaturas a ON c.id_asignatura = a.id_asignatura");

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID Certificado");
            model.addColumn("Estudiante");
            model.addColumn("Asignatura");
            model.addColumn("Tipo");
            model.addColumn("Ruta del Archivo");

            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getInt("id_certificado"),
                        rs.getString("estudiante"),
                        rs.getString("asignatura"),
                        rs.getString("tipo"),
                        rs.getString("ruta_archivo")
                });
            }

            tablaAdminCertificados.setModel(model);
            rs.close();
            stmt.close();
            conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar certificados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Generar reporte por materia
    private void generarReportePorMateria() {
        try {
            // Crear la conexión
            Connection conexion = CleverDB.getConexion();

            // Consulta SQL para obtener los certificados por materia
            String sql = "SELECT a.nombre AS asignatura, u.nombre AS estudiante, c.tipo, c.ruta_archivo " +
                    "FROM certificados c " +
                    "JOIN usuarios u ON c.id_estudiante = u.id " +
                    "JOIN asignaturas a ON c.id_asignatura = a.id_asignatura";

            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Mostrar el reporte en la consola (puedes modificarlo para generar un archivo PDF/Excel)
            while (rs.next()) {
                String asignatura = rs.getString("asignatura");
                String estudiante = rs.getString("estudiante");
                String tipo = rs.getString("tipo");
                String rutaArchivo = rs.getString("ruta_archivo");

                System.out.println("Materia: " + asignatura + " | Estudiante: " + estudiante + " | Tipo: " + tipo);
            }

            rs.close();
            stmt.close();
            conexion.close();

            JOptionPane.showMessageDialog(this, "Reporte por materia generado con éxito.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al generar el reporte por materia: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Generar reporte por estudiante
    private void generarReportePorEstudiante() {
        try {
            String estudianteNombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del estudiante:");

            if (estudianteNombre == null || estudianteNombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre del estudiante no puede estar vacío.");
                return;
            }

            // Crear la conexión
            Connection conexion = CleverDB.getConexion();

            // Obtener el ID del estudiante por nombre
            String idEstudianteSql = "SELECT id FROM usuarios WHERE nombre = ?";
            PreparedStatement stmt = conexion.prepareStatement(idEstudianteSql);
            stmt.setString(1, estudianteNombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idEstudiante = rs.getInt("id");

                // Consulta SQL para obtener los certificados por ese estudiante
                String sql = "SELECT a.nombre AS asignatura, c.tipo, c.ruta_archivo " +
                        "FROM certificados c " +
                        "JOIN asignaturas a ON c.id_asignatura = a.id_asignatura " +
                        "WHERE c.id_estudiante = ?";
                PreparedStatement stmtCertificados = conexion.prepareStatement(sql);
                stmtCertificados.setInt(1, idEstudiante);
                ResultSet rsCertificados = stmtCertificados.executeQuery();

                // Mostrar los certificados del estudiante
                while (rsCertificados.next()) {
                    String asignatura = rsCertificados.getString("asignatura");
                    String tipo = rsCertificados.getString("tipo");
                    String rutaArchivo = rsCertificados.getString("ruta_archivo");

                    System.out.println("Asignatura: " + asignatura + " | Tipo: " + tipo);
                }

                rsCertificados.close();
                stmtCertificados.close();
            } else {
                JOptionPane.showMessageDialog(this, "Estudiante no encontrado.");
            }

            rs.close();
            stmt.close();
            conexion.close();

            JOptionPane.showMessageDialog(this, "Reporte por estudiante generado con éxito.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al generar el reporte por estudiante: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Generar reporte por periodo
    private void generarReportePorPeriodo() {
        try {
            // Pedir el rango de fechas al administrador
            String fechaInicio = JOptionPane.showInputDialog(this, "Ingrese la fecha de inicio (YYYY-MM-DD):");
            String fechaFin = JOptionPane.showInputDialog(this, "Ingrese la fecha de fin (YYYY-MM-DD):");

            // Crear la conexión
            Connection conexion = CleverDB.getConexion();

            // Consulta SQL para obtener los certificados en ese rango
            String sql = "SELECT a.nombre AS asignatura, u.nombre AS estudiante, c.tipo, c.ruta_archivo " +
                    "FROM certificados c " +
                    "JOIN usuarios u ON c.id_estudiante = u.id " +
                    "JOIN asignaturas a ON c.id_asignatura = a.id_asignatura " +
                    "WHERE c.fecha_emision BETWEEN ? AND ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, fechaInicio);
            stmt.setString(2, fechaFin);
            ResultSet rs = stmt.executeQuery();

            // Mostrar los certificados en el periodo
            while (rs.next()) {
                String asignatura = rs.getString("asignatura");
                String estudiante = rs.getString("estudiante");
                String tipo = rs.getString("tipo");
                String rutaArchivo = rs.getString("ruta_archivo");

                System.out.println("Asignatura: " + asignatura + " | Estudiante: " + estudiante + " | Tipo: " + tipo);
            }

            rs.close();
            stmt.close();
            conexion.close();

            JOptionPane.showMessageDialog(this, "Reporte por periodo generado con éxito.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al generar el reporte por periodo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PrincipalCertificados_Admin().setVisible(true);
    }
}

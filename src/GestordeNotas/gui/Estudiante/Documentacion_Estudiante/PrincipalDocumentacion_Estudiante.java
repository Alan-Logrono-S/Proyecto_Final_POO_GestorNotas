package GestordeNotas.gui.Estudiante.Documentacion_Estudiante;

// Importación de clases necesarias
import GestordeNotas.database.CleverDB;
import GestordeNotas.gui.Principal.PrincipalEstudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.FileOutputStream;

// Librerías para generación de PDF con iText
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

// Clase principal para la interfaz de documentación del estudiante
public class PrincipalDocumentacion_Estudiante extends JFrame {
    // Componentes de la interfaz
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JButton regresarButton;
    private JButton salirButton;
    private JPanel EstudianteCertificados;
    private JTable tablaCertificadosEstudiantes;
    private JButton CERTIFICACIONESButton;
    private JPanel EstudiantesNotificaciones;
    private JTable tablaCorreoEstudiantes;
    private JButton CARGARCORREOButton;

    // ID del estudiante (recibido desde login)
    private int idEstudiante;

    // Constructor que recibe el ID del estudiante
    public PrincipalDocumentacion_Estudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;

        // Configuración de la ventana
        setContentPane(panel1);
        setTitle("Documentación - Estudiante");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Acción para generar y descargar PDF de certificados
        CERTIFICACIONESButton.addActionListener(e -> descargarCertificadoPDF());

        // Acción para cargar notificaciones del estudiante
        CARGARCORREOButton.addActionListener(e -> cargarNotificaciones());

        // Acción del botón regresar: vuelve a la ventana principal del estudiante
        regresarButton.addActionListener(e -> {
            dispose();
            new PrincipalEstudiante(idEstudiante).setVisible(true);
        });

        // Acción para cerrar la ventana actual
        salirButton.addActionListener(e -> dispose());
    }

    // Método para generar y descargar un certificado en PDF
    private void descargarCertificadoPDF() {
        try (Connection con = CleverDB.getConexion()) {
            // Consulta para obtener el nombre del estudiante
            String estudianteQuery = "SELECT nombre FROM usuarios WHERE id= ?" ;
            PreparedStatement psEst = con.prepareStatement(estudianteQuery);
            psEst.setInt(1, idEstudiante);
            ResultSet rsEst = psEst.executeQuery();

            String nombre = "";
            if (rsEst.next()){
                nombre = rsEst.getString("nombre");
            }

            // Consulta para obtener los certificados del estudiante
            String query = "SELECT a.nombre AS asignatura, c.tipo, c.fecha_emision " +
                    "FROM certificados c " +
                    "LEFT JOIN asignaturas a ON c.id_asignatura = a.id_asignatura " +
                    "WHERE c.id_estudiante = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            // Ruta de descarga del PDF en la carpeta Descargas del usuario
            String userHome = System.getProperty("user.home");
            String archivoNombre = userHome + "/Downloads/Certificado_" + nombre + ".pdf";

            // Crear documento PDF
            Document documento = new Document();
            PdfWriter.getInstance(documento,new FileOutputStream(archivoNombre));
            documento.open();

            // Título del certificado
            Paragraph titulo = new Paragraph("CERTIFICADOS DE ESTUDIOS",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK));
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(new Paragraph("\n"));

            // Información general del estudiante
            documento.add(new Paragraph("Nombre del estudiante: " + nombre));
            documento.add(new Paragraph("Fecha de emisión: " + java.time.LocalDate.now()));
            documento.add(new Paragraph("\nCalificaciones obtenidas: \n"));

            // Crear tabla de certificados
            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{4, 2, 3});
            tabla.addCell("Asignatura");
            tabla.addCell("Tipo");
            tabla.addCell("Fecha de Emisión");

            while (rs.next()) {
                tabla.addCell(rs.getString("asignatura") != null ? rs.getString("asignatura") : "General");
                tabla.addCell(rs.getString("tipo"));
                tabla.addCell(rs.getDate("fecha_emision").toString());
            }
            documento.add(tabla);

            // Agregar resumen de notas
            documento.add(new Paragraph("\nResumen de notas: \n"));
            String notasQuery = """
                SELECT a.nombre AS asignatura,
                       AVG(ca.calificacion) AS promedio
                FROM calificaciones ca
                JOIN matriculas m ON ca.id_matricula = m.id_matricula
                JOIN asignaturas a ON m.id_asignatura = a.id_asignatura
                WHERE m.id_estudiante = ?
                GROUP BY a.nombre
            """;
            PreparedStatement psNotas = con.prepareStatement(notasQuery);
            psNotas.setInt(1, idEstudiante);
            ResultSet rsNotas = psNotas.executeQuery();

            // Crear tabla para mostrar promedios por asignatura
            PdfPTable tablaNotas = new PdfPTable(2);
            tablaNotas.setWidthPercentage(100);
            tablaNotas.setWidths(new float[]{4, 2});
            tablaNotas.addCell("Asignatura");
            tablaNotas.addCell("Promedio");

            while (rsNotas.next()) {
                String asignatura = rsNotas.getString("asignatura");
                double promedio = rsNotas.getDouble("promedio");
                String promedioStr = rsNotas.wasNull() ? "N/A" : String.format("%.2f", promedio);
                tablaNotas.addCell(asignatura != null ? asignatura : "Sin nombre");
                tablaNotas.addCell(promedioStr);
            }

            documento.add(tablaNotas);
            documento.add(new Paragraph("\nFrima del responsable:___________________ "));
            documento.close();

            // Mensaje de éxito al usuario
            JOptionPane.showMessageDialog(null, "Certificado generado correctamente:\n" + archivoNombre);

            // Cierre de recursos
            rs.close();
            stmt.close();
            rsEst.close();
            psEst.close();
            rsNotas.close();
            psNotas.close();
        } catch (Exception ex) {
            // Manejo de errores
            JOptionPane.showMessageDialog(null, "Error al cargar certificados: " + ex.getMessage());
        }
    }

    // Método para cargar notificaciones del estudiante en la tabla
    private void cargarNotificaciones() {
        try (Connection con = CleverDB.getConexion()) {
            // Consulta para obtener notificaciones del estudiante
            String query = "SELECT mensaje, fecha_envio, leido FROM notificaciones WHERE id_destinatario = ? ORDER BY fecha_envio DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idEstudiante);
            ResultSet rs = stmt.executeQuery();

            // Crear modelo de tabla y cargar resultados
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

            // Mostrar resultados en la tabla de la interfaz
            tablaCorreoEstudiantes.setModel(model);

            // Cierre de recursos
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            // Manejo de errores
            JOptionPane.showMessageDialog(this, "Error al cargar notificaciones: " + ex.getMessage());
        }
    }
}

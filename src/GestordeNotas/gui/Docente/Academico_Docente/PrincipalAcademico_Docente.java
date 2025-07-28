package GestordeNotas.gui.Docente.Academico_Docente;

import GestordeNotas.database.CleverDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.HashMap;

public class PrincipalAcademico_Docente extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JButton regresarButton;
    private JButton salirButton;
    private JPanel DocentRegistroNotas;
    private JTable tablaCalificaciones;
    private JComboBox<String> comboBox1;
    private JButton ingresarCalificacionesButton;
    private JButton modificarCalificacionesButton;
    private JPanel DocentVerEstudiantesXasignatura;
    private JTable tablaVerEstudxAsig;
    private JButton button1;
    private JButton verCalificacionesButton;

    private int idDocente; // el ID del docente que inicia sesión

    // Constructor con idDocente
    public PrincipalAcademico_Docente(int idDocente) {
        this.idDocente = idDocente;

        setContentPane(panel1);
        setTitle("Académico - Docente");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cargarAsignaturasDocente();

        // Ver estudiantes por asignatura
        button1.addActionListener(e -> verEstudiantesPorAsignatura());

        // Ingresar calificaciones
        ingresarCalificacionesButton.addActionListener(e -> ingresarCalificacion());

        // Modificar calificación
        modificarCalificacionesButton.addActionListener(e -> modificarCalificacion());

        // Volver al menú principal
        regresarButton.addActionListener(e -> {
            dispose();
            new GestordeNotas.gui.Principal.PrincipalDocente(idDocente).setVisible(true);
        });

        salirButton.addActionListener(e -> dispose());
        verCalificacionesButton.addActionListener(e -> {
            // Aquí puedes agregar código para ver calificaciones si quieres
        });
    }

    // Constructor sin parámetros que llama al constructor con idDocente=3 por defecto
    public PrincipalAcademico_Docente() {
        this(3); // Valor por defecto para pruebas
    }

    private void cargarAsignaturasDocente() {
        try {
            Connection con = CleverDB.getConexion();
            String query = "SELECT a.nombre FROM asignaturas a " +
                    "JOIN docentes_asignaturas da ON a.id_asignatura = da.id_asignatura " +
                    "WHERE da.id_docente = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idDocente);
            ResultSet rs = stmt.executeQuery();

            comboBox1.removeAllItems();
            while (rs.next()) {
                comboBox1.addItem(rs.getString("nombre"));
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar asignaturas: " + ex.getMessage());
        }
    }

    private void verEstudiantesPorAsignatura() {
        String asignatura = (String) comboBox1.getSelectedItem();
        if (asignatura == null) return;

        try {
            Connection con = CleverDB.getConexion();
            String query = "SELECT u.id_usuario, u.nombre FROM usuarios u " +
                    "JOIN matriculas m ON u.id_usuario = m.id_estudiante " +
                    "JOIN asignaturas a ON m.id_asignatura = a.id_asignatura " +
                    "WHERE a.nombre = ? AND u.rol = 'estudiante'";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, asignatura);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nombre");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id_usuario"),
                        rs.getString("nombre")
                });
            }

            tablaVerEstudxAsig.setModel(model);
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar estudiantes: " + ex.getMessage());
        }
    }

    private void ingresarCalificacion() {
        // Implementar según necesidades
    }

    private void modificarCalificacion() {
        // Implementar según necesidades
    }

}

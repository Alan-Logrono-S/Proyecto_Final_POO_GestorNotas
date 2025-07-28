package GestordeNotas.gui.Administrador.Documentacion_Admin;

import GestordeNotas.gui.Principal.PrincipalAdmin;
import GestordeNotas.database.CleverDB;
import javax.swing.table.DefaultTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PrincipalDocumentacion_Admin extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel AdminPeriodosAcad;
    private JTable tablaAdminPeridoAcad;
    private JButton INGRESARButton;
    private JButton BUSCARButton;
    private JButton ELIMINARButton;
    private JPanel AdminMatricula;
    private JTable tablaAdminMatriculas;
    private JComboBox comboBox1;
    private JButton agregarButton;
    private JButton cancelarButton;
    private JButton regresarButton;
    private JButton salirDelSistemaButton;

    public PrincipalDocumentacion_Admin() {
        setContentPane(panel1);
        setTitle("Documentación - Administrador");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // PERIODO ACADEMICO

        BUSCARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPeriodoAcademico();  // Buscar periodos académicos
            }
        });

        INGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarPeriodoAcademico();  // Ingresar un nuevo periodo académico
            }
        });

        ELIMINARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarPeriodoAcademico();  // Eliminar periodo académico
            }
        });

        // MATRÍCULA

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarMatricula();  // Agregar matrícula
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarMatricula();  // Cancelar matrícula
            }
        });

        // EXIT

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PrincipalAdmin().setVisible(true);
            }
        });

        salirDelSistemaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí podrías agregar una confirmación antes de cerrar el sistema si lo deseas
                System.exit(0);
            }
        });
    }

    // Método para buscar un periodo académico
    private void buscarPeriodoAcademico() {
        String descripcionPeriodo = JOptionPane.showInputDialog(this, "Ingrese la descripción del periodo:");
        if (descripcionPeriodo != null && !descripcionPeriodo.trim().isEmpty()) {
            try {
                Connection conexion = CleverDB.getConexion();
                String query = "SELECT * FROM periodos WHERE descripcion LIKE ?";
                PreparedStatement stmt = conexion.prepareStatement(query);
                stmt.setString(1, "%" + descripcionPeriodo + "%");
                ResultSet rs = stmt.executeQuery();

                // Mostrar los resultados en la tabla
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("ID Periodo");
                model.addColumn("Descripción");
                model.addColumn("Fecha Inicio");
                model.addColumn("Fecha Fin");

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id_periodo"),
                            rs.getString("descripcion"),
                            rs.getDate("fecha_inicio"),
                            rs.getDate("fecha_fin")
                    });
                }

                tablaAdminPeridoAcad.setModel(model);
                rs.close();
                stmt.close();
                conexion.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al buscar el periodo académico: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor ingrese una descripción.");
        }
    }

    // Método para ingresar un nuevo periodo académico
    private void ingresarPeriodoAcademico() {
        JTextField descripcionField = new JTextField(20);
        JTextField fechaInicioField = new JTextField(20);
        JTextField fechaFinField = new JTextField(20);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Descripción:"));
        panel.add(descripcionField);
        panel.add(new JLabel("Fecha Inicio:"));
        panel.add(fechaInicioField);
        panel.add(new JLabel("Fecha Fin:"));
        panel.add(fechaFinField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Ingresar Periodo Académico", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String descripcion = descripcionField.getText();
            String fechaInicio = fechaInicioField.getText();
            String fechaFin = fechaFinField.getText();

            try {
                Connection conexion = CleverDB.getConexion();
                String query = "INSERT INTO periodos (descripcion, fecha_inicio, fecha_fin) VALUES (?, ?, ?)";
                PreparedStatement stmt = conexion.prepareStatement(query);
                stmt.setString(1, descripcion);
                stmt.setDate(2, Date.valueOf(fechaInicio));
                stmt.setDate(3, Date.valueOf(fechaFin));
                stmt.executeUpdate();
                stmt.close();
                conexion.close();

                JOptionPane.showMessageDialog(this, "Periodo académico ingresado exitosamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al ingresar el periodo académico: " + e.getMessage());
            }
        }
    }

    // Método para eliminar un periodo académico
    private void eliminarPeriodoAcademico() {
        int selectedRow = tablaAdminPeridoAcad.getSelectedRow();
        if (selectedRow != -1) {
            int idPeriodo = (int) tablaAdminPeridoAcad.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar el periodo?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Connection conexion = CleverDB.getConexion();
                    String query = "DELETE FROM periodos WHERE id_periodo = ?";
                    PreparedStatement stmt = conexion.prepareStatement(query);
                    stmt.setInt(1, idPeriodo);
                    stmt.executeUpdate();
                    stmt.close();
                    conexion.close();

                    JOptionPane.showMessageDialog(this, "Periodo académico eliminado.");
                    // Actualizar la tabla
                    buscarPeriodoAcademico();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el periodo académico: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un periodo para eliminar.");
        }
    }

    // Método para agregar matrícula
    private void agregarMatricula() {
        // Aquí iría el código para agregar una matrícula
        JOptionPane.showMessageDialog(this, "Funcionalidad de agregar matrícula aún no implementada.");
    }

    // Método para cancelar la operación de matrícula
    private void cancelarMatricula() {
        // Aquí iría el código para cancelar la matrícula
        JOptionPane.showMessageDialog(this, "Operación de matrícula cancelada.");
    }
}

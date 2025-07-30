package GestordeNotas.gui.Administrador.Academico_Admin;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import GestordeNotas.gui.Principal.PrincipalAdmin;
import GestordeNotas.database.CleverDB;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalAcademico_Admin extends JFrame {
    private JPanel panel1;
    private JPanel PrincipalAdmin;
    private JTabbedPane tabbedPane1;
    private JPanel AdminCrudAsig;
    private JTable s;
    private JButton INGRESARasignaturasButton;
    private JTextField textField1;
    private JButton MOSTRARasginaturasButton;
    private JButton ELIMINARasignaturaButton;
    private JButton LIMPIARButton;
    private JTextField textField2;
    private JButton ACTUALIZARasignaturasButton2;
    private JPanel AdminCrudUsuarios;
    private JTable tablaAdminGestionUsers;
    private JButton AGREGARButton;
    private JTextField textField3; // Nombre
    private JTextField textField4; // Correo
    private JTextField textField5; // Rol
    private JButton ACTUALIZARButton;
    private JButton regresarButton;
    private JButton salirDelSistemaButton;
    private JButton MOSTRARButton1;
    private JButton ELIMINARButton1;
    private JButton LIMPIARButton1;
    private JTextField textField6; // ID (si es necesario)
    private JTextField textFieldContraseña; // Nuevo campo para la contraseña
    private JTextField textField7;  // Descripción de asignatura
    private JTextField textFieldCreditos;  // Créditos de la asignatura

    public PrincipalAcademico_Admin() {
        setContentPane(panel1);
        setTitle("Academico - Administrador");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Acción para ingresar una asignatura
        INGRESARasignaturasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField1.getText();
                String descripcion = textField2.getText();
                String creditosStr = textFieldCreditos.getText();

                if (nombre.isEmpty() || descripcion.isEmpty() || creditosStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                try {
                    int creditos = Integer.parseInt(creditosStr); // Convertir créditos a número
                    Connection conexion = CleverDB.getConexion();
                    String query = "INSERT INTO asignaturas (nombre, descripcion, creditos) VALUES (?, ?, ?)";
                    PreparedStatement stmt = conexion.prepareStatement(query);
                    stmt.setString(1, nombre);
                    stmt.setString(2, descripcion);
                    stmt.setInt(3, creditos);
                    stmt.executeUpdate();
                    stmt.close();
                    conexion.close();
                    JOptionPane.showMessageDialog(null, "Asignatura agregada correctamente.");
                    cargarAsignaturas(); // Recargar asignaturas después de agregar una nueva
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al agregar asignatura: " + ex.getMessage());
                    ex.printStackTrace();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Los créditos deben ser un número.");
                }
            }
        });

        // Acción para actualizar una asignatura
        ACTUALIZARasignaturasButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = s.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una asignatura de la tabla para actualizar.");
                    return;
                }

                String nombre = textField1.getText();
                String descripcion = textField2.getText();
                String creditosStr = textFieldCreditos.getText();

                if (nombre.isEmpty() || descripcion.isEmpty() || creditosStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                try {
                    int id = (Integer) s.getValueAt(selectedRow, 0);
                    int creditos = Integer.parseInt(creditosStr); // Convertir créditos a número

                    Connection conexion = CleverDB.getConexion();
                    String query = "UPDATE asignaturas SET nombre = ?, descripcion = ?, creditos = ? WHERE id_asignatura = ?";
                    PreparedStatement stmt = conexion.prepareStatement(query);
                    stmt.setString(1, nombre);
                    stmt.setString(2, descripcion);
                    stmt.setInt(3, creditos);
                    stmt.setInt(4, id);
                    stmt.executeUpdate();
                    stmt.close();
                    conexion.close();
                    JOptionPane.showMessageDialog(null, "Asignatura actualizada correctamente.");
                    cargarAsignaturas(); // Recargar asignaturas después de actualizar
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar asignatura: " + ex.getMessage());
                    ex.printStackTrace();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Los créditos deben ser un número.");
                }
            }
        });

        // Acción para eliminar una asignatura
        ELIMINARasignaturaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = s.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una asignatura de la tabla para eliminar.");
                    return;
                }

                try {
                    int id = (Integer) s.getValueAt(selectedRow, 0);
                    int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar esta asignatura?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        Connection conexion = CleverDB.getConexion();
                        String query = "DELETE FROM asignaturas WHERE id_asignatura = ?";
                        PreparedStatement stmt = conexion.prepareStatement(query);
                        stmt.setInt(1, id);
                        stmt.executeUpdate();
                        stmt.close();
                        conexion.close();
                        JOptionPane.showMessageDialog(null, "Asignatura eliminada correctamente.");
                        cargarAsignaturas(); // Recargar asignaturas después de eliminar
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar asignatura: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        // Limpiar los campos de texto
        LIMPIARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText(""); // Nombre
                textField2.setText(""); // Descripción
                textField7.setText("");
                textFieldCreditos.setText(""); // Créditos
            }
        });

        // Limpiar los campos de texto en usuarios
        LIMPIARButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField3.setText(""); // Nombre
                textField4.setText(""); // Correo
                textField5.setText(""); // Rol
                textField6.setText("");
                textFieldContraseña.setText(""); // Contraseña
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

        // Acción para mostrar asignaturas
        MOSTRARasginaturasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarAsignaturas(); // Cargar asignaturas al presionar MOSTRAR
            }
        });

        // Acción de mostrar usuarios
        MOSTRARButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarUsuarios(); // Cargar usuarios al presionar MOSTRAR
            }
        });

        // Acción para actualizar usuarios
        ACTUALIZARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tablaAdminGestionUsers.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario de la tabla para actualizar.");
                    return;
                }

                String nombre = textField3.getText();
                String correo = textField4.getText();
                String rol = textField5.getText();

                if (nombre.isEmpty() || correo.isEmpty() || rol.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                try {
                    int id = (Integer) tablaAdminGestionUsers.getValueAt(selectedRow, 0);
                    Connection conexion = CleverDB.getConexion();
                    String query = "UPDATE usuarios SET nombre = ?, correo = ?, rol = ? WHERE id= ?";
                    PreparedStatement stmt = conexion.prepareStatement(query);
                    stmt.setString(1, nombre);
                    stmt.setString(2, correo);
                    stmt.setString(3, rol);
                    stmt.setInt(4, id);
                    stmt.executeUpdate();
                    stmt.close();
                    conexion.close();
                    JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente.");
                    cargarUsuarios(); // Recargar usuarios después de actualizar
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar usuario: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        AGREGARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField3.getText();
                String correo = textField4.getText();
                String rol = textField5.getText();
                String contraseña = textFieldContraseña.getText(); // Obtener la contraseña

                if (nombre.isEmpty() || correo.isEmpty() || rol.isEmpty() || contraseña.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                try {
                    // Conectar a la base de datos
                    Connection conexion = CleverDB.getConexion();
                    String query = "INSERT INTO usuarios (nombre, correo, rol, contraseña) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmt = conexion.prepareStatement(query);
                    stmt.setString(1, nombre);
                    stmt.setString(2, correo);
                    stmt.setString(3, rol);
                    stmt.setString(4, contraseña);  // Insertar la contraseña proporcionada
                    stmt.executeUpdate();
                    stmt.close();
                    conexion.close();
                    JOptionPane.showMessageDialog(null, "Usuario agregado correctamente.");
                    cargarUsuarios(); // Recargar usuarios después de agregar uno
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al agregar usuario: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        ELIMINARButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tablaAdminGestionUsers.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario de la tabla para eliminar.");
                    return;
                }

                try {
                    int id = (Integer) tablaAdminGestionUsers.getValueAt(selectedRow, 0);
                    int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este usuario?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        Connection conexion = CleverDB.getConexion();
                        String query = "DELETE FROM usuarios WHERE id = ?";
                        PreparedStatement stmt = conexion.prepareStatement(query);
                        stmt.setInt(1, id);
                        stmt.executeUpdate();
                        stmt.close();
                        conexion.close();
                        JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente.");
                        cargarUsuarios(); // Recargar usuarios después de eliminar
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

    }

    private void cargarUsuarios() {
        try {
            Connection conexion = CleverDB.getConexion();
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nombre, correo, rol FROM usuarios");

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Correo");
            model.addColumn("Rol");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("rol")
                });
            }

            tablaAdminGestionUsers.setModel(model);
            rs.close();
            stmt.close();
            conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarAsignaturas() {
        try {
            Connection conexion = CleverDB.getConexion();
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_asignatura, nombre, descripcion FROM asignaturas");

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Descripción");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id_asignatura"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                });
            }

            s.setModel(model);
            rs.close();
            stmt.close();
            conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar asignaturas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

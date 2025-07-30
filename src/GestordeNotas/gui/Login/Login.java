package GestordeNotas.gui.Login;

import GestordeNotas.database.CleverDB;
import GestordeNotas.gui.Principal.PrincipalAdmin;
import GestordeNotas.gui.Principal.PrincipalCoordinador;
import GestordeNotas.gui.Principal.PrincipalDocente;
import GestordeNotas.gui.Principal.PrincipalEstudiante;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame {

    private JPanel PanelLogin;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JComboBox<String> comboBox1;
    private JButton INGRESARALSISTEMAButton;
    private JButton SALIRDELSISTEMAButton;


    public Login() {
        setContentPane(PanelLogin);
        setTitle("Sistema de Gestión de Notas");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        INGRESARALSISTEMAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correo = textField1.getText();
                String contraseña = String.valueOf(passwordField1.getPassword());
                String rol = (String) comboBox1.getSelectedItem();

                try (Connection con = CleverDB.getConexion()) {
                    String sql = "SELECT * FROM usuarios WHERE correo = ? AND contraseña = ? AND rol = ?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, correo);
                    ps.setString(2, contraseña);
                    ps.setString(3, rol);

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, " Bienvenido " + rs.getString("nombre") + " como " + rol);

                        switch (rol) {
                            case "Administrador":
                                // Se crea la instancia de PrincipalAdmin y se muestra su formulario
                                new PrincipalAdmin().setVisible(true);
                                break;
                            case "Coordinador":
                                // Se crea la instancia de PrincipalCoordinador y se muestra su formulario
                                new PrincipalCoordinador().setVisible(true);
                                break;
                            case "Docente":
                                // Se crea la instancia de PrincipalDocente y se muestra su formulario
                                new PrincipalDocente().setVisible(true);
                                break;
                            case "Estudiante":
                                // Se crea la instancia de PrincipalEstudiante y se muestra su formulario
                                new PrincipalEstudiante().setVisible(true);
                                break;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, " Usuario o contraseña incorrectos");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, " Error al conectar a la base de datos");
                }
            }
        });

        SALIRDELSISTEMAButton.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}
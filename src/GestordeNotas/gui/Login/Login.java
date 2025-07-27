package GestordeNotas.gui.Login;

import GestordeNotas.gui.Principal.PrincipalAdmin;
import GestordeNotas.gui.Principal.PrincipalCoordinador;
import GestordeNotas.gui.Principal.PrincipalDocente;
import GestordeNotas.gui.Principal.PrincipalEstudiante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Login extends JFrame {

    // ---------- Componentes generados por el GUI Builder ----------
    private JPanel PanelLogin;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JComboBox<String> comboBox1;
    private JButton INGRESARALSISTEMAButton;
    private JButton SALIRDELSISTEMAButton;
    // --------------------------------------------------------------

    /* ========== Tabla de credenciales ========== */
    private static final Map<String, Map<String, String>> CREDENCIALES = new HashMap<>();

    static {
        /* --- Administrador --- */
        Map<String, String> admin = new HashMap<>();
        admin.put("admin", "1234");          // usuario: admin / pass: 1234
        CREDENCIALES.put("Administrador", admin);

        /* --- Coordinador --- */
        Map<String, String> coordinador = new HashMap<>();
        coordinador.put("coord", "5678");    // usuario: coord / pass: 5678
        CREDENCIALES.put("Coordinador", coordinador);

        /* --- Docente --- */
        Map<String, String> docente = new HashMap<>();
        docente.put("doc", "abcd");          // usuario: doc / pass: abcd
        CREDENCIALES.put("Docente", docente);

        /* --- Estudiante --- */
        Map<String, String> estudiante = new HashMap<>();
        estudiante.put("est", "pass");       // usuario: est / pass: pass
        estudiante.put("juan", "123");       // usuario: juan / pass: 123
        CREDENCIALES.put("Estudiante", estudiante);
    }

    /* =========================================== */

    public Login() {
        setTitle("Sistema de Login");
        setContentPane(PanelLogin);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);      // centra la ventana
        setVisible(true);

        /* ---------- Botón INGRESAR ---------- */
        INGRESARALSISTEMAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario     = textField1.getText().trim();
                String contraseña  = new String(passwordField1.getPassword());
                String rolSeleccionado = (String) comboBox1.getSelectedItem();

                if (validarCredenciales(rolSeleccionado, usuario, contraseña)) {

                    /* Abrir la ventana correspondiente */
                    switch (rolSeleccionado) {
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
                        default:
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Rol no reconocido.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                    }

                    dispose();  // cierra la ventana de login
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Usuario y/o contraseña incorrectos para el rol seleccionado",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    passwordField1.setText("");  // Borra la contraseña
                }
            }
        });

        /* ---------- Botón SALIR ---------- */
        SALIRDELSISTEMAButton.addActionListener(e -> dispose());
    }

    /** Valida usuario y contraseña según el rol. */
    private boolean validarCredenciales(String rol, String usuario, String contraseña) {
        Map<String, String> credRol = CREDENCIALES.get(rol);
        if (credRol == null) return false;
        String passAlmacenada = credRol.get(usuario);
        return contraseña.equals(passAlmacenada);
    }

    /** Punto de entrada para pruebas rápidas. */
    public static void main(String[] args) {
        new Login();
    }
}

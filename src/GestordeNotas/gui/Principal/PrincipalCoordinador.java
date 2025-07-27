package GestordeNotas.gui.Principal;
import GestordeNotas.gui.Login.Login;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalCoordinador extends JFrame {
    private JPanel PrincipalCoordinador;
    private JTextField textField1;
    private JButton GESTIONACADEMICAButton;
    private JButton GESTIONDOCUMENTACIONButton;
    private JButton DATOSPERSONALESButton;


    public PrincipalCoordinador() {

        setContentPane(PrincipalCoordinador);
        setTitle("Principal - Coordinador");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GESTIONACADEMICAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        GESTIONDOCUMENTACIONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        DATOSPERSONALESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
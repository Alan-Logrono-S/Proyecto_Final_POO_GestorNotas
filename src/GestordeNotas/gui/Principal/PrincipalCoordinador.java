package GestordeNotas.gui.Principal;
import GestordeNotas.gui.Coordinador.Academico_Coord.Principal_Academico_Coord;
import GestordeNotas.gui.Coordinador.Datos_Coord.CoordInicio;
import GestordeNotas.gui.Coordinador.Documentos_Coord.CoordReportNotas;
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
                dispose();
                new Principal_Academico_Coord().setVisible(true);
            }
        });
        GESTIONDOCUMENTACIONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CoordReportNotas().setVisible(true);
            }
        });
        DATOSPERSONALESButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CoordInicio().setVisible(true);
            }
        });
    }
}
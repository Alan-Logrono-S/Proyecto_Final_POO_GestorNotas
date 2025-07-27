package GestordeNotas.gui.Principal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalAdmin extends JFrame {
    private JTextField textField1;
    private JButton GESTIONACADEMICOButton;
    private JButton GESTIONDOCUMENTACIONButton;
    private JButton CERTIFICACIONESButton;
    private JButton DATOSPERSONALESButton;
    private JPanel PrincipalAdmin;

    public PrincipalAdmin() {

        setContentPane(PrincipalAdmin);
        setTitle("Principal - Administrador");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GESTIONACADEMICOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        GESTIONDOCUMENTACIONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        CERTIFICACIONESButton.addActionListener(new ActionListener() {
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
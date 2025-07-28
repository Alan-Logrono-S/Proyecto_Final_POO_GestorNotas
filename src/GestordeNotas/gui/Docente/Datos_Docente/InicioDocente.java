package GestordeNotas.gui.Docente.Datos_Docente;

import GestordeNotas.gui.Principal.PrincipalDocente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InicioDocente extends JFrame{
    private JTable tablaDatosPerDocente;
    private JButton CARGARDATOSPERSONALESButton;
    private JPanel InicioDocente;
    private JButton regresarButton;
    private JButton salirButton;

    public InicioDocente() {
        setTitle("Datos Personales Docente");
        setContentPane(InicioDocente);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PrincipalDocente().setVisible(true);
            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

}

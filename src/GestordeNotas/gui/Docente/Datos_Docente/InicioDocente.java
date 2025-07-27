package GestordeNotas.gui.Docente.Datos_Docente;

import javax.swing.*;

public class InicioDocente extends JFrame{
    private JTable tablaDatosPerDocente;
    private JButton CARGARDATOSPERSONALESButton;
    private JPanel InicioDocente;

    public InicioDocente() {
        setTitle("Datos Personales Docente");
        setContentPane(InicioDocente);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

}

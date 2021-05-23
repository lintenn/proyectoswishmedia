package prSwishMedia.Views;

import javax.swing.*;
import java.awt.event.ActionListener;


public class PrincipalView extends JFrame{
    private JPanel panel1;
    private JButton Perfil;

    public PrincipalView(){

    }

    public void controlador(ActionListener ctr){
        Perfil.addActionListener(ctr);


        Perfil.setActionCommand("PROFILE");
    }

    public JPanel getPanel() {
        return panel1;
    }
}

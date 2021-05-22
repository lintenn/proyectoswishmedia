package prSwishMedia.Views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ConfirmedView extends JFrame{
    private JPanel panel1;
    private JLabel Email;
    private JTextField Correo;
    private JButton enviarButton;
    private JButton volverButton;

    public ConfirmedView(){
        add(panel1);

    }

    public void controlador(ActionListener ctr){
        enviarButton.addActionListener(ctr);
        volverButton.addActionListener(ctr);

        enviarButton.setActionCommand("ENVIAR");
        volverButton.setActionCommand("VOLVER");

    }


    public JTextField getCorreo() {
        return Correo;
    }

    public JPanel getPanel() {
        return panel1;
    }

}

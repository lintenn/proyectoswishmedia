package prSwishMedia.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConfirmedView extends JFrame{
    private JPanel panel1;
    private JLabel Email;
    private JTextField Correo;
    private JButton enviarButton;
    private JButton volverButton;
    private JLabel Message;

    public ConfirmedView(){
        add(panel1);

    }

    public void setCMessage(String message) {
        Message.setText(message);
    }

    public void setEMessage(String message){
        Message.setBackground(Color.red);
        Message.setText(message);
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

    public void setMessage(String s){
        Message.setForeground(Color.white);
        Message.setText(s);
    }

    public void setErrorMessage(String s){
        Message.setForeground(Color.red);
        Message.setText(s);
    }

}

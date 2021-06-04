package prSwishMedia.Views;

import prSwishMedia.Controllers.ProfileController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotificacionAmistad extends JDialog{
    private JPanel panel1;
    private JButton aceptar;
    private JButton rechazar;
    private JLabel mensaje;

    private Cursor manita = new Cursor(Cursor.HAND_CURSOR);

    public NotificacionAmistad(){
        aceptar.setCursor(manita);
        rechazar.setCursor(manita);
    }

    public void controlador(ActionListener ctr) {
        aceptar.addActionListener(ctr);
        rechazar.addActionListener(ctr);

        aceptar.setActionCommand("ACEPTAR");
        rechazar.setActionCommand("RECHAZAR");
    }

    public JPanel getPanel1(){
        return panel1;
    }

    public void setMensaje(String s){
        mensaje.setText(s+" quiere ser tu amig@");
    }

    public String getMensaje(){
        return mensaje.getText();
    }
}

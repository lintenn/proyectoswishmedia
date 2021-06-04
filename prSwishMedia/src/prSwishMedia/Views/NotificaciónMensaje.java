package prSwishMedia.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class NotificaciónMensaje {
    private JPanel panel1;
    private JButton okey;
    private JLabel mensaje;

    private Cursor manita = new Cursor(Cursor.HAND_CURSOR);

    public NotificaciónMensaje() {
        okey.setCursor(manita);
    }

    public void controlador(ActionListener ctr) {
        okey.addActionListener(ctr);

        okey.setActionCommand("ACEPTAR");
    }

    public JPanel getPanel1(){
        return panel1;
    }

    public void setMensaje(String s){
        mensaje.setText(s);
    }

    public String getMensaje(){
        return mensaje.getText();
    }
}

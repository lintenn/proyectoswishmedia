package prSwishMedia.Views;

import prSwishMedia.Controllers.ProfileController;

import javax.management.Notification;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Notificación extends JDialog{
    private JPanel panel1;
    private JScrollPane PanelNotificaciones2;

    public Notificación(ProfileController parent, boolean modal){
        super(parent, modal);
        add(panel1);
        PanelNotificaciones2.getVerticalScrollBar().setUnitIncrement(16);
        setSize(new Dimension(430,500));
        this.setTitle("Notificaciones");
        this.setIconImage(new ImageIcon("LogoFondo.jpg").getImage());
        this.setLocationRelativeTo(null);
    }

    public void controlador(ActionListener ctr){

    }

    public void setPanelNotificaciones(JPanel p){
        PanelNotificaciones2.setViewportView(p);
    }
}

package prSwishMedia.Views;

import prSwishMedia.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ListaAmigosView extends JDialog {
    private JPanel panel1;
    private JScrollPane UsersPane;
    private JPanel Jpanel;
    private JPanel listaUsers;

    public ListaAmigosView(ProfileView pview, boolean modal){
        super(pview,modal);
        add(panel1);
        setSize(new Dimension(430,500));
        listaUsers=new JPanel();
        UsersPane.getVerticalScrollBar().setUnitIncrement(16);
        this.setTitle("Amigos");
        this.setIconImage(new ImageIcon("LogoFondo.jpg").getImage());
        this.setLocationRelativeTo(null);
    }

    public void controlador(ActionListener ctr){

    }
    public void setLayoutListasUsers(int cont){ listaUsers.setLayout(new GridLayout(cont,0,0,0));}

    public void removeAlllistasUsers(){ listaUsers.removeAll();}
    public void addListaUser(JPanel panel){ listaUsers.add(panel);}
    public void removeOneUser(JPanel panel){ listaUsers.remove(panel);}

    public JPanel getListaUsers(){ return listaUsers;}
    public void setViewportViewScrollUser(JPanel panel){UsersPane.setViewportView(panel);};
}

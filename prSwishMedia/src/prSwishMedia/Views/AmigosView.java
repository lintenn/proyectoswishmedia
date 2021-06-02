package prSwishMedia.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AmigosView extends JDialog {

    private JPanel panel1;
    private JScrollPane UsersPanel;
    private JPanel listaUsers;


    public AmigosView(){
        add(panel1);
        listaUsers=new JPanel();
        setSize(250,400);
    }
    public void controlador(ActionListener ctr){

    }

    public void setViewportViewScrollUser(JPanel panel){UsersPanel.setViewportView(panel);}
    public void setLayoutListasUsers(int cont){ listaUsers.setLayout(new GridLayout(cont,0,0,0));}

    public void addListaUser(JPanel panel){ listaUsers.add(panel);}
    public void removeAlllistasUsers(){ listaUsers.removeAll();}

    public JPanel getListaUsers(){ return listaUsers;}
}

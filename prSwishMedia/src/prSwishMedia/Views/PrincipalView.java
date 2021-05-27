package prSwishMedia.Views;

import prSwishMedia.Lista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class PrincipalView extends JFrame{
    private JPanel panel1;
    private JButton Perfil;
    private JTabbedPane tabbedPane1;
    private JPanel Películas;
    private JPanel Series;
    private JComboBox<Lista> comboBox1;
    private JPanel Listas;
    private JLabel Logo;
    private JPanel Usuarios;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton BuscarS;
    private JButton BuscarL;
    private JButton BuscarU;
    private JScrollPane Pelis;
    private JScrollPane SeriesPanel;
    private JButton BuscarP;
    private JScrollPane ListaUsers;
    private JScrollPane contenidoListas;
    private JScrollPane UsersPanel;
    private JPanel listaPelis;
    private JPanel listaSeries;
    private JPanel listaContenido;
    private JPanel listaUsers;

    public PrincipalView(){
        listaPelis=new JPanel();
        listaSeries=new JPanel();
        listaContenido=new JPanel();
        listaUsers=new JPanel();
    }

    public void controlador(ActionListener ctr){
        Perfil.addActionListener(ctr);
        comboBox1.addActionListener(ctr);

        comboBox1.setActionCommand("LISTA");
        Perfil.setActionCommand("PROFILE");
    }

    public void removeAllListas(){listaPelis.removeAll();}
    public void removeAllListasSerie(){listaSeries.removeAll();}
    public void removeAlllistasUsers(){ listaUsers.removeAll();}

    public void setLayoutListas(int cont){listaPelis.setLayout(new GridLayout(cont,0,0,0));}
    public void setLayoutListasSerie(int cont){listaSeries.setLayout(new GridLayout(cont,0,0,0));}
    public void setLayoutListasContenido(int cont){listaContenido.setLayout(new GridLayout(cont,0,0,0));}
    public void setLayoutListasUsers(int cont){ listaUsers.setLayout(new GridLayout(cont,0,0,0));}


    public void addListaPelis(JPanel panel){listaPelis.add(panel);}
    public void addListaSerie(JPanel panel){listaSeries.add(panel);}
    public void addListaContenido(JPanel panel){listaContenido.add(panel);}
    public void addListaUser(JPanel panel){ listaUsers.add(panel);}

    public JPanel getListaPelis(){return listaPelis;}
    public JPanel getListaSeries(){return listaSeries;}
    public JPanel getListaContenido(){return  listaContenido;}
    public JPanel getListaUsers(){ return listaUsers;}


    public void setViewportViewScroll(JPanel panel){Pelis.setViewportView(panel);};
    public void setViewportViewScrollSerie(JPanel panel){SeriesPanel.setViewportView(panel);};
    public void setViewportViewScrollContenido(JPanel panel){contenidoListas.setViewportView(panel);};
    public void setViewportViewScrollUser(JPanel panel){UsersPanel.setViewportView(panel);};

    public JComboBox getComboBox1(){return comboBox1;}
    public void setModelComboBox(ComboBoxModel<Lista> cbL){comboBox1.setModel(cbL);}
    public void removeAllComboBox(){comboBox1.removeAllItems();}
    public void addItemComboBox1(Lista l){comboBox1.addItem(l);}
    public JPanel getPanel() {
        return panel1;
    }
    public Lista getListaSeleccionada(){return (Lista) comboBox1.getSelectedItem(); }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


    public void removeAllContenido() { listaContenido.removeAll();}
}

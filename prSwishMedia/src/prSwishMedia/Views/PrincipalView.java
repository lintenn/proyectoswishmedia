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
    private JPanel listaPelis;
    private JPanel listaSeries;


    public PrincipalView(){
        listaPelis=new JPanel();
        listaSeries=new JPanel();
    }

    public void controlador(ActionListener ctr){
        Perfil.addActionListener(ctr);


        Perfil.setActionCommand("PROFILE");
    }

    public void removeAllListas(){listaPelis.removeAll();}
    public void removeAllListasSerie(){listaSeries.removeAll();}

    public void setLayoutListas(int cont){listaPelis.setLayout(new GridLayout(cont,0,0,0));}
    public void setLayoutListasSerie(int cont){listaSeries.setLayout(new GridLayout(cont,0,0,0));}

    public void getPanelPeli(PeliculaPreView pv){listaPelis.add(pv);}

    public void addListaPelis(JPanel panel){listaPelis.add(panel);}
    public void addListaSerie(JPanel panel){listaSeries.add(panel);}

    public JPanel getListaPelis(){return listaPelis;}
    public JPanel getListaSeries(){return listaSeries;}

    public void setViewportViewScroll(JPanel panel){Pelis.setViewportView(panel);};
    public void setViewportViewScrollSerie(JPanel panel){SeriesPanel.setViewportView(panel);};

    public JComboBox getComboBox1(){return comboBox1;}
    public void setModelComboBox(ComboBoxModel<Lista> cbL){comboBox1.setModel(cbL);}
    public void removeAllComboBox(){comboBox1.removeAllItems();}
    public void addItemComboBox1(Lista l){comboBox1.addItem(l);}
    public JPanel getPanel() {
        return panel1;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


}

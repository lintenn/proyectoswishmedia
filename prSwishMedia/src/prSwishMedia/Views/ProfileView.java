package prSwishMedia.Views;

import prSwishMedia.Lista;
import prSwishMedia.Main;
import prSwishMedia.Usuario;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;

public class ProfileView extends JFrame{
    private JPanel panel1;
    private JLabel labelPeliculasVistas;
    private JLabel labelCapVistos;
    private JLabel labelNumeroAmigos;
    private JLabel Descripción;
    private JLabel numPeliculas;
    private JLabel numCapitulos;
    private JLabel numAmigos;
    private JLabel nombreUsuario;
    private JLabel labelPrivacidad;
    private JCheckBox checkBoxPrivacidad;
    private JTextArea textAreaDescripcion;
    private JLabel labelSeriesVistas;
    private JLabel numeroSeriesVistas;
    private JLabel labelFechaCreacion;
    private JLabel fechaCreacion;
    private JLabel labelFechaNacimiento;
    private JLabel fechaNacimiento;
    private JComboBox<Lista> comboBoxListas;
    private JLabel labelLista;
    private JButton buttonCrearLista;
    private JButton buttonEliminarLista;
    private JPanel nombreYVisualizaciones;
    private JPanel listasPanel;
    private JTextField nombreLista;
    private JLabel labelInfoCrearLista;
    private JPanel panelFechas;
    private JLabel msgEliminarLista;
    private JButton volver;
    private JButton logout;
    private Usuario user;

    public ProfileView(){
        add(panel1);
        user= Main.getUser();
        setInfo();
    }

    private void actualizarComboBox() {
        if(user.getListasPersonales()!=null){
            for(Lista l: user.getListasPersonales()){
                comboBoxListas.addItem(l);
            }
        }
    }

    public void controlador(ActionListener ctr){
        buttonCrearLista.addActionListener(ctr);
        buttonEliminarLista.addActionListener(ctr);
        volver.addActionListener(ctr);
        logout.addActionListener(ctr);

        //checkBoxPrivacidad.addChangeListener(ctr1);


        //checkBoxPrivacidad.setActionCommand("PRIVACIDAD");
        logout.setActionCommand("LOGOUT");
        volver.setActionCommand("VOLVER");
        buttonEliminarLista.setActionCommand("ELIMINAR");
        buttonCrearLista.setActionCommand("CREAR");
    }

    public void setInfo(){

         nombreUsuario.setText(user.getNombre());
         numAmigos.setText(""+user.getNumAmigos()+"");
         numCapitulos.setText(user.getNumEpisodiosVistos() + "");
         numeroSeriesVistas.setText(user.getNumSeriesVistas()+"");
         numPeliculas.setText(user.getNumPeliculasVistas()+"");
         fechaCreacion.setText(user.getFechaCreacion().toString());
         fechaNacimiento.setText(user.getFechaNacimiento().toString());



    }

    public void setMsgEliminarLista(String error) {
        msgEliminarLista.setText(error);
    }

    public String getNombreListaCreada(){ return nombreLista.getText();}
    public Lista getListaEliminada(){ return (Lista) comboBoxListas.getSelectedItem(); }
    public JPanel getPanel() {
        return panel1;
    }
}

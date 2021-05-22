package prSwishMedia.Views;

import prSwishMedia.Lista;
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
    private Usuario user;

    public ProfileView(Usuario user){
        add(panel1);
        this.user=user;
        nombreUsuario.setText(user.getNombre());
        actualizarComboBox();
        numCapitulos.setText(String.valueOf(user.getNumEpisodiosVistos()));
        numeroSeriesVistas.setText(String.valueOf(user.getNumSeriesVistas()));
        numAmigos.setText(String.valueOf(user.getNumAmigos()));
        numPeliculas.setText(String.valueOf(user.getNumPeliculasVistas()));
        nombreUsuario.setText(user.getNombre());
        fechaNacimiento.setText(String.valueOf(user.getFechaNacimiento()));
        fechaCreacion.setText(String.valueOf(user.getFechaCreacion()));
        textAreaDescripcion.setText(user.getDescripcion());
    }

    private void actualizarComboBox() {
        if(user.getListasPersonales()!=null){
            for(Lista l: user.getListasPersonales()){
                comboBoxListas.addItem(l);
            }
        }
    }

    public void controlador(ActionListener ctr, ChangeListener ctr1){
        buttonCrearLista.addActionListener(ctr);
        buttonEliminarLista.addActionListener(ctr);
        checkBoxPrivacidad.addChangeListener(ctr1);

        checkBoxPrivacidad.setActionCommand("PRIVACIDAD");
        buttonEliminarLista.setActionCommand("ELIMINAR");
        buttonCrearLista.setActionCommand("CREAR");
    }

    public void setMsgEliminarLista(String error){
        msgEliminarLista.setText(error);};
    public String getNombreListaCreada(){ return nombreLista.getText();}
    public Lista getListaEliminada(){ return (Lista) comboBoxListas.getSelectedItem(); }
    public JPanel getPanel() {
        return panel1;
    }
}

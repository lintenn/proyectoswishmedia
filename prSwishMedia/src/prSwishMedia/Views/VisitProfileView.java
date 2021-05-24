package prSwishMedia.Views;

import prSwishMedia.Main;
import prSwishMedia.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class VisitProfileView extends JFrame{
    private JPanel panel1;
    private JLabel Descripción;
    private JLabel labelPrivacidad;
    private JCheckBox checkBoxPrivacidad;
    private JPanel nombreYVisualizaciones;
    private JLabel nombreUsuario;
    private JLabel labelPeliculasVistas;
    private JLabel labelCapVistos;
    private JLabel labelNumeroAmigos;
    private JLabel numPeliculas;
    private JLabel numCapitulos;
    private JLabel numAmigos;
    private JLabel labelSeriesVistas;
    private JLabel numeroSeriesVistas;
    private JPanel panelFechas;
    private JLabel labelFechaNacimiento;
    private JLabel labelFechaCreacion;
    private JLabel fechaCreacion;
    private JLabel fechaNacimiento;
    private JButton volver;
    private JTextArea textAreaDescripcion;
    private Usuario user;

    public  VisitProfileView(){
        add(panel1);
        setInfo();
    }

    public void setInfo(){
        textAreaDescripcion.setText(user.getDescripcion());
        nombreUsuario.setText(user.getNombre());
        numAmigos.setText(""+user.getNumAmigos()+"");
        numCapitulos.setText(user.getNumEpisodiosVistos() + "");
        numeroSeriesVistas.setText(user.getNumSeriesVistas()+"");
        numPeliculas.setText(user.getNumPeliculasVistas()+"");
        if(user.getFechaCreacion()!=null)fechaCreacion.setText(user.getFechaCreacion().toString());
        if(user.getFechaNacimiento()!=null)fechaNacimiento.setText(user.getFechaNacimiento().toString());

        if(user.getPrivacidad()){
            checkBoxPrivacidad.setEnabled(true);
        }else{
            checkBoxPrivacidad.setEnabled(false);
        }

    }

}

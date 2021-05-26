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

    }

    public void setDescripcion(String descripcion){
        textAreaDescripcion.setText(descripcion);
    }
    public void setNombreUsuario(String nombre){nombreUsuario.setText(nombre);}
    public void setNumCapitulos(int num){numCapitulos.setText(String.valueOf(num));}
    public void setNumSeriesVistas(int num){numeroSeriesVistas.setText(String.valueOf(num));}
    public void setNumPeliculas(int num){numPeliculas.setText(String.valueOf(num));}
    public void setFechaCreacion(String date){fechaCreacion.setText(date);}
    public void setNumAmigos(int num){numAmigos.setText(String.valueOf(num));}
    public void setFechaNacimiento(String date){fechaNacimiento.setText(date);}

    public void setCheckBoxPrivacidad(boolean valor) {checkBoxPrivacidad.setSelected(valor); }


}

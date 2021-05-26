package prSwishMedia.Views;

import javax.security.auth.login.AccountNotFoundException;
import javax.swing.*;
import java.awt.event.ActionListener;

public class PeliculaView extends JFrame{

    private JPanel panelPrincipal;
    private JPanel panelSuperior;
    private JPanel panelInferiorIzq;
    private JPanel panelInferiorDech;
    private JLabel nombrePelicula;
    private JLabel valoracionPelicula;
    private JLabel fechaPelicula;
    private JLabel añadidaPelicula;
    private JLabel peliculaDuracion;
    private JLabel peliculaGénero;
    private JTextPane sinopsisPelicula;
    private JTextPane repartoPelicula;
    private JButton trailerPelicula;
    private JComboBox valorarPelicula;
    private JPanel panelComentarios;
    private JComboBox comboBox1;
    private JLabel peliculaImagen;
    private JButton back;

    public PeliculaView(){


    }

    public void setNombrePelicula(String nombre) {
        nombrePelicula.setText(nombre);
    }

    public void setValoracionPelicula(String valoracion) {
        valoracionPelicula.setText(valoracion);
    }

    public void setFechaPelicula(String fecha) {
        fechaPelicula.setText(fecha);
    }

    public void setPeliculaDuracion(int Duracion) {
        peliculaDuracion.setText(Integer.toString(Duracion));
    }

    public void setPeliculaGénero(String genero) {
        peliculaGénero.setText(genero);
    }

    public void setSinopsisPelicula(String sinopsis) {
        sinopsisPelicula.setText(sinopsis);
    }

    public void setRepartoPelicula(String reparto) {
        repartoPelicula.setText(reparto);
    }

    public JPanel getPanel(){
        return panelPrincipal;
    }

    public void controller(ActionListener ctr){
        back.addActionListener(ctr);
        trailerPelicula.addActionListener(ctr);
        trailerPelicula.setActionCommand("TRAILER");
        back.setActionCommand("VOLVER");
    }
}

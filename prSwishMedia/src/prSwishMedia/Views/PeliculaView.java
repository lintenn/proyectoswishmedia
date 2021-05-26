package prSwishMedia.Views;

import javax.security.auth.login.AccountNotFoundException;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

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
    private JScrollPane ComentariosPanel;
    private JButton buttonenviar;
    private JTextField textFieldComentarios;
    private String nombrePeli;

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
        repartoPelicula.setText(reparto);
    }

    public JPanel getPanel(){
        return panelPrincipal;
    }

    public void controlador(ActionListener ctr){
        back.addActionListener(ctr);
        trailerPelicula.addActionListener(ctr);
        buttonenviar.addActionListener(ctr);
        textFieldComentarios.addKeyListener((KeyListener) ctr);

        trailerPelicula.setActionCommand("TRAILER");
        back.setActionCommand("VOLVER");
        buttonenviar.setActionCommand("ENVIAR");
    }

    public String getNombre(){
        return nombrePeli;
    }

    public void setComentariosPanel(JPanel panel){
        ComentariosPanel.setViewportView(panel);
    }

    public JTextField getTextFieldComentarios(){
        return textFieldComentarios;
    }

}

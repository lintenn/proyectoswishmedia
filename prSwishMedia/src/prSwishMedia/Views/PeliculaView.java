package prSwishMedia.Views;

import prSwishMedia.Lista;

import javax.imageio.ImageIO;
import javax.security.auth.login.AccountNotFoundException;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.SQLException;

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
    private JComboBox comboBoxvalorar;
    private JLabel peliculaImagen;
    private JButton back;
    private JScrollPane ComentariosPanel;
    private JButton buttonenviar;
    private JTextField textField1;
    private JLabel Foto;
    private JComboBox comboBoxlista;
    private JButton añadir;
    private JButton eliminar;
    private JTextArea textArea1;
    private JTextField textFieldComentarios;
    private String nombrePeli;

    public PeliculaView(){
        ComentariosPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ComentariosPanel.getVerticalScrollBar().setUnitIncrement(16);

        Cursor hand=new Cursor(Cursor.HAND_CURSOR);
        Cursor text=new Cursor(Cursor.TEXT_CURSOR);

        back.setCursor(hand);
        trailerPelicula.setCursor(hand);
        comboBoxlista.setCursor(hand);
        comboBoxvalorar.setCursor(hand);
        añadir.setCursor(hand);
        eliminar.setCursor(hand);
        buttonenviar.setCursor(hand);

        sinopsisPelicula.setCursor(text);
        repartoPelicula.setCursor(text);
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

    public void setAñadidaPelicula(String t) {
        añadidaPelicula.setText(t);
    }

    public void setRepartoPelicula(String reparto) {
        repartoPelicula.setText(reparto);
        repartoPelicula.setText(reparto);
    }

    public void setImagen(int id){

        try {
            Foto.setIcon(new ImageIcon(ImageIO.read (PeliculaView.class.getResourceAsStream(/*"prSwishMedia/imagen/" +*/ "/x"+id +".jpg"))));
        } catch (IOException e) {
            Foto.setIcon(new ImageIcon("prSwishMedia/imagen/x"+ id +".jpg"));
        }
    }

    public JPanel getPanel(){
        return panelPrincipal;
    }

    public void controlador(ActionListener ctr){
        back.addActionListener(ctr);
        trailerPelicula.addActionListener(ctr);
        buttonenviar.addActionListener(ctr);
        textField1.addKeyListener((KeyListener) ctr);
        comboBoxvalorar.addActionListener(ctr);
        añadir.addActionListener(ctr);
        eliminar.addActionListener(ctr);

        trailerPelicula.setActionCommand("TRAILER");
        back.setActionCommand("VOLVER");
        buttonenviar.setActionCommand("ENVIAR");
        comboBoxvalorar.setActionCommand("VALORAR");
        añadir.setActionCommand("AÑADIR");
        eliminar.setActionCommand("ELIMINAR");
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

    public JTextField getTextField1(){
        return textField1;
    }

    public void setTextField1(String s){
        textField1.setText(s);
    }
    public void setComboBoxvalorar2(String s){
        comboBoxvalorar.addItem(s);
    }
    public void setComboBoxvalorar(int x){
        comboBoxvalorar.addItem(x);
    }
    public Object getItemComboBoxvalorar(){
        return comboBoxvalorar.getSelectedItem();
    }

    public JComboBox getComboBoxvalorar(){
        return comboBoxvalorar;
    }



    public void setAñadirPelicula(Lista l){
        comboBoxlista.addItem(l);
    }

    public Lista getAñadirPelicula(){
        return (Lista) comboBoxlista.getSelectedItem();
    }

}

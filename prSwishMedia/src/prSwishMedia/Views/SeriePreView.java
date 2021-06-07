package prSwishMedia.Views;

import prSwishMedia.Lista;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SeriePreView extends ContenidoMultimediaPreView{
    private JPanel panel1;
    private JLabel Imagen;
    private JLabel Nombre;
    private JTextPane Sinopsis;
    private JLabel Genero;
    private JLabel labelnumTemporada;
    private JComboBox comboBox1;
    private JLabel msgInfo;
    private JLabel valoracion;
    private JLabel numTemporadas;
    private JButton añadirbotón;
    private JPanel panelito;

    private Cursor manita = new Cursor(Cursor.HAND_CURSOR);
    private Cursor cursorTexto = new Cursor(Cursor.TEXT_CURSOR);

    public SeriePreView(){
        panelito.setCursor(manita);
        Sinopsis.setCursor(cursorTexto);
        comboBox1.setCursor(manita);
        añadirbotón.setCursor(manita);
    }

    public void controlador(ActionListener ctr){
        añadirbotón.addActionListener(ctr);
        añadirbotón.setActionCommand("AÑADIR");

    }
    public Object getValoracion(){
        return comboBox1.getSelectedItem();
    }

    public void setValoracion2(String s) {
        valoracion.setText(s);
    }
    public void setMsgInfo(String msg){
        msgInfo.setText(msg);
    }

    public void setImagen(int id){

        try {
            Imagen.setIcon(new ImageIcon(ImageIO.read (SeriePreView.class.getResourceAsStream(/*"prSwishMedia/imagen/" +*/ "/"+id +".jpg"))));
        } catch (IOException e) {
            Imagen.setIcon(new ImageIcon( "prSwishMedia/imagen/"+id +".jpg"));
        }

    }
    public void setComboBox(JComboBox listas){
        if(listas!=null){
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            for(int i=0; i<listas.getItemCount();i++){
                model.addElement(listas.getItemAt(i));
            }
            comboBox1.setModel(model);
        }

    }
    public void setNombre(String n){
        Nombre.setText(n);
    }
    public void setSinopsis(String s){
        Sinopsis.setText(s);
    }
    public void setGenero(String genero) { Genero.setText(genero); }
    public void setValoracion(double v) {
        valoracion.setText(Double.toString(v));
    }
    public void setNumTemporadas(int num){numTemporadas.setText(String.valueOf(num));}
    public JPanel getPanel() {
        return panel1;
    }

    public Lista getSelectedComboBox() {return (Lista) comboBox1.getSelectedItem(); }

    public void setVisibleAñadir(boolean b) {
        añadirbotón.setVisible(b);
    }

    public void setVisibleComboBox(boolean b) {
        comboBox1.setVisible(b);
    }
}

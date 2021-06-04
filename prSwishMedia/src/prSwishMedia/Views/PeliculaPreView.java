package prSwishMedia.Views;

import prSwishMedia.Lista;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class PeliculaPreView extends ContenidoMultimediaPreView{
    private JPanel panel1;
    private JComboBox Listas;
    private JTextPane Sinopsis;
    private JLabel Nombre;
    private JLabel Imagen;
    private JLabel Genero;
    private JLabel Valoración;
    private JButton añadirbotón;
    private JPanel panelito;

    private Cursor manita = new Cursor(Cursor.HAND_CURSOR);
    private Cursor cursorTexto = new Cursor(Cursor.TEXT_CURSOR);

    public PeliculaPreView(){
        Sinopsis.setCursor(cursorTexto);
        Listas.setCursor(manita);
        añadirbotón.setCursor(manita);
        panelito.setCursor(manita);
    }
    public void controlador(ActionListener ctr){
        añadirbotón.addActionListener(ctr);
        añadirbotón.setActionCommand("AÑADIR");

    }
    // Método para actualizar la valoracion media
    public void setValoracion(double valoracion) {
        Valoración.setText(Double.toString(valoracion));
    }
    public void setValoracion2(String s) {
        Valoración.setText(s);
    }
    public void setNombre(String nombre){Nombre.setText(nombre);}
    public void setImagen(int imagen){Imagen.setIcon(new ImageIcon( "prSwishMedia/imagen/"+imagen +".jpg")); }
    public void setComboBox(JComboBox listas){
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        if(listas!=null){
            for(int i=0; i<listas.getItemCount();i++){
                model.addElement(listas.getItemAt(i));
            }
            Listas.setModel(model);
        }

    }
    public void setSinopsis(String s){Sinopsis.setText(s);}
    public void setGenero(String s){Genero.setText(s);}

    public Lista getSelectedComboBox(){return (Lista) Listas.getSelectedItem();}
    public JPanel getPanel() {
        return panel1;
    }


    public void setVisibleAñadir(boolean b) {
        añadirbotón.setVisible(false);
    }

    public void setVisibleComboBox(boolean b) {
        Listas.setVisible(false);
    }
}

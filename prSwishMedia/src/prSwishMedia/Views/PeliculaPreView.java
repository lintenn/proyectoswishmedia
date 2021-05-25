package prSwishMedia.Views;

import javax.swing.*;
import javax.swing.event.ListDataListener;

public class PeliculaPreView extends JFrame{
    private JPanel panel1;
    private JComboBox Listas;
    private JTextPane Sinopsis;
    private JLabel Nombre;
    private JLabel Imagen;
    private JLabel Genero;
    private JLabel Valoración;

    public PeliculaPreView(String nombre, int imagen,String sinopsis,String genero,int valoracion, JComboBox listas){
        Nombre.setText(nombre);
        Imagen.setIcon(new ImageIcon( "prSwishMedia/imagen/"+imagen +".jpg"));
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for(int i=0; i<listas.getItemCount();i++){
            model.addElement(listas.getItemAt(i));
        }
        Listas.setModel(model);
        Sinopsis.setText(sinopsis);
        Genero.setText(genero);
        Valoración.setText(Integer.toString(valoracion));
    }

    public JPanel getPanel() {
        return panel1;
    }

}

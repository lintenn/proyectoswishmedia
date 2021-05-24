package prSwishMedia.Views;

import javax.swing.*;

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
        Listas=listas;
        Sinopsis.setText(sinopsis);
        Genero.setText(genero);
        Valoración.setText(Integer.toString(valoracion));
    }

    public JPanel getPanel() {
        return panel1;
    }
}

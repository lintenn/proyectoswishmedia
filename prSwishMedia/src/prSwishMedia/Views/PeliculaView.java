package prSwishMedia.Views;

import javax.swing.*;

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
    private JLabel back;

    public PeliculaView(){
        add(panelPrincipal);
    }

    public JPanel getPanel(){
        return panelPrincipal;
    }

}

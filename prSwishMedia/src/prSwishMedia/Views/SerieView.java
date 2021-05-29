package prSwishMedia.Views;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class SerieView extends JFrame {

    private JPanel panelPrincipal;
    private JPanel panelSuperior;
    private JLabel nombreSerie;
    private JLabel valoracionSerie;
    private JLabel fechaSerie;
    private JLabel añadidaSerie;
    private JPanel panelInferiorDech;
    private JButton trailerSerie;
    private JPanel panelComentarios;
    private JComboBox valorarSerie;
    private JLabel generoSerie;
    private JLabel temporadasSerie;
    private JTextPane sinopsisSerie;
    private JTextPane repartoSerie;
    private JComboBox añadirSerie;
    private JLabel capitulosSerie;
    private JLabel duracionSerie;
    private JScrollPane ComentariosPanel;
    private JLabel Imagen;
    private JButton back;
    private JTextField textField1;
    private JButton button1;

    public SerieView(){
        ComentariosPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public void controlador(ActionListener ctr){
        back.addActionListener(ctr);
        button1.addActionListener(ctr);
        textField1.addKeyListener((KeyListener) ctr);

        back.setActionCommand("VOLVER");
        button1.setActionCommand("ENVIAR");
    }

    public void setNombreSerie(String nom) {
        nombreSerie.setText(nom);
    }

    public void setValoracionSerie(Double v){
        valoracionSerie.setText(Double.toString(v));
    }

    public void setFechaSerie(String f){
        fechaSerie.setText(f);
    }

    public void setGeneroSerie(String g){
        generoSerie.setText(g);
    }

    public void setAñadidaSerie(int s){
        añadidaSerie.setText(Integer.toString(s));
    }

    public void setTemporadasSerie(int n){
        temporadasSerie.setText(Integer.toString(n));
    }

    public void setSinopsisSerie(String s){
        sinopsisSerie.setText(s);
    }

    public void setRepartoSerie(String s){
        repartoSerie.setText(s);
    }

    public void setCapitulosSerie(int s){
        capitulosSerie.setText(Integer.toString(s));
    }

    public void setDuracionSerie(Double s){
        duracionSerie.setText(Double.toString(s));
    }

    public void setTextField1(String s){
        textField1.setText(s);
    }
    

    public JTextField getTextField1(){
        return textField1;
    }

    public void setComentariosPanel(JPanel panel){
        ComentariosPanel.setViewportView(panel);
    }

    public JPanel getPanel(){
        return panelPrincipal;
    }

}

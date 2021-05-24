package prSwishMedia.Views;

import javax.swing.*;

public class SeriePreView extends JFrame{
    private JPanel panel1;
    private JLabel Imagen;
    private JLabel Nombre;
    private JTextPane Sinopsis;
    private JLabel labelnumTemporada;
    private JComboBox comboBox1;
    private JLabel msgInfo;
    private JLabel valoracion;
    private JLabel numTemporadas;

    public SeriePreView(){
        add(panel1);

    }
    public Object getValoracion(){
        return comboBox1.getSelectedItem();
    }
    public void setNumTemporadas(int n){
        labelnumTemporada.setText("Número temporadas: "+String.valueOf(n));
    }
    public void setMsgInfo(String msg){
        msgInfo.setText(msg);
    }
    public void setImagen(int id){

    }
    public void setNombre(String n){
        Nombre.setText(n);
    }
    public void setSinopsis(String s){
        Sinopsis.setText(s);
    }

}

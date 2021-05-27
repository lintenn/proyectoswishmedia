package prSwishMedia.Views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SeriePreView extends JFrame{
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

    public SeriePreView(){

    }

    public void controlador(ActionListener ctr){

    }
    public Object getValoracion(){
        return comboBox1.getSelectedItem();
    }


    public void setMsgInfo(String msg){
        msgInfo.setText(msg);
    }
    public void setImagen(int id){
        Imagen.setIcon(new ImageIcon( "prSwishMedia/imagen/"+id +".jpg"));
    }
    public void setComboBox(JComboBox listas){
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for(int i=0; i<listas.getItemCount();i++){
            model.addElement(listas.getItemAt(i));
        }
        comboBox1.setModel(model);
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
}

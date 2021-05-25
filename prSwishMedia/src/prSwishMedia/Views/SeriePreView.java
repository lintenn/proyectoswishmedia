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

    public SeriePreView(String nombre, int imagen,String sinopsis,int valoracion, JComboBox listas, int numTemporadas){
        Nombre.setText(nombre);
        Imagen.setIcon(new ImageIcon( "prSwishMedia/imagen/"+imagen +".jpg"));
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for(int i=0; i<listas.getItemCount();i++){
            model.addElement(listas.getItemAt(i));
        }
        comboBox1.setModel(model);
        Sinopsis.setText(sinopsis);
        this.valoracion.setText(Integer.toString(valoracion));
        this.numTemporadas.setText(Integer.toString(numTemporadas));

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

    public JPanel getPanel() {
        return panel1;
    }
}

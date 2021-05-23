package prSwishMedia.Views;

import com.mysql.jdbc.Statement;
import prSwishMedia.Lista;
import prSwishMedia.Main;
import prSwishMedia.Usuario;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileView extends JFrame{
    private JPanel panel1;
    private JLabel labelPeliculasVistas;
    private JLabel labelCapVistos;
    private JLabel labelNumeroAmigos;
    private JLabel Descripción;
    private JLabel numPeliculas;
    private JLabel numCapitulos;
    private JLabel numAmigos;
    private JLabel nombreUsuario;
    private JLabel labelPrivacidad;
    private JCheckBox checkBoxPrivacidad;
    private JTextArea textAreaDescripcion;
    private JLabel labelSeriesVistas;
    private JLabel numeroSeriesVistas;
    private JLabel labelFechaCreacion;
    private JLabel fechaCreacion;
    private JLabel labelFechaNacimiento;
    private JLabel fechaNacimiento;
    private JComboBox<Lista> comboBoxListas;
    private JLabel labelLista;
    private JButton buttonCrearLista;
    private JButton buttonEliminarLista;
    private JPanel nombreYVisualizaciones;
    private JPanel listasPanel;
    private JTextField nombreLista;
    private JLabel labelInfoCrearLista;
    private JPanel panelFechas;
    private JLabel msgEliminarLista;
    private JButton volver;
    private JButton logout;
    private JLabel msgCrearLista;
    private Usuario user;

    public ProfileView(Statement st){
        add(panel1);
        user = Main.getUser();
        setInfo();

        try {
            ResultSet rs = st.executeQuery("SELECT * FROM Lista where Nombreusuario = '"+user.getNombre()+"';");
            while(rs.next()){
                user.añadirLista(new Lista(rs.getInt(1),rs.getString(2),rs.getDate(3)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        actualizarComboBox();
    }

    public void setUser(Usuario user) {
        this.user = user;
        setInfo(); //f
    }

    public void añadirComboBox(Lista l){
        comboBoxListas.addItem(l);
    }
    public void eliminarComboBox(Lista l){
        comboBoxListas.removeItem(l);
    }

    public void actualizarComboBox() {
        if(user.getListasPersonales()!=null){
            for(Lista l: user.getListasPersonales()){
                comboBoxListas.addItem(l);
            }
        }else {
            System.out.println("LISTA VACIA");
        }
    }

    public void controlador(ActionListener ctr){
        buttonCrearLista.addActionListener(ctr);
        buttonEliminarLista.addActionListener(ctr);
        volver.addActionListener(ctr);
        logout.addActionListener(ctr);

        //checkBoxPrivacidad.addChangeListener(ctr1);


        //checkBoxPrivacidad.setActionCommand("PRIVACIDAD");
        logout.setActionCommand("LOGOUT");
        volver.setActionCommand("VOLVER");
        buttonEliminarLista.setActionCommand("ELIMINAR");
        buttonCrearLista.setActionCommand("CREAR");
    }

    public void setInfo(){

         nombreUsuario.setText(user.getNombre());
         numAmigos.setText(""+user.getNumAmigos()+"");
         numCapitulos.setText(user.getNumEpisodiosVistos() + "");
         numeroSeriesVistas.setText(user.getNumSeriesVistas()+"");
         numPeliculas.setText(user.getNumPeliculasVistas()+"");
         if(user.getFechaCreacion()!=null)fechaCreacion.setText(user.getFechaCreacion().toString());

         if(user.getFechaNacimiento()!=null)fechaNacimiento.setText(user.getFechaNacimiento().toString());



    }

    public void setMsgEliminarLista(String error) {
        msgEliminarLista.setText(error);
    }
    public void setMsgCrearLista(String error) {
        msgCrearLista.setText(error);
    }
    public String getNombreListaCreada(){ return nombreLista.getText();}
    public Lista getListaEliminada(){ return (Lista) comboBoxListas.getSelectedItem(); }
    public JPanel getPanel() {
        return panel1;
    }
}

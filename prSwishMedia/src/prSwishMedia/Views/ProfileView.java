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
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.sql.SQLOutput;

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
    private KeyListener listener;
    private Statement stmt;


    public ProfileView(Statement st){
        add(panel1);
        user = Main.getUser();
        setInfo();
        stmt = st;
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Lista where Nombreusuario = '"+user.getNombre()+"';");
            while(rs.next()){
                user.añadirLista(new Lista(rs.getInt(1),rs.getString(2),rs.getDate(3)));
            }
            ResultSet rs2 = stmt.executeQuery("SELECT privacidad FROM Usuario where nombre = '"+user.getNombre()+"';");
            rs2.next();
            if(rs2.getInt(1)==1){
                checkBoxPrivacidad.setSelected(true);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        listener = new MyKeyListener();
        textAreaDescripcion.addKeyListener(listener);
        textAreaDescripcion.setFocusable(true);
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
        checkBoxPrivacidad.addActionListener(ctr);

        checkBoxPrivacidad.setActionCommand("PRIVACIDAD");
        logout.setActionCommand("LOGOUT");
        volver.setActionCommand("VOLVER");
        buttonEliminarLista.setActionCommand("ELIMINAR");
        buttonCrearLista.setActionCommand("CREAR");
    }

    public void setInfo(){
         textAreaDescripcion.setText(user.getDescripcion());
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

    public class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(KeyEvent.getKeyText(e.getKeyCode()).equals("Intro")||KeyEvent.getKeyText(e.getKeyCode()).equals("Enter")){
                try {
                    stmt.executeUpdate("UPDATE Usuario SET descripcion = '"+textAreaDescripcion.getText()+"' where nombre = '"+user.getNombre()+"';");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}

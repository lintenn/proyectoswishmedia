package prSwishMedia.Views;

import com.mysql.jdbc.Statement;
import prSwishMedia.Lista;
import prSwishMedia.Main;
import prSwishMedia.Usuario;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PrincipalView extends JFrame{
    private JPanel panel1;
    private JButton Perfil;
    private JTabbedPane tabbedPane1;
    private JPanel Películas;
    private JPanel Series;
    private JComboBox comboBox1;
    private JPanel Listas;
    private JLabel Logo;
    private JPanel Usuarios;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private Usuario user;
    private Statement st;

    public PrincipalView(Statement st){
        user = Main.getUser();
        this.st = st;


    }

    public void controlador(ActionListener ctr){
        Perfil.addActionListener(ctr);


        Perfil.setActionCommand("PROFILE");
    }

    public JPanel getPanel() {
        return panel1;
    }

    public void actualizarComboBox() {
        if(user.getListasPersonales()!=null){
            for(Lista l: user.getListasPersonales()){
                comboBox1.addItem(l);
            }
        }else {
            System.out.println("LISTA VACIA");
        }
    }

    public void setUser(Usuario u){
        user = u;
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
}

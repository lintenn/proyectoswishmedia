package prSwishMedia;

import com.kitfox.svg.Use;
import com.mysql.jdbc.Statement;
import prSwishMedia.Controllers.*;
import prSwishMedia.Listeners.MouseClick;
import prSwishMedia.Views.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Date;



public class Main {

    public static JFrame frame;
    static Usuario user=new Usuario("d");

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        java.sql.Connection conexion = DriverManager.getConnection("jdbc:mysql://iis2021.cobadwnzalab.eu-central-1.rds.amazonaws.com:3306/grupoG","usuarioG","gorgonezhao");
        Statement stmt = (Statement) conexion.createStatement();
        Statement stmt1 = (Statement) conexion.createStatement();
        Statement stmt2 = (Statement) conexion.createStatement();
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme");

        LoginView lview = new LoginView();
        RegisterView rview = new RegisterView();
        ConfirmedView cview = new ConfirmedView();



        MouseClick mc =new MouseClick(lview,cview);
        lview.getForgot().addMouseListener(mc);

        frame = new JFrame();
        frame.setMinimumSize(new Dimension(1050,610));

        frame.setTitle("SwishMedia");
        try {
            frame.setIconImage(new ImageIcon(ImageIO.read (Main.class.getResourceAsStream("/LogoFondo.jpg"))).getImage());
        } catch (IOException e) {
            frame.setIconImage(new ImageIcon("LogoFondo.jpg").getImage());
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(lview.getPanel());

        // Display the window.
        frame.pack(); //si lo pongo no va el tamaño por defecto
        frame.setVisible(true);


        LoginController cl      = new LoginController(rview,lview,cview,stmt,stmt1,stmt2);
        RegisterController cr   = new RegisterController(rview,lview,stmt);
        ConfirmedController cc  = new ConfirmedController(lview,cview,stmt);

        lview.controlador(cl);
        rview.controlador(cr);
        cview.controlador(cc);

    }

    public static Usuario getUser() {
        return user;
    }
    public static void setUser(Usuario user){
        Main.user =user;
    }
    public static int hallarAmigos(String nombre,java.sql.Statement conexion) {
        int num=0;
        try {
            ResultSet rs = conexion.executeQuery("SELECT COUNT(*) FROM Amigo WHERE usuario2='"+nombre+"' AND isAmigo=1;");
            rs.next();
            num=rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return num;
    }

    public static void setUser(String nick, Statement st) throws SQLException {
        Statement stmt=st;
        String email;
        String descripcion;
        Date fechaNac;
        Date fechaCre;
        String contraseña;
        int numList;
        int numAmigos;
        boolean priv;
        int numComentarios;
        int numSeries;
        int numCap;
        int numPel;

        ResultSet conex=stmt.executeQuery("SELECT * FROM Usuario WHERE nombre='" + nick + "';");
        conex.next();
        email=conex.getString(2);
        descripcion=conex.getString(3);
        try{
            fechaNac=conex.getDate(4);
        }catch (Exception e){
            fechaNac=null;
        }   
        try{
            fechaCre=conex.getDate(5);
        }catch (Exception e){
            fechaCre=null;
        }
        contraseña=conex.getString(6);
        numList=conex.getInt(7);
        numAmigos=conex.getInt(8);
        priv=conex.getBoolean(9);
        numComentarios=conex.getInt(10);
        numSeries=conex.getInt(11);
        numCap=conex.getInt(12);
        numPel=conex.getInt(13);

        user= new Usuario(nick,email,descripcion,fechaNac,fechaCre,contraseña,numList,numAmigos,priv,numComentarios,numSeries,numCap,numPel);

    }

}

package prSwishMedia;

import com.kitfox.svg.Use;
import com.mysql.jdbc.Statement;
import prSwishMedia.Controllers.*;
import prSwishMedia.Listeners.MouseClick;
import prSwishMedia.Views.*;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
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

        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme");

        LoginView lview = new LoginView();
        RegisterView rview = new RegisterView();
        ConfirmedView cview = new ConfirmedView();
        PrincipalView ppview=new PrincipalView();


        MouseClick mc =new MouseClick(lview,cview);
        lview.getForgot().addMouseListener(mc);

        frame = new JFrame();
        frame.setMinimumSize(new Dimension(1000,500));

        frame.setTitle("SwishMedia");
        frame.setIconImage(new ImageIcon("LogoFondo.jpg").getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(lview.getPanel());

        // Display the window.
        frame.pack(); //si lo pongo no va el tamaño por defecto
        frame.setVisible(true);


        LoginController cl      = new LoginController(rview,lview,cview,ppview,stmt);
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

        ResultSet conex=stmt.executeQuery("SELECT FROM Usuario WHERE nombre='" + nick + "';");
        conex.next();
        email=conex.getString(2);
        descripcion=conex.getString(4);
        try{
            fechaNac=conex.getDate(5);
        }catch (Exception e){
            fechaNac=null;
        }
        try{
            fechaCre=conex.getDate(6);
        }catch (Exception e){
            fechaCre=null;
        }
        contraseña=conex.getString(7);
        numList=conex.getInt(8);
        numAmigos=conex.getInt(9);
        priv=conex.getBoolean(10);
        numComentarios=conex.getInt(11);
        numSeries=conex.getInt(12);
        numCap=conex.getInt(13);
        numPel=conex.getInt(14);

        user= new Usuario(nick,email,descripcion,fechaNac,fechaCre,contraseña,numList,numAmigos,priv,numComentarios,numSeries,numCap,numPel);
     
    }

}

package prSwishMedia;

import com.mysql.jdbc.Statement;
import prSwishMedia.Controllers.ConfirmedController;
import prSwishMedia.Controllers.ProfileController;
import prSwishMedia.Controllers.RegisterController;
import prSwishMedia.Listeners.MouseClick;
import prSwishMedia.Controllers.LoginController;
import prSwishMedia.Views.ConfirmedView;
import prSwishMedia.Views.LoginView;
import prSwishMedia.Views.ProfileView;
import prSwishMedia.Views.RegisterView;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;



public class Main {

    public static JFrame frame;
    static Usuario user=null;

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        java.sql.Connection conexion = DriverManager.getConnection("jdbc:mysql://iis2021.cobadwnzalab.eu-central-1.rds.amazonaws.com:3306/grupoG","usuarioG","gorgonezhao");
        Statement stmt = (Statement) conexion.createStatement();

        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme");
        Usuario user=new Usuario("d");

        LoginView lview = new LoginView();
        RegisterView rview = new RegisterView();
        ConfirmedView cview = new ConfirmedView();
        ProfileView pview = new ProfileView(user);

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


        LoginController cl=new LoginController(rview,lview,cview,pview,stmt);
        RegisterController cr=new RegisterController(rview,lview,stmt);
        ConfirmedController cc= new ConfirmedController(lview,cview,stmt);
        ProfileController pc = new ProfileController(pview,user);

        lview.controlador(cl);
        rview.controlador(cr);
        cview.controlador(cc);

    }

    public static Usuario getUser() {
        return user;
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

        email=conex.getString(2);
        descripcion=conex.getString(3);
        fechaNac=conex.getDate(4);
        fechaCre=conex.getDate(5);
        contraseña=conex.getString(6);
        numList=conex.getInt(7);
        numAmigos=conex.getInt(8);
        priv=conex.getBoolean(9);
        numComentarios=conex.getInt(10);
        numSeries=conex.getInt(11);
        numCap=conex.getInt(12);
        numPel=conex.getInt(13);


        Usuario user= new Usuario(nick,email,descripcion,fechaNac,fechaCre,contraseña,numList,numAmigos,priv,numComentarios,numSeries,numCap,numPel);

    }

}

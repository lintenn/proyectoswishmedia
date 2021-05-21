package prSwishMedia;

import prSwishMedia.Controllers.ConfirmedController;
import prSwishMedia.Controllers.RegisterController;
import prSwishMedia.ContenidoMultimedia;
import prSwishMedia.Lista;
import prSwishMedia.Listeners.MouseClick;
import prSwishMedia.Usuario;
import prSwishMedia.Controllers.LoginController;
import prSwishMedia.Views.ConfirmedView;
import prSwishMedia.Views.LoginView;
import prSwishMedia.Views.RegisterView;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class Main {

    public static JFrame frame;

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver");
        java.sql.Connection conexion = DriverManager.getConnection("jdbc:mysql://iis2021.cobadwnzalab.eu-central-1.rds.amazonaws.com:3306/grupoG","usuarioG","gorgonezhao");


        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme");
        Usuario user=new Usuario(1);

        LoginView lview = new LoginView();
        RegisterView rview = new RegisterView();
        ConfirmedView cview = new ConfirmedView();
        MouseClick mc =new MouseClick(lview,cview);
        lview.getForgot().addMouseListener(mc);

        frame = new JFrame();


        frame.setTitle("SwishMedia");
        frame.setIconImage(new ImageIcon("LogoFondo.jpg").getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(lview.getPanel());


        // Display the window.
        frame.pack(); //si lo pongo no va el tamaño por defecto
        frame.setVisible(true);


        LoginController cl=new LoginController(rview,lview,cview,user);
        RegisterController cr=new RegisterController(rview,lview,user);
        ConfirmedController cc= new ConfirmedController(lview,cview);
        lview.controlador(cl);
        rview.controlador(cr);
        cview.controlador(cc);

    }

}

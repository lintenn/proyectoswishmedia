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
import java.awt.*;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static JFrame frame;

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        java.sql.Connection conexion = DriverManager.getConnection("jdbc:mysql://iis2021.cobadwnzalab.eu-central-1.rds.amazonaws.com:3306/grupoG","usuarioG","gorgonezhao");
        Statement stmt = (Statement) conexion.createStatement();

        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme");
        Usuario user=new Usuario(1);

        LoginView lview = new LoginView();
        RegisterView rview = new RegisterView();
        ConfirmedView cview = new ConfirmedView();
        ProfileView pview = new ProfileView();

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


        LoginController cl=new LoginController(rview,lview,cview,pview,stmt,user);
        RegisterController cr=new RegisterController(rview,lview,user);
        ConfirmedController cc= new ConfirmedController(lview,cview);
        ProfileController pc = new ProfileController(pview,user);

        lview.controlador(cl);
        rview.controlador(cr);
        cview.controlador(cc);

    }

}

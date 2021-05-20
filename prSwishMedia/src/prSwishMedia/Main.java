package prSwishMedia;

import prSwishMedia.Controllers.RegisterController;
import prSwishMedia.Usuario;
import prSwishMedia.Controllers.LoginController;
import prSwishMedia.Views.LoginView;
import prSwishMedia.Views.RegisterView;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static JFrame frame;

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme");
        Usuario user=new Usuario(1);
        LoginView lview = new LoginView();
        RegisterView rview = new RegisterView();
        frame = new JFrame();


        frame.setTitle("SwishMedia");
        frame.setIconImage(new ImageIcon("LogoFondo.jpg").getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(lview.getPanel());


        // Display the window.
        frame.pack(); //si lo pongo no va el tamaño por defecto
        frame.setVisible(true);


        LoginController cl=new LoginController(rview,lview,user);
        RegisterController cr=new RegisterController(rview,lview,user);
        lview.controlador(cl);
        rview.controlador(cr);

    }

}

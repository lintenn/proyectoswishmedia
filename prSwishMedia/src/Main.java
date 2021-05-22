import prSwishMedia.Usuario;
import prSwishMedia.Views.LoginController;
import prSwishMedia.Views.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme");
        Usuario user=new Usuario(1);
        LoginView view=new LoginView();
        LoginController c=new LoginController(view,user);
        view.controlador(c);
    }
}

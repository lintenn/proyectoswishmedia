package prSwishMedia.Controllers;

import prSwishMedia.Listeners.MouseClick;
import prSwishMedia.Main;
import prSwishMedia.Usuario;
import prSwishMedia.Views.ConfirmedView;
import prSwishMedia.Views.LoginView;
import prSwishMedia.Views.ProfileView;
import prSwishMedia.Views.RegisterView;

import javax.swing.plaf.nimbus.State;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController implements ActionListener {

    private LoginView lview;
    private RegisterView rview;
    private ConfirmedView cview;
    private ProfileView pview;
    private Usuario user;
    Statement conexion;

    public LoginController(RegisterView rv, LoginView lv, ConfirmedView cv, ProfileView pv, Statement st, Usuario u){
        lview=lv;
        rview=rv;
        cview=cv;
        pview=pv;
        conexion=st;
        user=u;
    }
    public void actionPerformed(ActionEvent ev){
        String act=ev.getActionCommand();

        if(act.equals("LOGIN")){
            String nick=lview.getUser().getText();
            String pass = new String(lview.getPassword().getPassword());
            try {
                ResultSet users = conexion.executeQuery("SELECT nombre FROM Usuario WHERE nombre='" + nick + "'AND contraseña='" + pass + "';");

                if (nick.equals("") || pass.equals("") || !users.next()) {
                    lview.clear("Datos erróneos");
                }else{
                    Main.frame.setContentPane(pview.getPanel());
                    Main.frame.setVisible(true);
                }

            }catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


        } else if(act.equals("REGISTRO")) {
            Main.frame.setContentPane(rview.getPanel());
            Main.frame.setVisible(true);
            lview.clear("");
        }

    }


}

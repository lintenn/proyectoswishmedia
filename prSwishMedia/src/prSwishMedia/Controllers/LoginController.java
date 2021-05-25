package prSwishMedia.Controllers;

import prSwishMedia.Listeners.MouseClick;
import prSwishMedia.Main;
import prSwishMedia.Usuario;
import prSwishMedia.Views.*;

import javax.swing.plaf.nimbus.State;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Statement;

public class LoginController implements ActionListener {

    private LoginView lview;
    private RegisterView rview;
    private ConfirmedView cview;
    Statement conexion;


    public LoginController(RegisterView rv, LoginView lv, ConfirmedView cv, Statement st){
        lview=lv;
        rview=rv;
        cview=cv;
        conexion=st;
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
                    lview.clrPass();
                }else{
                    Main.setUser(nick,conexion);

                    //CREAMOS LAS VISTAS UNA VEZ INICIAMOS SESIÓN
                    PrincipalView ppview=new PrincipalView(conexion);
                    PrincipalController ppc = new PrincipalController(lview, ppview,conexion);
                    ppview.setUser(Main.getUser());
                    ppview.añadirContenido(-2);
                    ppview.controlador(ppc);
                    Main.frame.setContentPane(ppview.getPanel());
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

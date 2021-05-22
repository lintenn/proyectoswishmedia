package prSwishMedia.Controllers;

import com.mysql.jdbc.Statement;
import prSwishMedia.Gmail;
import prSwishMedia.Main;
import prSwishMedia.Views.ConfirmedView;
import prSwishMedia.Views.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfirmedController implements ActionListener{

    private LoginView lview;
    private ConfirmedView cview;
    Statement stmt;

    public ConfirmedController(LoginView lv, ConfirmedView cv, Statement st){
        lview=lv;
        cview=cv;
        stmt=st;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {

        String act=ev.getActionCommand();

        if(act.equals("ENVIAR")){
            try {
                ResultSet esta=stmt.executeQuery("SELECT email FROM Usuario WHERE email='" + cview.getCorreo().getText() + "';");
                if(esta.next()) {
                    Gmail g = new Gmail();
                    g.enviarCorreo(cview.getCorreo().getText(), stmt);
                    cview.setCMessage("La contraseña ha sido enviada");
                }else
                    cview.setEMessage("No existe usuario asociado al email");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }else if(act.equals("VOLVER")){
            cview.clear();
            Main.frame.setContentPane(lview.getPanel());
            Main.frame.setVisible(true);
        }

    }

}

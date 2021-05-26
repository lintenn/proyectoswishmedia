package prSwishMedia.Controllers;

import prSwishMedia.Main;
import prSwishMedia.Views.LoginView;
import prSwishMedia.Views.PrincipalView;
import prSwishMedia.Views.ProfileView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;

public class PrincipalController implements ActionListener {

    LoginView lview;
    Statement conexion;
    PrincipalView ppView;

    public PrincipalController(LoginView lv, PrincipalView ppv, Statement st){
        conexion=st;
        lview=lv;
        ppView=ppv;
    }


    public void actionPerformed(ActionEvent e) {
        String act=e.getActionCommand();

        if(act.equals("PROFILE")){
            ProfileView pview = new ProfileView(conexion);
            ProfileController pc = new ProfileController(pview,ppView,lview,conexion);
            pview.controlador(pc);

            Main.frame.setContentPane(pview.getPanel());
            Main.frame.setVisible(true);
        }
    }
}

package prSwishMedia.Controllers;

import prSwishMedia.Main;
import prSwishMedia.Usuario;
import prSwishMedia.Views.LoginView;
import prSwishMedia.Views.RegisterView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {

    private LoginView lview;
    private RegisterView rview;
    private Usuario user;

    public LoginController(RegisterView rv, LoginView lv,Usuario u){
        lview=lv;
        rview=rv;
        user=u;
    }
    public void actionPerformed(ActionEvent ev){
        String act=ev.getActionCommand();

        if(act.equals("REGISTRO")) {
            Main.frame.setContentPane(rview.getPanel());
            Main.frame.setVisible(true);
        }


    }
}

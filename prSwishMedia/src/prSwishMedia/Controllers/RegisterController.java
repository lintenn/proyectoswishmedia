package prSwishMedia.Controllers;

import prSwishMedia.Gmail;
import prSwishMedia.Main;
import prSwishMedia.Usuario;
import prSwishMedia.Views.LoginView;
import prSwishMedia.Views.RegisterView;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController implements ActionListener {

    private LoginView lview;
    private RegisterView rview;
    private Usuario user;

    public RegisterController(RegisterView rv, LoginView lv,Usuario u){
        lview=lv;
        rview=rv;
        user=u;
    }
    public void actionPerformed(ActionEvent ev) {
        String act = ev.getActionCommand();

        if(act.equals("REGISTRO")){
            String nick=rview.getUser().getText();
            if(!rview.validarMail() || nick.length()<5 || nick.length()>15)
                rview.clear("Datos erróneos");

        }else if(act.equals("VOLVER")) {
            Main.frame.setContentPane(lview.getPanel());
            Main.frame.setVisible(true);
            rview.clear("");

        }


    }
}
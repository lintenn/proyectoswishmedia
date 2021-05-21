package prSwishMedia.Controllers;

import prSwishMedia.Listeners.MouseClick;
import prSwishMedia.Main;
import prSwishMedia.Usuario;
import prSwishMedia.Views.ConfirmedView;
import prSwishMedia.Views.LoginView;
import prSwishMedia.Views.RegisterView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LoginController implements ActionListener {

    private LoginView lview;
    private RegisterView rview;
    private ConfirmedView cview;
    private Usuario user;

    public LoginController(RegisterView rv, LoginView lv,ConfirmedView cv,Usuario u){
        lview=lv;
        rview=rv;
        cview=cv;
        user=u;
    }
    public void actionPerformed(ActionEvent ev){
        String act=ev.getActionCommand();

        if(act.equals("LOGIN")){

            String pass=new String (lview.getPassword().getPassword());
            if(lview.getUser().getText().equals("") || pass.equals("")){
                lview.clear("Datos erróneos");
            }


        } else if(act.equals("REGISTRO")) {
            Main.frame.setContentPane(rview.getPanel());
            Main.frame.setVisible(true);
            lview.clear("");
        }

    }


}

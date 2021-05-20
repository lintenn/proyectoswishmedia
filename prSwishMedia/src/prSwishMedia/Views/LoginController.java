package prSwishMedia.Views;

import prSwishMedia.Usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {

    private LoginView view;
    private Usuario user;

    public LoginController(LoginView v,Usuario u){
        view=v;
        user=u;
    }
    public void actionPerformed(ActionEvent ev){



    }
}

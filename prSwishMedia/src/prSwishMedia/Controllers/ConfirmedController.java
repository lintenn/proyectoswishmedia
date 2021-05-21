package prSwishMedia.Controllers;

import prSwishMedia.Gmail;
import prSwishMedia.Views.ConfirmedView;
import prSwishMedia.Views.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmedController implements ActionListener {

    private LoginView lview;
    private ConfirmedView cview;

    public ConfirmedController(LoginView lv, ConfirmedView cv){
        lview=lv;
        cview=cv;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {

        String act=ev.getActionCommand();

        if(act.equals("ENVIAR")){
            Gmail g=new Gmail();
            g.enviarCorreo();
        }

    }
}

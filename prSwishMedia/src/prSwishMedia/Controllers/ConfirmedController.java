package prSwishMedia.Controllers;

import prSwishMedia.Gmail;
import prSwishMedia.Main;
import prSwishMedia.Views.ConfirmedView;
import prSwishMedia.Views.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ConfirmedController implements ActionListener{

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
            g.enviarCorreo(cview.getCorreo().getText(),"contraseña","usuario");
        }else if(act.equals("VOLVER")){
            Main.frame.setContentPane(lview.getPanel());
            Main.frame.setVisible(true);
        }

    }

}

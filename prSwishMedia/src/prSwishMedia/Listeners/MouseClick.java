package prSwishMedia.Listeners;

import prSwishMedia.Controllers.ConfirmedController;
import prSwishMedia.Main;
import prSwishMedia.Views.ConfirmedView;
import prSwishMedia.Views.LoginView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClick implements MouseListener {

    ConfirmedView cv;
    LoginView lv;

    public MouseClick(LoginView l, ConfirmedView c){
        cv=c;
        lv=l;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Main.frame.setContentPane(cv.getPanel());
        Main.frame.setVisible(true);

        //ConfirmedController cc=new ConfirmedController(lv,cv);
        //cv.controlador(cc);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

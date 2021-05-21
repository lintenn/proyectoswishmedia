package prSwishMedia.Listeners;

import prSwishMedia.Main;
import prSwishMedia.Views.ConfirmedView;
import prSwishMedia.Views.LoginView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClick implements MouseListener {

    ConfirmedView cv=new ConfirmedView();

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
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

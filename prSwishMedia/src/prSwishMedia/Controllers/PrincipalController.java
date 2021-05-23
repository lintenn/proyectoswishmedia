package prSwishMedia.Controllers;

import prSwishMedia.Main;
import prSwishMedia.Views.ProfileView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalController implements ActionListener {

    ProfileView pview;

    public PrincipalController(ProfileView pv){
        pview=pv;
    }


    public void actionPerformed(ActionEvent e) {
        String act=e.getActionCommand();

        if(act.equals("PROFILE")){
            Main.frame.setContentPane(pview.getPanel());
            Main.frame.setVisible(true);
        }
    }
}

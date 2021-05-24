package prSwishMedia.Controllers;

import prSwishMedia.Main;
import prSwishMedia.Usuario;
import prSwishMedia.Views.LoginView;
import prSwishMedia.Views.PrincipalView;
import prSwishMedia.Views.ProfileView;
import prSwishMedia.Views.VisitProfileView;

import java.awt.event.ActionEvent;
import java.sql.Statement;

public class VisitProfileController {


    private VisitProfileView vpview;
    private PrincipalView ppview;
    private Statement conexion;
    private Usuario user;

    public VisitProfileController(VisitProfileView vp, PrincipalView ppv, Statement st){
        vpview=vp;
        ppview=ppv;
        user=Main.getUser();
        conexion=st;
    }

    public void actionPerformed(ActionEvent e) {
        String act = e.getActionCommand();
        switch (act) {
            case "VOLVER":
                Main.frame.setContentPane(ppview.getPanel());
                Main.frame.setVisible(true);
                break;
        }
    }
}

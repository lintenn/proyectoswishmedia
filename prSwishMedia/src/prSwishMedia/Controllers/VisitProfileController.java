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

    public VisitProfileController(VisitProfileView vp, PrincipalView ppv, Statement st, Usuario u){
        vpview=vp;
        ppview=ppv;
        user=u;
        conexion=st;
        setInfo();
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

    public void setInfo(){
        vpview.setDescripcion(user.getDescripcion());
        vpview.setDescripcion(user.getNombre());
        vpview.setNumAmigos(user.getNumAmigos());
        vpview.setNumCapitulos(user.getNumEpisodiosVistos());
        vpview.setNumSeriesVistas(user.getNumSeriesVistas());
        vpview.setNumPeliculas(user.getNumPeliculasVistas());
        if(user.getFechaCreacion()!=null)vpview.setFechaCreacion(user.getFechaCreacion().toString());
        if(user.getFechaNacimiento()!=null)vpview.setFechaNacimiento(user.getFechaNacimiento().toString());

        if(user.getPrivacidad()){
            vpview.setCheckBoxPrivacidad(true);
        }else{
            vpview.setCheckBoxPrivacidad(false);
        }

    }
}

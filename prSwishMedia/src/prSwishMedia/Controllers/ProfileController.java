package prSwishMedia.Controllers;

import prSwishMedia.Lista;
import prSwishMedia.Main;
import prSwishMedia.Usuario;
import prSwishMedia.Views.LoginView;
import prSwishMedia.Views.PrincipalView;
import prSwishMedia.Views.ProfileView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ProfileController implements ActionListener, ChangeListener {

    private ProfileView pview;
    private PrincipalView ppview;
    private LoginView lview;
    private Usuario user;

    public ProfileController(ProfileView vp, PrincipalView ppv,LoginView lv){
        lview=lv;
        pview=vp;
        ppview=ppv;
        user=Main.getUser();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String act=e.getActionCommand();
        switch (act){
            case "CREAR":
                List<Lista> listasSeries = user.getListasPersonales();
                String nombreLista = pview.getNombreListaCreada();
                listasSeries.add(new Lista(1, nombreLista, new Date()));
                break;
            case "ELIMINAR":
                List<Lista> listasSeries1 = user.getListasPersonales();
                boolean esta=listasSeries1.remove(pview.getListaEliminada());
                if(esta) pview.setMsgEliminarLista("Lista eliminada con éxito");
                    else pview.setMsgEliminarLista("Error al eliminar lista");
                break;
            case "VOLVER":
                Main.frame.setContentPane(ppview.getPanel());
                Main.frame.setVisible(true);
                break;
            case "LOGOUT":
                lview.clrPass();
                Main.frame.setContentPane(lview.getPanel());
                Main.frame.setVisible(true);
                break;
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        AbstractButton checkPrivacidad = (AbstractButton) e.getSource();
        ButtonModel buttonModelPrivacidad= (ButtonModel) checkPrivacidad;
        boolean privacidadTick=buttonModelPrivacidad.isPressed();

        if (privacidadTick){
            user.setPrivacidad(true);
        }


    }
}

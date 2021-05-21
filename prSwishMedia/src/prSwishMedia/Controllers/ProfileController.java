package prSwishMedia.Controllers;

import prSwishMedia.Usuario;
import prSwishMedia.Views.ProfileView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileController implements ActionListener, ChangeListener {

    private ProfileView profileView;
    private Usuario user;

    public ProfileController(ProfileView vp, Usuario u){
        profileView=vp;
        user=u;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String act=e.getActionCommand();
        switch (act){
            case "CREAR":

                break;
            case "ELIMINAR":

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

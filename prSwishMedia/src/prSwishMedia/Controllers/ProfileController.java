package prSwishMedia.Controllers;

import prSwishMedia.Lista;
import prSwishMedia.Usuario;
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
                List<Lista> listasSeries = user.getListasPersonales();
                String nombreLista = profileView.getNombreListaCreada();
                listasSeries.add(new Lista(1, nombreLista, new Date()));
                break;
            case "ELIMINAR":
                List<Lista> listasSeries1 = user.getListasPersonales();
                boolean esta=listasSeries1.remove(profileView.getListaEliminada());
                if(esta) profileView.setMsgEliminarLista("Lista eliminada con éxito");
                    else profileView.setMsgEliminarLista("Error al eliminar lista");
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

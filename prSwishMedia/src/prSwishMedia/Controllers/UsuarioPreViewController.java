package prSwishMedia.Controllers;

import prSwishMedia.Usuario;
import prSwishMedia.Views.UsuarioPreView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioPreViewController implements ActionListener {

    private UsuarioPreView userPv;
    private Usuario user;

    public UsuarioPreViewController(UsuarioPreView userpv, Usuario usuario){
        userPv=userpv;
        user=usuario;

        userPv.setNombre(user.getNombre());
        userPv.setDescripcion(user.getDescripcion());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

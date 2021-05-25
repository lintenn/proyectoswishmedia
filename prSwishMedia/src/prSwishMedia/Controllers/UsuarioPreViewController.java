package prSwishMedia.Controllers;

import prSwishMedia.Usuario;
import prSwishMedia.Views.UsuarioPreView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuarioPreViewController implements ActionListener {

    private UsuarioPreView pvuser;
    private Usuario user;

    public UsuarioPreViewController(UsuarioPreView pvu,Usuario u){
        pvuser = pvu;
        user = u;
        pvuser.setDescripción(u.getDescripcion());
        pvuser.setNombre(u.getNombre());
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

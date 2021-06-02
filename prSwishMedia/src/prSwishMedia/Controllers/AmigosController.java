package prSwishMedia.Controllers;

import prSwishMedia.Usuario;
import prSwishMedia.Views.AmigosView;
import prSwishMedia.Views.UsuarioPreView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AmigosController implements ActionListener {

    private Usuario user;
    private Statement conexion;
    private Statement conexion1;
    private AmigosView amigosView;
    public AmigosController(Usuario u, Statement st1,Statement st, AmigosView av){
        user=u;
        conexion=st;
        conexion1=st1;
        amigosView=av;
        rellenarLista();
    }

    private void rellenarLista() {
        amigosView.removeAlllistasUsers();
        try {
            ResultSet count= conexion.executeQuery("SELECT COUNT(*) FROM Amigo WHERE usuario1 = '" + user.getNombre() +"' AND isAmigo=1;");
            count.next();
            int cont = count.getInt(1);

            if(cont!=0){
                ResultSet users= conexion.executeQuery("SELECT * FROM Amigo WHERE usuario1 = '" + user.getNombre() +"' AND isAmigo=1;");
                amigosView.setLayoutListasUsers(cont);

                // Creo lista para almacenar los idContenidoMultimedia y referencias de userpv
                ArrayList<UsuarioPreView> listaUserpv = new ArrayList<>();
                ResultSet amigo;
                while(users.next()) {
                    amigo=conexion1.executeQuery("SELECT * FROM Usuario WHERE nombre = '"+users.getString("usuario2")+"';");
                    amigo.next();
                    Usuario usuario = new Usuario(amigo.getString("nombre"), amigo.getString("email"), amigo.getString("contraseña"),amigo.getString("descripcion"));

                    UsuarioPreView userpv = new UsuarioPreView();
                    UsuarioPreViewController userPvController = new UsuarioPreViewController(userpv,usuario,conexion,user);
                    //listauvC.add(userPvController);
                    userpv.controlador(userPvController);
                    listaUserpv.add(userpv);

                    //PrincipalController.MiMouseListener listener = new PrincipalController.MiMouseListener(0,3, usuario.getNombre(),this, null, null);
                    //userpv.getPanel().addMouseListener(listener);

                    amigosView.addListaUser(userpv.getPanel());
                }

                amigosView.setViewportViewScrollUser(amigosView.getListaUsers());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

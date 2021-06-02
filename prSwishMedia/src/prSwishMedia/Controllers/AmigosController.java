package prSwishMedia.Controllers;

import prSwishMedia.Usuario;
import prSwishMedia.Views.ListaAmigosView;
import prSwishMedia.Views.UsuarioPreView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AmigosController implements ActionListener {

    private Usuario user;
    private Statement conexion;
    private Statement conexion1;
    private ListaAmigosView amigosView;
    private List<UsuarioPreViewController> listauvC;
    private List<UsuarioPreView> listaUsuariosPreViews;

    public AmigosController(Usuario u, Statement st1,Statement st, ListaAmigosView av){
        user=u;
        conexion=st;
        conexion1=st1;
        amigosView=av;
        listauvC=new ArrayList<>();
        listaUsuariosPreViews=new ArrayList<>();
        rellenarLista();
    }

    public void rellenarLista() {
        amigosView.removeAlllistasUsers();
        try {
            ResultSet count= conexion.executeQuery("SELECT COUNT(*) FROM Amigo WHERE usuario1 = '" + user.getNombre() +"' OR usuario2 = '"+ user.getNombre()+"' AND isAmigo=1;");
            count.next();
            int cont = count.getInt(1);

            if(cont!=0){
                ResultSet users= conexion.executeQuery("SELECT * FROM Amigo WHERE usuario1 = '" + user.getNombre() +"' OR usuario2 = '"+ user.getNombre()+"' AND isAmigo=1;");
                amigosView.setLayoutListasUsers(cont);

                ResultSet amigo;
                while(users.next()) {
                    String usuario1 = users.getString("usuario1");
                    String usuario2 = users.getString("usuario2");

                    if(usuario1.equals(user.getNombre())){
                        amigo=conexion1.executeQuery("SELECT * FROM Usuario WHERE nombre = '"+usuario2+"';");
                    }else {
                        amigo=conexion1.executeQuery("SELECT * FROM Usuario WHERE nombre = '"+usuario1+"';");
                    }
                    amigo.next();

                    Usuario usuario = new Usuario(amigo.getString("nombre"), amigo.getString("email"), amigo.getString("contraseña"),amigo.getString("descripcion"));

                    UsuarioPreView userpv = new UsuarioPreView();
                    userpv.botonAñadirInvisible(false);
                    UsuarioPreViewController userPvController = new UsuarioPreViewController(userpv,usuario,conexion,user,this);
                    userpv.controlador(userPvController);

                    listaUsuariosPreViews.add(userpv);
                    listauvC.add(userPvController);

                    //PrincipalController.MiMouseListener listener = new PrincipalController.MiMouseListener(0,3, usuario.getNombre(),this, null, null);
                    // userpv.getPanel().addMouseListener(listener);

                    amigosView.addListaUser(userpv.getPanel());
                }
            }
            amigosView.setViewportViewScrollUser(amigosView.getListaUsers());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void eliminarUnUsuario(String usuario1, String usuario2){
        String amigo;
        if(usuario1.equals(user.getNombre())){
            amigo=usuario2;
        }else{
            amigo=usuario1;
        }
        int cont=0;
        while(cont<listaUsuariosPreViews.size() && !listaUsuariosPreViews.get(cont).getNombre().equals(amigo)){
            cont++;
        }
        amigosView.removeOneUser(listaUsuariosPreViews.get(cont).getPanel());

        amigosView.setViewportViewScrollUser(amigosView.getListaUsers());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String act = e.getActionCommand();

    }
}

package prSwishMedia.Controllers;

import prSwishMedia.Usuario;
import prSwishMedia.Views.NotificacionAmistad;
import prSwishMedia.Views.ProfileView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NotificacionAmistadController implements ActionListener {

    private Usuario tu;
    private String otro;
    private Statement conexion;
    private  NotificaciónController notificaciónController;
    private ProfileView profileView;

    public NotificacionAmistadController(Usuario user1, String user2, Statement st, NotificaciónController nc, ProfileView pw){
        tu=user1;
        otro=user2;
        conexion=st;
        notificaciónController=nc;
        profileView=pw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String act = e.getActionCommand();
        switch (act){
            case ("ACEPTAR"):
                try {
                    ResultSet rs5 = conexion.executeQuery("SELECT numAmigos FROM Usuario where nombre = '"+otro+"';");
                    rs5.next();
                    int nAmigos=rs5.getInt(1);
                    conexion.executeUpdate("UPDATE Amigo SET isAmigo=true where usuario1='"+tu.getNombre()+"' and usuario2='"+otro+"'");
                    conexion.executeUpdate("UPDATE Amigo SET solicitud=false where usuario1='"+tu.getNombre()+"' and usuario2='"+otro+"'");
                    conexion.executeUpdate("UPDATE Amigo SET isNuevoAmigo=true where usuario1='"+tu.getNombre()+"' and usuario2='"+otro+"'");
                    conexion.executeUpdate("UPDATE Amigo SET eresNuevoAmigo=true where usuario1='"+tu.getNombre()+"' and usuario2='"+otro+"'");
                    ResultSet rs3 = conexion.executeQuery("SELECT * FROM Usuario where nombre = '"+tu.getNombre()+"';");
                    rs3.next();
                    conexion.executeUpdate("UPDATE Usuario SET numAmigos="+(nAmigos+1)+" where nombre = '"+otro+"';");

                    notificaciónController.actualizarNotificaciones();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case ("RECHAZAR"):
                try {
                    conexion.executeUpdate("UPDATE Amigo SET isAmigo=false where usuario1='"+tu.getNombre()+"' and usuario2='"+otro+"'");
                    conexion.executeUpdate("UPDATE Amigo SET solicitud=false where usuario1='"+tu.getNombre()+"' and usuario2='"+otro+"'");
                    notificaciónController.actualizarNotificaciones();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
        }
    }
}

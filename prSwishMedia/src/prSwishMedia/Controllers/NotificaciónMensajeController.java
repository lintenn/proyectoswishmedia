package prSwishMedia.Controllers;

import prSwishMedia.Usuario;
import prSwishMedia.Views.NotificacionAmistad;
import prSwishMedia.Views.NotificaciónMensaje;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

public class NotificaciónMensajeController implements ActionListener {

    private Usuario tu;
    private String otro;
    private Statement conexion;
    private NotificaciónController notificaciónController;
    private int i;

    public NotificaciónMensajeController(Usuario user1, String user2, Statement st, NotificaciónController nc, int i){
        tu=user1;
        otro=user2;
        conexion=st;
        notificaciónController=nc;
        this.i=i;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String act = e.getActionCommand();
        switch (act){
            case ("ACEPTAR"):
                try {
                    if(i==1){
                        conexion.executeUpdate("UPDATE Amigo SET mensaje=false where usuario1='"+tu.getNombre()+"' and usuario2='"+otro+"'");
                    } else if(i==2){
                        conexion.executeUpdate("UPDATE Amigo SET isNuevoAmigo=false where usuario1='"+tu.getNombre()+"' and usuario2='"+otro+"'");
                    } else if(i==3) {
                        conexion.executeUpdate("UPDATE Amigo SET eresNuevoAmigo=false where usuario2='" + tu.getNombre() + "' and usuario1='" + otro + "'");
                    }
                        notificaciónController.actualizarNotificaciones();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
        }
    }
}

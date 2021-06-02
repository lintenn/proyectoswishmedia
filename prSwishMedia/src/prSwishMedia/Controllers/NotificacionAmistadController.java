package prSwishMedia.Controllers;

import prSwishMedia.Usuario;
import prSwishMedia.Views.NotificacionAmistad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

public class NotificacionAmistadController implements ActionListener {

    private Usuario tu;
    private String otro;
    private Statement conexion;

    public NotificacionAmistadController(Usuario user1, String user2, Statement st){
        tu=user1;
        otro=user2;
        conexion=st;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String act = e.getActionCommand();
        switch (act){
            case ("ACEPTAR"):
                try {
                    conexion.executeUpdate("UPDATE Amigo SET isAmigo=true where usuario1='"+tu.getNombre()+"' and usuario2='"+otro+"'");
                    conexion.executeUpdate("UPDATE Amigo SET solicitud=false where usuario1='"+tu.getNombre()+"' and usuario2='"+otro+"'");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case ("RECHAZAR"):
                try {
                    conexion.executeUpdate("UPDATE Amigo SET isAmigo=false where usuario1='"+tu.getNombre()+"' and usuario2='"+otro+"'");
                    conexion.executeUpdate("UPDATE Amigo SET solicitud=false where usuario1='"+tu.getNombre()+"' and usuario2='"+otro+"'");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
        }
    }
}

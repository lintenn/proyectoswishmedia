package prSwishMedia.Controllers;

import prSwishMedia.Usuario;
import prSwishMedia.Views.NotificacionAmistad;
import prSwishMedia.Views.Notificación;
import prSwishMedia.Views.NotificaciónMensaje;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NotificaciónController implements ActionListener {

    private Usuario tu;
    private Statement conexion;
    private JPanel listaNotificaciones;
    private Notificación notificación;

    public NotificaciónController(Notificación n, Usuario user1, Statement st){
        tu=user1;
        conexion=st;
        listaNotificaciones = new JPanel();
        notificación=n;
        actualizarNotificaciones();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void actualizarNotificaciones(){
        listaNotificaciones.removeAll();
        try {
            ResultSet rs = conexion.executeQuery("SELECT COUNT(*) FROM Amigo where usuario1 = '"+tu.getNombre()+"' and solicitud=true;");
            rs.next();
            int cont = rs.getInt(1);
            ResultSet rs2 = conexion.executeQuery("SELECT COUNT(*) FROM Amigo where usuario1 = '"+tu.getNombre()+"' and mensaje=true;");
            rs2.next();
            cont+=rs2.getInt(1);
            if(cont<=4){
                listaNotificaciones.setLayout(new GridLayout(5,0,0,0));
            } else {
                listaNotificaciones.setLayout(new GridLayout(cont,0,0,0));
            }
            ResultSet rsamigo = conexion.executeQuery("SELECT * FROM Amigo where usuario1 = '"+tu.getNombre()+"' and solicitud = true");
            while(rsamigo.next()){
                NotificacionAmistad na = new NotificacionAmistad();
                NotificacionAmistadController nac = new NotificacionAmistadController(tu, rsamigo.getString("usuario2"), conexion);
                na.controlador(nac);
                na.setMensaje(rsamigo.getString("usuario2"));
                listaNotificaciones.add(na.getPanel1());
            }
            ResultSet rsmensaje = conexion.executeQuery("SELECT * FROM Amigo where usuario1 = '"+tu.getNombre()+"' and mensaje = true");
            while(rsmensaje.next()){
                NotificaciónMensaje na = new NotificaciónMensaje();
                NotificaciónMensajeController nac = new NotificaciónMensajeController(tu, rsmensaje.getString("usuario2"), conexion);
                na.controlador(nac);
                na.setMensaje(rsmensaje.getString("usuario2"));
                listaNotificaciones.add(na.getPanel1());
            }
            notificación.setPanelNotificaciones(listaNotificaciones);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

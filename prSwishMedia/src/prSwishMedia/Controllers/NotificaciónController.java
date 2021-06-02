package prSwishMedia.Controllers;

import prSwishMedia.Usuario;
import prSwishMedia.Views.NotificacionAmistad;
import prSwishMedia.Views.Notificación;
import prSwishMedia.Views.NotificaciónMensaje;
import prSwishMedia.Views.ProfileView;

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
    private ProfileView profileView;

    public NotificaciónController(Notificación n, Usuario user1, Statement st, ProfileView pw){
        tu=user1;
        conexion=st;
        listaNotificaciones = new JPanel();
        notificación=n;
        profileView=pw;
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
                NotificacionAmistadController nac = new NotificacionAmistadController(tu, rsamigo.getString("usuario2"), conexion, this, profileView);
                na.controlador(nac);
                na.setMensaje(rsamigo.getString("usuario2"));
                listaNotificaciones.add(na.getPanel1());
            }
            ResultSet rsmensaje = conexion.executeQuery("SELECT * FROM Amigo where usuario1 = '"+tu.getNombre()+"' and mensaje = true");
            while(rsmensaje.next()){
                NotificaciónMensaje na = new NotificaciónMensaje();
                NotificaciónMensajeController nac = new NotificaciónMensajeController(tu, rsmensaje.getString("usuario2"), conexion, this, 1);
                na.controlador(nac);
                na.setMensaje(rsmensaje.getString("usuario2")+" te ha enviado un mensaje");
                listaNotificaciones.add(na.getPanel1());
            }
            ResultSet rsisAmigoNuevo = conexion.executeQuery("SELECT * FROM Amigo where usuario1 = '"+tu.getNombre()+"' and isNuevoAmigo = true");
            while(rsisAmigoNuevo.next()){
                NotificaciónMensaje na = new NotificaciónMensaje();
                NotificaciónMensajeController nac = new NotificaciónMensajeController(tu, rsisAmigoNuevo.getString("usuario2"), conexion, this, 2);
                na.controlador(nac);
                na.setMensaje(rsisAmigoNuevo.getString("usuario2")+" es tu nuevo amigo");
                listaNotificaciones.add(na.getPanel1());
            }
            ResultSet rseresAmigoNuevo = conexion.executeQuery("SELECT * FROM Amigo where usuario2 = '"+tu.getNombre()+"' and eresNuevoAmigo = true");
            while(rseresAmigoNuevo.next()){
                NotificaciónMensaje na = new NotificaciónMensaje();
                NotificaciónMensajeController nac = new NotificaciónMensajeController(tu, rseresAmigoNuevo.getString("usuario1"), conexion, this, 3);
                na.controlador(nac);
                na.setMensaje("Ahora eres amigo de "+rseresAmigoNuevo.getString("usuario1"));
                listaNotificaciones.add(na.getPanel1());
            }
            notificación.setPanelNotificaciones(listaNotificaciones);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

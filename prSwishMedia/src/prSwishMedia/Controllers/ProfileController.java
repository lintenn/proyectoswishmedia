package prSwishMedia.Controllers;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import prSwishMedia.Lista;
import prSwishMedia.Main;
import prSwishMedia.Usuario;
import prSwishMedia.Views.LoginView;
import prSwishMedia.Views.PrincipalView;
import prSwishMedia.Views.ProfileView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.List;

public class ProfileController implements ActionListener, ChangeListener {

    private ProfileView pview;
    private PrincipalView ppview;
    private LoginView lview;
    private Usuario user;
    private Statement conexion;

    public ProfileController(ProfileView vp, PrincipalView ppv, LoginView lv, Statement st){
        lview=lv;
        pview=vp;
        ppview=ppv;
        user=Main.getUser();
        conexion=st;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String act=e.getActionCommand();
        switch (act){
            case "CREAR":
                List<Lista> listasSeries = user.getListasPersonales();
                String nombreLista = pview.getNombreListaCreada();
                java.util.Date d = new java.util.Date ();
                java.sql.Date fecha = new java.sql.Date(d.getTime());
                int id;
                if(!nombreExistente(listasSeries,nombreLista)){
                    try {
                        id=generateID();
                        conexion.executeUpdate("INSERT INTO Lista (ID,nombre,fechaCreacion,Nombreusuario) VALUES ("+id+",'"+nombreLista +"','"+ fecha +"','" +user.getNombre()+"');" );
                        Lista nuevaLista=new Lista(id, nombreLista, new Date());
                        listasSeries.add(nuevaLista);

                        user.setListasPersonales(listasSeries);
                        pview.setUser(user);
                        pview.añadirComboBox(nuevaLista);
                    } catch (MySQLIntegrityConstraintViolationException error){
                        pview.setMsgCrearLista("ERROR: Nombre usado");
                        error.printStackTrace();
                    } catch(SQLException throwables) {
                        pview.setMsgCrearLista("ERROR: Al añadir a la base de datos");
                        throwables.printStackTrace();
                    }
                }else {
                    pview.setMsgCrearLista("Nombre existente");
                }



                break;

            case "ELIMINAR":

                List<Lista> listasSeries1 = user.getListasPersonales();
                Lista listaEliminada = pview.getListaEliminada();
                boolean esta=false;
                if(!listaEliminada.getNombre().equals("Pendientes") && !listaEliminada.getNombre().equals("Vistas"))
                     esta=listasSeries1.remove(listaEliminada);

                if(esta) pview.setMsgEliminarLista("Lista eliminada con éxito");
                    else pview.setMsgEliminarLista("Error al eliminar lista");

                try {
                    conexion.executeUpdate("DELETE FROM Lista WHERE nombre= '"+listaEliminada.getNombre()+"';" );
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                user.setListasPersonales(listasSeries1);

                pview.setUser(user);
                pview.eliminarComboBox(listaEliminada);
                break;
            case "VOLVER":
                Main.frame.setContentPane(ppview.getPanel());
                Main.frame.setVisible(true);
                pview.setMsgEliminarLista("");
                break;
            case "LOGOUT":
                lview.clrPass();
                Main.frame.setContentPane(lview.getPanel());
                Main.frame.setVisible(true);
                pview.setMsgEliminarLista("");
                pview.setMsgCrearLista("");
                break;
        }
    }

    private boolean nombreExistente(List<Lista> listasSeries, String nombreLista) {
        boolean encontrado=false;
        Iterator it = listasSeries.iterator();
        Lista actual;
        while(it.hasNext()){
            actual= (Lista) it.next();
            if(actual.getNombre().equals(nombreLista)){
                encontrado=true;
            }
        }

        return encontrado;
    }

    private int generateID () throws SQLException {
        ResultSet res = conexion.executeQuery("SELECT MAX(id) FROM Lista;");

        int id=0;
        try{
        res.next();
        id=res.getInt("MAX(id)")+1;
        }catch (SQLException e) {
            pview.setMsgEliminarLista("ERROR Base en la base de datos al generar id");
        }

        return id;
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

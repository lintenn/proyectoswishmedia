package prSwishMedia.Controllers;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
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
                ResultSet idResult=null;
                int id=0;
                try {
                    idResult=conexion.executeQuery("SELECT * FROM (SELECT * FROM Lista ORDER BY ID DESC) WHERE rownum = 1;");
                    idResult.next();
                    id = idResult.getInt("id");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }



                Lista nuevaLista=new Lista(id, nombreLista, new Date());
                listasSeries.add(nuevaLista);

                user.setListasPersonales(listasSeries);

                pview.setMsgEliminarLista("Lista creada con éxito");

                pview.setUser(user);
                pview.añadirComboBox(nuevaLista);
                break;

            case "ELIMINAR":

                List<Lista> listasSeries1 = user.getListasPersonales();
                Lista listaEliminada = pview.getListaEliminada();
                boolean esta=listasSeries1.remove(listaEliminada);

                if(esta) pview.setMsgEliminarLista("Lista eliminada con éxito");
                    else pview.setMsgEliminarLista("Error al eliminar lista");

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
                break;
        }
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

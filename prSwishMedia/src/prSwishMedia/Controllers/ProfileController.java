package prSwishMedia.Controllers;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import prSwishMedia.Lista;
import prSwishMedia.Main;
import prSwishMedia.Usuario;
import prSwishMedia.Views.LoginView;
import prSwishMedia.Views.PrincipalView;
import prSwishMedia.Views.ProfileView;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

public class ProfileController implements ActionListener {

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
                        if(!nombreLista.equals("")){
                            conexion.executeUpdate("INSERT INTO Lista (ID,nombre,fechaCreacion,Nombreusuario) VALUES ("+id+",'"+nombreLista +"','"+ fecha +"','" +user.getNombre()+"');" );
                            Lista nuevaLista=new Lista(id, nombreLista, d);
                            listasSeries.add(nuevaLista);
                            pview.setMsgModificarLista("Lista creada con éxito");
                            user.setListasPersonales(listasSeries);
                            pview.añadirComboBox(nuevaLista);
                        } else{
                            pview.setMsgModificarLista("ERROR: Tiene que introducir un nombre");
                        }
                    } catch (MySQLIntegrityConstraintViolationException error){
                        pview.setMsgModificarLista("ERROR: Nombre usado");
                        error.printStackTrace();
                    } catch(SQLException throwables) {
                        pview.setMsgModificarLista("ERROR: Al añadir a la base de datos");
                        throwables.printStackTrace();
                    }
                }else {
                    pview.setMsgModificarLista("Nombre existente");
                }

                break;

            case "ELIMINAR":

                List<Lista> listasSeries1 = user.getListasPersonales();
                Lista listaEliminada = pview.getListaEliminada();
                boolean esta=false;
                if(!listaEliminada.getNombre().equals("Pendientes") && !listaEliminada.getNombre().equals("Vistas"))
                     esta=listasSeries1.remove(listaEliminada);

                if(esta) pview.setMsgModificarLista("Lista eliminada con éxito");
                    else pview.setMsgModificarLista("Error al eliminar lista");

                try {
                    if(esta)
                        conexion.executeUpdate("DELETE FROM Lista WHERE nombre= '"+listaEliminada.getNombre()+"';" );
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                user.setListasPersonales(listasSeries1);
                if(esta){
                    pview.eliminarComboBox(listaEliminada);
                    pview.setMsgModificarLista("Lista eliminada con éxito");
                }
                break;
            case "VOLVER":
                ppview.setUser(user);
                Main.frame.setContentPane(ppview.getPanel());
                Main.frame.setVisible(true);
                pview.setMsgModificarLista("");
                break;
            case "LOGOUT":
                lview.clrPass();
                Main.frame.setContentPane(lview.getPanel());
                Main.frame.setVisible(true);
                pview.setMsgModificarLista("");

                break;
            case "PRIVACIDAD":
                try {
                    ResultSet rs = conexion.executeQuery("SELECT privacidad FROM Usuario where nombre = '"+user.getNombre()+"';");
                    rs.next();
                    if(rs.getInt(1)==0){
                        conexion.executeUpdate("UPDATE Usuario SET privacidad=1 where nombre = '"+user.getNombre()+"';");
                    } else{
                        conexion.executeUpdate("UPDATE Usuario SET privacidad=0 where nombre = '"+user.getNombre()+"';");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case "FECHA":
                int dia=pview.getDiaN();
                int mes=pview.getMesN();
                int anyo= pview.getAnyoN();
                if(dia==29&&mes==2&&(anyo%4!=0)){
                    pview.setMsgModificarFN("ERROR: No puede ser 29 de febrero en un año no bisiesto");
                } else if(dia>29&&mes==2){
                    pview.setMsgModificarFN("ERROR: No puede haber un día mayor a 29 en febrero");
                } else if(dia==31&&(mes==4||mes==6||mes==9||mes==11)){
                    pview.setMsgModificarFN("ERROR: No puede haber 31 días en abril, junio, septiembre o noviembre");
                } else{
                    java.util.Date date2 = new Date(anyo,mes,dia);
                    java.sql.Date date = new Date(date2.getTime());
                    pview.setMsgModificarFN("Día válido");
                    try {
                        conexion.executeUpdate("UPDATE Usuario SET fechaNacimiento= '"+anyo+"-"+mes+"-"+dia+"' where nombre='"+user.getNombre()+"';");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
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
            pview.setMsgModificarLista("ERROR Base en la base de datos al generar id");
        }

        return id;
    }



}

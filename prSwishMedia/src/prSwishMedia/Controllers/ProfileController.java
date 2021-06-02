package prSwishMedia.Controllers;

import com.kitfox.svg.A;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import prSwishMedia.Lista;
import prSwishMedia.Main;
import prSwishMedia.Usuario;
import prSwishMedia.Views.*;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

public class ProfileController extends JFrame implements ActionListener, KeyListener {

    private ProfileView pview;
    private PrincipalView ppview;
    private LoginView lview;
    private Usuario user;
    private PrincipalController pcc;
    private Statement conexion;
    private Statement conexion1;

    public ProfileController(PrincipalController principalController, ProfileView vp, PrincipalView ppv, LoginView lv, Statement st,Statement st1, Usuario u){
        lview=lv;
        pview=vp;
        ppview=ppv;
        user=u;
        conexion=st;
        conexion1=st1;
        pcc=principalController;
        setInfo();
    }
    public void setInfo(){
        pview.setDescripcion(user.getDescripcion());
        pview.setNombreUsuario(user.getNombre());
        pview.setNumAmigos(user.getNumAmigos());
        pview.setNumCapitulos(user.getNumEpisodiosVistos());
        pview.setNumSeriesVistas(user.getNumSeriesVistas());
        pview.setNumPeliculas(user.getNumPeliculasVistas());
        if(user.getFechaCreacion()!=null)pview.setFechaCreacion(user.getFechaCreacion().toString());
        actualizarComboBox();
        fechaPrivacidad();
    }

    private void fechaPrivacidad() {


        pview.setCheckBoxPrivacidad(user.getPrivacidad());

        actualizarComboBoxFechaN(user.getFechaNacimiento().toString());

    }


    public void actualizarComboBoxFechaN(String date) {
        for(int i=1; i<=31; i++){
            pview.añadirComboBoxDia(i);
        }
        for(int i=1900; i<=2021; i++){
            pview.añadirComboBoxAnyo(i);
        }
        pview.añadirComboBoxMes("Enero");
        pview.añadirComboBoxMes("Febrero");
        pview.añadirComboBoxMes("Marzo");
        pview.añadirComboBoxMes("Abril");
        pview.añadirComboBoxMes("Mayo");
        pview.añadirComboBoxMes("Junio");
        pview.añadirComboBoxMes("Julio");
        pview.añadirComboBoxMes("Agosto");
        pview.añadirComboBoxMes("Septiembre");
        pview.añadirComboBoxMes("Octubre");
        pview.añadirComboBoxMes("Noviembre");
        pview.añadirComboBoxMes("Diciembre");

        String[] parts = date.split("-");

        pview.setSetSelectedIndexComboBoxAnyo(Integer.parseInt(parts[0])-1900);
        pview.setSetSelectedIndexComboBoxMes(Integer.parseInt(parts[1])-1);
        pview.setSetSelectedIndexComboBoxDia(Integer.parseInt(parts[2])-1);

        if(Integer.parseInt(parts[1])==2 && Integer.parseInt(parts[0])%4!=0){
            updateDay(28);
        } else if(Integer.parseInt(parts[1])==2){
            updateDay(28);
        } else if(Integer.parseInt(parts[1])==4 || Integer.parseInt(parts[1])==6 || Integer.parseInt(parts[1])==9 || Integer.parseInt(parts[1])==11){
            updateDay(30);
        }
    }

    public void updateDay(int n) {
        int x = pview.getSelectedIndexComboBoxDia();
        try {
            pview.eliminarComboBoxTodoDia();
        }catch (NullPointerException e){

        }
        for(int i=0;i<n;i++){
            pview.añadirComboBoxDia(i+1);
        }
        if(x<=n){
            pview.setSetSelectedIndexComboBoxDia(x);
        }
    }

    public void actualizarComboBox() {
        if(user.getListasPersonales()!=null){
            for(Lista l: user.getListasPersonales()){

                pview.añadirComboBox(l);
            }
        }else {
            System.out.println("LISTA VACIA");
        }
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
                            Lista nuevaLista=new Lista(id, nombreLista, d,conexion);
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
                pcc.setLista();
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
                    int value=rs.getInt(1);

                    if(value==0){
                        conexion.executeUpdate("UPDATE Usuario SET privacidad=1 where nombre = '"+user.getNombre()+"';");
                        user.setPrivacidad(true);
                    } else{
                        conexion.executeUpdate("UPDATE Usuario SET privacidad=0 where nombre = '"+user.getNombre()+"';");
                        user.setPrivacidad(false);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case "FECHAMES":
                int mes=pview.getMesN();
                int anyo= pview.getAnyoN();
                if(mes==2&&(anyo%4!=0)){
                   updateDay(28);
                } else if(mes==2){
                    updateDay(29);
                } else if(mes==4||mes==6||mes==9||mes==11){
                    updateDay(30);
                } else{
                    updateDay(31);
                }
                break;
            case "FECHA":
                 int dia=pview.getDiaN();
                 mes=pview.getMesN();
                 anyo= pview.getAnyoN();
                 try {
                     conexion.executeUpdate("UPDATE Usuario SET fechaNacimiento= '"+anyo+"-"+mes+"-"+dia+"' where nombre='"+user.getNombre()+"';");
                 } catch (SQLException throwables) {
                     throwables.printStackTrace();
                 }

                break;
            case "NOTIFICACION":
                Notificación notificación = new Notificación(this, true);
                NotificaciónController nc = new NotificaciónController(notificación, user,conexion, pview);
                notificación.controlador(nc);
                notificación.setVisible(true);
                break;
            case "AMIGOS":
                ListaAmigosView amigosView = new ListaAmigosView();
                AmigosController ac = new AmigosController(user,conexion1,conexion,amigosView,pview);
                ListaAmigosView amigosView = new ListaAmigosView(pview, true);
                AmigosController ac = new AmigosController(user,conexion1,conexion,amigosView, pview);
                amigosView.controlador(ac);
                amigosView.setVisible(true);
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


    @Override
    public void keyTyped(KeyEvent e) {
        if(pview.getDescripcion().length()==60){
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==10){
            e.consume();
            try {
                conexion.executeUpdate("UPDATE Usuario SET descripcion = '"+pview.getDescripcion()+"' where nombre = '"+user.getNombre()+"';");
                user.setDescripcion(pview.getDescripcion());

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

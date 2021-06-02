package prSwishMedia.Controllers;

import prSwishMedia.Main;
import prSwishMedia.Usuario;
import prSwishMedia.Views.*;

import javax.swing.plaf.nimbus.State;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioPreViewController implements ActionListener {

    private UsuarioPreView userPv;
    private Usuario user;
    private Statement conexion;
    private Usuario tu;
    private AmigosController amigosController;
    private ProfileView profileView;

    public UsuarioPreViewController(UsuarioPreView userpv, Usuario usuario, Statement st, Usuario u2, AmigosController ac, ProfileView pw){
        userPv=userpv;
        user=usuario;
        conexion=st;
        tu=u2;
        userPv.setNombre(user.getNombre());
        userPv.setDescripcion(user.getDescripcion());
        amigosController=ac;
        profileView = pw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String act = e.getActionCommand();
        switch (act) {
            case "AÑADIRAMIGO":
                try {
                    ResultSet rs2 = conexion.executeQuery("SELECT COUNT(*) FROM Amigo where usuario1 = '"+user.getNombre()+"' and usuario2 = '"+tu.getNombre()+"';");
                    rs2.next();
                    int cont = rs2.getInt(1);
                    if(cont==0){
                        ResultSet rs = conexion.executeQuery("SELECT * FROM Usuario where nombre='" + user.getNombre() + "';");
                        rs.next();
                        if (rs.getBoolean("privacidad")) {
                            conexion.executeUpdate("INSERT INTO Amigo (id, usuario1, usuario2, solicitud) values(" + generateID() + ",'" + user.getNombre() + "','" + tu.getNombre() + "', true)");
                        } else {
                            conexion.executeUpdate("INSERT INTO Amigo (id, usuario1, usuario2, isAmigo) values(" + generateID() + ",'" + user.getNombre() + "','" + tu.getNombre() + "', true)");
                            conexion.executeUpdate("UPDATE Amigo SET isNuevoAmigo=true where usuario1='"+user.getNombre()+"' and usuario2='"+tu.getNombre()+"'");
                            conexion.executeUpdate("UPDATE Amigo SET eresNuevoAmigo=true where usuario1='"+user.getNombre()+"' and usuario2='"+tu.getNombre()+"'");
                            ResultSet rs3 = conexion.executeQuery("SELECT * FROM Usuario where nombre = '"+user.getNombre()+"';");
                            rs3.next();
                            conexion.executeUpdate("UPDATE Usuario SET numAmigos="+(rs3.getInt("numAmigos")+1)+" where nombre = '"+user.getNombre()+"';");
                        }
                    } else {
                        ResultSet rs = conexion.executeQuery("SELECT * FROM Amigo where usuario1 = '"+user.getNombre()+"' and usuario2 = '"+tu.getNombre()+"';");
                        rs.next();
                        if(!rs.getBoolean("isAmigo") && !rs.getBoolean("solicitud")){
                            conexion.executeUpdate("UPDATE Amigo SET solicitud=true where usuario1 = '"+user.getNombre()+"' and usuario2 = '"+tu.getNombre()+"';");
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case "ELIMINARAMIGO":
                try {
                    int rs = conexion.executeUpdate("DELETE FROM Amigo where (usuario1 = '"+user.getNombre()+"' and usuario2 = '"+tu.getNombre()+
                            "') OR (usuario2 = '"+user.getNombre()+"' and usuario1 = '"+tu.getNombre()+"');");

                    amigosController.eliminarUnUsuario(user.getNombre(),tu.getNombre());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case "CHATEAR":
                ChatView cw = new ChatView();
                ChatController cc = new ChatController(tu, user, conexion, cw, null, profileView);
                cw.controlador(cc);
                Main.frame.setContentPane(cw.getPanel1());
                Main.frame.setVisible(true);
                break;
        }
    }

    private int generateID(){
        ResultSet res = null;
        try {
            res = conexion.executeQuery("SELECT MAX(id) FROM Amigo;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        int id=0;
        try{
            res.next();
            id=res.getInt("MAX(id)")+1;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}


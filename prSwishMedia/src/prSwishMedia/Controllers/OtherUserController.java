package prSwishMedia.Controllers;

import prSwishMedia.Lista;
import prSwishMedia.Main;
import prSwishMedia.Usuario;
import prSwishMedia.Views.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OtherUserController implements ActionListener {
    private OtherUserView uview;
    private PrincipalView ppview;
    private Usuario user;
    private PrincipalController pcc;
    private Statement conexion,conexion1;
    private Usuario tu;

    public OtherUserController(PrincipalController principalController, OtherUserView uv, PrincipalView ppv, Statement st,Statement st1, Usuario u, Usuario u2) {
        uview = uv;
        ppview = ppv;
        user = u;
        conexion = st;
        conexion1=st1;
        pcc = principalController;
        tu=u2;
        setInfo();
    }

    public void setInfo(){
        uview.setDescripcion(user.getDescripcion());
        uview.setNombreUsuario(user.getNombre());
        uview.setNumAmigos(user.getNumAmigos());
        uview.setNumCapitulos(user.getNumEpisodiosVistos());
        uview.setNumSeriesVistas(user.getNumSeriesVistas());
        uview.setNumPeliculas(user.getNumPeliculasVistas());

        if (user.getFechaCreacion() != null) {
            uview.setFechaCreacion(user.getFechaCreacion().toString());
        }

        uview.setFechaNacimiento(user.getFechaNacimiento().toString());
        actualizarComboBox();
    }

    public void actualizarComboBox() {
        if (user.getListasPersonales() != null) {
            for (Lista l : user.getListasPersonales()) {
                uview.añadirComboBox(l);
            }
        } else {
            System.out.println("LISTA VACIA");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String act = e.getActionCommand();
        switch (act) {
            case "VOLVER":
                pcc.setLista();
                Main.frame.setContentPane(ppview.getPanel());
                Main.frame.setVisible(true);
                break;

            case "VERLISTA":
                VerListaView verListaView = new VerListaView(this);
                verListaView.setMinimumSize(new Dimension(400,600));
                VerListaController verListaController = new VerListaController(verListaView,conexion,conexion1,uview.getListaSelecionada());
                verListaView.controlador(verListaController);
                verListaView.setVisible(true);
                break;

            case "AÑADIRAMIGO":

                try {
                    ResultSet rs2 = conexion.executeQuery("SELECT COUNT(*) FROM Amigo where usuario1 = '"+user.getNombre()+"' and usuario2 = '"+tu.getNombre()+"';");
                    rs2.next();
                    int cont = rs2.getInt(1);
                    if(cont==0){
                        ResultSet rs = conexion.executeQuery("SELECT * FROM Usuario where nombre='"+user.getNombre()+"';");
                        rs.next();
                        if(rs.getBoolean("privacidad")){
                            conexion.executeUpdate("INSERT INTO Amigo (id, usuario1, usuario2, solicitud) values("+generateID()+",'"+user.getNombre()+"','"+tu.getNombre()+"', true)");
                        } else {
                            conexion.executeUpdate("INSERT INTO Amigo (id, usuario1, usuario2, isAmigo) values("+generateID()+",'"+user.getNombre()+"','"+tu.getNombre()+"', true)");
                            conexion.executeUpdate("UPDATE Amigo SET isNuevoAmigo=true where usuario1='"+user.getNombre()+"' and usuario2='"+tu.getNombre()+"'");
                            conexion.executeUpdate("UPDATE Amigo SET eresNuevoAmigo=true where usuario1='"+user.getNombre()+"' and usuario2='"+tu.getNombre()+"'");
                            ResultSet rs3 = conexion.executeQuery("SELECT * FROM Usuario where nombre = '"+user.getNombre()+"';");
                            rs3.next();
                            conexion.executeUpdate("UPDATE Usuario SET numAmigos="+(tu.getNumAmigos()+1)+" where nombre = '"+tu.getNombre()+"';");
                            ResultSet rs4 = conexion.executeQuery("SELECT * FROM Usuario where nombre = '"+user.getNombre()+"';");
                            rs4.next();
                            user.setNumAmigos(rs4.getInt("numAmigos"));
                            uview.setNumAmigos(user.getNumAmigos());
                        }
                    } else {
                        ResultSet rs = conexion.executeQuery("SELECT * FROM Amigo where usuario1 = '"+user.getNombre()+"' and usuario2 = '"+tu.getNombre()+"';");
                        rs.next();
                        if(!rs.getBoolean("isAmigo") && !rs.getBoolean("solicitud")){
                            conexion.executeUpdate("UPDATE Amigo SET solictud=true where '"+user.getNombre()+"' and usuario2 = '"+tu.getNombre()+"';");
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case "CHAT":
                ChatView cw = new ChatView();
                ChatController cc = new ChatController(tu, user, conexion, cw, uview, null);
                cw.controlador(cc);
                Main.frame.setContentPane(cw.getPanel1());
                Main.frame.setVisible(true);
                break;
        }
    }

    private int generateID () throws SQLException {
        ResultSet res = conexion.executeQuery("SELECT MAX(id) FROM Amigo;");
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
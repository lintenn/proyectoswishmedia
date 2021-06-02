package prSwishMedia.Controllers;

import prSwishMedia.Lista;
import prSwishMedia.Main;
import prSwishMedia.Usuario;
import prSwishMedia.Views.OtherUserView;
import prSwishMedia.Views.PrincipalView;

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
    private Statement conexion;
    private Usuario tu;

    public OtherUserController(PrincipalController principalController, OtherUserView uv, PrincipalView ppv, Statement st, Usuario u, Usuario u2) {
        uview = uv;
        ppview = ppv;
        user = u;
        conexion = st;
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
                break;

            case "AÑADIRAMIGO":
                try {
                    ResultSet rs = conexion.executeQuery("SELECT * FROM Usuario where nombre='"+user.getNombre()+"';");
                    rs.next();
                    if(rs.getBoolean("privacidad")){
                        
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
        }
    }
}
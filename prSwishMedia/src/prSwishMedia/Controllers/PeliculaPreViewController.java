package prSwishMedia.Controllers;

import prSwishMedia.Lista;
import prSwishMedia.Main;
import prSwishMedia.Pelicula;
import prSwishMedia.Usuario;
import prSwishMedia.Views.PeliculaPreView;
import prSwishMedia.Views.PrincipalView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PeliculaPreViewController extends ContenidoMultimediaPreViewController implements ActionListener {

    private PrincipalView ppview;
    private PeliculaPreView peliPv;
    private Pelicula pelicula;
    private Usuario user;
    private JComboBox listas;
    private Statement conexion;

    public PeliculaPreViewController(PrincipalView principalView,PeliculaPreView ppv, Pelicula p,Usuario u,Statement stmt, JComboBox l){
        peliPv=ppv;
        pelicula=p;
        ppview=principalView;
        listas=l;
        user=u;
        conexion=stmt;
        setInfo();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String act=e.getActionCommand();

        switch (act){
            case "AÑADIR":
                List<Lista> listasUsuariouser=user.getListasPersonales();
                Lista listaSeleccionada=peliPv.getSelectedComboBox();
                if(listasUsuariouser.contains(listaSeleccionada) && !listaSeleccionada.esta(pelicula.getId())){
                    try {

                        if(listaSeleccionada.getNombre().equals("Vistas")) {
                            user.setNumPeliculasVistas(user.getNumPeliculasVistas() + 1);

                            ResultSet r = conexion.executeQuery("SELECT numPeliculasVistas from Usuario where nombre='" + user.getNombre() + "';");
                            r.next();
                            int n = r.getInt(1);
                            conexion.executeUpdate("UPDATE Usuario SET numPeliculasVistas=" + (n + 1) + " where nombre='" + user.getNombre() + "';");
                        }

                        conexion.executeUpdate("INSERT INTO AñadirContenido (idContenidoMultimedia,idLista) VALUES("+pelicula.getId()+","+listaSeleccionada.getId()+");");
                        ResultSet rs = conexion.executeQuery("SELECT * from ContenidoMultimedia where idContenidoMultimedia="+pelicula.getId()+"");
                        rs.next();
                        conexion.executeUpdate("UPDATE ContenidoMultimedia SET veces_añadidas="+(rs.getInt("veces_añadidas")+1)+" where idContenidoMultimedia="+pelicula.getId()+"");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                break;
        }
    }

    public void actualizarComboBox(JComboBox l){
        peliPv.setComboBox(l);
    }

    public void setInfo(){
        peliPv.setNombre(pelicula.getNombre());
        peliPv.setImagen(pelicula.getId());
        peliPv.setComboBox(listas);
        peliPv.setSinopsis(pelicula.getSinopsis());
        peliPv.setGenero(pelicula.getGenero());
        peliPv.setValoracion(pelicula.getRating());
    }
}

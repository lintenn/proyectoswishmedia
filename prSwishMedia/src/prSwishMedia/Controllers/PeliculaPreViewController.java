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
                if(listasUsuariouser.contains(listaSeleccionada)){
                    try {
                        conexion.executeUpdate("INSERT INTO AñadirContenido (idContenidoMultimedia,idLista) VALUES("+pelicula.getId()+","+listaSeleccionada.getId()+");");
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

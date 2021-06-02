package prSwishMedia.Controllers;

import prSwishMedia.ContenidoMultimedia;
import prSwishMedia.Lista;
import prSwishMedia.Serie;
import prSwishMedia.Usuario;
import prSwishMedia.Views.SeriePreView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SeriePreviewController extends ContenidoMultimediaPreViewController implements ActionListener {

    private SeriePreView pvSerie;
    private Serie contenido;
    private Usuario user;
    private Statement conexion;

    public SeriePreviewController(Usuario u,Serie s, SeriePreView sv, JComboBox comboBox,Statement st){
        pvSerie=sv;
        contenido=s;
        user=u;
        conexion=st;
        pvSerie.setGenero(contenido.getGenero());
        pvSerie.setNombre(contenido.getNombre());
        pvSerie.setSinopsis(contenido.getSinopsis());
        pvSerie.setImagen(contenido.getId());
        pvSerie.setComboBox(comboBox);
        pvSerie.setValoracion(contenido.getValoracion());
        pvSerie.setNumTemporadas(contenido.getNumTemporadas());


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String act = e.getActionCommand();
        switch (act){
            case "VALORAR":
                double valoracionPrevia=contenido.getValoracion();
                int vecesValorada=contenido.getVeces_anyadidas();
                double valoracion= (double) pvSerie.getValoracion();
                double valoracionNueva = ((valoracionPrevia*vecesValorada)+valoracion)/(vecesValorada+1);
                contenido.setVeces_anyadidas(vecesValorada+1);
                contenido.setRating((int) valoracionNueva);
                pvSerie.setMsgInfo("Valorada con éxito");
                break;
            case "AÑADIR":
                List<Lista> listasUsuariouser=user.getListasPersonales();
                Lista listaSeleccionada=pvSerie.getSelectedComboBox();
                if(listasUsuariouser.contains(listaSeleccionada) && !listaSeleccionada.esta(contenido.getId())){

                    if(listaSeleccionada.getNombre().equals("Vistas")){
                        user.setNumSeriesVistas(user.getNumPeliculasVistas()+1);
                        user.setNumEpisodiosVistos(user.getNumEpisodiosVistos()+contenido.getNumCapitulos());
                    }

                    try {
                        conexion.executeUpdate("INSERT INTO AñadirContenido (idContenidoMultimedia,idLista) VALUES("+contenido.getId()+","+listaSeleccionada.getId()+");");
                        if(listaSeleccionada.getNombre().equals("Vistas")) {
                            user.setNumSeriesVistas(user.getNumSeriesVistas() + 1);
                            user.setNumEpisodiosVistos(user.getNumEpisodiosVistos()+contenido.getNumCapitulos());
                        }
                        ResultSet rs = conexion.executeQuery("SELECT * from ContenidoMultimedia where idContenidoMultimedia="+contenido.getId()+"");
                        rs.next();
                        conexion.executeUpdate("UPDATE ContenidoMultimedia SET veces_añadidas="+(rs.getInt("veces_añadidas")+1)+" where idContenidoMultimedia="+contenido.getId()+"");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                break;
        }
    }
    public void actualizarComboBox(JComboBox l){
        pvSerie.setComboBox(l);
    }
}

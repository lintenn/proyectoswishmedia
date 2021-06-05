package prSwishMedia.Controllers;

import prSwishMedia.ContenidoMultimedia;
import prSwishMedia.Lista;
import prSwishMedia.Pelicula;
import prSwishMedia.Serie;
import prSwishMedia.Views.PeliculaPreView;
import prSwishMedia.Views.SeriePreView;
import prSwishMedia.Views.VerListaView;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VerListaController {

    Statement conexion,conexion1;
    VerListaView verListaView;
    Lista listaSeleccionada;

    public VerListaController(VerListaView verListaV, Statement st,Statement st1, Lista lista){
        conexion=st;
        conexion1=st1;
        verListaView=verListaV;
        listaSeleccionada=lista;
        verListaView.setNombre(lista.getNombre());
        actualizarListas();
    }

    private void actualizarListas() {

        verListaView.removeAllContenido();

        ResultSet count3=null;
        try {
            ResultSet count = conexion.executeQuery("SELECT COUNT(*) FROM ContenidoMultimedia join AñadirContenido on AñadirContenido.idContenidoMultimedia=ContenidoMultimedia.idContenidoMultimedia && AñadirContenido.idLista="+listaSeleccionada.getId()+";");
            count.next();
            int cont=count.getInt(1);
            if(cont!=0) {

                verListaView.setLayoutListasContenido(cont);
                // Creo listas para almacenar los idContenidoMultimedia y referencias de pelipv y seriepv
                ArrayList<Integer> listaidspelis = new ArrayList<>();
                ArrayList<Integer> listaidsseries = new ArrayList<>();
                ArrayList<PeliculaPreView> listapelipv = new ArrayList<>();
                ArrayList<SeriePreView> listaseriepv = new ArrayList<>();
                count = conexion1.executeQuery("SELECT * FROM ContenidoMultimedia join AñadirContenido on AñadirContenido.idContenidoMultimedia=ContenidoMultimedia.idContenidoMultimedia && AñadirContenido.idLista=" + listaSeleccionada.getId() + ";");

                while (count.next()) {
                    int id = count.getInt("idContenidoMultimedia");

                    ResultSet count2 = conexion.executeQuery("SELECT COUNT(*) FROM Serie WHERE idContenidoMultimedia=" + id + ";");
                    count2.next();
                    int numSerie = count2.getInt("COUNT(*)");

                    if (numSerie == 1) { // es serie
                        listaidsseries.add(id);

                        count3 = conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia && Serie.idContenidoMultimedia=" + id + ";");
                        count3.next();
                        Serie serie=new Serie(count3.getInt("idContenidoMultimedia"),count3.getString("nombre"), count3.getString("sinopsis"),count3.getString("reparto"),0,count3.getString("fecha_estreno"),count3.getString("Genero"),count3.getString("premios"),0,count3.getString("trailer"),count3.getInt("veces_añadidas"),0,false,count3.getInt("numCapitulos"),count3.getInt("numTemporadas"),count3.getDouble("duracionMedia"));

                        SeriePreView seriepv = new SeriePreView();
                        seriepv.setVisibleAñadir(false);
                        seriepv.setVisibleComboBox(false);
                        SeriePreviewController seriepvC = new SeriePreviewController(null, serie, seriepv, null, conexion);
                        seriepv.controlador(seriepvC);

                        listaseriepv.add(seriepv);

                        verListaView.addListaContenido(seriepv.getPanel());
                    } else { // es pelicula

                        listaidspelis.add(id);
                        count3 = conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Pelicula on ContenidoMultimedia.idContenidoMultimedia=Pelicula.idContenidoMultimedia && Pelicula.idContenidoMultimedia=" + id + ";");
                        count3.next();
                        Pelicula pelicula = new Pelicula(count3.getString("nombre"), count3.getInt("idContenidoMultimedia"), count3.getString("sinopsis"), count3.getString("genero"), 0, count3.getInt("veces_añadidas"));

                        PeliculaPreView pelipv = new PeliculaPreView();
                        pelipv.setVisibleAñadir(false);
                        pelipv.setVisibleComboBox(false);
                        PeliculaPreViewController peliPvController = new PeliculaPreViewController(null, pelipv, pelicula, null, conexion, null);
                        pelipv.controlador(peliPvController);

                        listapelipv.add(pelipv);

                        verListaView.addListaContenido(pelipv.getPanel());
                    }

                }

                for (int i = 0; i < listaidsseries.size(); i++) {
                    //Por cada serie generamos su valoracion media
                    ResultSet valmed = conexion.executeQuery("SELECT IFNULL(AVG(valoracion),0) FROM Valora WHERE idContenido=" + listaidsseries.get(i) + ";");
                    valmed.next(); //apuntamos
                    listaseriepv.get(i).setValoracion(valmed.getDouble(1));
                }

                for (int i = 0; i < listaidspelis.size(); i++) {
                    //Por cada peli generamos su valoracion media
                    ResultSet valmed = conexion.executeQuery("SELECT IFNULL(AVG(valoracion),0) FROM Valora WHERE idContenido=" + listaidspelis.get(i) + ";");
                    valmed.next(); //apuntamos
                    listapelipv.get(i).setValoracion(valmed.getDouble(1));
                }


            }
            verListaView.setViewportViewScrollContenido(verListaView.getListaContenido());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}

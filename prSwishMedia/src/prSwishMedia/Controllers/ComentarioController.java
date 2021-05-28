package prSwishMedia.Controllers;

import prSwishMedia.Views.ComentarioView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ComentarioController implements ActionListener {

    Statement conexion;
    ComentarioView cview;
    PeliculaController pcontroler;
    SerieController scontroler;
    int ID;

    public ComentarioController(Statement st, ComentarioView comentarioView, PeliculaController peliculaController, int id, SerieController serieController){
        conexion=st;
        cview=comentarioView;
        pcontroler=peliculaController;
        scontroler=serieController;
        ID=id;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String act = e.getActionCommand();
        switch (act){
            case "BORRAR":
                try {
                    conexion.executeUpdate("set SQL_SAFE_UPDATES=0;");
                    conexion.executeUpdate("delete from Comentario where Comentario.ID="+ID+";");
                    conexion.executeUpdate("DELETE FROM Comunicación where Comunicación.ID="+ID+";");
                    if(pcontroler!=null){
                        pcontroler.actualizarComentarios();
                    }
                    if(scontroler!=null){
                        scontroler.actualizarComentarios();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                break;
        }
    }
}

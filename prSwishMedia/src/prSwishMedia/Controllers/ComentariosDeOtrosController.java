package prSwishMedia.Controllers;

import prSwishMedia.Usuario;
import prSwishMedia.Views.ComentariosDeOtros;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ComentariosDeOtrosController implements ActionListener {

    Statement conexion;
    ComentariosDeOtros cview;
    PeliculaController pcontroler;
    SerieController scontroler;
    int ID;
    Usuario user;

    public ComentariosDeOtrosController(Statement st, ComentariosDeOtros comentarioView, PeliculaController peliculaController, int id, SerieController serieController, Usuario u){
        conexion=st;
        cview=comentarioView;
        pcontroler=peliculaController;
        scontroler=serieController;
        ID=id;
        user=u;
    }

    public void actionPerformed(ActionEvent e) {
        String act = e.getActionCommand();
        switch (act){
            case "LIKE":
                try {
                    ResultSet rs = conexion.executeQuery("SELECT COUNT(*) FROM islike, Comentario where islike.usuario='"+user.getNombre()+"' and islike.idcomentario="+ID+";");
                    rs.next();
                    if(rs.getInt(1)==0){
                        conexion.executeUpdate("Insert into islike (usuario,idcomentario,islike,dislike) values('"+user.getNombre()+"',"+ID+",0,0)");
                    }
                    ResultSet rs2 = conexion.executeQuery("SELECT * FROM islike, Comentario where islike.usuario='"+user.getNombre()+"' and islike.idcomentario="+ID+" and islike.idcomentario=Comentario.ID;");
                    rs2.next();
                    int numLikes=rs2.getInt("numLikes");
                    if(rs2.getInt("islike")==1){
                        numLikes--;
                        conexion.executeUpdate("UPDATE Comentario SET numLikes="+numLikes+" where ID="+ID+"");
                        conexion.executeUpdate("UPDATE islike SET islike=false where islike.usuario='"+user.getNombre()+"' and islike.idcomentario="+ID+";");
                    } else if(rs2.getInt("dislike")==1){
                        numLikes++;
                        int numDislikes = rs2.getInt("numDislikes");
                        numDislikes--;
                        conexion.executeUpdate("UPDATE Comentario SET numLikes="+numLikes+" where ID="+ID+";");
                        conexion.executeUpdate("UPDATE Comentario SET numDislikes="+numDislikes+" where ID="+ID+";");
                        conexion.executeUpdate("UPDATE islike SET islike=true where islike.usuario='"+user.getNombre()+"' and islike.idcomentario="+ID+";");
                        conexion.executeUpdate("UPDATE islike SET dislike=false where islike.usuario='"+user.getNombre()+"' and islike.idcomentario="+ID+";");
                    } else {
                        numLikes++;
                        conexion.executeUpdate("UPDATE Comentario SET numLikes="+numLikes+" where ID="+ID+";");
                        conexion.executeUpdate("UPDATE islike SET islike=true where islike.usuario='"+user.getNombre()+"' and islike.idcomentario="+ID+";");
                    }
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
            case "DISLIKE":
                try {
                    ResultSet rs = conexion.executeQuery("SELECT COUNT(*) FROM islike, Comentario where islike.usuario='"+user.getNombre()+"' and islike.idcomentario="+ID+";");
                    rs.next();
                    if(rs.getInt(1)==0){
                        conexion.executeUpdate("Insert into islike (usuario,idcomentario,islike,dislike) values('"+user.getNombre()+"',"+ID+",0,0)");
                    }
                    ResultSet rs2 = conexion.executeQuery("SELECT * FROM islike, Comentario where islike.usuario='"+user.getNombre()+"' and islike.idcomentario="+ID+" and islike.idcomentario=Comentario.ID;");
                    rs2.next();
                    int numDislikes=rs2.getInt("numDislikes");
                    if(rs2.getInt("dislike")==1){
                        numDislikes--;
                        conexion.executeUpdate("UPDATE Comentario SET numDislikes="+numDislikes+" where ID="+ID+"");
                        conexion.executeUpdate("UPDATE islike SET dislike=false where islike.usuario='"+user.getNombre()+"' and islike.idcomentario="+ID+";");
                    } else if(rs2.getInt("islike")==1){
                        numDislikes++;
                        int numLikes = rs2.getInt("numLikes");
                        numLikes--;
                        conexion.executeUpdate("UPDATE Comentario SET numLikes="+numLikes+" where ID="+ID+";");
                        conexion.executeUpdate("UPDATE Comentario SET numDislikes="+numDislikes+" where ID="+ID+";");
                        conexion.executeUpdate("UPDATE islike SET dislike=true where islike.usuario='"+user.getNombre()+"' and islike.idcomentario="+ID+";");
                        conexion.executeUpdate("UPDATE islike SET islike=false where islike.usuario='"+user.getNombre()+"' and islike.idcomentario="+ID+";");
                    } else {
                        numDislikes++;
                        conexion.executeUpdate("UPDATE Comentario SET numDislikes="+numDislikes+" where ID="+ID+";");
                        conexion.executeUpdate("UPDATE islike SET dislike=true where islike.usuario='"+user.getNombre()+"' and islike.idcomentario="+ID+";");
                    }
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

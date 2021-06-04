package prSwishMedia.Controllers;

import prSwishMedia.Usuario;
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
    Usuario user;
    ChatController chatController;

    public ComentarioController(Statement st, ComentarioView comentarioView, PeliculaController peliculaController, int id, SerieController serieController, Usuario u, ChatController cc){
        conexion=st;
        cview=comentarioView;
        pcontroler=peliculaController;
        scontroler=serieController;
        ID=id;
        user=u;
        chatController=cc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String act = e.getActionCommand();
        switch (act){
            case "BORRAR":
                try {
                    if(chatController==null){
                        conexion.executeUpdate("set SQL_SAFE_UPDATES=0;");
                        conexion.executeUpdate("delete from islike where idcomentario="+ID+";");
                        conexion.executeUpdate("delete from Comentario where Comentario.ID="+ID+";");
                        conexion.executeUpdate("DELETE FROM Comunicación where Comunicación.ID="+ID+";");
                        if(pcontroler!=null){
                            pcontroler.actualizarComentarios();
                        }
                        if(scontroler!=null){
                            scontroler.actualizarComentarios();
                        }
                    } else {
                        conexion.executeUpdate("set SQL_SAFE_UPDATES=0;");
                        conexion.executeUpdate("delete from Mensaje where Mensaje.ID="+ID+";");
                        conexion.executeUpdate("DELETE FROM Comunicación where Comunicación.ID="+ID+";");
                        chatController.actualizarComentarios();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                break;
            case "LIKE":
                try {
                    if(chatController==null){
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
                    } else {
                        ResultSet rs2 = conexion.executeQuery("SELECT * FROM Comunicación, Mensaje where usuario='"+user.getNombre()+"' and Mensaje.ID="+ID+" and Mensaje.id=Comunicación.ID;");
                        rs2.next();
                        if(rs2.getInt("islikeUser")==1){
                            conexion.executeUpdate("UPDATE Mensaje SET isLikeUser=0 where ID="+ID+"");
                        } else if(rs2.getInt("isDislikeUser")==1){
                            conexion.executeUpdate("UPDATE Mensaje SET isLikeUser=1 where ID="+ID+";");
                            conexion.executeUpdate("UPDATE Mensaje SET isDislikeUser=0 where ID="+ID+";");
                        } else {
                            conexion.executeUpdate("UPDATE Mensaje SET isLikeUser=1 where ID="+ID+";");
                        }
                        chatController.actualizarComentarios();
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case "DISLIKE":
                try {
                    if(chatController==null){
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
                    } else {
                        ResultSet rs2 = conexion.executeQuery("SELECT * FROM Comunicación, Mensaje where usuario='"+user.getNombre()+"' and Mensaje.ID="+ID+" and Mensaje.id=Comunicación.ID;");
                        rs2.next();
                        if(rs2.getInt("islikeUser")==1){
                            conexion.executeUpdate("UPDATE Mensaje SET isLikeUser=0 where ID="+ID+"");
                            conexion.executeUpdate("UPDATE Mensaje SET isDislikeUser=1 where ID="+ID+"");
                        } else if(rs2.getInt("isDislikeUser")==1){
                            conexion.executeUpdate("UPDATE Mensaje SET isDislikeUser=0 where ID="+ID+";");
                        } else {
                            conexion.executeUpdate("UPDATE Mensaje SET isDislikeUser=1 where ID="+ID+";");
                        }
                        chatController.actualizarComentarios();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
        }
    }
}

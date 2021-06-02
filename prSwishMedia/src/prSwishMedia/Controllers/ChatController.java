package prSwishMedia.Controllers;

import prSwishMedia.Usuario;
import prSwishMedia.Views.ChatView;
import prSwishMedia.Views.ComentarioView;
import prSwishMedia.Views.ComentariosDeOtros;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChatController implements ActionListener {

    private Usuario tu;
    private Usuario otro;
    private Statement conexion;
    private  ChatView chatView;
    private JPanel listaComentarios;

    public ChatController(Usuario u1, Usuario u2, Statement st, ChatView cv){
        tu=u1;
        otro=u2;
        conexion=st;
        chatView=cv;
        listaComentarios=new JPanel();
        actualizarComentarios();
        setInfo();
    }

    public void setInfo() {
        chatView.setChat(otro.getNombre());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void actualizarComentarios(){
        listaComentarios.removeAll();
        try {
            ResultSet rs3 = conexion.executeQuery("SELECT COUNT(*) FROM Mensaje, Comunicación where Usuario='"+tu.getNombre()+"' and destinatario='"+otro.getNombre()+"';");
            rs3.next();
            int cont = rs3.getInt(1);
            if(cont<=3){
                listaComentarios.setLayout(new GridLayout(7,0,0,0));
            } else {
                listaComentarios.setLayout(new GridLayout(cont,0,0,0));
            }
            ResultSet rs2 = conexion.executeQuery("SELECT * FROM Comunicación, Mensaje where (Usuario='"+tu.getNombre()+"' and destinatario='"+otro.getNombre()+"' and Comunicación.ID=Mensaje.ID) or (Usuario='"+otro.getNombre()+"' and destinatario='"+tu.getNombre()+"' and Comunicación.ID=Mensaje.ID) order by fechaEnvio;");
            while(rs2.next()){
                if(tu.getNombre().equals(rs2.getString("Usuario"))){
                    ComentarioView comentario = new ComentarioView(rs2.getString("texto"),rs2.getInt("numDislikes"), rs2.getInt("numLikes"),rs2.getString("Usuario"),rs2.getString("fechaEnvio"));
                    ComentarioController controller = new ComentarioController(conexion,comentario,null,rs2.getInt("ID"),null,tu);
                    comentario.controlador(controller);
                    listaComentarios.add(comentario.get());
                } else {
                    ComentariosDeOtros comentario2 = new ComentariosDeOtros(rs2.getString("texto"),rs2.getInt("numDislikes"), rs2.getInt("numLikes"),rs2.getString("Usuario"),rs2.getString("fechaEnvio"));
                    ComentariosDeOtrosController controller = new ComentariosDeOtrosController(conexion,comentario2,null,rs2.getInt("ID"),null,otro);
                    comentario2.controlador(controller);
                    listaComentarios.add(comentario2.get());
                }
            }
            chatView.setMensajes(listaComentarios);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}

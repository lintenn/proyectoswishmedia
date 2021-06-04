package prSwishMedia.Controllers;

import prSwishMedia.Main;
import prSwishMedia.Usuario;
import prSwishMedia.Views.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChatController implements ActionListener, KeyListener {

    private Usuario tu;
    private Usuario otro;
    private Statement conexion;
    private  ChatView chatView;
    private JPanel listaComentarios;
    private OtherUserView otherUserView;
    private ProfileView profileView;
    private int num;

    public ChatController(Usuario u1, Usuario u2, Statement st, ChatView cv, OtherUserView ouv, ProfileView pv){
        tu=u1;
        otro=u2;
        conexion=st;
        chatView=cv;
        listaComentarios=new JPanel();
        otherUserView=ouv;
        profileView=pv;
        num=0;
        actualizarComentarios();
        setInfo();
    }

    public void setInfo() {
        chatView.setChat(otro.getNombre());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String act = e.getActionCommand();

        switch (act){
            case "VOLVER":
                if(otherUserView==null){
                    Main.frame.setContentPane(profileView.getPanel());
                    Main.frame.setVisible(true);
                } else {
                    Main.frame.setContentPane(otherUserView.getPanel());
                    Main.frame.setVisible(true);
                }
                break;
            case "ENVIAR":
                try {
                    introducirComentario();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
        }

    }

    public void actualizarComentarios(){
        listaComentarios.removeAll();
        try {
            ResultSet rs3 = conexion.executeQuery("SELECT COUNT(*) FROM Comunicación, Mensaje where (Usuario='"+tu.getNombre()+"' and destinatario='"+otro.getNombre()+"' and Comunicación.ID=Mensaje.ID) or (Usuario='"+otro.getNombre()+"' and destinatario='"+tu.getNombre()+"' and Comunicación.ID=Mensaje.ID) order by fechaEnvio;");
            rs3.next();
            int cont = rs3.getInt(1);
            if(cont<=3){
                listaComentarios.setLayout(new GridLayout(4,0,0,0));
            } else {
                listaComentarios.setLayout(new GridLayout(cont,0,0,0));
            }
            ResultSet rs2 = conexion.executeQuery("SELECT * FROM Comunicación, Mensaje where (Usuario='"+tu.getNombre()+"' and destinatario='"+otro.getNombre()+"' and Comunicación.ID=Mensaje.ID) or (Usuario='"+otro.getNombre()+"' and destinatario='"+tu.getNombre()+"' and Comunicación.ID=Mensaje.ID) order by fechaEnvio;");
            while(rs2.next()){
                if(tu.getNombre().equals(rs2.getString("Usuario"))){
                    ComentarioView comentario = new ComentarioView(rs2.getString("texto"),rs2.getInt("isDislikeUser")+rs2.getInt("isDislikeDest"), rs2.getInt("isLikeUser")+rs2.getInt("isLikeDest"),rs2.getString("Usuario"),rs2.getString("fechaEnvio"));
                    ComentarioController controller = new ComentarioController(conexion,comentario,null,rs2.getInt("ID"),null,tu, this);
                    comentario.controlador(controller);
                    listaComentarios.add(comentario.get());
                } else {
                    ComentariosDeOtros comentario2 = new ComentariosDeOtros(rs2.getString("texto"),rs2.getInt("isDislikeUser")+rs2.getInt("isDislikeDest"), rs2.getInt("isLikeUser")+rs2.getInt("isLikeDest"),rs2.getString("Usuario"),rs2.getString("fechaEnvio"));
                    ComentariosDeOtrosController controller = new ComentariosDeOtrosController(conexion,comentario2,null,rs2.getInt("ID"),null,otro, this);
                    comentario2.controlador(controller);
                    listaComentarios.add(comentario2.get());
                }
            }
            chatView.setMensajes(listaComentarios);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void ponerComentario(boolean si) throws SQLException {
        ResultSet rs2 = conexion.executeQuery("SELECT Count(*) FROM Comunicación, Mensaje where (Usuario='"+tu.getNombre()+"' and destinatario='"+otro.getNombre()+"' and Comunicación.ID=Mensaje.ID) or (Usuario='"+otro.getNombre()+"' and destinatario='"+tu.getNombre()+"' and Comunicación.ID=Mensaje.ID) order by fechaEnvio;");
        rs2.next();
        int max=rs2.getInt(1);
        ResultSet rs = conexion.executeQuery("SELECT * FROM Comunicación, Mensaje where (Usuario='"+tu.getNombre()+"' and destinatario='"+otro.getNombre()+"' and Comunicación.ID=Mensaje.ID) or (Usuario='"+otro.getNombre()+"' and destinatario='"+tu.getNombre()+"' and Comunicación.ID=Mensaje.ID) order by fechaEnvio;");
        int i=0;
        if(si){
            if(num<max){
                num++;
            }
            while(rs.next()&&num>i){
                chatView.setTextField1(rs.getString("texto"));
                i++;
            }
        } else {
            if(num>0){
                num--;
            }
            while(rs.next()&&num>i){
                chatView.setTextField1(rs.getString("texto"));
                i++;
            }
        }
        if(num==0){
            chatView.setTextField1("");
        }
    }

    private int generateID () throws SQLException {
        ResultSet res = conexion.executeQuery("SELECT MAX(id) FROM Comunicación;");
        int id=0;
        try{
            res.next();
            id=res.getInt("MAX(id)")+1;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private void introducirComentario() throws SQLException {
        int ID = generateID();
        StringBuilder s = new StringBuilder();
        s.append(chatView.getTextField1());
        while(s.indexOf(" ")!=-1){
            s.deleteCharAt(s.indexOf(" "));
        }
        if(s.length()>0){
            try {
                conexion.executeUpdate("Insert into Comunicación (texto, fechaEnvio, Usuario, ID) values('"+chatView.getTextField1()+"',now(),'"+tu.getNombre()+"',"+ID+");");
                conexion.executeUpdate("Insert Into Mensaje (ID,islikeUser,destinatario,isDislikeUser) values("+ID+",0,'"+otro.getNombre()+"',0);");
                ResultSet rs = conexion.executeQuery("SELECT COUNT(*) FROM Amigo where usuario1 = '"+otro.getNombre()+"' and usuario2 = '"+tu.getNombre()+"' ");
                rs.next();
                if(rs.getInt(1)==0){
                    conexion.executeUpdate("INSERT INTO Amigo (id, usuario1, usuario2, mensaje) values(" + generateID() + ",'" + otro.getNombre() + "','" + tu.getNombre() + "', true)");
                }
                System.out.println(tu.getNombre());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            chatView.setTextField1("");
            actualizarComentarios();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==38){ //flecha hacia arriba
            try {
                ponerComentario(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (e.getKeyCode()==40){ //flecha hacia abajo
            try {
                ponerComentario(false);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if(e.getKeyCode()==10){
            try {
                introducirComentario();
            } catch (SQLException throwables) {
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==222){
            StringBuilder s = new StringBuilder();
            s.append(chatView.getTextField1());
            String s1 = chatView.getTextField1();
            while(s1.contains("'")){
                s.deleteCharAt(s.indexOf("'"));
                chatView.setTextField1(s.toString());
                s1 = chatView.getTextField1();
            }
            e.consume();
        }
    }
}

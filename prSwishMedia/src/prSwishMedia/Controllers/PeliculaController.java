package prSwishMedia.Controllers;

import prSwishMedia.Comentario;
import prSwishMedia.Main;
import prSwishMedia.Pelicula;
import prSwishMedia.Views.PeliculaView;
import prSwishMedia.Views.PrincipalView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import prSwishMedia.Usuario;
import prSwishMedia.Views.ComentarioView;
import prSwishMedia.Views.ComentariosDeOtros;
import prSwishMedia.Views.PeliculaView;

import javax.swing.*;
import java.awt.event.KeyEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PeliculaController implements ActionListener, KeyListener {

    private PeliculaView peliview;
    private boolean porderEntregar;
    private Usuario user;
    private Statement conexion;
    private int IDContenido;
    private JPanel listaComentarios = new JPanel();
    PrincipalView principalView;
    Pelicula pelicula;
    String fecha_Estreno;
    private int num;

    public PeliculaController(PeliculaView peliview, Usuario u, Statement st, Pelicula p, PrincipalView ppv, int id, String fechaE) throws SQLException {
        this.peliview=peliview;
        user=u;
        conexion=st;
        pelicula=p;
        principalView=ppv;
        IDContenido=id;
        fecha_Estreno=fechaE;
        setInfo();
        actualizarComentarios();
        num=0;
    }

    private void setInfo() {
        peliview.setNombrePelicula(pelicula.getNombre());
        peliview.setValoracionPelicula(Integer.toString(pelicula.getRating()));
        peliview.setFechaPelicula(fecha_Estreno);
        peliview.setPeliculaDuracion(pelicula.getDuracion());
        peliview.setPeliculaGénero(pelicula.getGenero());
        peliview.setSinopsisPelicula(pelicula.getSinopsis());
        peliview.setRepartoPelicula(pelicula.getReparto());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String act = e.getActionCommand();
        switch (act){
            case ("VOLVER"):
                Main.frame.setContentPane(principalView.getPanel());
                Main.frame.setVisible(true);
                break;
            case ("TRAILER"):
                openWebPage("https://youtu.be/dQw4w9WgXcQ");
                break;
            case ("ENVIAR"):
                try {
                    introducirComentario();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
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
        s.append(peliview.getTextField1().getText());
        while(s.indexOf(" ")!=-1){
            s.deleteCharAt(s.indexOf(" "));
        }
        if(s.length()>0){
            try {
                conexion.executeUpdate("Insert into Comunicación (texto, fechaEnvio, Usuario, ID) values('"+peliview.getTextField1().getText()+"',now(),'"+user.getNombre()+"',"+ID+");");
                conexion.executeUpdate("Insert Into Comentario (numDislikes,numLikes,ID,IDContenido) values(0,0,"+ID+","+IDContenido+");");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            peliview.setTextField1("");
            actualizarComentarios();
        }
    }

    public void ponerComentario(boolean si) throws SQLException {
        ResultSet rs2 = conexion.executeQuery("SELECT COUNT(*) FROM Comentario, Comunicación where Usuario='"+user.getNombre()+"' and Comentario.ID = Comunicación.ID and Comentario.IDContenido="+IDContenido+" Order by fechaEnvio DESC;");
        rs2.next();
        int max=rs2.getInt(1);
        ResultSet rs = conexion.executeQuery("SELECT * FROM Comentario, Comunicación where Usuario='"+user.getNombre()+"' and Comentario.ID = Comunicación.ID and Comentario.IDContenido="+IDContenido+" Order by fechaEnvio DESC;");
        int i=0;
        if(si){
            if(num<max){
                num++;
            }
            while(rs.next()&&num>i){
                peliview.setTextField1(rs.getString("texto"));
                i++;
            }
        } else {
            if(num>0){
                num--;
            }
            while(rs.next()&&num>i){
                peliview.setTextField1(rs.getString("texto"));
                i++;
            }
        }
        if(num==0){
            peliview.setTextField1("");
        }
    }

    public void actualizarComentarios(){
        listaComentarios.removeAll();
        try {
            ResultSet rs3 = conexion.executeQuery("SELECT COUNT(*) FROM Comentario, Comunicación where IDContenido="+IDContenido+" and Comentario.ID=Comunicación.ID");
            rs3.next();
            int cont = rs3.getInt(1);
            if(cont<=3){
                listaComentarios.setLayout(new GridLayout(4,0,0,0));
            } else {
                listaComentarios.setLayout(new GridLayout(cont,0,0,0));
            }
            ResultSet rs2 = conexion.executeQuery("SELECT * FROM Comunicación, Comentario where IDContenido="+IDContenido+" and Comentario.ID=Comunicación.ID");
            while(rs2.next()){
                if(user.getNombre().equals(rs2.getString("Usuario"))){
                    ComentarioView comentario = new ComentarioView(rs2.getString("texto"),rs2.getInt("numDislikes"), rs2.getInt("numLikes"),rs2.getString("Usuario"),rs2.getString("fechaEnvio"));
                    ComentarioController controller = new ComentarioController(conexion,comentario,this,rs2.getInt("ID"),null);
                    comentario.controlador(controller);
                    listaComentarios.add(comentario.get());
                } else {
                    listaComentarios.add(new ComentariosDeOtros(rs2.getString("texto"),rs2.getInt("numDislikes"), rs2.getInt("numLikes"),rs2.getString("Usuario"),rs2.getString("fechaEnvio")).get());
                }
            }
            peliview.setComentariosPanel(listaComentarios);
        } catch (SQLException e){
            e.printStackTrace();
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
            s.append(peliview.getTextField1().getText());
            String s1 = peliview.getTextField1().getText();
            while(s1.contains("'")){
                s.deleteCharAt(s.indexOf("'"));
                peliview.setTextField1(s.toString());
                s1 = peliview.getTextField1().getText();
            }
            e.consume();
        }
    }

    public void openWebPage(String url){
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        }
        catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

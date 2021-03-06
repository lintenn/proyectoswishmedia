package prSwishMedia.Controllers;

import prSwishMedia.*;
import prSwishMedia.Views.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import prSwishMedia.Views.PeliculaView;

import javax.swing.*;
import java.awt.event.KeyEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;


public class PeliculaController implements ActionListener, KeyListener {

    private PeliculaView peliview;
    private Usuario user;
    private Statement conexion;
    private int IDContenido;
    private JPanel listaComentarios = new JPanel();
    PrincipalView principalView;
    Pelicula pelicula;
    String fecha_Estreno;
    private int num;
    PeliculaPreView ppview;

    public PeliculaController(PeliculaView peliview, Usuario u, Statement st, Pelicula p, PrincipalView ppv, int id, String fechaE, PeliculaPreView peliculaPreView) throws SQLException {
        this.peliview=peliview;
        user=u;
        conexion=st;
        pelicula=p;
        principalView=ppv;
        IDContenido=id;
        fecha_Estreno=fechaE;
        ppview=peliculaPreView;
        setInfo();
        actualizarComentarios();
        actualizarCombobox();
        ponerValoracion();
        num=0;
    }

    private void setInfo() {
        peliview.setNombrePelicula(pelicula.getNombre());
        peliview.setValoracionPelicula(Double.toString(pelicula.getValoracion()));
        peliview.setFechaPelicula(fecha_Estreno);
        peliview.setPeliculaDuracion(pelicula.getDuracion());
        peliview.setPeliculaGénero(pelicula.getGenero());
        peliview.setSinopsisPelicula(pelicula.getSinopsis());
        peliview.setRepartoPelicula(pelicula.getReparto());
        peliview.setAñadidaPelicula(Integer.toString(pelicula.getVeces_anyadidas()));
        peliview.setImagen(pelicula.getId());

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
                String url="";
                try {
                    ResultSet rs= conexion.executeQuery("SELECT trailer FROM ContenidoMultimedia where idContenidoMultimedia=" + pelicula.getId() + ";");
                    rs.next();
                    url= rs.getString(1);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                openWebPage(url);
                break;
            case ("ENVIAR"):
                try {
                    introducirComentario();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case ("VALORAR"):
                cambiarValoracion();
                actualizarValoracion();
                break;
            case ("AÑADIR"):
                java.util.List<Lista> listasUsuariouser=user.getListasPersonales();
                Lista listaSeleccionada=peliview.getAñadirPelicula();
                if(listasUsuariouser.contains(listaSeleccionada) && !listaSeleccionada.esta(pelicula.getId())){
                    if(listaSeleccionada.getNombre().equals("Vistas")){
                        try {
                            conexion.executeUpdate("UPDATE Usuario SET numPeliculasVistas="+(user.getNumPeliculasVistas()+1)+" where nombre='"+user.getNombre()+"';" );
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        user.setNumPeliculasVistas(user.getNumPeliculasVistas()+1);
                    }
                    try {
                        conexion.executeUpdate("INSERT INTO AñadirContenido (idContenidoMultimedia,idLista) VALUES("+pelicula.getId()+","+listaSeleccionada.getId()+");");
                        ResultSet rs = conexion.executeQuery("SELECT * from ContenidoMultimedia where idContenidoMultimedia="+IDContenido+"");
                        rs.next();
                        conexion.executeUpdate("UPDATE ContenidoMultimedia SET veces_añadidas="+(rs.getInt("veces_añadidas")+1)+" where idContenidoMultimedia="+IDContenido+"");
                        pelicula.setVeces_anyadidas(pelicula.getVeces_anyadidas()+1);
                        peliview.setAñadidaPelicula(Integer.toString(pelicula.getVeces_anyadidas()));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                break;
            case ("ELIMINAR"):
                List<Lista> listasUsuariouser2=user.getListasPersonales();
                Lista listaSeleccionada2=peliview.getAñadirPelicula();
                if(listasUsuariouser2.contains(listaSeleccionada2) && listaSeleccionada2.esta(pelicula.getId())){

                    if(listaSeleccionada2.getNombre().equals("Vistas")){
                        try {
                            conexion.executeUpdate("UPDATE Usuario SET numPeliculasVistas="+(user.getNumPeliculasVistas()-1)+" where nombre='"+user.getNombre()+"';" );
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        user.setNumPeliculasVistas(user.getNumPeliculasVistas()-1);
                    }

                    try {
                        ResultSet rs = conexion.executeQuery("SELECT * from ContenidoMultimedia where idContenidoMultimedia="+IDContenido+"");
                        rs.next();
                        conexion.executeUpdate("UPDATE ContenidoMultimedia SET veces_añadidas="+(rs.getInt("veces_añadidas")-1)+" where idContenidoMultimedia="+IDContenido+"");
                        conexion.executeUpdate("DELETE FROM AñadirContenido where idContenidoMultimedia = "+pelicula.getId()+" and idLista ="+listaSeleccionada2.getId()+";");
                        pelicula.setVeces_anyadidas(pelicula.getVeces_anyadidas()-1);
                        peliview.setAñadidaPelicula(Integer.toString(pelicula.getVeces_anyadidas()));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
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
                    ComentarioController controller = new ComentarioController(conexion,comentario,this,rs2.getInt("ID"),null,user, null);
                    comentario.controlador(controller);
                    listaComentarios.add(comentario.get());
                } else {
                    ComentariosDeOtros comentario2 = new ComentariosDeOtros(rs2.getString("texto"),rs2.getInt("numDislikes"), rs2.getInt("numLikes"),rs2.getString("Usuario"),rs2.getString("fechaEnvio"));
                    ComentariosDeOtrosController controller = new ComentariosDeOtrosController(conexion,comentario2,this,rs2.getInt("ID"),null,user, null);
                    comentario2.controlador(controller);
                    listaComentarios.add(comentario2.get());
                }
            }
            peliview.setComentariosPanel(listaComentarios);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void ponerValoracion(){
        peliview.getComboBoxvalorar().removeAll();
        peliview.setComboBoxvalorar2("-");
        for(int i=0; i<=10; i++){
            peliview.setComboBoxvalorar(i);
        }
        try {
            ResultSet rs2 = conexion.executeQuery("SELECT Count(*) FROM Valora where nombreUsuario='"+user.getNombre()+"' and idContenido="+IDContenido+";");
            rs2.next();
            if(rs2.getInt(1)!=0){
                ResultSet rs = conexion.executeQuery("SELECT * FROM Valora where nombreUsuario='"+user.getNombre()+"' and idContenido="+IDContenido+";");
                rs.next();
                peliview.getComboBoxvalorar().setSelectedItem(rs.getInt("valoracion"));
            }
        } catch (SQLException e) {
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

    public void cambiarValoracion(){
        try {
            ResultSet rs = conexion.executeQuery("SELECT COUNT(*) FROM Valora where nombreUsuario='"+user.getNombre()+"' and idContenido="+IDContenido+";");
            rs.next();
            if(!peliview.getItemComboBoxvalorar().toString().equals("-")){
                if(rs.getInt(1)==0){
                    conexion.executeUpdate("INSERT INTO Valora (nombreUsuario,idContenido,valoracion) values ('"+user.getNombre()+"', "+IDContenido+", "+Integer.parseInt(peliview.getItemComboBoxvalorar().toString())+")");
                } else {
                    conexion.executeUpdate("UPDATE Valora SET valoracion="+peliview.getItemComboBoxvalorar()+" where nombreUsuario='"+user.getNombre()+"' and idContenido="+IDContenido+";");
                }
            } else {
                conexion.executeUpdate("DELETE FROM Valora where nombreUsuario='"+user.getNombre()+"' and idContenido="+IDContenido+";");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarValoracion(){
        try {
            ResultSet rs = conexion.executeQuery("SELECT COUNT(*) FROM Valora where idContenido="+IDContenido+";");
            rs.next();
            double num=rs.getInt(1);
            if(num==0){
                peliview.setValoracionPelicula(Integer.toString(0));
                ppview.setValoracion(0);
            } else {
                ResultSet rs2 = conexion.executeQuery("SELECT SUM(valoracion) as valoracion FROM Valora where idContenido="+IDContenido+";");
                rs2.next();
                double val=rs2.getInt(1);
                DecimalFormat formato2 = new DecimalFormat("#.0");
                ppview.setValoracion2(formato2.format(val/num));
                peliview.setValoracionPelicula(formato2.format(val/num));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void actualizarCombobox(){
        if(user.getListasPersonales()!=null){
            for(Lista l : user.getListasPersonales()){
                peliview.setAñadirPelicula(l);
            }
        }
    }
}

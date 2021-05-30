package prSwishMedia.Controllers;

import prSwishMedia.Main;
import prSwishMedia.Serie;
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
import java.text.DecimalFormat;

public class SerieController  implements ActionListener, KeyListener {

    PrincipalView ppview;
    SerieView serieView;
    Serie serie;
    Statement conexion;
    private int num;
    private JPanel listaComentarios = new JPanel();
    private int IDContenido;
    private Usuario user;
    SeriePreView spview;

    public SerieController(PrincipalView ppv, SerieView sv, Serie s, Statement conexion, int id, Usuario u, SeriePreView spv){
        ppview=ppv;
        serieView=sv;
        serie=s;
        this.conexion=conexion;
        num=0;
        IDContenido=id;
        user=u;
        spview=spv;
        setInfo();
        actualizarComentarios();
        ponerValoracion();
    }

    public void setInfo(){
        serieView.setNombreSerie(serie.getNombre());
        serieView.setValoracionSerie(serie.getValoracion());
        serieView.setFechaSerie(serie.getFecha());
        serieView.setAñadidaSerie(serie.getVeces_anyadidas());
        serieView.setCapitulosSerie(serie.getNumCapitulos());
        serieView.setTemporadasSerie(serie.getNumTemporadas());
        serieView.setGeneroSerie(serie.getGenero());
        serieView.setSinopsisSerie(serie.getSinopsis());
        serieView.setRepartoSerie(serie.getReparto());
        serieView.setDuracionSerie(serie.getDuracionMedia());
        serieView.setImagen(IDContenido);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String act = e.getActionCommand();
        switch (act) {
            case ("VOLVER"):
                Main.frame.setContentPane(ppview.getPanel());
                Main.frame.setVisible(true);
                break;
            case ("TRAILER"):
                String url="";
                try {
                    ResultSet rs= conexion.executeQuery("SELECT trailer FROM ContenidoMultimedia where idContenidoMultimedia=" + serie.getId() + ";");
                    rs.next();
                    url= rs.getString(1);
                    System.out.println(url);
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
        s.append(serieView.getTextField1().getText());
        while(s.indexOf(" ")!=-1){
            s.deleteCharAt(s.indexOf(" "));
        }
        if(s.length()>0){
            try {
                conexion.executeUpdate("Insert into Comunicación (texto, fechaEnvio, Usuario, ID) values('"+serieView.getTextField1().getText()+"',now(),'"+user.getNombre()+"',"+ID+");");
                conexion.executeUpdate("Insert Into Comentario (numDislikes,numLikes,ID,IDContenido) values(0,0,"+ID+","+IDContenido+");");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            serieView.setTextField1("");
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
                serieView.setTextField1(rs.getString("texto"));
                i++;
            }
        } else {
            if(num>0){
                num--;
            }
            while(rs.next()&&num>i){
                serieView.setTextField1(rs.getString("texto"));
                i++;
            }
        }
        if(num==0){
            serieView.setTextField1("");
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
                    ComentarioController controller = new ComentarioController(conexion,comentario,null,rs2.getInt("ID"), this,user);
                    comentario.controlador(controller);
                    listaComentarios.add(comentario.get());
                } else {
                    ComentariosDeOtros comentario2 = new ComentariosDeOtros(rs2.getString("texto"),rs2.getInt("numDislikes"), rs2.getInt("numLikes"),rs2.getString("Usuario"),rs2.getString("fechaEnvio"));
                    ComentariosDeOtrosController controller = new ComentariosDeOtrosController(conexion,comentario2,null,rs2.getInt("ID"),this,user);
                    comentario2.controlador(controller);
                    listaComentarios.add(comentario2.get());
                }
            }
            serieView.setComentariosPanel(listaComentarios);
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
            s.append(serieView.getTextField1().getText());
            String s1 = serieView.getTextField1().getText();
            while(s1.contains("'")){
                s.deleteCharAt(s.indexOf("'"));
                serieView.setTextField1(s.toString());
                s1 = serieView.getTextField1().getText();
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
            if(!serieView.getItemComboBoxvalorar().toString().equals("-")){
                if(rs.getInt(1)==0){
                    conexion.executeUpdate("INSERT INTO Valora (nombreUsuario,idContenido,valoracion) values ('"+user.getNombre()+"', "+IDContenido+", "+Integer.parseInt(serieView.getItemComboBoxvalorar().toString())+")");
                } else {
                    conexion.executeUpdate("UPDATE Valora SET valoracion="+serieView.getItemComboBoxvalorar()+" where nombreUsuario='"+user.getNombre()+"' and idContenido="+IDContenido+";");
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
                serieView.setValoracionSerie2(Integer.toString(0));
            } else {
                ResultSet rs2 = conexion.executeQuery("SELECT SUM(valoracion) as valoracion FROM Valora where idContenido="+IDContenido+";");
                rs2.next();
                double val=rs2.getInt(1);
                DecimalFormat formato2 = new DecimalFormat("#.0");
                spview.setValoracion2(formato2.format(val/num));
                serieView.setValoracionSerie2(formato2.format(val/num));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ponerValoracion(){
        serieView.getComboBoxvalorar().removeAll();
        serieView.setComboBoxvalorar2("-");
        for(int i=0; i<=10; i++){
            serieView.setComboBoxvalorar(i);
        }
        try {
            ResultSet rs2 = conexion.executeQuery("SELECT Count(*) FROM Valora where nombreUsuario='"+user.getNombre()+"' and idContenido="+IDContenido+";");
            rs2.next();
            if(rs2.getInt(1)!=0){
                ResultSet rs = conexion.executeQuery("SELECT * FROM Valora where nombreUsuario='"+user.getNombre()+"' and idContenido="+IDContenido+";");
                rs.next();
                serieView.getComboBoxvalorar().setSelectedItem(rs.getInt("valoracion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

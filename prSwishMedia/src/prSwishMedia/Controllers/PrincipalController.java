package prSwishMedia.Controllers;

import com.kitfox.svg.A;
import prSwishMedia.*;
import prSwishMedia.Views.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrincipalController implements ActionListener {

    LoginView lview;
    Statement conexion;
    PrincipalView ppView;
    Usuario user;
    List<PeliculaPreViewController> listapvC;
    List<SeriePreviewController> listasvC;
    List<UsuarioPreViewController> listauvC;

    public PrincipalController(LoginView lv, PrincipalView ppv, Statement st, Usuario u){
        conexion=st;
        lview=lv;
        ppView=ppv;
        user=u;
        listapvC=new ArrayList<>();
        listasvC=new ArrayList<>();
        listauvC=new ArrayList<>();
        añadirContenido(-3);
        añadirContenido(-2);
        añadirContenido(-1);
        setLista();
    }


    public void actionPerformed(ActionEvent e) {
        String act=e.getActionCommand();

        if(act.equals("PROFILE")){
            ProfileView pview = new ProfileView(conexion);
            ProfileController pc = new ProfileController(this,pview,ppView,lview,conexion,user);
            pview.controlador(pc);

            Main.frame.setContentPane(pview.getPanel());
            Main.frame.setVisible(true);
        }else if(act.equals("LISTA")){
            //añadirContenidoListas(ppView.getListaSeleccionada());
       }
    }

    public void añadirContenido(int idList){

        if(idList==-3) {
            ppView.removeAlllistasUsers();
            try {
                ResultSet count= conexion.executeQuery("SELECT COUNT(*) FROM Usuario WHERE nombre <> '" + user.getNombre() +"';");
                count.next();
                int cont=count.getInt(1);
                ResultSet users= conexion.executeQuery("SELECT * FROM Usuario WHERE nombre <> '" + user.getNombre() +"';");
                ppView.setLayoutListasUsers(cont);

                // Creo lista para almacenar los idContenidoMultimedia y referencias de pelipv
                ArrayList<UsuarioPreView> listaUserpv = new ArrayList<>();
                while(users.next()) {

                    Usuario usuario = new Usuario(users.getString("nombre"), users.getString("email"), users.getString("contraseña"));
                    UsuarioPreView userpv = new UsuarioPreView();
                    UsuarioPreViewController userPvController = new UsuarioPreViewController(userpv,usuario);
                    listauvC.add(userPvController);

                    userpv.controlador(userPvController);
                    listaUserpv.add(userpv);

                    //MiMouseListener listener = new MiMouseListener(pelipv,peli.getInt(1));
                    //pelipv.getPanel().addMouseListener(listener);
                    ppView.addListaUser(userpv.getPanel());
                }

                ppView.setViewportViewScroll(ppView.getListaUsers());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else if(idList==-2){
            ppView.removeAllListas();
            try {
                ResultSet count= conexion.executeQuery("SELECT COUNT(*) FROM ContenidoMultimedia join Pelicula on ContenidoMultimedia.idContenidoMultimedia=Pelicula.idContenidoMultimedia;");
                count.next();
                int cont=count.getInt(1);
                ResultSet peli= conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Pelicula on ContenidoMultimedia.idContenidoMultimedia=Pelicula.idContenidoMultimedia;");
                ppView.setLayoutListas(cont);

                // Creo lista para almacenar los idContenidoMultimedia y referencias de pelipv
                ArrayList<Integer> listaids = new ArrayList<>();
                ArrayList<PeliculaPreView> listapelipv = new ArrayList<>();
                while(peli.next()) {
                    // Necesitamos guardar las variables antes de hacer una nueva consulta,
                    // si no, se borra la informacion de la anterior consulta
                    listaids.add(peli.getInt("idContenidoMultimedia"));

                    Pelicula pelicula = new Pelicula(peli.getString("nombre"), peli.getInt("imagen"), peli.getString("sinopsis"), peli.getString("genero"), 0);
                    PeliculaPreView pelipv = new PeliculaPreView();
                    PeliculaPreViewController peliPvController = new PeliculaPreViewController(ppView,pelipv,pelicula,user,conexion,ppView.getComboBox1());
                    listapvC.add(peliPvController);

                    pelipv.controlador(peliPvController);
                    listapelipv.add(pelipv);

                    MiMouseListener listener = new MiMouseListener(pelipv,peli.getInt(1));
                    pelipv.getPanel().addMouseListener(listener);
                    ppView.addListaPelis(pelipv.getPanel());
                }
                for (int i = 0; i < listaids.size(); i++) {
                    //Por cada peli generamos su valoracion media
                    ResultSet valmed = conexion.executeQuery("SELECT IFNULL(AVG(valoracion),0) FROM Valora WHERE idContenido=" + listaids.get(i) + ";");
                    valmed.next(); //apuntamos
                    listapelipv.get(i).setValoracion(valmed.getInt(1));
                }

                ppView.setViewportViewScroll(ppView.getListaPelis());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else if(idList==-1){
            ppView.removeAllListasSerie();
            try {
                ResultSet count= conexion.executeQuery("SELECT COUNT(*) FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia;");
                count.next();
                int cont=count.getInt(1);
                ResultSet peli= conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia;");
                ppView.setLayoutListasSerie(cont);
                while(peli.next()) {
                    Serie serie=new Serie(peli.getString("nombre"), peli.getInt("imagen"), peli.getString("sinopsis"), peli.getString("Genero"),0,peli.getInt("numTemporadas"));
                    SeriePreView seriepv = new SeriePreView();
                    SeriePreviewController seriepvC = new SeriePreviewController(serie,seriepv,ppView.getComboBox1());
                    listasvC.add(seriepvC);
                    seriepv.controlador(seriepvC);

                    //MiMouseListener listener = new MiMouseListener();
                    //seriepv.getPanel().addMouseListener(listener);
                    ppView.addListaSerie(seriepv.getPanel());
                }
                ppView.setViewportViewScrollSerie(ppView.getListaSeries());


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void setLista(){
        ArrayList<Lista> listaActualizada = new ArrayList<>();
        Lista actual;

        ppView.removeAllComboBox();
        try {
            ResultSet rs = conexion.executeQuery("SELECT * FROM Lista where Nombreusuario = '"+user.getNombre()+"';");
            while(rs.next()){
                actual=new Lista(rs.getInt(1),rs.getString(2),rs.getDate(3));
                listaActualizada.add(actual);
                ppView.addItemComboBox1(actual);
            }
            user.setListasPersonales(listaActualizada);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        setListaPreViews();
    }

    private void setListaPreViews() {
        for(PeliculaPreViewController l: listapvC){
            l.actualizarComboBox(ppView.getComboBox1());
        }
        for(SeriePreviewController l: listasvC){
            l.actualizarComboBox(ppView.getComboBox1());
        }
    }

    public class MiMouseListener implements MouseListener {
        PeliculaPreView pview;
        int id;
        public MiMouseListener(PeliculaPreView pelipv, int anInt) {
            pview=pelipv;
            id=anInt;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                ResultSet resst = conexion.executeQuery("SELECT * FROM ContenidoMultimedia, Pelicula where ContenidoMultimedia.idContenidoMultimedia="+id+";");
                resst.next();
                String fechaEstreno=resst.getString("fecha_estreno");
                Date date=new SimpleDateFormat("dd-MM-yyyy").parse(fechaEstreno);
                Pelicula pelicula = new Pelicula(resst.getString("nombre"),0,fechaEstreno,resst.getInt("duracion"), resst.getString("genero"), resst.getString("sinopsis"),resst.getString("reparto"));
                PeliculaView peliview = new PeliculaView();
                PeliculaController peliculaController=new PeliculaController(peliview,user,conexion,pelicula,ppView,id);
                peliview.controlador(peliculaController);
                Main.frame.setContentPane(peliview.getPanel());
                Main.frame.setVisible(true);
            } catch (SQLException | ParseException throwables) {
                throwables.printStackTrace();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //System.out.println("hola2");
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //System.out.println("hola3");
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //System.out.println("hola4");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //System.out.println("hola5");
        }
    }
}

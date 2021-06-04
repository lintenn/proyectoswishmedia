package prSwishMedia.Controllers;

import com.kitfox.svg.A;
import prSwishMedia.*;
import prSwishMedia.Views.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrincipalController implements ActionListener {

    private LoginView lview;
    private Statement conexion;
    private Statement conexion1;
    private Statement conexion2;
    private PrincipalView ppView;
    private Usuario user;
    private List<PeliculaPreViewController> listapvC;
    private List<SeriePreviewController> listasvC;
    private List<ContenidoMultimediaPreViewController>  listasSyPC;
    private List<UsuarioPreViewController> listauvC;
    private ProfileView pview;

    public PrincipalController(LoginView lv, PrincipalView ppv, Statement st,Statement st1,Statement st2, Usuario u){
        conexion=st;
        conexion1=st1;
        conexion2=st2;
        lview=lv;
        ppView=ppv;
        user=u;
        listapvC=new ArrayList<>();
        listasvC=new ArrayList<>();
        listasSyPC=new ArrayList<>();
        listauvC=new ArrayList<>();

        añadirContenidoG(-3,null,"inicial");//añadirContenido(-3); // usuarios
        añadirContenidoG(-2,null, "inicial");//añadirContenido(-2); // peliculas
        añadirContenidoG(-1,null, "inicial");//añadirContenido(-1); // series
        setLista();
    }

    public void aumentarUnAmigo(){user.setNumAmigos(user.getNumAmigos()+1);}

    public void actionPerformed(ActionEvent e) {
        String act=e.getActionCommand();

        if(act.equals("PROFILE")){
            try {
                Main.setUser(user.getNombre(),conexion);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            pview = new ProfileView(conexion);
            ProfileController pc = new ProfileController(this,pview,ppView,lview,conexion,conexion1,Main.getUser());
            pview.controlador(pc);

            Main.frame.setContentPane(pview.getPanel());
            Main.frame.setVisible(true);
        }else if(act.equals("LISTA")){
            if(ppView.getListaSeleccionada()!=null) añadirContenidoG(ppView.getListaSeleccionada().getId(),null,"inicial");//añadirContenido(ppView.getListaSeleccionada().getId());
       }else if(act.equals("BUSCARL")){
            if(ppView.getListaSeleccionada()!=null){
                añadirContenidoG(ppView.getListaSeleccionada().getId(),ppView.getBuscadorL().getText(),"subs");//añadirContenidoSubs(ppView.getListaSeleccionada().getId(),ppView.getBuscadorL().getText());
            }
        }else if(act.equals("BUSCARP")){
            añadirContenidoG(-2,ppView.getBuscadorP().getText(), "subs");//añadirContenidoSubs(-2,ppView.getBuscadorP().getText());
        }else if(act.equals("BUSCARS")){
            añadirContenidoG(-1,ppView.getBuscadorS().getText(), "subs");//añadirContenidoSubs(-1,ppView.getBuscadorS().getText());
        }else if(act.equals("BUSCARU")){
            añadirContenidoG(-3,ppView.getBuscadorU().getText(), "subs");//añadirContenidoSubs(-3,ppView.getBuscadorU().getText());
        } else if(act.equals("ORDENL")){
            if(ppView.getListaSeleccionada()!=null){
                añadirContenidoG(ppView.getListaSeleccionada().getId(),ppView.getBuscadorL().getText(), "ord");//añadirContenidoOrd(ppView.getListaSeleccionada().getId(),ppView.getBuscadorL().getText());
            }
        }else if(act.equals("ORDENP")){
            añadirContenidoG(-2,ppView.getBuscadorP().getText(), "ord");//añadirContenidoOrd(-2,ppView.getBuscadorP().getText());
        }else if(act.equals("ORDENS")){
            añadirContenidoG(-1,ppView.getBuscadorS().getText(), "ord");//añadirContenidoOrd(-1,ppView.getBuscadorS().getText());
        }else if(act.equals("ORDENU")){
            añadirContenidoG(-3,ppView.getBuscadorU().getText(), "ord");//añadirContenidoOrd(-3,ppView.getBuscadorU().getText());
        }
    }

    public void añadirContenidoG(int idList, String s, String metodo) {

        if(idList==-3) { // obtener usuarios
            ppView.removeAlllistasUsers();
            try {
                ResultSet count= conexion.executeQuery("SELECT COUNT(*) FROM Usuario WHERE nombre <> '" + user.getNombre() +"';");
                count.next();
                int cont=count.getInt(1);

                ResultSet users;
                // VARÍA EN FUNCIÓN DEL MÉTODO SOLICITADO
                if (metodo.equals("subs")) {
                    users = conexion.executeQuery("SELECT * FROM Usuario WHERE nombre <> '" + user.getNombre() + "' AND nombre LIKE '%" + s + "%';");
                } else if (metodo.equals("ord")) {
                    users= conexion.executeQuery("SELECT * FROM Usuario WHERE nombre <> '" + user.getNombre() +"' AND nombre LIKE '%" + s + "%' ORDER BY nombre;");
                } else {
                    users= conexion.executeQuery("SELECT * FROM Usuario WHERE nombre <> '" + user.getNombre() +"';");
                }

                ppView.setLayoutListasUsers(cont);

                while(users.next()) {

                    if (metodo.equals("inicial")) {
                        ProfileView pview = new ProfileView(conexion);

                        ProfileController pc = new ProfileController(this, pview, ppView, lview, conexion, conexion1, user);
                        pview.controlador(pc);
                    }

                    Usuario usuario = new Usuario(users.getString("nombre"), users.getString("email"), users.getString("contraseña"),users.getString("descripcion"),users.getBoolean("privacidad"));

                    UsuarioPreView userpv = new UsuarioPreView();
                    userpv.botonEliminarInvisible(false); // distinto segun el método
                    userpv.setChatear(false); // distinto segun el método
                    UsuarioPreViewController userPvController = new UsuarioPreViewController(userpv,usuario,conexion,user,null, pview);
                    listauvC.add(userPvController);

                    userpv.controlador(userPvController);

                    MiMouseListener listener = new MiMouseListener(0,3, usuario.getNombre(),this, null, null);
                    userpv.getPanel().addMouseListener(listener);
                    if(usuario.getPrivacidad())
                        userpv.añadirCandado();
                    else
                        userpv.quitarCandado();

                    ppView.addListaUser(userpv.getPanel());
                }

                ppView.setViewportViewScrollUser(ppView.getListaUsers());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else if(idList==-2){ // obtener peliculas
            ppView.removeAllListas();
            try {
                ResultSet count= conexion.executeQuery("SELECT COUNT(*) FROM ContenidoMultimedia join Pelicula on ContenidoMultimedia.idContenidoMultimedia=Pelicula.idContenidoMultimedia;");
                count.next();
                int cont=count.getInt(1);

                ResultSet peli;
                // VARÍA EN FUNCIÓN DEL MÉTODO SOLICITADO
                if (metodo.equals("subs")) {
                    peli = conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Pelicula on ContenidoMultimedia.idContenidoMultimedia=Pelicula.idContenidoMultimedia WHERE nombre LIKE '%" + s + "%';");
                } else if (metodo.equals("ord")) {
                    peli= conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Pelicula on ContenidoMultimedia.idContenidoMultimedia=Pelicula.idContenidoMultimedia WHERE nombre LIKE '%"+ s + "%' ORDER BY nombre;");
                } else {
                    peli= conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Pelicula on ContenidoMultimedia.idContenidoMultimedia=Pelicula.idContenidoMultimedia;");
                }

                ppView.setLayoutListas(cont);

                // Creo lista para almacenar los idContenidoMultimedia y referencias de pelipv
                ArrayList<Integer> listaids = new ArrayList<>();
                ArrayList<PeliculaPreView> listapelipv = new ArrayList<>();
                while(peli.next()) {
                    // Necesitamos guardar las variables antes de hacer una nueva consulta,
                    // si no, se borra la informacion de la anterior consulta (Result Set)
                    listaids.add(peli.getInt("idContenidoMultimedia"));

                    Pelicula pelicula = new Pelicula(peli.getString("nombre"), peli.getInt("idContenidoMultimedia"), peli.getString("sinopsis"), peli.getString("genero"), 0);
                    PeliculaPreView pelipv = new PeliculaPreView();
                    PeliculaPreViewController peliPvController = new PeliculaPreViewController(ppView,pelipv,pelicula,user,conexion,ppView.getComboBox1());
                    listapvC.add(peliPvController);

                    pelipv.controlador(peliPvController);
                    listapelipv.add(pelipv);

                    MiMouseListener listener = new MiMouseListener(peli.getInt(1),1, "",this, pelipv, null);
                    pelipv.getPanel().addMouseListener(listener);
                    ppView.addListaPelis(pelipv.getPanel());
                }

                for (int i = 0; i < listaids.size(); i++) {
                    //Por cada peli generamos su valoracion media
                    ResultSet valmed = conexion.executeQuery("SELECT IFNULL(AVG(valoracion),0) FROM Valora WHERE idContenido=" + listaids.get(i) + ";");
                    valmed.next(); //apuntamos
                    listapelipv.get(i).setValoracion(valmed.getDouble(1));
                }

                ppView.setViewportViewScroll(ppView.getListaPelis());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else if(idList==-1){ // obtener series
            ppView.removeAllListasSerie();
            try {
                ResultSet count= conexion.executeQuery("SELECT COUNT(*) FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia;");
                count.next();
                int cont=count.getInt(1);

                ResultSet peli;
                // VARÍA EN FUNCIÓN DEL MÉTODO SOLICITADO
                if (metodo.equals("subs")) {
                    peli = conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia WHERE nombre LIKE '%" + s + "%';");
                } else if (metodo.equals("ord")) {
                    peli= conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia WHERE nombre LIKE '%" + s + "%' ORDER BY nombre;");
                } else {
                    peli= conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia;");
                }

                ppView.setLayoutListasSerie(cont);

                // Creo lista para almacenar los idContenidoMultimedia y referencias de seriepv
                ArrayList<Integer> listaids = new ArrayList<>();
                ArrayList<SeriePreView> listaseriepv = new ArrayList<>();
                while(peli.next()) {
                    // Necesitamos guardar las variables antes de hacer una nueva consulta,
                    // si no, se borra la informacion de la anterior consulta (Result Set)
                    listaids.add(peli.getInt("idContenidoMultimedia"));

                    Serie serie=new Serie(peli.getInt("idContenidoMultimedia"),peli.getString("nombre"), peli.getString("sinopsis"),peli.getString("reparto"),0,peli.getString("fecha_estreno"),peli.getString("Genero"),peli.getString("premios"),0,peli.getString("trailer"),peli.getInt("veces_añadidas"),0,false,peli.getInt("numCapitulos"),peli.getInt("numTemporadas"),peli.getDouble("duracionMedia"));
                    SeriePreView seriepv = new SeriePreView();
                    SeriePreviewController seriepvC = new SeriePreviewController(user,serie,seriepv,ppView.getComboBox1(),conexion);
                    listasvC.add(seriepvC);

                    seriepv.controlador(seriepvC);
                    listaseriepv.add(seriepv);

                    MiMouseListener listener = new MiMouseListener(peli.getInt(1), 2, "",this,null, seriepv);
                    seriepv.getPanel().addMouseListener(listener);

                    ppView.addListaSerie(seriepv.getPanel());
                }

                for (int i = 0; i < listaids.size(); i++) {
                    //Por cada serie generamos su valoracion media
                    ResultSet valmed = conexion.executeQuery("SELECT IFNULL(AVG(valoracion),0) FROM Valora WHERE idContenido=" + listaids.get(i) + ";");
                    valmed.next(); //apuntamos
                    listaseriepv.get(i).setValoracion(valmed.getDouble(1));
                }

                ppView.setViewportViewScrollSerie(ppView.getListaSeries());

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else { // listas personales

            ppView.removeAllContenido();

            ResultSet count3=null;
            try {
                ResultSet count = conexion.executeQuery("SELECT COUNT(*) FROM ContenidoMultimedia join AñadirContenido on AñadirContenido.idContenidoMultimedia=ContenidoMultimedia.idContenidoMultimedia && AñadirContenido.idLista="+idList+";");
                count.next();
                int cont=count.getInt(1);
                if(cont!=0) {

                    // VARÍA EN FUNCIÓN DEL MÉTODO SOLICITADO
                    if (metodo.equals("subs")) {
                        count = conexion1.executeQuery("SELECT * FROM ContenidoMultimedia join AñadirContenido on AñadirContenido.idContenidoMultimedia=ContenidoMultimedia.idContenidoMultimedia && AñadirContenido.idLista=" + idList + " WHERE nombre LIKE '%" + s + "%';");
                    } else if (metodo.equals("ord")) {
                        count = conexion1.executeQuery("SELECT * FROM ContenidoMultimedia join AñadirContenido on AñadirContenido.idContenidoMultimedia=ContenidoMultimedia.idContenidoMultimedia && AñadirContenido.idLista=" + idList + " WHERE nombre LIKE '%"+ s + "%' ORDER BY nombre;");
                    } else {
                        count = conexion1.executeQuery("SELECT * FROM ContenidoMultimedia join AñadirContenido on AñadirContenido.idContenidoMultimedia=ContenidoMultimedia.idContenidoMultimedia && AñadirContenido.idLista=" + idList + ";");
                    }

                    ppView.setLayoutListasContenido(cont);

                    // Creo listas para almacenar los idContenidoMultimedia y referencias de pelipv y seriepv
                    ArrayList<Integer> listaidspelis = new ArrayList<>();
                    ArrayList<Integer> listaidsseries = new ArrayList<>();
                    ArrayList<PeliculaPreView> listapelipv = new ArrayList<>();
                    ArrayList<SeriePreView> listaseriepv = new ArrayList<>();

                    while (count.next()) {
                        int id = count.getInt("idContenidoMultimedia");
                        // Necesitamos guardar las variables antes de hacer una nueva consulta,
                        // si no, se borra la informacion de la anterior consulta (Result Set)

                        ResultSet count2 = conexion.executeQuery("SELECT COUNT(*) FROM Serie WHERE idContenidoMultimedia=" + id + ";");
                        count2.next();
                        int numSerie = count2.getInt("COUNT(*)");

                        if (numSerie == 1) { // es serie
                            listaidsseries.add(id);

                            count3 = conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia && Serie.idContenidoMultimedia=" + id + ";");
                            count3.next();
                            Serie serie=new Serie(count3.getInt("idContenidoMultimedia"),count3.getString("nombre"), count3.getString("sinopsis"),count3.getString("reparto"),0,count3.getString("fecha_estreno"),count3.getString("Genero"),count3.getString("premios"),0,count3.getString("trailer"),count3.getInt("veces_añadidas"),0,false,count3.getInt("numCapitulos"),count3.getInt("numTemporadas"),count3.getDouble("duracionMedia"));

                            SeriePreView seriepv = new SeriePreView();
                            SeriePreviewController seriepvC = new SeriePreviewController(user, serie, seriepv, ppView.getComboBox1(), conexion);
                            seriepv.controlador(seriepvC);
                            listasSyPC.add(seriepvC);
                            listaseriepv.add(seriepv);

                            MiMouseListener listener = new MiMouseListener(count3.getInt(1), 2, "",this,null, seriepv);
                            seriepv.getPanel().addMouseListener(listener);

                            ppView.addListaContenido(seriepv.getPanel());
                        } else { // es pelicula

                            listaidspelis.add(id);
                            count3 = conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Pelicula on ContenidoMultimedia.idContenidoMultimedia=Pelicula.idContenidoMultimedia && Pelicula.idContenidoMultimedia=" + id + ";");
                            count3.next();
                            Pelicula pelicula = new Pelicula(count3.getString("nombre"), count3.getInt("idContenidoMultimedia"), count3.getString("sinopsis"), count3.getString("genero"), 0, count3.getInt("veces_añadidas"));

                            PeliculaPreView pelipv = new PeliculaPreView();
                            PeliculaPreViewController peliPvController = new PeliculaPreViewController(ppView, pelipv, pelicula, user, conexion, ppView.getComboBox1());
                            pelipv.controlador(peliPvController);
                            listasSyPC.add(peliPvController);
                            listapelipv.add(pelipv);

                            MiMouseListener listener = new MiMouseListener(count3.getInt(1), 1, "",this,pelipv, null);
                            pelipv.getPanel().addMouseListener(listener);

                            ppView.addListaContenido(pelipv.getPanel());
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
                ppView.setViewportViewScrollContenido(ppView.getListaContenido());
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
            ResultSet rs = conexion2.executeQuery("SELECT * FROM Lista where Nombreusuario = '"+user.getNombre()+"';");
            while(rs.next()){
                int id=rs.getInt(1);
                String nombre=rs.getString(2);
                Date fecha=rs.getDate(3);

                actual=new Lista(id,nombre,fecha,conexion);
                listaActualizada.add(actual);
                ppView.addItemComboBox1(actual);
            }
            user.setListasPersonales(listaActualizada);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        setListaPreViews();
    }


    public void setListaPreViews() {
        for(PeliculaPreViewController l: listapvC){
            l.actualizarComboBox(ppView.getComboBox1());
        }
        for(SeriePreviewController l: listasvC){
            l.actualizarComboBox(ppView.getComboBox1());
        }
        setListaPreViewsContenido();
    }

    public void setListaPreViewsContenido() {
        for(ContenidoMultimediaPreViewController l:listasSyPC){
            l.actualizarComboBox(ppView.getComboBox1());
        }
    }

    public class MiMouseListener extends MouseAdapter {
        int id, tipo;
        String nombre;
        PrincipalController pController;
        PeliculaPreView ppview;
        SeriePreView spview;

        public MiMouseListener(int anInt, int tipo, String n, PrincipalController pc, PeliculaPreView peliculaPreView, SeriePreView spv) {
            pController = pc;
            this.tipo=tipo;
            id=anInt;
            nombre = n;
            ppview=peliculaPreView;
            spview=spv;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                if(tipo==1) {
                    ResultSet resst = conexion.executeQuery("SELECT * FROM ContenidoMultimedia, Pelicula where ContenidoMultimedia.idContenidoMultimedia=" + id + ";");
                    resst.next();
                    String fechaEstreno = resst.getString("fecha_estreno");
                    Pelicula pelicula = new Pelicula(resst.getInt("idContenidoMultimedia"),resst.getString("nombre"), 0, fechaEstreno, resst.getInt("duracion"), resst.getString("genero"), resst.getString("sinopsis"), resst.getString("reparto"),resst.getInt("veces_añadidas"));

                    // aun falta la valoración para que esté creada completa la pelicula. Lo obtenemos:
                    ResultSet valmed = conexion.executeQuery("SELECT IFNULL(AVG(valoracion),0) FROM Valora WHERE idContenido=" + id + ";");
                    valmed.next(); //apuntamos
                    pelicula.setValoracion(valmed.getDouble(1));
                    // ahora está lista la pelicula

                    PeliculaView peliview = new PeliculaView();
                    PeliculaController peliculaController = new PeliculaController(peliview, user, conexion, pelicula, ppView, id, fechaEstreno, ppview);
                    peliview.controlador(peliculaController);
                    Main.frame.setContentPane(peliview.getPanel());
                    Main.frame.setVisible(true);

                }else if(tipo==2){

                    ResultSet resst = conexion.executeQuery("SELECT * FROM ContenidoMultimedia, Serie where ContenidoMultimedia.idContenidoMultimedia="+id+";");
                    resst.next();
                    String fechaEstreno=resst.getString("fecha_estreno");
                    Serie serie=new Serie(resst.getInt("idContenidoMultimedia"),resst.getString("nombre"), resst.getString("sinopsis"),resst.getString("reparto"),0,resst.getString("fecha_estreno"),resst.getString("Genero"),resst.getString("premios"),0,resst.getString("trailer"),resst.getInt("veces_añadidas"),0,false,resst.getInt("numCapitulos"),resst.getInt("numTemporadas"),resst.getDouble("duracionMedia"));

                    // aun falta la valoración para que esté creada completa la serie. Lo obtenemos:
                    ResultSet valmed = conexion.executeQuery("SELECT IFNULL(AVG(valoracion),0) FROM Valora WHERE idContenido=" + id + ";");
                    valmed.next(); //apuntamos
                    serie.setValoracion(valmed.getDouble(1));
                    // ahora está lista la serie

                    SerieView sv = new SerieView();
                    SerieController sc=new SerieController(ppView,sv,serie,conexion,id,user,spview);
                    sv.controlador(sc);
                    Main.frame.setContentPane(sv.getPanel());
                    Main.frame.setVisible(true);

                } else if (tipo == 3) {
                    ResultSet resst3 = conexion.executeQuery("SELECT COUNT(*) FROM Amigo where usuario1='"+nombre+"' and usuario2='"+user.getNombre()+"';");
                    resst3.next();
                    int cont=resst3.getInt(1);
                    boolean amigos=false;
                    if(cont>0){
                        ResultSet resst2 = conexion.executeQuery("SELECT * FROM Amigo where usuario1='"+nombre+"' and usuario2='"+user.getNombre()+"';");
                        resst2.next();
                        amigos = resst2.getBoolean("isAmigo");
                    }

                    ResultSet resst = conexion.executeQuery("SELECT * FROM Usuario where nombre='"+nombre+"';");
                    resst.next();
                    if(!resst.getBoolean("privacidad") || amigos){
                        Usuario usuario = new Usuario(resst.getString("nombre"), resst.getString("email"), resst.getString("descripcion"),
                                resst.getDate("fechaNacimiento"), resst.getDate("fechaCreacion"), resst.getString("contraseña"),
                                resst.getInt("numListas"), resst.getInt("numAmigos"), resst.getBoolean("privacidad"),
                                resst.getInt("numComentarios"), resst.getInt("numSeriesVistas"), resst.getInt("numEpisodiosVistos"),
                                resst.getInt("numPeliculasVistas"));
                        ResultSet listas = conexion.executeQuery("SELECT * FROM Lista WHERE Nombreusuario = '" + usuario.getNombre() +"';");
                        List<Lista> lista = new ArrayList<>();
                        while(listas.next()){
                            lista.add(new Lista(listas.getInt("ID"), listas.getString("nombre"), listas.getDate("fechaCreacion"), conexion));
                        }
                        usuario.setListasPersonales(lista);
                        OtherUserView ouv = new OtherUserView();
                        OtherUserController ouc = new OtherUserController(pController, ouv, ppView, conexion, usuario, user);
                        ouv.controlador(ouc);
                        Main.frame.setContentPane(ouv.getPanel());
                        Main.frame.setVisible(true);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }



    }


        /* DESACTUALIZADO
    public void añadirContenido(int idList){ // el inicial

        if(idList==-3) { // obtener usuarios
            ppView.removeAlllistasUsers();
            try {
                ResultSet count= conexion.executeQuery("SELECT COUNT(*) FROM Usuario WHERE nombre <> '" + user.getNombre() +"';");
                count.next();
                int cont=count.getInt(1);
                ResultSet users= conexion.executeQuery("SELECT * FROM Usuario WHERE nombre <> '" + user.getNombre() +"';");
                ppView.setLayoutListasUsers(cont);

                while(users.next()) {

                    ProfileView pview = new ProfileView(conexion);
                    ProfileController pc = new ProfileController(this,pview,ppView,lview,conexion,conexion1,user);
                    pview.controlador(pc);

                    Usuario usuario = new Usuario(users.getString("nombre"), users.getString("email"), users.getString("contraseña"),users.getString("descripcion"));

                    UsuarioPreView userpv = new UsuarioPreView();
                    userpv.botonEliminarInvisible(false);
                    userpv.setChatear(false);
                    UsuarioPreViewController userPvController = new UsuarioPreViewController(userpv,usuario,conexion,user,null, pview);
                    listauvC.add(userPvController);

                    userpv.controlador(userPvController);

                    MiMouseListener listener = new MiMouseListener(0,3, usuario.getNombre(),this, null, null);
                    userpv.getPanel().addMouseListener(listener);
                    ppView.addListaUser(userpv.getPanel());
                }

                ppView.setViewportViewScrollUser(ppView.getListaUsers());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else if(idList==-2){ // obtener peliculas
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
                    // si no, se borra la informacion de la anterior consulta (Result Set)
                    listaids.add(peli.getInt("idContenidoMultimedia"));

                    Pelicula pelicula = new Pelicula(peli.getString("nombre"), peli.getInt("imagen"), peli.getString("sinopsis"), peli.getString("genero"), 0);
                    PeliculaPreView pelipv = new PeliculaPreView();
                    PeliculaPreViewController peliPvController = new PeliculaPreViewController(ppView,pelipv,pelicula,user,conexion,ppView.getComboBox1());
                    listapvC.add(peliPvController);

                    pelipv.controlador(peliPvController);
                    listapelipv.add(pelipv);

                    MiMouseListener listener = new MiMouseListener(peli.getInt(1),1, "",this, pelipv, null);
                    pelipv.getPanel().addMouseListener(listener);
                    ppView.addListaPelis(pelipv.getPanel());
                }

                for (int i = 0; i < listaids.size(); i++) {
                    //Por cada peli generamos su valoracion media
                    ResultSet valmed = conexion.executeQuery("SELECT IFNULL(AVG(valoracion),0) FROM Valora WHERE idContenido=" + listaids.get(i) + ";");
                    valmed.next(); //apuntamos
                    listapelipv.get(i).setValoracion(valmed.getDouble(1));
                }

                ppView.setViewportViewScroll(ppView.getListaPelis());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else if(idList==-1){ // obtener series
            ppView.removeAllListasSerie();
            try {
                ResultSet count= conexion.executeQuery("SELECT COUNT(*) FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia;");
                count.next();
                int cont=count.getInt(1);

                ResultSet peli= conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia;");
                ppView.setLayoutListasSerie(cont);

                // Creo lista para almacenar los idContenidoMultimedia y referencias de seriepv
                ArrayList<Integer> listaids = new ArrayList<>();
                ArrayList<SeriePreView> listaseriepv = new ArrayList<>();
                while(peli.next()) {
                    // Necesitamos guardar las variables antes de hacer una nueva consulta,
                    // si no, se borra la informacion de la anterior consulta (Result Set)
                    listaids.add(peli.getInt("idContenidoMultimedia"));

                    Serie serie=new Serie(peli.getInt("idContenidoMultimedia"),peli.getString("nombre"), peli.getString("sinopsis"),peli.getString("reparto"),0,peli.getString("fecha_estreno"),peli.getString("Genero"),peli.getString("premios"),0,peli.getString("trailer"),peli.getInt("veces_añadidas"),0,false,peli.getInt("numCapitulos"),peli.getInt("numTemporadas"),peli.getDouble("duracionMedia"));
                    SeriePreView seriepv = new SeriePreView();
                    SeriePreviewController seriepvC = new SeriePreviewController(user,serie,seriepv,ppView.getComboBox1(),conexion);
                    listasvC.add(seriepvC);

                    seriepv.controlador(seriepvC);
                    listaseriepv.add(seriepv);

                    MiMouseListener listener = new MiMouseListener(peli.getInt(1), 2, "",this,null, seriepv);
                    seriepv.getPanel().addMouseListener(listener);

                    ppView.addListaSerie(seriepv.getPanel());
                }

                for (int i = 0; i < listaids.size(); i++) {
                    //Por cada serie generamos su valoracion media
                    ResultSet valmed = conexion.executeQuery("SELECT IFNULL(AVG(valoracion),0) FROM Valora WHERE idContenido=" + listaids.get(i) + ";");
                    valmed.next(); //apuntamos
                    listaseriepv.get(i).setValoracion(valmed.getDouble(1));
                }

                ppView.setViewportViewScrollSerie(ppView.getListaSeries());


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else { // listas personales

            ppView.removeAllContenido();

            ResultSet count3=null;
            try {
                ResultSet count = conexion.executeQuery("SELECT COUNT(*) FROM ContenidoMultimedia join AñadirContenido on AñadirContenido.idContenidoMultimedia=ContenidoMultimedia.idContenidoMultimedia && AñadirContenido.idLista="+idList+";");
                count.next();
                int cont=count.getInt(1);
                if(cont!=0) {

                    count = conexion1.executeQuery("SELECT * FROM ContenidoMultimedia join AñadirContenido on AñadirContenido.idContenidoMultimedia=ContenidoMultimedia.idContenidoMultimedia && AñadirContenido.idLista=" + idList + ";");
                    ppView.setLayoutListasContenido(cont);

                    // Creo listas para almacenar los idContenidoMultimedia y referencias de pelipv y seriepv
                    ArrayList<Integer> listaidspelis = new ArrayList<>();
                    ArrayList<Integer> listaidsseries = new ArrayList<>();
                    ArrayList<PeliculaPreView> listapelipv = new ArrayList<>();
                    ArrayList<SeriePreView> listaseriepv = new ArrayList<>();
                    while (count.next()) {
                        int id = count.getInt("idContenidoMultimedia");
                        // Necesitamos guardar las variables antes de hacer una nueva consulta,
                        // si no, se borra la informacion de la anterior consulta (Result Set)

                        ResultSet count2 = conexion.executeQuery("SELECT COUNT(*) FROM Serie WHERE idContenidoMultimedia=" + id + ";");
                        count2.next();
                        int numSerie = count2.getInt("COUNT(*)");

                        if (numSerie == 1) { // es serie
                            listaidsseries.add(id);

                            count3 = conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia && Serie.idContenidoMultimedia=" + id + ";");
                            count3.next();
                            Serie serie=new Serie(count3.getInt("idContenidoMultimedia"),count3.getString("nombre"), count3.getString("sinopsis"),count3.getString("reparto"),0,count3.getString("fecha_estreno"),count3.getString("Genero"),count3.getString("premios"),0,count3.getString("trailer"),count3.getInt("veces_añadidas"),0,false,count3.getInt("numCapitulos"),count3.getInt("numTemporadas"),count3.getDouble("duracionMedia"));

                            SeriePreView seriepv = new SeriePreView();
                            SeriePreviewController seriepvC = new SeriePreviewController(user, serie, seriepv, ppView.getComboBox1(), conexion);
                            seriepv.controlador(seriepvC);
                            listasSyPC.add(seriepvC);
                            listaseriepv.add(seriepv);

                            MiMouseListener listener = new MiMouseListener(count3.getInt(1), 2, "",this,null, seriepv);
                            seriepv.getPanel().addMouseListener(listener);

                            ppView.addListaContenido(seriepv.getPanel());
                        } else { // es pelicula

                            listaidspelis.add(id);
                            count3 = conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Pelicula on ContenidoMultimedia.idContenidoMultimedia=Pelicula.idContenidoMultimedia && Pelicula.idContenidoMultimedia=" + id + ";");
                            count3.next();
                            Pelicula pelicula = new Pelicula(count3.getString("nombre"), count3.getInt("imagen"), count3.getString("sinopsis"), count3.getString("genero"), 0, count3.getInt("veces_añadidas"));

                            PeliculaPreView pelipv = new PeliculaPreView();
                            PeliculaPreViewController peliPvController = new PeliculaPreViewController(ppView, pelipv, pelicula, user, conexion, ppView.getComboBox1());
                            pelipv.controlador(peliPvController);
                            listasSyPC.add(peliPvController);
                            listapelipv.add(pelipv);

                            MiMouseListener listener = new MiMouseListener(count3.getInt(1), 1, "",this,pelipv, null);
                            pelipv.getPanel().addMouseListener(listener);

                            ppView.addListaContenido(pelipv.getPanel());
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

                    ppView.setViewportViewScrollContenido(ppView.getListaContenido());
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    public void añadirContenidoSubs(int idList,String s){

        if(idList==-3) { // obtener usuarios
            ppView.removeAlllistasUsers();
            try {
                ResultSet count= conexion.executeQuery("SELECT COUNT(*) FROM Usuario WHERE nombre <> '" + user.getNombre() +"';");
                count.next();
                int cont=count.getInt(1);
                ResultSet users= conexion.executeQuery("SELECT * FROM Usuario WHERE nombre <> '" + user.getNombre() +"' AND nombre LIKE '%" + s + "%';");
                ppView.setLayoutListasUsers(cont);

                while(users.next()) {

                    Usuario usuario = new Usuario(users.getString("nombre"), users.getString("email"), users.getString("contraseña"),users.getString("descripcion"));

                    UsuarioPreView userpv = new UsuarioPreView();
                    UsuarioPreViewController userPvController = new UsuarioPreViewController(userpv,usuario,conexion,user,null, pview);
                    listauvC.add(userPvController);

                    userpv.controlador(userPvController);

                    MiMouseListener listener = new MiMouseListener(0,3, usuario.getNombre(),this, null, null);
                    userpv.getPanel().addMouseListener(listener);
                    ppView.addListaUser(userpv.getPanel());
                }

                ppView.setViewportViewScrollUser(ppView.getListaUsers());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else if(idList==-2){ // obtener peliculas
            ppView.removeAllListas();
            try {
                ResultSet count= conexion.executeQuery("SELECT COUNT(*) FROM ContenidoMultimedia join Pelicula on ContenidoMultimedia.idContenidoMultimedia=Pelicula.idContenidoMultimedia;");
                count.next();
                int cont=count.getInt(1);
                ResultSet peli= conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Pelicula on ContenidoMultimedia.idContenidoMultimedia=Pelicula.idContenidoMultimedia WHERE nombre LIKE '%"+ s + "%';");
                ppView.setLayoutListas(cont);

                // Creo lista para almacenar los idContenidoMultimedia y referencias de pelipv
                ArrayList<Integer> listaids = new ArrayList<>();
                ArrayList<PeliculaPreView> listapelipv = new ArrayList<>();
                while(peli.next()) {
                    // Necesitamos guardar las variables antes de hacer una nueva consulta,
                    // si no, se borra la informacion de la anterior consulta (Result Set)
                    listaids.add(peli.getInt("idContenidoMultimedia"));

                    Pelicula pelicula = new Pelicula(peli.getString("nombre"), peli.getInt("imagen"), peli.getString("sinopsis"), peli.getString("genero"), 0);
                    PeliculaPreView pelipv = new PeliculaPreView();
                    PeliculaPreViewController peliPvController = new PeliculaPreViewController(ppView,pelipv,pelicula,user,conexion,ppView.getComboBox1());
                    listapvC.add(peliPvController);

                    pelipv.controlador(peliPvController);
                    listapelipv.add(pelipv);

                    MiMouseListener listener = new MiMouseListener(peli.getInt(1),1, "",this, pelipv, null);
                    pelipv.getPanel().addMouseListener(listener);
                    ppView.addListaPelis(pelipv.getPanel());
                }

                for (int i = 0; i < listaids.size(); i++) {
                    //Por cada peli generamos su valoracion media
                    ResultSet valmed = conexion.executeQuery("SELECT IFNULL(AVG(valoracion),0) FROM Valora WHERE idContenido=" + listaids.get(i) + ";");
                    valmed.next(); //apuntamos
                    listapelipv.get(i).setValoracion(valmed.getDouble(1));
                }

                ppView.setViewportViewScroll(ppView.getListaPelis());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else if(idList==-1){ // obtener series
            ppView.removeAllListasSerie();
            try {
                ResultSet count= conexion.executeQuery("SELECT COUNT(*) FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia;");
                count.next();
                int cont=count.getInt(1);

                ResultSet peli= conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia WHERE nombre LIKE '%" + s + "%';");
                ppView.setLayoutListasSerie(cont);

                // Creo lista para almacenar los idContenidoMultimedia y referencias de seriepv
                ArrayList<Integer> listaids = new ArrayList<>();
                ArrayList<SeriePreView> listaseriepv = new ArrayList<>();
                while(peli.next()) {
                    // Necesitamos guardar las variables antes de hacer una nueva consulta,
                    // si no, se borra la informacion de la anterior consulta (Result Set)
                    listaids.add(peli.getInt("idContenidoMultimedia"));

                    Serie serie=new Serie(peli.getInt("idContenidoMultimedia"),peli.getString("nombre"), peli.getString("sinopsis"),peli.getString("reparto"),0,peli.getString("fecha_estreno"),peli.getString("Genero"),peli.getString("premios"),0,peli.getString("trailer"),peli.getInt("veces_añadidas"),0,false,peli.getInt("numCapitulos"),peli.getInt("numTemporadas"),peli.getDouble("duracionMedia"));
                    SeriePreView seriepv = new SeriePreView();
                    SeriePreviewController seriepvC = new SeriePreviewController(user,serie,seriepv,ppView.getComboBox1(),conexion);
                    listasvC.add(seriepvC);

                    seriepv.controlador(seriepvC);
                    listaseriepv.add(seriepv);

                    MiMouseListener listener = new MiMouseListener(peli.getInt(1), 2, "",this,null, seriepv);
                    seriepv.getPanel().addMouseListener(listener);

                    ppView.addListaSerie(seriepv.getPanel());
                }

                for (int i = 0; i < listaids.size(); i++) {
                    //Por cada serie generamos su valoracion media
                    ResultSet valmed = conexion.executeQuery("SELECT IFNULL(AVG(valoracion),0) FROM Valora WHERE idContenido=" + listaids.get(i) + ";");
                    valmed.next(); //apuntamos
                    listaseriepv.get(i).setValoracion(valmed.getDouble(1));
                }

                ppView.setViewportViewScrollSerie(ppView.getListaSeries());


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else { // listas personales

            ppView.removeAllContenido();

            ResultSet count3=null;
            try {
                ResultSet count = conexion.executeQuery("SELECT COUNT(*) FROM ContenidoMultimedia join AñadirContenido on AñadirContenido.idContenidoMultimedia=ContenidoMultimedia.idContenidoMultimedia && AñadirContenido.idLista="+idList+";");
                count.next();
                int cont=count.getInt(1);
                if(cont!=0) {

                    count = conexion1.executeQuery("SELECT * FROM ContenidoMultimedia join AñadirContenido on AñadirContenido.idContenidoMultimedia=ContenidoMultimedia.idContenidoMultimedia && AñadirContenido.idLista=" + idList + " WHERE nombre LIKE '%"+ s + "%';");
                    ppView.setLayoutListasContenido(cont);

                    // Creo listas para almacenar los idContenidoMultimedia y referencias de pelipv y seriepv
                    ArrayList<Integer> listaidspelis = new ArrayList<>();
                    ArrayList<Integer> listaidsseries = new ArrayList<>();
                    ArrayList<PeliculaPreView> listapelipv = new ArrayList<>();
                    ArrayList<SeriePreView> listaseriepv = new ArrayList<>();
                    while (count.next()) {
                        int id = count.getInt("idContenidoMultimedia");
                        // Necesitamos guardar las variables antes de hacer una nueva consulta,
                        // si no, se borra la informacion de la anterior consulta (Result Set)

                        ResultSet count2 = conexion.executeQuery("SELECT COUNT(*) FROM Serie WHERE idContenidoMultimedia=" + id + ";");
                        count2.next();
                        int numSerie = count2.getInt("COUNT(*)");

                        if (numSerie == 1) { // es serie
                            listaidsseries.add(id);

                            count3 = conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia && Serie.idContenidoMultimedia=" + id + ";");
                            count3.next();
                            Serie serie=new Serie(count3.getInt("idContenidoMultimedia"),count3.getString("nombre"), count3.getString("sinopsis"),count3.getString("reparto"),0,count3.getString("fecha_estreno"),count3.getString("Genero"),count3.getString("premios"),0,count3.getString("trailer"),count3.getInt("veces_añadidas"),0,false,count3.getInt("numCapitulos"),count3.getInt("numTemporadas"),count3.getDouble("duracionMedia"));

                            SeriePreView seriepv = new SeriePreView();
                            SeriePreviewController seriepvC = new SeriePreviewController(user, serie, seriepv, ppView.getComboBox1(), conexion);
                            seriepv.controlador(seriepvC);
                            listasSyPC.add(seriepvC);
                            listaseriepv.add(seriepv);

                            MiMouseListener listener = new MiMouseListener(count3.getInt(1), 2, "",this,null, seriepv);
                            seriepv.getPanel().addMouseListener(listener);

                            ppView.addListaContenido(seriepv.getPanel());
                        } else { // es pelicula

                            listaidspelis.add(id);
                            count3 = conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Pelicula on ContenidoMultimedia.idContenidoMultimedia=Pelicula.idContenidoMultimedia && Pelicula.idContenidoMultimedia=" + id + ";");
                            count3.next();
                            Pelicula pelicula = new Pelicula(count3.getString("nombre"), count3.getInt("imagen"), count3.getString("sinopsis"), count3.getString("genero"), 0, count3.getInt("veces_añadidas"));

                            PeliculaPreView pelipv = new PeliculaPreView();
                            PeliculaPreViewController peliPvController = new PeliculaPreViewController(ppView, pelipv, pelicula, user, conexion, ppView.getComboBox1());
                            pelipv.controlador(peliPvController);
                            listasSyPC.add(peliPvController);
                            listapelipv.add(pelipv);

                            MiMouseListener listener = new MiMouseListener(count3.getInt(1), 1, "",this,pelipv, null);
                            pelipv.getPanel().addMouseListener(listener);

                            ppView.addListaContenido(pelipv.getPanel());
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

                    ppView.setViewportViewScrollContenido(ppView.getListaContenido());
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void añadirContenidoOrd(int idList,String s){

        if(idList==-3) { // obtener usuarios
            ppView.removeAlllistasUsers();
            try {
                ResultSet count= conexion.executeQuery("SELECT COUNT(*) FROM Usuario WHERE nombre <> '" + user.getNombre() +"';");
                count.next();
                int cont=count.getInt(1);
                ResultSet users= conexion.executeQuery("SELECT * FROM Usuario WHERE nombre <> '" + user.getNombre() +"' AND nombre LIKE '%" + s + "%' ORDER BY nombre;");
                ppView.setLayoutListasUsers(cont);

                while(users.next()) {

                    Usuario usuario = new Usuario(users.getString("nombre"), users.getString("email"), users.getString("contraseña"),users.getString("descripcion"));

                    UsuarioPreView userpv = new UsuarioPreView();
                    UsuarioPreViewController userPvController = new UsuarioPreViewController(userpv,usuario,conexion,user,null, pview);
                    listauvC.add(userPvController);

                    userpv.controlador(userPvController);

                    MiMouseListener listener = new MiMouseListener(0,3, usuario.getNombre(),this, null, null);
                    userpv.getPanel().addMouseListener(listener);
                    ppView.addListaUser(userpv.getPanel());
                }

                ppView.setViewportViewScrollUser(ppView.getListaUsers());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else if(idList==-2){ // obtener peliculas
            ppView.removeAllListas();
            try {
                ResultSet count= conexion.executeQuery("SELECT COUNT(*) FROM ContenidoMultimedia join Pelicula on ContenidoMultimedia.idContenidoMultimedia=Pelicula.idContenidoMultimedia;");
                count.next();
                int cont=count.getInt(1);
                ResultSet peli= conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Pelicula on ContenidoMultimedia.idContenidoMultimedia=Pelicula.idContenidoMultimedia WHERE nombre LIKE '%"+ s + "%' ORDER BY nombre;");
                ppView.setLayoutListas(cont);

                // Creo lista para almacenar los idContenidoMultimedia y referencias de pelipv
                ArrayList<Integer> listaids = new ArrayList<>();
                ArrayList<PeliculaPreView> listapelipv = new ArrayList<>();
                while(peli.next()) {
                    // Necesitamos guardar las variables antes de hacer una nueva consulta,
                    // si no, se borra la informacion de la anterior consulta (Result Set)
                    listaids.add(peli.getInt("idContenidoMultimedia"));

                    Pelicula pelicula = new Pelicula(peli.getString("nombre"), peli.getInt("imagen"), peli.getString("sinopsis"), peli.getString("genero"), 0);
                    PeliculaPreView pelipv = new PeliculaPreView();
                    PeliculaPreViewController peliPvController = new PeliculaPreViewController(ppView,pelipv,pelicula,user,conexion,ppView.getComboBox1());
                    listapvC.add(peliPvController);

                    pelipv.controlador(peliPvController);
                    listapelipv.add(pelipv);

                    MiMouseListener listener = new MiMouseListener(peli.getInt(1),1, "",this, pelipv, null);
                    pelipv.getPanel().addMouseListener(listener);
                    ppView.addListaPelis(pelipv.getPanel());
                }

                for (int i = 0; i < listaids.size(); i++) {
                    //Por cada peli generamos su valoracion media
                    ResultSet valmed = conexion.executeQuery("SELECT IFNULL(AVG(valoracion),0) FROM Valora WHERE idContenido=" + listaids.get(i) + ";");
                    valmed.next(); //apuntamos
                    listapelipv.get(i).setValoracion(valmed.getDouble(1));
                }

                ppView.setViewportViewScroll(ppView.getListaPelis());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else if(idList==-1){ // obtener series
            ppView.removeAllListasSerie();
            try {
                ResultSet count= conexion.executeQuery("SELECT COUNT(*) FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia;");
                count.next();
                int cont=count.getInt(1);

                ResultSet peli= conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia WHERE nombre LIKE '%" + s + "%' ORDER BY nombre;");
                ppView.setLayoutListasSerie(cont);

                // Creo lista para almacenar los idContenidoMultimedia y referencias de seriepv
                ArrayList<Integer> listaids = new ArrayList<>();
                ArrayList<SeriePreView> listaseriepv = new ArrayList<>();
                while(peli.next()) {
                    // Necesitamos guardar las variables antes de hacer una nueva consulta,
                    // si no, se borra la informacion de la anterior consulta (Result Set)
                    listaids.add(peli.getInt("idContenidoMultimedia"));

                    Serie serie=new Serie(peli.getInt("idContenidoMultimedia"),peli.getString("nombre"), peli.getString("sinopsis"),peli.getString("reparto"),0,peli.getString("fecha_estreno"),peli.getString("Genero"),peli.getString("premios"),0,peli.getString("trailer"),peli.getInt("veces_añadidas"),0,false,peli.getInt("numCapitulos"),peli.getInt("numTemporadas"),peli.getDouble("duracionMedia"));
                    SeriePreView seriepv = new SeriePreView();
                    SeriePreviewController seriepvC = new SeriePreviewController(user,serie,seriepv,ppView.getComboBox1(),conexion);
                    listasvC.add(seriepvC);

                    seriepv.controlador(seriepvC);
                    listaseriepv.add(seriepv);

                    MiMouseListener listener = new MiMouseListener(peli.getInt(1), 2, "",this,null, seriepv);
                    seriepv.getPanel().addMouseListener(listener);

                    ppView.addListaSerie(seriepv.getPanel());
                }

                for (int i = 0; i < listaids.size(); i++) {
                    //Por cada serie generamos su valoracion media
                    ResultSet valmed = conexion.executeQuery("SELECT IFNULL(AVG(valoracion),0) FROM Valora WHERE idContenido=" + listaids.get(i) + ";");
                    valmed.next(); //apuntamos
                    listaseriepv.get(i).setValoracion(valmed.getDouble(1));
                }

                ppView.setViewportViewScrollSerie(ppView.getListaSeries());


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else { // listas personales

            ppView.removeAllContenido();

            ResultSet count3=null;
            try {
                ResultSet count = conexion.executeQuery("SELECT COUNT(*) FROM ContenidoMultimedia join AñadirContenido on AñadirContenido.idContenidoMultimedia=ContenidoMultimedia.idContenidoMultimedia && AñadirContenido.idLista="+idList+";");
                count.next();
                int cont=count.getInt(1);
                if(cont!=0) {

                    count = conexion1.executeQuery("SELECT * FROM ContenidoMultimedia join AñadirContenido on AñadirContenido.idContenidoMultimedia=ContenidoMultimedia.idContenidoMultimedia && AñadirContenido.idLista=" + idList + " WHERE nombre LIKE '%"+ s + "%' ORDER BY nombre;");
                    ppView.setLayoutListasContenido(cont);

                    // Creo listas para almacenar los idContenidoMultimedia y referencias de pelipv y seriepv
                    ArrayList<Integer> listaidspelis = new ArrayList<>();
                    ArrayList<Integer> listaidsseries = new ArrayList<>();
                    ArrayList<PeliculaPreView> listapelipv = new ArrayList<>();
                    ArrayList<SeriePreView> listaseriepv = new ArrayList<>();
                    while (count.next()) {
                        int id = count.getInt("idContenidoMultimedia");
                        // Necesitamos guardar las variables antes de hacer una nueva consulta,
                        // si no, se borra la informacion de la anterior consulta (Result Set)

                        ResultSet count2 = conexion.executeQuery("SELECT COUNT(*) FROM Serie WHERE idContenidoMultimedia=" + id + ";");
                        count2.next();
                        int numSerie = count2.getInt("COUNT(*)");

                        if (numSerie == 1) { // es serie
                            listaidsseries.add(id);

                            count3 = conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia && Serie.idContenidoMultimedia=" + id + ";");
                            count3.next();
                            Serie serie=new Serie(count3.getInt("idContenidoMultimedia"),count3.getString("nombre"), count3.getString("sinopsis"),count3.getString("reparto"),0,count3.getString("fecha_estreno"),count3.getString("Genero"),count3.getString("premios"),0,count3.getString("trailer"),count3.getInt("veces_añadidas"),0,false,count3.getInt("numCapitulos"),count3.getInt("numTemporadas"),count3.getDouble("duracionMedia"));

                            SeriePreView seriepv = new SeriePreView();
                            SeriePreviewController seriepvC = new SeriePreviewController(user, serie, seriepv, ppView.getComboBox1(), conexion);
                            seriepv.controlador(seriepvC);
                            listasSyPC.add(seriepvC);
                            listaseriepv.add(seriepv);

                            MiMouseListener listener = new MiMouseListener(count3.getInt(1), 2, "",this,null, seriepv);
                            seriepv.getPanel().addMouseListener(listener);

                            ppView.addListaContenido(seriepv.getPanel());
                        } else { // es pelicula

                            listaidspelis.add(id);
                            count3 = conexion.executeQuery("SELECT * FROM ContenidoMultimedia join Pelicula on ContenidoMultimedia.idContenidoMultimedia=Pelicula.idContenidoMultimedia && Pelicula.idContenidoMultimedia=" + id + ";");
                            count3.next();
                            Pelicula pelicula = new Pelicula(count3.getString("nombre"), count3.getInt("imagen"), count3.getString("sinopsis"), count3.getString("genero"), 0, count3.getInt("veces_añadidas"));

                            PeliculaPreView pelipv = new PeliculaPreView();
                            PeliculaPreViewController peliPvController = new PeliculaPreViewController(ppView, pelipv, pelicula, user, conexion, ppView.getComboBox1());
                            pelipv.controlador(peliPvController);
                            listasSyPC.add(peliPvController);
                            listapelipv.add(pelipv);

                            MiMouseListener listener = new MiMouseListener(count3.getInt(1), 1, "",this,pelipv, null);
                            pelipv.getPanel().addMouseListener(listener);

                            ppView.addListaContenido(pelipv.getPanel());
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

                    ppView.setViewportViewScrollContenido(ppView.getListaContenido());
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    */

}

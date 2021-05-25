package prSwishMedia.Views;

import com.mysql.jdbc.Statement;
import prSwishMedia.Lista;
import prSwishMedia.Listeners.MouseClick;
import prSwishMedia.Main;
import prSwishMedia.Pelicula;
import prSwishMedia.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class PrincipalView extends JFrame{
    private JPanel panel1;
    private JButton Perfil;
    private JTabbedPane tabbedPane1;
    private JPanel Películas;
    private JPanel Series;
    private JComboBox<Lista> comboBox1;
    private JPanel Listas;
    private JLabel Logo;
    private JPanel Usuarios;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton Buscar;
    private JButton BuscarS;
    private JButton BuscarL;
    private JButton BuscarU;
    private JScrollPane Pelis;
    private JScrollPane SeriesPanel;
    private JPanel listaPelis;
    private JPanel listaSeries;
    private Usuario user;
    private Statement st;
    private MouseListener listener;

    public PrincipalView(Statement st){
        user = Main.getUser();
        this.st = st;
        listaPelis=new JPanel();
        listaSeries=new JPanel();
        actualizarComboBox();
    }

    public void controlador(ActionListener ctr){
        Perfil.addActionListener(ctr);


        Perfil.setActionCommand("PROFILE");
    }

    public void añadirContenidoPelicula(int idList){
        listaPelis.removeAll();
        if(idList==-2){

            try {
                ResultSet count= st.executeQuery("SELECT COUNT(*) FROM ContenidoMultimedia join Pelicula on ContenidoMultimedia.idContenidoMultimedia=Pelicula.idContenidoMultimedia;");
                count.next();
                int cont=count.getInt(1);
                ResultSet peli= st.executeQuery("SELECT * FROM ContenidoMultimedia join Pelicula on ContenidoMultimedia.idContenidoMultimedia=Pelicula.idContenidoMultimedia;");
                listaPelis.setLayout(new GridLayout(cont, 0, 0, 0));
                while(peli.next()) {
                    PeliculaPreView pelipv = new PeliculaPreView(peli.getString("nombre"), peli.getInt("imagen"), peli.getString("sinopsis"), peli.getString("genero"), 0, comboBox1);
                    listener = new MiMouseListener();
                    pelipv.getPanel().addMouseListener(listener);
                    listaPelis.add(pelipv.getPanel());
                }
                Pelis.setViewportView(listaPelis);


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

    }
    public void añadirContenidoSerie(int idList){
        listaSeries.removeAll();
        if(idList==-2){

            try {
                ResultSet count= st.executeQuery("SELECT COUNT(*) FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia;");
                count.next();
                int cont=count.getInt(1);
                ResultSet peli= st.executeQuery("SELECT * FROM ContenidoMultimedia join Serie on ContenidoMultimedia.idContenidoMultimedia=Serie.idContenidoMultimedia;");
                listaSeries.setLayout(new GridLayout(cont, 0, 0, 0));
                while(peli.next()) {
                    SeriePreView seriepv = new SeriePreView(peli.getString("nombre"), peli.getInt("imagen"), peli.getString("sinopsis"), 0, comboBox1, peli.getInt("numTemporadas"));
                    listener = new MiMouseListener();
                    seriepv.getPanel().addMouseListener(listener);
                    listaSeries.add(seriepv.getPanel());
                }
                SeriesPanel.setViewportView(listaSeries);


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

    }


    public JPanel getPanel() {
        return panel1;
    }

    public void actualizarComboBox() {
        comboBox1.removeAll();

        if(user.getListasPersonales()!=null){
            for(Lista l: user.getListasPersonales()){
                comboBox1.addItem(l);
            }
        }else {
            System.out.println("LISTA VACIA");
        }
    }

    public void setUser(Usuario u){
        user = u;
        ArrayList<Lista> listaActualizada = new ArrayList<>();
        Lista actual;
        
        comboBox1.removeAllItems();
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM Lista where Nombreusuario = '"+user.getNombre()+"';");
            while(rs.next()){
                actual=new Lista(rs.getInt(1),rs.getString(2),rs.getDate(3));
                listaActualizada.add(actual);
                comboBox1.addItem(actual);
            }
            user.setListasPersonales(listaActualizada);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public class MiMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            PeliculaView peliview = new PeliculaView();
            Main.frame.setContentPane(peliview.getPanel());
            Main.frame.setVisible(true);
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

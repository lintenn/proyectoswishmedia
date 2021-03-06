package prSwishMedia;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Lista {
    private List<ContenidoMultimedia> contMedia;
    private int id;
    private String nombre;
    private Date fechaCreacion;
    public int numCont;
    Statement st;

    public Lista(int id, String nombre, Date fechaCreacion, Statement stmt) {
        contMedia = new ArrayList<>();
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        numCont = 0;
        st=stmt;
    }

    public Lista(List<ContenidoMultimedia> list, int id, String nombre, Date fechaCreacion) {
        contMedia = list;
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        numCont = 0;
    }

    public boolean esta(int id){
        int res = 0;
        try {
            ResultSet rs= st.executeQuery("SELECT count(*) FROM A├▒adirContenido WHERE idContenidoMultimedia=" + id + " AND idLista=" + this.id + ";");
            rs.next();
            res=rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res>0;
    }

    public List<ContenidoMultimedia> getContMedia() {
        return contMedia;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public int getNumCont() {
        return numCont;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void a├▒adirContenidoMultimedia(ContenidoMultimedia cM) {
        contMedia.add(cM);
    }

    public void borrarContenidoMultimedia(ContenidoMultimedia cM) {
        contMedia.remove(cM);
    }

    public String mostrarLista() {
        String s;

        s = "Pel├şculas y series de la lista " + getNombre() + ":\n";

        for (ContenidoMultimedia contMul : contMedia) {
            s += contMul.getNombre() + "\n";
        }

        return s;
    }


    public String toString() {
        return getNombre();

    }
}
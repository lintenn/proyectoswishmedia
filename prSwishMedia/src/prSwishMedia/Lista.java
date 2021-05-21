package prSwishMedia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Lista {
    private List<ContenidoMultimedia> contMedia;
    private int id;
    private String nombre;
    private Date fechaCreacion;
    public int numCont;

    public Lista(int id, String nombre, Date fechaCreacion) {
        contMedia = new ArrayList<>();
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        numCont=0;
    }

    public Lista(List<ContenidoMultimedia> list,int id, String nombre, Date fechaCreacion) {
        contMedia = list;
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        numCont=0;
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

    public void añadirContenidoMultimedia(ContenidoMultimedia cM){
        contMedia.add(cM);
<<<<<<< HEAD
    }

    public void borrarContenidoMultimedia(ContenidoMultimedia cM){
        contMedia.remove(cM);
=======
    }

    public void borrarContenidoMultimedia(ContenidoMultimedia cM){
        contMedia.remove(cM);
    }

    public String toString() {
        String s;

        s = "Películas y series de la lista " + getNombre() + ":\n";

        for (ContenidoMultimedia contMul : contMedia) {
            s += contMul.getNombre() + "\n";
        }

        return s;
>>>>>>> Angelo
    }
}

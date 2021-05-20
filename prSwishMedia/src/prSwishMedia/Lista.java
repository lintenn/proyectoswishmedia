package prSwishMedia;

import java.util.Arrays;
import java.util.Date;

public class Lista {
    private ContenidoMultimedia[] contMedia;
    private int id;
    private String nombre;
    private Date fechaCreacion;
    public int numCont;

    public Lista(int id, String nombre, Date fechaCreacion) {
        contMedia = new ContenidoMultimedia[1];
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        numCont=0;
    }

    public ContenidoMultimedia[] getContMedia() {
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
        if(contMedia.length==numCont){
            contMedia = Arrays.copyOf(contMedia, numCont*2);
        }
        contMedia[numCont] = cM;
        numCont++;
    }

    private int buscar(ContenidoMultimedia cM){
        int i=0;
        while(contMedia[i]!=cM && i<numCont){
            i++;
        }
        return i;
    }

    public void borrarContenidoMultimedia(ContenidoMultimedia cM){
        int i=buscar(cM);
        if(i<numCont){
            while(i<numCont-1){
                contMedia[i]=contMedia[i+1];
                i++;
            }
            numCont--;
        }
    }
}

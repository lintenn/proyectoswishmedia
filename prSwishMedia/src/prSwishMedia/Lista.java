package prSwishMedia;

import java.util.Date;

public class Lista {
    private ContenidoMultimedia[] contMedia;
    private int id;
    private String nombre;
    private Date fechaCreacion;

    public Lista(ContenidoMultimedia[] contMedia, int id, String nombre, Date fechaCreacion) {
        this.contMedia = contMedia;
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
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

    public void setContMedia(ContenidoMultimedia[] contMedia) {
        this.contMedia = contMedia;
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
}

package prSwishMedia;

import java.util.Date;

public class Pelicula extends ContenidoMultimedia {
    private int duracion;

    // Constructor Esencial
    public Pelicula(int id, String nombre, boolean aprobado) {
        super(id, nombre, aprobado);
    }

    public Pelicula(int id, String nombre, String sinopsis, String reparto, double valoracion, Date fecha, String genero, String premios, int rating, String trailer, int veces_anyadidas, int ranking, boolean aprobado, int duracion) {
        super(id, nombre, sinopsis, reparto, valoracion, fecha, genero, premios, rating, trailer, veces_anyadidas, ranking, aprobado);
        this.duracion = duracion;
    }

    // Getters
    public int getDuracion() {
        return duracion;
    }

    // Setters
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}

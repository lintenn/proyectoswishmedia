package prSwishMedia;

import java.util.Date;

public class Pelicula extends ContenidoMultimedia {
    private int duracion;

    public Pelicula(int id, String nombre, String sinopsis, String reparto, double valoracion, Date fecha, String genero, String premios, int rating, String trailer, int veces_anyadidas, int ranking, int duracion) {
        super(id, nombre, sinopsis, reparto, valoracion, fecha, genero, premios, rating, trailer, veces_anyadidas, ranking);
        this.duracion = duracion;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}

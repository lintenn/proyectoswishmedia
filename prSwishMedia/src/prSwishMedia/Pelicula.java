package prSwishMedia;

import java.util.Date;
import java.util.List;

public class Pelicula extends ContenidoMultimedia {
    private int duracion;

    // Constructor Esencial
    public Pelicula(int id, String nombre, boolean aprobado) {
        super(id, nombre, aprobado);
    }

    public Pelicula(int id, String nombre, String sinopsis, String reparto, double valoracion, String fecha, String genero, String premios, int rating, String trailer, int veces_anyadidas, int ranking, boolean aprobado, int duracion) {
        super(id, nombre, sinopsis, reparto, valoracion, fecha, genero, premios, rating, trailer, veces_anyadidas, ranking, aprobado);
        this.duracion = duracion;
    }

    public Pelicula(String nombre, int imagen, String sinopsis, String genero, double valoracion) { //changed
        super(nombre, imagen,sinopsis,genero,valoracion);
    }

    public Pelicula(String nombre, double val, String fecha_estreno, int duracion, String genero, String sinopsis, String reparto) { //changed
        super(nombre,val,fecha_estreno,genero,sinopsis,reparto);
        this.duracion=duracion;
    }

    public Pelicula(int id,String nombre, double val, String fecha_estreno, int duracion, String genero, String sinopsis, String reparto) { //changed
        super(id,nombre,val,fecha_estreno,genero,sinopsis,reparto);
        this.duracion=duracion;
    }

    public Pelicula(int idContenidoMultimedia, String nombre, int i, String fechaEstreno, int duracion, String genero, String sinopsis, String reparto, int veces_añadidas) {
        super(idContenidoMultimedia,nombre,fechaEstreno,genero,sinopsis,reparto,veces_añadidas);
        this.duracion=duracion;
    }

    public Pelicula(String nombre, int imagen, String sinopsis, String genero, double valoracion, int veces_añadidas) { //changed
        super(nombre, imagen,sinopsis,genero,valoracion,veces_añadidas);
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

package prSwishMedia;

import java.util.Date;
import java.util.List;

public class Serie extends ContenidoMultimedia {
    private int numCapitulos, numTemporadas;
    private double duracionMedia;

    // Constructor Esencial
    public Serie(int id, String nombre, boolean aprobado) {
        super(id, nombre, aprobado);
    }

    public Serie(int id, String nombre, String sinopsis, String reparto, double valoracion, Date fecha, String genero, String premios, int rating, String trailer, int veces_anyadidas, int ranking, boolean aprobado, int numCapitulos, int numTemporadas, double duracionMedia) {
        super(id, nombre, sinopsis, reparto, valoracion, fecha, genero, premios, rating, trailer, veces_anyadidas, ranking, aprobado);
        this.duracionMedia = duracionMedia;
        this.numCapitulos = numCapitulos;
        this.numTemporadas = numTemporadas;
    }

    // Getters
    public double getDuracionMedia() {
        return duracionMedia;
    }

    public int getNumCapitulos() {
        return numCapitulos;
    }

    public int getNumTemporadas() {
        return numTemporadas;
    }

    // Setters
    public void setDuracionMedia(double duracionMedia) {
        this.duracionMedia = duracionMedia;
    }

    public void setNumCapitulos(int numCapitulos) {
        this.numCapitulos = numCapitulos;
    }

    public void setNumTemporadas(int numTemporadas) {
        this.numTemporadas = numTemporadas;
    }
}

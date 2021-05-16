package prSwishMedia;

import java.util.Date;

public class Serie extends ContenidoMultimedia {
    private int numCapitulos, numTemporadas;
    private double duracionMedia;


    public Serie(int id, String nombre, String sinopsis, String reparto, double valoracion, Date fecha, String genero, String premios, int rating, String trailer, int veces_anyadidas, int ranking, int numCapitulos, int numTemporadas, double duracionMedia) {
        super(id, nombre, sinopsis, reparto, valoracion, fecha, genero, premios, rating, trailer, veces_anyadidas, ranking);
        this.duracionMedia = duracionMedia;
        this.numCapitulos = numCapitulos;
        this.numTemporadas = numTemporadas;

    }

    public double getDuracionMedia() {
        return duracionMedia;
    }

    public int getNumCapitulos() {
        return numCapitulos;
    }

    public int getNumTemporadas() {
        return numTemporadas;
    }

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

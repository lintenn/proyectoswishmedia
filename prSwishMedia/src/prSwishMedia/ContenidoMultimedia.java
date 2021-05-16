package prSwishMedia;

import java.util.Date;

public class ContenidoMultimedia {
    private int id;
    private String nombre;
    private String sinopsis;
    private String reparto;
    private double valoracion;
    private Date fecha;
    private String genero;
    private String premios;
    private int rating;
    private String trailer; //enlace del trailer
    private int veces_anyadidas;
    private int ranking;

    public ContenidoMultimedia(int id,
                    String nombre,
                    String sinopsis,
                    String reparto,
                    double valoracion,
                    Date fecha,
                    String genero,
                    String premios,
                    int rating,
                    String trailer, //enlace del trailer
                    int veces_anyadidas,
                    int ranking) {
        this.fecha = fecha;
        this.genero = genero;
        this.id = id;
        this.nombre = nombre;
        this.premios = premios;
        this.ranking = ranking;
        this.rating = rating;
        this.reparto = reparto;
        this.sinopsis = sinopsis;
        this.trailer = trailer;
        this.valoracion = valoracion;
        this.veces_anyadidas = veces_anyadidas;
    }

    // Getters
    public Date getFecha() {
        return fecha;
    }

    public double getValoracion() {
        return valoracion;
    }

    public int getId() {
        return id;
    }

    public int getRanking() {
        return ranking;
    }

    public int getRating() {
        return rating;
    }

    public int getVeces_anyadidas() {
        return veces_anyadidas;
    }

    public String getGenero() {
        return genero;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPremios() {
        return premios;
    }

    public String getReparto() {
        return reparto;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public String getTrailer() {
        return trailer;
    }

    // Setters
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPremios(String premios) {
        this.premios = premios;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReparto(String reparto) {
        this.reparto = reparto;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public void setValoracion(double valoracion) {
        this.valoracion = valoracion;
    }

    public void setVeces_anyadidas(int veces_anyadidas) {
        this.veces_anyadidas = veces_anyadidas;
    }

}

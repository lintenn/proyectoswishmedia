package prSwishMedia;

import java.util.Date;
import java.util.List;

public class ContenidoMultimedia {
    private int id; // obligatorio
    private String nombre; // obligatorio
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
    private boolean aprobado; //true sii aprobado por admin, false sii no // obligatorio

    // Constructor Esencial
    public ContenidoMultimedia(int id, String nombre, boolean aprobado) {
        this.id = id;
        this.nombre = nombre;
        this.aprobado = aprobado;
        this.sinopsis = "";
        this.valoracion = 0;
        this.veces_anyadidas = 0;
    }

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
                    int ranking,
                    boolean aprobado) {
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
        this.aprobado = aprobado;
    }

    public ContenidoMultimedia(String nombre,int imagen, String sinopsis, String genero, int valoracion) {

        this.nombre = nombre;
        this.id=imagen;
        this.genero = genero;
        this.sinopsis = sinopsis;
        this.valoracion = valoracion;

    }

    public ContenidoMultimedia(String nombre, int imagen, String sinopsis, int valoracion) {
        this.nombre = nombre;
        this.id=imagen;
        this.sinopsis = sinopsis;
        this.valoracion = valoracion;
    }

    public ContenidoMultimedia(String nombre, int valoracion, Date fecha_estreno, String genero, String sinopsis, String reparto) {
        this.nombre = nombre;
        this.fecha = fecha_estreno ;
        this.genero=genero;
        this.reparto=reparto;
        this.sinopsis = sinopsis;
        this.valoracion = valoracion;
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

    public boolean isAprobado() {
        return aprobado;
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

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }
}

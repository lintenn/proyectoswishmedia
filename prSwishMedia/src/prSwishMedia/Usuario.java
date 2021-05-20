package prSwishMedia;

import java.util.Date;


public class Usuario {
    private int id; // obligatorio
    private String nombre;
    private String email; // obligatorio
    private String foto;
    private String descripcion;
    private Date fechaNacimiento;
    private Date fechaCreacion;
    private String contrasenya; // obligatorio
    private int numListas;
    private int numAmigos;
    private boolean privacidad;
    private int numComentarios;
    private int numSeriesVistas;
    private int numEpisodiosVistos;
    private int numPeliculasVistas;

    public Usuario(int id){
        this.id = id;
    }

    // Constructor Esencial
    public Usuario(int id, String email, String contrasenya) {
        this.id = id;
        this.email = email;
        this.contrasenya = contrasenya;
    }

    public Usuario(int id,
                    String nombre,
                    String email,
                    Date fechaNacimiento,
                    Date fechaCreacion,
                    String contrasenya) {
        this.nombre = nombre;
        this.id = id;
        this.contrasenya = contrasenya;
        this.descripcion = "";
        this.email = email;
        this.fechaCreacion = fechaCreacion;
        this.fechaNacimiento = fechaNacimiento;
        this.numListas = 3;
        this.numPeliculasVistas = 0;
        this.numSeriesVistas = 0;
        this.privacidad = false;
        this.numAmigos=0;
        this.numComentarios=0;
        this.numEpisodiosVistos=0;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public int getNumAmigos() {
        return numAmigos;
    }

    public int getNumComentarios() {
        return numComentarios;
    }

    public int getNumEpisodiosVistos() {
        return numEpisodiosVistos;
    }

    public int getNumListas() {
        return numListas;
    }

    public int getNumPeliculasVistas() {
        return numPeliculasVistas;
    }

    public int getNumSeriesVistas() {
        return numSeriesVistas;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEmail() {
        return email;
    }

    public String getFoto() {
        return foto;
    }

    public boolean getPrivacidad() {
        return privacidad;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setNumAmigos(int numAmigos) {
        this.numAmigos = numAmigos;
    }

    public void setNumComentarios(int numComentarios) {
        this.numComentarios = numComentarios;
    }

    public void setNumEpisodiosVistos(int numEpisodiosVistos) {
        this.numEpisodiosVistos = numEpisodiosVistos;
    }

    public void setNumListas(int numListas) {
        this.numListas = numListas;
    }

    public void setNumPeliculasVistas(int numPeliculasVistas) {
        this.numPeliculasVistas = numPeliculasVistas;
    }

    public void setNumSeriesVistas(int numSeriesVistas) {
        this.numSeriesVistas = numSeriesVistas;
    }

    public void setPrivacidad(boolean privacidad) {
        this.privacidad = privacidad;
    }

}

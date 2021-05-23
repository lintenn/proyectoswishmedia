package prSwishMedia;

import java.sql.Date;
import java.util.ArrayList;

import java.util.List;


public class Usuario {
    private String nombre; // obligatorio
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
    private List<Lista> listasPersonales;

    public Usuario(String nick){ //de prueba
        nombre = nick;
        fechaCreacion=new Date(0);
        fechaNacimiento=new Date(0);
        listasPersonales=new ArrayList<>();
    }

    // Constructor Esencial
    public Usuario( String nombre, String email, String contrasenya) {
        this.nombre = nombre;
        this.email = email;
        this.contrasenya = contrasenya;
        this.descripcion = "";
        this.numListas = 3;
        this.numPeliculasVistas = 0;
        this.fechaNacimiento=null;
        this.numSeriesVistas = 0;
        this.privacidad = false;
        this.numAmigos=0;
        this.numComentarios=0;
        this.numEpisodiosVistos=0;
        this.listasPersonales = new ArrayList<>();
    }

    public Usuario(String nombre,
                    String email,
                    Date fechaNacimiento,
                    Date fechaCreacion,
                    String contrasenya) {
        this.nombre = nombre;
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
        this.listasPersonales = new ArrayList<>();
    }

    public Usuario(String nombre, String email, String descripcion, Date fechaNacimiento, Date fechaCreacion, String contrasenya, int numListas, int numAmigos, boolean privacidad, int numComentarios, int numSeriesVistas, int numEpisodiosVistos, int numPeliculasVistas) {
        this.nombre = nombre;
        this.email = email;
        this.descripcion = descripcion;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaCreacion = fechaCreacion;
        this.contrasenya = contrasenya;
        this.numListas = numListas;
        this.numAmigos = numAmigos;
        this.privacidad = privacidad;
        this.numComentarios = numComentarios;
        this.numSeriesVistas = numSeriesVistas;
        this.numEpisodiosVistos = numEpisodiosVistos;
        this.numPeliculasVistas = numPeliculasVistas;
        this.listasPersonales = new ArrayList<>();
    }

    // Getters
    public String getNombre() {
        return nombre;
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

    public List<Lista> getListasPersonales() {
        return listasPersonales;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public void setListasPersonales(List<Lista> listasPersonales) {
        this.listasPersonales = listasPersonales;
    }



    public void añadirLista(Lista l){
        listasPersonales.add(l);
    }
}

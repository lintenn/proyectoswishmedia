package prSwishMedia;

import java.util.Date;

public class Comunicacion {
    private int id;
    private String texto;
    private Date fechaEnvio;

    public Comunicacion(int id, String texto, Date fechaEnvio) {
        this.id = id;
        this.texto = texto;
        this.fechaEnvio = fechaEnvio;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
}

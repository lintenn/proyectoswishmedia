package prSwishMedia;

import java.util.Date;

public class Comentario extends Comunicacion {
    private int nLikes = 0;
    private int nDislikes = 0;

    // Constructor Esencial
    public Comentario(int id, String texto) {
        super(id, texto);
    }

    public Comentario(int id, String texto, Date fechaEnvio) {
        super(id, texto, fechaEnvio);
    }

    // Getters
    public int getnLikes() {
        return nLikes;
    }

    public int getnDislikes() {
        return nDislikes;
    }

    // Setters
    public void setnLikes(int nLikes) {
        this.nLikes = nLikes;
    }

    public void setnDislikes(int nDislikes) {
        this.nDislikes = nDislikes;
    }
}

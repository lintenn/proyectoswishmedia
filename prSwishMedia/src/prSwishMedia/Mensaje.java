package prSwishMedia;

import java.util.Date;

public class Mensaje extends Comunicacion {
    private boolean like;

    public Mensaje(int id, String texto, Date fechaEnvio) {
        super(id, texto, fechaEnvio);
    }

    // Getters
    public boolean isLike() {
        return like;
    }

    // Setters
    public void setLike(boolean like) {
        this.like = like;
    }
}

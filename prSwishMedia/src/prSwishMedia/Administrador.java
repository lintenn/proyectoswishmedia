package prSwishMedia;

import java.util.Date;

public class Administrador extends Usuario {

    // Constructor Esencial
    public Administrador(int id, String nombre, String email, String contrasenya) {
        super(id, nombre, email, contrasenya);
    }

    public Administrador(int id, String nombre, String email, Date fechaNacimiento, Date fechaCreacion, String contrasenya) {
        super(id, nombre, email, fechaNacimiento, fechaCreacion, contrasenya);
    }

    public void aprobar(ContenidoMultimedia cm) {
        cm.setAprobado(true);
    }
}

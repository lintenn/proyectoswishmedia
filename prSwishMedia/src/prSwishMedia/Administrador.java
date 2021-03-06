package prSwishMedia;

import java.sql.Date;

public class Administrador extends Usuario {

    // Constructor Esencial
    public Administrador(int id, String nombre, String email, String contrasenya) {
        super(nombre, email, contrasenya);
    }

    public Administrador(int id, String nombre, String email, Date fechaNacimiento, Date fechaCreacion, String contrasenya) {
        super(nombre, email, fechaNacimiento, fechaCreacion, contrasenya);
    }

    public void aprobar(ContenidoMultimedia cm) {
        cm.setAprobado(true);
    }
}

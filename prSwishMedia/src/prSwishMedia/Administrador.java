package prSwishMedia;

import java.util.Date;

public class Administrador extends Usuario {

    public Administrador(int id, String nombre, String email, String foto, String descripcion, Date fechaNacimiento, Date fechaCreacion, String contrasenya, int numListas, int numAmigos, String privacidad, int numComentarios, int numSeriesVistas, int numEpisodiosVistos, int numPeliculasVistas) {
        super(id, nombre, email, foto, descripcion, fechaNacimiento, fechaCreacion, contrasenya, numListas, numAmigos, privacidad, numComentarios, numSeriesVistas, numEpisodiosVistos, numPeliculasVistas);
    }
}

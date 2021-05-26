package prSwishMedia.Controllers;

import prSwishMedia.Main;
import prSwishMedia.Pelicula;
import prSwishMedia.Views.PeliculaView;
import prSwishMedia.Views.PrincipalView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PeliculaController implements ActionListener {
    PeliculaView peliculaView;
    PrincipalView principalView;
    Pelicula pelicula;

    public PeliculaController(PrincipalView ppv,PeliculaView pv,Pelicula p){
        peliculaView=pv;
        pelicula=p;
        principalView=ppv;
        setInfo();
    }

    private void setInfo() {
        peliculaView.setNombrePelicula(pelicula.getNombre());
        peliculaView.setValoracionPelicula(Integer.toString(pelicula.getRating()));
        peliculaView.setFechaPelicula(pelicula.getFecha().toString());
        peliculaView.setPeliculaDuracion(pelicula.getDuracion());
        peliculaView.setPeliculaGénero(pelicula.getGenero());
        peliculaView.setSinopsisPelicula(pelicula.getSinopsis());
        peliculaView.setRepartoPelicula(pelicula.getReparto());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String act = e.getActionCommand();
        switch (act){
            case ("VOLVER"):
                Main.frame.setContentPane(principalView.getPanel());
                Main.frame.setVisible(true);
                break;
        }
    }
}

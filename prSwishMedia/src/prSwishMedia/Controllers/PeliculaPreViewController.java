package prSwishMedia.Controllers;

import prSwishMedia.Main;
import prSwishMedia.Pelicula;
import prSwishMedia.Views.PeliculaPreView;
import prSwishMedia.Views.PrincipalView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PeliculaPreViewController implements ActionListener {

    private PrincipalView ppview;
    private PeliculaPreView peliPv;
    private Pelicula pelicula;
    private JComboBox listas;

    public PeliculaPreViewController(PrincipalView principalView,PeliculaPreView ppv, Pelicula p, JComboBox l){
        peliPv=ppv;
        pelicula=p;
        ppview=principalView;
        listas=l;
        setInfo();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void actualizarComboBox(JComboBox l){
        peliPv.setComboBox(l);
    }
    public void setInfo(){
        peliPv.setNombre(pelicula.getNombre());
        peliPv.setImagen(pelicula.getId());
        peliPv.setComboBox(listas);
        peliPv.setSinopsis(pelicula.getSinopsis());
        peliPv.setGenero(pelicula.getGenero());
        peliPv.setValoracion(pelicula.getRating());
    }
}

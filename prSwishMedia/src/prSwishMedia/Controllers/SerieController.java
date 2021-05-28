package prSwishMedia.Controllers;

import prSwishMedia.Main;
import prSwishMedia.Serie;
import prSwishMedia.Views.PrincipalView;
import prSwishMedia.Views.SerieView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SerieController  implements ActionListener {

    PrincipalView ppview;
    SerieView serieView;
    Serie serie;

    public SerieController(PrincipalView ppv, SerieView sv, Serie s){
        ppview=ppv;
        serieView=sv;
        serie=s;
        setInfo();
    }

    public void setInfo(){
        serieView.setNombreSerie(serie.getNombre());
        serieView.setValoracionSerie(serie.getValoracion());
        serieView.setFechaSerie(serie.getFecha());
        serieView.setAñadidaSerie(serie.getVeces_anyadidas());
        serieView.setCapitulosSerie(serie.getNumCapitulos());
        serieView.setTemporadasSerie(serie.getNumTemporadas());
        serieView.setGeneroSerie(serie.getGenero());
        serieView.setSinopsisSerie(serie.getSinopsis());
        serieView.setRepartoSerie(serie.getReparto());
        serieView.setDuracionSerie(serie.getDuracionMedia());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String act = e.getActionCommand();
        switch (act) {
            case ("VOLVER"):
                Main.frame.setContentPane(ppview.getPanel());
                Main.frame.setVisible(true);
                break;
        }
    }



}

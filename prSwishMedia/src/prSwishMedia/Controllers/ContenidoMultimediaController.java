package prSwishMedia.Controllers;

import prSwishMedia.Views.PeliculaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContenidoMultimediaController implements ActionListener {

    private PeliculaView peliculaView;

    public ContenidoMultimediaController(PeliculaView peliculaView){
        this.peliculaView=peliculaView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command=e.getActionCommand();
        if(command=="TRAILER"){
            System.out.println("trailer");
        }
    }
}

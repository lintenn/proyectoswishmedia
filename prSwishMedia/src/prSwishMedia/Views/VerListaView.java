package prSwishMedia.Views;

import prSwishMedia.Controllers.OtherUserController;
import prSwishMedia.Controllers.VerListaController;

import javax.swing.*;
import java.awt.*;

public class VerListaView extends JDialog{
    private JScrollPane contenidoListas;
    private JLabel nombreLista;
    private JPanel panel1;
    private JPanel listaContenido;
    private OtherUserController user;

    public VerListaView(OtherUserController otherUserController) {
        user=otherUserController;

        listaContenido=new JPanel();
        add(panel1);
        setSize(new Dimension(800,500));
        this.setTitle("Listas");
        this.setIconImage(new ImageIcon("LogoFondo.jpg").getImage());
        this.setLocationRelativeTo(null);
    }

    public void controlador(VerListaController verListaController) {

    }

    public void removeAllContenido() {
        listaContenido.removeAll();
    }

    public void setLayoutListasContenido(int cont) {
        listaContenido.setLayout(new GridLayout(cont,0,0,0));
    }

    public void addListaContenido(JPanel panel) {
        listaContenido.add(panel);
    }

    public JPanel getListaContenido() {
        return listaContenido;
    }

    public void setViewportViewScrollContenido(JPanel listaContenido) {
        contenidoListas.setViewportView(listaContenido);
    }

    public void setNombre(String nombre) {
        nombreLista.setText(nombre);
    }
}

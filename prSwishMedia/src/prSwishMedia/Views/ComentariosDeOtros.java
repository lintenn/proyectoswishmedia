package prSwishMedia.Views;

import javax.swing.*;
import java.awt.*;

public class ComentariosDeOtros extends JFrame{
    FondoPanel fondo = new FondoPanel();
    private JTextArea textArea1;
    private JPanel panel1;
    private JButton button1;
    private JButton button2;
    private JLabel numNoMegusta;
    private JLabel numMegusta;
    private JLabel NombrePerfil;
    private JLabel FechaEntrega;

    public ComentariosDeOtros(String comentario, int nomg, int mg, String nombre, String fecha){
        textArea1.setText(comentario);
        numNoMegusta.setText(Integer.toString(nomg));
        numMegusta.setText(Integer.toString(mg));
        NombrePerfil.setText(nombre);
        FechaEntrega.setText(fecha);
    }

    private void createUIComponents() {
        panel1 = new FondoPanel();
    }

    public JPanel get(){
        return panel1;
    }

    class FondoPanel extends JPanel{
        private Image imagen;

        public void paint(Graphics g){
            imagen = new ImageIcon(getClass().getResource("/Resources/comentariodeotro.png")).getImage();

            g.drawImage(imagen,0,0,getWidth(), getHeight(),this);

            setOpaque(false);

            super.paint(g);
        }

    }
}

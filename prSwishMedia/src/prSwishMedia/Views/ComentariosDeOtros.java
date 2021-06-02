package prSwishMedia.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ComentariosDeOtros extends JFrame{
    FondoPanel fondo = new FondoPanel();
    private JTextArea textArea1;
    private JPanel panel1;
    private JButton dislikeboton;
    private JButton likeboton;
    private JLabel numNoMegusta;
    private JLabel numMegusta;
    private JLabel NombrePerfil;
    private JLabel FechaEntrega;

    private Cursor manita = new Cursor(Cursor.HAND_CURSOR);
    private Cursor cursorTexto = new Cursor(Cursor.TEXT_CURSOR);

    public ComentariosDeOtros(String comentario, int nomg, int mg, String nombre, String fecha){
        textArea1.setText(comentario);
        numNoMegusta.setText(Integer.toString(nomg));
        numMegusta.setText(Integer.toString(mg));
        NombrePerfil.setText(nombre);
        FechaEntrega.setText(fecha);
        fondo.setLayout(new GridBagLayout());

        textArea1.setCursor(cursorTexto);
        likeboton.setCursor(manita);
        dislikeboton.setCursor(manita);
    }

    public void controlador(ActionListener ctr){
        likeboton.addActionListener(ctr);
        dislikeboton.addActionListener(ctr);

        likeboton.setActionCommand("LIKE");
        dislikeboton.setActionCommand("DISLIKE");
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
            imagen = new ImageIcon(getClass().getResource("/Resources/Sin_título2(1).png")).getImage();

            g.drawImage(imagen,0,0,420, 134,this);

            setOpaque(false);

            super.paint(g);
        }

    }
}

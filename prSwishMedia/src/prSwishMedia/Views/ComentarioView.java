package prSwishMedia.Views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ComentarioView extends JFrame{
    FondoPanel fondo = new FondoPanel();
    private JTextArea textArea1;
    private JPanel panel1;
    private JButton likeboton;
    private JButton dislikeboton;
    private JLabel numNoMegusta;
    private JLabel numMegusta;
    private JLabel NombrePerfil;
    private JLabel FechaEntrega;
    private JButton button2;

    private Cursor manita = new Cursor(Cursor.HAND_CURSOR);
    private Cursor cursorTexto = new Cursor(Cursor.TEXT_CURSOR);

    public ComentarioView(String comentario, int nomg, int mg, String nombre, String fecha){
        textArea1.setText(comentario);
        numNoMegusta.setText(Integer.toString(nomg));
        numMegusta.setText(Integer.toString(mg));
        NombrePerfil.setText(nombre);
        FechaEntrega.setText(fecha);
        fondo.setLayout(new GridBagLayout());

        textArea1.setCursor(cursorTexto);
        likeboton.setCursor(manita);
        dislikeboton.setCursor(manita);
        button2.setCursor(manita);
    }

    public void controlador(ActionListener ctr){
        button2.addActionListener(ctr);
        likeboton.addActionListener(ctr);
        dislikeboton.addActionListener(ctr);

        button2.setActionCommand("BORRAR");
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
            int y=panel1.getY();
            imagen = new ImageIcon(getClass().getResource("/Resources/Sin_título(1).png")).getImage();
            panel1.setLocation(getWidth()-420,y);
            g.drawImage(imagen,0,0,420, 134,this);

            setOpaque(false);

            super.paint(g);
        }

    }
}

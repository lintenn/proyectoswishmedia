package prSwishMedia.Views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class UsuarioPreView extends JFrame{
    private JLabel Nombre;
    private JTextPane Descripción;
    private JPanel panel1;
    private JButton eliminarAmigo;

    public UsuarioPreView(){ }
    public JPanel getPanel() {
        return panel1;
    }

    public void setDescripción(String descripción) { Descripción.setText(descripción);}
    public void setNombre(String nombre) { Nombre.setText(nombre); }
}

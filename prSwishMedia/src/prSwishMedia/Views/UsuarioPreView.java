package prSwishMedia.Views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class UsuarioPreView {
    private JLabel Nombre;
    private JTextPane Descripcion;
    private JPanel panel1;
    private JButton eliminarAmigo;

    public UsuarioPreView(){

    }

    public void controlador(ActionListener ctr) {
        eliminarAmigo.addActionListener(ctr);

        eliminarAmigo.setActionCommand("AÑADIRAMIGO");
    }


    public JPanel getPanel() {
        return panel1;
    }

    public void botonAñadirInvisible(boolean a){eliminarAmigo.setVisible(a); }
    public void setNombre(String nombre) {
        Nombre.setText(nombre);
    }

    public void setDescripcion(String descripcion) {
        Descripcion.setText(descripcion);
    }

}

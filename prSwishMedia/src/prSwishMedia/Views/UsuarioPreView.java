package prSwishMedia.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UsuarioPreView {
    private JLabel Nombre;
    private JTextPane Descripcion;
    private JPanel panel1;
    private JButton añadirAmigo;
    private JButton eliminarAmigo;
    private JButton chatear;
    private JLabel candado;
    private JPanel panelito;

    private Cursor manita = new Cursor(Cursor.HAND_CURSOR);
    private Cursor cursorTexto = new Cursor(Cursor.TEXT_CURSOR);

    public UsuarioPreView(){
        Descripcion.setCursor(cursorTexto);
        eliminarAmigo.setCursor(manita);
        añadirAmigo.setCursor(manita);
        chatear.setCursor(manita);
        panelito.setCursor(manita);
    }

    public void controlador(ActionListener ctr) {
        añadirAmigo.addActionListener(ctr);
        eliminarAmigo.addActionListener(ctr);
        chatear.addActionListener(ctr);

        añadirAmigo.setActionCommand("AÑADIRAMIGO");
        eliminarAmigo.setActionCommand("ELIMINARAMIGO");
        chatear.setActionCommand("CHATEAR");
    }

    public void añadirCandado(){
        candado.setVisible(true);
    }

    public void quitarCandado(){
        candado.setVisible(false);
    }

    public JPanel getPanel() {
        return panel1;
    }

    public void botonAñadirInvisible(boolean a){ añadirAmigo.setVisible(a); }
    public void botonEliminarInvisible(boolean a){ eliminarAmigo.setVisible(a); }
    public void setChatear(boolean a){
        chatear.setVisible(a);
    }

    public void setNombre(String nombre) {
        Nombre.setText(nombre);
    }
    public String getNombre(){return Nombre.getText(); }
    public void setDescripcion(String descripcion) {
        Descripcion.setText(descripcion);
    }

}

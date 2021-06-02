package prSwishMedia.Views;

import prSwishMedia.Lista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OtherUserView {
    private JPanel panel1;
    private JLabel Descripción;
    private JPanel nombreYVisualizaciones;
    private JLabel nombreUsuario;
    private JLabel labelPeliculasVistas;
    private JLabel labelCapVistos;
    private JLabel labelNumeroAmigos;
    private JLabel numPeliculas;
    private JLabel numCapitulos;
    private JLabel numAmigos;
    private JLabel labelSeriesVistas;
    private JLabel numeroSeriesVistas;
    private JPanel panelFechas;
    private JLabel labelFechaNacimiento;
    private JLabel labelFechaCreacion;
    private JLabel fechaCreacion;
    private JButton volver;
    private JPanel listasPanel;
    private JLabel labelLista;
    private JButton verLista;
    private JComboBox comboBoxListas;
    private JLabel fechaNacimiento;
    private JButton añadirAmigo;
    private JTextPane descripcionPanel;
    private JButton button1;

    private Cursor manita = new Cursor(Cursor.HAND_CURSOR);
    private Cursor cursorTexto = new Cursor(Cursor.TEXT_CURSOR);

    public OtherUserView() {
        volver.setCursor(manita);
        verLista.setCursor(manita);
        añadirAmigo.setCursor(manita);
        comboBoxListas.setCursor(manita);
        descripcionPanel.setCursor(cursorTexto);
        descripcionPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    }

    public void añadirComboBox(Lista l){
        comboBoxListas.addItem(l);
    }

    public void controlador(ActionListener ctr) {
        volver.addActionListener(ctr);
        volver.setActionCommand("VOLVER");

        verLista.addActionListener(ctr);
        verLista.setActionCommand("VERLISTA");

        añadirAmigo.addActionListener(ctr);
        añadirAmigo.setActionCommand("AÑADIRAMIGO");
    }

    public void setFechaCreacion(String date) { fechaCreacion.setText(date); }
    public void setFechaNacimiento(String date) { fechaNacimiento.setText(date); }
    public void setDescripcion(String descripcion){
        descripcionPanel.setText(descripcion);
    }
    public void setNombreUsuario(String nombre) { nombreUsuario.setText(nombre); }
    public void setNumCapitulos(int num) { numCapitulos.setText(String.valueOf(num)); }
    public void setNumSeriesVistas(int num) { numeroSeriesVistas.setText(String.valueOf(num)); }
    public void setNumPeliculas(int num) { numPeliculas.setText(String.valueOf(num)); }
    public void setNumAmigos(int num) { numAmigos.setText(String.valueOf(num)); }
    public String getDescripcion() { return descripcionPanel.getText(); }

    public JPanel getPanel() {
        return panel1;
    }
}

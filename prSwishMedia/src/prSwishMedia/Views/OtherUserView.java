package prSwishMedia.Views;

import prSwishMedia.Lista;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.sql.Statement;

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
    private JTextField descripcionField;
    private JLabel fechaNacimiento;

    public OtherUserView(Statement st) { }

    public void añadirComboBox(Lista l){
        comboBoxListas.addItem(l);
    }
    public void eliminarComboBox(Lista l){
        comboBoxListas.removeItem(l);
    }

    public void controlador(ActionListener ctr) {
        volver.addActionListener(ctr);
        volver.setActionCommand("VOLVER");

        verLista.addActionListener(ctr);
        verLista.setActionCommand("VERLISTA");
    }

    public void setNombreUsuario(String nombre) { nombreUsuario.setText(nombre); }
    public void setNumCapitulos(int num) { numCapitulos.setText(String.valueOf(num)); }
    public void setNumSeriesVistas(int num) { numeroSeriesVistas.setText(String.valueOf(num)); }
    public void setNumPeliculas(int num) { numPeliculas.setText(String.valueOf(num)); }
    public void setNumAmigos(int num) { numAmigos.setText(String.valueOf(num)); }
    public String getDescripcion() { return descripcionField.getText(); }

    public JPanel getPanel() {
        return panel1;
    }
}
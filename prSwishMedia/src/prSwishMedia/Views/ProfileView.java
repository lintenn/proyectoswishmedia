package prSwishMedia.Views;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;

public class ProfileView extends JFrame{
    private JPanel panel1;
    private JLabel labelPeliculasVistas;
    private JLabel labelCapVistos;
    private JLabel labelNumeroAmigos;
    private JLabel Descripción;
    private JLabel numPeliculas;
    private JLabel numCapitulos;
    private JLabel numAmigos;
    private JLabel nombreUsuario;
    private JLabel labelPrivacidad;
    private JCheckBox checkBoxPrivacidad;
    private JTextArea textAreaDescripcion;
    private JLabel labelSeriesVistas;
    private JLabel numeroSeriesVistas;
    private JLabel labelFechaCreacion;
    private JLabel fechaCreacion;
    private JLabel labelFechaNacimiento;
    private JLabel fechaNacimiento;
    private JComboBox comboBoxListas;
    private JLabel labelLista;
    private JButton buttonCrearLista;
    private JButton buttonEliminarLista;
    private JPanel nombreYVisualizaciones;
    private JPanel listasPanel;
    private JTextField textField1;
    private JLabel labelInfoCrearLista;
    private JPanel panelFechas;

    public ProfileView(){
        add(panel1);
    }

    public void controlador(ActionListener ctr, ChangeListener ctr1){
        buttonCrearLista.addActionListener(ctr);
        buttonEliminarLista.addActionListener(ctr);

        checkBoxPrivacidad.addChangeListener(ctr1);

        checkBoxPrivacidad.setActionCommand("PRIVACIDAD");

        buttonEliminarLista.setActionCommand("ELIMINAR");
        buttonCrearLista.setActionCommand("CREAR");
    }

    public JPanel getPanel() {
        return panel1;
    }
}

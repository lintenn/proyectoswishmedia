package prSwishMedia.Views;


import prSwishMedia.Lista;
import prSwishMedia.Main;
import prSwishMedia.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.time.*;

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
    private JComboBox<Lista> comboBoxListas;
    private JLabel labelLista;
    private JButton buttonCrearLista;
    private JButton buttonEliminarLista;
    private JPanel nombreYVisualizaciones;
    private JPanel listasPanel;
    private JTextField nombreLista;
    private JLabel labelInfoCrearLista;
    private JPanel panelFechas;
    private JButton volver;
    private JButton logout;
    private JLabel msgInfomodificarLista;
    private JComboBox comboBoxAnyo;
    private JComboBox comboBoxMes;
    private JComboBox comboBoxDia;
    private JButton botonnotificacion;
    private JButton AmigosButton;

    private Cursor manita = new Cursor(Cursor.HAND_CURSOR);
    private Cursor cursorTexto = new Cursor(Cursor.TEXT_CURSOR);


    public ProfileView(Statement st){
        add(panel1);
        textAreaDescripcion.setFocusable(true);

        AmigosButton.setCursor(manita);
        botonnotificacion.setCursor(manita);
        comboBoxListas.setCursor(manita);
        buttonEliminarLista.setCursor(manita);
        buttonCrearLista.setCursor(manita);
        checkBoxPrivacidad.setCursor(manita);
        comboBoxAnyo.setCursor(manita);
        comboBoxDia.setCursor(manita);
        comboBoxMes.setCursor(manita);
        volver.setCursor(manita);
        logout.setCursor(manita);
    }


    public void añadirComboBox(Lista l){
        comboBoxListas.addItem(l);
    }
    public void eliminarComboBox(Lista l){
        comboBoxListas.removeItem(l);
    }
    public void añadirComboBoxMes(String l){
        comboBoxMes.addItem(l);
    }
    public void eliminarComboBoxMes(String l){
        comboBoxMes.removeItem(l);
    }
    public void añadirComboBoxDia(int l){
        comboBoxDia.addItem(l);
    }
    public void eliminarComboBoxDia(int l){
        comboBoxDia.removeItem(l);
    }
    public void añadirComboBoxAnyo(int l){
        comboBoxAnyo.addItem(l);
    }
    public void eliminarComboBoxAnyo(int l){
        comboBoxAnyo.removeItem(l);
    }
    public void eliminarComboBoxTodoDia(){comboBoxDia.removeAllItems();}
    public int getSelectedIndexComboBoxDia(){return  comboBoxDia.getSelectedIndex();}
    public void setSetSelectedIndexComboBoxDia(int x){comboBoxDia.setSelectedIndex(x);}
    public void setSetSelectedIndexComboBoxMes(int x){comboBoxMes.setSelectedIndex(x);}
    public void setSetSelectedIndexComboBoxAnyo(int x){comboBoxAnyo.setSelectedIndex(x);}

    public void controlador(ActionListener ctr){
        buttonCrearLista.addActionListener(ctr);
        buttonEliminarLista.addActionListener(ctr);
        volver.addActionListener(ctr);
        logout.addActionListener(ctr);
        checkBoxPrivacidad.addActionListener(ctr);
        comboBoxAnyo.addActionListener(ctr);
        comboBoxMes.addActionListener(ctr);
        comboBoxDia.addActionListener(ctr);
        textAreaDescripcion.addKeyListener((KeyListener) ctr);
        botonnotificacion.addActionListener(ctr);
        AmigosButton.addActionListener(ctr);

        checkBoxPrivacidad.setActionCommand("PRIVACIDAD");
        logout.setActionCommand("LOGOUT");
        volver.setActionCommand("VOLVER");
        buttonEliminarLista.setActionCommand("ELIMINAR");
        buttonCrearLista.setActionCommand("CREAR");
        comboBoxAnyo.setActionCommand("FECHAMES");
        comboBoxMes.setActionCommand("FECHAMES");
        comboBoxDia.setActionCommand("FECHA");
        botonnotificacion.setActionCommand("NOTIFICACION");
        AmigosButton.setActionCommand("AMIGOS");
    }


    public void setDescripcion(String descripcion){
        textAreaDescripcion.setText(descripcion);
    }
    public void setNombreUsuario(String nombre){nombreUsuario.setText(nombre);}
    public void setNumCapitulos(int num){numCapitulos.setText(String.valueOf(num));}
    public void setNumSeriesVistas(int num){numeroSeriesVistas.setText(String.valueOf(num));}
    public void setNumPeliculas(int num){numPeliculas.setText(String.valueOf(num));}
    public void setFechaCreacion(String date){fechaCreacion.setText(date);}
    public void setNumAmigos(int num){numAmigos.setText(String.valueOf(num));}
    public void setCheckBoxPrivacidad(boolean valor) {checkBoxPrivacidad.setSelected(valor); }
    public void setMsgModificarLista(String error) {
        msgInfomodificarLista.setText(error);
    }
    public String getNombreListaCreada(){ return nombreLista.getText();}
    public String getDescripcion(){return textAreaDescripcion.getText();}
    public Lista getListaEliminada(){ return (Lista) comboBoxListas.getSelectedItem(); }

    public JPanel getPanel() {
        return panel1;
    }
    public Integer getDiaN(){
        return (Integer) comboBoxDia.getSelectedItem();
    }
    public Integer getMesN(){
        String mes=(String) comboBoxMes.getSelectedItem();
        int m=0;
        switch(mes){
            case "Enero":
                m=1;
                break;
            case "Febrero":
                m=2;
                break;
            case "Marzo":
                m=3;
                break;
            case "Abril":
                m=4;
                break;
            case "Mayo":
                m=5;
                break;
            case "Junio":
                m=6;
                break;
            case "Julio":
                m=7;
                break;
            case "Agosto":
                m=8;
                break;
            case "Septiembre":
                m=9;
                break;
            case "Octubre":
                m=10;
                break;
            case "Noviembre":
                m=11;
                break;
            case "Diciembre":
                m=12;
                break;
        }


        return m;
    }

    public Integer getAnyoN(){
        return (Integer) comboBoxAnyo.getSelectedItem();
    }



}

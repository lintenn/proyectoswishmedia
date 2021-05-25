package prSwishMedia.Views;


import prSwishMedia.Lista;
import prSwishMedia.Main;
import prSwishMedia.Usuario;

import javax.swing.*;
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
    private Usuario user;
    private KeyListener listener;
    private Statement stmt;


    public ProfileView(Statement st){
        add(panel1);
        user = Main.getUser();
        setInfo();
        stmt = st;
        try {

            ResultSet rs2 = stmt.executeQuery("SELECT privacidad,fechaNacimiento FROM Usuario where nombre = '"+user.getNombre()+"';");
            rs2.next();
            if(rs2.getInt(1)==1){
                checkBoxPrivacidad.setSelected(true);
            }
            actualizarComboBoxFechaN(rs2.getString(2));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        listener = new MyKeyListener();
        textAreaDescripcion.addKeyListener(listener);
        textAreaDescripcion.setFocusable(true);
        actualizarComboBox();
    }

    public void actualizarComboBoxFechaN(String date) {
        for(int i=1; i<=31; i++){
            comboBoxDia.addItem(i);
        }
        for(int i=1900; i<=2021; i++){
            comboBoxAnyo.addItem(i);
        }
        comboBoxMes.addItem("Enero");
        comboBoxMes.addItem("Febrero");
        comboBoxMes.addItem("Marzo");
        comboBoxMes.addItem("Abril");
        comboBoxMes.addItem("Mayo");
        comboBoxMes.addItem("Junio");
        comboBoxMes.addItem("Julio");
        comboBoxMes.addItem("Agosto");
        comboBoxMes.addItem("Septiembre");
        comboBoxMes.addItem("Octubre");
        comboBoxMes.addItem("Noviembre");
        comboBoxMes.addItem("Diciembre");

        String[] parts = date.split("-");

        comboBoxAnyo.setSelectedIndex(Integer.parseInt(parts[0])-1900);
        comboBoxMes.setSelectedIndex(Integer.parseInt(parts[1])-1);
        comboBoxDia.setSelectedIndex(Integer.parseInt(parts[2])-1);

        if(Integer.parseInt(parts[1])==2 && Integer.parseInt(parts[0])%4!=0){
            updateDay(28);
        } else if(Integer.parseInt(parts[1])==2){
            updateDay(28);
        } else if(Integer.parseInt(parts[1])==4 || Integer.parseInt(parts[1])==6 || Integer.parseInt(parts[1])==9 || Integer.parseInt(parts[1])==11){
            updateDay(30);
        }
    }

    public void añadirComboBox(Lista l){
        comboBoxListas.addItem(l);
    }
    public void eliminarComboBox(Lista l){
        comboBoxListas.removeItem(l);
    }

    public void actualizarComboBox() {
        if(user.getListasPersonales()!=null){
            for(Lista l: user.getListasPersonales()){
                comboBoxListas.addItem(l);
            }
        }else {
            System.out.println("LISTA VACIA");
        }
    }

    public void controlador(ActionListener ctr){
        buttonCrearLista.addActionListener(ctr);
        buttonEliminarLista.addActionListener(ctr);
        volver.addActionListener(ctr);
        logout.addActionListener(ctr);
        checkBoxPrivacidad.addActionListener(ctr);
        comboBoxAnyo.addActionListener(ctr);
        comboBoxMes.addActionListener(ctr);
        comboBoxDia.addActionListener(ctr);

        
        checkBoxPrivacidad.setActionCommand("PRIVACIDAD");
        logout.setActionCommand("LOGOUT");
        volver.setActionCommand("VOLVER");
        buttonEliminarLista.setActionCommand("ELIMINAR");
        buttonCrearLista.setActionCommand("CREAR");
        comboBoxAnyo.setActionCommand("FECHAMES");
        comboBoxMes.setActionCommand("FECHAMES");
        comboBoxDia.setActionCommand("FECHA");
    }

    public void setInfo(){
         textAreaDescripcion.setText(user.getDescripcion());
         nombreUsuario.setText(user.getNombre());
         numAmigos.setText(""+user.getNumAmigos()+"");
         numCapitulos.setText(user.getNumEpisodiosVistos() + "");
         numeroSeriesVistas.setText(user.getNumSeriesVistas()+"");
         numPeliculas.setText(user.getNumPeliculasVistas()+"");
         if(user.getFechaCreacion()!=null)fechaCreacion.setText(user.getFechaCreacion().toString());

    }

    public void setDescripcion(String descripcion){
        textAreaDescripcion.setText(descripcion);
    }
    
    public void setMsgModificarLista(String error) {
        msgInfomodificarLista.setText(error);
    }
    public String getNombreListaCreada(){ return nombreLista.getText();}
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




    public class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            if(textAreaDescripcion.getText().length()==60){
                e.consume();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==10){
                e.consume();
                try {
                    stmt.executeUpdate("UPDATE Usuario SET descripcion = '"+textAreaDescripcion.getText()+"' where nombre = '"+user.getNombre()+"';");
                    user.setDescripcion(textAreaDescripcion.getText());

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    public void updateDay(int n) {
        int x = comboBoxDia.getSelectedIndex();
        try {
            comboBoxDia.removeAllItems();
        }catch (NullPointerException e){

        }
        for(int i=0;i<n;i++){
            comboBoxDia.addItem(i+1);
        }
        if(x<=n){
            comboBoxDia.setSelectedIndex(x);
        }
    }

}

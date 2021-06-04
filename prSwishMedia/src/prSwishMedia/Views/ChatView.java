package prSwishMedia.Views;

import prSwishMedia.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class ChatView extends JFrame{
    private JPanel panel1;
    private JLabel Chat;
    private JScrollPane Mensajes;
    private JTextField textField1;
    private JButton button1;
    private JButton button2;

    public ChatView(){
        Mensajes.getVerticalScrollBar().setUnitIncrement(16);
        setSize(new Dimension(500,400));
    }

    public void controlador(ActionListener ctr){
        button1.addActionListener(ctr);
        button2.addActionListener(ctr);
        textField1.addKeyListener((KeyListener) ctr);

        button1.setActionCommand("ENVIAR");
        button2.setActionCommand("VOLVER");
    }

    public void setChat(String s){
        Chat.setText("Chateando con "+s);
    }

    public void setMensajes(JPanel p){
        Mensajes.setViewportView(p);
    }

    public void setTextField1(String s){
        textField1.setText(s);
    }

    public String getTextField1(){
        return textField1.getText();
    }

    public JPanel getPanel1(){
        return panel1;
    }
}

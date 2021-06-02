package prSwishMedia.Views;

import prSwishMedia.Usuario;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ChatView {
    private JPanel panel1;
    private JLabel Chat;
    private JScrollPane Mensajes;
    private JTextField textField1;
    private JButton button1;

    public ChatView(){

    }

    public void controlador(ActionListener ctr){
        button1.addActionListener(ctr);

        button1.setActionCommand("ENVIAR");
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
}

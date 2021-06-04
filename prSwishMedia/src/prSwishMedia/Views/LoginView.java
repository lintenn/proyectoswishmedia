package prSwishMedia.Views;

import prSwishMedia.Controllers.ConfirmedController;
import prSwishMedia.Listeners.MouseClick;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;

public class LoginView extends JFrame{
    private JPanel panel1;
    private JPasswordField password;
    private JTextField user;
    private JButton loginButton;
    private JButton registroButton;
    private JLabel Logo;
    private JLabel Error;
    private JLabel Forgot;

    private Cursor manita = new Cursor(Cursor.HAND_CURSOR);

    public LoginView(){
        add(panel1);

        loginButton.setCursor(manita);
        registroButton.setCursor(manita);
        Forgot.setCursor(manita);
    }

    public void controlador(ActionListener ctr){
        loginButton.addActionListener(ctr);
        registroButton.addActionListener(ctr);
        password.addActionListener(ctr);

        loginButton.setActionCommand("LOGIN");
        registroButton.setActionCommand("REGISTRO");
        password.setActionCommand("LOGIN");

    }

    public void clrPass(){
        getPassword().getAccessibleContext().getAccessibleEditableText().delete(0,getPassword().getAccessibleContext().getAccessibleText().getCharCount());

    }

    public JLabel getForgot() {
        return Forgot;
    }

    public JTextField getUser() {
        return user;
    }

    public JPasswordField getPassword() {

        return password;
    }

    public void clear(String s){
        user.setText("");
        getPassword().getAccessibleContext().getAccessibleEditableText().delete(0,getPassword().getAccessibleContext().getAccessibleText().getCharCount());
        setErrorMessage(s);
    }


    public void setErrorMessage(String s){
        this.Error.setForeground(Color.red);
        this.Error.setText(s);
    }

    public JPanel getPanel(){
        return panel1;
    }




}

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


    public LoginView(){
        ConfirmedView cv = new ConfirmedView();
        MouseClick mc = new MouseClick();
        add(panel1);
       /* setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("SwishMedia");
        setIconImage(new ImageIcon("LogoFondo.jpg").getImage());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);*/
        Forgot.addMouseListener(mc);
    }

    public void controlador(ActionListener ctr){
        loginButton.addActionListener(ctr);
        registroButton.addActionListener(ctr);

        loginButton.setActionCommand("LOGIN");
        registroButton.setActionCommand("REGISTRO");

    }

    public JTextField getUser() {
        return user;
    }

    public JPasswordField getPassword() {

        return password;
    }



    public void setErrorMessage(String s){
        this.Error.setForeground(Color.red);
        this.Error.setText(s);
    }

    public JPanel getPanel(){
        return panel1;
    }


}

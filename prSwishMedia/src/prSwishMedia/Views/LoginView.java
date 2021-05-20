package prSwishMedia.Views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginView extends JFrame{
    private JPanel panel1;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JButton loginButton;
    private JButton registroButton;
    private JLabel Logo;

    public LoginView(){
        
        add(panel1);
       /* setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("SwishMedia");
        setIconImage(new ImageIcon("LogoFondo.jpg").getImage());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);*/
    }

    public void controlador(ActionListener ctr){
        loginButton.addActionListener(ctr);
        registroButton.addActionListener(ctr);

        loginButton.setActionCommand("LOGIN");
        registroButton.setActionCommand("REGISTRO");


    }

    public JPanel getPanel(){
        return panel1;
    }


}

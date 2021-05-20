package prSwishMedia.Views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class RegisterView extends JFrame {
    private JPanel panel1;
    private JTextField textField2;
    private JTextField textField3;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton registroButton;
    private JButton volverButton;

    public RegisterView(){

        add(panel1);
        /*setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("SwishMedia");
        setIconImage(new ImageIcon("LogoFondo.jpg").getImage());

        pack();
        setLocationRelativeTo(null);
        setVisible(false);*/
    }

    public void controlador(ActionListener ctr){
        registroButton.addActionListener(ctr);
        volverButton.addActionListener(ctr);

        registroButton.setActionCommand("REGISTRO");
        volverButton.setActionCommand("VOLVER");

    }

    public JPanel getPanel() {
        return panel1;
    }
}
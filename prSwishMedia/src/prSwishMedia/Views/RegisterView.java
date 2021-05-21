package prSwishMedia.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterView extends JFrame {
    private JPanel panel1;
    private JTextField user;
    private JTextField email;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton registroButton;
    private JButton volverButton;
    private JLabel Error;

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

    public JTextField getEmail() {
        return email;
    }

    public void clear(String s){
        user.setText("");
        email.setText("");
        passwordField1.getAccessibleContext().getAccessibleEditableText().delete(0,passwordField1.getAccessibleContext().getAccessibleText().getCharCount());
        passwordField2.getAccessibleContext().getAccessibleEditableText().delete(0,passwordField2.getAccessibleContext().getAccessibleText().getCharCount());
        setErrorMessage(s);
    }



    public  boolean validarMail() {
        // Patron para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email.getText());
        return mather.find();
    }

    public JPanel getPanel() {
        return panel1;
    }

    public JTextField getUser() {
        return user;
    }

    public void setErrorMessage(String s){
        Error.setForeground(Color.red);
        Error.setText(s);
    }
}
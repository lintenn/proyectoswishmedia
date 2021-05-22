package prSwishMedia.Controllers;

import prSwishMedia.Gmail;
import prSwishMedia.Main;
import prSwishMedia.Usuario;
import prSwishMedia.Views.LoginView;
import prSwishMedia.Views.RegisterView;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterController implements ActionListener {

    private LoginView lview;
    private RegisterView rview;
    Statement conexion;
    Usuario user;


    public RegisterController(RegisterView rv, LoginView lv, Statement st){
        lview=lv;
        rview=rv;
        conexion=st;
    }
    public void actionPerformed(ActionEvent ev) {
        String act = ev.getActionCommand();

        if(act.equals("REGISTRO")){
            String nick=rview.getUser().getText();
            String pass1 = new String(rview.getPassword1().getPassword());
            String pass2 = new String(rview.getPassword2().getPassword());
            String email = rview.getEmail().getText();

            boolean esta=false;
            try {
                ResultSet users = conexion.executeQuery("SELECT nombre FROM Usuario WHERE nombre='" + nick + "';");
                esta=users.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            if(!rview.validarMail()){
                rview.clear("Correo erróneo");
            } else if( nick.length()<5 || nick.length()>15){
                rview.clear("Introduce un nombre de usuario entre 5 y 15 caracteres");
            }else if (pass1.length()<6 || pass1.length()>16 || !pass1.equals(pass2) ) {
                rview.clear("Introduzca una contraseña de entre 6 y 15 caracteres");
            }else if(esta){
                rview.clear("Usuario ya existente");
            }else{

                try {
                    conexion.executeUpdate("INSERT INTO Usuario (nombre,email,contraseña) VALUES ('" + nick +  "','"+email+"','" + pass1 + "' )");
                    rview.clear("Usuario creado con éxito");
                    Gmail g=new Gmail();
                    g.enviarCorreoRegistro(email,pass1,nick);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }else if(act.equals("VOLVER")) {
            Main.frame.setContentPane(lview.getPanel());
            Main.frame.setVisible(true);
            rview.clear("");

        }


    }
}
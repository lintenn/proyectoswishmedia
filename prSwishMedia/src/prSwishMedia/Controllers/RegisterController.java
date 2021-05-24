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
                rview.setErrorMessage("Introduzca una contraseña de entre 6 y 15 caracteres");
                rview.clrPass();
            }else if(esta){
                rview.clear("Usuario ya existente");
            }else{

                try { //entonces aqui no se ponia lo de las fechas? Porque estoy insertando. Entonces esto lo borro? Ok
                    conexion.executeUpdate("INSERT INTO Usuario (nombre,email,contraseña,fechaNacimiento,fechaCreacion,numlistas,numseriesvistas,numepisodiosvistos,numpeliculasvistas,numamigos,numcomentarios,privacidad,descripcion) " + "VALUES ('" + nick +  "','"+email+"','" + pass1 + "', curdate(), curdate(),0,0,0,0,0,0,0,'')");
                    conexion.executeUpdate("INSERT INTO Lista (id,nombre,fechaCreacion,Nombreusuario) VALUES ("+generateID()+",'Vistas',curdate(),'"+nick+"')");
                    conexion.executeUpdate("INSERT INTO Lista (id,nombre,fechaCreacion,Nombreusuario) VALUES ("+generateID()+",'Pendientes',curdate(),'"+nick+"')");
                    rview.setMessage("Usuario creado con éxito");
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

    private int generateID () throws SQLException {
        ResultSet res = conexion.executeQuery("SELECT MAX(id) FROM Lista;");

        int id=0;
        try{
            res.next();
            id=res.getInt("MAX(id)")+1;
        }catch (SQLException e) {
            rview.setErrorMessage("ERROR Base en la base de datos al generar id");
        }

        return id;
    }
}
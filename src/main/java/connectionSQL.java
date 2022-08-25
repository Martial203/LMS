
import java.sql.Connection;
import java.sql.DriverManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MARTIAL
 */
public class connectionSQL{
    public static void main(String args[]){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/lms_g8","root","");
            System.out.println("Connecion établie avec succès !");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

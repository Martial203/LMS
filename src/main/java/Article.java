
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MARTIAL
 */
public class Article {
   /* public Article(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/lms_g8","root","");
            statement = connection.createStatement();
            System.out.println("Connecion établie avec succès !");
        }catch(Exception e){
            e.printStackTrace();
        }
    }*/
    public Article(String categorie, String titre, String domaine, String auteur, String maisonDEdition, int dateDePublication, String emplacement){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/lms_g8","root","");
            statement = connection.createStatement();
            System.out.println("Connecion établie avec succès !");
            this.categorie=categorie;
            this.titre=titre;
            this.domaine=domaine;
            this.auteur=auteur;
            this.maisonEdition=maisonDEdition;
            this.datePublication=dateDePublication;
            this.position=emplacement;
            }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void ajouter(){
        try{
            statement.executeUpdate("INSERT INTO articles (Categorie, Titre, Domaine, Auteur, Maison_edition, Date_publication,Emplacement) VALUES('"+this.categorie+"','"+this.titre+"','"+this.domaine+"','"+this.auteur+"','"+this.maisonEdition+"','"+this.datePublication+"','"+this.position+"')");
            /*result = statement.executeQuery("SELECT ID from articles WHERE Emplacement='"+this.position+"'");
            this.ID=result.getInt("ID");*/
            System.out.println("Ajout effectué avec succès !");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void supprimer(){
        try{
            statement.executeUpdate("DELETE FROM articles WHERE Emplacement='"+position+"'");
            System.out.println("Suppression effectuée avec succès !");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void modifierEmplacement(String nouvelEmplacement){
        try{
            statement.executeUpdate("UPDATE articles SET Emplacement='"+nouvelEmplacement+"' WHERE Emplacement="+this.position);
            this.position=nouvelEmplacement;
        }catch(Exception e){
        e.printStackTrace();
    }
    }
    private int ID;
    private String categorie;
    private String titre;
    private String auteur;
    private String domaine;
    private String maisonEdition;
    private int datePublication;
    private int nombreDisponible;
    private String position;
    private Connection connection;
    private Statement statement;
    private ResultSet result;
}

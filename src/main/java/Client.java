
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
public class Client {
    public Client(String categorie, String matricule, String nom, String prenom, String sexe, String filiere, int niveau, String adresseMail){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/lms_g8","root","");
            statement = connection.createStatement();
            System.out.println("Connexion étabie avec succès !");
            this.categorie=categorie;
            this.matricule=matricule;
            this.noms=nom;
            this.prenoms=prenom;
            this.sexe=sexe;
            this.filiere=filiere;
            this.niveau=niveau;
            this.adresseMail=adresseMail;
        }catch(Exception e){
            e.printStackTrace();
        }
}
    public void ajouter(){
        try{
            statement.executeUpdate("INSERT INTO clients(Matricule, Noms, Prenoms, Sexe, Filiere, Niveau, Adresse_mail, Categorie) VALUES ('"+this.matricule+"','"+this.noms+"','"+this.prenoms+"','"+this.sexe+"','"+this.filiere+"',"+this.niveau+",'"+this.adresseMail+"','"+this.categorie+"')");
            System.out.println("Ajout effectué avec succès !");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void supprimer(){
        try{
            statement.executeUpdate("DELETE FROM clients WHERE Matricule='"+this.matricule+"'");
            System.out.println("Suppression effectuée avec succès !");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void modifierInformations(String champAModifier, String value){
        try{
            if(!(champAModifier.equals("Niveau"))){
                statement.executeUpdate("UPDATE clients SET "+champAModifier+"='"+value+"' WHERE Matricule='"+this.matricule+"'");
               if(champAModifier.equals("Matricule")){
                   this.matricule=value;
               }
               else if(champAModifier.equals("Noms")){
                   this.noms=value;
               } 
               else if(champAModifier.equals("Prenoms")){
                   this.prenoms=value;
               }
               else if(champAModifier.equals("Sexe")){
                   this.sexe=value;
               }
               else if(champAModifier.equals("Filiere")){
                   this.filiere=value;
               }
               else if(champAModifier.equals("Adresse_mail")){
                   this.adresseMail=value;
               }
               else if(champAModifier.equals("Categorie")){
                   this.categorie=value;
               }
                System.out.println("Modification effectuée avec succès !");
            }
            else{
                statement.executeUpdate("UPDATE clients SET "+champAModifier+"="+Integer.parseInt(value)+" WHERE Matricule='"+this.matricule+"'");
                this.niveau=Integer.parseInt(value);
                System.out.println("Modification effectuée avec succès !");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void consulterInformartions(){
        
    }
    public void rechercher(){
        
    }
    public void ajouterALaListeNoire(){
        
    }
    public void supprimerDeLaListeNoire(){
        
    }
    private String categorie;
    private String matricule;
    private String noms;
    private String prenoms;
    private String sexe;
    private String filiere;
    private int niveau;
    private String adresseMail;
    private Connection connection;
    private Statement statement;
    private ResultSet result;
}

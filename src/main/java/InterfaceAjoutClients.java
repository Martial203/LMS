
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import static java.util.Locale.filter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author MARTIAL
 */
public class InterfaceAjoutClients extends javax.swing.JFrame {

    /**
     * Creates new form InterfaceClients1
     */
    public InterfaceAjoutClients(int lang) {
        initComponents();
        //Traduction
        langue = lang;
        String title[] = new String[]{"Ajouter un nouveau client","Add a new customer"};
        setTitle(title[langue]);
        article = new String[2];
            article[0] = "Articles";
            article[1] = "Items";
            toogleArticles.setText(article[langue]);
        client = new String[2];
            client[0] = "Clients";
            client[1] = "Customers";
            ToogleClients.setText(client[langue]);
        pret = new String[2];
            pret[0] = "Prêts";
            pret[1] = "Loans";
            tooglePrets.setText(pret[langue]);
        formul = new String[2];
            formul[0] = "FORMULAIRE D'AJOUT D'ETUDIANT/ENSEIGNANT";
            formul[1] = "STUDENT/TEACHER ADDING FORM";
            lblFormul.setText(formul[langue]);
        categ = new String[2];
            categ[0] = "Catégorie :";
            categ[1] = "Category :";
            lblCateg.setText(categ[langue]);
        matric = new String[2];
            matric[0] = "Matricule :";
            matric[1] = "Matricule :";
            lblMat.setText(matric[langue]);
        noms = new String[2];
            noms[0] = "Noms :";
            noms[1] = "Name :";
            lblNom.setText(noms[langue]);
        prenoms = new String[2];
            prenoms[0] = "Prénoms :";
            prenoms[1] = "First Name :";
            lblPrenom.setText(prenoms[langue]);
        sexe = new String[2];
            sexe[0] = "Sexe :";
            sexe[1] = "Gender :";
            lblSexe.setText(sexe[langue]);
        mascul = new String[2];
            mascul[0] = "Masculin";
            mascul[1] = "Male";
            masculin.setText(mascul[langue]);
        femin = new String[2];
            femin[0] = "Féminin";
            femin[1] = "Female";
            feminin.setText(femin[langue]);
        filier = new String[2];
            filier[0] = "Filière :";
            filier[1] = "Department :";
            lblFiliere.setText(filier[langue]);
        nivea = new String[2];
            nivea[0] = "Niveau :";
            nivea[1] = "Level :";
            lblNiveau.setText(nivea[langue]);
        adresseMai = new String[2];
            adresseMai[0] = "Adresse mail :";
            adresseMai[1] = "Mail adress :";
            lblMail.setText(adresseMai[langue]);
        enregist = new String[2];
            enregist[0] = "Enregistrer";
            enregist[1] = "Register";
            enregistrer.setText(enregist[langue]);
        reinit = new String[2];
            reinit[0] = "Réinitialiser";
            reinit[1] = "Reset";
            reset.setText(reinit[langue]);
        listeCli = new String[2];
            listeCli[0] = "Liste des clients";
            listeCli[1] = "Customers list";
            toogleListeClients.setText(listeCli[langue]);
        ajouterUnCli = new String[2];
            ajouterUnCli[0] = "Ajouter un client";
            ajouterUnCli[1] = "Add a customer";
            toogleAjoutClient.setText(ajouterUnCli[langue]);
        champObli = new String[2];
            champObli[0] = "Champ obligatoire";
            champObli[1] = "Required field";
        optionSucces = new String[2];
            optionSucces[0] = "Ajout effectué avec succès !";
            optionSucces[1] = "Customer succesfully added";
        optionEchec = new String[2];
            optionEchec[0] = "Echec d'ajout !\n Veuillez verifier votre connexion au serveur.";
            optionEchec[1] = "Registration failed !\n Please check your connection to the server.";
        optionNeccessaire = new String[2];
            optionNeccessaire[0] = "Veuillez fournir toutes les informations requises !";
            optionNeccessaire[1] = "Please provide all required informations !";
        optionOcuppe = new String[2];
            optionOcuppe[0] = "Le matricule entré est déja utilisé par un autre client. Veuillez le verifier de nouveau !";
            optionOcuppe[1] = "This matricule is already used by another customer. Please check it again !";
        quellaCategorieEmprunter = new String[2];
            quellaCategorieEmprunter[0] = "Quelle catégorie de clients souhaitez vous importer ?\nEntrez 0 pour 'Etudiants' ou 1 pour 'Enseignants'";
            quellaCategorieEmprunter[1] = "Which category of customers do you want to import ?\nEnter 0 for 'Students' or 1 for 'Teachers'";
        etudiants = new String[2];
            etudiants[0] = "Etudiants";
            etudiants[1] = "Students";
        enseignants = new String[2];
            enseignants[0] = "Enseignants";
            enseignants[1] = "Teachers";
        entrerLeFichier = new String[2];
            entrerLeFichier[0] = "Veuillez entrer le nom du fichier Excel contenant la liste des ";
            entrerLeFichier[1] = "Please enter the name of the Excel file which contains the list of";
        aImporter = new String[2];
            aImporter[0] = " à importer.\nNB:Fichier de type xls";
            aImporter[1] = " top import.\nWarning:File which the extension .xls";
        entrerPourLeFichier = new String[2];
            entrerPourLeFichier[0] = "Veuillez entrer pour le fichier ";
            entrerPourLeFichier[1] = "Please enter, for the file ";
        leNomDeLaFeuille = new String[2];
            leNomDeLaFeuille[0] = ", le nom de la feuille contenant la liste des ";
            leNomDeLaFeuille[1] = ", the name of the sheet which contains the list of";
        aImporte = new String[2];
            aImporte[0] = " à importer";
            aImporte[1] = " to import";
        importationTerminee = new String[2];
            importationTerminee[0] = "Importation terminée !\n Clients ajoutés avec succès : ";
            importationTerminee[1] = "Importation completed !\n Customers successfully registered : ";
        choixInvalide = new String[2];
            choixInvalide[0] = "Choix non reconnu ! Veuillez ressayer !";
            choixInvalide[1] = "Unknow choice ! Please try again !";
        clientsExistants = new String[2];
            clientsExistants[0] = "\n Clients préalablement existants : ";
            clientsExistants[1] = "[n Already existing customers :";
        erreursDAjout = new String[2];
            erreursDAjout[0] = "\n Erreurs d'ajouts : ";
            erreursDAjout[1] = "\n Registration errors : ";
        fichiersExcel = new String[2];
            fichiersExcel[0] = "Selectionner un fichier Excel 98-2003";
            fichiersExcel[1] = "Select an Excel file 98-2003";
        fichierInvalide = new String[2];
            fichierInvalide[0] = "Type de fichier invalide, Veuillez selectionner un fichier de type xls";
            fichierInvalide[1] = "Invalid file type, Please select a xls file";
        //Connection à la BD
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/lms_g8", "root","");
            statement = connection.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        ToogleClients = new javax.swing.JToggleButton();
        lblFormul = new javax.swing.JLabel();
        toogleArticles = new javax.swing.JToggleButton();
        tooglePrets = new javax.swing.JToggleButton();
        lblCateg = new javax.swing.JLabel();
        Categorie = new javax.swing.JComboBox<>();
        lblErrorMatricule = new javax.swing.JLabel();
        matricule = new javax.swing.JTextField();
        lblMat = new javax.swing.JLabel();
        lblNom = new javax.swing.JLabel();
        nom = new javax.swing.JTextField();
        lblErrorNom = new javax.swing.JLabel();
        lblErrorPrenom = new javax.swing.JLabel();
        prenom = new javax.swing.JTextField();
        lblPrenom = new javax.swing.JLabel();
        lblSexe = new javax.swing.JLabel();
        masculin = new javax.swing.JRadioButton();
        feminin = new javax.swing.JRadioButton();
        lblErrorSexe = new javax.swing.JLabel();
        filiere = new javax.swing.JComboBox<>();
        lblFiliere = new javax.swing.JLabel();
        lblNiveau = new javax.swing.JLabel();
        niveau = new javax.swing.JComboBox<>();
        lblErrorMail = new javax.swing.JLabel();
        adresseMail = new javax.swing.JTextField();
        lblMail = new javax.swing.JLabel();
        enregistrer = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        toogleListeClients = new javax.swing.JToggleButton();
        toogleAjoutClient = new javax.swing.JToggleButton();
        importButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar3 = new javax.swing.JMenuBar();
        Main = new javax.swing.JMenu();
        Edit = new javax.swing.JMenu();
        jMenuClients = new javax.swing.JMenu();
        jMenuArticles = new javax.swing.JMenu();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ToogleClients.setSelected(true);
        ToogleClients.setText("Clients");
        ToogleClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ToogleClientsActionPerformed(evt);
            }
        });
        getContentPane().add(ToogleClients, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 90, -1));

        lblFormul.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblFormul.setText("FORMULAIRE D'AJOUT D'ETUDIANT/ENSEIGNANT");
        getContentPane().add(lblFormul, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, -1, -1));

        toogleArticles.setText("Articles");
        toogleArticles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleArticlesActionPerformed(evt);
            }
        });
        getContentPane().add(toogleArticles, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 90, -1));

        tooglePrets.setText("Prets");
        tooglePrets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tooglePretsActionPerformed(evt);
            }
        });
        getContentPane().add(tooglePrets, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 90, -1));

        lblCateg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCateg.setText("Catégorie :");
        getContentPane().add(lblCateg, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, -1, -1));

        Categorie.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Categorie.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Etudiant", "Enseignant" }));
        Categorie.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CategorieItemStateChanged(evt);
            }
        });
        getContentPane().add(Categorie, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 203, -1));

        lblErrorMatricule.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        lblErrorMatricule.setForeground(new java.awt.Color(209, 10, 61));
        lblErrorMatricule.setText("*");
        getContentPane().add(lblErrorMatricule, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 120, 193, -1));

        matricule.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        matricule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matriculeActionPerformed(evt);
            }
        });
        getContentPane().add(matricule, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 203, -1));

        lblMat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMat.setText("Matricule :");
        getContentPane().add(lblMat, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 105, -1, 30));

        lblNom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNom.setText("Noms :");
        getContentPane().add(lblNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 145, -1, 30));

        nom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(nom, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 150, 200, -1));

        lblErrorNom.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        lblErrorNom.setForeground(new java.awt.Color(209, 10, 61));
        lblErrorNom.setText("*");
        getContentPane().add(lblErrorNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 160, 193, -1));

        lblErrorPrenom.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        lblErrorPrenom.setForeground(new java.awt.Color(209, 10, 61));
        lblErrorPrenom.setText("*");
        getContentPane().add(lblErrorPrenom, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 193, -1));

        prenom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(prenom, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, 200, -1));

        lblPrenom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPrenom.setText("Prénoms :");
        getContentPane().add(lblPrenom, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, -1, 20));

        lblSexe.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSexe.setText("Sexe :");
        getContentPane().add(lblSexe, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 225, -1, 20));

        masculin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        masculin.setText("Masculin");
        masculin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masculinActionPerformed(evt);
            }
        });
        getContentPane().add(masculin, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 220, -1, 30));

        feminin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        feminin.setText("Feminin");
        feminin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                femininActionPerformed(evt);
            }
        });
        getContentPane().add(feminin, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 220, -1, 30));

        lblErrorSexe.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        lblErrorSexe.setForeground(new java.awt.Color(209, 10, 61));
        lblErrorSexe.setText("*");
        getContentPane().add(lblErrorSexe, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 230, 193, -1));

        filiere.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        filiere.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Création et Design Numérique", "Ingenierie Numérique Sociotechnique", "Ingenierie des Systèmes Numériques" }));
        getContentPane().add(filiere, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, 200, -1));

        lblFiliere.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblFiliere.setText("Filière :");
        getContentPane().add(lblFiliere, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, -1, 20));

        lblNiveau.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNiveau.setText("Niveau :");
        getContentPane().add(lblNiveau, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, -1, 20));

        niveau.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        niveau.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5" }));
        niveau.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                niveauItemStateChanged(evt);
            }
        });
        getContentPane().add(niveau, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 300, 200, -1));

        lblErrorMail.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        lblErrorMail.setForeground(new java.awt.Color(209, 10, 61));
        lblErrorMail.setText("*");
        getContentPane().add(lblErrorMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 340, 193, -1));

        adresseMail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(adresseMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 340, 200, -1));

        lblMail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMail.setText("Adresse mail :");
        getContentPane().add(lblMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 340, -1, -1));

        enregistrer.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        enregistrer.setText("Enregistrer");
        enregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enregistrerActionPerformed(evt);
            }
        });
        getContentPane().add(enregistrer, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 380, -1, -1));

        reset.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        reset.setText("Réinitialiser");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        getContentPane().add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 380, -1, -1));

        toogleListeClients.setText("Liste des clients");
        toogleListeClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleListeClientsActionPerformed(evt);
            }
        });
        getContentPane().add(toogleListeClients, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 80, 110, -1));

        toogleAjoutClient.setSelected(true);
        toogleAjoutClient.setText("Ajouter un client");
        toogleAjoutClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleAjoutClientActionPerformed(evt);
            }
        });
        getContentPane().add(toogleAjoutClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 130, 110, -1));

        importButton.setText("Importer");
        importButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importButtonActionPerformed(evt);
            }
        });
        getContentPane().add(importButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 160, 90, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\MARTIAL\\Documents\\NetBeansProjects\\LMS\\src\\main\\java\\images\\clientAddin.png")); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 450));

        Main.setText("Main");
        Main.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MainMouseClicked(evt);
            }
        });
        Main.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MainActionPerformed(evt);
            }
        });
        jMenuBar3.add(Main);

        Edit.setText("Modifier");
        Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditActionPerformed(evt);
            }
        });

        jMenuClients.setText("Clients");
        jMenuClients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuClientsMouseClicked(evt);
            }
        });
        jMenuClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuClientsActionPerformed(evt);
            }
        });
        Edit.add(jMenuClients);

        jMenuArticles.setText("Emplacement articles");
        jMenuArticles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuArticlesMouseClicked(evt);
            }
        });
        Edit.add(jMenuArticles);

        jMenuBar3.add(Edit);

        setJMenuBar(jMenuBar3);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void toogleArticlesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleArticlesActionPerformed
        // TODO add your handling code here:
        if(toogleArticles.isSelected()){
            toogleArticles.setSelected(true);
            ToogleClients.setSelected(false);
            tooglePrets.setSelected(false);
            InterfaceAjoutArticles interfaceAjoutAtrticle = new InterfaceAjoutArticles(langue);
            interfaceAjoutAtrticle.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_toogleArticlesActionPerformed

    private void toogleAjoutClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleAjoutClientActionPerformed
        // TODO add your handling code here:
        if(toogleAjoutClient.isSelected()){
            toogleAjoutClient.setSelected(true);
            toogleListeClients.setSelected(true);
        }
        
    }//GEN-LAST:event_toogleAjoutClientActionPerformed

    private void toogleListeClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleListeClientsActionPerformed
        // TODO add your handling code here:
        if(toogleListeClients.isSelected()){
            toogleListeClients.setSelected(true);
            toogleAjoutClient.setSelected(false);
            InterfaceListeClientsEtudiants interfaceListeEtudiants = new InterfaceListeClientsEtudiants("", langue);
            interfaceListeEtudiants.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_toogleListeClientsActionPerformed

    private void matriculeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matriculeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_matriculeActionPerformed

    private void ToogleClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ToogleClientsActionPerformed
        // TODO add your handling code here:
        if(ToogleClients.isSelected()){
            ToogleClients.setSelected(true);
            toogleArticles.setSelected(false);
            tooglePrets.setSelected(false);
        }
    }//GEN-LAST:event_ToogleClientsActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // TODO add your handling code here:
        matricule.setText("");
        nom.setText("");
        prenom.setText("");
        masculin.setSelected(false);
        feminin.setSelected(false);
        adresseMail.setText("");
        lblErrorMatricule.setText("*");
        lblErrorNom.setText("*");
        lblErrorPrenom.setText("*");
        lblErrorMail.setText("*");
        lblErrorSexe.setText("*");
    }//GEN-LAST:event_resetActionPerformed

    private void enregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enregistrerActionPerformed
        // TODO add your handling code here:
        int Lv;
        String sexe = null;
        boolean OK=true;
        boolean matriculeExistant=false;
        //Verification avant inscription, champs vides, données invalides
        if("Etudiant".equals((String)Categorie.getSelectedItem())){
            if(matricule.getText().equals("")){
                lblErrorMatricule.setText(champObli[langue]);
                OK=false;
            }
            else{
                lblErrorMatricule.setText("*");
            }
            if(nom.getText().equals("")){
                lblErrorNom.setText(champObli[langue]);
                OK=false;
            }
            else{
                lblErrorNom.setText("*");
            }
            if(prenom.getText().equals("")){
                lblErrorPrenom.setText(champObli[langue]);
                OK=false;
            }
            else{
                lblErrorPrenom.setText("*");
            }
            if(adresseMail.getText().equals("")){
                lblErrorMail.setText(champObli[langue]);
                OK=false;
            }
            else{
                lblErrorMail.setText("*");
            }
            if(!(masculin.isSelected() || feminin.isSelected())){
                lblErrorSexe.setText(champObli[langue]);
                OK=false;
            }
            else{
                sexe = (masculin.isSelected())? "Masculin":"Feminin";
                lblErrorSexe.setText("*");
            }
            Lv = Integer.parseInt((String)niveau.getSelectedItem());
            /*else if(masculin.isSelected()){
                sexe = "Masculin";
            }
            else if(feminin.isSelected()){
                sexe = "Feminin";
            }*/
            if(OK==true){
                //Vérification que le matricule fourni n'est utilisé par aucune autre personne
                try{
                    result = statement.executeQuery("SELECT COUNT(*) as nbreClientsPourMatricule FROM clients WHERE Matricule='"+matricule.getText()+"'");
                    result.next();
                    if(result.getInt("nbreClientsPourMatricule")!=0){
                        OK=false;
                        matriculeExistant=true;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(OK==true){ //Si tout est bon, ajouter le client à la BD
                try{
                    Client nouveauClient = new Client((String)Categorie.getSelectedItem(), matricule.getText(), nom.getText(), prenom.getText(), sexe, (String)filiere.getSelectedItem(), Lv, adresseMail.getText());
                    nouveauClient.ajouter();
                    JOptionPane.showMessageDialog(rootPane, optionSucces[langue]);
                }catch(Exception e){
                    JOptionPane.showMessageDialog(rootPane, optionEchec[langue]);
                }
            }
            else{
                if(matriculeExistant==false){
                    JOptionPane.showMessageDialog(rootPane, optionNeccessaire[langue]);
                }
                else{
                    JOptionPane.showMessageDialog(rootPane, optionOcuppe[langue]);
                }
            }
        }
        //Plus haut il s'agissait d'un ajout d'étudiant, ci dessous il s'agit d'un ajout d'enseignant, le processus est le meme
        else{
            if(matricule.getText().equals("")){
                lblErrorMatricule.setText(champObli[langue]);
                OK=false;
            }
            else{
                lblErrorMatricule.setText("*");
            }
            if(nom.getText().equals("")){
                lblErrorNom.setText(champObli[langue]);
                OK=false;
            }
            else{
                lblErrorNom.setText("*");
            }
            if(prenom.getText().equals("")){
                lblErrorPrenom.setText(champObli[langue]);
                OK=false;
            }
            else{
                lblErrorPrenom.setText("*");
            }
            if(adresseMail.getText().equals("")){
                lblErrorMail.setText(champObli[langue]);
                OK=false;
            }
            else{
                lblErrorMail.setText("*");
            }
            if(!(masculin.isSelected() || feminin.isSelected())){
                lblErrorSexe.setText(champObli[langue]);
                OK=false;
            }
            else{
                sexe = (masculin.isSelected())? "Masculin":"Feminin";
            }
            
            if(OK==true){
                try{
                    result = statement.executeQuery("SELECT COUNT(*) as nbreClientsPourMatricule FROM clients WHERE Matricule='"+matricule.getText()+"'");
                    result.next();
                    if(result.getInt("nbreClientsPourMatricule")!=0){
                        OK=false;
                        matriculeExistant=true;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            
            if(OK==true){
                try{
                    Client nouveauClient = new Client((String)Categorie.getSelectedItem(), matricule.getText(), nom.getText(), prenom.getText(), sexe, "", 0, adresseMail.getText());
                    nouveauClient.ajouter();
                    JOptionPane.showMessageDialog(rootPane, "Ajout effectué avec succès !");
                    nouveauClient.modifierInformations("Matricule", "Matricule");
                    
                }catch(Exception e){
                    JOptionPane.showMessageDialog(rootPane, optionEchec[langue]);
                }
            }
            else{
                if(matriculeExistant==false){
                    JOptionPane.showMessageDialog(rootPane, optionNeccessaire[langue]);
                }
                else{
                    JOptionPane.showMessageDialog(rootPane, optionOcuppe[langue]);
                }
            }
        }
    }//GEN-LAST:event_enregistrerActionPerformed

    private void CategorieItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CategorieItemStateChanged
        // TODO add your handling code here:
        if("Enseignant".equals((String)Categorie.getSelectedItem())){
            filiere.setVisible(false);
            lblFiliere.setVisible(false);
            lblNiveau.setVisible(false);
            niveau.setVisible(false);
        }
        else{
            filiere.setVisible(true);
            lblFiliere.setVisible(true);
            lblNiveau.setVisible(true);
            niveau.setVisible(true);
        }
    }//GEN-LAST:event_CategorieItemStateChanged

    private void niveauItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_niveauItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_niveauItemStateChanged

    private void tooglePretsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tooglePretsActionPerformed
        // TODO add your handling code here:
        if(tooglePrets.isSelected()){
            tooglePrets.setSelected(true);
            ToogleClients.setSelected(false);
            toogleArticles.setSelected(false);
            InterfacePrets interfacePrets = new InterfacePrets(langue);
            interfacePrets.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_tooglePretsActionPerformed

    private void MainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainMouseClicked
        // TODO add your handling code here:
        InterfaceDomainesArticles listeDomaines = new InterfaceDomainesArticles(langue);
        listeDomaines.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_MainMouseClicked

    private void MainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MainActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MainActionPerformed

    private void jMenuClientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuClientsMouseClicked
        // TODO add your handling code here:
        InterfaceListeClientsModifiable clientsModifiables = new InterfaceListeClientsModifiable(langue);
        clientsModifiables.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuClientsMouseClicked

    private void jMenuClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuClientsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuClientsActionPerformed

    private void jMenuArticlesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuArticlesMouseClicked
        // TODO add your handling code here:
        InterfaceListeArticlesModifiable listeArticlesModifiables = new InterfaceListeArticlesModifiable("", langue);
        listeArticlesModifiables.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuArticlesMouseClicked

    private void EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EditActionPerformed

    private void masculinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masculinActionPerformed
        // TODO add your handling code here:
        if(masculin.isSelected()){
            feminin.setSelected(false);
        }
    }//GEN-LAST:event_masculinActionPerformed

    private void femininActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_femininActionPerformed
        // TODO add your handling code here:
        if(feminin.isSelected()){
            masculin.setSelected(false);
        }
    }//GEN-LAST:event_femininActionPerformed

    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importButtonActionPerformed
        // TODO add your handling code here:
        Object values[] = new Object[8];
        String choix = JOptionPane.showInputDialog(quellaCategorieEmprunter[langue]);
        String valeurChoix [] = {etudiants[langue], enseignants[langue]};
        if(choix!=null){
            if(choix.equals("0")||choix.equals("1")){
                JFileChooser fc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(fichiersExcel[langue], "xls"); 
                fc.setFileFilter(filter);
                int returnValue = fc.showOpenDialog(fc);
                if(returnValue==JFileChooser.APPROVE_OPTION){
                    File file = fc.getSelectedFile();
                    String fichier = file.getAbsolutePath();
                    String feuille = JOptionPane.showInputDialog(entrerPourLeFichier[langue]+fichier+leNomDeLaFeuille[langue]+valeurChoix[Integer.parseInt(choix)]+aImporte[langue]);
                    if(feuille!=null){
                        int compteurAjouts=0, compteurDejaExistants=0, compteurErreurs=0;
                        try{
                            InputStream input = new FileInputStream(fichier);
                            POIFSFileSystem fs = new POIFSFileSystem(input);
                            HSSFWorkbook wb = new HSSFWorkbook(fs);
                            HSSFSheet sheet = wb.getSheet(feuille);
                            Iterator rows = sheet.rowIterator();
                            HSSFRow row = (HSSFRow)rows.next();
                            while(rows.hasNext()){
                                int i = 0;
                                row = (HSSFRow)rows.next();
                                Iterator cells = row.cellIterator();
                                while(cells.hasNext()){
                                    HSSFCell cell = (HSSFCell)cells.next();
                                    if((choix.equals("0"))&&(i==5)){
                                        values[i]=cell.getNumericCellValue();
                                    }
                                    else{
                                        values[i]=cell.getStringCellValue();
                                    }
                                    i++;
                                }
                                result = statement.executeQuery("SELECT COUNT(*) as nbreClientsPourMatricule FROM clients WHERE Matricule='"+values[0]+"'");
                                result.next();
                                if(result.getInt("nbreClientsPourMatricule")!=0){
                                    compteurDejaExistants++;
                                }
                                else{
                                    try{
                                        if(choix.equals("0")){
                                            compteurAjouts = statement.executeUpdate("INSERT INTO clients(Matricule, Noms, Prenoms, Sexe, Filiere, Niveau, Adresse_mail, Categorie) VALUES('"+values[0]+"', '"+values[1]+"', '"+values[2]+"', '"+values[3]+"', '"+values[4]+"', "+values[5]+", '"+values[6]+"', 'Etudiant')");
                                        }
                                        else{
                                            compteurAjouts = statement.executeUpdate("INSERT INTO clients(Matricule, Noms, Prenoms, Sexe, Filiere, Niveau, Adresse_mail, Categorie) VALUES('"+values[0]+"', '"+values[1]+"', '"+values[2]+"', '"+values[3]+"', '', 0, '"+values[4]+"', 'Enseignant')");
                                        }
                                    }catch(Exception ex){
                                        ex.printStackTrace();
                                        compteurErreurs++;
                                    }
                                }
                            }
                            JOptionPane.showMessageDialog(rootPane, importationTerminee[langue]+compteurAjouts+clientsExistants[langue]+compteurDejaExistants+erreursDAjout[langue]+compteurErreurs);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(rootPane, fichierInvalide[langue]);
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(rootPane, choixInvalide[langue]);
            }
        }
    }//GEN-LAST:event_importButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfaceAjoutClients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfaceAjoutClients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfaceAjoutClients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceAjoutClients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfaceAjoutClients(langue).setVisible(true);
            }
        });
    }

    private Connection connection;
    private Statement statement;
    private ResultSet result;
    static int langue;
    private String article[];
    private String client[];
    private String pret[];
    private String formul[];
    private String categ[];
    private String matric[];
    private String noms[];
    private String prenoms[];
    private String sexe[];
    private String mascul[];
    private String femin[];
    private String filier[];
    private String nivea[];
    private String adresseMai[];
    private String enregist[];
    private String reinit[];
    private String listeCli[];
    private String ajouterUnCli[];
    private String champObli[];
    private String optionSucces[];
    private String optionEchec[];
    private String optionNeccessaire[];
    private String optionOcuppe[];
    private String quellaCategorieEmprunter[];
    private String etudiants[];
    private String enseignants[];
    private String entrerLeFichier[];
    private String aImporter[];
    private String entrerPourLeFichier[];
    private String leNomDeLaFeuille[];
    private String aImporte[];
    private String importationTerminee[];
    private String choixInvalide[];
    private String clientsExistants[];
    private String erreursDAjout[];
    private String fichiersExcel[];
    private String fichierInvalide[];
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Categorie;
    private javax.swing.JMenu Edit;
    private javax.swing.JMenu Main;
    private javax.swing.JToggleButton ToogleClients;
    private javax.swing.JTextField adresseMail;
    private javax.swing.JButton enregistrer;
    private javax.swing.JRadioButton feminin;
    private javax.swing.JComboBox<String> filiere;
    private javax.swing.JButton importButton;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenuArticles;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenu jMenuClients;
    private javax.swing.JLabel lblCateg;
    private javax.swing.JLabel lblErrorMail;
    private javax.swing.JLabel lblErrorMatricule;
    private javax.swing.JLabel lblErrorNom;
    private javax.swing.JLabel lblErrorPrenom;
    private javax.swing.JLabel lblErrorSexe;
    private javax.swing.JLabel lblFiliere;
    private javax.swing.JLabel lblFormul;
    private javax.swing.JLabel lblMail;
    private javax.swing.JLabel lblMat;
    private javax.swing.JLabel lblNiveau;
    private javax.swing.JLabel lblNom;
    private javax.swing.JLabel lblPrenom;
    private javax.swing.JLabel lblSexe;
    private javax.swing.JRadioButton masculin;
    private javax.swing.JTextField matricule;
    private javax.swing.JComboBox<String> niveau;
    private javax.swing.JTextField nom;
    private javax.swing.JTextField prenom;
    private javax.swing.JButton reset;
    private javax.swing.JToggleButton toogleAjoutClient;
    private javax.swing.JToggleButton toogleArticles;
    private javax.swing.JToggleButton toogleListeClients;
    private javax.swing.JToggleButton tooglePrets;
    // End of variables declaration//GEN-END:variables
}


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
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
public class InterfaceAjoutArticles extends javax.swing.JFrame {

    /**
     * Creates new form InterfaceArticles
     */
    public InterfaceAjoutArticles(int lang) {
        initComponents();
        //Traduction
        langue = lang;
        String title[] = new String[]{"Ajouter un nouvel article","Add a new item"};
        setTitle(title[langue]);
        formulaire = new String[2];
            formulaire[0] = "FORMULAIRE D'AJOUT DE NOUVEL ARTICLE";
            formulaire[1] = "NEW ITEM ADDING FORM";
            lblFromulaire.setText(formulaire[lang]);
        article = new String[2];
            article[0] = "Articles";
            article[1] = "Items";
            toogleArticles.setText(article[lang]);
        client = new String[2];
            client[0] = "Clients";
            client[1] = "Customers";
            toogleClients.setText(client[lang]);
        pret = new String[2];
            pret[0] = "Prêts";
            pret[1] = "Loans";
            tooglePrets.setText(pret[lang]);
        catego = new String[2];
            catego[0] = "Catégorie :";
            catego[1] = "Category :";
            lblCatego.setText(catego[lang]);
        titr = new String[2];
            titr[0] = "Titre :";
            titr[1] = "Title :";
            lblTitr.setText(titr[lang]);
        domain = new String[2];
            domain[0] = "Domaine :";
            domain[1] = "Domain :";
            lblDomain.setText(domain[lang]);
        auteu = new String[2];
            auteu[0] = "Auteur :";
            auteu[1] = "Author :";
            lblAuteu.setText(auteu[lang]);
        maisonDed = new String[2];
            maisonDed[0] = "Maison d'édition :";
            maisonDed[1] = "Publishing house :";
            lblMaisonDed.setText(maisonDed[lang]);
        anneeDePu = new String[2];
            anneeDePu[0] = "Année de publication :";
            anneeDePu[1] = "Publication year :";
            lblAnneePub.setText(anneeDePu[lang]);
        emplace = new String[2];
            emplace[0] = "Emplacement :";
            emplace[1] = "Location :";
            lblEmplace.setText(emplace[lang]);
        enregis = new String[2];
            enregis[0] = "Enregistrer";
            enregis[1] = "Register";
            EnregistrerNouvelArticle.setText(enregis[lang]);
        reinit = new String[2];
            reinit[0] = "Réinitialiser";
            reinit[1] = "Reset";
            reset.setText(reinit[lang]);
        listeArti = new String[2];
            listeArti[0] = "Liste des articles";
            listeArti[1] = "Items list";
            toogleListeArticles.setText(listeArti[lang]);
        ajouterArti = new String[2];
            ajouterArti[0] = "Ajouter un article";
            ajouterArti[1] = "Add an item";
            toogleAjoutArticle.setText(ajouterArti[lang]);
        champOblig = new String[2];
            champOblig[0] = "Ce champ est obligatoire";
            champOblig[1] = "This field is required";
        donneeInval = new String[2];
            donneeInval[0] = "Donnée invalide";
            donneeInval[1] = "Invalid input";
        emplaceOccupe = new String[2];
            emplaceOccupe[0] = "Emplacement déja occupé";
            emplaceOccupe[1] = "Location already occupied";
        optionReussi = new String[2];
            optionReussi[0] = "L'article a été enregistré avec succès !";
            optionReussi[1] = "The item has been successfully registered !";
        optionEchec = new String[2];
            optionEchec[0] = "Echec de l'enregistrement !\n Veuillez vérifier votre connection au serveur.";
            optionEchec[1] = "Registration failed !\n Please check your connection to the server.";
        optionOccupe = new String[2];
            optionOccupe[0] = "L'emplacement que vous avez choisi est déja occupé, please take another one ";
            optionOccupe[1] = "The location you have chosen is already occupied, ";
        optionNecessaires = new String[2];
            optionNecessaires[0] = "Veuillez fournir toutes les informations requises !";
            optionNecessaires[1] = "Please provide all the required informations! ";
        nomDuFichierExcel = new String[2];
            nomDuFichierExcel[0] = "Veuillez entrer le nom du fichier Excel contenant la liste des articles à importer.\nNB:Fichier de type xls";
            nomDuFichierExcel[1] = "Please enter the name of the Excel file which contains the list of the items to import.\nWarning:File with the extension .xls";
        entrerPourLeFichier = new String[2];
            entrerPourLeFichier[0] = "Veuillez entrer pour le fichier ";
            entrerPourLeFichier[1] = "Please enter, for the file ";
        nomDeLaFeuille = new String[2];
            nomDeLaFeuille[0] = ", le nom de la feuille contenant la liste des articles à importer";
            nomDeLaFeuille[1] = ",  the name of the sheet which contains the list of items to import";
        importationTerminee = new String[2];
            importationTerminee[0] = "Importation terminée !\n Articles ajoutés avec succès : ";
            importationTerminee[1] = "Importation completed !\n Items succesfully registered : ";
        articlesExistants = new String[2];
            articlesExistants[0] = "\n Articles préalablement existants : ";
            articlesExistants[1] = "\n Items already existing : ";
        erreursAjout = new String[2];
            erreursAjout[0] = "\n Erreurs d'ajouts : ";
            erreursAjout[1] = "\n Registration errors : ";
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        toogleClients = new javax.swing.JToggleButton();
        toogleListeArticles = new javax.swing.JToggleButton();
        toogleArticles = new javax.swing.JToggleButton();
        toogleAjoutArticle = new javax.swing.JToggleButton();
        tooglePrets = new javax.swing.JToggleButton();
        lblFromulaire = new javax.swing.JLabel();
        lblCatego = new javax.swing.JLabel();
        lblTitr = new javax.swing.JLabel();
        lblDomain = new javax.swing.JLabel();
        lblAuteu = new javax.swing.JLabel();
        lblMaisonDed = new javax.swing.JLabel();
        lblAnneePub = new javax.swing.JLabel();
        lblEmplace = new javax.swing.JLabel();
        EnregistrerNouvelArticle = new javax.swing.JButton();
        ITitre = new javax.swing.JTextField();
        IAuteur = new javax.swing.JTextField();
        IMaisonEdition = new javax.swing.JTextField();
        IDateDePublication = new javax.swing.JTextField();
        IEmplacement = new javax.swing.JTextField();
        ICategorie = new javax.swing.JComboBox<>();
        IDomaine = new javax.swing.JComboBox<>();
        lblErrorTitre = new javax.swing.JLabel();
        lblErrorAuteur = new javax.swing.JLabel();
        lblErrorMaisonE = new javax.swing.JLabel();
        lblErrorDatePub = new javax.swing.JLabel();
        lblErrorEmplacement = new javax.swing.JLabel();
        reset = new javax.swing.JButton();
        importButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar3 = new javax.swing.JMenuBar();
        Main = new javax.swing.JMenu();
        Edit = new javax.swing.JMenu();
        jMenuClients = new javax.swing.JMenu();
        jMenuArticles = new javax.swing.JMenu();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 243, 175));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        toogleClients.setText("Clients");
        toogleClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleClientsActionPerformed(evt);
            }
        });
        getContentPane().add(toogleClients, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 70, -1));

        toogleListeArticles.setText("Liste des articles");
        toogleListeArticles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleListeArticlesActionPerformed(evt);
            }
        });
        getContentPane().add(toogleListeArticles, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 120, -1));

        toogleArticles.setSelected(true);
        toogleArticles.setText("Articles");
        toogleArticles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleArticlesActionPerformed(evt);
            }
        });
        getContentPane().add(toogleArticles, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 70, -1));

        toogleAjoutArticle.setSelected(true);
        toogleAjoutArticle.setText("Ajouter un article");
        toogleAjoutArticle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleAjoutArticleActionPerformed(evt);
            }
        });
        getContentPane().add(toogleAjoutArticle, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 130, 120, -1));

        tooglePrets.setText("Prets");
        tooglePrets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tooglePretsActionPerformed(evt);
            }
        });
        getContentPane().add(tooglePrets, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 70, -1));

        lblFromulaire.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        lblFromulaire.setText("FORMULAIRE D'AJOUT DE NOUVEL ARTICLE");
        getContentPane().add(lblFromulaire, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 390, 30));

        lblCatego.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCatego.setText("Catégorie :");
        getContentPane().add(lblCatego, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, -1, 20));

        lblTitr.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTitr.setText("Titre :");
        getContentPane().add(lblTitr, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, -1, 40));

        lblDomain.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDomain.setText("Domaine :");
        getContentPane().add(lblDomain, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, -1, -1));

        lblAuteu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblAuteu.setText("Auteur :");
        getContentPane().add(lblAuteu, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, -1, -1));

        lblMaisonDed.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMaisonDed.setText("Maison d'édition :");
        getContentPane().add(lblMaisonDed, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, -1, -1));

        lblAnneePub.setText("Année de publication :");
        getContentPane().add(lblAnneePub, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 130, 20));

        lblEmplace.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEmplace.setText("Emplacement :");
        getContentPane().add(lblEmplace, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, -1, -1));

        EnregistrerNouvelArticle.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        EnregistrerNouvelArticle.setText("Enregistrer");
        EnregistrerNouvelArticle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnregistrerNouvelArticleActionPerformed(evt);
            }
        });
        getContentPane().add(EnregistrerNouvelArticle, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 360, -1, -1));

        ITitre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ITitre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ITitreActionPerformed(evt);
            }
        });
        getContentPane().add(ITitre, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 170, -1));

        IAuteur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IAuteur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IAuteurActionPerformed(evt);
            }
        });
        getContentPane().add(IAuteur, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 190, 170, -1));

        IMaisonEdition.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(IMaisonEdition, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 230, 170, -1));

        IDateDePublication.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(IDateDePublication, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 270, 170, -1));

        IEmplacement.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(IEmplacement, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 310, 170, -1));

        ICategorie.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ICategorie.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Livre", "Materiel de recherche", "CD" }));
        ICategorie.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ICategorieItemStateChanged(evt);
            }
        });
        ICategorie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ICategorieMouseClicked(evt);
            }
        });
        ICategorie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ICategorieActionPerformed(evt);
            }
        });
        getContentPane().add(ICategorie, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 170, -1));

        IDomaine.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IDomaine.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Informatique", "Mathematiques", "Litterature", "Science-fiction", "Anthropologie et Culture", "Droit", "Art", "Electronique", "Entrepreuneuriat", "Autre" }));
        getContentPane().add(IDomaine, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 150, 170, -1));

        lblErrorTitre.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        lblErrorTitre.setForeground(new java.awt.Color(209, 10, 61));
        lblErrorTitre.setText("*");
        getContentPane().add(lblErrorTitre, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 110, 170, -1));

        lblErrorAuteur.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        lblErrorAuteur.setForeground(new java.awt.Color(209, 10, 61));
        lblErrorAuteur.setText("*");
        getContentPane().add(lblErrorAuteur, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 190, 170, -1));

        lblErrorMaisonE.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        lblErrorMaisonE.setForeground(new java.awt.Color(209, 10, 61));
        lblErrorMaisonE.setText("*");
        getContentPane().add(lblErrorMaisonE, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 230, 170, -1));

        lblErrorDatePub.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        lblErrorDatePub.setForeground(new java.awt.Color(209, 10, 61));
        lblErrorDatePub.setText("*");
        getContentPane().add(lblErrorDatePub, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 270, 170, -1));

        lblErrorEmplacement.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        lblErrorEmplacement.setForeground(new java.awt.Color(209, 10, 61));
        lblErrorEmplacement.setText("*");
        getContentPane().add(lblErrorEmplacement, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 320, 170, -1));

        reset.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        reset.setText("Réinitialiser");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        getContentPane().add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 360, -1, -1));

        importButton.setText("Importer");
        importButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importButtonActionPerformed(evt);
            }
        });
        getContentPane().add(importButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 160, -1, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\MARTIAL\\Documents\\NetBeansProjects\\LMS\\src\\main\\java\\images\\clientAddin.png")); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 430));

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
            toogleClients.setSelected(false);
            tooglePrets.setSelected(false);
        }
    }//GEN-LAST:event_toogleArticlesActionPerformed

    private void toogleAjoutArticleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleAjoutArticleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_toogleAjoutArticleActionPerformed

    private void toogleListeArticlesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleListeArticlesActionPerformed
        // TODO add your handling code here:
        if(toogleListeArticles.isSelected()){
            toogleListeArticles.setSelected(true);
            toogleAjoutArticle.setSelected(false);
            InterfaceListeArticles interfaceListeArticles = new InterfaceListeArticles("", langue);
            interfaceListeArticles.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_toogleListeArticlesActionPerformed

    private void IAuteurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IAuteurActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IAuteurActionPerformed

    private void EnregistrerNouvelArticleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnregistrerNouvelArticleActionPerformed
        // TODO add your handling code here:
        boolean OK=true;
        boolean emplacementOccupe=false;
        int anneeDePublication = 0;
        //Verifications avant soummission, champs vides, données invalides
        if("Livre".equals((String)ICategorie.getSelectedItem()) || "CD".equals((String)ICategorie.getSelectedItem())){
            if(ITitre.getText().equals("")){
                lblErrorTitre.setText(champOblig[langue]);
                OK=false;
            }
            else{
                lblErrorTitre.setText("");
            }
            if(IAuteur.getText().equals("")){
                lblErrorAuteur.setText(champOblig[langue]);
                OK=false;
            }
            else{
                lblErrorAuteur.setText("");
            }
            if(IMaisonEdition.getText().equals("")){
                lblErrorMaisonE.setText(champOblig[langue]);
                OK=false;
            }
            else{
                lblErrorMaisonE.setText("");
            }
            if(IDateDePublication.getText().equals("")){
                lblErrorDatePub.setText(champOblig[langue]);
                OK=false;
            }
            else{
                try{
                    anneeDePublication = Integer.parseInt(IDateDePublication.getText());
                    lblErrorDatePub.setText("");
                }catch(Exception e){
                    lblErrorDatePub.setText(donneeInval[langue]);
                }
            }
            if(IEmplacement.getText().equals("")){
                lblErrorEmplacement.setText(champOblig[langue]);
                OK=false;
            }
            else{
                lblErrorEmplacement.setText("");
            }
        }
        else if("Materiel de recherche".equals((String)ICategorie.getSelectedItem())){
            if(ITitre.getText().equals("")){
                lblErrorTitre.setText(champOblig[langue]);
                OK=false;
            }
            else{
                lblErrorTitre.setText("");
            }
            if(IMaisonEdition.getText().equals("")){
                lblErrorMaisonE.setText(champOblig[langue]);
                OK=false;
            }
            else{
                lblErrorMaisonE.setText("");
            }
            if(IDateDePublication.getText().equals("")){
                lblErrorDatePub.setText(champOblig[langue]);
                OK=false;
            }
            else{
                try{
                    anneeDePublication = Integer.parseInt(IDateDePublication.getText());
                    lblErrorDatePub.setText("");
                }catch(Exception e){
                    lblErrorDatePub.setText(donneeInval[langue]);
                    OK=false;
                }
            }
            if(IEmplacement.getText().equals("")){
                lblErrorEmplacement.setText(champOblig[langue]);
                OK=false;
            }
            else{
                lblErrorEmplacement.setText("");
            }
        }
        if(OK==true){
            //Verification de la disponibilité de l'emplacement demandé
            try{
                result = statement.executeQuery("SELECT COUNT(*) as nbreArticlesPourEmplacement FROM articles WHERE Emplacement='"+IEmplacement.getText()+"' AND Date_suppression IS NULL");
                result.next();
                if(result.getInt("nbreArticlesPourEmplacement")!=0){
                    lblErrorEmplacement.setText(emplaceOccupe[langue]);
                    OK=false;
                    emplacementOccupe=true;
                }    
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        if(OK==true){ //Si aucune erreur n'a été signalée, Ajout de l'article à la BD
            try{
                Article nouvelArticle = new Article((String)ICategorie.getSelectedItem(), ITitre.getText(),(String)IDomaine.getSelectedItem(), IAuteur.getText(), IMaisonEdition.getText(), anneeDePublication, IEmplacement.getText());
                nouvelArticle.ajouter();
                JOptionPane.showMessageDialog(rootPane, optionReussi[langue]);
            }catch(Exception e){
                JOptionPane.showMessageDialog(rootPane, optionEchec[langue]);
            }
        } 
        else{
            if(emplacementOccupe==false){
                JOptionPane.showMessageDialog(rootPane, optionNecessaires[langue]);
            }
            else{
                JOptionPane.showMessageDialog(rootPane, optionOccupe[langue]);
            }
        }
    }//GEN-LAST:event_EnregistrerNouvelArticleActionPerformed

    private void ITitreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ITitreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ITitreActionPerformed

    private void ICategorieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ICategorieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ICategorieActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // TODO add your handling code here:
        ITitre.setText("");
        IAuteur.setText("");
        IDateDePublication.setText("");
        IMaisonEdition.setText("");
        IEmplacement.setText("");
        lblErrorTitre.setText("*");
        lblErrorAuteur.setText("*");
        lblErrorDatePub.setText("*");
        lblErrorMaisonE.setText("*");
        lblErrorEmplacement.setText("*");
    }//GEN-LAST:event_resetActionPerformed

    private void ICategorieMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ICategorieMouseClicked
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_ICategorieMouseClicked

    private void ICategorieItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ICategorieItemStateChanged
        // TODO add your handling code here:
        if(!("Livre".equals((String)ICategorie.getSelectedItem()))){
            lblErrorAuteur.setText("");
        }
        else{
            lblErrorAuteur.setText("*");
        }
    }//GEN-LAST:event_ICategorieItemStateChanged

    private void toogleClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleClientsActionPerformed
        // TODO add your handling code here:
        if(toogleClients.isSelected()){
            toogleClients.setSelected(true);
            toogleArticles.setSelected(false);
            tooglePrets.setSelected(false);
            InterfaceAjoutClients ajoutClient = new InterfaceAjoutClients(langue);
            ajoutClient.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_toogleClientsActionPerformed

    private void tooglePretsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tooglePretsActionPerformed
        // TODO add your handling code here:
        if(tooglePrets.isSelected()){
            tooglePrets.setSelected(true);
            toogleClients.setSelected(false);
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

    private void importButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importButtonActionPerformed
        // TODO add your handling code here:
        Object values[] = new Object[8];
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(fichiersExcel[langue], "xls"); 
        fc.setFileFilter(filter);
        int returnValue = fc.showOpenDialog(fc);
        if(returnValue==JFileChooser.APPROVE_OPTION){
            File file = fc.getSelectedFile();
            String fichier = file.getAbsolutePath();
            String feuille = JOptionPane.showInputDialog(entrerPourLeFichier[langue]+fichier+nomDeLaFeuille[langue]);
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
                            if(i==5){
                                System.out.println(cell.getNumericCellValue());
                                values[i]=cell.getNumericCellValue();
                            }
                            else{
                                values[i]=cell.getStringCellValue();
                            }
                            i++;
                        }
                        result = statement.executeQuery("SELECT COUNT(*) as nbreArticlesPourEmplacement FROM articles WHERE Emplacement='"+values[6]+"' AND Date_suppression IS NULL");
                        result.next();
                        if(result.getInt("nbreArticlesPourEmplacement")!=0){
                            compteurDejaExistants++;
                        } 
                        else{
                            try{
                                compteurAjouts = statement.executeUpdate("INSERT INTO articles(Categorie, Titre, Domaine, Auteur, Maison_edition, Date_publication, Emplacement) VALUES('"+values[0]+"', '"+values[1]+"', '"+values[2]+"', '"+values[3]+"', '"+values[4]+"', "+values[5]+", '"+values[6]+"')");
                            }catch(Exception ex){
                                ex.printStackTrace();
                                compteurErreurs++;
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(rootPane, importationTerminee[langue]+compteurAjouts+articlesExistants[langue]+compteurDejaExistants+erreursAjout[langue]+compteurErreurs);
                }catch(Exception e){
                    e.printStackTrace();
                }
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
            java.util.logging.Logger.getLogger(InterfaceAjoutArticles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfaceAjoutArticles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfaceAjoutArticles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceAjoutArticles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfaceAjoutArticles(langue).setVisible(true);
            }
        });
    }

    private Connection connection;
    private Statement statement;
    private ResultSet result;
    static int langue;
    private String formulaire[];
    private String article[];
    private String client[];
    private String pret[];
    private String catego[];
    private String titr[];
    private String domain[];
    private String auteu[];
    private String maisonDed[];
    private String anneeDePu[];
    private String emplace[];
    private String enregis[];
    private String reinit[];
    private String listeArti[];
    private String ajouterArti[];
    private String champOblig[];
    private String donneeInval[];
    private String emplaceOccupe[];
    private String optionReussi[];
    private String optionEchec[];
    private String optionOccupe[];
    private String optionNecessaires[];
    private String nomDuFichierExcel[];
    private String entrerPourLeFichier[];
    private String nomDeLaFeuille[];
    private String importationTerminee[];
    private String articlesExistants[];
    private String erreursAjout[];
    private String fichiersExcel[];
    private String fichierInvalide[];
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Edit;
    private javax.swing.JButton EnregistrerNouvelArticle;
    private javax.swing.JTextField IAuteur;
    private javax.swing.JComboBox<String> ICategorie;
    private javax.swing.JTextField IDateDePublication;
    private javax.swing.JComboBox<String> IDomaine;
    private javax.swing.JTextField IEmplacement;
    private javax.swing.JTextField IMaisonEdition;
    private javax.swing.JTextField ITitre;
    private javax.swing.JMenu Main;
    private javax.swing.JButton importButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenuArticles;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenu jMenuClients;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAnneePub;
    private javax.swing.JLabel lblAuteu;
    private javax.swing.JLabel lblCatego;
    private javax.swing.JLabel lblDomain;
    private javax.swing.JLabel lblEmplace;
    private javax.swing.JLabel lblErrorAuteur;
    private javax.swing.JLabel lblErrorDatePub;
    private javax.swing.JLabel lblErrorEmplacement;
    private javax.swing.JLabel lblErrorMaisonE;
    private javax.swing.JLabel lblErrorTitre;
    private javax.swing.JLabel lblFromulaire;
    private javax.swing.JLabel lblMaisonDed;
    private javax.swing.JLabel lblTitr;
    private javax.swing.JButton reset;
    private javax.swing.JToggleButton toogleAjoutArticle;
    private javax.swing.JToggleButton toogleArticles;
    private javax.swing.JToggleButton toogleClients;
    private javax.swing.JToggleButton toogleListeArticles;
    private javax.swing.JToggleButton tooglePrets;
    // End of variables declaration//GEN-END:variables
}

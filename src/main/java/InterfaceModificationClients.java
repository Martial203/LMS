
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author MARTIAL
 */
public class InterfaceModificationClients extends javax.swing.JFrame {

    /**
     * Creates new form InterfaceClients1
     */
    public InterfaceModificationClients(String Categorie, String Matricule, String Noms, String Prenoms, String Sexe, String Filiere, int Niveau, String adresseMail, int lang){
        initComponents();
        langue = lang;
        String title[] = new String[]{"Modifier client","Edit/Update a customer"};
        setTitle(title[langue]);
        formul = new String[2];
            formul[0] = "FORMULAIRE DE MODIFICATION D'ETUDIANT/ENSEIGNANT";
            formul[1] = "EDITING/UPDATING CUSTOMER FORM";
            lblFormModif.setText(formul[langue]);
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
        categ = new String[2];
            categ[0] = "Catégorie :";
            categ[1] = "Category :";
            lblCatego.setText(categ[langue]);
        matric = new String[2];
            matric[0] = "Matricule :";
            matric[1] = "Matricule :";
            lblMatricule.setText(matric[langue]);
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
            mascul[1] = "Male :";
            Imasculin.setText(mascul[langue]);
        femin = new String[2];
            femin[0] = "Féminin";
            femin[1] = "Female :";
            Ifeminin.setText(femin[langue]);
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
        champObli = new String[2];
            champObli[0] = "Champ obligatoire";
            champObli[1] = "Required field";
        modifierUnCli = new String[2];
            modifierUnCli[0] = "Modifier un client";
            modifierUnCli[1] = "Edit a customer";
            toogleAjoutClient.setText(modifierUnCli[langue]);
        optionInfosNecces = new String[2];
            optionInfosNecces[0] = "Veuillez fournir toutes les informations requises !";
            optionInfosNecces[1] = "Please provided all requiered informations !";
        optionEchec = new String[2];
            optionEchec[0] = "Echec de modification !\n Veuillez verifier votre connexion au serveur.";
            optionEchec[1] = "Editing failed !\n Please check your connection to the server.";
        optionReussi = new String[2];
            optionReussi[0] = "Modification effectuée avec succès !";
            optionReussi[1] = "Editing successfully made !";
        optionMatriculeDejaUtilise = new String[2];
            optionMatriculeDejaUtilise[0] = "Le matricule entré est déja utilisé par un autre client. Veuillez le verifier de nouveau !";
            optionMatriculeDejaUtilise[1] = "The provided matricule is already used by another customer. Please check it again !";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/lms_g8", "root","");
            statement = connection.createStatement();
            
            matricule = Matricule;
            ICategorie.setSelectedItem(Categorie);
            Imatricule.setText(Matricule);
            Inom.setText(Noms);
            Iprenom.setText(Prenoms);
            if(Sexe.equals("Masculin")){
                Imasculin.setSelected(true);
                Ifeminin.setSelected(false);
            }
            else{
                Imasculin.setSelected(false);
                Ifeminin.setSelected(true);
            }
            Ifiliere.setSelectedItem(Filiere);
            Iniveau.setSelectedItem(Niveau);
            IadresseMail.setText(adresseMail);
            
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

        toogleListeClients = new javax.swing.JToggleButton();
        toogleAjoutClient = new javax.swing.JToggleButton();
        toogleArticles = new javax.swing.JToggleButton();
        ToogleClients = new javax.swing.JToggleButton();
        tooglePrets = new javax.swing.JToggleButton();
        lblFormModif = new javax.swing.JLabel();
        lblCatego = new javax.swing.JLabel();
        ICategorie = new javax.swing.JComboBox<>();
        lblErrorMatricule = new javax.swing.JLabel();
        Imatricule = new javax.swing.JTextField();
        lblMatricule = new javax.swing.JLabel();
        lblNom = new javax.swing.JLabel();
        Inom = new javax.swing.JTextField();
        lblErrorNom = new javax.swing.JLabel();
        lblErrorPrenom = new javax.swing.JLabel();
        Iprenom = new javax.swing.JTextField();
        lblPrenom = new javax.swing.JLabel();
        lblSexe = new javax.swing.JLabel();
        Imasculin = new javax.swing.JRadioButton();
        Ifeminin = new javax.swing.JRadioButton();
        lblErrorSexe = new javax.swing.JLabel();
        Ifiliere = new javax.swing.JComboBox<>();
        lblFiliere = new javax.swing.JLabel();
        lblNiveau = new javax.swing.JLabel();
        Iniveau = new javax.swing.JComboBox<>();
        lblErrorMail = new javax.swing.JLabel();
        IadresseMail = new javax.swing.JTextField();
        lblMail = new javax.swing.JLabel();
        enregistrer = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar3 = new javax.swing.JMenuBar();
        Main = new javax.swing.JMenu();
        Edit = new javax.swing.JMenu();
        jMenuClients = new javax.swing.JMenu();
        jMenuArticles = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        toogleListeClients.setText("Liste des clients");
        toogleListeClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleListeClientsActionPerformed(evt);
            }
        });
        getContentPane().add(toogleListeClients, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 90, -1, -1));

        toogleAjoutClient.setSelected(true);
        toogleAjoutClient.setText("Modifier un client");
        toogleAjoutClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleAjoutClientActionPerformed(evt);
            }
        });
        getContentPane().add(toogleAjoutClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 150, -1, -1));

        toogleArticles.setText("Articles");
        toogleArticles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleArticlesActionPerformed(evt);
            }
        });
        getContentPane().add(toogleArticles, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 90, -1));

        ToogleClients.setSelected(true);
        ToogleClients.setText("Clients");
        ToogleClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ToogleClientsActionPerformed(evt);
            }
        });
        getContentPane().add(ToogleClients, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 90, -1));

        tooglePrets.setText("Prets");
        tooglePrets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tooglePretsActionPerformed(evt);
            }
        });
        getContentPane().add(tooglePrets, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 90, -1));

        lblFormModif.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblFormModif.setText("FORMULAIRE DE MODIFICATION D'ETUDIANT/ENSEIGNANT");
        getContentPane().add(lblFormModif, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, -1, -1));

        lblCatego.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCatego.setText("Catégorie :");
        getContentPane().add(lblCatego, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, -1, -1));

        ICategorie.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ICategorie.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Etudiant", "Enseignant" }));
        ICategorie.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ICategorieItemStateChanged(evt);
            }
        });
        getContentPane().add(ICategorie, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 203, -1));

        lblErrorMatricule.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        lblErrorMatricule.setForeground(new java.awt.Color(209, 10, 61));
        lblErrorMatricule.setText("*");
        getContentPane().add(lblErrorMatricule, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 120, 193, -1));

        Imatricule.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Imatricule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImatriculeActionPerformed(evt);
            }
        });
        getContentPane().add(Imatricule, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 203, -1));

        lblMatricule.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMatricule.setText("Matricule :");
        getContentPane().add(lblMatricule, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        lblNom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNom.setText("Noms :");
        getContentPane().add(lblNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, -1, -1));

        Inom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(Inom, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 203, -1));

        lblErrorNom.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        lblErrorNom.setForeground(new java.awt.Color(209, 10, 61));
        lblErrorNom.setText("*");
        getContentPane().add(lblErrorNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 160, 193, -1));

        lblErrorPrenom.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        lblErrorPrenom.setForeground(new java.awt.Color(209, 10, 61));
        lblErrorPrenom.setText("*");
        getContentPane().add(lblErrorPrenom, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 200, 193, -1));

        Iprenom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(Iprenom, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 200, 203, -1));

        lblPrenom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPrenom.setText("Prénoms :");
        getContentPane().add(lblPrenom, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, -1, -1));

        lblSexe.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSexe.setText("Sexe :");
        getContentPane().add(lblSexe, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, -1, -1));

        Imasculin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Imasculin.setText("Masculin");
        getContentPane().add(Imasculin, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 240, -1, -1));

        Ifeminin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Ifeminin.setText("Feminin");
        getContentPane().add(Ifeminin, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, -1, -1));

        lblErrorSexe.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        lblErrorSexe.setForeground(new java.awt.Color(209, 10, 61));
        lblErrorSexe.setText("*");
        getContentPane().add(lblErrorSexe, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 240, 193, -1));

        Ifiliere.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Ifiliere.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Création et Design Numérique", "Ingenierie Numérique Sociotechnique", "Ingenierie des Systèmes Numériques" }));
        getContentPane().add(Ifiliere, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 280, 200, -1));

        lblFiliere.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblFiliere.setText("Filière :");
        getContentPane().add(lblFiliere, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 280, -1, -1));

        lblNiveau.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNiveau.setText("Niveau :");
        getContentPane().add(lblNiveau, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 320, -1, -1));

        Iniveau.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Iniveau.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5" }));
        Iniveau.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                IniveauItemStateChanged(evt);
            }
        });
        getContentPane().add(Iniveau, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 320, 203, -1));

        lblErrorMail.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        lblErrorMail.setForeground(new java.awt.Color(209, 10, 61));
        lblErrorMail.setText("*");
        getContentPane().add(lblErrorMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 360, 193, -1));

        IadresseMail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(IadresseMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 360, 203, -1));

        lblMail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMail.setText("Adresse mail :");
        getContentPane().add(lblMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 360, -1, -1));

        enregistrer.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        enregistrer.setText("Enregistrer");
        enregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enregistrerActionPerformed(evt);
            }
        });
        getContentPane().add(enregistrer, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 410, -1, -1));

        reset.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        reset.setText("Réinitialiser");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        getContentPane().add(reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 410, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\MARTIAL\\Documents\\NetBeansProjects\\LMS\\src\\main\\java\\images\\clientA.png")); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, 480));

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
            toogleListeClients.setSelected(false);
        }
        
    }//GEN-LAST:event_toogleAjoutClientActionPerformed

    private void toogleListeClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleListeClientsActionPerformed
        // TODO add your handling code here:
        if(toogleListeClients.isSelected()){
            toogleListeClients.setSelected(true);
            toogleAjoutClient.setSelected(false);
            InterfaceListeClientsModifiable interfaceListeEtudiants = new InterfaceListeClientsModifiable(langue);
            interfaceListeEtudiants.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_toogleListeClientsActionPerformed

    private void ImatriculeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImatriculeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ImatriculeActionPerformed

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
        Imatricule.setText("");
        Inom.setText("");
        Iprenom.setText("");
        IadresseMail.setText("");
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
        if("Etudiant".equals((String)ICategorie.getSelectedItem())){
            if(Imatricule.getText().equals("")){
                lblErrorMatricule.setText(champObli[langue]);
                OK=false;
            }
            else{
                lblErrorMatricule.setText("*");
            }
            if(Inom.getText().equals("")){
                lblErrorNom.setText(champObli[langue]);
                OK=false;
            }
            else{
                lblErrorNom.setText("*");
            }
            if(Iprenom.getText().equals("")){
                lblErrorPrenom.setText(champObli[langue]);
                OK=false;
            }
            else{
                lblErrorPrenom.setText("*");
            }
            if(IadresseMail.getText().equals("")){
                lblErrorMail.setText(champObli[langue]);
                OK=false;
            }
            else{
                lblErrorMail.setText("*");
            }
            if(!(Imasculin.isSelected() || Ifeminin.isSelected())){
                lblErrorSexe.setText(champObli[langue]);
                OK=false;
            }
            else{
                sexe = (Imasculin.isSelected())? "Masculin":"Feminin";
                lblErrorSexe.setText("*");
            }
            Lv = Integer.parseInt((String)Iniveau.getSelectedItem());
            /*else if(masculin.isSelected()){
                sexe = "Masculin";
            }
            else if(feminin.isSelected()){
                sexe = "Feminin";
            }*/
            if(OK==true){
                try{
                    result = statement.executeQuery("SELECT COUNT(*) as nbreClientsPourMatricule FROM clients WHERE Matricule='"+Imatricule.getText()+"'");
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
                    Client nouveauClient = new Client((String)ICategorie.getSelectedItem(), matricule, Inom.getText(), Iprenom.getText(), sexe, (String)Ifiliere.getSelectedItem(), Lv, IadresseMail.getText());
                    nouveauClient.modifierInformations("Matricule", Imatricule.getText());
                    nouveauClient.modifierInformations("Noms", Inom.getText());
                    nouveauClient.modifierInformations("Prenoms", Iprenom.getText());
                    nouveauClient.modifierInformations("Sexe", sexe);
                    nouveauClient.modifierInformations("Filiere", (String)Ifiliere.getSelectedItem());
                    nouveauClient.modifierInformations("Niveau", Integer.toString(Lv));
                    nouveauClient.modifierInformations("Adresse_mail", IadresseMail.getText());
                    nouveauClient.modifierInformations("Categorie", (String)ICategorie.getSelectedItem());
                    JOptionPane.showMessageDialog(rootPane, optionReussi[langue]);
                    InterfaceListeClientsModifiable listeClientsModiables = new InterfaceListeClientsModifiable(langue);
                    listeClientsModiables.setVisible(true);
                    this.dispose();
                }catch(Exception e){
                    JOptionPane.showMessageDialog(rootPane, optionEchec[langue]);
                }
            }
            else{
                if(matriculeExistant==false){
                    JOptionPane.showMessageDialog(rootPane, optionInfosNecces[langue]);
                }
                else{
                    JOptionPane.showMessageDialog(rootPane, optionMatriculeDejaUtilise[langue]);
                }
            }
        }
        
        else{
            if(Imatricule.getText().equals("")){
                lblErrorMatricule.setText(champObli[langue]);
                OK=false;
            }
            else{
                lblErrorMatricule.setText("*");
            }
            if(Inom.getText().equals("")){
                lblErrorNom.setText(champObli[langue]);
                OK=false;
            }
            else{
                lblErrorNom.setText("*");
            }
            if(Iprenom.getText().equals("")){
                lblErrorPrenom.setText(champObli[langue]);
                OK=false;
            }
            else{
                lblErrorPrenom.setText("*");
            }
            if(IadresseMail.getText().equals("")){
                lblErrorMail.setText(champObli[langue]);
                OK=false;
            }
            else{
                lblErrorMail.setText("*");
            }
            if(!(Imasculin.isSelected() || Ifeminin.isSelected())){
                lblErrorSexe.setText(champObli[langue]);
                OK=false;
            }
            else{
                sexe = (Imasculin.isSelected())? "Masculin":"Feminin";
            }
            
            if(OK==true){
                try{
                    Client nouveauClient = new Client((String)ICategorie.getSelectedItem(), matricule, Inom.getText(), Iprenom.getText(), sexe, "", 0, IadresseMail.getText());
                    nouveauClient.modifierInformations("Matricule", Imatricule.getText());
                    nouveauClient.modifierInformations("Noms", Inom.getText());
                    nouveauClient.modifierInformations("Prenoms", Iprenom.getText());
                    nouveauClient.modifierInformations("Sexe", sexe) ;
                    nouveauClient.modifierInformations("Filiere", "");
                    nouveauClient.modifierInformations("Niveau", "0");
                    nouveauClient.modifierInformations("Adresse_mail", IadresseMail.getText());
                    nouveauClient.modifierInformations("Categorie", (String)ICategorie.getSelectedItem());
                    JOptionPane.showMessageDialog(rootPane, optionReussi[langue]);
                }catch(Exception e){
                    JOptionPane.showMessageDialog(rootPane, optionEchec[langue]);
                }
            }
            else{
                JOptionPane.showMessageDialog(rootPane, optionInfosNecces[langue]);
            }
        }
    }//GEN-LAST:event_enregistrerActionPerformed

    private void ICategorieItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ICategorieItemStateChanged
        // TODO add your handling code here:
        if("Enseignant".equals((String)ICategorie.getSelectedItem())){
            Ifiliere.setVisible(false);
            lblFiliere.setVisible(false);
            lblNiveau.setVisible(false);
            Iniveau.setVisible(false);
        }
        else{
            Ifiliere.setVisible(true);
            lblFiliere.setVisible(true);
            lblNiveau.setVisible(true);
            Iniveau.setVisible(true);
        }
    }//GEN-LAST:event_ICategorieItemStateChanged

    private void IniveauItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_IniveauItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_IniveauItemStateChanged

    private void tooglePretsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tooglePretsActionPerformed
        // TODO add your handling code here:
        if(tooglePrets.isSelected()){
            ToogleClients.setSelected(false);
            toogleArticles.setSelected(false);
            tooglePrets.setSelected(true);
            InterfacePrets listeEmprunts = new InterfacePrets(langue);
            listeEmprunts.setVisible(true);
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
    private String matricule;
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
    private String modifierUnCli[];
    private String champObli[];
    private String optionInfosNecces[];
    private String optionEchec[];
    private String optionReussi[];
    private String optionMatriculeDejaUtilise[];
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Edit;
    private javax.swing.JComboBox<String> ICategorie;
    private javax.swing.JTextField IadresseMail;
    private javax.swing.JRadioButton Ifeminin;
    private javax.swing.JComboBox<String> Ifiliere;
    private javax.swing.JRadioButton Imasculin;
    private javax.swing.JTextField Imatricule;
    private javax.swing.JComboBox<String> Iniveau;
    private javax.swing.JTextField Inom;
    private javax.swing.JTextField Iprenom;
    private javax.swing.JMenu Main;
    private javax.swing.JToggleButton ToogleClients;
    private javax.swing.JButton enregistrer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenuArticles;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenu jMenuClients;
    private javax.swing.JLabel lblCatego;
    private javax.swing.JLabel lblErrorMail;
    private javax.swing.JLabel lblErrorMatricule;
    private javax.swing.JLabel lblErrorNom;
    private javax.swing.JLabel lblErrorPrenom;
    private javax.swing.JLabel lblErrorSexe;
    private javax.swing.JLabel lblFiliere;
    private javax.swing.JLabel lblFormModif;
    private javax.swing.JLabel lblMail;
    private javax.swing.JLabel lblMatricule;
    private javax.swing.JLabel lblNiveau;
    private javax.swing.JLabel lblNom;
    private javax.swing.JLabel lblPrenom;
    private javax.swing.JLabel lblSexe;
    private javax.swing.JButton reset;
    private javax.swing.JToggleButton toogleAjoutClient;
    private javax.swing.JToggleButton toogleArticles;
    private javax.swing.JToggleButton toogleListeClients;
    private javax.swing.JToggleButton tooglePrets;
    // End of variables declaration//GEN-END:variables
}


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author MARTIAL
 */
public class InterfaceListeClientsEnseignants extends javax.swing.JFrame {

    /**
     * Creates new form InterfaceClients2
     */
    
    class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            String emplacementArticleEmprunt = JOptionPane.showInputDialog(optionEntrerEmplac[langue]);
            if(emplacementArticleEmprunt!=null){
                try{
                    result = statement.executeQuery("SELECT * FROM articles WHERE Emplacement='"+emplacementArticleEmprunt+"' AND Date_suppression IS NULL");
                    if(result.next()){
                        String validationPret = JOptionPane.showInputDialog(optionDateEmprun[langue]+result.getString("Titre")+categ[langue]+result.getString("Categorie")+domain[langue]+result.getString("Domaine")+auteu[langue]+result.getString("Auteur")+maisonD[langue]+result.getString("Maison_edition")+anneePub[langue]+result.getInt("Date_publication")+emplacem[langue]+result.getString("Emplacement")+optionPourValider[langue]);
                            result = statement.executeQuery("SELECT * FROM liste_noire WHERE Matricule_client='"+validationPret+"' AND Date_de_sortie IS NULL");
                                if(result.next()){
                                    JOptionPane.showMessageDialog(editorComponent, optionCeClientNe[langue]+result.getString("Dette"));
                                }
                                else{
                                    result = statement.executeQuery("SELECT COUNT(*) as nbreEmpruntsActifs FROM emprunts WHERE Matricule_Emprunteur='"+validationPret+"' AND Date_remise IS NULL");
                                    result.next();
                                    if(result.getInt("nbreEmpruntsActifs")<4){
                                        statement.executeUpdate("INSERT INTO emprunts(Matricule_Emprunteur, Emplacement_Article, Date_emprunt) VALUES('"+validationPret+"', '"+result.getString("Emplacement")+"', NOW())");
                                        JOptionPane.showMessageDialog(editorComponent, optionEmpruntSucces[langue]);
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(editorComponent, optionPlusDeQuatreArti[langue]);
                                    }
                                }
                    }
                    
                    else if(emplacementArticleEmprunt!=null){
                        JOptionPane.showMessageDialog(editorComponent, optionAucunArticle[langue]);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}
    
    public InterfaceListeClientsEnseignants(int lang){
        initComponents();
        langue = lang;
        String title[] = new String[]{"Liste des clients","Customers list"};
        setTitle(title[langue]);
        article = new String[2];
            article[0] = "Articles";
            article[1] = "Items";
            toogleArticles.setText(article[langue]);
        client = new String[2];
            client[0] = "Clients";
            client[1] = "Customers";
            toogleClients.setText(client[langue]);
        pret = new String[2];
            pret[0] = "Prêts";
            pret[1] = "Loans";
            tooglePrets.setText(pret[langue]);
        listeCli = new String[2];
            listeCli[0] = "Liste des clients";
            listeCli[1] = "Customers list";
            toogleListeClients.setText(listeCli[langue]);
        ajouterUnCli = new String[2];
            ajouterUnCli[0] = "Ajouter un client";
            ajouterUnCli[1] = "Add an item";
            toogleAjoutClient.setText(ajouterUnCli[langue]);
        recherch = new String[2];
            recherch[0] = "Rechercher";
            recherch[1] = "Search";
            recherche.setText(recherch[langue]);
        trierPar = new String[2];
            trierPar[0] = "Trier par :";
            trierPar[1] = "Sort by :";
            lblTri.setText(trierPar[langue]);
        enseignant = new String[2];
            enseignant[0] = "Enseignants";
            enseignant[1] = "Teachers";
        auteu = new String[2];
            auteu[0] = "\n Auteur : ";
            auteu[0] = "\n Author : ";
        maisonD = new String[2];
            maisonD[0] = "\n Maison d'édition : ";
            maisonD[1] = "\n Publishing house : ";
        anneePub = new String[2];
            anneePub[0] = "\n Année de publication : ";
            anneePub[1] = "\n Publishing year : ";
        emplacem = new String[2];
            emplacem[0] = "\n Emplacement : ";
            emplacem[1] = "\n Location : ";
        domain = new String[2];
            domain[0] = "\n Domaine : ";
            domain[1] = "\n Domain : ";
        categ = new String[2];
            categ[0] = "\n Catégorie : ";
            categ[1] = "\n Category : ";
        optionCeClientNe = new String[2];
            optionCeClientNe[0] = "Ce client ne peut bénéficier des services de cette bibliothèque. \n Motif: Dette de ";
            optionCeClientNe[1] = "This customer cannot benefit from the library services. \n Reason : Debt of ";
        optionDateEmprun = new String[2];
            optionDateEmprun[0] = "Demande d'emprunt de l'article aux caractéristiques suivantes : \n Titre: ";
            optionDateEmprun[1] = "Borrowing request for the article with the following characteristics : \n Title: ";
        optionEmpruntSucces = new String[2];
            optionEmpruntSucces[0] = "Emprunt effectué avec succès !"; 
            optionEmpruntSucces[1] = "Borrowing successfully completed !";
        optionEntrerEmplac = new String[2];
            optionEntrerEmplac[0] = "Veuillez entrer l'emplacement de l'article que vous souhaitez prêter.";
            optionEntrerEmplac[1] = "Please provide the location of the item you want to lend.";
        optionPlusDeQuatreArti = new String[2];
            optionPlusDeQuatreArti[0] = "Ce client a déja 04 articles en sa possession, il ne peut en emprunter d'autres !";
            optionPlusDeQuatreArti[1] = "This customer already has 04 items in his possession, he cannot borrow more !";
        optionPourValider = new String[2];
            optionPlusDeQuatreArti[0] = "\n\n Pour valider le prêt, veuillez entrer le matricule du client";
            optionPlusDeQuatreArti[1] = "\n\n To confirm the loan, please enter this customer's matricule";
        optionAucunArticle = new String[2];
            optionAucunArticle[0] = "Aucun article n'est associé à l'emplacement désigné. Veuillez ressayer !";
            optionAucunArticle[1] = "No item is associated with the designated location. Please try again !";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/lms_g8", "root", "");
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM clients WHERE Categorie='Enseignant' AND Date_suppression IS NULL ORDER BY Noms");
                this.tabInfosEnseignats = new Object [6];
                DefaultTableModel tabInformations = (DefaultTableModel)tableEnseignants.getModel();
                tableEnseignants.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
                tableEnseignants.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));
                while(result.next()){
                    tabInfosEnseignats[0]=result.getString("Matricule");
                    tabInfosEnseignats[1]=result.getString("Noms");
                    tabInfosEnseignats[2]=result.getString("Prenoms");
                    tabInfosEnseignats[3]=result.getString("Sexe");
                    tabInfosEnseignats[4]=result.getString("Adresse_Mail");
                    tabInfosEnseignats[5]="OK";
                    tabInformations.addRow(tabInfosEnseignats);
                }
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

        toogleArticles = new javax.swing.JToggleButton();
        toogleClients = new javax.swing.JToggleButton();
        tooglePrets = new javax.swing.JToggleButton();
        toogleListeClients = new javax.swing.JToggleButton();
        toogleAjoutClient = new javax.swing.JToggleButton();
        comboTri = new javax.swing.JComboBox<>();
        lblTri = new javax.swing.JLabel();
        searchIcon = new javax.swing.JLabel();
        recherche = new javax.swing.JTextField();
        toogleEnseignants = new javax.swing.JToggleButton();
        toogleISN = new javax.swing.JToggleButton();
        toogleINS = new javax.swing.JToggleButton();
        toogleCDN = new javax.swing.JToggleButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableEnseignants = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar3 = new javax.swing.JMenuBar();
        Main = new javax.swing.JMenu();
        Edit = new javax.swing.JMenu();
        jMenuClients = new javax.swing.JMenu();
        jMenuArticles = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        toogleArticles.setText("Articles");
        toogleArticles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleArticlesActionPerformed(evt);
            }
        });
        getContentPane().add(toogleArticles, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 90, -1));

        toogleClients.setSelected(true);
        toogleClients.setText("Clients");
        toogleClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleClientsActionPerformed(evt);
            }
        });
        getContentPane().add(toogleClients, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 90, -1));

        tooglePrets.setText("Prets");
        tooglePrets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tooglePretsActionPerformed(evt);
            }
        });
        getContentPane().add(tooglePrets, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 90, -1));

        toogleListeClients.setSelected(true);
        toogleListeClients.setText("Liste des clients");
        toogleListeClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleListeClientsActionPerformed(evt);
            }
        });
        getContentPane().add(toogleListeClients, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 120, 110, -1));

        toogleAjoutClient.setText("Ajouter un client");
        toogleAjoutClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleAjoutClientActionPerformed(evt);
            }
        });
        getContentPane().add(toogleAjoutClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 180, -1, -1));

        comboTri.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Matricule" }));
        comboTri.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboTriItemStateChanged(evt);
            }
        });
        comboTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTriActionPerformed(evt);
            }
        });
        getContentPane().add(comboTri, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 90, -1));

        lblTri.setText("Trier par :");
        getContentPane().add(lblTri, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, -1, -1));

        searchIcon.setIcon(new javax.swing.ImageIcon("C:\\wamp64\\www\\Code\\photos\\search.png")); // NOI18N
        searchIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchIconMouseClicked(evt);
            }
        });
        getContentPane().add(searchIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, -1, -1));

        recherche.setText("Rechercher");
        recherche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rechercheActionPerformed(evt);
            }
        });
        getContentPane().add(recherche, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 121, -1));

        toogleEnseignants.setSelected(true);
        toogleEnseignants.setText("Enseignants");
        toogleEnseignants.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleEnseignantsActionPerformed(evt);
            }
        });
        getContentPane().add(toogleEnseignants, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 50, 90, -1));

        toogleISN.setText("ISN");
        toogleISN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleISNActionPerformed(evt);
            }
        });
        getContentPane().add(toogleISN, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, 70, -1));

        toogleINS.setText("INS");
        toogleINS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleINSActionPerformed(evt);
            }
        });
        getContentPane().add(toogleINS, new org.netbeans.lib.awtextra.AbsoluteConstraints(269, 50, 70, -1));

        toogleCDN.setText("CDN");
        toogleCDN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleCDNActionPerformed(evt);
            }
        });
        getContentPane().add(toogleCDN, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 70, -1));

        tableEnseignants.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 204), null));
        tableEnseignants.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Matricule", "Noms", "Prénoms", "Sexe", "Adresse mail", "Faire un prêt"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableEnseignants);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 490, 353));

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
            toogleClients.setSelected(false);
            tooglePrets.setSelected(false);
            InterfaceListeArticles listeArticles = new InterfaceListeArticles("", langue);
            listeArticles.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_toogleArticlesActionPerformed

    private void toogleAjoutClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleAjoutClientActionPerformed
        // TODO add your handling code here:
        if(toogleAjoutClient.isSelected()){
            toogleListeClients.setSelected(false);
            toogleAjoutClient.setSelected(true);
            InterfaceAjoutClients interfaceAjoutClient = new InterfaceAjoutClients(langue);
            interfaceAjoutClient.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_toogleAjoutClientActionPerformed

    private void toogleListeClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleListeClientsActionPerformed
        // TODO add your handling code here:
        if(toogleListeClients.isSelected()){
            toogleListeClients.setSelected(true);
            toogleAjoutClient.setSelected(false);
        }
    }//GEN-LAST:event_toogleListeClientsActionPerformed

    private void rechercheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rechercheActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rechercheActionPerformed

    private void comboTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTriActionPerformed

    private void toogleEnseignantsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleEnseignantsActionPerformed
        // TODO add your handling code here:
        if(toogleEnseignants.isSelected()){
            toogleCDN.setSelected(false);
            toogleINS.setSelected(false);
            toogleISN.setSelected(false);
        }
    }//GEN-LAST:event_toogleEnseignantsActionPerformed

    private void toogleCDNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleCDNActionPerformed
        // TODO add your handling code here:
        if(toogleCDN.isSelected()){
            toogleEnseignants.setSelected(false);
            toogleINS.setSelected(false);
            toogleISN.setSelected(false);
            InterfaceListeClientsEtudiants interfaceListeEtudiants = new InterfaceListeClientsEtudiants("Création et Design Numérique", langue);
            interfaceListeEtudiants.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_toogleCDNActionPerformed

    private void toogleINSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleINSActionPerformed
        // TODO add your handling code here:
        if(toogleINS.isSelected()){
            toogleEnseignants.setSelected(false);
            toogleCDN.setSelected(false);
            toogleISN.setSelected(false);
            InterfaceListeClientsEtudiants interfaceListeEtudiants = new InterfaceListeClientsEtudiants("Ingenierie Numérique Sociotechnique", langue);
            interfaceListeEtudiants.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_toogleINSActionPerformed

    private void toogleISNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleISNActionPerformed
        // TODO add your handling code here:
        if(toogleISN.isSelected()){
            toogleEnseignants.setSelected(false);
            toogleINS.setSelected(false);
            toogleCDN.setSelected(false);
            InterfaceListeClientsEtudiants interfaceListeEtudiants = new InterfaceListeClientsEtudiants("Ingenierie des Systèmes Numériques", langue);
            interfaceListeEtudiants.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_toogleISNActionPerformed

    private void searchIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchIconMouseClicked
        // TODO add your handling code here:
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/lms_g8", "root", "");
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM clients WHERE (Categorie='"+recherche.getText()+"' OR Noms LIKE'%"+recherche.getText()+"%' OR Prenoms LIKE '%"+recherche.getText()+"%' OR Sexe LIKE '"+recherche.getText()+"%' OR Adresse_mail LIKE '%"+recherche.getText()+"%') AND Date_suppression IS NULL ORDER BY Noms");
                this.tabInfosEnseignats = new Object [6];
                DefaultTableModel tabInformations = (DefaultTableModel)tableEnseignants.getModel();
                for(int i=tabInformations.getRowCount()-1; i>=0; i--){
                    tabInformations.removeRow(i);
                }
                int compteur=0;
                while(result.next()){
                    tabInfosEnseignats[0]=result.getString("Matricule");
                    tabInfosEnseignats[1]=result.getString("Noms");
                    tabInfosEnseignats[2]=result.getString("Prenoms");
                    tabInfosEnseignats[3]=result.getString("Sexe");
                    tabInfosEnseignats[4]=result.getString("Adresse_Mail");
                    tabInfosEnseignats[5]="OK";
                    tabInformations.addRow(tabInfosEnseignats);
                    compteur++;
                }
                if(compteur==0){
                    JOptionPane.showMessageDialog(rootPane, "Aucun client ne correspond à votre recherche");
                }
        }catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_searchIconMouseClicked

    private void comboTriItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboTriItemStateChanged
        // TODO add your handling code here:
        if(((String)comboTri.getSelectedItem()).equals("")){
            try{
                result = statement.executeQuery("SELECT * FROM clients WHERE Categorie='Enseignant' AND Date_suppression IS NULL ORDER BY Noms");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            try{
                result = statement.executeQuery("SELECT * FROM clients WHERE Categorie='Enseignant' AND Date_suppression IS NULL ORDER BY "+(String)comboTri.getSelectedItem()+", Noms");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        this.tabInfosEnseignats = new Object [6];
                DefaultTableModel tabInformations = (DefaultTableModel)tableEnseignants.getModel();
                for(int i=tabInformations.getRowCount()-1; i>=0; i--){
                    tabInformations.removeRow(i);
                }
                try{
                    while(result.next()){
                        tabInfosEnseignats[0]=result.getString("Matricule");
                        tabInfosEnseignats[1]=result.getString("Noms");
                        tabInfosEnseignats[2]=result.getString("Prenoms");
                        tabInfosEnseignats[3]=result.getString("Sexe");
                        tabInfosEnseignats[4]=result.getString("Adresse_Mail");
                        tabInfosEnseignats[5]="OK";
                        tabInformations.addRow(tabInfosEnseignats);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
    }//GEN-LAST:event_comboTriItemStateChanged

    private void toogleClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleClientsActionPerformed
        // TODO add your handling code here:
        if(toogleClients.isSelected()){
            toogleClients.setSelected(true);
            toogleArticles.setSelected(false);
            tooglePrets.setSelected(false);
        }
    }//GEN-LAST:event_toogleClientsActionPerformed

    private void tooglePretsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tooglePretsActionPerformed
        // TODO add your handling code here:
        if(tooglePrets.isSelected()){
            tooglePrets.setSelected(true);
            toogleClients.setSelected(false);
            toogleArticles.setSelected(false);
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
            java.util.logging.Logger.getLogger(InterfaceListeClientsEtudiants.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfaceListeClientsEtudiants.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfaceListeClientsEtudiants.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceListeClientsEtudiants.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfaceListeClientsEnseignants(langue).setVisible(true);
            }
        });
    }
    private Object tabInfosEnseignats[];
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    static int langue;
    private String article[];
    private String client[];
    private String pret[];
    private String listeCli[];
    private String ajouterUnCli[];
    private String recherch[];
    private String trierPar[];
    private String enseignant[];
    private String optionEntrerEmplac[];
    private String optionCeClientNe[];
    private String optionDateEmprun[];
    private String optionEmpruntSucces[];
    private String optionPlusDeQuatreArti[];
    private String auteu[];
    private String maisonD[];
    private String anneePub[];
    private String emplacem[];
    private String domain[];
    private String categ[];
    private String optionPourValider[];
    private String optionAucunArticle[];
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Edit;
    private javax.swing.JMenu Main;
    private javax.swing.JComboBox<String> comboTri;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenuArticles;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenu jMenuClients;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblTri;
    private javax.swing.JTextField recherche;
    private javax.swing.JLabel searchIcon;
    private javax.swing.JTable tableEnseignants;
    private javax.swing.JToggleButton toogleAjoutClient;
    private javax.swing.JToggleButton toogleArticles;
    private javax.swing.JToggleButton toogleCDN;
    private javax.swing.JToggleButton toogleClients;
    private javax.swing.JToggleButton toogleEnseignants;
    private javax.swing.JToggleButton toogleINS;
    private javax.swing.JToggleButton toogleISN;
    private javax.swing.JToggleButton toogleListeClients;
    private javax.swing.JToggleButton tooglePrets;
    // End of variables declaration//GEN-END:variables
}

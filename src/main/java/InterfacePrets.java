
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
public class InterfacePrets extends javax.swing.JFrame {

    /**
     * Creates new form InterfacePrets
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
            try{
                result = statement.executeQuery("SELECT * FROM emprunts, clients, articles WHERE Matricule=Matricule_Emprunteur AND Emplacement_Article='"+tableEmprunts.getValueAt(tableEmprunts.getSelectedRow(), 4)+"' AND Date_remise IS NULL AND Matricule_Emprunteur='"+tableEmprunts.getValueAt(tableEmprunts.getSelectedRow(),0)+"' AND clients.Date_suppression IS NULL AND articles.Date_suppression IS NULL");
                result.next();
                String validationPret= JOptionPane.showInputDialog(optionRemisePar[langue]+result.getString("Noms")+" "+result.getString("Prenoms")+optionLarticleAuxCaract[langue]+result.getString("Titre")+categ[langue]+result.getString("Categorie")+domain[langue]+result.getString("Domaine")+auteu[langue]+result.getString("Auteur")+maisonD[langue]+result.getString("Maison_edition")+anneePub[langue]+result.getInt("Date_publication")+emplacem[langue]+result.getString("Emplacement")+optionValiderLaRemise[langue]);
                if(validationPret!=null){
                    if(validationPret.equals(tableEmprunts.getValueAt(tableEmprunts.getSelectedRow(), 0))){
                        statement.executeUpdate("UPDATE emprunts SET Date_remise=NOW() WHERE Matricule_Emprunteur='"+validationPret+"' AND Emplacement_Article='"+tableEmprunts.getValueAt(tableEmprunts.getSelectedRow(), 4)+"' AND Date_remise IS NULL");
                        JOptionPane.showMessageDialog(editorComponent, optionRemiseSucces[langue]);
                        ((DefaultTableModel)tableEmprunts.getModel()).removeRow(tableEmprunts.getSelectedRow());
                    }
                    else{
                        JOptionPane.showMessageDialog(editorComponent, optionMatriculeNonCores[langue]);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
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
    
    
    public InterfacePrets(int lang) {
        initComponents();
        langue = lang;
        String title[] = new String[]{"Liste des prêts","Loans list"};
        setTitle(title[langue]);
        try{
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
        listeEmprunte = new String[2];
            listeEmprunte[0] = "Liste des articles";
            listeEmprunte[1] = "Items list";
            toogleListeEmprunteurs.setText(listeEmprunte[langue]);
        listeNoir = new String[2];
            listeNoir[0] = "Liste noire";
            listeNoir[1] = "Black list";
            toogleListeNoire.setText(listeNoir[langue]);
        recherch = new String[2];
            recherch[0] = "Rechercher";
            recherch[1] = "Search";
            recherche.setText(recherch[langue]);
        trierPar = new String[2];
            trierPar[0] = "Trier par :";
            trierPar[1] = "Sort by :";
            lblTri.setText(trierPar[langue]);
        formul = new String[2];
            formul[0] = "LISTE DES PERSONNES AYANT EMPRUNTES ET PAS ENCORE RENDUS ";
            formul[1] = "LIST OF CUSTOMERS WHO BORROWED AN ITEM AND DIDN'T GIVE IT BACK YET";
            lblFormul.setText(formul[langue]);
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
        optionRemiseSucces = new String[2];
            optionRemiseSucces[0] = "Remise effectuée avec succès !";
            optionRemiseSucces[1] = "Item successfully returned !";
        optionMatriculeNonCores = new String[2];
            optionMatriculeNonCores[0] = "Le matricule que vous venez d'entrer ne correspond pas à celui avec lequel a été initiée la demande. Veuillez ressayer !";
            optionMatriculeNonCores[1] = "The matricule you have provided doesn't match with the one which has initiated the request. Please try again !";
        optionRemisePar = new String[2];
            optionRemisePar[0] = "Remise par ";
            optionRemisePar[1] = "Returned by ";
        optionLarticleAuxCaract = new String[2];
            optionLarticleAuxCaract[0] = " de l'aricle aux caractéristiques suivantes :\n Titre: ";
            optionLarticleAuxCaract[1] = " , of the item with the following characteristics :\n Title: ";
        optionValiderLaRemise = new String[2];
            optionValiderLaRemise[0] = "\n\n Pour valider la remise, veuillez entrer le matricule du client";
            optionValiderLaRemise[1] = "\n\n To confirm this return, please enter the customer matricule";
            
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/lms_g8", "root","");
            statement = connection.createStatement();
            
            this.tabInfosEmprunts = new Object[7];
            DefaultTableModel tabInformations = (DefaultTableModel)tableEmprunts.getModel();
            tableEmprunts.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());;
            tableEmprunts.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
            result = statement.executeQuery("SELECT *, DATE_FORMAT(Date_emprunt,'%d/%m/%Y, %H:%i') as date FROM emprunts, clients, articles WHERE Matricule_Emprunteur=Matricule AND Emplacement_Article=Emplacement AND Date_remise IS NULL AND clients.Date_suppression IS NULL  AND articles.Date_suppression IS NULL ORDER BY date_emprunt DESC");
            researchField = false;
            while(result.next()){
                tabInfosEmprunts[0] = result.getString("Matricule");
                tabInfosEmprunts[1] = result.getString("Noms");
                tabInfosEmprunts[2] = result.getString("Prenoms");
                tabInfosEmprunts[3] = result.getString("Titre");
                tabInfosEmprunts[4] = result.getString("Emplacement");
                tabInfosEmprunts[5] = result.getString("date");
                tabInfosEmprunts[6] = "OK";
                tabInformations.addRow(tabInfosEmprunts);
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
        toogleListeEmprunteurs = new javax.swing.JToggleButton();
        toogleListeNoire = new javax.swing.JToggleButton();
        recherche = new javax.swing.JTextField();
        searchIcon = new javax.swing.JLabel();
        lblTri = new javax.swing.JLabel();
        comboTri = new javax.swing.JComboBox<>();
        lblFormul = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEmprunts = new javax.swing.JTable();
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
        getContentPane().add(toogleArticles, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 90, -1));

        toogleClients.setText("Clients");
        toogleClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleClientsActionPerformed(evt);
            }
        });
        getContentPane().add(toogleClients, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 90, -1));

        tooglePrets.setSelected(true);
        tooglePrets.setText("Prets");
        tooglePrets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tooglePretsActionPerformed(evt);
            }
        });
        getContentPane().add(tooglePrets, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 90, -1));

        toogleListeEmprunteurs.setSelected(true);
        toogleListeEmprunteurs.setText("Liste des emprunteurs");
        toogleListeEmprunteurs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleListeEmprunteursActionPerformed(evt);
            }
        });
        getContentPane().add(toogleListeEmprunteurs, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 130, -1, -1));

        toogleListeNoire.setText("Liste noire");
        toogleListeNoire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleListeNoireActionPerformed(evt);
            }
        });
        getContentPane().add(toogleListeNoire, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 180, -1, -1));

        recherche.setText("Rechercher");
        recherche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rechercheActionPerformed(evt);
            }
        });
        getContentPane().add(recherche, new org.netbeans.lib.awtextra.AbsoluteConstraints(364, 20, 110, -1));

        searchIcon.setIcon(new javax.swing.ImageIcon("C:\\wamp64\\www\\Code\\photos\\search.png")); // NOI18N
        searchIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchIconMouseClicked(evt);
            }
        });
        getContentPane().add(searchIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, -1, -1));

        lblTri.setText("Trier par :");
        getContentPane().add(lblTri, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 20, -1, 20));

        comboTri.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Matricule", "Emplacement" }));
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
        getContentPane().add(comboTri, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 100, -1));

        lblFormul.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblFormul.setText("LISTE DES PERSONNES AYANT EMPRUNTES ET PAS ENCORE RENDUS ");
        getContentPane().add(lblFormul, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, -1, -1));

        tableEmprunts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Matricule", "Noms", "Prenoms", "Titre de l'article emprunté", "Emplacement", "Date d'emprunt", "Rendre ?"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableEmprunts);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 570, 370));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\MARTIAL\\Documents\\NetBeansProjects\\LMS\\src\\main\\java\\images\\clientA.png")); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 490));

        Main.setText("Main");
        jMenuBar3.add(Main);

        Edit.setText("Modifier");

        jMenuClients.setText("Clients");
        Edit.add(jMenuClients);

        jMenuArticles.setText("Emplacement articles");
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

    private void toogleListeNoireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleListeNoireActionPerformed
        // TODO add your handling code here:
        if(toogleListeNoire.isSelected()){
            toogleListeNoire.setSelected(true);
            toogleListeEmprunteurs.setSelected(false);
            InterfaceListeNoire listeNoire = new InterfaceListeNoire(langue);
            listeNoire.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_toogleListeNoireActionPerformed

    private void toogleListeEmprunteursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleListeEmprunteursActionPerformed
        // TODO add your handling code here:
        if(toogleListeEmprunteurs.isSelected()){
            toogleListeEmprunteurs.setSelected(true);
            toogleListeNoire.setSelected(false);
            researchField = false;
            try{
                this.tabInfosEmprunts = new Object[7];
                DefaultTableModel tabInformations = (DefaultTableModel)tableEmprunts.getModel();
                tableEmprunts.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());;
                tableEmprunts.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
                result = statement.executeQuery("SELECT *, DATE_FORMAT(Date_emprunt,'%d/%m/%Y, %H:%i') as date FROM emprunts, clients, articles WHERE Matricule_Emprunteur=Matricule AND Emplacement_Article=Emplacement AND Date_remise IS NULL AND articles.Date_suppression IS NULL  AND clients.Date_suppression IS NULL ORDER BY date_emprunt DESC");
                researchField = false;
                for(int i=tabInformations.getRowCount()-1; i>=0; i--){
                    tabInformations.removeRow(i);
                }
                while(result.next()){
                    tabInfosEmprunts[0] = result.getString("Matricule");
                    tabInfosEmprunts[1] = result.getString("Noms");
                    tabInfosEmprunts[2] = result.getString("Prenoms");
                    tabInfosEmprunts[3] = result.getString("Titre");
                    tabInfosEmprunts[4] = result.getString("Emplacement");
                    tabInfosEmprunts[5] = result.getString("date");
                    tabInfosEmprunts[6] = "OK";
                    tabInformations.addRow(tabInfosEmprunts);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_toogleListeEmprunteursActionPerformed

    private void rechercheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rechercheActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rechercheActionPerformed

    private void comboTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTriActionPerformed

    private void searchIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchIconMouseClicked
        // TODO add your handling code here:
        try{
            DefaultTableModel tabInformations = (DefaultTableModel)tableEmprunts.getModel();
            tableEmprunts.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());;
            tableEmprunts.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
            result = statement.executeQuery("SELECT *, DATE_FORMAT(Date_emprunt,'%d/%m/%Y, %H:%i') as date FROM emprunts, clients, articles WHERE Matricule_Emprunteur=Matricule AND Emplacement_Article=Emplacement AND Date_remise IS NULL AND (Matricule LIKE '"+recherche.getText()+"%' OR Noms LIKE '"+recherche.getText()+"%' OR Prenoms LIKE '"+recherche.getText()+"%' OR Titre LIKE '"+recherche.getText()+"%' OR Date_emprunt LIKE '%"+recherche.getText()+"%' OR Emplacement LIKE '"+recherche.getText()+"%') AND Date_suppression IS NULL ORDER BY date_emprunt DESC");
            for(int i=tabInformations.getRowCount()-1; i>=0; i--){
                tabInformations.removeRow(i);
            }
            researchField = true;
            while(result.next()){
                tabInfosEmprunts[0] = result.getString("Matricule");
                tabInfosEmprunts[1] = result.getString("Noms");
                tabInfosEmprunts[2] = result.getString("Prenoms");
                tabInfosEmprunts[3] = result.getString("Titre");
                tabInfosEmprunts[4] = result.getString("Emplacement");
                tabInfosEmprunts[5] = result.getString("date");
                tabInfosEmprunts[6] = "OK";
                tabInformations.addRow(tabInfosEmprunts);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_searchIconMouseClicked

    private void tooglePretsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tooglePretsActionPerformed
        // TODO add your handling code here:
        if(tooglePrets.isSelected()){
            toogleClients.setSelected(false);
            toogleArticles.setSelected(false);
            tooglePrets.setSelected(true);
        }
    }//GEN-LAST:event_tooglePretsActionPerformed

    private void toogleClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleClientsActionPerformed
        // TODO add your handling code here:
        if(toogleClients.isSelected()){
            toogleClients.setSelected(true);
            toogleArticles.setSelected(false);
            tooglePrets.setSelected(false);
            InterfaceListeClientsEtudiants listeClients = new InterfaceListeClientsEtudiants("", langue);
            listeClients.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_toogleClientsActionPerformed

    private void comboTriItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboTriItemStateChanged
        // TODO add your handling code here:
        if(researchField==true){
            if(((String)comboTri.getSelectedItem()).equals("")){
                try{
                    DefaultTableModel tabInformations = (DefaultTableModel)tableEmprunts.getModel();
                    tableEmprunts.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());;
                    tableEmprunts.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
                    result = statement.executeQuery("SELECT *, DATE_FORMAT(Date_emprunt,'%d/%m/%Y, %H:%i') as date FROM emprunts, clients, articles WHERE Matricule_Emprunteur=Matricule AND Emplacement_Article=Emplacement AND Date_remise IS NULL AND (Matricule LIKE '"+recherche.getText()+"%' OR Noms LIKE '"+recherche.getText()+"%' OR Prenoms LIKE '"+recherche.getText()+"%' OR Titre LIKE '"+recherche.getText()+"%' OR Date_emprunt LIKE '%"+recherche.getText()+"%' OR Emplacement LIKE '"+recherche.getText()+"%') AND articles.Date_suppression IS NULL AND clients.Date_suppression IS NULL ORDER BY date_emprunt DESC");
                    for(int i=tabInformations.getRowCount()-1; i>=0; i--){
                        tabInformations.removeRow(i);
                    }
                    researchField = true;
                    while(result.next()){
                        tabInfosEmprunts[0] = result.getString("Matricule");
                        tabInfosEmprunts[1] = result.getString("Noms");
                        tabInfosEmprunts[2] = result.getString("Prenoms");
                        tabInfosEmprunts[3] = result.getString("Titre");
                        tabInfosEmprunts[4] = result.getString("Emplacement");
                        tabInfosEmprunts[5] = result.getString("date");
                        tabInfosEmprunts[6] = "OK";
                        tabInformations.addRow(tabInfosEmprunts);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            
            else{
                try{
                    DefaultTableModel tabInformations = (DefaultTableModel)tableEmprunts.getModel();
                    tableEmprunts.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());;
                    tableEmprunts.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
                    result = statement.executeQuery("SELECT *, DATE_FORMAT(Date_emprunt,'%d/%m/%Y, %H:%i') as date FROM emprunts, clients, articles WHERE Matricule_Emprunteur=Matricule AND Emplacement_Article=Emplacement AND Date_remise IS NULL AND (Matricule LIKE '"+recherche.getText()+"%' OR Noms LIKE '"+recherche.getText()+"%' OR Prenoms LIKE '"+recherche.getText()+"%' OR Titre LIKE '"+recherche.getText()+"%' OR Date_emprunt LIKE '%"+recherche.getText()+"%' OR Emplacement LIKE '"+recherche.getText()+"%') AND articles.Date_suppression IS NULL AND clients.Date_suppression IS NULL ORDER BY "+(String)comboTri.getSelectedItem()+", date_emprunt DESC");
                    for(int i=tabInformations.getRowCount()-1; i>=0; i--){
                        tabInformations.removeRow(i);
                    }
                    researchField = true;
                    while(result.next()){
                        tabInfosEmprunts[0] = result.getString("Matricule");
                        tabInfosEmprunts[1] = result.getString("Noms");
                        tabInfosEmprunts[2] = result.getString("Prenoms");
                        tabInfosEmprunts[3] = result.getString("Titre");
                        tabInfosEmprunts[4] = result.getString("Emplacement");
                        tabInfosEmprunts[5] = result.getString("date");
                        tabInfosEmprunts[6] = "OK";
                        tabInformations.addRow(tabInfosEmprunts);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        else{
            if(((String)comboTri.getSelectedItem()).equals("")){
                try{
                    this.tabInfosEmprunts = new Object[7];
                    DefaultTableModel tabInformations = (DefaultTableModel)tableEmprunts.getModel();
                    tableEmprunts.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());;
                    tableEmprunts.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
                    result = statement.executeQuery("SELECT *, DATE_FORMAT(Date_emprunt,'%d/%m/%Y, %H:%i') as date FROM emprunts, clients, articles WHERE Matricule_Emprunteur=Matricule AND Emplacement_Article=Emplacement AND Date_remise IS NULL AND clients.Date_suppression IS NULL  AND articles.Date_suppression IS NULL ORDER BY date_emprunt DESC");
                    researchField = false;
                    for(int i=tabInformations.getRowCount()-1; i>=0; i--){
                        tabInformations.removeRow(i);
                    }
                    while(result.next()){
                        tabInfosEmprunts[0] = result.getString("Matricule");
                        tabInfosEmprunts[1] = result.getString("Noms");
                        tabInfosEmprunts[2] = result.getString("Prenoms");
                        tabInfosEmprunts[3] = result.getString("Titre");
                        tabInfosEmprunts[4] = result.getString("Emplacement");
                        tabInfosEmprunts[5] = result.getString("date");
                        tabInfosEmprunts[6] = "OK";
                        tabInformations.addRow(tabInfosEmprunts);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{
                try{
                    this.tabInfosEmprunts = new Object[7];
                    DefaultTableModel tabInformations = (DefaultTableModel)tableEmprunts.getModel();
                    tableEmprunts.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());;
                    tableEmprunts.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
                    result = statement.executeQuery("SELECT *, DATE_FORMAT(Date_emprunt,'%d/%m/%Y, %H:%i') as date FROM emprunts, clients, articles WHERE Matricule_Emprunteur=Matricule AND Emplacement_Article=Emplacement AND Date_remise IS NULL AND clients.Date_suppression IS NULL  AND articles.Date_suppression IS NULL ORDER BY "+(String)comboTri.getSelectedItem()+", date_emprunt DESC");
                    researchField = false;
                    for(int i=tabInformations.getRowCount()-1; i>=0; i--){
                        tabInformations.removeRow(i);
                    }
                    while(result.next()){
                        tabInfosEmprunts[0] = result.getString("Matricule");
                        tabInfosEmprunts[1] = result.getString("Noms");
                        tabInfosEmprunts[2] = result.getString("Prenoms");
                        tabInfosEmprunts[3] = result.getString("Titre");
                        tabInfosEmprunts[4] = result.getString("Emplacement");
                        tabInfosEmprunts[5] = result.getString("date");
                        tabInfosEmprunts[6] = "OK";
                        tabInformations.addRow(tabInfosEmprunts);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_comboTriItemStateChanged

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
            java.util.logging.Logger.getLogger(InterfacePrets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfacePrets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfacePrets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfacePrets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfacePrets(langue).setVisible(true);
            }
        });
    }
    
    private Object tabInfosEmprunts[];
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private boolean researchField;
    static int langue;
    private String article[];
    private String client[];
    private String pret[];
    private String listeEmprunte[];
    private String listeNoir[];
    private String recherch[];
    private String trierPar[];
    private String formul[];
    private String optionRemiseSucces[];
    private String optionMatriculeNonCores[];
    private String optionRemisePar[];
    private String optionLarticleAuxCaract[];
    private String auteu[];
    private String maisonD[];
    private String anneePub[];
    private String emplacem[];
    private String domain[];
    private String categ[];
    private String optionValiderLaRemise[];
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Edit;
    private javax.swing.JMenu Main;
    private javax.swing.JComboBox<String> comboTri;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenuArticles;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenu jMenuClients;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFormul;
    private javax.swing.JLabel lblTri;
    private javax.swing.JTextField recherche;
    private javax.swing.JLabel searchIcon;
    private javax.swing.JTable tableEmprunts;
    private javax.swing.JToggleButton toogleArticles;
    private javax.swing.JToggleButton toogleClients;
    private javax.swing.JToggleButton toogleListeEmprunteurs;
    private javax.swing.JToggleButton toogleListeNoire;
    private javax.swing.JToggleButton tooglePrets;
    // End of variables declaration//GEN-END:variables
}


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
import static javax.swing.JOptionPane.YES_NO_OPTION;
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
public class InterfaceListeClientsModifiable extends javax.swing.JFrame {

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
            if(tableEnseignants.getSelectedColumn()==5){
                try{
                    result = statement.executeQuery("SELECT * FROM clients WHERE Matricule='"+tableEnseignants.getValueAt(tableEnseignants.getSelectedRow(), 0)+"' AND Date_suppression IS NULL");
                    result.next();
                    InterfaceModificationClients modificationClients = new InterfaceModificationClients(result.getString("Categorie"), result.getString("Matricule"), result.getString("Noms"), result.getString("Prenoms"), result.getString("Sexe"), result.getString("Filiere"), result.getInt("Niveau"), result.getString("Adresse_mail"), langue);
                    modificationClients.setVisible(true);
                    listeEtudiants.dispose();
                }catch(Exception e){
                    
                }
            }
            else if(tableEnseignants.getSelectedColumn()==6){
                int supprimer = JOptionPane.showConfirmDialog(editorComponent, optionVoulezVousSup[langue]+tableEnseignants.getValueAt(tableEnseignants.getSelectedRow(), 1)+" "+tableEnseignants.getValueAt(tableEnseignants.getSelectedRow(), 2)+optionDeLaBD[langue], confirmSupp[langue], YES_NO_OPTION);
                if(supprimer==JOptionPane.YES_OPTION){
                    try{
                        statement.executeUpdate("UPDATE clients SET Date_suppression=NOW() WHERE Matricule='"+tableEnseignants.getValueAt(tableEnseignants.getSelectedRow(), 0)+"' AND Date_suppression IS NULL");
                        JOptionPane.showMessageDialog(editorComponent, optionSuppSucces[langue]);
                        ((DefaultTableModel)tableEnseignants.getModel()).removeRow(tableEnseignants.getSelectedRow());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
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
    
    public InterfaceListeClientsModifiable(int lang){
        initComponents();
        langue = lang;
        String title[] = new String[]{"Liste des clients(Modifier)","Customers list(Edit)"};
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
            listeCli[1] = "Items list";
            toogleListeClients.setText(listeCli[langue]);
        ajouterUnCli = new String[2];
            ajouterUnCli[0] = "Ajouter un client";
            ajouterUnCli[1] = "Add a customer";
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
        optionSuppSucces = new String[2];
            optionSuppSucces[0] = "Suppression effectuée avec succès !";
            optionSuppSucces[1] = "The customer has been successfully removed !";
        optionVoulezVousSup = new String[2];
            optionVoulezVousSup[0] = "Voulez vous vraiment supprimer le client ";
            optionVoulezVousSup[1] = "Do you really want to remove ";
        optionDeLaBD = new String[2];
            optionDeLaBD[0] = " de la base de données ?\n NB: Cette action est irreversible !";
            optionDeLaBD[1] = " from the database ?\n Warning: This action is irreversible !";
        confirmSupp = new String[2];
            confirmSupp[0] = "Confirmation suppression";
            confirmSupp[1] = "Deletion confirmation";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/lms_g8", "root", "");
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM clients WHERE Date_suppression IS NULL ORDER BY Noms");
                this.tabInfosEnseignats = new Object [7];
                DefaultTableModel tabInformations = (DefaultTableModel)tableEnseignants.getModel();
                tableEnseignants.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
                tableEnseignants.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));
                tableEnseignants.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
                tableEnseignants.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
                while(result.next()){
                    tabInfosEnseignats[0]=result.getString("Matricule");
                    tabInfosEnseignats[1]=result.getString("Noms");
                    tabInfosEnseignats[2]=result.getString("Prenoms");
                    tabInfosEnseignats[3]=result.getString("Sexe");
                    tabInfosEnseignats[4]=result.getString("Adresse_Mail");
                    tabInfosEnseignats[5]="OK";
                    tabInfosEnseignats[6]="OK";
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
        recherche = new javax.swing.JTextField();
        searchIcon = new javax.swing.JLabel();
        lblTri = new javax.swing.JLabel();
        comboTri = new javax.swing.JComboBox<>();
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
        getContentPane().add(toogleArticles, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 90, -1));

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
        getContentPane().add(tooglePrets, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 90, -1));

        toogleListeClients.setSelected(true);
        toogleListeClients.setText("Liste des clients");
        toogleListeClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleListeClientsActionPerformed(evt);
            }
        });
        getContentPane().add(toogleListeClients, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 80, 110, -1));

        toogleAjoutClient.setText("Ajouter un client");
        toogleAjoutClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleAjoutClientActionPerformed(evt);
            }
        });
        getContentPane().add(toogleAjoutClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 140, -1, -1));

        recherche.setText("Rechercher");
        recherche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rechercheActionPerformed(evt);
            }
        });
        getContentPane().add(recherche, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 121, -1));

        searchIcon.setIcon(new javax.swing.ImageIcon("C:\\wamp64\\www\\Code\\photos\\search.png")); // NOI18N
        searchIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchIconMouseClicked(evt);
            }
        });
        getContentPane().add(searchIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, -1, -1));

        lblTri.setText("Trier par :");
        getContentPane().add(lblTri, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, -1, 20));

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
        getContentPane().add(comboTri, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 100, -1));

        tableEnseignants.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Matricule", "Noms", "Prénoms", "Sexe", "Adresse mail", "Modifier informations ?", "Supprimer ?"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableEnseignants);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 560, 410));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\MARTIAL\\Documents\\NetBeansProjects\\LMS\\src\\main\\java\\images\\clientA.png")); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 490));

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
            InterfaceListeArticlesModifiable listeArticlesModifiables = new InterfaceListeArticlesModifiable("", langue);
            listeArticlesModifiables.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_toogleArticlesActionPerformed

    private void rechercheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rechercheActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rechercheActionPerformed

    private void comboTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTriActionPerformed

    private void searchIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchIconMouseClicked
        // TODO add your handling code here:
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/lms_g8", "root", "");
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM clients WHERE (Categorie='"+recherche.getText()+"' OR Noms LIKE'%"+recherche.getText()+"%' OR Prenoms LIKE '%"+recherche.getText()+"%' OR Sexe LIKE '"+recherche.getText()+"%' OR Adresse_mail LIKE '%"+recherche.getText()+"%') AND Date_suppression IS NULL ORDER BY Noms");
                this.tabInfosEnseignats = new Object [7];
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
                    tabInfosEnseignats[6]="OK";
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
                result = statement.executeQuery("SELECT * FROM clients WHERE AND Date_suppression IS NULL ORDER BY Noms");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            try{
                result = statement.executeQuery("SELECT * FROM clients WHERE AND Date_suppression IS NULL ORDER BY "+(String)comboTri.getSelectedItem()+", Noms");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        this.tabInfosEnseignats = new Object [7];
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
                        tabInfosEnseignats[6]="OK";
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
            toogleClients.setSelected(false);
            toogleArticles.setSelected(false);
            tooglePrets.setSelected(true);
            InterfacePrets listeEmprunts = new InterfacePrets(langue);
            listeEmprunts.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_tooglePretsActionPerformed

    private void toogleListeClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleListeClientsActionPerformed
        // TODO add your handling code here:
        if(toogleListeClients.isSelected()){
            toogleListeClients.setSelected(true);
            toogleAjoutClient.setSelected(false);
        }
    }//GEN-LAST:event_toogleListeClientsActionPerformed

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
                listeEtudiants = new InterfaceListeClientsModifiable(langue);
                listeEtudiants.setVisible(true);
            }
        });
    }
    private Object tabInfosEnseignats[];
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    static InterfaceListeClientsModifiable listeEtudiants;
    static int langue;
    private String article[];
    private String client[];
    private String pret[];
    private String listeCli[];
    private String ajouterUnCli[];
    private String recherch[];
    private String trierPar[];
    private String enseignant[];
    private String optionSuppSucces[];
    private String optionVoulezVousSup[];
    private String optionDeLaBD[];
    private String confirmSupp[];
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
    private javax.swing.JToggleButton toogleClients;
    private javax.swing.JToggleButton toogleListeClients;
    private javax.swing.JToggleButton tooglePrets;
    // End of variables declaration//GEN-END:variables
}

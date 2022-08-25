

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Iterator;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import static jdk.jfr.consumer.EventStream.openFile;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author MARTIAL
 */
public class InterfaceListeNoire extends javax.swing.JFrame {

    /**
     * Creates new form InterfaceListeNoire
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
            String matricule = JOptionPane.showInputDialog(optionEntrerMatric[langue]);
            if(matricule!=null){
                if(matricule.equals(tableListeNoire.getValueAt(tableListeNoire.getSelectedRow(), 0))){
                    String montant = JOptionPane.showInputDialog(optionEntrerTota[langue]);
                    if(montant!=null){
                        boolean conversionValide = true;
                        int montantEntier;
                        try{
                            montantEntier = Integer.parseInt(montant);
                            if(montantEntier==(int)(tableListeNoire.getValueAt(tableListeNoire.getSelectedRow(), 3))){
                                try{
                                    statement.executeUpdate("UPDATE liste_noire SET Date_de_sortie=NOW() WHERE Matricule_client='"+tableListeNoire.getValueAt(tableListeNoire.getSelectedRow(), 0)+"' AND Date_de_sortie IS NULL");
                                    JOptionPane.showMessageDialog(editorComponent, optionPaiementEffect[langue]);
                                }catch(Exception ex){
                                    ex.printStackTrace();
                                }
                            }
                        }catch(Exception e){
                            conversionValide=false;
                            JOptionPane.showMessageDialog(editorComponent, optionLeMontantEntre[langue]);
                        }
                        if(conversionValide==true){
                            
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(editorComponent, optionMatriculeNonCorres[langue]);
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
    
    
    public InterfaceListeNoire(int lang) {
        initComponents();
        langue = lang;
        String title[] = new String[]{"Clients suspendus","Suspended customers"};
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
        listeEmprunte = new String[2];
            listeEmprunte[0] = "Liste des articles";
            listeEmprunte[1] = "Items list";
            toogleEmprunteurs.setText(listeEmprunte[langue]);
        listeNoir = new String[2];
            listeNoir[0] = "Liste noire";
            listeNoir[1] = "Black list";
            toogleListeNoire.setText(listeNoir[langue]);
        recherch = new String[2];
            recherch[0] = "Rechercher";
            recherch[1] = "Search";
            recherche.setText(recherch[langue]);
        trierPar = new String[2];
            trierPar[0] = "Classer par :";
            trierPar[1] = "Order by :";
            lblTri.setText(trierPar[langue]);
        formul = new String[2];
            formul[0] = "LISTE DES PERSONNES N'AYANT PLUS ACCES AUX SERVICES ";
            formul[1] = "SUSPENDED CUSTOMERS LIST";
            lblFormul.setText(formul[langue]);
        optionEntrerMatric = new String[2];
            optionEntrerMatric[0] = "Veuillez entrer le matricule du client";
            optionEntrerMatric[1] = "Please enter the customer matricule";
        optionEntrerTota = new String[2];
            optionEntrerTota[0] = "Veuillez entrer la totalité de la somme à payer. \n NB: La dette est payée en une fois, et en totalité.";
            optionEntrerTota[1] = "Please enter the full amount to be baid. \n Warning: The debt is paid at one, in his totality.";
        optionPaiementEffect = new String[2];
            optionPaiementEffect[0] = "Paiement effectué avec succès !";
            optionPaiementEffect[1] = "Payment successfully made !";
        optionMatriculeNonCorres = new String[2];
            optionMatriculeNonCorres[0] = "Le matricule que vous avez entré ne correspond pas à celui avec lequel la demande a été initiée. Veuillez ressayer !";
            optionMatriculeNonCorres[0] = "The matricule you provided doesn't match with the one which initialised the request. Please try again !";
        optionLeMontantEntre = new String[2];
            optionLeMontantEntre[0] = "Le montant entré ne correspond à celui dû, veuillez entrer le montant total à payer !";
            optionLeMontantEntre[1] = "The provided amount doesn't match with the one to be paid. Please enter the full amount to be paid !";
        optionAucunArti = new String[2];
            optionAucunArti[0] = "Aucun article ne correspond à votre recherche";
            optionAucunArti[1] = "No items matching your search";
        exportationReussie = new String[2];
            exportationReussie[0] = "Exportation éffectuée avec succès !";
            exportationReussie[1] = "Export successfully completed !";
        try{
            researchResult = false;
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/lms_g8", "root", "");
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM liste_noire, clients WHERE  Matricule=Matricule_client AND Date_de_sortie IS NULL AND Date_suppression IS NULL ORDER BY Noms");
                this.tabInfosSuspendus = new Object [5];
                DefaultTableModel tabInformations = (DefaultTableModel)tableListeNoire.getModel();
                tableListeNoire.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
                tableListeNoire.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));
                while(result.next()){
                    tabInfosSuspendus[0]=result.getString("Matricule_client");
                    tabInfosSuspendus[1]=result.getString("Noms");
                    tabInfosSuspendus[2]=result.getString("Prenoms");
                    tabInfosSuspendus[3]=result.getInt("Dette");
                    tabInfosSuspendus[4]="OK";
                    tabInformations.addRow(tabInfosSuspendus);
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

        toogleClients = new javax.swing.JToggleButton();
        toogleArticles = new javax.swing.JToggleButton();
        tooglePrets = new javax.swing.JToggleButton();
        toogleEmprunteurs = new javax.swing.JToggleButton();
        toogleListeNoire = new javax.swing.JToggleButton();
        comboTri = new javax.swing.JComboBox<>();
        lblTri = new javax.swing.JLabel();
        searchIcon = new javax.swing.JLabel();
        recherche = new javax.swing.JTextField();
        lblFormul = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableListeNoire = new javax.swing.JTable();
        exportButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar3 = new javax.swing.JMenuBar();
        Main = new javax.swing.JMenu();
        Edit = new javax.swing.JMenu();
        jMenuClients = new javax.swing.JMenu();
        jMenuArticles = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        toogleClients.setText("Clients");
        toogleClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleClientsActionPerformed(evt);
            }
        });
        getContentPane().add(toogleClients, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 90, -1));

        toogleArticles.setText("Articles");
        toogleArticles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleArticlesActionPerformed(evt);
            }
        });
        getContentPane().add(toogleArticles, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 90, -1));

        tooglePrets.setSelected(true);
        tooglePrets.setText("Prets");
        tooglePrets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tooglePretsActionPerformed(evt);
            }
        });
        getContentPane().add(tooglePrets, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 90, -1));

        toogleEmprunteurs.setText("Liste des emprunteurs");
        toogleEmprunteurs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleEmprunteursActionPerformed(evt);
            }
        });
        getContentPane().add(toogleEmprunteurs, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, -1, -1));

        toogleListeNoire.setSelected(true);
        toogleListeNoire.setText("Liste noire");
        toogleListeNoire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleListeNoireActionPerformed(evt);
            }
        });
        getContentPane().add(toogleListeNoire, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 150, 90, -1));

        comboTri.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Dette croissante", "Dette décroissante" }));
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
        getContentPane().add(comboTri, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 77, -1));

        lblTri.setText("Classer par :");
        getContentPane().add(lblTri, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, -1, -1));

        searchIcon.setIcon(new javax.swing.ImageIcon("C:\\wamp64\\www\\Code\\photos\\search.png")); // NOI18N
        searchIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchIconMouseClicked(evt);
            }
        });
        getContentPane().add(searchIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, -1, -1));

        recherche.setText("Rechercher");
        recherche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rechercheActionPerformed(evt);
            }
        });
        getContentPane().add(recherche, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 105, -1));

        lblFormul.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblFormul.setText("LISTE DES PERSONNES N'AYANT PLUS ACCES AUX SERVICES");
        getContentPane().add(lblFormul, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, -1, -1));

        tableListeNoire.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Matricule", "Noms", "Prenoms", "Dette", "Payer ?"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableListeNoire);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 550, 380));

        exportButton.setText("Exporter");
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });
        getContentPane().add(exportButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 180, 90, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\MARTIAL\\Documents\\NetBeansProjects\\LMS\\src\\main\\java\\images\\clientA.png")); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 470));

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
            tooglePrets .setSelected(false);
            toogleClients.setSelected(false);
            new InterfaceListeArticles("", langue).setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_toogleArticlesActionPerformed

    private void toogleListeNoireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleListeNoireActionPerformed
        // TODO add your handling code here:
        if(toogleListeNoire.isSelected()){
            toogleListeNoire.setSelected(true);
            toogleEmprunteurs.setSelected(false);
            try{
                researchResult = false;
                result = statement.executeQuery("SELECT * FROM liste_noire, clients WHERE Matricule=Matricule_client AND Date_de_sortie IS NULL AND Date_suppression IS NULL ORDER BY Noms");
                    this.tabInfosSuspendus = new Object [5];
                    DefaultTableModel tabInformations = (DefaultTableModel)tableListeNoire.getModel();
                    for(int i=tabInformations.getRowCount()-1; i>=0; i--){
                        tabInformations.removeRow(i);
                    }
                    while(result.next()){
                        tabInfosSuspendus[0]=result.getString("Matricule_client");
                        tabInfosSuspendus[1]=result.getString("Noms");
                        tabInfosSuspendus[2]=result.getString("Prenoms");
                        tabInfosSuspendus[3]=result.getInt("Dette");
                        tabInfosSuspendus[4]="OK";
                        tabInformations.addRow(tabInfosSuspendus);
                    }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_toogleListeNoireActionPerformed

    private void toogleEmprunteursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleEmprunteursActionPerformed
        // TODO add your handling code here:
        if(toogleEmprunteurs.isSelected()){
            toogleEmprunteurs.setSelected(true);
            toogleListeNoire.setSelected(false);
            InterfacePrets listeEmprunteurs = new InterfacePrets(langue);
            listeEmprunteurs.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_toogleEmprunteursActionPerformed

    private void rechercheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rechercheActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rechercheActionPerformed

    private void comboTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTriActionPerformed

    private void searchIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchIconMouseClicked
        // TODO add your handling code here:
        try{
            researchResult = true;
            int compteur = 0;
            int valRechercheSiNombre = 0;
            tabInfosSuspendus = new Object[5];
            DefaultTableModel tabInformations = (DefaultTableModel)tableListeNoire.getModel();
            for(int i=tabInformations.getRowCount()-1; i>=0; i--){
                tabInformations.removeRow(i);
            }
            try{
                valRechercheSiNombre = Integer.parseInt(recherche.getText());
                result = statement.executeQuery("SELECT * FROM liste_noire, clients WHERE  Matricule=Matricule_client AND Date_de_sortie IS NULL AND (Matricule_client LIKE '"+recherche.getText()+"%' OR Noms LIKE '"+recherche.getText()+"%' OR Prenoms LIKE '"+recherche.getText()+"%' '"+recherche.getText()+"%' OR Dette="+valRechercheSiNombre+") AND Date_suppression IS NULL ORDER BY Noms");
            }catch(Exception e){
                result = statement.executeQuery("SELECT * FROM liste_noire, clients WHERE  Matricule=Matricule_client AND Date_de_sortie IS NULL AND(Matricule_client LIKE '"+recherche.getText()+"%' OR Noms LIKE '"+recherche.getText()+"%' OR Prenoms LIKE '"+recherche.getText()+"%' '"+recherche.getText()+"%') AND Date_suppression IS NULL ORDER BY Noms");
            }
            while(result.next()){
                tabInfosSuspendus[0] = result.getString("Matricule_client");
                tabInfosSuspendus[1] = result.getString("Noms");
                tabInfosSuspendus[2] = result.getString("Prenoms");
                tabInfosSuspendus[3] = result.getInt("Dette");
                tabInfosSuspendus[4] = "OK";
                tabInformations.addRow(tabInfosSuspendus);
                compteur++;
            }
            if(compteur==0){
                JOptionPane.showMessageDialog(rootPane, optionAucunArti[langue]);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_searchIconMouseClicked

    private void comboTriItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboTriItemStateChanged
        // TODO add your handling code here:
        if(researchResult==true){
            try{
                researchResult = true;
                int compteur = 0;
                int valRechercheSiNombre = 0;
                tabInfosSuspendus = new Object[5];
                DefaultTableModel tabInformations = (DefaultTableModel)tableListeNoire.getModel();
                for(int i=tabInformations.getRowCount()-1; i>=0; i--){
                    tabInformations.removeRow(i);
                }
                try{
                    valRechercheSiNombre = Integer.parseInt(recherche.getText());
                    if(((String)comboTri.getSelectedItem()).equals("Dette croissante")){
                        result = statement.executeQuery("SELECT * FROM liste_noire, clients WHERE  Matricule=Matricule_client AND Date_de_sortie IS NULL AND (Matricule_client LIKE '"+recherche.getText()+"%' OR Noms LIKE '"+recherche.getText()+"%' OR Prenoms LIKE '"+recherche.getText()+"%' '"+recherche.getText()+"%' OR Dette="+valRechercheSiNombre+") AND Date_suppression IS NULL ORDER BY Dette, Noms");
                    }
                    else if(((String)comboTri.getSelectedItem()).equals("Dette décroissante")){
                        result = statement.executeQuery("SELECT * FROM liste_noire, clients WHERE  Matricule=Matricule_client AND Date_de_sortie IS NULL AND (Matricule_client LIKE '"+recherche.getText()+"%' OR Noms LIKE '"+recherche.getText()+"%' OR Prenoms LIKE '"+recherche.getText()+"%' '"+recherche.getText()+"%' OR Dette="+valRechercheSiNombre+") AND Date_suppression IS NULL ORDER BY Dette DESC, Noms");
                    }
                    else{
                        result = statement.executeQuery("SELECT * FROM liste_noire, clients WHERE  Matricule=Matricule_client AND Date_de_sortie IS NULL AND (Matricule_client LIKE '"+recherche.getText()+"%' OR Noms LIKE '"+recherche.getText()+"%' OR Prenoms LIKE '"+recherche.getText()+"%' '"+recherche.getText()+"%' OR Dette="+valRechercheSiNombre+") AND Date_suppression IS NULL ORDER BY Noms");
                    }
                }catch(Exception e){
                    if(((String)comboTri.getSelectedItem()).equals("Dette croissante")){
                        result = statement.executeQuery("SELECT * FROM liste_noire, clients WHERE  Matricule=Matricule_client AND Date_de_sortie IS NULL AND(Matricule_client LIKE '"+recherche.getText()+"%' OR Noms LIKE '"+recherche.getText()+"%' OR Prenoms LIKE '"+recherche.getText()+"%' '"+recherche.getText()+"%') AND Date_suppression IS NULL ORDER BY Dette, Noms");
                    }
                    else if(((String)comboTri.getSelectedItem()).equals("Dette décroissante")){
                        result = statement.executeQuery("SELECT * FROM liste_noire, clients WHERE  Matricule=Matricule_client AND Date_de_sortie IS NULL AND(Matricule_client LIKE '"+recherche.getText()+"%' OR Noms LIKE '"+recherche.getText()+"%' OR Prenoms LIKE '"+recherche.getText()+"%' '"+recherche.getText()+"%') AND Date_suppression IS NULL ORDER BY Dette DESC, Noms");
                    }
                    else{
                        result = statement.executeQuery("SELECT * FROM liste_noire, clients WHERE  Matricule=Matricule_client AND Date_de_sortie IS NULL AND(Matricule_client LIKE '"+recherche.getText()+"%' OR Noms LIKE '"+recherche.getText()+"%' OR Prenoms LIKE '"+recherche.getText()+"%' '"+recherche.getText()+"%') AND Date_suppression IS NULL ORDER BY Noms");
                    }
                }
                while(result.next()){
                    tabInfosSuspendus[0] = result.getString("Matricule_client");
                    tabInfosSuspendus[1] = result.getString("Noms");
                    tabInfosSuspendus[2] = result.getString("Prenoms");
                    tabInfosSuspendus[3] = result.getInt("Dette");
                    tabInfosSuspendus[4] = "OK";
                    tabInformations.addRow(tabInfosSuspendus);
                    compteur++;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            try{
                researchResult = false;
                if(((String)comboTri.getSelectedItem()).equals("Dette croissante")){
                    result = statement.executeQuery("SELECT * FROM liste_noire, clients WHERE  Matricule=Matricule_client AND Date_de_sortie IS NULL AND Date_suppression IS NULL ORDER BY Dette, Noms");
                }
                else if(((String)comboTri.getSelectedItem()).equals("Dette décroissante")){
                    result = statement.executeQuery("SELECT * FROM liste_noire, clients WHERE  Matricule=Matricule_client AND Date_de_sortie IS NULL AND Date_suppression IS NULL ORDER BY Dette DESC, Noms");
                }
                else{
                    result = statement.executeQuery("SELECT * FROM liste_noire, clients WHERE  Matricule=Matricule_client AND Date_de_sortie IS NULL AND Date_suppression IS NULL ORDER BY Noms");
                }
                this.tabInfosSuspendus = new Object [5];
                DefaultTableModel tabInformations = (DefaultTableModel)tableListeNoire.getModel();
                for(int i=tabInformations.getRowCount()-1; i>=0; i--){
                    tabInformations.removeRow(i);
                }
                while(result.next()){
                    tabInfosSuspendus[0]=result.getString("Matricule_client");
                    tabInfosSuspendus[1]=result.getString("Noms");
                    tabInfosSuspendus[2]=result.getString("Prenoms");
                    tabInfosSuspendus[3]=result.getInt("Dette");
                    tabInfosSuspendus[4]="OK";
                    tabInformations.addRow(tabInfosSuspendus);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_comboTriItemStateChanged

    private void tooglePretsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tooglePretsActionPerformed
        // TODO add your handling code here:
        if(tooglePrets.isSelected()){
            tooglePrets.setSelected(true);
            toogleArticles.setSelected(false);
            toogleClients.setSelected(false);
        }
    }//GEN-LAST:event_tooglePretsActionPerformed

    private void toogleClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleClientsActionPerformed
        // TODO add your handling code here:
        if(toogleClients.isSelected()){
            toogleClients.setSelected(true);
            toogleArticles.setSelected(false);
            tooglePrets.setSelected(false);
            new InterfaceListeClientsEtudiants("", langue).setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_toogleClientsActionPerformed

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

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
        // TODO add your handling code here:
        try{
            JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel (.xlsx)", "xlsx");
            fc.setFileFilter(filter);
            int returnValue = fc.showSaveDialog(fc);
            if(returnValue==JFileChooser.APPROVE_OPTION){
                File file = fc.getSelectedFile();
                file = new File(file.toString()+".xlsx");
                Workbook wb = new XSSFWorkbook();
                String feuille = "Pénalités"+LocalDate.now();
                Sheet sheet = wb.createSheet(feuille);
                Row rowCol = sheet.createRow(0);
                for(int i=0; i<=tableListeNoire.getColumnCount()-2; i++){
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tableListeNoire.getColumnName(i));
                }
                for(int i=0; i<=tableListeNoire.getRowCount()-1; i++){
                    Row row = sheet.createRow(i+1);
                    for(int j=0; j<=tableListeNoire.getColumnCount()-2; j++){
                        Cell cell = row.createCell(j);
                        cell.setCellValue(""+tableListeNoire.getValueAt(i, j));
                    }
                }
                FileOutputStream out = new FileOutputStream(new File(file.toString()));
                wb.write(out);
                wb.close();
                out.close();
                Desktop desktop = Desktop.getDesktop();
                desktop.open(file);
                /*String fichier = file.getAbsolutePath();
                InputStream input = new FileInputStream(fichier);
                POIFSFileSystem fs = new POIFSFileSystem(input);
                HSSFWorkbook wb = new HSSFWorkbook(fs);
                String feuille = "Pénalités";
                HSSFSheet sheet = wb.createSheet(feuille);
                //Iterator rows = sheet.rowIterator();
                int u=0;
                while(u<=tableListeNoire.getRowCount()-1){
                int i = 0;
                HSSFRow row = sheet.createRow(u);
                while(i<=tableListeNoire.getColumnCount()-2){
                HSSFCell cell = row.createCell(i, CellType.STRING);
                i++;
                }
                u++;
                }
                sheet = wb.getSheet(feuille);
                Iterator rows = sheet.rowIterator();
                HSSFRow row = (HSSFRow)rows.next();
                u=0;
                while(rows.hasNext()){
                int i = 0;
                row = (HSSFRow)rows.next();
                Iterator cells = row.cellIterator();
                while(cells.hasNext()){
                HSSFCell cell = (HSSFCell) cells.next();
                cell.setCellValue(""+tableListeNoire.getValueAt(u, i));
                i++;
                }
                u++;
                }*/
                JOptionPane.showMessageDialog(rootPane, exportationReussie[langue]);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_exportButtonActionPerformed

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
            java.util.logging.Logger.getLogger(InterfaceListeNoire.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfaceListeNoire.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfaceListeNoire.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceListeNoire.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfaceListeNoire(langue).setVisible(true);
            }
        });
    }

    private Object tabInfosSuspendus[];
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private boolean researchResult;
    static int langue;
    private String article[];
    private String client[];
    private String pret[];
    private String listeEmprunte[];
    private String listeNoir[];
    private String recherch[];
    private String trierPar[];
    private String formul[];
    private String optionEntrerMatric[];
    private String optionEntrerTota[];
    private String optionPaiementEffect[];
    private String optionMatriculeNonCorres[];
    private String optionLeMontantEntre[];
    private String optionAucunArti[];
    private String exportationReussie[];
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Edit;
    private javax.swing.JMenu Main;
    private javax.swing.JComboBox<String> comboTri;
    private javax.swing.JButton exportButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenuArticles;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenu jMenuClients;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFormul;
    private javax.swing.JLabel lblTri;
    private javax.swing.JTextField recherche;
    private javax.swing.JLabel searchIcon;
    private javax.swing.JTable tableListeNoire;
    private javax.swing.JToggleButton toogleArticles;
    private javax.swing.JToggleButton toogleClients;
    private javax.swing.JToggleButton toogleEmprunteurs;
    private javax.swing.JToggleButton toogleListeNoire;
    private javax.swing.JToggleButton tooglePrets;
    // End of variables declaration//GEN-END:variables
}

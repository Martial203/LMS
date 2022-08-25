
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



/**
 *
 * @author MARTIAL
 */
public class InterfaceListeArticles extends javax.swing.JFrame {
    /**
     * Creates new form InterfaceArticle2
     */
//La classe ButtonEditor permet de faire modifier la nature d'un cellule d'une JTable, dans notre cas, nous la transformons en un boutton
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
        if (isPushed) { //Action effectuée lorsque le boutton est pressé
            String matriculeEmprunteur = JOptionPane.showInputDialog(button, "Voulez vous vraiment prêter cet article ? \n Si oui, veuillez entrer le matricule du client");
            if(matriculeEmprunteur!=null){
                try{
                    result = statement.executeQuery("SELECT COUNT(*) as nbreMatPresents FROM clients WHERE Matricule='"+matriculeEmprunteur+"' AND Date_suppression IS NULL");
                    result.next();
                    if(result.getInt("nbreMatPresents")==0){
                        JOptionPane.showMessageDialog(editorComponent, optionNeCorrespond[langue]);
                    }
                    else{
                        result = statement.executeQuery("SELECT COUNT(*) as presence FROM liste_noire WHERE Matricule_client='"+matriculeEmprunteur+"' AND Date_de_sortie IS NULL");
                        result.next();
                        if(result.getInt("presence")==0){
                            result = statement.executeQuery("SELECT COUNT(*) nbreArticlesDejaEmpruntes FROM emprunts WHERE Matricule_Emprunteur='"+matriculeEmprunteur+"' AND Date_remise IS NULL");
                            result.next();
                            if(result.getInt("nbreArticlesDejaEmpruntes")<4){
                                statement.executeUpdate("INSERT INTO emprunts(Matricule_Emprunteur, Emplacement_Article, Date_emprunt) VALUES('"+matriculeEmprunteur+"', '"+tableArticles.getValueAt(tableArticles.getSelectedRow(), 6)+"', NOW())");
                                JOptionPane.showMessageDialog(editorComponent, optionEmpruntReussi[langue]);
                                ((DefaultTableModel)tableArticles.getModel()).removeRow(tableArticles.getSelectedRow());
                            }
                            else{
                                JOptionPane.showMessageDialog(editorComponent, optionQuatreArticles[langue]);
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(editorComponent, optionListeNoire+" \n "+optionMotif[langue]+result.getString("Dette"));
                        }
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
    
    
    public InterfaceListeArticles(String critere, int lang) {
        initComponents();
        langue = lang;
        //Traduction
        String title[] = new String[]{"Liste des articles","Items list"};
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
        listeArti = new String[2];
            listeArti[0] = "Liste des articles";
            listeArti[1] = "Items list";
            toogleListeArticles.setText(listeArti[langue]);
        ajouterArti = new String[2];
            ajouterArti[0] = "Ajouter un article";
            ajouterArti[1] = "Add an item";
            toogleAjoutArticles.setText(ajouterArti[langue]);
        recherch = new String[2];
            recherch[0] = "Rechercher";
            recherch[1] = "Search";
            recherche.setText(recherch[langue]);
        trierPar = new String[2];
            trierPar[0] = "Trier par :";
            trierPar[1] = "Sort by :";
            lblTri.setText(trierPar[langue]);
        livre = new String[2];
            livre[0] = "Livres";
            livre[1] = "Books";
            toogleLivres.setText(livre[langue]);
        materie = new String[2];
            materie[0] = "Matériel";
            materie[1] = "Equipment";
            toogleMateriel.setText(materie[langue]);
        optionNeCorrespond = new String[2];
            optionNeCorrespond[0] = "Le matricule entré ne correspond à aucun client dans la base de données. Veuillez le vérifier à nouveau !";
            optionNeCorrespond[1] = "The entered matricule doesn't refers to any customer in the database. Please check it again !";
        optionEmpruntReussi = new String[2];
            optionEmpruntReussi[0] = "Emprunt enregistré avec succès !";
            optionEmpruntReussi[1] = "Borrowing successfully registered !";
        optionQuatreArticles = new String[2];
            optionQuatreArticles[0] = "Ce client a déja 04 articles en sa possession, il ne peut en emprunter d'autres !";
            optionQuatreArticles[1] = "This customer already has 04 items in his possession, he can't borrow more !";
        optionListeNoire = new String[2];
            optionListeNoire[0] = "Ce client ne peut bénéficier des services de cette bibliothèque.";
            optionListeNoire[1] = "This customer cannot benefit from the services of this library.";
        optionMotif = new String[2];
            optionMotif[0] = "Motif: Dette de ";
            optionMotif[1] = "Reason: Debt of ";
        optionAucunArticle = new String[2];
            optionAucunArticle[0] = "Aucun article ne correspond à votre recherche";
            optionAucunArticle[1] = "No items matching your search";
        try{
            //Connection à la BD
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/lms_g8", "root", "");
            statement = connection.createStatement();
            
            //Recupération infos de la BD
            if((critere.equals(""))){
                result = statement.executeQuery("SELECT * FROM articles WHERE Categorie='Livre' AND Date_suppression IS NULL AND Emplacement NOT IN (SELECT Emplacement_Article FROM emprunts WHERE Date_remise IS NULL) ORDER BY Titre");
            }
            else{
                result = statement.executeQuery("SELECT * FROM articles WHERE Domaine='"+critere+"' AND Date_suppression IS NULL ORDER BY Titre");
                toogleLivres.setSelected(false);
            }
            
            //Affichage infos dans le JTable
                this.tabInfosArticles = new Object [8];
                DefaultTableModel tabInformations = (DefaultTableModel)tableArticles.getModel();
                tableArticles.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());;
                tableArticles.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox()));
                while(result.next()){
                    tabInfosArticles[0] = result.getString("Titre");
                    tabInfosArticles[1] = result.getString("Auteur");
                    tabInfosArticles[2] = result.getString("Domaine");
                    tabInfosArticles[3] = result.getString("Maison_edition");
                    tabInfosArticles[4] = result.getInt("Date_publication");
                    tabInfosArticles[6] = result.getString("Emplacement");
                    tabInfosArticles[7] = "OK";
                    Statement st = connection.createStatement();
                    ResultSet rslt = st.executeQuery("SELECT (COUNT(*)-(SELECT COUNT(*) FROM emprunts WHERE Emplacement_Article='"+result.getString("Emplacement")+"' AND Date_remise IS NULL)) as nbreExemplaires FROM articles WHERE Titre='"+result.getString("Titre")+"' AND Auteur='"+result.getString("Auteur")+"' AND Domaine='"+result.getString("Domaine")+"' AND Categorie='"+result.getString("Categorie")+"'");
                    rslt.next();
                    if(rslt.getInt("nbreExemplaires")!=0){
                        tabInfosArticles[5] = rslt.getInt("nbreExemplaires");
                        tabInformations.addRow(tabInfosArticles);
                    }
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

        jFrame1 = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel2 = new javax.swing.JPanel();
        jToggleButton5 = new javax.swing.JToggleButton();
        jToggleButton4 = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jFrame2 = new javax.swing.JFrame();
        jPanel4 = new javax.swing.JPanel();
        jToggleButton6 = new javax.swing.JToggleButton();
        jToggleButton7 = new javax.swing.JToggleButton();
        jToggleButton8 = new javax.swing.JToggleButton();
        jPanel5 = new javax.swing.JPanel();
        jToggleButton9 = new javax.swing.JToggleButton();
        jToggleButton10 = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jTextField4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        toogleArticles = new javax.swing.JToggleButton();
        toogleClients = new javax.swing.JToggleButton();
        tooglePrets = new javax.swing.JToggleButton();
        recherche = new javax.swing.JTextField();
        lblTri = new javax.swing.JLabel();
        searchIcon = new javax.swing.JLabel();
        comboTri = new javax.swing.JComboBox<>();
        toogleLivres = new javax.swing.JToggleButton();
        toogleCD = new javax.swing.JToggleButton();
        toogleMateriel = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableArticles = new javax.swing.JTable();
        toogleListeArticles = new javax.swing.JToggleButton();
        toogleAjoutArticles = new javax.swing.JToggleButton();
        jLabel8 = new javax.swing.JLabel();
        jMenuBar3 = new javax.swing.JMenuBar();
        Main = new javax.swing.JMenu();
        Edit = new javax.swing.JMenu();
        jMenuClients = new javax.swing.JMenu();
        jMenuArticles = new javax.swing.JMenu();

        jFrame1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jFrame1.setBackground(new java.awt.Color(51, 204, 255));

        jToggleButton2.setText("Clients");

        jToggleButton3.setText("Prets");

        jToggleButton1.setText("Articles");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButton3)
                    .addComponent(jToggleButton1)
                    .addComponent(jToggleButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToggleButton1)
                .addGap(39, 39, 39)
                .addComponent(jToggleButton2)
                .addGap(53, 53, 53)
                .addComponent(jToggleButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToggleButton5.setText("Ajouter un article");
        jToggleButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton5ActionPerformed(evt);
            }
        });

        jToggleButton4.setText("Liste des articles");
        jToggleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButton4)
                    .addComponent(jToggleButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jToggleButton4)
                .addGap(18, 18, 18)
                .addComponent(jToggleButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTextField2.setText("Rechercher");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Trier par :");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Domaine", "Auteur", "Ordre alphabétique", "Maison d'édition" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton3.setText("Electronique");

        jButton6.setText("Anthropologie et Culture");

        jButton9.setText("Management");

        jButton8.setText("Art");

        jButton5.setText("Litterature");

        jButton2.setText("Mathematiques");

        jButton1.setText("Informatique");

        jButton4.setText("Science-fiction");

        jButton7.setText("Droit");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton4)
                    .addComponent(jButton7))
                .addGap(54, 54, 54)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(61, 61, 61)
                        .addComponent(jButton3))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addGap(50, 50, 50)
                        .addComponent(jButton6))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                        .addComponent(jButton9)
                        .addGap(29, 29, 29)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(51, 51, 51)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addGap(53, 53, 53)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton8)
                    .addComponent(jButton9))
                .addContainerGap(154, Short.MAX_VALUE))
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jFrame1.setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jFrame1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jFrame2.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jFrame2.setBackground(new java.awt.Color(51, 204, 255));

        jToggleButton6.setText("Clients");

        jToggleButton7.setText("Prets");

        jToggleButton8.setText("Articles");
        jToggleButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButton7)
                    .addComponent(jToggleButton8)
                    .addComponent(jToggleButton6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToggleButton8)
                .addGap(39, 39, 39)
                .addComponent(jToggleButton6)
                .addGap(53, 53, 53)
                .addComponent(jToggleButton7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToggleButton9.setText("Ajouter un article");
        jToggleButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton9ActionPerformed(evt);
            }
        });

        jToggleButton10.setText("Liste des articles");
        jToggleButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButton10)
                    .addComponent(jToggleButton9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jToggleButton10)
                .addGap(18, 18, 18)
                .addComponent(jToggleButton9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTextField3.setText("Rechercher");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel4.setText("Trier par :");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Domaine", "Auteur", "Ordre alphabétique", "Maison d'édition" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jButton10.setText("Electronique");

        jButton11.setText("Anthropologie et Culture");

        jButton12.setText("Management");

        jButton13.setText("Art");

        jButton14.setText("Litterature");

        jButton15.setText("Mathematiques");

        jButton16.setText("Informatique");

        jButton17.setText("Science-fiction");

        jButton18.setText("Droit");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton16)
                    .addComponent(jButton17)
                    .addComponent(jButton18))
                .addGap(54, 54, 54)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton15)
                        .addGap(61, 61, 61)
                        .addComponent(jButton10))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton14)
                        .addGap(50, 50, 50)
                        .addComponent(jButton11))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                        .addComponent(jButton12)
                        .addGap(29, 29, 29)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton16)
                    .addComponent(jButton15)
                    .addComponent(jButton10))
                .addGap(51, 51, 51)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton17)
                    .addComponent(jButton14)
                    .addComponent(jButton11))
                .addGap(53, 53, 53)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton18)
                    .addComponent(jButton13)
                    .addComponent(jButton12))
                .addContainerGap(154, Short.MAX_VALUE))
        );

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        jFrame2.setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jFrame2Layout.createSequentialGroup()
                .addGroup(jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jFrame2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTextField4.setText("Rechercher");
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jLabel5.setText("Trier par :");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Domaine", "Auteur", "Ordre alphabétique", "Maison d'édition" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setText("jLabel7");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        toogleArticles.setSelected(true);
        toogleArticles.setText("Articles");
        toogleArticles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleArticlesActionPerformed(evt);
            }
        });
        getContentPane().add(toogleArticles, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 80, -1));

        toogleClients.setText("Clients");
        toogleClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleClientsActionPerformed(evt);
            }
        });
        getContentPane().add(toogleClients, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 80, -1));

        tooglePrets.setText("Prets");
        tooglePrets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tooglePretsActionPerformed(evt);
            }
        });
        getContentPane().add(tooglePrets, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 80, -1));

        recherche.setText("Rechercher");
        recherche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rechercheActionPerformed(evt);
            }
        });
        getContentPane().add(recherche, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 150, -1));

        lblTri.setText("Trier par :");
        getContentPane().add(lblTri, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, -1, -1));

        searchIcon.setIcon(new javax.swing.ImageIcon("C:\\wamp64\\www\\Code\\photos\\search.png")); // NOI18N
        searchIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchIconMouseClicked(evt);
            }
        });
        getContentPane().add(searchIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, -1, -1));

        comboTri.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Domaine", "Auteur", "Maison_edition", "Emplacement" }));
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
        getContentPane().add(comboTri, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, 77, -1));

        toogleLivres.setSelected(true);
        toogleLivres.setText("Livres");
        toogleLivres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleLivresActionPerformed(evt);
            }
        });
        getContentPane().add(toogleLivres, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, -1, -1));

        toogleCD.setText("CD");
        toogleCD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleCDActionPerformed(evt);
            }
        });
        getContentPane().add(toogleCD, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, -1, -1));

        toogleMateriel.setText("Materiel");
        toogleMateriel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleMaterielActionPerformed(evt);
            }
        });
        getContentPane().add(toogleMateriel, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, -1, -1));

        tableArticles.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tableArticles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Titre", "Auteur", "Domaine", "Maison d'édition", "Année de publication", "Nombre d'exemplaires dispo", "Emplacement", "Emprunter ?"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableArticles);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 600, 443));

        toogleListeArticles.setSelected(true);
        toogleListeArticles.setText("Liste des articles");
        toogleListeArticles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleListeArticlesActionPerformed(evt);
            }
        });
        getContentPane().add(toogleListeArticles, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 100, -1, -1));

        toogleAjoutArticles.setText("Ajouter un article");
        toogleAjoutArticles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleAjoutArticlesActionPerformed(evt);
            }
        });
        getContentPane().add(toogleAjoutArticles, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 150, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon("C:\\Users\\MARTIAL\\Documents\\NetBeansProjects\\LMS\\src\\main\\java\\images\\clientA.png")); // NOI18N
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 520));

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

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton5ActionPerformed

    private void jToggleButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton4ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jToggleButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton8ActionPerformed

    private void jToggleButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton9ActionPerformed

    private void jToggleButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton10ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void toogleArticlesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleArticlesActionPerformed
        // TODO add your handling code here:
        if(toogleArticles.isSelected()){
            toogleArticles.setSelected(true);
            toogleClients.setSelected(false);
            tooglePrets.setSelected(false);
        }
    }//GEN-LAST:event_toogleArticlesActionPerformed

    private void toogleAjoutArticlesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleAjoutArticlesActionPerformed
        // TODO add your handling code here:
        if(toogleAjoutArticles.isSelected()){
            toogleListeArticles.setSelected(false);
            InterfaceAjoutArticles interfaceAjoutArticle = new InterfaceAjoutArticles(langue);
            interfaceAjoutArticle.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_toogleAjoutArticlesActionPerformed

    private void toogleListeArticlesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleListeArticlesActionPerformed
        // TODO add your handling code here:
        if(toogleListeArticles.isSelected()){
            toogleListeArticles.setSelected(true);
            toogleAjoutArticles.setSelected(false);
        }
    }//GEN-LAST:event_toogleListeArticlesActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void comboTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTriActionPerformed

    private void rechercheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rechercheActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rechercheActionPerformed

    private void toogleLivresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleLivresActionPerformed
        // TODO add your handling code here:
        if(toogleLivres.isSelected()){
            toogleCD.setSelected(false);
            toogleMateriel.setSelected(false);
            //Recupération des éléments à afficher dans la JTable
            try{
                result = statement.executeQuery("SELECT * FROM articles WHERE Categorie='Livre' AND Date_suppression IS NULL ORDER BY Titre");
                this.tabInfosArticles = new Object [8];
                DefaultTableModel tabInformations = (DefaultTableModel)tableArticles.getModel();
                //Effacaement des lignes préalablement existantes, afin d'éviter une superposition entre les éléments précédents et ceux à venir
                for(int i=tabInformations.getRowCount()-1; i>=0; i--){
                    tabInformations.removeRow(i);
                }
                //Recupération des informations de la BD et affichage dans la JTable
                while(result.next()){
                    tabInfosArticles[0] = result.getString("Titre");
                    tabInfosArticles[1] = result.getString("Auteur");
                    tabInfosArticles[2] = result.getString("Domaine");
                    tabInfosArticles[3] = result.getString("Maison_edition");
                    tabInfosArticles[4] = result.getInt("Date_publication");
                    tabInfosArticles[6] = result.getString("Emplacement");
                    tabInfosArticles[7] = "OK";
                    Statement st = connection.createStatement();
                    ResultSet rslt = st.executeQuery("SELECT (COUNT(*)-(SELECT COUNT(*) FROM emprunts WHERE Emplacement_Article='"+result.getString("Emplacement")+"' AND Date_remise IS NULL)) as nbreExemplaires FROM articles WHERE Titre='"+result.getString("Titre")+"' AND Auteur='"+result.getString("Auteur")+"' AND Domaine='"+result.getString("Domaine")+"' AND Categorie='"+result.getString("Categorie")+"' AND Date_suppression IS NULL");
                    rslt.next();
                    if(rslt.getInt("nbreExemplaires")!=0){
                        tabInfosArticles[5] = rslt.getInt("nbreExemplaires");
                        tabInformations.addRow(tabInfosArticles);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_toogleLivresActionPerformed

    private void toogleCDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleCDActionPerformed
        // TODO add your handling code here:
        if(toogleCD.isSelected()){
            toogleLivres.setSelected(false);
            toogleMateriel.setSelected(false);
            try{
                result = statement.executeQuery("SELECT * FROM articles WHERE Categorie='CD' AND Date_suppression IS NULL ORDER BY Titre");
                this.tabInfosArticles = new Object [8];
                DefaultTableModel tabInformations = (DefaultTableModel)tableArticles.getModel();
                for(int i=tabInformations.getRowCount()-1; i>=0; i--){
                    tabInformations.removeRow(i);
                }
                while(result.next()){
                    tabInfosArticles[0] = result.getString("Titre");
                    tabInfosArticles[1] = result.getString("Auteur");
                    tabInfosArticles[2] = result.getString("Domaine");
                    tabInfosArticles[3] = result.getString("Maison_edition");
                    tabInfosArticles[4] = result.getInt("Date_publication");
                    tabInfosArticles[6] = result.getString("Emplacement");
                    tabInfosArticles[7] = "OK";
                    Statement st = connection.createStatement();
                    ResultSet rslt = st.executeQuery("SELECT (COUNT(*)-(SELECT COUNT(*) FROM emprunts WHERE Emplacement_Article='"+result.getString("Emplacement")+"' AND Date_remise IS NULL)) as nbreExemplaires FROM articles WHERE Titre='"+result.getString("Titre")+"' AND Auteur='"+result.getString("Auteur")+"' AND Domaine='"+result.getString("Domaine")+"' AND Categorie='"+result.getString("Categorie")+"' AND Date_suppression IS NULL");
                    rslt.next();
                    if(rslt.getInt("nbreExemplaires")!=0){
                        tabInfosArticles[5] = rslt.getInt("nbreExemplaires");
                        tabInformations.addRow(tabInfosArticles);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_toogleCDActionPerformed

    private void toogleMaterielActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleMaterielActionPerformed
        // TODO add your handling code here:
        if(toogleMateriel.isSelected()){
            toogleLivres.setSelected(false);
            toogleCD.setSelected(false);
            try{
                result = statement.executeQuery("SELECT * FROM articles WHERE Categorie='Materiel de recherche' AND Date_suppression IS NULL ORDER BY Titre");
                this.tabInfosArticles = new Object [8];
                DefaultTableModel tabInformations = (DefaultTableModel)tableArticles.getModel();
                for(int i=tabInformations.getRowCount()-1; i>=0; i--){
                    tabInformations.removeRow(i);
                }
                while(result.next()){
                    tabInfosArticles[0] = result.getString("Titre");
                    tabInfosArticles[1] = result.getString("Auteur");
                    tabInfosArticles[2] = result.getString("Domaine");
                    tabInfosArticles[3] = result.getString("Maison_edition");
                    tabInfosArticles[4] = result.getInt("Date_publication");
                    tabInfosArticles[6] = result.getString("Emplacement");
                    tabInfosArticles[7] = "OK";
                    Statement st = connection.createStatement();
                    ResultSet rslt = st.executeQuery("SELECT (COUNT(*)-(SELECT COUNT(*) FROM emprunts WHERE Emplacement_Article='"+result.getString("Emplacement")+"' AND Date_remise IS NULL)) as nbreExemplaires FROM articles WHERE Titre='"+result.getString("Titre")+"' AND Auteur='"+result.getString("Auteur")+"' AND Domaine='"+result.getString("Domaine")+"' AND Categorie='"+result.getString("Categorie")+"' AND Date_suppression IS NULL");
                    rslt.next();
                    if(rslt.getInt("nbreExemplaires")!=0){
                        tabInfosArticles[5] = rslt.getInt("nbreExemplaires");
                        tabInformations.addRow(tabInfosArticles);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_toogleMaterielActionPerformed

    private void searchIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchIconMouseClicked
        // TODO add your handling code here:
        try{
            toogleLivres.setSelected(false);
            toogleCD.setSelected(false);
            toogleMateriel.setSelected(false);
            int compteur = 0;
            int valRechercheSiNombre = 0;
            tabInfosArticles = new Object[8];
            DefaultTableModel tabInformations = (DefaultTableModel)tableArticles.getModel();
            for(int i=tabInformations.getRowCount()-1; i>=0; i--){
                tabInformations.removeRow(i);
            }
            try{
                valRechercheSiNombre = Integer.parseInt(recherche.getText());
                 result = statement.executeQuery("SELECT * FROM articles WHERE (Titre LIKE '%"+recherche.getText()+"%' OR Auteur LIKE '%"+recherche.getText()+"%' OR Domaine LIKE '"+recherche.getText()+"%' OR Maison_edition LIKE '%"+recherche.getText()+"%' OR Date_publication="+valRechercheSiNombre+" OR Emplacement LIKE '%"+recherche.getText()+"%') AND Date_suppression IS NULL ORDER BY Titre");
            }catch(Exception e){
                result = statement.executeQuery("SELECT * FROM articles WHERE (Titre LIKE '%"+recherche.getText()+"%' OR Auteur LIKE '%"+recherche.getText()+"%' OR Domaine LIKE '"+recherche.getText()+"%' OR Maison_edition LIKE '%"+recherche.getText()+"%' OR Emplacement LIKE '%"+recherche.getText()+"%') AND Date_suppression IS NULL ORDER BY Titre");
            }
            while(result.next()){
                tabInfosArticles[0] = result.getString("Titre");
                tabInfosArticles[1] = result.getString("Auteur");
                tabInfosArticles[2] = result.getString("Domaine");
                tabInfosArticles[3] = result.getString("Maison_edition");
                tabInfosArticles[4] = result.getInt("Date_publication");
                tabInfosArticles[6] = result.getString("Emplacement");
                tabInfosArticles[7] = "OK";
                Statement st = connection.createStatement();
                ResultSet rslt = st.executeQuery("SELECT (COUNT(*)-(SELECT COUNT(*) FROM emprunts WHERE Emplacement_Article='"+result.getString("Emplacement")+"' AND Date_remise IS NULL)) as nbreExemplaires FROM articles WHERE Titre='"+result.getString("Titre")+"' AND Auteur='"+result.getString("Auteur")+"' AND Domaine='"+result.getString("Domaine")+"' AND Categorie='"+result.getString("Categorie")+"' AND Date_suppression IS NULL");
                rslt.next();
                if(rslt.getInt("nbreExemplaires")!=0){
                    tabInfosArticles[5] = rslt.getInt("nbreExemplaires");
                    tabInformations.addRow(tabInfosArticles);
                }
                compteur++;
            }
            if(compteur==0){
                JOptionPane.showMessageDialog(rootPane, optionAucunArticle[langue]);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_searchIconMouseClicked

    private void comboTriItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboTriItemStateChanged
        // TODO add your handling code here:
        if(((String)comboTri.getSelectedItem()).equals("")){
            if(toogleLivres.isSelected()){
                try{
                    result = statement.executeQuery("SELECT * FROM articles WHERE Categorie='Livre' AND Date_suppression IS NULL ORDER BY Titre");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        else if(toogleCD.isSelected()){
                try{
                    result = statement.executeQuery("SELECT * FROM articles WHERE Categorie='CD' AND Date_suppression IS NULL ORDER BY Titre");
                }catch(Exception e){
                    e.printStackTrace();
                }
        }
        else if(toogleMateriel.isSelected()){
                try{
                    result = statement.executeQuery("SELECT * FROM articles WHERE Categorie='Materiel de recherche' AND Date_suppression IS NULL ORDER BY Titre");
                }catch(Exception e){
                    e.printStackTrace();
                }
        }
        else{
            try{
                int valRechercheSiNombre = Integer.parseInt(recherche.getText());
                result = statement.executeQuery("SELECT * FROM articles WHERE (Titre LIKE '%"+recherche.getText()+"%' OR Auteur LIKE '%"+recherche.getText()+"%' OR Domaine LIKE '"+recherche.getText()+"%' OR Maison_edition LIKE '%"+recherche.getText()+"%' OR Date_publication="+valRechercheSiNombre+" OR Emplacement LIKE '%"+recherche.getText()+"%') AND Date_suppression IS NULL ORDER BY Titre");
            }catch(Exception e){
                try{
                    result = statement.executeQuery("SELECT * FROM articles WHERE (Titre LIKE '%"+recherche.getText()+"%' OR Auteur LIKE '%"+recherche.getText()+"%' OR Domaine LIKE '"+recherche.getText()+"%' OR Maison_edition LIKE '%"+recherche.getText()+"%' OR Emplacement LIKE '%"+recherche.getText()+"%') AND Date_suppression IS NULL ORDER BY Titre");
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
            
        }
        else{
            if(toogleLivres.isSelected()){
                try{
                    result = statement.executeQuery("SELECT * FROM articles WHERE Categorie='Livre' AND Date_suppression IS NULL ORDER BY "+(String)comboTri.getSelectedItem()+", Titre");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else if(toogleCD.isSelected()){
                try{
                    result = statement.executeQuery("SELECT * FROM articles WHERE Categorie='CD' AND Date_suppression IS NULL ORDER BY "+(String)comboTri.getSelectedItem()+", Titre");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else if(toogleMateriel.isSelected()){
                try{
                    result = statement.executeQuery("SELECT * FROM articles WHERE Categorie='Materiel de recherche' AND Date_suppression IS NULL ORDER BY "+(String)comboTri.getSelectedItem()+", Titre");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{
                try{
                    int valRechercheSiNombre = Integer.parseInt(recherche.getText());
                    result = statement.executeQuery("SELECT * FROM articles WHERE (Titre LIKE '%"+recherche.getText()+"%' OR Auteur LIKE '%"+recherche.getText()+"%' OR Domaine LIKE '"+recherche.getText()+"%' OR Maison_edition LIKE '%"+recherche.getText()+"%' OR Date_publication="+valRechercheSiNombre+" OR Emplacement LIKE '%"+recherche.getText()+"%') AND Date_suppression IS NULL ORDER BY "+(String)comboTri.getSelectedItem()+", Titre");
                }catch(Exception e){
                    try{
                        result = statement.executeQuery("SELECT * FROM articles WHERE (Titre LIKE '%"+recherche.getText()+"%' OR Auteur LIKE '%"+recherche.getText()+"%' OR Domaine LIKE '"+recherche.getText()+"%' OR Maison_edition LIKE '%"+recherche.getText()+"%' OR Emplacement LIKE '%"+recherche.getText()+"%') AND Date_suppression IS NULL ORDER BY "+(String)comboTri.getSelectedItem()+", Titre");
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
        this.tabInfosArticles = new Object [8];
                DefaultTableModel tabInformations = (DefaultTableModel)tableArticles.getModel();
                for(int i=tabInformations.getRowCount()-1; i>=0; i--){
                    tabInformations.removeRow(i);
                }
                try{
                    while(result.next()){
                        tabInfosArticles[0] = result.getString("Titre");
                        tabInfosArticles[1] = result.getString("Auteur");
                        tabInfosArticles[2] = result.getString("Domaine");
                        tabInfosArticles[3] = result.getString("Maison_edition");
                        tabInfosArticles[4] = result.getInt("Date_publication");
                        tabInfosArticles[6] = result.getString("Emplacement");
                        tabInfosArticles[7] = "OK";
                        Statement st = connection.createStatement();
                        ResultSet rslt = st.executeQuery("SELECT (COUNT(*)-(SELECT COUNT(*) FROM emprunts WHERE Emplacement_Article='"+result.getString("Emplacement")+"' AND Date_remise IS NULL)) as nbreExemplaires FROM articles WHERE Titre='"+result.getString("Titre")+"' AND Auteur='"+result.getString("Auteur")+"' AND Domaine='"+result.getString("Domaine")+"' AND Categorie='"+result.getString("Categorie")+"' AND Date_suppression IS NULL");
                        rslt.next();
                        if(rslt.getInt("nbreExemplaires")!=0){
                            tabInfosArticles[5] = rslt.getInt("nbreExemplaires");
                            tabInformations.addRow(tabInfosArticles);
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
    }//GEN-LAST:event_comboTriItemStateChanged

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
            java.util.logging.Logger.getLogger(InterfaceListeArticles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfaceListeArticles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfaceListeArticles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceListeArticles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfaceListeArticles("", langue).setVisible(true);
            }
        });
    }
    private Object tabInfosArticles[];
    private Connection connection;
    private Statement statement;
    private ResultSet result;
    static int langue;
    private String article[];
    private String client[];
    private String pret[];
    private String listeArti[];
    private String ajouterArti[];
    private String recherch[];
    private String trierPar[];
    private String livre[];
    private String materie[];
    private String optionNeCorrespond[];
    private String optionEmpruntReussi[];
    private String optionQuatreArticles[];
    private String optionListeNoire[];
    private String optionAucunArticle[];
    private String optionMotif[];
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Edit;
    private javax.swing.JMenu Main;
    private javax.swing.JComboBox<String> comboTri;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenuArticles;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenu jMenuClients;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton10;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JToggleButton jToggleButton5;
    private javax.swing.JToggleButton jToggleButton6;
    private javax.swing.JToggleButton jToggleButton7;
    private javax.swing.JToggleButton jToggleButton8;
    private javax.swing.JToggleButton jToggleButton9;
    private javax.swing.JLabel lblTri;
    private javax.swing.JTextField recherche;
    private javax.swing.JLabel searchIcon;
    private javax.swing.JTable tableArticles;
    private javax.swing.JToggleButton toogleAjoutArticles;
    private javax.swing.JToggleButton toogleArticles;
    private javax.swing.JToggleButton toogleCD;
    private javax.swing.JToggleButton toogleClients;
    private javax.swing.JToggleButton toogleListeArticles;
    private javax.swing.JToggleButton toogleLivres;
    private javax.swing.JToggleButton toogleMateriel;
    private javax.swing.JToggleButton tooglePrets;
    // End of variables declaration//GEN-END:variables
}

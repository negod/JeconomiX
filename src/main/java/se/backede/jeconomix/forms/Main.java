/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms;

import com.backede.fileutils.csv.parser.CsvExtractor;
import se.backede.jeconomix.forms.report.TransactionReport;
import se.backede.jeconomix.forms.importexport.FileChooser;
import se.backede.jeconomix.forms.company.CompanyEditor;
import se.backede.jeconomix.forms.category.CategoryEditor;
import java.util.Optional;
import java.util.function.Consumer;
import javax.swing.JFileChooser;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.constants.NordeaCsvFields;
import se.backede.jeconomix.database.CacheInitializer;
import se.backede.jeconomix.database.CompanyHandler;
import se.backede.jeconomix.exporter.CategoryExporter;
import se.backede.jeconomix.exporter.CompanyExporter;
import se.backede.jeconomix.forms.basic.NegodJFrame;
import se.backede.jeconomix.forms.report.TransactionsTotalReport;
import se.backede.jeconomix.importer.CategoryImporter;
import se.backede.jeconomix.forms.company.CompanyImporter;
import se.backede.jeconomix.importer.NordeaCsvTransactionImporter;
import se.backede.jeconomix.importer.Transactions;
import se.backede.jeconomix.utils.TimeDecider;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class Main extends NegodJFrame {

    private CacheInitializer cache = new CacheInitializer();

    /**
     * Creates new form NewApplication
     */
    public Main() {
        super();
        initComponents();
        budgetQuarter1.setData(TimeDecider.getCurrenQuarter(), TimeDecider.getCurrentYear());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        budgetQuarter1 = new se.backede.jeconomix.forms.budget.BudgetQuarter();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        importerMenu = new javax.swing.JMenu();
        importTransactionMenuItem = new javax.swing.JMenuItem();
        importMenu = new javax.swing.JMenu();
        importCategoriesMenuItem = new javax.swing.JMenuItem();
        importCompanyMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        exportCompanies = new javax.swing.JMenuItem();
        exportExpenses = new javax.swing.JMenuItem();
        exportExpenseTypes = new javax.swing.JMenuItem();
        exportAll = new javax.swing.JMenuItem();
        handleListMenu = new javax.swing.JMenu();
        expenseCategoryMenuItem = new javax.swing.JMenuItem();
        handleCompaniesMenuItem = new javax.swing.JMenuItem();
        reportMenu = new javax.swing.JMenu();
        expenseReportMenuItem = new javax.swing.JMenuItem();
        billReportMenuItem = new javax.swing.JMenuItem();
        incomeReportMenuItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        reindecLuceneMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JeconomiX");

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        importerMenu.setText("Import");
        importerMenu.setToolTipText("");

        importTransactionMenuItem.setText("Transactions");
        importTransactionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importTransactionMenuItemActionPerformed(evt);
            }
        });
        importerMenu.add(importTransactionMenuItem);

        importMenu.setText("Listdata");

        importCategoriesMenuItem.setText("Categories");
        importCategoriesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importCategoriesMenuItemActionPerformed(evt);
            }
        });
        importMenu.add(importCategoriesMenuItem);

        importCompanyMenuItem.setText("Companies");
        importCompanyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importCompanyMenuItemActionPerformed(evt);
            }
        });
        importMenu.add(importCompanyMenuItem);

        importerMenu.add(importMenu);

        menuBar.add(importerMenu);

        jMenu2.setText("Export");

        exportCompanies.setText("Companies");
        exportCompanies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportCompaniesActionPerformed(evt);
            }
        });
        jMenu2.add(exportCompanies);

        exportExpenses.setText("Transactions");
        jMenu2.add(exportExpenses);

        exportExpenseTypes.setText("Categories");
        exportExpenseTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportExpenseTypesActionPerformed(evt);
            }
        });
        jMenu2.add(exportExpenseTypes);

        exportAll.setText("All");
        jMenu2.add(exportAll);

        menuBar.add(jMenu2);

        handleListMenu.setText("Handle lists");

        expenseCategoryMenuItem.setText("Categories");
        expenseCategoryMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expenseCategoryMenuItemActionPerformed(evt);
            }
        });
        handleListMenu.add(expenseCategoryMenuItem);

        handleCompaniesMenuItem.setText("Companies");
        handleCompaniesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleCompaniesMenuItemActionPerformed(evt);
            }
        });
        handleListMenu.add(handleCompaniesMenuItem);

        menuBar.add(handleListMenu);

        reportMenu.setText("Reports");

        expenseReportMenuItem.setText("Expense report");
        expenseReportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expenseReportMenuItemActionPerformed(evt);
            }
        });
        reportMenu.add(expenseReportMenuItem);

        billReportMenuItem.setText("Bill report");
        billReportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billReportMenuItemActionPerformed(evt);
            }
        });
        reportMenu.add(billReportMenuItem);

        incomeReportMenuItem.setText("Income report");
        incomeReportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incomeReportMenuItemActionPerformed(evt);
            }
        });
        reportMenu.add(incomeReportMenuItem);

        jMenuItem1.setText("Total report");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        reportMenu.add(jMenuItem1);

        menuBar.add(reportMenu);

        jMenu1.setText("Admin");

        reindecLuceneMenuItem.setText("Reindex lucene");
        reindecLuceneMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reindecLuceneMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(reindecLuceneMenuItem);

        menuBar.add(jMenu1);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(budgetQuarter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(budgetQuarter1, javax.swing.GroupLayout.PREFERRED_SIZE, 742, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void importCompanyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importCompanyMenuItemActionPerformed
        Optional<String> filePath = FileChooser.getInstance().getXmlFilePath(JFileChooser.FILES_AND_DIRECTORIES);
        if (filePath.isPresent()) {

            if (filePath.isPresent()) {
                ProgressDialog progressBar = new ProgressDialog(this, false, ProgressDialog.IMPORT);
                progressBar.setLocationRelativeTo(this);
                progressBar.setVisible(true);

                String path = filePath.get();
                CompanyImporter.getInstance().importCompanies(path);
            }
        }
    }//GEN-LAST:event_importCompanyMenuItemActionPerformed

    private void expenseCategoryMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expenseCategoryMenuItemActionPerformed
        new CategoryEditor(this, Boolean.TRUE).setVisible(true);
    }//GEN-LAST:event_expenseCategoryMenuItemActionPerformed

    private void expenseReportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expenseReportMenuItemActionPerformed
        new TransactionReport(this, true, CategoryTypeEnum.EXPENSE, Boolean.TRUE).setVisible(true);
    }//GEN-LAST:event_expenseReportMenuItemActionPerformed

    private void reindecLuceneMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reindecLuceneMenuItemActionPerformed
        CompanyHandler.getInstance().reIndex();
    }//GEN-LAST:event_reindecLuceneMenuItemActionPerformed

    private void handleCompaniesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleCompaniesMenuItemActionPerformed
        new CompanyEditor(this, true).setVisible(true);
    }//GEN-LAST:event_handleCompaniesMenuItemActionPerformed

    private void exportCompaniesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportCompaniesActionPerformed
        Optional<String> filePath = FileChooser.getInstance().getXmlFilePath(JFileChooser.DIRECTORIES_ONLY);
        if (filePath.isPresent()) {
            ProgressDialog progressBar = new ProgressDialog(this, false, ProgressDialog.EXPORT);
            progressBar.setLocationRelativeTo(this);
            progressBar.setVisible(true);

            CompanyExporter.getInstance().exportCompanies(filePath.get() + "//companies.xml");
        }
    }//GEN-LAST:event_exportCompaniesActionPerformed

    private void exportExpenseTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportExpenseTypesActionPerformed
        Optional<String> filePath = FileChooser.getInstance().getXmlFilePath(JFileChooser.DIRECTORIES_ONLY);
        if (filePath.isPresent()) {
            ProgressDialog progressBar = new ProgressDialog(this, false, ProgressDialog.EXPORT);
            progressBar.setLocationRelativeTo(this);
            progressBar.setVisible(true);

            CategoryExporter.getInstance().exportCategories(filePath.get() + "//categories.xml");
        }
    }//GEN-LAST:event_exportExpenseTypesActionPerformed

    private void importTransactionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importTransactionMenuItemActionPerformed
        FileChooser.getInstance().getCsvFilePath(JFileChooser.FILES_AND_DIRECTORIES).ifPresent(filePath -> {
            ProgressDialog progressBar = new ProgressDialog(this, false, ProgressDialog.IMPORT);
            progressBar.setLocationRelativeTo(this);
            progressBar.setVisible(true);

            Consumer<Transactions> startImporterDialog = transactions -> {
                Importer importer = new Importer(this, true, transactions, NordeaCsvFields.TRANSACTION);
                importer.setVisible(true);
            };

            CsvExtractor<Transactions> extractor = new CsvExtractor(new NordeaCsvTransactionImporter(), filePath, CsvExtractor.CsvFileHasHeaders);
            extractor.executeLogic(startImporterDialog);

        });

    }//GEN-LAST:event_importTransactionMenuItemActionPerformed

    private void importCategoriesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importCategoriesMenuItemActionPerformed
        Optional<String> filePath = FileChooser.getInstance().getXmlFilePath(JFileChooser.FILES_AND_DIRECTORIES);
        if (filePath.isPresent()) {
            ProgressDialog progressBar = new ProgressDialog(this, false, ProgressDialog.IMPORT);
            progressBar.setLocationRelativeTo(this);
            progressBar.setVisible(true);
            CategoryImporter.getInstance().importCategories(filePath.get());
        }
    }//GEN-LAST:event_importCategoriesMenuItemActionPerformed

    private void billReportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billReportMenuItemActionPerformed
        new TransactionReport(this, true, CategoryTypeEnum.BILL, Boolean.TRUE).setVisible(true);
    }//GEN-LAST:event_billReportMenuItemActionPerformed

    private void incomeReportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incomeReportMenuItemActionPerformed
        new TransactionReport(this, true, CategoryTypeEnum.INCOME, Boolean.TRUE).setVisible(true);
    }//GEN-LAST:event_incomeReportMenuItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new TransactionsTotalReport(this, true).setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem billReportMenuItem;
    private se.backede.jeconomix.forms.budget.BudgetQuarter budgetQuarter1;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenuItem expenseCategoryMenuItem;
    private javax.swing.JMenuItem expenseReportMenuItem;
    private javax.swing.JMenuItem exportAll;
    private javax.swing.JMenuItem exportCompanies;
    private javax.swing.JMenuItem exportExpenseTypes;
    private javax.swing.JMenuItem exportExpenses;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem handleCompaniesMenuItem;
    private javax.swing.JMenu handleListMenu;
    private javax.swing.JMenuItem importCategoriesMenuItem;
    private javax.swing.JMenuItem importCompanyMenuItem;
    private javax.swing.JMenu importMenu;
    private javax.swing.JMenuItem importTransactionMenuItem;
    private javax.swing.JMenu importerMenu;
    private javax.swing.JMenuItem incomeReportMenuItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem reindecLuceneMenuItem;
    private javax.swing.JMenu reportMenu;
    // End of variables declaration//GEN-END:variables

}

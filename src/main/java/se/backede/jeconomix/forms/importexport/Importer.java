/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.importexport;

import se.backede.jeconomix.forms.editor.AddCategory;
import java.util.LinkedList;
import java.util.Optional;
import javax.swing.JOptionPane;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.constants.ComboBoxRenderer;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.EventObserver;
import se.backede.jeconomix.event.NegodEvent;
import se.backede.jeconomix.event.events.TransactionEvent;
import se.backede.jeconomix.models.table.TransactionModel;
import se.backede.jeconomix.utils.GenericIterator;
import se.backede.jeconomix.database.CompanyHandler;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.event.events.CategoryEvent;
import se.backede.jeconomix.event.events.fields.CategoryValues;
import se.backede.jeconomix.models.combobox.CategoryComboModel;
import se.backede.jeconomix.renderer.combobox.CategoryItemRenderer;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class Importer extends javax.swing.JDialog implements EventObserver {

    /**
     * Creates new form AddCompany
     */
    private final GenericIterator<CompanyDto> companyIterator;

    public Importer(java.awt.Frame parent, boolean modal, LinkedList<CompanyDto> companies) {
        super(parent, modal);
        companyIterator = new GenericIterator(companies);
        initComponents();
        categoryButtonGroup.add(billRadioButton);
        categoryButtonGroup.add(expenseRadioButton);
        categoryButtonGroup.add(incomeRadioButton);
        categoryButtonGroup.add(transferRadionButton);
        categoryButtonGroup.setSelected(incomeRadioButton.getModel(), true);

        registerAsObserver();
        setCategoryComboBoxData();
        progressBar.setMaximum(companyIterator.getAll().size());
        setValues(companyIterator.first());
    }

    public final void setCategoryComboBoxData() {
        categoryComboBox.setModel(new CategoryComboModel(CategoryTypeEnum.INCOME));
        categoryComboBox.setRenderer(new CategoryItemRenderer(ComboBoxRenderer.SINGLE));
    }

    public final void setValues(CompanyDto company) {
        counterLabel.setText((progressBar.getValue() + 1) + " of " + progressBar.getMaximum());
        companyName.setText(company.getName());
        TransactionModel transactionModel = new TransactionModel(company.getTransactions());
        transactionsTable.setModel(transactionModel);

        if (company.getCategory() != null) {
            categoryComboBox.setSelectedItem(company.getCategory());
        }

        if (!companyIterator.hasNext()) {
            nextButton.setText("Save changes");
        } else {
            nextButton.setText("Next");
        }

        if (!companyIterator.hasPrevious()) {
            prevButton.setEnabled(false);
        } else {
            prevButton.setEnabled(true);
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

        categoryButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        companyName = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        transactionsTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        prevButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        categoryComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        addCategoryComboBox = new javax.swing.JButton();
        counterLabel = new javax.swing.JLabel();
        incomeRadioButton = new javax.swing.JRadioButton();
        expenseRadioButton = new javax.swing.JRadioButton();
        billRadioButton = new javax.swing.JRadioButton();
        transferRadionButton = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        companyName.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        companyName.setText("company");

        transactionsTable.setBackground(new java.awt.Color(255, 255, 255));
        transactionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1"
            }
        ));
        jScrollPane1.setViewportView(transactionsTable);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        prevButton.setBackground(new java.awt.Color(255, 255, 255));
        prevButton.setText("<- Previous");
        prevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevButtonActionPerformed(evt);
            }
        });

        nextButton.setBackground(new java.awt.Color(255, 255, 255));
        nextButton.setText("Next ->");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(319, Short.MAX_VALUE)
                .addComponent(prevButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nextButton)
                .addGap(5, 5, 5))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prevButton)
                    .addComponent(nextButton))
                .addContainerGap())
        );

        jLabel2.setText("Transactions");

        categoryComboBox.setBackground(new java.awt.Color(255, 255, 255));
        categoryComboBox.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        categoryComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        categoryComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                categoryComboBoxItemStateChanged(evt);
            }
        });

        jLabel4.setText("Set Category!");

        progressBar.setBackground(new java.awt.Color(255, 255, 255));

        addCategoryComboBox.setBackground(new java.awt.Color(255, 255, 255));
        addCategoryComboBox.setText("Create  new category");
        addCategoryComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCategoryComboBoxActionPerformed(evt);
            }
        });

        counterLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        counterLabel.setText("1 of 25");

        incomeRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        incomeRadioButton.setText("Income");
        incomeRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incomeRadioButtonActionPerformed(evt);
            }
        });

        expenseRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        expenseRadioButton.setText("Expense");
        expenseRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expenseRadioButtonActionPerformed(evt);
            }
        });

        billRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        billRadioButton.setText("Bill");
        billRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billRadioButtonActionPerformed(evt);
            }
        });

        transferRadionButton.setBackground(new java.awt.Color(255, 255, 255));
        transferRadionButton.setText("Transfer");
        transferRadionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transferRadionButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setText("Company name:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(422, 422, 422)
                            .addComponent(counterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(incomeRadioButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(expenseRadioButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(billRadioButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(transferRadionButton))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(categoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(addCategoryComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(companyName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(progressBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(companyName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(expenseRadioButton)
                        .addComponent(billRadioButton)
                        .addComponent(transferRadionButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(incomeRadioButton)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addCategoryComboBox)
                    .addComponent(categoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(counterLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        if (companyIterator.hasNext()) {

            CompanyDto atCurrentIndex = companyIterator.getAtCurrentIndex();
            if (atCurrentIndex.getCategory() == null) {
                CategoryDto category = (CategoryDto) categoryComboBox.getSelectedItem();
                companyIterator.getAtCurrentIndex().setCategory(category);
            }

            progressBar.setValue(progressBar.getValue() + 1);
            setValues(companyIterator.next());
        } else {

            for (CompanyDto company : companyIterator.getAll()) {
                if (company.getCategory() == null) {
                    JOptionPane.showMessageDialog(rootPane, "Company: " + company.getName() + " must have a category set!", "close", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }

            for (CompanyDto company : companyIterator.getAll()) {
                for (TransactionDto transaction : company.getTransactions()) {
                    transaction.setCompany(company);
                }
                Optional<CompanyDto> persist = CompanyHandler.getInstance().createCompany(company);
            }
            EventController.getInstance().notifyObservers(TransactionEvent.CREATE, null);
            this.dispose();
        }
    }//GEN-LAST:event_nextButtonActionPerformed

    private void prevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevButtonActionPerformed
        if (companyIterator.hasPrevious()) {
            progressBar.setValue(progressBar.getValue() - 1);
            setValues(companyIterator.previous());
        }
    }//GEN-LAST:event_prevButtonActionPerformed

    private void addCategoryComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCategoryComboBoxActionPerformed
        new AddCategory(this, false).setVisible(true);
    }//GEN-LAST:event_addCategoryComboBoxActionPerformed

    private void categoryComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_categoryComboBoxItemStateChanged
        CategoryDto category = (CategoryDto) evt.getItem();
        CompanyDto selectedCompany = companyIterator.getAtCurrentIndex();
        selectedCompany.setCategory(category);
        companyIterator.replaceAtCurrentIndex(selectedCompany);
    }//GEN-LAST:event_categoryComboBoxItemStateChanged

    private void incomeRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incomeRadioButtonActionPerformed
        categoryComboBox.setModel(new CategoryComboModel(CategoryTypeEnum.INCOME));
        categoryComboBox.setRenderer(new CategoryItemRenderer(ComboBoxRenderer.SINGLE));
    }//GEN-LAST:event_incomeRadioButtonActionPerformed

    private void expenseRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expenseRadioButtonActionPerformed
        categoryComboBox.setModel(new CategoryComboModel(CategoryTypeEnum.EXPENSE));
        categoryComboBox.setRenderer(new CategoryItemRenderer(ComboBoxRenderer.SINGLE));
    }//GEN-LAST:event_expenseRadioButtonActionPerformed

    private void billRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billRadioButtonActionPerformed
        categoryComboBox.setModel(new CategoryComboModel(CategoryTypeEnum.BILL));
        categoryComboBox.setRenderer(new CategoryItemRenderer(ComboBoxRenderer.SINGLE));
    }//GEN-LAST:event_billRadioButtonActionPerformed

    private void transferRadionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transferRadionButtonActionPerformed
        categoryComboBox.setModel(new CategoryComboModel(CategoryTypeEnum.TRANSFER));
        categoryComboBox.setRenderer(new CategoryItemRenderer(ComboBoxRenderer.SINGLE));
    }//GEN-LAST:event_transferRadionButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCategoryComboBox;
    private javax.swing.JRadioButton billRadioButton;
    private javax.swing.ButtonGroup categoryButtonGroup;
    private javax.swing.JComboBox<String> categoryComboBox;
    private javax.swing.JLabel companyName;
    private javax.swing.JLabel counterLabel;
    private javax.swing.JRadioButton expenseRadioButton;
    private javax.swing.JRadioButton incomeRadioButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton prevButton;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JTable transactionsTable;
    private javax.swing.JRadioButton transferRadionButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(NegodEvent event) {
        if (event.equalsEvent(CategoryEvent.CREATE)) {
            Optional<CategoryDto> category = event.getValues().get(CategoryValues.CATEGORY_DTO).getObject();
            if (category.isPresent()) {
                CategoryComboModel model = (CategoryComboModel) categoryComboBox.getModel();
                model.addElement(category.get());
            }
        }
    }

}

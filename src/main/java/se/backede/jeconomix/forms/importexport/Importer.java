/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.importexport;

import java.util.Enumeration;
import se.backede.jeconomix.forms.editor.AddCategory;
import java.util.LinkedList;
import java.util.Optional;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.constants.ComboBoxRenderer;
import se.backede.jeconomix.constants.Creation;
import se.backede.jeconomix.constants.Modal;
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
import se.backede.jeconomix.dto.CompanyAccociationDto;
import se.backede.jeconomix.event.events.CategoryEvent;
import se.backede.jeconomix.event.events.CompanyEvent;
import se.backede.jeconomix.event.events.fields.CategoryValues;
import se.backede.jeconomix.event.events.fields.CompanyValues;
import se.backede.jeconomix.forms.editor.AddCompany;
import se.backede.jeconomix.models.combobox.CategoryComboModel;
import se.backede.jeconomix.models.combobox.CompanyComboBoxModel;
import se.backede.jeconomix.renderer.combobox.CategoryItemRenderer;
import se.backede.jeconomix.renderer.combobox.CompanyComboBoxRenderer;

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
        setCategoryComboBoxData(CategoryTypeEnum.INCOME);
        setAccociatedComboBoxData();
        progressBar.setMaximum(companyIterator.getAll().size());
        setValues(companyIterator.first());

        toggleAccocoationItems(!accociateToggleBtn.isSelected());
    }

    private void toggleAccocoationItems(boolean value) {
        for (Enumeration<AbstractButton> buttons = categoryButtonGroup.getElements(); buttons.hasMoreElements();) {
            buttons.nextElement().setEnabled(value);
        }
        categoryComboBox.setEnabled(value);
        addCategoryBtn.setEnabled(value);
        addCompanyButton.setEnabled(!value);
        accociatedComboBox.setEnabled(!value);
    }

    public final void setAccociatedComboBoxData() {
        CompanyHandler.getInstance().getAllCompanies().ifPresent(company -> {
            accociatedComboBox.setModel(new CompanyComboBoxModel(company));
        });
        accociatedComboBox.setRenderer(new CompanyComboBoxRenderer());
    }

    public final void setCategoryComboBoxData(CategoryTypeEnum category) {
        categoryComboBox.setModel(new CategoryComboModel(category));
        categoryComboBox.setRenderer(new CategoryItemRenderer(ComboBoxRenderer.SINGLE));
    }

    public final void setValues(CompanyDto company) {
        counterLabel.setText((progressBar.getValue() + 1) + " of " + progressBar.getMaximum());

        companyName.setText(company.getName());

        companyName.setText(
                company.getNewlyAcciciatedCompanyName().isPresent()
                ? company.getNewlyAcciciatedCompanyName().get()
                : company.getName());

        if (company.getNewlyAcciciatedCompanyName().isPresent()) {
            accociatedComboBox.setSelectedItem(company);
            accociatedComboBoxActionPerformed(null);
        } else {
            accociatedComboBoxActionPerformed(null);
        }

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
        progressBar = new javax.swing.JProgressBar();
        counterLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        categoryComboBox = new javax.swing.JComboBox<>();
        addCategoryBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        incomeRadioButton = new javax.swing.JRadioButton();
        transferRadionButton = new javax.swing.JRadioButton();
        billRadioButton = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        expenseRadioButton = new javax.swing.JRadioButton();
        accociatedComboBox = new javax.swing.JComboBox<>();
        addCompanyButton = new javax.swing.JButton();
        accociateToggleBtn = new javax.swing.JToggleButton();

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

        progressBar.setBackground(new java.awt.Color(255, 255, 255));

        counterLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        counterLabel.setText("1 of 25");

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setText("Company name:");

        jLabel3.setText("New company name found!");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel5.setText("Accociate with:");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        categoryComboBox.setBackground(new java.awt.Color(255, 255, 255));
        categoryComboBox.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        categoryComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        categoryComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                categoryComboBoxItemStateChanged(evt);
            }
        });

        addCategoryBtn.setBackground(new java.awt.Color(255, 255, 255));
        addCategoryBtn.setText("Create  new category");
        addCategoryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCategoryBtnActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        incomeRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        incomeRadioButton.setText("Income");
        incomeRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incomeRadioButtonActionPerformed(evt);
            }
        });

        transferRadionButton.setBackground(new java.awt.Color(255, 255, 255));
        transferRadionButton.setText("Transfer");
        transferRadionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transferRadionButtonActionPerformed(evt);
            }
        });

        billRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        billRadioButton.setText("Bill");
        billRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billRadioButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("Set Category!");

        expenseRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        expenseRadioButton.setText("Expense");
        expenseRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expenseRadioButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(incomeRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(expenseRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(billRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(transferRadionButton)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(expenseRadioButton)
                        .addComponent(billRadioButton)
                        .addComponent(transferRadionButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(incomeRadioButton)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(categoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addCategoryBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(categoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addCategoryBtn))
                .addGap(7, 7, 7))
        );

        accociatedComboBox.setBackground(new java.awt.Color(255, 255, 255));
        accociatedComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        accociatedComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accociatedComboBoxActionPerformed(evt);
            }
        });

        addCompanyButton.setText("Create new and accociate");
        addCompanyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCompanyButtonActionPerformed(evt);
            }
        });

        accociateToggleBtn.setText("Accociate");
        accociateToggleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accociateToggleBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(422, 422, 422)
                                .addComponent(counterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(progressBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel1)
                                                .addComponent(jLabel5))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                    .addComponent(accociateToggleBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(addCompanyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(companyName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(accociatedComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                    .addComponent(jLabel3))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(companyName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(accociatedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addCompanyButton)
                    .addComponent(accociateToggleBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void addCategoryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCategoryBtnActionPerformed
        new AddCategory(this, false).setVisible(true);
    }//GEN-LAST:event_addCategoryBtnActionPerformed

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

    private void addCompanyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCompanyButtonActionPerformed
        AddCompany addCompany = new AddCompany(this, Modal.TRUE, Creation.EVENT, companyIterator.getAtCurrentIndex());
        addCompany.setVisible(true);
    }//GEN-LAST:event_addCompanyButtonActionPerformed

    private void accociatedComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accociatedComboBoxActionPerformed

        CompanyComboBoxModel model = (CompanyComboBoxModel) accociatedComboBox.getModel();

        CompanyDto company = (CompanyDto) model.getSelectedItem();
        CategoryDto category = company.getCategory();

        switch (category.getCategoryType().getType()) {
            case INCOME:
                categoryButtonGroup.setSelected(incomeRadioButton.getModel(), true);
                break;
            case EXPENSE:
                categoryButtonGroup.setSelected(expenseRadioButton.getModel(), true);
                break;
            case BILL:
                categoryButtonGroup.setSelected(billRadioButton.getModel(), true);
                break;
            case TRANSFER:
                categoryButtonGroup.setSelected(transferRadionButton.getModel(), true);
                break;
            default:
                throw new AssertionError();
        }

        setCategoryComboBoxData(category.getCategoryType().getType());

        CategoryComboModel categoryModel = (CategoryComboModel) categoryComboBox.getModel();

        for (int i = 0; i < categoryModel.getSize(); i++) {
            CategoryDto categoryDto = (CategoryDto) categoryModel.getElementAt(i);
            if (categoryDto.getId().equals(company.getCategory().getId())) {
                categoryComboBox.setSelectedItem(categoryDto);
            }
        }

        //categoryComboBox.setSelectedItem(category);
    }//GEN-LAST:event_accociatedComboBoxActionPerformed

    private void accociateToggleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accociateToggleBtnActionPerformed
        toggleAccocoationItems(!accociateToggleBtn.isSelected());
    }//GEN-LAST:event_accociateToggleBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton accociateToggleBtn;
    private javax.swing.JComboBox<String> accociatedComboBox;
    private javax.swing.JButton addCategoryBtn;
    private javax.swing.JButton addCompanyButton;
    private javax.swing.JRadioButton billRadioButton;
    private javax.swing.ButtonGroup categoryButtonGroup;
    private javax.swing.JComboBox<String> categoryComboBox;
    private javax.swing.JLabel companyName;
    private javax.swing.JLabel counterLabel;
    private javax.swing.JRadioButton expenseRadioButton;
    private javax.swing.JRadioButton incomeRadioButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
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

        if (event.equalsEvent(CompanyEvent.CREATE)) {

            Optional<CompanyDto> company = event.getValues().get(CompanyValues.COMPANY_DTO).getObject();

            if (company.isPresent()) {
                CompanyComboBoxModel model = (CompanyComboBoxModel) accociatedComboBox.getModel();

                for (CompanyAccociationDto accociation : company.get().getAccociations()) {
                    company.get().getAccociations().add(accociation);
                }

                company.get().setTransactions(companyIterator.getAtCurrentIndex().getTransactions());
                company.get().setNewlyAcciciatedCompanyName(Optional.of(companyIterator.getAtCurrentIndex().getName()));
                companyIterator.replaceAtCurrentIndex(company.get());
                model.addElement(company.get());
            }
        }

    }

}

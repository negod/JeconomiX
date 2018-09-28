/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.company;

import java.awt.event.ItemEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.constants.ComboBoxRenderer;
import se.backede.jeconomix.database.CategoryHandler;
import se.backede.jeconomix.database.CompanyHandler;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.events.CategoryEvent;
import se.backede.jeconomix.forms.basic.NegodDialog;
import se.backede.jeconomix.models.combobox.CategoryComboBoxModel;
import se.backede.jeconomix.models.table.CompanyModel;
import se.backede.jeconomix.models.table.TransactionModel;
import se.backede.jeconomix.renderer.combobox.CategoryItemRenderer;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CompanyEditor extends NegodDialog {

    private static final long serialVersionUID = 1L;

    /**
     * Creates new form CompanyHandler
     */
    public CompanyEditor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        categoryChooser1.init();
        setTableData(null, CategoryTypeEnum.INCOME);
        setEvents();
    }

    public void setEvents() {

        Consumer<CategoryTypeEnum> setSelectedCategoryType = categoryType -> {
            if (categoryType != null) {
                setTableData(null, categoryType);
            }
        };
        EventController.getInstance().addObserver(CategoryEvent.CATEGORY_TYPE_SELECTED, setSelectedCategoryType);

        Consumer<CategoryDto> setSelectedCategory = category -> {
            if (category != null) {
                setTableData(category, category.getCategoryType().getType());
            }
        };
        EventController.getInstance().addObserver(CategoryEvent.SELECTED, setSelectedCategory);

    }

    public void setUpCategoryDropDownColumn(TableColumn column, CategoryTypeEnum type) {
        JComboBox comboBox = new JComboBox();

        comboBox.<CategoryComboBoxModel>setModel(new CategoryComboBoxModel(type));
        comboBox.<CategoryItemRenderer>setRenderer(new CategoryItemRenderer(ComboBoxRenderer.SINGLE));
        column.setCellEditor(new DefaultCellEditor(comboBox));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click to edit Category");
        column.setCellRenderer(renderer);

        comboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                categoryComboBoxItemStateChanged(evt);
            }
        });
    }

    private void categoryComboBoxItemStateChanged(ItemEvent evt) {
        CompanyModel model = (CompanyModel) companyTable.getModel();
        CompanyDto company = model.getCompanyAt(companyTable.getSelectedRow());
        company.setCategory((CategoryDto) evt.getItem());
        CompanyHandler.getInstance().setCategory(company, (CategoryDto) evt.getItem());
    }

    public void setTableData(CategoryDto category, CategoryTypeEnum categoryType) {

        CategoryHandler.getInstance().getFilteredCategories(categoryType).map(categories -> {
            Set<CompanyDto> companies = new HashSet<>();
            for (CategoryDto categoryDto : categories) {

                if (category == null) {
                    companies.addAll(categoryDto.getCompanies());
                } else {
                    if (categoryDto.getId().equals(category.getId())) {
                        companies.addAll(categoryDto.getCompanies());
                    }
                }

            }
            return companies;

        }).ifPresent(companyList -> {

            CompanyModel companyModel = new CompanyModel(companyList.stream().collect(Collectors.toList()));
            companyTable.setModel(companyModel);
            setUpCategoryDropDownColumn(companyTable.getColumnModel().getColumn(1), categoryType);

            if (!companyList.isEmpty()) {
                companyTable.setRowSelectionInterval(0, 0);
                CompanyDto selectedCompany = companyModel.getCompanyAt(0);

                //Align columns to right
                DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
                rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
                TransactionModel transModel = new TransactionModel(selectedCompany.getTransactions());
                transactionTable.setModel(transModel);
                transactionTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);

                transactionSumLabel.setText(transModel.getSum().toString().concat(" Kr"));

                companyNameLabel.setText(selectedCompany.getName());
            }
            companyTable.setModel(companyModel);
            setUpCategoryDropDownColumn(companyTable.getColumnModel().getColumn(1), categoryType);

        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        transactionSumLabel = new javax.swing.JLabel();
        companyNameLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        transactionTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        companyTable = new javax.swing.JTable();
        sumLabel = new javax.swing.JLabel();
        categoryChooser1 = new se.backede.jeconomix.forms.category.CategoryChooser();
        setParentCompanyBtn = new javax.swing.JButton();
        addCompanyBtn = new javax.swing.JButton();
        deleteCompanyBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        transactionSumLabel.setBackground(new java.awt.Color(255, 255, 255));
        transactionSumLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        transactionSumLabel.setText("jLabel1");

        companyNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        companyNameLabel.setText("jLabel1");
        companyNameLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        transactionTable.setAutoCreateRowSorter(true);
        transactionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        transactionTable.setShowHorizontalLines(false);
        jScrollPane2.setViewportView(transactionTable);

        companyTable.setAutoCreateRowSorter(true);
        companyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        companyTable.setShowVerticalLines(false);
        companyTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                companyTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(companyTable);

        sumLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        sumLabel.setText("Sum:");

        setParentCompanyBtn.setText("P");
        setParentCompanyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setParentCompanyBtnActionPerformed(evt);
            }
        });

        addCompanyBtn.setText("+");
        addCompanyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCompanyBtnActionPerformed(evt);
            }
        });

        deleteCompanyBtn.setText("-");
        deleteCompanyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCompanyBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(categoryChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(companyNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(deleteCompanyBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addCompanyBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                            .addComponent(setParentCompanyBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(sumLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(transactionSumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(companyNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(deleteCompanyBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addCompanyBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(setParentCompanyBtn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(transactionSumLabel)
                        .addComponent(sumLabel))
                    .addComponent(categoryChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void companyTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_companyTableMouseClicked
        int selectedRow = companyTable.getSelectedRow();
        CompanyModel model = (CompanyModel) companyTable.getModel();
        CompanyDto company = model.getCompanyAt(selectedRow);

        if (company.getTransactions() != null) {
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

            TransactionModel transModel = new TransactionModel(company.getTransactions());
            transactionTable.setModel(transModel);
            transactionTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);

            transactionSumLabel.setText(transModel.getSum().toString().concat(" Kr"));

        }

        companyNameLabel.setText(company.getName());
    }//GEN-LAST:event_companyTableMouseClicked

    private void addCompanyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCompanyBtnActionPerformed
        new AddCompany(this, true);
    }//GEN-LAST:event_addCompanyBtnActionPerformed

    private void deleteCompanyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCompanyBtnActionPerformed
        CompanyModel model = (CompanyModel) companyTable.getModel();
        CompanyHandler.getInstance().delete(model.getCompanyAt(companyTable.getSelectedRow()).getId());
    }//GEN-LAST:event_deleteCompanyBtnActionPerformed

    private void setParentCompanyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setParentCompanyBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_setParentCompanyBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCompanyBtn;
    private se.backede.jeconomix.forms.category.CategoryChooser categoryChooser1;
    private javax.swing.JLabel companyNameLabel;
    private javax.swing.JTable companyTable;
    private javax.swing.JButton deleteCompanyBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton setParentCompanyBtn;
    private javax.swing.JLabel sumLabel;
    private javax.swing.JLabel transactionSumLabel;
    private javax.swing.JTable transactionTable;
    // End of variables declaration//GEN-END:variables

}

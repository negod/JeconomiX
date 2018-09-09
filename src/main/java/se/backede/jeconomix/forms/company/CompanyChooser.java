/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.company;

import java.util.function.Consumer;
import java.util.function.Supplier;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.events.CompanyEvent;
import se.backede.jeconomix.forms.basic.NegodPanel;
import se.backede.jeconomix.forms.basic.component.ComboBoxWrapper;
import se.backede.jeconomix.models.combobox.CompanyComboBoxModel;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CompanyChooser extends NegodPanel {

    private static final long serialVersionUID = 1L;

    ComboBoxWrapper<CompanyDto, CompanyComboBoxModel> companyCB;

    /**
     * Creates new form CategoryChooser
     */
    public CompanyChooser() {
        super();
        initComponents();

        Consumer<CompanyDto> setSelectedCompany = company -> {
            companyCB.setSelectedItem(company);
        };
        EventController.getInstance().addObserver(CompanyEvent.SET_SELECTED, setSelectedCompany);

        Consumer<CompanyDto> createCompany = company -> {
            companyCB.getComboBoxModel().addElement(company);
        };
        EventController.getInstance().addObserver(CompanyEvent.CREATE, createCompany);
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
        jPanel4 = new javax.swing.JPanel();
        companyComboBox = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        incomeRadioButton = new javax.swing.JRadioButton();
        billRadioButton = new javax.swing.JRadioButton();
        expenseRadioButton = new javax.swing.JRadioButton();
        transferRadionButton = new javax.swing.JRadioButton();
        addCompanyBtn = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        companyComboBox.setBackground(new java.awt.Color(255, 255, 255));
        companyComboBox.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        companyComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        companyComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                companyComboBoxItemStateChanged(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("Choose category!");

        incomeRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        categoryButtonGroup.add(incomeRadioButton);
        incomeRadioButton.setText("Income");
        incomeRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incomeRadioButtonActionPerformed(evt);
            }
        });

        billRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        categoryButtonGroup.add(billRadioButton);
        billRadioButton.setText("Bill");
        billRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billRadioButtonActionPerformed(evt);
            }
        });

        expenseRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        categoryButtonGroup.add(expenseRadioButton);
        expenseRadioButton.setText("Expense");
        expenseRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expenseRadioButtonActionPerformed(evt);
            }
        });

        transferRadionButton.setBackground(new java.awt.Color(255, 255, 255));
        categoryButtonGroup.add(transferRadionButton);
        transferRadionButton.setText("Transfer");
        transferRadionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transferRadionButtonActionPerformed(evt);
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(incomeRadioButton)
                    .addComponent(expenseRadioButton)
                    .addComponent(billRadioButton)
                    .addComponent(transferRadionButton))
                .addContainerGap())
        );

        addCompanyBtn.setBackground(new java.awt.Color(255, 255, 255));
        addCompanyBtn.setText("Create  new company");
        addCompanyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCompanyBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 30, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(companyComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(addCompanyBtn)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(companyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addCompanyBtn)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void companyComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_companyComboBoxItemStateChanged
        Supplier<CompanyDto> getCompany = () -> (CompanyDto) evt.getItem();
        EventController.getInstance().notifyObservers(CompanyEvent.CREATE, getCompany);
    }//GEN-LAST:event_companyComboBoxItemStateChanged

    private void addCompanyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCompanyBtnActionPerformed
        AddCompany company = new AddCompany(null, true);
        company.setAlwaysOnTop(true);
        company.setVisible(true);
    }//GEN-LAST:event_addCompanyBtnActionPerformed

    private void incomeRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incomeRadioButtonActionPerformed
        companyCB.getComboBoxModel().getAllData(CategoryTypeEnum.INCOME);
    }//GEN-LAST:event_incomeRadioButtonActionPerformed

    private void transferRadionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transferRadionButtonActionPerformed
        companyCB.getComboBoxModel().getAllData(CategoryTypeEnum.TRANSFER);
    }//GEN-LAST:event_transferRadionButtonActionPerformed

    private void billRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billRadioButtonActionPerformed
        companyCB.getComboBoxModel().getAllData(CategoryTypeEnum.BILL);
    }//GEN-LAST:event_billRadioButtonActionPerformed

    private void expenseRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expenseRadioButtonActionPerformed
        companyCB.getComboBoxModel().getAllData(CategoryTypeEnum.EXPENSE);
    }//GEN-LAST:event_expenseRadioButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCompanyBtn;
    private javax.swing.JRadioButton billRadioButton;
    private javax.swing.ButtonGroup categoryButtonGroup;
    private javax.swing.JComboBox<String> companyComboBox;
    private javax.swing.JRadioButton expenseRadioButton;
    private javax.swing.JRadioButton incomeRadioButton;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton transferRadionButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void init() {
        categoryButtonGroup.setSelected(incomeRadioButton.getModel(), true);
        companyCB = new ComboBoxWrapper(companyComboBox);
        companyCB.setComboBoxModel(new CompanyComboBoxModel(CategoryTypeEnum.INCOME));
    }
}

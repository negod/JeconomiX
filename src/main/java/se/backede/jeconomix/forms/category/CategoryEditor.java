/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.category;

import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.forms.basic.NegodDialog;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CategoryEditor extends NegodDialog {

    /**
     * Creates new form CategoryHandler
     */
    public CategoryEditor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        incomeTab = new se.backede.jeconomix.forms.category.CategoryEdit(CategoryTypeEnum.INCOME);
        expenseTab = new se.backede.jeconomix.forms.category.CategoryEdit(CategoryTypeEnum.EXPENSE);
        billTab = new se.backede.jeconomix.forms.category.CategoryEdit(CategoryTypeEnum.BILL);
        transferTab = new se.backede.jeconomix.forms.category.CategoryEdit(CategoryTypeEnum.TRANSFER);
        loanTab = new se.backede.jeconomix.forms.category.CategoryEdit(CategoryTypeEnum.LOAN);
        creditCardTab = new se.backede.jeconomix.forms.category.CategoryEdit(CategoryTypeEnum.CREDIT_CARD);
        savingsTab = new se.backede.jeconomix.forms.category.CategoryEdit(CategoryTypeEnum.SAVING);
        pocketMoneyTab = new se.backede.jeconomix.forms.category.CategoryEdit(CategoryTypeEnum.POCKET_MONEY);
        companyEditTable1 = new se.backede.jeconomix.forms.company.CompanyEditTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTabbedPane1.addTab("Income", incomeTab);
        jTabbedPane1.addTab("Expense", expenseTab);
        jTabbedPane1.addTab("Bill", billTab);
        jTabbedPane1.addTab("Transfer", transferTab);
        jTabbedPane1.addTab("Loan", loanTab);
        jTabbedPane1.addTab("Credit card", creditCardTab);
        jTabbedPane1.addTab("Savings", savingsTab);
        jTabbedPane1.addTab("Pocket money", pocketMoneyTab);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(companyEditTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(companyEditTable1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private se.backede.jeconomix.forms.category.CategoryEdit billTab;
    private se.backede.jeconomix.forms.company.CompanyEditTable companyEditTable1;
    private se.backede.jeconomix.forms.category.CategoryEdit creditCardTab;
    private se.backede.jeconomix.forms.category.CategoryEdit expenseTab;
    private se.backede.jeconomix.forms.category.CategoryEdit incomeTab;
    private javax.swing.JTabbedPane jTabbedPane1;
    private se.backede.jeconomix.forms.category.CategoryEdit loanTab;
    private se.backede.jeconomix.forms.category.CategoryEdit pocketMoneyTab;
    private se.backede.jeconomix.forms.category.CategoryEdit savingsTab;
    private se.backede.jeconomix.forms.category.CategoryEdit transferTab;
    // End of variables declaration//GEN-END:variables

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms;

import java.math.BigDecimal;
import java.time.Month;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.constants.ComboBoxRenderer;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;
import se.backede.jeconomix.models.combobox.CategoryComboModel;
import se.backede.jeconomix.models.table.BudgetModel;
import se.backede.jeconomix.renderer.combobox.CategoryItemRenderer;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BudgetMonth extends javax.swing.JPanel {

    /**
     * Creates new form BudgetMonth
     */
    public BudgetMonth() {
        initComponents();
    }

    public void setMonth(Month month, Integer year) {
        monthLabel.setText(month.name());

        billTable.setModel(new BudgetModel(month, year, CategoryTypeEnum.BILL));
        setUpDropDownColumn(billTable, billTable.getColumnModel().getColumn(0), CategoryTypeEnum.BILL);

        expenseTable.setModel(new BudgetModel(month, year, CategoryTypeEnum.EXPENSE));
        setUpDropDownColumn(expenseTable, expenseTable.getColumn(CategoryTypeEnum.EXPENSE.name()), CategoryTypeEnum.EXPENSE);

        incomeTable.setModel(new BudgetModel(month, year, CategoryTypeEnum.INCOME));
        setUpDropDownColumn(incomeTable, incomeTable.getColumn(CategoryTypeEnum.INCOME.name()), CategoryTypeEnum.INCOME);

    }

    public void setUpDropDownColumn(JTable table, TableColumn column, CategoryTypeEnum type) {
        //Set up the editor for the sport cells.
        JComboBox comboBox = new JComboBox();
        comboBox.setModel(new CategoryComboModel(type));
        comboBox.setRenderer(new CategoryItemRenderer(ComboBoxRenderer.SINGLE));
        column.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        column.setCellRenderer(renderer);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        incomeTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        billTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        expenseTable = new javax.swing.JTable();
        monthLabel = new javax.swing.JLabel();
        addIncomeBtn = new javax.swing.JButton();
        removeIncomeBtn = new javax.swing.JButton();
        removeBillBtn = new javax.swing.JButton();
        addBillBtn = new javax.swing.JButton();
        addExpenseBtn = new javax.swing.JButton();
        removeExpenseBtn = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        incomeTable.setBackground(new java.awt.Color(255, 255, 255));
        incomeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Income", "Sum"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(incomeTable);
        if (incomeTable.getColumnModel().getColumnCount() > 0) {
            incomeTable.getColumnModel().getColumn(0).setResizable(false);
            incomeTable.getColumnModel().getColumn(1).setResizable(false);
        }

        billTable.setBackground(new java.awt.Color(255, 255, 255));
        billTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bill", "Sum"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(billTable);
        if (billTable.getColumnModel().getColumnCount() > 0) {
            billTable.getColumnModel().getColumn(0).setResizable(false);
            billTable.getColumnModel().getColumn(1).setResizable(false);
        }

        expenseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Expense", "Sum"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(expenseTable);
        if (expenseTable.getColumnModel().getColumnCount() > 0) {
            expenseTable.getColumnModel().getColumn(0).setResizable(false);
        }

        monthLabel.setBackground(new java.awt.Color(255, 255, 255));
        monthLabel.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        monthLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        monthLabel.setText("JANUARY");

        addIncomeBtn.setBackground(new java.awt.Color(255, 255, 255));
        addIncomeBtn.setText("+");
        addIncomeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addIncomeBtnActionPerformed(evt);
            }
        });

        removeIncomeBtn.setBackground(new java.awt.Color(255, 255, 255));
        removeIncomeBtn.setText("-");
        removeIncomeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeIncomeBtnActionPerformed(evt);
            }
        });

        removeBillBtn.setBackground(new java.awt.Color(255, 255, 255));
        removeBillBtn.setText("-");
        removeBillBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBillBtnActionPerformed(evt);
            }
        });

        addBillBtn.setBackground(new java.awt.Color(255, 255, 255));
        addBillBtn.setText("+");
        addBillBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBillBtnActionPerformed(evt);
            }
        });

        addExpenseBtn.setBackground(new java.awt.Color(255, 255, 255));
        addExpenseBtn.setText("+");
        addExpenseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addExpenseBtnActionPerformed(evt);
            }
        });

        removeExpenseBtn.setBackground(new java.awt.Color(255, 255, 255));
        removeExpenseBtn.setText("-");
        removeExpenseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeExpenseBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addIncomeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeIncomeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeBillBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addBillBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addExpenseBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeExpenseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addIncomeBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeIncomeBtn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addBillBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeBillBtn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addExpenseBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeExpenseBtn)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public void addBudgetExense(JTable table) {
        BudgetModel model = (BudgetModel) table.getModel();
        BudgetExpenseDto dto = new BudgetExpenseDto();
        CategoryDto category = new CategoryDto();
        category.setName("");
        dto.setCategory(category);
        dto.setEstimatedsum(BigDecimal.valueOf(0.00));
        model.addBudgetExpence(dto);
    }

    public void removeBudgetExpense(JTable table) {
        BudgetModel model = (BudgetModel) table.getModel();
        model.removeBudgetExpenseAt(table.getSelectedRow());
    }

    private void addIncomeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addIncomeBtnActionPerformed
        addBudgetExense(incomeTable);
    }//GEN-LAST:event_addIncomeBtnActionPerformed

    private void removeIncomeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeIncomeBtnActionPerformed
        removeBudgetExpense(incomeTable);
    }//GEN-LAST:event_removeIncomeBtnActionPerformed

    private void removeExpenseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeExpenseBtnActionPerformed
        removeBudgetExpense(expenseTable);
    }//GEN-LAST:event_removeExpenseBtnActionPerformed

    private void addBillBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBillBtnActionPerformed
        addBudgetExense(billTable);
    }//GEN-LAST:event_addBillBtnActionPerformed

    private void removeBillBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBillBtnActionPerformed
        removeBudgetExpense(billTable);
    }//GEN-LAST:event_removeBillBtnActionPerformed

    private void addExpenseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addExpenseBtnActionPerformed
        addBudgetExense(expenseTable);
    }//GEN-LAST:event_addExpenseBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBillBtn;
    private javax.swing.JButton addExpenseBtn;
    private javax.swing.JButton addIncomeBtn;
    private javax.swing.JTable billTable;
    private javax.swing.JTable expenseTable;
    private javax.swing.JTable incomeTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JButton removeBillBtn;
    private javax.swing.JButton removeExpenseBtn;
    private javax.swing.JButton removeIncomeBtn;
    // End of variables declaration//GEN-END:variables
}

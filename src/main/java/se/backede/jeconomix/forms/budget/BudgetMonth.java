/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.budget;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Optional;
import java.util.function.Consumer;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.events.BudgetEvent;
import se.backede.jeconomix.forms.basic.NegodPanel;
import se.backede.jeconomix.models.table.BudgetModel;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BudgetMonth extends NegodPanel {

    private static final long serialVersionUID = 1L;

    private YearMonth currentYearMonth;

    /**
     * Creates new form BudgetMonth
     */
    public BudgetMonth() {
        initComponents();
        setEvents();
    }

    public void setMonth(YearMonth yearMonth) {
        this.currentYearMonth = yearMonth;
        monthLabel.setText(yearMonth.getMonth().name());

        setInitData(billTable, yearMonth, totalExpenseLbl, CategoryTypeEnum.BILL);
        setInitData(expenseTable, yearMonth, totalExpenseLbl, CategoryTypeEnum.EXPENSE);
        setInitData(incomeTable, yearMonth, totalIncomeLbl, CategoryTypeEnum.INCOME);
    }

    public void setInitData(JTable table, YearMonth budgetMonth, JLabel lbl, CategoryTypeEnum category) {
        table.setModel(new BudgetModel(budgetMonth, category));
        setUpDropDownColumn(table, table.getColumnModel().getColumn(0), category);
        BudgetModel billModel = (BudgetModel) table.getModel();
        lbl.setText(billModel.getTotalSumForColumn(1).toString());
    }

    public void setEvents() {

        Consumer<BudgetExpenseDto> createBudgetExpense = (dto) -> {

            if (dto.getBudget().getMonth().equals(currentYearMonth.getMonth()) && dto.getBudget().getYear() == currentYearMonth.getYear()) {

                switch (dto.getCategory().getCategoryType().getType()) {

                    case INCOME:
                        BudgetModel icomeModel = (BudgetModel) incomeTable.getModel();
                        icomeModel.addBudgetExpence(dto);
                        totalIncomeLbl.setText(icomeModel.getTotalSumForColumn(1).toString());
                        break;
                    case EXPENSE:
                        BudgetModel expenseModel = (BudgetModel) expenseTable.getModel();
                        expenseModel.addBudgetExpence(dto);
                        totalExpenseLbl.setText(expenseModel.getTotalSumForColumn(1).toString());
                        break;
                    case BILL:
                        BudgetModel billModel = (BudgetModel) billTable.getModel();
                        billModel.addBudgetExpence(dto);
                        totalBillLbl.setText(billModel.getTotalSumForColumn(1).toString());
                        break;
                    case TRANSFER:
                        break;
                    default:
                        throw new AssertionError(dto.getCategory().getCategoryType().getType().name());

                }

            }

        };
        EventController.getInstance().addObserver(BudgetEvent.ADD_BUDGET_ROW, createBudgetExpense);

    }

    public void setUpDropDownColumn(JTable table, TableColumn column, CategoryTypeEnum type) {
//        //Set up the editor for the sport cells.
//        JComboBox comboBox = new JComboBox();
//        comboBox.setModel(new CategoryComboBoxModel(type));
//        comboBox.setRenderer(new CompanyItemRenderer(ComboBoxRenderer.SINGLE));
//        column.setCellEditor(new DefaultCellEditor(comboBox));
//
//        //Set up tool tips for the sport cells.
//        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
//        renderer.setToolTipText("Click for combo box");
//        column.setCellRenderer(renderer);
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
        budgetSuggestionBtn = new javax.swing.JButton();
        totalIncomeLbl = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        totalBillLbl = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        totalExpenseLbl = new javax.swing.JLabel();

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
        incomeTable.setFocusCycleRoot(true);
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

        budgetSuggestionBtn.setBackground(new java.awt.Color(255, 255, 255));
        budgetSuggestionBtn.setText("*");
        budgetSuggestionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                budgetSuggestionBtnActionPerformed(evt);
            }
        });

        totalIncomeLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalIncomeLbl.setText("0.00");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Total:");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Total:");

        totalBillLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalBillLbl.setText("0.00");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Total:");

        totalExpenseLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalExpenseLbl.setText("0.00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(budgetSuggestionBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalExpenseLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalBillLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalIncomeLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addIncomeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeIncomeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeBillBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addBillBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addExpenseBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeExpenseBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(budgetSuggestionBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addIncomeBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeIncomeBtn))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalIncomeLbl)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalBillLbl)
                            .addComponent(jLabel3)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addBillBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeBillBtn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addExpenseBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeExpenseBtn))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalExpenseLbl)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void addBudgetLine(CategoryTypeEnum category, Optional<BudgetExpenseDto> budgetExpense) {
        AddBudgetLine budgetLine = new AddBudgetLine(budgetExpense, currentYearMonth);
        budgetLine.init(category);
        budgetLine.setVisible(true);
    }

    private void addIncomeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addIncomeBtnActionPerformed
        addBudgetLine(CategoryTypeEnum.INCOME, Optional.empty());
    }//GEN-LAST:event_addIncomeBtnActionPerformed

    private void removeIncomeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeIncomeBtnActionPerformed
        removeBudgetExpense(incomeTable);
    }//GEN-LAST:event_removeIncomeBtnActionPerformed

    private void removeExpenseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeExpenseBtnActionPerformed
        removeBudgetExpense(expenseTable);
    }//GEN-LAST:event_removeExpenseBtnActionPerformed

    private void addBillBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBillBtnActionPerformed
        addBudgetLine(CategoryTypeEnum.BILL, Optional.empty());
    }//GEN-LAST:event_addBillBtnActionPerformed

    private void removeBillBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBillBtnActionPerformed
        removeBudgetExpense(billTable);
    }//GEN-LAST:event_removeBillBtnActionPerformed

    private void addExpenseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addExpenseBtnActionPerformed
        addBudgetLine(CategoryTypeEnum.EXPENSE, Optional.empty());
    }//GEN-LAST:event_addExpenseBtnActionPerformed

    private void budgetSuggestionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_budgetSuggestionBtnActionPerformed
        new BudgetSuggestion(null, true, currentYearMonth).setVisible(true);
    }//GEN-LAST:event_budgetSuggestionBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBillBtn;
    private javax.swing.JButton addExpenseBtn;
    private javax.swing.JButton addIncomeBtn;
    private javax.swing.JTable billTable;
    private javax.swing.JButton budgetSuggestionBtn;
    private javax.swing.JTable expenseTable;
    private javax.swing.JTable incomeTable;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JButton removeBillBtn;
    private javax.swing.JButton removeExpenseBtn;
    private javax.swing.JButton removeIncomeBtn;
    private javax.swing.JLabel totalBillLbl;
    private javax.swing.JLabel totalExpenseLbl;
    private javax.swing.JLabel totalIncomeLbl;
    // End of variables declaration//GEN-END:variables

    @Override
    public void init() {
    }

}

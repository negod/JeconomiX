/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.budget;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.constants.ComboBoxRenderer;
import se.backede.jeconomix.database.BudgetExpenseHandler;
import se.backede.jeconomix.dto.budget.BudgetDto;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.events.BudgetEvent;
import se.backede.jeconomix.models.combobox.CategoryComboBoxModel;
import se.backede.jeconomix.models.table.BudgetModel;
import se.backede.jeconomix.renderer.combobox.CategoryItemRenderer;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BudgetMonthDev extends javax.swing.JPanel {

    private final YearMonth CURRENT_YEAR_MONTH;
    private final CategoryTypeEnum CATEGORY;

    /**
     * Creates new form BudgetMonthDev
     */
    public BudgetMonthDev() {
        //initComponents();
        this.CURRENT_YEAR_MONTH = YearMonth.now();
        this.CATEGORY = CategoryTypeEnum.INCOME;
    }

    public BudgetMonthDev(YearMonth currentYearMonth, CategoryTypeEnum category) {
        initComponents();

        this.CURRENT_YEAR_MONTH = currentYearMonth;
        this.CATEGORY = category;

        setEvents();

        setInitData(incomeTable, CURRENT_YEAR_MONTH, totalSumLabel, CATEGORY);

    }

    public void setEvents() {

        Consumer<BudgetExpenseDto> createBudgetExpense = (dto) -> {

            if (dto.getBudget().getMonth().equals(CURRENT_YEAR_MONTH.getMonth()) && dto.getBudget().getYear() == CURRENT_YEAR_MONTH.getYear()) {
                BudgetModel icomeModel = (BudgetModel) incomeTable.getModel();
                icomeModel.addBudgetExpence(dto);
                totalSumLabel.setText(icomeModel.getTotalSum().toString());
            }
        };
        EventController.getInstance().addObserver(BudgetEvent.ADD_BUDGET_ROW, createBudgetExpense);

        Consumer<BudgetDto> clearAllTables = (dto) -> {

            if (dto.getMonth().equals(CURRENT_YEAR_MONTH.getMonth()) && dto.getYear() == CURRENT_YEAR_MONTH.getYear()) {

                BudgetModel icomeModel = (BudgetModel) incomeTable.getModel();

                List<BudgetExpenseDto> allBudgetExpenses = new ArrayList<>();
                allBudgetExpenses.addAll(icomeModel.getAll());

                allBudgetExpenses.forEach((budgetExpense) -> {
                    BudgetExpenseHandler.getInstance().deleteBudgetExpense(budgetExpense);
                });

                icomeModel.clearAll();
            }
        };

        EventController.getInstance().addObserver(BudgetEvent.CLEAR_BUDGET_LISTS, clearAllTables);

    }

    public void setUpDropDownColumn(TableColumn column, CategoryTypeEnum type) {
        JComboBox comboBox = new JComboBox();

        comboBox.setModel(new CategoryComboBoxModel(type));
        comboBox.setRenderer(new CategoryItemRenderer(ComboBoxRenderer.SINGLE));
        column.setCellEditor(new DefaultCellEditor(comboBox));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        column.setCellRenderer(renderer);
    }

    public void setTotalSumLabel() {
        BudgetModel icomeModel = (BudgetModel) incomeTable.getModel();
        BigDecimal incomeSum = icomeModel.getTotalSum();
        totalSumLabel.setText(incomeSum.toString());
    }

    public void setInitData(JTable table, YearMonth budgetMonth, JLabel lbl, CategoryTypeEnum category) {
        table.setModel(new BudgetModel(budgetMonth, category));
        setUpDropDownColumn(table.getColumnModel().getColumn(0), category);
        BudgetModel model = (BudgetModel) table.getModel();
        lbl.setText(model.getTotalSum().toString());
    }

    public void setMonth(YearMonth yearMonth, CategoryTypeEnum category) {
        monthLabel.setText(yearMonth.getMonth().name());
        setInitData(incomeTable, yearMonth, totalSumLabel, category);
        setTotalSumLabel();
    }

    public void removeBudgetExpense(JTable table) {
        BudgetModel model = (BudgetModel) table.getModel();
        model.removeBudgetExpenseAt(table.getSelectedRow());
        setTotalSumLabel();
    }

    private void addBudgetLine(CategoryTypeEnum category) {
        AddBudgetLine budgetLine = new AddBudgetLine(CURRENT_YEAR_MONTH);
        budgetLine.init(category);
        budgetLine.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        addIncomeBtn = new javax.swing.JButton();
        monthLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        incomeTable = new javax.swing.JTable();
        removeIncomeBtn = new javax.swing.JButton();
        totalSumLabel = new javax.swing.JLabel();

        addIncomeBtn.setBackground(new java.awt.Color(255, 255, 255));
        addIncomeBtn.setText("+");
        addIncomeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addIncomeBtnActionPerformed(evt);
            }
        });

        monthLabel.setBackground(new java.awt.Color(255, 255, 255));
        monthLabel.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        monthLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        monthLabel.setText("JANUARY");

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

        removeIncomeBtn.setBackground(new java.awt.Color(255, 255, 255));
        removeIncomeBtn.setText("-");
        removeIncomeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeIncomeBtnActionPerformed(evt);
            }
        });

        totalSumLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalSumLabel.setText("0.00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(totalSumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addIncomeBtn)
                            .addComponent(removeIncomeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalSumLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(addIncomeBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeIncomeBtn)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void removeIncomeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeIncomeBtnActionPerformed
        removeBudgetExpense(incomeTable);
    }//GEN-LAST:event_removeIncomeBtnActionPerformed

    private void addIncomeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addIncomeBtnActionPerformed
        addBudgetLine(CategoryTypeEnum.INCOME);
    }//GEN-LAST:event_addIncomeBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addIncomeBtn;
    private javax.swing.JTable incomeTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JButton removeIncomeBtn;
    private javax.swing.JLabel totalSumLabel;
    // End of variables declaration//GEN-END:variables
}

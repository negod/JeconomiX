/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.budget;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.events.BudgetEvent;
import se.backede.jeconomix.event.events.dto.BudgetEventDto;
import se.backede.jeconomix.event.events.dto.BudgetExpenseEventDto;
import se.backede.jeconomix.forms.basic.NegodPanel;
import se.backede.jeconomix.models.table.BudgetModel;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BudgetTable extends NegodPanel {

    BudgetEventDto CURRENT_BUDGET;

    /**
     * Creates new form BudgetTable
     */
    public BudgetTable() {
        initComponents();
    }

    public void init(BudgetEventDto currentBudget, List<BudgetExpenseDto> budgetList) {
        this.CURRENT_BUDGET = currentBudget;
        budgetTable.setModel(new BudgetModel(CURRENT_BUDGET, budgetList));
        setEvents();
    }

    public BigDecimal getTotalSum() {
        BudgetModel icomeModel = (BudgetModel) budgetTable.getModel();
        return icomeModel.getTotalSum();
    }

    public void setEvents() {

        Consumer<BudgetExpenseEventDto> createBudgetExpense = (dto) -> {
            if (dto.getBudgetEvent().equals(CURRENT_BUDGET)) {
                BudgetModel icomeModel = (BudgetModel) budgetTable.getModel();
                icomeModel.addBudgetExpence(dto.getBudgetExpense());
            }
        };
        EventController.getInstance().addObserver(BudgetEvent.ADD_BUDGET_ROW, createBudgetExpense);
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
        budgetTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        removeBudgetRow = new javax.swing.JButton();
        addBudgetRow = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(400, 350));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(500, 400));

        budgetTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        budgetTable.setFocusCycleRoot(true);
        jScrollPane1.setViewportView(budgetTable);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        removeBudgetRow.setText("-");
        removeBudgetRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBudgetRowActionPerformed(evt);
            }
        });

        addBudgetRow.setText("+");
        addBudgetRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBudgetRowActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removeBudgetRow, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addBudgetRow))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(addBudgetRow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeBudgetRow)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addBudgetRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBudgetRowActionPerformed
        AddBudgetLine budgetLine = new AddBudgetLine(CURRENT_BUDGET.getYearMonth());
        budgetLine.init(CURRENT_BUDGET.getCategory());
        budgetLine.setVisible(true);
    }//GEN-LAST:event_addBudgetRowActionPerformed

    private void removeBudgetRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBudgetRowActionPerformed
        BudgetModel model = (BudgetModel) budgetTable.getModel();
        BudgetExpenseDto budgetExpenceAt = model.getBudgetExpenceAt(budgetTable.getSelectedRow());

        BudgetExpenseEventDto budgetEvent = BudgetExpenseEventDto.builder()
                .budgetEvent(CURRENT_BUDGET)
                .budgetExpense(budgetExpenceAt)
                .build();

        EventController.getInstance().notifyObservers(BudgetEvent.DELETE_BUDGET_ROW, () -> budgetEvent);

        model.removeBudgetExpenseAt(budgetTable.getSelectedRow());
    }//GEN-LAST:event_removeBudgetRowActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBudgetRow;
    private javax.swing.JTable budgetTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton removeBudgetRow;
    // End of variables declaration//GEN-END:variables

    @Override
    public void init() {
    }
}

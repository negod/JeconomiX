/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.budget;

import java.time.YearMonth;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.constants.ComboBoxRenderer;
import se.backede.jeconomix.forms.basic.NegodPanel;
import se.backede.jeconomix.models.combobox.CategoryComboBoxModel;
import se.backede.jeconomix.renderer.combobox.CategoryItemRenderer;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BudgetMonth extends NegodPanel {

    private static final long serialVersionUID = 1L;

    private YearMonth CURRENT_YEAR_MONTH;

    /**
     * Creates new form BudgetMonth
     */
    public BudgetMonth() {
        initComponents();
    }

    public void setMonth(YearMonth yearMonth, CategoryTypeEnum... categories) {
        this.CURRENT_YEAR_MONTH = yearMonth;
        monthLabel.setText(yearMonth.getMonth().name());
        budgetList.init(yearMonth, categories);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        monthLabel = new javax.swing.JLabel();
        budgetSuggestionBtn = new javax.swing.JButton();
        totalSumLabel = new javax.swing.JLabel();
        outcomeButton = new javax.swing.JButton();
        budgetList = new se.backede.jeconomix.forms.budget.BudgetList();

        setBackground(new java.awt.Color(255, 255, 255));

        monthLabel.setBackground(new java.awt.Color(255, 255, 255));
        monthLabel.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        monthLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        monthLabel.setText("JANUARY");

        budgetSuggestionBtn.setBackground(new java.awt.Color(255, 255, 255));
        budgetSuggestionBtn.setText("Create budget");
        budgetSuggestionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                budgetSuggestionBtnActionPerformed(evt);
            }
        });

        totalSumLabel.setText("jLabel1");

        outcomeButton.setText("$");
        outcomeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outcomeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(budgetSuggestionBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalSumLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(outcomeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(budgetList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(budgetSuggestionBtn)
                    .addComponent(totalSumLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(outcomeButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(budgetList, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void budgetSuggestionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_budgetSuggestionBtnActionPerformed
        new BudgetSuggestion(null, true, CURRENT_YEAR_MONTH).setVisible(true);
    }//GEN-LAST:event_budgetSuggestionBtnActionPerformed

    private void outcomeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outcomeButtonActionPerformed
        new BudgetOutcome(CURRENT_YEAR_MONTH).setVisible(true);
    }//GEN-LAST:event_outcomeButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private se.backede.jeconomix.forms.budget.BudgetList budgetList;
    private javax.swing.JButton budgetSuggestionBtn;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JButton outcomeButton;
    private javax.swing.JLabel totalSumLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void init() {

    }

}

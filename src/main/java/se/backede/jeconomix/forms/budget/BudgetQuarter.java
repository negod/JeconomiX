/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.budget;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.Map;
import java.util.Optional;
import se.backede.jeconomix.constants.BudgetQuarterEnum;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.BudgetHandler;
import se.backede.jeconomix.dto.budget.BudgetCalculationDto;
import se.backede.jeconomix.forms.basic.NegodPanel;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BudgetQuarter extends NegodPanel {

    private static final long serialVersionUID = 1L;

    BudgetQuarterEnum CURRENT_QUARTER;
    Year CURRENT_YEAR;

    static CategoryTypeEnum[] CATEGORIES = CategoryTypeEnum.values();

    /**
     * Creates new form BudgetQuarter
     */
    public BudgetQuarter() {
        initComponents();
    }

    public void setData(BudgetQuarterEnum quarter, Year year) {

        CURRENT_QUARTER = quarter;
        CURRENT_YEAR = year;

        BudgetHandler.getInstance().getCalculatedBudgetByQuarter(quarter, year).ifPresent(map -> {
            budgetMonth1.setMonth(map.get(quarter.firstMonth()));
            budgetMonth2.setMonth(map.get(quarter.secondMonth()));
            budgetMonth3.setMonth(map.get(quarter.thirdMonth()));
        });

        yearLabel.setText(year.toString());
        quarterLabel.setText(quarter.name());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        budgetMonth1 = new se.backede.jeconomix.forms.budget.BudgetMonth();
        budgetMonth2 = new se.backede.jeconomix.forms.budget.BudgetMonth();
        budgetMonth3 = new se.backede.jeconomix.forms.budget.BudgetMonth();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        quarterFwrdBtn = new javax.swing.JButton();
        quarterBackBtn = new javax.swing.JButton();
        quarterLabel = new javax.swing.JLabel();
        yearLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 97, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        quarterFwrdBtn.setBackground(new java.awt.Color(255, 255, 255));
        quarterFwrdBtn.setText(">");
        quarterFwrdBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quarterFwrdBtnActionPerformed(evt);
            }
        });

        quarterBackBtn.setBackground(new java.awt.Color(255, 255, 255));
        quarterBackBtn.setText("<");
        quarterBackBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quarterBackBtnActionPerformed(evt);
            }
        });

        quarterLabel.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        quarterLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        quarterLabel.setText("Quarter");

        yearLabel.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        yearLabel.setText("Year");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(quarterBackBtn)
                .addGap(64, 64, 64)
                .addComponent(quarterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(yearLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(quarterFwrdBtn)
                .addGap(470, 470, 470))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(quarterBackBtn)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(quarterLabel)
                .addComponent(yearLabel)
                .addComponent(quarterFwrdBtn))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(budgetMonth1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(budgetMonth2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(budgetMonth3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(750, 750, 750))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(budgetMonth2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(budgetMonth1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(budgetMonth3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void quarterBackBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quarterBackBtnActionPerformed
        switch (CURRENT_QUARTER) {
            case QUARTER1:
                CURRENT_QUARTER = BudgetQuarterEnum.QUARTER4;
                CURRENT_YEAR = CURRENT_YEAR.minusYears(1);
                break;
            case QUARTER2:
                CURRENT_QUARTER = BudgetQuarterEnum.QUARTER1;
                break;
            case QUARTER3:
                CURRENT_QUARTER = BudgetQuarterEnum.QUARTER2;
                break;
            case QUARTER4:
                CURRENT_QUARTER = BudgetQuarterEnum.QUARTER3;
                break;
            default:
                throw new AssertionError();
        }
        setData(CURRENT_QUARTER, CURRENT_YEAR);
    }//GEN-LAST:event_quarterBackBtnActionPerformed

    private void quarterFwrdBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quarterFwrdBtnActionPerformed
        switch (CURRENT_QUARTER) {
            case QUARTER1:
                CURRENT_QUARTER = BudgetQuarterEnum.QUARTER2;
                break;
            case QUARTER2:
                CURRENT_QUARTER = BudgetQuarterEnum.QUARTER3;
                break;
            case QUARTER3:
                CURRENT_QUARTER = BudgetQuarterEnum.QUARTER4;
                break;
            case QUARTER4:
                CURRENT_QUARTER = BudgetQuarterEnum.QUARTER1;
                CURRENT_YEAR = CURRENT_YEAR.plusYears(1);
                break;
            default:
                throw new AssertionError();
        }
        setData(CURRENT_QUARTER, CURRENT_YEAR);
    }//GEN-LAST:event_quarterFwrdBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private se.backede.jeconomix.forms.budget.BudgetMonth budgetMonth1;
    private se.backede.jeconomix.forms.budget.BudgetMonth budgetMonth2;
    private se.backede.jeconomix.forms.budget.BudgetMonth budgetMonth3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton quarterBackBtn;
    private javax.swing.JButton quarterFwrdBtn;
    private javax.swing.JLabel quarterLabel;
    private javax.swing.JLabel yearLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

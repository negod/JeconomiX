/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.budget;

import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.swing.ButtonModel;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.BudgetHandler;
import se.backede.jeconomix.database.TransactionHandler;
import se.backede.jeconomix.dto.budget.BudgetCalculationDto;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.events.BudgetEvent;
import se.backede.jeconomix.forms.basic.NegodDialog;
import se.backede.jeconomix.utils.BudgetUtils;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class BudgetSuggestion extends NegodDialog {

    private static final long serialVersionUID = 1L;

    private final YearMonth CURRENT_BUDGET_MONTH;

    private final YearMonth ONE_YEAR_BACK;
    private final YearMonth ONE_MONTH_BACK;
    private final YearMonth ONE_QUARTER_BACK;

    /**
     * Creates new form BudgetSuggestion
     *
     * @param parent
     * @param modal
     * @param yearMonth
     */
    public BudgetSuggestion(java.awt.Frame parent, boolean modal, YearMonth yearMonth) {
        super(parent, modal);

        initComponents();

        this.CURRENT_BUDGET_MONTH = yearMonth;
        this.ONE_YEAR_BACK = CURRENT_BUDGET_MONTH.minusYears(1);
        this.ONE_MONTH_BACK = CURRENT_BUDGET_MONTH.minusMonths(1);
        this.ONE_QUARTER_BACK = CURRENT_BUDGET_MONTH.minusMonths(3);

        yearLabel.setText(Integer.toString(yearMonth.getYear()));
        monthLabel.setText(yearMonth.getMonth().name());

        baseBudgetOnBtnGroup.setSelected(baseOnBudgetRadioBtn.getModel(), true);
        periodSelectionBtnGroup.setSelected(preMonthRadioBtn.getModel(), true);

        budgetMonth.showHeader(Boolean.FALSE);

        setBudgetFromPreviousBudget(yearMonth);
    }

    private void setBudgetFromPreviousBudget(YearMonth yearMonth) {
        BudgetHandler.getInstance()
                .getCalculatedBudgetByMonths(Year.of(yearMonth.getYear()), yearMonth.getMonth())
                .ifPresent(budgetMap -> {
                    budgetMonth.setMonth(budgetMap.get(yearMonth.getMonth()));
                });
    }

    private void setBudgetFromActualOutcome(YearMonth yearMonth) {
        TransactionHandler.getInstance().getTransactionsByBudgetMonth(yearMonth).ifPresent(transactions -> {
            BudgetCalculationDto budgetCaclulation = BudgetUtils.mapToBudgetCalculationDto(transactions, yearMonth);
            budgetMonth.setMonth(budgetCaclulation);
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

        periodSelectionBtnGroup = new javax.swing.ButtonGroup();
        baseBudgetOnBtnGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        yearLabel = new javax.swing.JLabel();
        monthLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        baseOnBudgetRadioBtn = new javax.swing.JRadioButton();
        baseOnOutcomeRadionBtn = new javax.swing.JRadioButton();
        jButton2 = new javax.swing.JButton();
        suggestBudgetBtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        preYearRadioBtn = new javax.swing.JRadioButton();
        preMonthRadioBtn = new javax.swing.JRadioButton();
        preQuarterRadioBtn = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        cancelBtn = new javax.swing.JButton();
        addBudgetBtn = new javax.swing.JButton();
        budgetMonth = new se.backede.jeconomix.forms.budget.BudgetMonth();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        yearLabel.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        yearLabel.setText("Year");

        monthLabel.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        monthLabel.setText("Month");

        baseBudgetOnBtnGroup.add(baseOnBudgetRadioBtn);
        baseOnBudgetRadioBtn.setText("Base on budget");

        baseBudgetOnBtnGroup.add(baseOnOutcomeRadionBtn);
        baseOnOutcomeRadionBtn.setText("Base on outcome");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(baseOnBudgetRadioBtn)
                    .addComponent(baseOnOutcomeRadionBtn))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(baseOnBudgetRadioBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(baseOnOutcomeRadionBtn)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jButton2.setText("Analyze and suggest");
        jButton2.setEnabled(false);

        suggestBudgetBtn.setText("Suggest");
        suggestBudgetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestBudgetBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suggestBudgetBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(suggestBudgetBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 626, Short.MAX_VALUE)
        );

        periodSelectionBtnGroup.add(preYearRadioBtn);
        preYearRadioBtn.setText("Previous year");

        periodSelectionBtnGroup.add(preMonthRadioBtn);
        preMonthRadioBtn.setText("Previous month");

        periodSelectionBtnGroup.add(preQuarterRadioBtn);
        preQuarterRadioBtn.setText("Previous quarter");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(preQuarterRadioBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(preMonthRadioBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(preYearRadioBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(preYearRadioBtn)
                .addGap(3, 3, 3)
                .addComponent(preMonthRadioBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 3, Short.MAX_VALUE)
                .addComponent(preQuarterRadioBtn)
                .addContainerGap())
        );

        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        addBudgetBtn.setText("Add budget");
        addBudgetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBudgetBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addBudgetBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBudgetBtn)
                    .addComponent(cancelBtn))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(yearLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 498, Short.MAX_VALUE))
                    .addComponent(jSeparator1))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(budgetMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthLabel)
                    .addComponent(yearLabel))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(budgetMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void suggestBudgetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestBudgetBtnActionPerformed

        ButtonModel periodSelection = periodSelectionBtnGroup.getSelection();
        ButtonModel basedOnSelection = baseBudgetOnBtnGroup.getSelection();

        if (basedOnSelection.equals(baseOnOutcomeRadionBtn.getModel())) {
            if (periodSelection.equals(preYearRadioBtn.getModel())) {
                setBudgetFromActualOutcome(ONE_YEAR_BACK);
            } else if (periodSelection.equals(preMonthRadioBtn.getModel())) {
                setBudgetFromActualOutcome(ONE_MONTH_BACK);
            } else if (periodSelection.equals(preQuarterRadioBtn.getModel())) {
                setBudgetFromActualOutcome(ONE_QUARTER_BACK);
            }
        } else if (basedOnSelection.equals(baseOnBudgetRadioBtn.getModel())) {
            if (periodSelection.equals(preYearRadioBtn.getModel())) {
                setBudgetFromPreviousBudget(ONE_YEAR_BACK);
            } else if (periodSelection.equals(preMonthRadioBtn.getModel())) {
                setBudgetFromPreviousBudget(ONE_MONTH_BACK);
            } else if (periodSelection.equals(preQuarterRadioBtn.getModel())) {
                setBudgetFromPreviousBudget(ONE_QUARTER_BACK);
            }
        }
    }//GEN-LAST:event_suggestBudgetBtnActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void addBudgetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBudgetBtnActionPerformed

        BudgetHandler.getInstance().getBudgetByYear(CURRENT_BUDGET_MONTH).ifPresent(budget -> {

            EventController.getInstance().notifyObservers(BudgetEvent.CLEAR_BUDGET_LISTS, () -> budget);

//            BudgetModel incomes = (BudgetModel) incomeTable.getModel();
//            BudgetModel expenses = (BudgetModel) expenseTable.getModel();
//            BudgetModel bills = (BudgetModel) billTable.getModel();
//
//            List<BudgetExpenseDto> allBudgetExpenses = new ArrayList<>();
//            allBudgetExpenses.addAll(incomes.getAll());
//            allBudgetExpenses.addAll(expenses.getAll());
//            allBudgetExpenses.addAll(bills.getAll());
//
//            allBudgetExpenses.stream().map((budgetDto) -> {
//                budgetDto.setBudget(budget);
//                budgetDto.setId(null);
//                return budgetDto;
//            }).forEachOrdered((budgetDto) -> {
//                BudgetExpenseHandler.getInstance().upsertBudgetExpense(budgetDto).ifPresent(budgetExpense -> {
//                    BudgetExpenseEventDto build = BudgetExpenseEventDto.builder()
//                            .budgetExpense(budgetExpense)
//                            .budgetEvent(
//                                    BudgetEventDto.builder()
//                                            .category(budgetExpense.getCategory().getCategoryType().getType())
//                                            .yearMonth(YearMonth.of(budgetDto.getBudget().getYear(), budgetDto.getBudget().getMonth()))
//                                            .build()
//                            )
//                            .build();
//                    EventController.getInstance().notifyObservers(BudgetEvent.ADD_BUDGET_ROW, () -> build);
//                });
//            });
        });

        this.dispose();
    }//GEN-LAST:event_addBudgetBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBudgetBtn;
    private javax.swing.ButtonGroup baseBudgetOnBtnGroup;
    private javax.swing.JRadioButton baseOnBudgetRadioBtn;
    private javax.swing.JRadioButton baseOnOutcomeRadionBtn;
    private se.backede.jeconomix.forms.budget.BudgetMonth budgetMonth;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel monthLabel;
    private javax.swing.ButtonGroup periodSelectionBtnGroup;
    private javax.swing.JRadioButton preMonthRadioBtn;
    private javax.swing.JRadioButton preQuarterRadioBtn;
    private javax.swing.JRadioButton preYearRadioBtn;
    private javax.swing.JButton suggestBudgetBtn;
    private javax.swing.JLabel yearLabel;
    // End of variables declaration//GEN-END:variables

}

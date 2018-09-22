/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.budget;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.swing.ButtonModel;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.BudgetHandler;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.events.BudgetEvent;
import se.backede.jeconomix.forms.basic.NegodDialog;
import se.backede.jeconomix.models.table.BudgetModel;
import se.backede.jeconomix.utils.BudgetUtils;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BudgetSuggestion extends NegodDialog {

    YearMonth CURRENT_BUDGET_MONTH;

    /**
     * Creates new form BudgetSuggestion
     *
     * @param parent
     * @param modal
     * @param yearMonth
     */
    public BudgetSuggestion(java.awt.Frame parent, boolean modal, YearMonth yearMonth) {
        super(parent, modal);
        this.CURRENT_BUDGET_MONTH = yearMonth;
        initComponents();
        yearLabel.setText(Integer.toString(yearMonth.getYear()));
        monthLabel.setText(yearMonth.getMonth().name());

        baseBudgetOnBtnGroup.setSelected(baseOnBudgetRadioBtn.getModel(), true);
        periodSelectionBtnGroup.setSelected(preMonthRadioBtn.getModel(), true);

    }

    private void setBudgetTables(Map<CategoryTypeEnum, List<BudgetExpenseDto>> map) {
        map.forEach((key, dtoList) -> {
            switch (key) {
                case INCOME:
                    incomeTable.setModel(new BudgetModel(dtoList, true, CategoryTypeEnum.INCOME));
                    break;
                case EXPENSE:
                    expenseTable.setModel(new BudgetModel(dtoList, true, CategoryTypeEnum.EXPENSE));
                    break;
                case BILL:
                    billTable.setModel(new BudgetModel(dtoList, true, CategoryTypeEnum.BILL));
                    break;
                case TRANSFER:
                    break;
                default:
                    throw new AssertionError(key.name());
            }
        });
    }

    private void clearTables() {
        incomeTable.setModel(new BudgetModel(new ArrayList<>(), true, CategoryTypeEnum.INCOME));
        expenseTable.setModel(new BudgetModel(new ArrayList<>(), true, CategoryTypeEnum.EXPENSE));
        billTable.setModel(new BudgetModel(new ArrayList<>(), true, CategoryTypeEnum.BILL));
    }

    private void setBudgetFromActualOutcome(YearMonth yearMonth) {
        BudgetUtils.getInstance().createBudgetFromTransaction(yearMonth).ifPresent(map -> {

            Supplier<List<BudgetExpenseDto>> addedBudgets = () -> {
                List<BudgetExpenseDto> budgetExpenses = new ArrayList<>();
                map.forEach((key, value) -> {
                    budgetExpenses.addAll(value);
                });
                return budgetExpenses;
            };

            EventController.getInstance().notifyObservers(BudgetEvent.SET_BUDGET_TOTAL, addedBudgets);
            setBudgetTables(map);
        });
    }

    private void setBudgetFromBudget(YearMonth yearMonth) {
        BudgetHandler.getInstance().getBudget(yearMonth).ifPresent(budget -> {
            Map<CategoryTypeEnum, List<BudgetExpenseDto>> mapPersons = budget.getBudgetExpenseSet()
                    .stream()
                    .collect(Collectors.groupingBy(dto -> dto.getCategory().getCategoryType().getType()));
            setBudgetTables(mapPersons);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        incomeTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        billTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        expenseTable = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        budgetTotal1 = new se.backede.jeconomix.forms.budget.BudgetTotal();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        baseOnBudgetRadioBtn = new javax.swing.JRadioButton();
        baseOnOutcomeRadionBtn = new javax.swing.JRadioButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        preYearRadioBtn = new javax.swing.JRadioButton();
        preMonthRadioBtn = new javax.swing.JRadioButton();
        preQuarterRadioBtn = new javax.swing.JRadioButton();
        suggestBudgetBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        yearLabel.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        yearLabel.setText("Year");

        monthLabel.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        monthLabel.setText("Month");

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

        jButton3.setText("Add budget");

        jButton4.setText("Cancel");

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
                    .addComponent(preQuarterRadioBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suggestBudgetBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(suggestBudgetBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                                .addComponent(budgetTotal1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 2, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton3))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(yearLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jSeparator1))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthLabel)
                    .addComponent(yearLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(budgetTotal1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jButton4)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void suggestBudgetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestBudgetBtnActionPerformed

        clearTables();

        ButtonModel periodSelection = periodSelectionBtnGroup.getSelection();
        ButtonModel basedOnSelection = baseBudgetOnBtnGroup.getSelection();

        if (basedOnSelection.equals(baseOnOutcomeRadionBtn.getModel())) {
            if (periodSelection.equals(preYearRadioBtn.getModel())) {
                setBudgetFromActualOutcome(CURRENT_BUDGET_MONTH.minusYears(1));
            } else if (periodSelection.equals(preMonthRadioBtn.getModel())) {
                setBudgetFromActualOutcome(CURRENT_BUDGET_MONTH.minusMonths(1));
            } else if (periodSelection.equals(preQuarterRadioBtn.getModel())) {
                setBudgetFromActualOutcome(CURRENT_BUDGET_MONTH.minusMonths(3));
            }
        } else if (basedOnSelection.equals(baseOnBudgetRadioBtn.getModel())) {
            if (periodSelection.equals(preYearRadioBtn.getModel())) {
                setBudgetFromBudget(CURRENT_BUDGET_MONTH.minusYears(1));
            } else if (periodSelection.equals(preMonthRadioBtn.getModel())) {
                setBudgetFromBudget(CURRENT_BUDGET_MONTH.minusMonths(1));
            } else if (periodSelection.equals(preQuarterRadioBtn.getModel())) {
                setBudgetFromBudget(CURRENT_BUDGET_MONTH.minusMonths(3));
            }
        }

    }//GEN-LAST:event_suggestBudgetBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup baseBudgetOnBtnGroup;
    private javax.swing.JRadioButton baseOnBudgetRadioBtn;
    private javax.swing.JRadioButton baseOnOutcomeRadionBtn;
    private javax.swing.JTable billTable;
    private se.backede.jeconomix.forms.budget.BudgetTotal budgetTotal1;
    private javax.swing.JTable expenseTable;
    private javax.swing.JTable incomeTable;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.budget;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.BudgetHandler;
import se.backede.jeconomix.database.TransactionHandler;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;
import se.backede.jeconomix.dto.budget.BudgetOutcomeDto;
import se.backede.jeconomix.models.table.BudgetOutcomeModel;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BudgetOutcome extends javax.swing.JFrame {

    private final YearMonth BUDGET_MONTH;

    /**
     * Creates new form BudgetOutcome
     */
    public BudgetOutcome(YearMonth budgetMonth) {
        this.BUDGET_MONTH = budgetMonth;
        initComponents();

        Map<CategoryTypeEnum, List<BudgetOutcomeDto>> budgetOutcome = getBudgetOutcome();

        billTable.setModel(new BudgetOutcomeModel(budgetOutcome.get(CategoryTypeEnum.BILL), CategoryTypeEnum.BILL));
        expenseTable.setModel(new BudgetOutcomeModel(budgetOutcome.get(CategoryTypeEnum.EXPENSE), CategoryTypeEnum.EXPENSE));
        incomeTable.setModel(new BudgetOutcomeModel(budgetOutcome.get(CategoryTypeEnum.INCOME), CategoryTypeEnum.INCOME));

    }

    private Map<CategoryTypeEnum, List<BudgetOutcomeDto>> getBudgetOutcome() {

        Map<CategoryTypeEnum, List<BudgetOutcomeDto>> budgetOutcomeMap = getBudgetOutcomeMap();
        Map<CategoryTypeEnum, List<BudgetOutcomeDto>> budgetMap = getBudgetMap();

        return combineMaps(budgetOutcomeMap, budgetMap);
    }

    Map<CategoryTypeEnum, List<BudgetOutcomeDto>> combineMaps(
            Map<CategoryTypeEnum, List<BudgetOutcomeDto>> budgetOutcomeMap,
            Map<CategoryTypeEnum, List<BudgetOutcomeDto>> budgetMap) {

        Map<CategoryTypeEnum, List<BudgetOutcomeDto>> finalBudgetOutcomeMap = new HashMap<>();
        for (CategoryTypeEnum categoryTypeEnum : budgetOutcomeMap.keySet()) {
            if (budgetMap.containsKey(categoryTypeEnum)) {
                List<BudgetOutcomeDto> budgetList = budgetMap.get(categoryTypeEnum);
                List<BudgetOutcomeDto> budgetOutcomeList = budgetOutcomeMap.get(categoryTypeEnum);
                List<BudgetOutcomeDto> combineLists = combineLists(budgetList, budgetOutcomeList);
                finalBudgetOutcomeMap.put(categoryTypeEnum, combineLists);
            } else {
                finalBudgetOutcomeMap.put(categoryTypeEnum, budgetOutcomeMap.get(categoryTypeEnum));
            }
        }

        return finalBudgetOutcomeMap;
    }

    private List<BudgetOutcomeDto> combineLists(List<BudgetOutcomeDto> budgetList, List<BudgetOutcomeDto> budgetOutcomeList) {
        List<BudgetOutcomeDto> combinedList = new ArrayList<>();
        for (BudgetOutcomeDto budgetOutcome : budgetOutcomeList) {
            for (BudgetOutcomeDto budget : budgetList) {
                if (budget.equals(budgetOutcome)) {
                    budgetOutcome.setBudget(budget.getBudget());
                }
            }
            combinedList.add(budgetOutcome);
        }
        return combinedList;
    }

    private Map<CategoryTypeEnum, List<BudgetOutcomeDto>> getBudgetMap() {
        Map<CategoryTypeEnum, List<BudgetOutcomeDto>> budgetMap = new HashMap<>();
        BudgetHandler.getInstance().getBudget(BUDGET_MONTH).ifPresent(list -> {
            list.getBudgetExpenseSet().forEach(budgetExpense -> {
                BudgetOutcomeDto outcome = finalizeBudgetOutcomeDto(budgetExpense);
                if (budgetMap.containsKey(outcome.getCategoryType())) {
                    budgetMap.get(outcome.getCategoryType()).add(outcome);
                } else {
                    budgetMap.put(outcome.getCategoryType(), new ArrayList<>(List.of(outcome)));
                }
            });
        });
        return budgetMap;
    }

    private Map<CategoryTypeEnum, List<BudgetOutcomeDto>> getBudgetOutcomeMap() {
        Map<CategoryTypeEnum, List<BudgetOutcomeDto>> budgetOutcomeMap = new HashMap<>();
        TransactionHandler.getInstance().getTransactionsByBudgetMonth(BUDGET_MONTH).ifPresent(list -> {
            list.forEach(tranasction -> {
                BudgetOutcomeDto outcome = mapTransactionToBudgetOutcomeDto(tranasction);
                if (budgetOutcomeMap.containsKey(outcome.getCategoryType())) {
                    budgetOutcomeMap.get(outcome.getCategoryType()).add(outcome);
                } else {
                    budgetOutcomeMap.put(outcome.getCategoryType(), new ArrayList<>(List.of(outcome)));
                }
            });
        });
        return budgetOutcomeMap;
    }

    private BudgetOutcomeDto finalizeBudgetOutcomeDto(BudgetExpenseDto expense) {
        BudgetOutcomeDto outcome = new BudgetOutcomeDto();
        outcome.setCategoryType(expense.getCategory().getCategoryType().getType());
        outcome.setCategory(expense.getCategory().getName());
        outcome.setBudget(expense.getEstimatedsum());
        outcome.setOutcome(BigDecimal.valueOf(0.00));
        return outcome;
    }

    private BudgetOutcomeDto mapTransactionToBudgetOutcomeDto(TransactionDto transaction) {
        BudgetOutcomeDto outcome = new BudgetOutcomeDto();
        outcome.setCategoryType(transaction.getCompany().getCategory().getCategoryType().getType());
        outcome.setCategory(transaction.getCompany().getCategory().getName());
        outcome.setOutcome(transaction.getSum());
        outcome.setBudget(BigDecimal.valueOf(0.00));
        return outcome;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        incomeTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        expenseTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        billTable = new javax.swing.JTable();
        incomeChartPanel = new javax.swing.JPanel();
        expenseChartPanel = new javax.swing.JPanel();
        billChartLabel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        incomeTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(incomeTable);

        expenseTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(expenseTable);

        billTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(billTable);

        javax.swing.GroupLayout incomeChartPanelLayout = new javax.swing.GroupLayout(incomeChartPanel);
        incomeChartPanel.setLayout(incomeChartPanelLayout);
        incomeChartPanelLayout.setHorizontalGroup(
            incomeChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        incomeChartPanelLayout.setVerticalGroup(
            incomeChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 229, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout expenseChartPanelLayout = new javax.swing.GroupLayout(expenseChartPanel);
        expenseChartPanel.setLayout(expenseChartPanelLayout);
        expenseChartPanelLayout.setHorizontalGroup(
            expenseChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 333, Short.MAX_VALUE)
        );
        expenseChartPanelLayout.setVerticalGroup(
            expenseChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout billChartLabelLayout = new javax.swing.GroupLayout(billChartLabel);
        billChartLabel.setLayout(billChartLabelLayout);
        billChartLabelLayout.setHorizontalGroup(
            billChartLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        billChartLabelLayout.setVerticalGroup(
            billChartLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                    .addComponent(incomeChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expenseChartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(billChartLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(incomeChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(expenseChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(billChartLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel billChartLabel;
    private javax.swing.JTable billTable;
    private javax.swing.JPanel expenseChartPanel;
    private javax.swing.JTable expenseTable;
    private javax.swing.JPanel incomeChartPanel;
    private javax.swing.JTable incomeTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}

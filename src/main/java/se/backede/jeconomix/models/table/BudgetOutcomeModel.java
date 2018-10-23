/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.models.table;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.table.AbstractTableModel;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.BudgetExpenseHandler;
import se.backede.jeconomix.database.TransactionHandler;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;
import se.backede.jeconomix.dto.budget.BudgetOutcomeDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BudgetOutcomeModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private List<BudgetOutcomeDto> filteredCategories = new LinkedList<>();
    private final YearMonth BUDGET_MONTH;
    private final CategoryTypeEnum CATEGORY;

    public BudgetOutcomeModel(CategoryTypeEnum category, YearMonth budgetMonth) {
        this.CATEGORY = category;
        this.BUDGET_MONTH = budgetMonth;

        this.filteredCategories = getBudgetOutcome();
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Category";
            case 1:
                return "Budget";
            case 2:
                return "Outcome";
            case 3:
                return "Diff";

        }
        return "";
    }

    @Override
    public int getRowCount() {
        return filteredCategories.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex
    ) {
        Object value = "";
        BudgetOutcomeDto outcomeDto = filteredCategories.get(rowIndex);
        switch (columnIndex) {
            case 0:
                if (outcomeDto.getCategory() != null) {
                    value = outcomeDto.getCategory();
                }
                break;
            case 1:
                if (outcomeDto.getBudget() != null) {
                    value = outcomeDto.getBudget().abs();
                } else {
                    value = BigDecimal.valueOf(0.00);
                }
                break;
            case 2:
                if (outcomeDto.getOutcome() != null) {
                    value = outcomeDto.getOutcome().abs();
                } else {
                    value = BigDecimal.valueOf(0.00);
                }
                break;
            case 3:
                switch (CATEGORY) {
                    case INCOME:
                        value = outcomeDto.getOutcome().abs().subtract(outcomeDto.getBudget().abs());
                        break;
                    case EXPENSE:
                    case BILL:
                    case TRANSFER:
                        value = outcomeDto.getBudget().abs().subtract(outcomeDto.getOutcome().abs());
                        break;
                    default:
                        throw new AssertionError();
                }
                break;
        }
        return value;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex
    ) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return BigDecimal.class;
            case 2:
                return BigDecimal.class;
            case 3:
                return BigDecimal.class;
        }
        return null;
    }

    public BigDecimal getTotalBudgetSum() {
        BigDecimal totalSum = BigDecimal.ZERO;
        for (BudgetOutcomeDto budget : filteredCategories) {
            totalSum = totalSum.add(budget.getBudget());
        }
        return totalSum;
    }

    public BigDecimal getTotalOutcomeSum() {
        BigDecimal totalSum = BigDecimal.ZERO;
        for (BudgetOutcomeDto outcome : filteredCategories) {
            totalSum = totalSum.add(outcome.getOutcome());
        }
        return totalSum;
    }

    private List<BudgetOutcomeDto> orderByCategoryBudget(List<BudgetOutcomeDto> outcomeList) {
        Map<String, BudgetOutcomeDto> orderedMap = new HashMap<>();
        for (BudgetOutcomeDto budgetOutcomeDto : outcomeList) {
            if (orderedMap.containsKey(budgetOutcomeDto.getCategory())) {
                BigDecimal newBudgetValue = budgetOutcomeDto.getBudget().add(orderedMap.get(budgetOutcomeDto.getCategory()).getBudget());
                orderedMap.get(budgetOutcomeDto.getCategory()).setBudget(newBudgetValue);
            } else {
                orderedMap.put(budgetOutcomeDto.getCategory(), budgetOutcomeDto);
            }
        }
        return new ArrayList<>(orderedMap.values());
    }

    private List<BudgetOutcomeDto> orderByCategoryOutcome(List<BudgetOutcomeDto> outcomeList) {
        Map<String, BudgetOutcomeDto> orderedMap = new HashMap<>();

        for (BudgetOutcomeDto budgetOutcomeDto : outcomeList) {
            if (orderedMap.containsKey(budgetOutcomeDto.getCategory())) {
                BigDecimal newOutcomeValue = budgetOutcomeDto.getOutcome().add(orderedMap.get(budgetOutcomeDto.getCategory()).getOutcome());
                orderedMap.get(budgetOutcomeDto.getCategory()).setOutcome(newOutcomeValue);
            } else {
                orderedMap.put(budgetOutcomeDto.getCategory(), budgetOutcomeDto);
            }
        }
        return new ArrayList<>(orderedMap.values());
    }

    private List<BudgetOutcomeDto> getBudgetOutcome() {

        List<BudgetOutcomeDto> budgetOutcomeMap = getBudgetOutcomeMap();
        List<BudgetOutcomeDto> budgetMap = getBudgetMap();

        List<BudgetOutcomeDto> orderByCategoryOutcome = orderByCategoryOutcome(budgetOutcomeMap);
        List<BudgetOutcomeDto> orderByCategoryBudget = orderByCategoryBudget(budgetMap);

        List<BudgetOutcomeDto> combineLists = combineLists(orderByCategoryBudget, orderByCategoryOutcome);
        List<BudgetOutcomeDto> combineLists2 = combineLists2(orderByCategoryBudget, orderByCategoryOutcome);

        Set<BudgetOutcomeDto> test = new HashSet<>();
        for (BudgetOutcomeDto combineList : combineLists) {
            test.add(combineList);
        }

        for (BudgetOutcomeDto budgetOutcomeDto : combineLists2) {
            test.add(budgetOutcomeDto);
        }

        return new ArrayList<>(test);
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

    private List<BudgetOutcomeDto> combineLists2(List<BudgetOutcomeDto> budgetList, List<BudgetOutcomeDto> budgetOutcomeList) {
        List<BudgetOutcomeDto> combinedList = new ArrayList<>();
        for (BudgetOutcomeDto budgetOutcome : budgetList) {
            for (BudgetOutcomeDto budget : budgetOutcomeList) {
                if (budget.equals(budgetOutcome)) {
                    budgetOutcome.setOutcome(budget.getOutcome());
                }
            }
            combinedList.add(budgetOutcome);
        }
        return combinedList;
    }

    private List<BudgetOutcomeDto> getBudgetMap() {
        List<BudgetOutcomeDto> budgetMap = new ArrayList<>();
        BudgetExpenseHandler.getInstance().getBudgetExpenseByMonthAndCategory(BUDGET_MONTH, CATEGORY).ifPresent(list -> {
            list.forEach(budgetExpense -> {
                budgetMap.add(finalizeBudgetOutcomeDto(budgetExpense));
            });
        });

        return budgetMap;
    }

    private List<BudgetOutcomeDto> getBudgetOutcomeMap() {
        List<BudgetOutcomeDto> budgetOutcomeMap = new ArrayList<>();
        TransactionHandler.getInstance().getTransactionsByBudgetMonthAndCategory(BUDGET_MONTH, CATEGORY).ifPresent(list -> {
            list.forEach(tranasction -> {
                budgetOutcomeMap.add(mapTransactionToBudgetOutcomeDto(tranasction));
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

}

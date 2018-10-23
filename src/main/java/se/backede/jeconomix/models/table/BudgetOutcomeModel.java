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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.dto.budget.BudgetOutcomeDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BudgetOutcomeModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private List<BudgetOutcomeDto> filteredCategories = new LinkedList<>();
    private YearMonth BUDGET_MONTH;
    private CategoryTypeEnum category;

    public BudgetOutcomeModel() {
    }

    public BudgetOutcomeModel(List<BudgetOutcomeDto> filteredCategories, CategoryTypeEnum category) {
        this.category = category;
        this.filteredCategories = orderByCategory(filteredCategories);
    }

    private List<BudgetOutcomeDto> orderByCategory(List<BudgetOutcomeDto> outcomeList) {
        Map<String, BudgetOutcomeDto> orderedMap = new HashMap<>();

        for (BudgetOutcomeDto budgetOutcomeDto : outcomeList) {
            if (orderedMap.containsKey(budgetOutcomeDto.getCategory())) {
                //BigDecimal newBudgetValue = budgetOutcomeDto.getBudget().add(orderedMap.get(budgetOutcomeDto.getCategory()).getBudget());
                BigDecimal newOutcomeValue = budgetOutcomeDto.getOutcome().add(orderedMap.get(budgetOutcomeDto.getCategory()).getOutcome());

                orderedMap.get(budgetOutcomeDto.getCategory()).setOutcome(newOutcomeValue);
                //orderedMap.get(budgetOutcomeDto.getCategory()).setBudget(newBudgetValue);
            } else {
                orderedMap.put(budgetOutcomeDto.getCategory(), budgetOutcomeDto);
            }
        }

        return new ArrayList<>(orderedMap.values());
    }

    @Override
    public String getColumnName(int columnIndex
    ) {
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
                switch (category) {
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

}

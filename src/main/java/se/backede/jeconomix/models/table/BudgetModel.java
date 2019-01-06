/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.models.table;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import org.apache.commons.lang3.text.WordUtils;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.BudgetExpenseHandler;
import se.backede.jeconomix.database.BudgetHandler;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.budget.BudgetDto;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;
import se.backede.jeconomix.event.events.dto.BudgetEventDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BudgetModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private BudgetDto BUDGET;
    private List<BudgetExpenseDto> filteredCategories = new LinkedList<>();
    private BudgetEventDto BUDGET_EVENT;

    public BudgetModel() {
    }

    public BudgetModel(BudgetEventDto currentBudget, List<BudgetExpenseDto> filteredCategories) {
        this.BUDGET_EVENT = currentBudget;
        this.filteredCategories = filteredCategories;
    }

    private Predicate<BudgetExpenseDto> isCategoryType(CategoryTypeEnum type) {
        return p -> p.getCategory().getCategoryType().getType().equals(type);
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return WordUtils.capitalizeFully(BUDGET_EVENT.getCategory().name());
            case 1:
                return "Estimated sum";
        }
        return "";
    }

    /*
         * Don't need to implement this method unless your table's
         * editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        switch (col) {
            case 0:
                return true;
            case 1:
                return true;
        }
        return true;
    }

    @Override
    public int getRowCount() {
        return filteredCategories.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "";
        BudgetExpenseDto expenseDto = filteredCategories.get(rowIndex);
        switch (columnIndex) {
            case 0:
                if (expenseDto.getCategory() != null) {
                    value = expenseDto.getCategory().getName();
                }
                break;
            case 1:
                if (expenseDto.getEstimatedsum() != null) {
                    value = expenseDto.getEstimatedsum();
                } else {
                    value = BigDecimal.valueOf(0.00);
                }
                break;
        }
        return value;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                getValueAt(0, columnIndex).getClass();
            case 1:
                return BigDecimal.class;
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        BudgetExpenseDto expenseDto = filteredCategories.get(rowIndex);
        switch (columnIndex) {
            case 0:
                expenseDto.setCategory((CategoryDto) aValue);
                break;
            case 1:
                expenseDto.setEstimatedsum((BigDecimal) aValue);
                break;
        }

        if (expenseDto.getCategory() != null) {
            Optional<BudgetExpenseDto> upsertBudgetExpense = BudgetExpenseHandler.getInstance().upsertBudgetExpense(expenseDto);
            if (upsertBudgetExpense.isPresent()) {
                filteredCategories.set(rowIndex, upsertBudgetExpense.get());
                fireTableCellUpdated(rowIndex, columnIndex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Budget transaction must have a category set!", "close", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public BigDecimal getTotalSum() {
        BigDecimal totalSum = BigDecimal.ZERO;
        for (BudgetExpenseDto filteredCategory : filteredCategories) {
            totalSum = totalSum.add(filteredCategory.getEstimatedsum());
        }
        return totalSum;
    }

    /**
     * This will return the report at the specified row...
     *
     * @param row
     * @return
     */
    public BudgetExpenseDto getBudgetExpenceAt(int row) {
        return filteredCategories.get(row);
    }

    public void removeBudgetExpenseAt(int row) {
        BudgetExpenseHandler.getInstance().deleteBudgetExpense(getBudgetExpenceAt(row)).ifPresent(action -> {
            filteredCategories.remove(row);
            this.fireTableRowsDeleted(row, row);
        });
    }

    public void addBudgetExpence(BudgetExpenseDto dto) {
        dto.setBudget(BUDGET);
        filteredCategories.add(dto);
        this.fireTableDataChanged();
    }

    public List<BudgetExpenseDto> getAll() {
        return filteredCategories;
    }

    public void clearAll() {
        filteredCategories.clear();
        this.fireTableDataChanged();
    }

    public Optional<Boolean> delete(BudgetExpenseDto budget) {
        boolean remove = filteredCategories.remove(budget);
        this.fireTableDataChanged();
        return Optional.ofNullable(remove);
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.DB.Dto;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.negod.DB.Entities.budget.Budget;
import org.negod.DB.Entities.budget.BudgetRow;
import org.negod.DB.Enums.MonthEnum;
import org.negod.DB.Enums.TransactionTypeEnum;

/**
 *
 * @author Joakim
 */
public class BudgetDto {

    private Budget budget;
    private List<BudgetRow> income;
    private List<BudgetRow> expense;
    private double totalIncome = 0.0;
    private double totalExpense = 0.0;
    private double total = 0.0;
    private MonthEnum monthInYear;
    private Long budgerYear;

    public BudgetDto(Budget budget) {
        this.budget = budget;
        this.monthInYear = budget.getMonthInYear();
        this.budgerYear = budget.getBudgetYear();
        income = new ArrayList<BudgetRow>();
        expense = new ArrayList<BudgetRow>();

        if (budget.getBudgetRow() != null) {
            for (BudgetRow row : budget.getBudgetRow()) {
                if (row.getTransactionType().getTransactionType() == TransactionTypeEnum.expense) {
                    expense.add(row);
                    totalExpense += row.getTotalSum();
                } else if (row.getTransactionType().getTransactionType() == TransactionTypeEnum.income) {
                    income.add(row);
                    totalIncome += row.getTotalSum();
                }
            }
        }
        total = totalIncome - totalExpense;
    }

    public Budget getBudget() {
        return budget;
    }

    public MonthEnum getMonthInYear() {
        return monthInYear;
    }

    public void setMonthInYear(MonthEnum monthInYear) {
        this.monthInYear = monthInYear;
    }

    public Long getBudgerYear() {
        return budgerYear;
    }

    public void setBudgerYear(Long budgerYear) {
        this.budgerYear = budgerYear;
    }

    public List<BudgetRow> getIncome() {
        return income;
    }

    public void setIncome(List<BudgetRow> income) {
        this.income = income;
    }

    public List<BudgetRow> getExpense() {
        return expense;
    }

    public void setExpense(List<BudgetRow> expense) {
        this.expense = expense;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

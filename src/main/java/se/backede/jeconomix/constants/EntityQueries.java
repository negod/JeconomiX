/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.constants;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class EntityQueries {

    public static final String FIND_BUDGET_BY_YEAR_AND_MONTH = "Budget.findByYearAndMonth";
    public static final String GET_BUDGET_EXPENSE_SUM_BY_MONTH_AND_YEAR = "Budget.getBudgetExpeseSumByMonthAndYear";
    public static final String FIND_BUDGET_EXPENSE_BY_YEARMONTH_AND_CATEGORY = "Budget.findExpenseByYearMonthAndCategory";
    public static final String TRANSACTION_EXISTS = "Transaction.transactionExists";
    public static final String TRANSACTION_BY_BUDGETMONTH = "Transaction.byBudgetMonth";
    public static final String TRANSACTION_BY_BUDGETMONTH_AND_CATEGORY = "Transaction.byBudgetMonthAndCategory";
    public static final String FILTERED_CATEGORIES = "Category.byCategoryType";
    public static final String FILTERED_CATEGORIES_BY_YEAR = "Category.byTypeAndYear";

}

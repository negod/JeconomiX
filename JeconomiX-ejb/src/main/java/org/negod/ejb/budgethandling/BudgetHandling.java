/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.ejb.budgethandling;

import java.util.List;
import javax.ejb.Local;
import org.negod.DB.Entities.budget.Budget;
import org.negod.DB.Enums.MonthEnum;

/**
 *
 * @author Joakim
 */
@Local
public interface BudgetHandling {

    public void addBudget(Budget budget) throws Exception;

    public void updateBudget(Budget budget) throws Exception;

    public Budget getBudgetByYearAndMonth(MonthEnum month, int year, String username)throws Exception;

    public List<Budget> getBudgetByYear(int year, String username)throws Exception;
    
    public Budget getBudgetByMonth(MonthEnum month, String username)throws Exception;

    public List<Budget> getAllBudget(String username)throws Exception;
}

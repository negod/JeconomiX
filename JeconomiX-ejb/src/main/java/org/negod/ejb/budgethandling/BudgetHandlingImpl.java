/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.ejb.budgethandling;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.negod.DB.Entities.budget.Budget;
import org.negod.DB.Enums.MonthEnum;

/**
 *
 * @author Joakim
 */
@Stateless
public class BudgetHandlingImpl implements BudgetHandling, Serializable {

    @PersistenceContext(unitName = "jeconomix")
    private EntityManager em;

    @Override
    public void addBudget(Budget budget) throws Exception {
        em.persist(budget);
    }

    @Override
    public void updateBudget(Budget budget) throws Exception {
        em.merge(budget);
    }

    @Override
    public Budget getBudgetByYearAndMonth(MonthEnum month, int year, String username) throws Exception {
        Query q = em.createNamedQuery("Budget.findByYearAndMonth");
        q.setParameter("month", month);
        q.setParameter("year", year);
        q.setParameter("user", username);
        return (Budget) q.getSingleResult();
    }

    @Override
    public List<Budget> getBudgetByYear(int year, String username) throws Exception {
        Query q = em.createNamedQuery("Budget.findByYearAndUser");
        q.setParameter("year", year);
        q.setParameter("user", username);
        return (List<Budget>) q.getResultList();
    }

    @Override
    public List<Budget> getAllBudget(String username) throws Exception {
        Query q = em.createNamedQuery("Budget.findByUser");
        q.setParameter("user", username);
        return (List<Budget>) q.getResultList();
    }

    @Override
    public Budget getBudgetByMonth(MonthEnum month, String username) throws Exception {
        Query q = em.createNamedQuery("Budget.findByMonthAndUser");
        q.setParameter("month", month);
        q.setParameter("user", username);
        return (Budget) q.getSingleResult();
    }
}

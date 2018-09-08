/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.dao;

import com.negod.generics.persistence.GenericDao;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import se.backede.jeconomix.database.PersistenceHandler;
import se.backede.jeconomix.database.entity.budget.BudgetExpense;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BudgetExpenseDao extends GenericDao<BudgetExpense> {

    @Override
    public EntityManager getEntityManager() {
        return PersistenceHandler.getInstance().getEntityManager();
    }

    @Override
    public EntityManager getEntityManager(String name) {
        return PersistenceHandler.getInstance().getEntityManager(name);
    }

    @Override
    public Session getHibernateSession() {
        return PersistenceHandler.getInstance().getHibernateSession();
    }

}

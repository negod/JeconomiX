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
import org.negod.DB.Entities.budget.TransactionType;

/**
 *
 * @author Joakim
 */

@Stateless
public class TransactionHandlingImpl implements TransactionHandling, Serializable {

    @PersistenceContext(unitName = "jeconomix")
    private EntityManager em;

    @Override
    public void addTransactionType(TransactionType type) throws Exception{
        em.persist(type);
    }

    @Override
    public void removeTransactionType(TransactionType type) throws Exception{
        em.remove(type);
    }

    @Override
    public void updateTransactinType(TransactionType type) throws Exception{
        em.merge(type);
    }

    @Override
    public List<TransactionType> getAllTransactionTypes(String username) throws Exception{
        Query q = em.createNamedQuery("TransactionType.findByUser");
        q.setParameter("user", username);
        return (List<TransactionType>) q.getResultList();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.ejb.budgethandling;

import java.util.List;
import javax.ejb.Local;
import org.negod.DB.Entities.budget.TransactionType;

/**
 *
 * @author Joakim
 */
@Local
public interface TransactionHandling {

    public void addTransactionType(TransactionType type)throws Exception;

    public void removeTransactionType(TransactionType type)throws Exception;

    public void updateTransactinType(TransactionType type)throws Exception;

    public List<TransactionType> getAllTransactionTypes(String username)throws Exception;
}

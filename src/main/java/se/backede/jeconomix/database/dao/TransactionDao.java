/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.dao;

import com.negod.generics.persistence.GenericDao;
import javax.persistence.EntityManager;
import se.backede.jeconomix.database.PersistenceHandler;
import se.backede.jeconomix.database.entity.Transaction;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class TransactionDao extends GenericDao<Transaction> {

    @Override
    public EntityManager getEntityManager() {
        return PersistenceHandler.getInstance().getEntityManager();
    }

}

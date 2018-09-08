/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.Session;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class PersistenceHandler {

    private static EntityManager em;
    private static EntityManagerFactory emf;

    private static final PersistenceHandler INSTANCE = new PersistenceHandler();

    protected PersistenceHandler() {
    }

    public static final PersistenceHandler getInstance() {
        return INSTANCE;
    }

    public EntityManager getEntityManager() {
        return getEntityManager("PU");
    }

    public EntityManager getEntityManager(String name) {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(name);
        }
        if (em == null || !em.isOpen()) {
            em = emf.createEntityManager();
        }
        return em;
    }

    public Session getHibernateSession() {
        return getEntityManager().unwrap(org.hibernate.Session.class);
    }

    public Session getHibernateSession(String name) {
        return getEntityManager(name).unwrap(org.hibernate.Session.class);
    }

}

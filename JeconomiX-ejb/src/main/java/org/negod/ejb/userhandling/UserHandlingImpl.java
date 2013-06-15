/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.ejb.userhandling;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.negod.DB.Entities.userhandling.Groups;
import org.negod.DB.Entities.userhandling.Users;

/**
 *
 * @author Joakim
 */
@Stateless
public class UserHandlingImpl implements UserHandling, Serializable {

    @PersistenceContext(unitName = "jeconomix")
    private EntityManager em;

    //****** USER ******//
    @Override
    public List<Users> getAllUserss() throws Exception {
        Query q = em.createNamedQuery("Users.findAll");
        return (List<Users>) q.getResultList();
    }

    @Override
    public void addUsers(Users user) throws Exception {
        em.persist(user);
    }

    @Override
    public void updateUsers(Users user) throws Exception {
        em.merge(user);
    }

    @Override
    public void deleteUsers(Users user) throws Exception {
        em.remove(user);
    }

    //****** USERGROUP ******//
    @Override
    public List<Groups> getAllGroups() throws Exception {
        Query q = em.createNamedQuery("Groups.findAll");
        return (List<Groups>) q.getResultList();
    }

    @Override
    public void addGroups(Groups userGroup) throws Exception {
        em.persist(userGroup);
    }

    @Override
    public void updateGroups(Groups userGroup) throws Exception {
        em.persist(userGroup);
    }

    @Override
    public void deleteGroups(Groups userGroup) throws Exception {
        em.remove(userGroup);
    }

    @Override
    public Users getUserByUserName(String username) {
        Query q = em.createNamedQuery("Users.findByUsername");
        q.setParameter("username", username);
        return (Users) q.getSingleResult();
    }
}

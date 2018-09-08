/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.dao;

import com.negod.generics.persistence.GenericDao;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import se.backede.jeconomix.database.entity.Company;
import se.backede.jeconomix.database.PersistenceHandler;
import se.backede.jeconomix.database.entity.Company_;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CompanyDao extends GenericDao<Company> {

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

    protected Optional<Company> getCompanyByCompanyName(String name) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Company> cq = criteriaBuilder.createQuery(getEntityClass());
        Root<Company> entity = cq.from(getEntityClass());
        cq.where(entity.get(Company_.name).in(name));
        return get(cq);
    }

}

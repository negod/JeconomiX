/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.dao;

import com.negod.generics.persistence.GenericDao;
import com.negod.generics.persistence.exception.DaoException;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import se.backede.jeconomix.database.PersistenceHandler;
import se.backede.jeconomix.database.entity.Company;
import se.backede.jeconomix.database.entity.CompanyAccociation;
import se.backede.jeconomix.database.entity.CompanyAccociation_;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class AccociatedCompanyDao extends GenericDao<CompanyAccociation> {

    @Override
    public EntityManager getEntityManager() {
        return PersistenceHandler.getInstance().getEntityManager();
    }

    @Override
    public Session getHibernateSession() {
        return PersistenceHandler.getInstance().getHibernateSession();
    }

    public Optional<CompanyAccociation> getByAccociatedCompanyByName(String name) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = criteriaBuilder.createQuery(getEntityClass());
        Root entity = cq.from(getEntityClass());
        cq.where(entity.get(CompanyAccociation_.name).in(name));
        return get(cq);
    }

    public Optional<Company> getCompanyByAccociatedName(String name) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = criteriaBuilder.createQuery(getEntityClass());
        Root entity = cq.from(getEntityClass());
        cq.where(entity.get(CompanyAccociation_.name).in(name));
        Optional<CompanyAccociation> data = get(cq);
        if (data.isPresent()) {
            return Optional.of(data.get().getCompany());
        }
        return Optional.empty();
    }

}

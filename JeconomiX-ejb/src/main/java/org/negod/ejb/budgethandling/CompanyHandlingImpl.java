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
import org.negod.DB.Entities.budget.Company;

/**
 *
 * @author Joakim
 */

@Stateless
public class CompanyHandlingImpl implements CompanyHandling, Serializable {

    @PersistenceContext(unitName = "jeconomix")
    private EntityManager em;

    @Override
    public void addCompany(Company company) throws Exception{
        em.persist(company);
    }

    @Override
    public void updateCompany(Company company) throws Exception{
        em.merge(company);
    }

    @Override
    public void deleteCompany(Company company) throws Exception{
        em.remove(company);
    }

    @Override
    public List<Company> getAllCompanies(String username) throws Exception{
        Query q = em.createNamedQuery("Company.findByUser");
        q.setParameter("user", username);
        return (List<Company>) q.getResultList();
    }

    @Override
    public Company getCompanyByName(String companyName, String user) throws Exception{
        Query q = em.createNamedQuery("Company.findByNameAndUser");
        q.setParameter("companyName", companyName);
        q.setParameter("user", user);
        return (Company) q.getSingleResult();
    }
}

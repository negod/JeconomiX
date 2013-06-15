/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.ejb.budgethandling;

import java.util.List;
import javax.ejb.Local;
import org.negod.DB.Entities.budget.Company;

/**
 *
 * @author Joakim
 */
@Local
public interface CompanyHandling {

    public void addCompany(Company company) throws Exception;

    public void updateCompany(Company company) throws Exception;

    public void deleteCompany(Company company) throws Exception;

    public List<Company> getAllCompanies(String username) throws Exception;

    public Company getCompanyByName(String companyName, String user) throws Exception;
}

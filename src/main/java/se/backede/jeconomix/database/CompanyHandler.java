/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import com.negod.generics.persistence.entity.GenericEntity_;
import com.negod.generics.persistence.exception.ConstraintException;
import com.negod.generics.persistence.exception.DaoException;
import com.negod.generics.persistence.mapper.DtoEntityBaseMapper;
import com.negod.generics.persistence.search.GenericFilter;
import com.negod.generics.persistence.update.ObjectUpdate;
import com.negod.generics.persistence.update.UpdateType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.ExpenseCategoryDto;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.database.dao.CompanyDao;
import se.backede.jeconomix.database.entity.Company;
import se.backede.jeconomix.database.entity.Transaction;
import se.backede.jeconomix.database.entity.search.CompanySearch;
import se.backede.jeconomix.dto.BillCategoryDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class CompanyHandler {

    CompanyDao dao = new CompanyDao();
    DtoEntityBaseMapper<CompanyDto, Company> companyMapper = new DtoEntityBaseMapper(CompanyDto.class, Company.class);
    DtoEntityBaseMapper<TransactionDto, Transaction> transactionMapper = new DtoEntityBaseMapper(TransactionDto.class, Transaction.class);
    TransactionHandler transactionHandler = new TransactionHandler();

    private static final CompanyHandler companyHandler = new CompanyHandler();

    protected CompanyHandler() {
    }

    public static final CompanyHandler getInstance() {
        return companyHandler;
    }

    public Optional<CompanyDto> createCompany(CompanyDto company) {

        Optional<Company> entity = companyMapper.mapFromDtoToEntity(company);

        //Handle transactions
        entity.get().getTransactions().clear();

        if (entity.isPresent()) {
            try {

                dao.startTransaction();
                Optional<Company> persist = dao.persist(entity.get());
                dao.commitTransaction();

                if (company.getTransactions() != null && !company.getTransactions().isEmpty()) {
                    for (TransactionDto transaction : company.getTransactions()) {
                        dao.startTransaction();
                        Transaction get = transactionMapper.mapFromDtoToEntity(transaction).get();
                        Optional<Company> byId = dao.getById(persist.get().getId());
                        get.setCompany(byId.get());
                        byId.get().getTransactions().add(get);
                        dao.commitTransaction();
                    }
                }

                if (persist.isPresent()) {
                    return companyMapper.mapFromEntityToDto(entity.get());
                }

            } catch (DaoException | ConstraintException ex) {
                log.error("Error when persisting Company", ex);
            }
        }
        return Optional.empty();
    }

    public Optional<CompanyDto> updateCompany(CompanyDto company) {
        Optional<Company> entity = companyMapper.mapFromDtoToEntity(company);
        if (entity.isPresent()) {
            try {
                dao.startTransaction();
                Optional<Company> persist = dao.update(entity.get());
                dao.commitTransaction();

                if (persist.isPresent()) {
                    return companyMapper.mapFromEntityToDto(entity.get());
                }

            } catch (DaoException ex) {
                log.error("Error when updating Company", ex);
            }
        }
        return Optional.empty();
    }

    public Optional<List<CompanyDto>> getAllCompanies() {
        try {
            Optional<List<Company>> all = dao.getAll();
            if (all.isPresent()) {
                return companyMapper.mapToDtoList(all.get());
            }
        } catch (DaoException e) {
            log.error("Error when getting companies", e);
        }
        return Optional.empty();
    }

    public Optional<CompanyDto> getCompanyByName(String name) {
        try {
            Optional<Company> company = dao.getCompanyByName(name);
            if (company.isPresent()) {
                return companyMapper.mapFromEntityToDto(company.get());
            }
        } catch (DaoException ex) {
            Logger.getLogger(CompanyHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }

    public Optional<CompanyDto> setExpenseCategory(CompanyDto companyDto, ExpenseCategoryDto expenseCategory) {
        if (expenseCategory != null) {
            ObjectUpdate update = new ObjectUpdate();
            update.setObject("expenseCategory");
            update.setType(UpdateType.UPDATE);
            update.setObjectId(expenseCategory.getId());

            try {
                dao.startTransaction();
                Optional<Company> company = dao.update(companyDto.getId(), update);
                dao.commitTransaction();

                if (company.isPresent()) {
                    return companyMapper.mapFromEntityToDto(company.get());
                }

            } catch (DaoException ex) {
                Logger.getLogger(CompanyHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return Optional.empty();

    }

    public Optional<CompanyDto> setBillCategory(CompanyDto companyDto, BillCategoryDto billCategory) {

        if (billCategory != null) {
            ObjectUpdate update = new ObjectUpdate();
            update.setObject("billCategory");
            update.setType(UpdateType.UPDATE);
            update.setObjectId(billCategory.getId());

            try {
                dao.startTransaction();
                Optional<Company> company = dao.update(companyDto.getId(), update);
                dao.commitTransaction();

                if (company.isPresent()) {
                    return companyMapper.mapFromEntityToDto(company.get());
                }

            } catch (DaoException ex) {
                Logger.getLogger(CompanyHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return Optional.empty();

    }

    public Optional<CompanyDto> getCompanyById(String id) {
        try {
            Optional<Company> byId = dao.getById(id);
            if (byId.isPresent()) {
                return companyMapper.mapFromEntityToDto(byId.get());
            }
        } catch (DaoException ex) {
            Logger.getLogger(CompanyHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }

    public void reIndex() {
        dao.indexEntity();
    }

}

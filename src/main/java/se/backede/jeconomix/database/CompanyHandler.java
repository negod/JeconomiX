/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import com.negod.generics.persistence.exception.ConstraintException;
import com.negod.generics.persistence.exception.DaoException;
import com.negod.generics.persistence.mapper.DtoEntityBaseMapper;
import com.negod.generics.persistence.update.ObjectUpdate;
import com.negod.generics.persistence.update.UpdateType;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.database.dao.AccociatedCompanyDao;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.database.dao.CompanyDao;
import se.backede.jeconomix.database.entity.Company;
import se.backede.jeconomix.database.entity.CompanyAccociation;
import se.backede.jeconomix.database.entity.Transaction;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.CompanyAccociationDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class CompanyHandler {

    CompanyDao companyDao = new CompanyDao();
    AccociatedCompanyDao accocociatedCompanyDao = new AccociatedCompanyDao();

    DtoEntityBaseMapper<CompanyDto, Company> companyMapper = new DtoEntityBaseMapper(CompanyDto.class, Company.class);
    DtoEntityBaseMapper<TransactionDto, Transaction> transactionMapper = new DtoEntityBaseMapper(TransactionDto.class, Transaction.class);
    DtoEntityBaseMapper<CompanyAccociationDto, CompanyAccociation> accociatedCompanyMapper = new DtoEntityBaseMapper(CompanyAccociationDto.class, CompanyAccociation.class);

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

                companyDao.startTransaction();
                Optional<Company> persist = companyDao.persist(entity.get());
                companyDao.commitTransaction();

                if (company.getTransactions() != null && !company.getTransactions().isEmpty()) {
                    for (TransactionDto transaction : company.getTransactions()) {
                        companyDao.startTransaction();
                        Transaction transactionEntity = transactionMapper.mapFromDtoToEntity(transaction).get();
                        Optional<Company> byId = companyDao.getById(persist.get().getId());
                        TransactionHandler.getInstance().decideBudgetMonth(transactionEntity, byId.get().getCategory().getCategoryType().getType());
                        transactionEntity.setCompany(byId.get());

                        byId.get().getTransactions().add(transactionEntity);
                        companyDao.commitTransaction();
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
                companyDao.startTransaction();
                Optional<Company> persist = companyDao.update(entity.get());
                companyDao.commitTransaction();

                if (persist.isPresent()) {
                    return companyMapper.mapFromEntityToDto(entity.get());
                }

            } catch (DaoException ex) {
                log.error("Error when updating Company", ex);
            }
        }
        return Optional.empty();
    }

    public Optional<CompanyDto> addAccociatedCompany(CompanyDto company) {
        Optional<Company> entity = companyMapper.mapFromDtoToEntity(company);
        if (entity.isPresent()) {
            try {
                companyDao.startTransaction();

                Optional<Company> persist = companyDao.update(entity.get());

                companyDao.commitTransaction();

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
            Optional<List<Company>> all = companyDao.getAll();
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
            Optional<Company> company = companyDao.getCompanyByName(name);
            if (company.isPresent()) {
                return companyMapper.mapFromEntityToDto(company.get());
            }
        } catch (DaoException ex) {
            log.error("Error when getting company by name", ex);
        }
        return Optional.empty();
    }

    public Optional<CompanyAccociationDto> getAccociatedCompanyByName(String name) {
        try {
            Optional<CompanyAccociation> company = companyDao.getByAcciciatedCompany(name);
            if (company.isPresent()) {
                return accociatedCompanyMapper.mapFromEntityToDto(company.get());
            }
        } catch (DaoException ex) {
            log.error("Error when getting company by name", ex);
        }
        return Optional.empty();
    }

    public Optional<CompanyDto> setCategory(CompanyDto companyDto, CategoryDto category) {

        if (category != null) {
            ObjectUpdate update = new ObjectUpdate();
            update.setObject("category");
            update.setType(UpdateType.UPDATE);
            update.setObjectId(category.getId());

            try {
                companyDao.startTransaction();
                Optional<Company> company = companyDao.update(companyDto.getId(), update);
                companyDao.commitTransaction();

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
            Optional<Company> byId = companyDao.getById(id);
            if (byId.isPresent()) {
                return companyMapper.mapFromEntityToDto(byId.get());
            }
        } catch (DaoException ex) {
            Logger.getLogger(CompanyHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }

    public void reIndex() {
        companyDao.indexEntity();
    }

}

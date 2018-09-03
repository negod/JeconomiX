/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import com.negod.generics.persistence.exception.DaoException;
import com.negod.generics.persistence.mapper.DtoEntityBaseMapper;
import com.negod.generics.persistence.update.ObjectUpdate;
import com.negod.generics.persistence.update.UpdateType;
import java.util.List;
import java.util.Optional;
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

    DtoEntityBaseMapper<CompanyDto, Company> companyMapper = new DtoEntityBaseMapper(CompanyDto.class, Company.class);
    DtoEntityBaseMapper<TransactionDto, Transaction> transactionMapper = new DtoEntityBaseMapper(TransactionDto.class, Transaction.class);

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

        }
        return Optional.empty();
    }

    public Optional<CompanyDto> updateCompany(CompanyDto companyDto) {
        return companyMapper.mapFromDtoToEntity(companyDto).map(companyEntity -> {
            companyDao.startTransaction();
            Optional<CompanyDto> mappedCompany = companyDao.update(companyEntity).map(newCompany -> {
                return companyMapper.mapFromEntityToDto(newCompany).get();
            });
            companyDao.commitTransaction();
            return mappedCompany.get();
        });

    }

    public Optional<CompanyDto> addAccociatedCompany(CompanyDto companyDto) {
        return companyMapper.mapFromDtoToEntity(companyDto).map(company -> {
            return companyDao.execute(() -> companyDao.update(company)).map(persistedCompany -> {
                return companyMapper.mapFromEntityToDto(persistedCompany).get();
            }).get();
        });

    }

    public Optional<List<CompanyDto>> getAllCompanies() {
        return companyDao.getAll().map(companies -> {
            return companyMapper.mapToDtoList(companies).get();
        });
    }

    public Optional<CompanyDto> getCompanyByName(String name) {
        log.debug("Getting company by name {}", name);
        return companyDao.getCompanyByName(name).map(company -> {
            return companyMapper.mapFromEntityToDto(company).get();
        });
    }

    public Optional<CompanyDto> setCategory(CompanyDto companyDto, CategoryDto category) {

        ObjectUpdate update = new ObjectUpdate();
        update.setObject("category");
        update.setType(UpdateType.UPDATE);
        update.setObjectId(category.getId());

        companyDao.startTransaction();

        Optional<CompanyDto> updatedCompany = companyDao.update(companyDto.getId(), update).map(company -> {
            return companyMapper.mapFromEntityToDto(company).get();
        });

        companyDao.commitTransaction();

        return updatedCompany;

    }

    public Optional<CompanyDto> getCompanyById(String id) {
        return companyDao.getById(id).map(company -> {
            return companyMapper.mapFromEntityToDto(company).get();
        });
    }

    public void reIndex() {
        companyDao.indexEntity();
    }

}

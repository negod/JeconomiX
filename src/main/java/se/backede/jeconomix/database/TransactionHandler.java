/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import com.negod.generics.persistence.exception.ConstraintException;
import com.negod.generics.persistence.exception.DaoException;
import com.negod.generics.persistence.mapper.DtoEntityBaseMapper;
import java.util.List;
import java.util.Optional;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.constants.EntityQueries;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.database.dao.CompanyDao;
import se.backede.jeconomix.database.dao.TransactionDao;
import se.backede.jeconomix.database.entity.Company;
import se.backede.jeconomix.database.entity.Transaction;
import se.backede.jeconomix.database.entity.budget.Budget;
import se.backede.jeconomix.dto.CompanyDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class TransactionHandler {

    TransactionDao dao = new TransactionDao();
    CompanyDao companyDao = new CompanyDao();
    DtoEntityBaseMapper<TransactionDto, Transaction> mapper = new DtoEntityBaseMapper(TransactionDto.class, Transaction.class);
    DtoEntityBaseMapper<CompanyDto, Company> companyMapper = new DtoEntityBaseMapper(CompanyDto.class, Company.class);

    private static final TransactionHandler companyHandler = new TransactionHandler();

    protected TransactionHandler() {
    }

    public static final TransactionHandler getInstance() {
        return companyHandler;
    }

    public boolean transactionExists(TransactionDto transaction) {
        try {
            Optional<Company> companyEntity = companyMapper.mapFromDtoToEntity(transaction.getCompany());
            dao.startTransaction();
            Query query = dao.getEntityManager().createNamedQuery(EntityQueries.TRANSACTION_EXISTS);
            query.setParameter("company", companyEntity.get());
            query.setParameter("date", transaction.getTransDate());
            query.setParameter("saldo", transaction.getSaldo());
            query.setParameter("sum", transaction.getSum());
            Transaction transactionEntity = (Transaction) query.getSingleResult();
        } catch (NoResultException ex) {
            log.debug("No result for query when getting Transaction");
            return false;
        } finally {
            dao.commitTransaction();
        }
        return true;
    }

    public Optional<TransactionDto> createTransaction(TransactionDto transaction) {
        Optional<Transaction> entity = mapper.mapFromDtoToEntity(transaction);
        if (entity.isPresent()) {
            try {

                dao.startTransaction();
                Optional<Company> byId = companyDao.getById(transaction.getCompany().getId());
                entity.get().setCompany(byId.get());
                Optional<Transaction> persist = dao.persist(entity.get());
                dao.commitTransaction();

                if (persist.isPresent()) {
                    return mapper.mapFromEntityToDto(entity.get());
                }

            } catch (DaoException | ConstraintException ex) {
                log.error("Error when persisting expense category", ex);
            }
        }
        return Optional.empty();
    }

    public Optional<List<TransactionDto>> getAllTransactions() {
        try {
            Optional<List<Transaction>> all = dao.getAll();
            if (all.isPresent()) {
                return mapper.mapToDtoList(all.get());
            }
        } catch (DaoException e) {
            log.error("Error when getting all transactions", e);
        }
        return Optional.empty();
    }

}

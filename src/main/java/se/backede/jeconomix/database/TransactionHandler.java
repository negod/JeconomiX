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
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.database.dao.CompanyDao;
import se.backede.jeconomix.database.dao.TransactionDao;
import se.backede.jeconomix.database.entity.Company;
import se.backede.jeconomix.database.entity.Transaction;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class TransactionHandler {

    TransactionDao dao = new TransactionDao();
    CompanyDao companyDao = new CompanyDao();
    DtoEntityBaseMapper<TransactionDto, Transaction> mapper = new DtoEntityBaseMapper(TransactionDto.class, Transaction.class);

    private static final TransactionHandler companyHandler = new TransactionHandler();

    protected TransactionHandler() {
    }

    public static final TransactionHandler getInstance() {
        return companyHandler;
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
            log.error("Error when getting expenseCategories", e);
        }
        return Optional.empty();
    }
}

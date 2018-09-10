/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import com.negod.generics.persistence.mapper.DtoEntityBaseMapper;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.constants.EntityQueries;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.database.dao.CompanyDao;
import se.backede.jeconomix.database.dao.TransactionDao;
import se.backede.jeconomix.database.entity.Company;
import se.backede.jeconomix.database.entity.Transaction;
import se.backede.jeconomix.dto.CompanyDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class TransactionHandler extends TransactionDao {

    CompanyDao companyDao = new CompanyDao();
    DtoEntityBaseMapper<TransactionDto, Transaction> mapper = new DtoEntityBaseMapper<>(TransactionDto.class, Transaction.class);
    DtoEntityBaseMapper<CompanyDto, Company> companyMapper = new DtoEntityBaseMapper<>(CompanyDto.class, Company.class);

    private static final TransactionHandler INSTANCE = new TransactionHandler();

    protected TransactionHandler() {
    }

    public static final TransactionHandler getInstance() {
        return INSTANCE;
    }

    public boolean transactionExists(TransactionDto transaction) {

        Supplier<Optional<Transaction>> getTransaction = () -> {
            if (transaction.getCompany() != null) {
                return companyMapper.mapFromDtoToEntity(transaction.getCompany()).map(companyEntity -> {

                    try {
                        Query query = super.getEntityManager().createNamedQuery(EntityQueries.TRANSACTION_EXISTS);
                        query.setParameter("company", companyEntity);
                        query.setParameter("date", transaction.getTransDate());
                        query.setParameter("saldo", transaction.getSaldo());
                        query.setParameter("sum", transaction.getSum());
                        query.setParameter("originalValue", transaction.getOriginalValue());
                        return (Transaction) query.getSingleResult();
                    } catch (javax.persistence.NoResultException e) {
                        log.error("No result when getting transaction [Transaction exists?]");
                        return null;
                    }
                });
            } else {
                return Optional.empty();
            }
        };

        return super.executeTransaction(getTransaction).isPresent();

    }

    public Transaction decideBudgetMonth(Transaction transaction, CategoryTypeEnum category) {

        Month budgetMonth = transaction.getTransDate().toLocalDate().getMonth();
        Integer budgetYear = transaction.getTransDate().toLocalDate().getYear();

        if (category.equals(CategoryTypeEnum.BILL) || category.equals(CategoryTypeEnum.INCOME)) {
            if (transaction.getTransDate().toLocalDate().getDayOfMonth() >= 22) {
                budgetMonth = transaction.getTransDate().toLocalDate().getMonth().plus(1);
                if (budgetMonth == Month.JANUARY) {
                    budgetYear += 1;
                }
            }
        }

        transaction.setBudgetMonth(budgetMonth);
        transaction.setBudgetYear(budgetYear);
        return transaction;
    }

    public Optional<TransactionDto> createTransaction(TransactionDto transaction) {

        Optional<Transaction> entity = mapper.mapFromDtoToEntity(transaction);
        if (entity.isPresent()) {

            super.startTransaction();
            Optional<Company> byId = companyDao.getById(transaction.getCompany().getId());

            CategoryTypeEnum type = byId.get().getCategory().getCategoryType().getType();
            Transaction decideBudgetMonth = decideBudgetMonth(entity.get(), type);
            decideBudgetMonth.setCompany(byId.get());

            Optional<Transaction> persist = super.persist(entity.get());
            super.commitTransaction();

            if (persist.isPresent()) {
                return mapper.mapFromEntityToDto(entity.get());
            }

        }
        return Optional.empty();
    }

    public Optional<List<TransactionDto>> getAllTransactions() {
        Optional<List<Transaction>> all = super.getAll();
        if (all.isPresent()) {
            return mapper.mapToDtoList(all.get());
        }
        return Optional.empty();
    }

}

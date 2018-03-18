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
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.dto.ExpenseCategoryDto;
import se.backede.jeconomix.database.dao.ExpenseCategoryDao;
import se.backede.jeconomix.database.entity.ExpenseCategory;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class ExpenseCategoryHandler {

    ExpenseCategoryDao dao = new ExpenseCategoryDao();
    DtoEntityBaseMapper<ExpenseCategoryDto, ExpenseCategory> mapper = new DtoEntityBaseMapper(ExpenseCategoryDto.class, ExpenseCategory.class);

    private static final ExpenseCategoryHandler companyHandler = new ExpenseCategoryHandler();

    protected ExpenseCategoryHandler() {
    }

    public static final ExpenseCategoryHandler getInstance() {
        return companyHandler;
    }

    public Optional<ExpenseCategoryDto> getById(String id) {
        try {
            dao.startTransaction();
            Optional<ExpenseCategory> byId = dao.getById(id);
            dao.commitTransaction();

            if (byId.isPresent()) {
                return mapper.mapFromEntityToDto(byId.get());
            }
        } catch (DaoException ex) {
            Logger.getLogger(ExpenseCategoryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }

    public Optional<ExpenseCategoryDto> createExpenseCategory(ExpenseCategoryDto company) {
        Optional<ExpenseCategory> entity = mapper.mapFromDtoToEntity(company);
        if (entity.isPresent()) {
            try {
                dao.startTransaction();
                Optional<ExpenseCategory> persist = dao.persist(entity.get());
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

    public Optional<List<ExpenseCategoryDto>> getAllExpenseCategories() {
        try {
            Optional<List<ExpenseCategory>> all = dao.getAll();
            if (all.isPresent()) {
                return mapper.mapToDtoList(all.get());
            }
        } catch (DaoException e) {
            log.error("Error when getting expenseCategories", e);
        }
        return Optional.empty();
    }
}
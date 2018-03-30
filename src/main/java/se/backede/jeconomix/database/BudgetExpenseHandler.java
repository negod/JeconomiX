/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import com.negod.generics.persistence.exception.ConstraintException;
import com.negod.generics.persistence.exception.DaoException;
import com.negod.generics.persistence.exception.NotFoundException;
import com.negod.generics.persistence.mapper.DtoEntityBaseMapper;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.database.dao.BudgetDao;
import se.backede.jeconomix.database.dao.BudgetExpenseDao;
import se.backede.jeconomix.database.dao.CategoryDao;
import se.backede.jeconomix.database.entity.Category;
import se.backede.jeconomix.database.entity.budget.Budget;
import se.backede.jeconomix.database.entity.budget.BudgetExpense;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class BudgetExpenseHandler {

    BudgetExpenseDao dao = new BudgetExpenseDao();
    BudgetDao budgetDao = new BudgetDao();
    CategoryDao categoryDao = new CategoryDao();

    DtoEntityBaseMapper<BudgetExpenseDto, BudgetExpense> mapper = new DtoEntityBaseMapper(BudgetExpenseDto.class, BudgetExpense.class);

    private static final BudgetExpenseHandler INSTANCE = new BudgetExpenseHandler();

    protected BudgetExpenseHandler() {
    }

    public static final BudgetExpenseHandler getInstance() {
        return INSTANCE;
    }

    public Optional<BudgetExpenseDto> createBudgetExpense(BudgetExpenseDto dto) {
        Optional<BudgetExpense> mapFromDtoToEntity = mapper.mapFromDtoToEntity(dto);
        if (mapFromDtoToEntity.isPresent()) {
            try {
                dao.startTransaction();

                Optional<Budget> budget = budgetDao.getById(dto.getBudget().getId());
                Optional<Category> category = categoryDao.getById(dto.getCategory().getId());

                if (budget.isPresent()) {
                    mapFromDtoToEntity.get().setBudget(budget.get());
                }

                if (category.isPresent()) {
                    mapFromDtoToEntity.get().setCategory(category.get());
                }

                Optional<BudgetExpense> persist = dao.persist(mapFromDtoToEntity.get());
                if (persist.isPresent()) {
                    return mapper.mapFromEntityToDto(persist.get());
                }
            } catch (DaoException | ConstraintException ex) {
                log.error("Error when persisting BudgetExpense", ex);
                return Optional.empty();
            } finally {
                dao.commitTransaction();
            }
        }
        return Optional.empty();
    }

    public Optional<BudgetExpenseDto> updateBudgetExpense(BudgetExpenseDto dto) {
        Optional<BudgetExpense> mapFromDtoToEntity = mapper.mapFromDtoToEntity(dto);
        if (mapFromDtoToEntity.isPresent()) {
            try {
                dao.startTransaction();

                Optional<BudgetExpense> budgetExpenseEntity = dao.getById(dto.getId());
                if (budgetExpenseEntity.isPresent()) {
                    if (!budgetExpenseEntity.get().getCategory().getId().equals(dto.getCategory().getId())) {
                        Optional<Category> categoryEntity = CategoryHandler.getInstance().DAO.getById(dto.getCategory().getId());
                        budgetExpenseEntity.get().setCategory(categoryEntity.get());
                    }
                }

                budgetExpenseEntity.get().setEstimatedsum(dto.getEstimatedsum());

                BudgetExpense merge = dao.getEntityManager().merge(budgetExpenseEntity.get());
                Optional<BudgetExpenseDto> mapFromEntityToDto = mapper.mapFromEntityToDto(merge);
                dao.commitTransaction();

                return mapFromEntityToDto;
            } catch (DaoException ex) {
                log.error("Error when updating BudgetExpense", ex);
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public Boolean deleteBudgetExpense(BudgetExpenseDto dto) {
        try {
            dao.startTransaction();
            Boolean delete = dao.delete(dto.getId());
            dao.commitTransaction();
            return delete;
        } catch (NotFoundException ex) {
            log.error("Error when deleting budget expense", ex);
        }
        return false;
    }

    public Optional<BudgetExpenseDto> upsertBudgetExpense(BudgetExpenseDto dto) {
        if (dto.getId() == null) {
            return createBudgetExpense(dto);
        } else {
            return updateBudgetExpense(dto);
        }
    }

}

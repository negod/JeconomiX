/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import java.time.Year;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import se.backede.generics.persistence.mapper.DtoEntityBaseMapper;
import java.util.Optional;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.constants.BudgetQuarterEnum;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.constants.EntityQueries;
import se.backede.jeconomix.database.dao.BudgetDao;
import se.backede.jeconomix.database.dao.BudgetExpenseDao;
import se.backede.jeconomix.database.dao.CategoryDao;
import se.backede.jeconomix.database.entity.Category;
import se.backede.jeconomix.database.entity.budget.BudgetExpense;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;
import se.backede.jeconomix.dto.mappers.BudgetExpenseMapper;
import se.backede.jeconomix.utils.BudgetUtils;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class BudgetExpenseHandler extends BudgetExpenseDao {

    private final BudgetDao budgetDao = new BudgetDao();
    private final CategoryDao categoryDao = new CategoryDao();

    private static final BudgetExpenseHandler INSTANCE = new BudgetExpenseHandler();

    protected BudgetExpenseHandler() {
    }

    public static final BudgetExpenseHandler getInstance() {
        return INSTANCE;
    }

    public Optional<BudgetExpenseDto> createBudgetExpense(BudgetExpenseDto dto) {

        BudgetExpense mapToBudgetExpense = BudgetExpenseMapper.INSTANCE.mapToBudgetExpense(dto);

        budgetDao.getById(mapToBudgetExpense.getId()).ifPresent(budget -> {
            mapToBudgetExpense.setBudget(budget);
        });

        categoryDao.getById(mapToBudgetExpense.getId()).ifPresent(category -> {
            mapToBudgetExpense.setCategory(category);
        });

        return super.executeTransaction(() -> super.persist(mapToBudgetExpense)).map(persisted -> {
            return BudgetExpenseMapper.INSTANCE.mapToBudgetExpenseDto(persisted);
        });

    }

    public Optional<BudgetExpenseDto> updateBudgetExpense(BudgetExpenseDto dto) {

        super.startTransaction();

        Optional<BudgetExpense> budgetExpenseEntity = super.getById(dto.getId());
        if (budgetExpenseEntity.isPresent()) {
            if (!budgetExpenseEntity.get().getCategory().getId().equals(dto.getCategory().getId())) {
                Optional<Category> categoryEntity = CategoryHandler.getInstance().getById(dto.getCategory().getId());
                budgetExpenseEntity.get().setCategory(categoryEntity.get());
            }
        }

        budgetExpenseEntity.get().setEstimatedsum(dto.getEstimatedsum());

        BudgetExpense merge = super.getEntityManager().merge(budgetExpenseEntity.get());

        super.commitTransaction();

        return Optional.ofNullable(BudgetExpenseMapper.INSTANCE.mapToBudgetExpenseDto(merge));

    }

    public Optional<Boolean> deleteBudgetExpense(BudgetExpenseDto dto) {
        return super.executeTransactionBoolean(() -> super.delete(dto.getId()));
    }

    public Optional<BudgetExpenseDto> upsertBudgetExpense(BudgetExpenseDto dto) {
        if (dto.getId() == null) {
            return createBudgetExpense(dto);
        } else {
            return updateBudgetExpense(dto);
        }
    }

    public Optional<List<BudgetExpenseDto>> getBudgetExpenseByMonthAndCategory(YearMonth yearMonth, CategoryTypeEnum category) {
        try {
            this.startTransaction();
            Query query = this.getEntityManager().createNamedQuery(EntityQueries.FIND_BUDGET_EXPENSE_BY_YEARMONTH_AND_CATEGORY);
            query.setParameter("month", yearMonth.getMonth());
            query.setParameter("year", yearMonth.getYear());
            query.setParameter("categoryType", category);
            List<BudgetExpense> budget = query.getResultList();

            if (budget != null) {
                return Optional.ofNullable(BudgetExpenseMapper.INSTANCE.mapToBudgetExpenseDtoList(budget));
            }
        } catch (NoResultException ex) {
            log.debug("No result for query when getting Budget", ex);
        } finally {
            this.commitTransaction();
        }
        return Optional.empty();
    }

    public Optional<List<BudgetExpenseDto>> getBudgetExpenseByYearMonth(YearMonth yearMonth) {
        try {
            this.startTransaction();
            Query query = this.getEntityManager().createNamedQuery(EntityQueries.FIND_BUDGET_EXPENSE_BY_YEARMONTH);
            query.setParameter("month", yearMonth.getMonth());
            query.setParameter("year", yearMonth.getYear());
            List<BudgetExpense> budget = query.getResultList();

            if (budget != null) {
                return Optional.ofNullable(BudgetExpenseMapper.INSTANCE.mapToBudgetExpenseDtoList(budget));
            }
        } catch (NoResultException ex) {
            log.debug("No result for query when getting Budget", ex);
        } finally {
            this.commitTransaction();
        }
        return Optional.empty();
    }

    public Optional<List<BudgetExpenseDto>> getBudgetExpenseByQuarter(BudgetQuarterEnum quarter, Year year) {
        try {
            this.startTransaction();
            Query query = this.getEntityManager().createNamedQuery(EntityQueries.FIND_BUDGET_EXPENSE_BY_QUARTER);
            query.setParameter("months", quarter.months());
            query.setParameter("year", year.getValue());
            List<BudgetExpense> budget = query.getResultList();

            if (budget != null) {
                return Optional.ofNullable(BudgetExpenseMapper.INSTANCE.mapToBudgetExpenseDtoList(budget));
            }
        } catch (NoResultException ex) {
            log.debug("No result for query when getting Budget", ex);
        } finally {
            this.commitTransaction();
        }
        return Optional.empty();
    }

    public Optional<Map<CategoryTypeEnum, List<BudgetExpenseDto>>> getFilteredBudgetExpenseByYearAndMonth(YearMonth yearMonth) {
        return getBudgetExpenseByYearMonth(yearMonth).map(expenseList -> {
            return BudgetUtils.getCategoryFilteredBudgetExpense(new HashSet<>(expenseList));
        });
    }

    public Optional<Map<CategoryTypeEnum, List<BudgetExpenseDto>>> getFilteredBudgetExpenseByQuarter(BudgetQuarterEnum quarter, Year year) {
        return getBudgetExpenseByQuarter(quarter, year).map(expenseList -> {
            return BudgetUtils.getCategoryFilteredBudgetExpense(new HashSet<>(expenseList));
        });
    }

}

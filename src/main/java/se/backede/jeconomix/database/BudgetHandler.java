/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import com.negod.generics.persistence.exception.ConstraintException;
import com.negod.generics.persistence.exception.DaoException;
import com.negod.generics.persistence.mapper.DtoEntityBaseMapper;
import java.time.YearMonth;
import java.util.Optional;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.constants.EntityQueries;
import se.backede.jeconomix.database.dao.BudgetDao;
import se.backede.jeconomix.database.entity.budget.Budget;
import se.backede.jeconomix.dto.budget.BudgetDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class BudgetHandler {

    BudgetDao dao = new BudgetDao();
    DtoEntityBaseMapper<BudgetDto, Budget> mapper = new DtoEntityBaseMapper(BudgetDto.class, Budget.class);

    private static final BudgetHandler INSTANCE = new BudgetHandler();

    protected BudgetHandler() {
    }

    public static final BudgetHandler getInstance() {
        return INSTANCE;
    }

    public Optional<BudgetDto> createBudget(BudgetDto budget) {
        Optional<Budget> mapFromDtoToEntity = mapper.mapFromDtoToEntity(budget);
        if (mapFromDtoToEntity.isPresent()) {
            dao.startTransaction();
            Optional<Budget> persist = dao.persist(mapFromDtoToEntity.get());
            dao.commitTransaction();
            return mapper.mapFromEntityToDto(persist.get());
        }
        return Optional.empty();
    }

    public Optional<BudgetDto> getBudget(YearMonth yearMonth) {
        try {
            dao.startTransaction();
            Query query = dao.getEntityManager().createNamedQuery(EntityQueries.FIND_BUDGET_BY_YEAR_AND_MONTH);
            query.setParameter("month", yearMonth.getMonth());
            query.setParameter("year", yearMonth.getYear());
            Budget budget = (Budget) query.getSingleResult();

            if (budget != null) {
                return mapper.mapFromEntityToDto(budget);
            }
        } catch (NoResultException ex) {
            log.debug("No result for query when getting Budget", ex);
        } finally {
            dao.commitTransaction();
        }
        return Optional.empty();
    }

}

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
import java.time.Month;
import java.util.Optional;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.constants.EntityQueries;
import se.backede.jeconomix.database.dao.BudgetDao;
import se.backede.jeconomix.database.entity.budget.Budget;
import se.backede.jeconomix.database.entity.budget.Budget_;
import se.backede.jeconomix.dto.budget.BudgetDto;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;

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
            try {
                dao.startTransaction();
                Optional<Budget> persist = dao.persist(mapFromDtoToEntity.get());
                dao.commitTransaction();
                return mapper.mapFromEntityToDto(persist.get());
            } catch (DaoException | ConstraintException ex) {
                log.error("Error when persisting Budget");
            }
        }
        return Optional.empty();
    }

    public Optional<BudgetDto> getBudget(Month month, Integer year) {
        try {
            dao.startTransaction();
            Query query = dao.getEntityManager().createNamedQuery(EntityQueries.FIND_BUDGET_BY_YEAR_AND_MONTH);
            query.setParameter("month", month);
            query.setParameter("year", year);
            Budget budget = (Budget) query.getSingleResult();

            if (budget != null) {
                return mapper.mapFromEntityToDto(budget);
            }
        } catch (NoResultException ex) {
            log.info("No result for query when getting Budget");
        } finally {
            dao.commitTransaction();
        }
        return Optional.empty();
    }

}

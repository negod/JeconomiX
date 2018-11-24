package se.backede.jeconomix.database;

import se.backede.generics.persistence.mapper.DtoEntityBaseMapper;
import java.time.YearMonth;
import java.util.List;
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
public class BudgetHandler extends BudgetDao {

    DtoEntityBaseMapper<BudgetDto, Budget> MAPPER = new DtoEntityBaseMapper<>(BudgetDto.class, Budget.class);

    private static final BudgetHandler INSTANCE = new BudgetHandler();

    protected BudgetHandler() {
    }

    public static final BudgetHandler getInstance() {
        return INSTANCE;
    }

    public Optional<BudgetDto> createBudget(BudgetDto budget) {
        return MAPPER.mapFromDtoToEntity(budget).map(budgetEntity -> {
            return super.executeTransaction(() -> super.persist(budgetEntity)).map(persisted -> {
                return MAPPER.mapFromEntityToDto(persisted).get();
            });
        }).orElse(Optional.empty());
    }

    public Optional<List<BudgetDto>> getAllAsDto() {
        return this.getAll().map(budgets -> {
            return MAPPER.mapToDtoList(budgets);
        }).orElse(Optional.empty());
    }

    public Optional<BudgetDto> getBudget(YearMonth yearMonth) {
        try {
            this.startTransaction();
            Query query = this.getEntityManager().createNamedQuery(EntityQueries.FIND_BUDGET_BY_YEAR_AND_MONTH);
            query.setParameter("month", yearMonth.getMonth());
            query.setParameter("year", yearMonth.getYear());
            Budget budget = (Budget) query.getSingleResult();

            if (budget != null) {
                return MAPPER.mapFromEntityToDto(budget);
            }
        } catch (NoResultException ex) {
            log.debug("No result for query when getting Budget", ex);
        } finally {
            this.commitTransaction();
        }
        return Optional.empty();
    }

}

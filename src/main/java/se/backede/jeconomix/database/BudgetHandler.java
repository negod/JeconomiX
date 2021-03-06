package se.backede.jeconomix.database;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.constants.BudgetQuarterEnum;
import se.backede.jeconomix.constants.EntityQueries;
import se.backede.jeconomix.database.dao.BudgetDao;
import se.backede.jeconomix.database.entity.budget.Budget;
import se.backede.jeconomix.dto.budget.BudgetCalculationDto;
import se.backede.jeconomix.dto.budget.BudgetDto;
import se.backede.jeconomix.dto.mappers.BudgetMapper;
import se.backede.jeconomix.utils.BudgetUtils;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class BudgetHandler extends BudgetDao {

    private static final BudgetHandler INSTANCE = new BudgetHandler();

    protected BudgetHandler() {
    }

    public static final BudgetHandler getInstance() {
        return INSTANCE;
    }

    public Optional<Map<Month, BudgetCalculationDto>> createBudgetQuarter(BudgetQuarterEnum quarter, Year year) {

        List<BudgetDto> budgetList = new ArrayList<>();

        for (Month month : quarter.months()) {
            BudgetDto budget = new BudgetDto();
            budget.setMonth(month);
            budget.setYear(year.getValue());

            createBudget(budget).ifPresent(budgetDto -> {
                budgetList.add(budgetDto);
            });
        }

        return BudgetUtils.getBudgetFilteredByMonth(budgetList).map(filteredByMonth -> {
            return BudgetUtils.getCalculatedBudgetsByMonth(filteredByMonth);
        }).get();

    }

    public Optional<BudgetDto> createBudget(BudgetDto budget) {
        Budget mapToBudget = BudgetMapper.INSTANCE.mapToBudget(budget);
        return super.executeTransaction(() -> super.persist(mapToBudget)).map(persisted -> {
            return BudgetMapper.INSTANCE.mapToBudgetDto(persisted);
        });
    }

    public Optional<List<BudgetDto>> getAllAsDto() {
        return this.getAll().map(budgets -> {
            return BudgetMapper.INSTANCE.mapToBudgetDtoList(budgets);
        });
    }

    public Optional<BudgetDto> getBudgetByYear(YearMonth yearMonth) {

        try {
            this.startTransaction();
            Query query = this.getEntityManager().createNamedQuery(EntityQueries.FIND_BUDGET_BY_YEAR_AND_MONTH);
            query.setParameter("month", yearMonth.getMonth());
            query.setParameter("year", yearMonth.getYear());
            Budget budget = (Budget) query.getSingleResult();

            if (budget != null) {
                return Optional.ofNullable(BudgetMapper.INSTANCE.mapToBudgetDto(budget));
            }
        } catch (NoResultException ex) {
            log.debug("No result for query when getting Budget", ex);
        } finally {
            this.commitTransaction();
        }
        return Optional.empty();

    }

    public Optional<List<BudgetDto>> getBudgetByQuarter(Month[] months, Year year) {

        try {

            this.startTransaction();
            Query query = this.getEntityManager().createNamedQuery(EntityQueries.FIND_BUDGET_BY_QARTER);
            query.setParameter("months", Arrays.asList(months));
            query.setParameter("year", year.getValue());
            List<Budget> budget = (List<Budget>) query.getResultList();

            if (budget != null && !budget.isEmpty()) {
                return Optional.ofNullable(BudgetMapper.INSTANCE.mapToBudgetDtoList(budget));
            }

        } catch (NoResultException ex) {
            log.debug("No result for query when getting Budget", ex);
        } finally {
            this.commitTransaction();
        }
        return Optional.empty();
    }

    public Optional<Map<Month, BudgetCalculationDto>> getCalculatedBudgetByQuarter(BudgetQuarterEnum quarter, Year year) {
        return getCalculatedBudgetByMonths(year, quarter.months());
    }

    public Optional<Map<Month, BudgetCalculationDto>> getCalculatedBudgetByMonths(Year year, Month... months) {
        return getBudgetByQuarter(months, year).map(budgetList -> {
            return BudgetUtils.getBudgetFilteredByMonth(budgetList).map(filteredByMonth -> {
                return BudgetUtils.getCalculatedBudgetsByMonth(filteredByMonth);
            }).get();
        }).orElse(Optional.empty());
    }

}

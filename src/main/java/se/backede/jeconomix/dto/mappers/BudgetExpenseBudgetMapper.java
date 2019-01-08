/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import se.backede.jeconomix.database.entity.budget.Budget;
import se.backede.jeconomix.dto.budget.BudgetDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Mapper
public interface BudgetExpenseBudgetMapper {

    BudgetExpenseBudgetMapper INSTANCE = Mappers.getMapper(BudgetExpenseBudgetMapper.class);

    @Mappings({
        @Mapping(target = "budgetExpenseSet", ignore = true)
    })
    Budget mapToBudget(BudgetDto budget);

    @Mappings({
        @Mapping(target = "budgetExpenseSet", ignore = true)
    })
    BudgetDto mapToBudgetDto(Budget budget);

    List<Budget> mapToBudgetList(List<BudgetDto> budget);

    List<BudgetDto> mapToBudgetDtoList(List<Budget> budgets);

}

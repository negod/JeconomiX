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
import se.backede.jeconomix.database.entity.budget.BudgetExpense;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Mapper(uses = {BudgetExpenseBudgetMapper.class, CategoryMapper.class})
public interface BudgetExpenseMapper {

    BudgetExpenseMapper INSTANCE = Mappers.getMapper(BudgetExpenseMapper.class);

    BudgetExpense mapToBudgetExpense(BudgetExpenseDto budgetExpense);

    @Mappings({
        @Mapping(target = "budget", ignore = true)
    })
    BudgetExpenseDto mapToBudgetExpenseDto(BudgetExpense budgetExpense);

    List<BudgetExpense> mapToBudgetExpenseList(List<BudgetExpenseDto> budgetExpenses);

    List<BudgetExpenseDto> mapToBudgetExpenseDtoList(List<BudgetExpense> budgetExpenses);

}

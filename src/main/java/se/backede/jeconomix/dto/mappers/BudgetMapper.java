/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import se.backede.jeconomix.database.entity.budget.Budget;
import se.backede.jeconomix.dto.budget.BudgetDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Mapper(uses = {BudgetExpenseMapper.class})
public interface BudgetMapper {

    BudgetMapper INSTANCE = Mappers.getMapper(BudgetMapper.class);

    Budget mapToBudget(BudgetDto budget);

    BudgetDto mapToBudgetDto(Budget budget);

    List<Budget> mapToBudgetList(List<BudgetDto> budget);

    List<BudgetDto> mapToBudgetDtoList(List<Budget> budgets);

}

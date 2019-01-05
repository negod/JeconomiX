/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.event.events.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Builder
@Getter
@EqualsAndHashCode
public class BudgetExpenseEventDto {

    BudgetEventDto budgetEvent;
    BudgetExpenseDto budgetExpense;

}

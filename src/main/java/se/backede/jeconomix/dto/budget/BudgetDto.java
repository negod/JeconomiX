/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.budget;

import se.backede.generics.persistence.dto.GenericDto;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Setter
public class BudgetDto extends GenericDto {

    private static final long serialVersionUID = 1L;

    private int year;
    private Month month;
    private Set<BudgetExpenseDto> budgetExpenseSet = new HashSet<>();

}

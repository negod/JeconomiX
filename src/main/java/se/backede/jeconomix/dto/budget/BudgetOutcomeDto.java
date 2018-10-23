/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.budget;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import se.backede.jeconomix.constants.CategoryTypeEnum;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = {"diff", "budget", "outcome"})
public class BudgetOutcomeDto {

    BigDecimal outcome;
    BigDecimal budget;
    BigDecimal diff;
    String category;
    CategoryTypeEnum categoryType;

}

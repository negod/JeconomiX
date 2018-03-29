/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.budget;

import com.negod.generics.persistence.dto.GenericDto;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import se.backede.jeconomix.dto.CategoryDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Setter
public class BudgetCategoryDto extends GenericDto {

    private String name;
    private BigDecimal estimatedSum;
    private CategoryDto billCategory;

}

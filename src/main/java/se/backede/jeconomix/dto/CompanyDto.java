/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto;

import com.negod.generics.persistence.dto.GenericDto;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.annotations.Indexed;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Setter
public class CompanyDto extends GenericDto {

    private String name;

    private ExpenseCategoryDto expenseCategory;

    private BillCategoryDto billCategory;

    private CompanyTypeDto companyType;

    private Set<TransactionDto> transactions = new HashSet<>();

}

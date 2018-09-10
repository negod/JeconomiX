/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto;

import com.negod.generics.persistence.dto.GenericDto;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import se.backede.jeconomix.annotations.ComboBoxField;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Setter
@ToString(exclude = "companies")
@EqualsAndHashCode(exclude = "companies")
public class CategoryDto extends GenericDto implements Comparable<CategoryDto> {

    private static final long serialVersionUID = 1L;

    @ComboBoxField
    private String name;
    private Set<CompanyDto> companies;
    private CategoryTypeDto categoryType;

    @Override
    public int compareTo(CategoryDto company) {
        return name.compareTo(company.name);
    }

}

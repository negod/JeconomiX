/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto;

import com.negod.generics.persistence.dto.GenericDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import se.backede.jeconomix.constants.CategoryTypeEnum;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Setter
@ToString
public class CategoryTypeDto extends GenericDto {

    private CategoryTypeEnum type;

    public CategoryTypeDto() {
    }

}

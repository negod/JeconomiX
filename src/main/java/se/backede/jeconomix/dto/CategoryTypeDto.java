/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto;

import com.negod.generics.persistence.dto.GenericDto;
import lombok.Getter;
import lombok.Setter;
import se.backede.jeconomix.constants.CategoryTypeType;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Setter
public class CategoryTypeDto extends GenericDto {

    private CategoryTypeType type;

}

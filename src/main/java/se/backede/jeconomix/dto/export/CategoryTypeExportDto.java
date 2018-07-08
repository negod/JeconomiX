/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.export;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import se.backede.jeconomix.constants.CategoryTypeEnum;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Setter
@XmlRootElement
public class CategoryTypeExportDto {

    String id;
    private CategoryTypeEnum type;

    public CategoryTypeExportDto() {
    }

    public CategoryTypeExportDto(String id, CategoryTypeEnum type) {
        this.id = id;
        this.type = type;
    }

}

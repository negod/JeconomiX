/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto;

import javax.xml.bind.annotation.XmlRootElement;
import se.backede.generics.persistence.dto.GenericDto;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Setter
@XmlRootElement(name = "subCompany")
public class CompanyAccociationDto extends GenericDto {

    private String name;
    private String originalName;
    private CompanyDto company;

    public CompanyAccociationDto() {
    }

    public CompanyAccociationDto(String name) {
        this.name = name;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.export;

import java.util.Date;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import se.backede.jeconomix.dto.CompanyAccociationDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Setter
@XmlRootElement(name = "company")
public class CompanyExportDto {

    private String id;
    private Date updatedDate;
    private String name;
    private String category;
    private String originalName;
    private Set<CompanyAccociationExportDto> accociations;

}

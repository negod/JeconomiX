/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto;

import se.backede.generics.persistence.dto.GenericDto;
import java.util.HashSet;
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
@EqualsAndHashCode(exclude = {"transactions", "accociations"})
@ToString(exclude = {"transactions", "accociations"})
public class CompanyDto extends GenericDto implements Comparable<CompanyDto> {

    @ComboBoxField
    private String name;
    private String originalName;
    private CategoryDto category;
    private Set<TransactionDto> transactions = new HashSet<>();
    private Set<CompanyAccociationDto> accociations = new HashSet<>();

    public CompanyDto() {
    }

    public CompanyDto(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(CompanyDto company) {
        return name.compareTo(company.name);
    }

}

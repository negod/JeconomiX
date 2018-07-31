/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto;

import com.negod.generics.persistence.dto.GenericDto;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Setter
public class CompanyDto extends GenericDto {

    private String name;

    private CategoryDto category;

    private Set<TransactionDto> transactions = new HashSet<>();

    private Set<CompanyAccociationDto> accociations = new HashSet<>();

    private Optional<String> newlyAcciciatedCompanyName = Optional.empty();

    public CompanyDto() {
    }

    public CompanyDto(String name) {
        this.name = name;
    }

}

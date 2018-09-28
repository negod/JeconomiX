/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto;

import se.backede.generics.persistence.dto.GenericDto;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Month;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = {"company", "ascociatedCompany"})
@ToString(exclude = {"company", "ascociatedCompany"})
public class TransactionDto extends GenericDto {

    private static final long serialVersionUID = 1L;

    private Date transDate;

    private BigDecimal sum;

    private BigDecimal saldo;

    private CompanyDto company;

    private Month budgetMonth;

    private Integer budgetYear;

    private String originalValue;

    private CompanyAccociationDto ascociatedCompany;

}

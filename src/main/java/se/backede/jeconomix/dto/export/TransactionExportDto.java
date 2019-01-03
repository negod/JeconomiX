/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.export;

import se.backede.generics.persistence.dto.GenericDto;
import java.math.BigDecimal;
import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Setter
public class TransactionExportDto extends GenericDto {

    private Date transDate;
    private BigDecimal sum;
    private BigDecimal saldo;
    private String company;
    private String transactionType;

}

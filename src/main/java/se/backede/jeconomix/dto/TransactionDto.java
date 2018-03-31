/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto;

import com.negod.generics.persistence.dto.GenericDto;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import se.backede.jeconomix.database.entity.Transaction;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Setter
public class TransactionDto extends GenericDto {

    private Date transDate;

    private BigDecimal sum;

    private BigDecimal saldo;

    private CompanyDto company;

    private CategoryTypeDto transactionType;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.transDate);
        hash = 71 * hash + Objects.hashCode(this.sum);
        hash = 71 * hash + Objects.hashCode(this.saldo);
        return hash;
    }

    public boolean equals(Transaction obj) {
        if (obj == null) {
            return false;
        }
        final Transaction other = (Transaction) obj;
        if (!Objects.equals(this.transDate, other.getTransDate())) {
            return false;
        }
        if (!Objects.equals(this.sum, other.getSum())) {
            return false;
        }
        if (!Objects.equals(this.saldo, other.getSaldo())) {
            return false;
        }
        return true;
    }

}

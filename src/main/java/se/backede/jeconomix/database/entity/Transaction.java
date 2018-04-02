/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.entity;

import com.negod.generics.persistence.entity.GenericEntity;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Month;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import se.backede.jeconomix.constants.EntityConstants;
import se.backede.jeconomix.constants.EntityQueries;
import se.backede.jeconomix.dto.TransactionDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Table(name = EntityConstants.TRANSACTION)
@Entity
@Getter
@Setter
@NamedQuery(name = EntityQueries.TRANSACTION_EXISTS, query = "select t from Transaction t where t.company =:company  AND t.transDate =:date and t.saldo=:saldo and t.sum=:sum")
public class Transaction extends GenericEntity {

    private Date transDate;

    private BigDecimal sum;

    private BigDecimal saldo;

    @Enumerated(EnumType.STRING)
    private Month budgetMonth;

    private Integer budgetYear;

    @ManyToOne
    @JoinColumn(name = "company", referencedColumnName = "id", insertable = true)
    private Company company;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.transDate);
        hash = 59 * hash + Objects.hashCode(this.sum);
        hash = 59 * hash + Objects.hashCode(this.saldo);
        return hash;
    }

    public boolean equals(TransactionDto obj) {
        if (obj == null) {
            return false;
        }
        final TransactionDto other = (TransactionDto) obj;
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

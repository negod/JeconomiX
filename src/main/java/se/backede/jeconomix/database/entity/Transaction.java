/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.entity;

import com.negod.generics.persistence.entity.GenericEntity;
import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import se.backede.jeconomix.constants.EntityConstants;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Table(name = EntityConstants.TRANSACTION)
@Entity
@Getter
@Setter
public class Transaction extends GenericEntity {

    private Date transDate;

    private BigDecimal sum;

    private BigDecimal saldo;

    @ManyToOne
    @JoinColumn(name = "company", referencedColumnName = "id", insertable = true)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "transaction_type", referencedColumnName = "id")
    private TransactionType transactionType;

}

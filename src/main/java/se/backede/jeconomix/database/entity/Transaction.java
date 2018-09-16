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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
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
@NamedQuery(name = EntityQueries.TRANSACTION_EXISTS, query = "select t from Transaction t where t.company =:company  AND t.transDate =:date and t.saldo=:saldo and t.sum=:sum and t.originalValue=:originalValue")
@EqualsAndHashCode(exclude = {"company", "ascociatedCompany"})
public class Transaction extends GenericEntity {

    private Date transDate;

    private BigDecimal sum;

    private BigDecimal saldo;

    private String originalValue;

    @Enumerated(EnumType.STRING)
    private Month budgetMonth;

    private Integer budgetYear;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "company", referencedColumnName = "id", insertable = true)
    private Company company;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ascociatedCompany", referencedColumnName = "id", insertable = true)
    private CompanyAccociation ascociatedCompany;

}

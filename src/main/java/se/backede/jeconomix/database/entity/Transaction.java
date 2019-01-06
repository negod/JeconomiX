/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.entity;

import se.backede.generics.persistence.entity.GenericEntity;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Month;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import se.backede.jeconomix.constants.EntityConstants;
import se.backede.jeconomix.constants.EntityQueries;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Table(name = EntityConstants.TRANSACTION)
@Entity
@Getter
@Setter
@NamedQueries({
    @NamedQuery(name = EntityQueries.TRANSACTION_EXISTS, query = "select t from Transaction t where t.company =:company  AND t.transDate =:date and t.saldo=:saldo and t.sum=:sum and t.originalValue=:originalValue"),
    @NamedQuery(name = EntityQueries.TRANSACTION_BY_BUDGETMONTH, query = "select t from Transaction t where t.budgetMonth =:budgetMonth AND t.budgetYear =:budgetYear"),
    @NamedQuery(name = EntityQueries.TRANSACTION_BY_BUDGETMONTH_AND_CATEGORY, query = "select t from Transaction t where t.budgetMonth =:budgetMonth AND t.budgetYear =:budgetYear AND t.company.category.categoryType.type =:categoryType"),
    @NamedQuery(name = EntityQueries.TRANSACTION_BY_QUARTER, query = "select t from Transaction t where t.budgetYear =:budgetYear AND t.budgetMonth IN :budgetMonths")
})
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

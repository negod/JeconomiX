/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.entity.budget;

import se.backede.generics.persistence.entity.GenericEntity;
import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import se.backede.jeconomix.constants.EntityQueries;
import se.backede.jeconomix.database.entity.Category;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Entity
@Table(name = "BUDGET_EXPENSE")
@XmlRootElement
@Getter
@Setter
@NamedQueries({
    @NamedQuery(name = EntityQueries.FIND_BUDGET_EXPENSE_BY_YEARMONTH_AND_CATEGORY, query = "SELECT b FROM BudgetExpense b where b.budget.year = :year AND b.budget.month = :month AND b.category.categoryType.type =:categoryType")
})
public class BudgetExpense extends GenericEntity {

    @Column(name = "ESTIMATEDSUM")
    private BigDecimal estimatedsum;

    @JoinColumn(name = "BUDGET", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Budget budget;

    @JoinColumn(name = "CATEGORY", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Category category;

    public BudgetExpense() {
    }

}

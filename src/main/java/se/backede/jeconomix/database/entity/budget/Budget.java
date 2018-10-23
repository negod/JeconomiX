/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.entity.budget;

import se.backede.generics.persistence.entity.GenericEntity;
import java.time.Month;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import se.backede.jeconomix.constants.EntityQueries;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Entity
@Table(name = "BUDGET")
@XmlRootElement
@Getter
@Setter
@NamedQueries({
    @NamedQuery(name = EntityQueries.FIND_BUDGET_BY_YEAR_AND_MONTH, query = "SELECT b FROM Budget b where b.year = :year AND b.month = :month")})
public class Budget extends GenericEntity {

    @Column(name = "YEAR")
    private int year;

    @Enumerated(EnumType.STRING)
    private Month month;

    @OneToMany(mappedBy = "budget", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<BudgetExpense> budgetExpenseSet;

    public Budget() {
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.entity;

import com.negod.generics.persistence.entity.GenericEntity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import se.backede.jeconomix.constants.EntityConstants;
import se.backede.jeconomix.constants.EntityQueries;
import se.backede.jeconomix.database.entity.budget.BudgetExpense;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Table(name = EntityConstants.CATEGORY)
@Entity
@Getter
@Setter
@NamedQueries({
    @NamedQuery(name = EntityQueries.FILTERED_CATEGORIES_BY_YEAR, query = "select t from Category t left join t.companies c left join c.transactions tr where t.categoryType.type IN :type and tr.budgetYear =:year group by t.id"),
    @NamedQuery(name = EntityQueries.FILTERED_CATEGORIES, query = "select t from Category t where t.categoryType.type IN :type")
})

public class Category extends GenericEntity {

    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Company> companies;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_type", referencedColumnName = "id")
    private CategoryType categoryType;

    @OneToMany(mappedBy = "category", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<BudgetExpense> budgetExpenseSet;

}

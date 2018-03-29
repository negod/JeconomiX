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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import se.backede.jeconomix.constants.EntityConstants;
import se.backede.jeconomix.database.entity.budget.BudgetCategory;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Table(name = EntityConstants.CATEGORY)
@Entity
@Getter
@Setter
public class Category extends GenericEntity {

    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Company> company;

    @ManyToOne
    @JoinColumn(name = "category_type", referencedColumnName = "id")
    private CategoryType categoryType;

//    @JoinColumn(name = "BUDGET_BILL", referencedColumnName = "ID")
//    @OneToOne(fetch = FetchType.EAGER)
//    private BudgetCategory budgetBill;

}

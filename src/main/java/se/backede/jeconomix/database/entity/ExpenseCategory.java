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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import se.backede.jeconomix.constants.EntityConstants;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Table(name = EntityConstants.EXPENSE_CATEGORY)
@Entity
@Getter
@Setter
public class ExpenseCategory extends GenericEntity {

    private String name;

    @OneToMany(mappedBy = "expenseCategory", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Company> company;

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.entity.budget;

import com.negod.generics.persistence.entity.GenericEntity;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import se.backede.jeconomix.database.entity.Category;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Entity
@Table(name = "BUDGET_BILL")
@XmlRootElement
@Getter
@Setter
public class BudgetCategory extends GenericEntity {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "NAME")
    private String name;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTIMATEDSUM")
    private BigDecimal estimatedSum;

    @OneToMany(mappedBy = "budgetBill", fetch = FetchType.EAGER)
    private Set<BudgetBudgetCategory> budgetBudgetBillSet;

    @OneToMany(mappedBy = "budgetBill", fetch = FetchType.EAGER)
    private Set<Category> billCategory;

}

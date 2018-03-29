/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.entity.budget;

import com.negod.generics.persistence.entity.GenericEntity;
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

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Entity
@Table(name = "BUDGET")
@XmlRootElement
@Getter
@Setter
public class Budget extends GenericEntity {

    @Basic(optional = false)
    @NotNull
    @Column(name = "YEAR")
    private int year;

    @Size(max = 8)
    @Column(name = "MONTH")
    private String month;

    @OneToMany(mappedBy = "budget", fetch = FetchType.EAGER)
    private Set<BudgetBudgetCategory> budgetBills;

}

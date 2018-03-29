/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.entity.budget;

import com.negod.generics.persistence.entity.GenericEntity;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "BUDGET_BUDGET_BILL")
@XmlRootElement
@Getter
@Setter
public class BudgetBudgetCategory extends GenericEntity {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "NAME")
    private String name;

    @JoinColumn(name = "BUDGET", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Budget budget;

    @JoinColumn(name = "BUDGET_BILL", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private BudgetCategory budgetBill;

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.DB.Entities.budget;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.negod.DB.Entities.userhandling.Users;

/**
 *
 * @author Joakim
 */
@Entity
@Table(name = "BudgetRow")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BudgetRow.findAll", query = "SELECT g FROM BudgetRow g"),
    @NamedQuery(name = "BudgetRow.findByUser", query = "SELECT g FROM BudgetRow g WHERE g.users = :user"),
    @NamedQuery(name = "BudgetRow.findByCompanyAndUser", query = "SELECT g FROM BudgetRow g WHERE g.company = :company AND g.users.username = :users")})
public class BudgetRow implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdBudgetRow;
    @JoinColumn(name = "company")
    @ManyToOne
    private Company company;
    @Column(name = "totalSum")
    @NotNull
    private Double totalSum;
    @Column(name = "other", length = 256)
    private String other;
    @JoinColumn(name = "users")
    @ManyToOne
    private Users users;
    @JoinColumn(name = "transactionType")
    @ManyToOne
    private TransactionType transactionType;

    public Long getIdBudgetRow() {
        return IdBudgetRow;
    }

    public void setIdBudgetRow(Long IdBudgetRow) {
        this.IdBudgetRow = IdBudgetRow;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (IdBudgetRow != null ? IdBudgetRow.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BudgetRow)) {
            return false;
        }
        BudgetRow other = (BudgetRow) object;
        if ((this.IdBudgetRow == null && other.IdBudgetRow != null) || (this.IdBudgetRow != null && !this.IdBudgetRow.equals(other.IdBudgetRow))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.negod.DB.Entities.budget.BudgetRow[ id=" + IdBudgetRow + " ]";
    }
}

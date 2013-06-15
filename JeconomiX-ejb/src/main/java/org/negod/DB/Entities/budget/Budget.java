/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.DB.Entities.budget;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.ConversionValue;
import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.ObjectTypeConverter;
import org.negod.DB.Entities.userhandling.Users;
import org.negod.DB.Enums.MonthEnum;

/**
 *
 * @author Joakim
 */
@Entity
@Table(name = "Budget")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Budget.findAll", query = "SELECT g FROM Budget g"),
    @NamedQuery(name = "Budget.findByUser", query = "SELECT g FROM Budget g WHERE g.users = :user"),
    @NamedQuery(name = "Budget.findByMonthAndUser", query = "SELECT g FROM Budget g WHERE g.monthInYear = :month AND g.users.username = :user"),
    @NamedQuery(name = "Budget.findByYearAndUser", query = "SELECT g FROM Budget g WHERE g.budgerYear = :year AND g.users.username = :user"),
    @NamedQuery(name = "Budget.findByYearAndMonth", query = "SELECT g FROM Budget g WHERE g.monthInYear = :month ANd g.budgerYear = :year AND g.users.username = :user")})
@ObjectTypeConverter(name = "month", objectType = MonthEnum.class, dataType = String.class, conversionValues = {
    @ConversionValue(objectValue = "January", dataValue = "JAN"),
    @ConversionValue(objectValue = "February", dataValue = "FEB"),
    @ConversionValue(objectValue = "March", dataValue = "MAR"),
    @ConversionValue(objectValue = "April", dataValue = "APR"),
    @ConversionValue(objectValue = "May", dataValue = "MAY"),
    @ConversionValue(objectValue = "June", dataValue = "JUN"),
    @ConversionValue(objectValue = "July", dataValue = "JUL"),
    @ConversionValue(objectValue = "August", dataValue = "AUG"),
    @ConversionValue(objectValue = "September", dataValue = "SEP"),
    @ConversionValue(objectValue = "October", dataValue = "OCT"),
    @ConversionValue(objectValue = "November", dataValue = "NOV"),
    @ConversionValue(objectValue = "December", dataValue = "DEC")})
public class Budget implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "users")
    @ManyToOne
    private Users users;
    @NotNull
    @Basic
    @Convert("month")
    private MonthEnum monthInYear;
    @Column(name = "budgetyear")
    @NotNull
    //@Pattern(regexp = "^[12][0-9]{3}$")
    private Long budgerYear;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @OneToMany
    private List<BudgetRow> budgetRow;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BudgetRow> getBudgetRow() {
        return budgetRow;
    }

    public void setBudgetRow(List<BudgetRow> budgetRow) {
        this.budgetRow = budgetRow;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public MonthEnum getMonthInYear() {
        return monthInYear;
    }

    public void setMonthInYear(MonthEnum monthInYear) {
        this.monthInYear = monthInYear;
    }

    public Long getBudgetYear() {
        return budgerYear;
    }

    public void getBudgetYear(Long year) {
        this.budgerYear = year;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Budget)) {
            return false;
        }
        Budget other = (Budget) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.negod.DB.Entities.budget.Budget[ id=" + id + " ]";
    }
}

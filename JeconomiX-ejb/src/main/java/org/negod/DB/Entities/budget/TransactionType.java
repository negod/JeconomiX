/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.DB.Entities.budget;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.ConversionValue;
import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.ObjectTypeConverter;
import org.negod.DB.Entities.userhandling.Users;
import org.negod.DB.Enums.TransactionTypeEnum;

/**
 *
 * @author Joakim
 */
@Entity
@Table(name = "TransactionType")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionType.findAll", query = "SELECT g FROM TransactionType g"),
    @NamedQuery(name = "TransactionType.findByUser", query = "SELECT g FROM TransactionType g WHERE g.user.username = :user")})
@ObjectTypeConverter(name = "transactionType", objectType = TransactionTypeEnum.class, dataType = String.class, conversionValues = {
    @ConversionValue(objectValue = "income", dataValue = "I"),
    @ConversionValue(objectValue = "expense", dataValue = "E")})
public class TransactionType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idTransactionType")
    private Long idTransactionType;
    @NotNull
    @Basic
    @Column(name="TransactionType")
    @Convert("transactionType")
    private TransactionTypeEnum transactionType;
    @NotNull
    @Column(name="TransactionName")
    private String transactionName;
    @NotNull
    @JoinColumn(name="users")
    @ManyToOne
    private Users user;

    public Long getId() {
        return idTransactionType;
    }

    public void setId(Long id) {
        this.idTransactionType = id;
    }

    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEnum tansactionType) {
        this.transactionType = tansactionType;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransactionType != null ? idTransactionType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionType)) {
            return false;
        }
        TransactionType other = (TransactionType) object;
        if ((this.idTransactionType == null && other.idTransactionType != null) || (this.idTransactionType != null && !this.idTransactionType.equals(other.idTransactionType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.negod.DB.Entities.budget.TransactionType[ id=" + idTransactionType + " ]";
    }
}

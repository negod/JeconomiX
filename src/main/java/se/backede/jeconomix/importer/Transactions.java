/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.importer;

import java.util.Optional;
import java.util.Set;
import lombok.Getter;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
public class Transactions {

    private Optional<Set<TransactionWrapper>> newTransactionsToEdit = Optional.empty();
    private Optional<Set<TransactionWrapper>> duplicateTransactions = Optional.empty();
    private Optional<Set<TransactionWrapper>> invalidTransactions = Optional.empty();
    private Optional<Set<TransactionWrapper>> validTransactionsForInsert = Optional.empty();

    public void setNewTransactionsToEdit(Set<TransactionWrapper> transactions) {
        newTransactionsToEdit = Optional.ofNullable(transactions);
    }

    public void setDuplicateTransactions(Set<TransactionWrapper> transactions) {
        duplicateTransactions = Optional.ofNullable(transactions);
    }

    public void setInvalidTransactions(Set<TransactionWrapper> transactions) {
        invalidTransactions = Optional.ofNullable(transactions);
    }

    public void setvValidTransactionsForInsert(Set<TransactionWrapper> transactions) {
        validTransactionsForInsert = Optional.ofNullable(transactions);
    }

}

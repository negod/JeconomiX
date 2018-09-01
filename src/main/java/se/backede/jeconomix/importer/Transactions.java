/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.importer;

import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
public class Transactions {

    private Set<TransactionWrapper> newTransactionsToEdit = new LinkedHashSet<>();
    private Set<TransactionWrapper> duplicateTransactions = new LinkedHashSet<>();
    private Set<TransactionWrapper> invalidTransactions = new LinkedHashSet<>();
    private Set<TransactionWrapper> validTransactionsForInsert = new LinkedHashSet<>();

}

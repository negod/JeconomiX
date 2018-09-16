/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.importer;

import com.backede.fileutils.csv.parser.CsvImporter;
import com.backede.fileutils.csv.parser.CsvRecordWrapper;
import com.backede.fileutils.csv.parser.Normalizer;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.constants.NordeaCsvFields;
import se.backede.jeconomix.database.TransactionHandler;
import se.backede.jeconomix.database.entity.extractor.TransactionExtractor;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class NordeaCsvTransactionImporter implements CsvImporter<Transactions> {

    @Override
    public Optional<List<CsvRecordWrapper>> modifyCsvRecords(Optional<Normalizer> records) {
        return records.map(handler -> {
            return handler
                    .copyValueToNewColumn(NordeaCsvFields.ORIGINAL_VALUE, NordeaCsvFields.TRANSACTION)
                    .capitalizeAll(NordeaCsvFields.TRANSACTION)
                    .removePeriod(NordeaCsvFields.BELOPP)
                    .removePeriod(NordeaCsvFields.SALDO)
                    .removeWord(NordeaCsvFields.TRANSACTION, "KORTKÖP")
                    .removeWord(NordeaCsvFields.TRANSACTION, "RESERVATION")
                    .removeWord(NordeaCsvFields.TRANSACTION, "AUTOGIRO")
                    .removeWord(NordeaCsvFields.TRANSACTION, "BETALNING BG")
                    .removeWord(NordeaCsvFields.TRANSACTION, "BETALNING PG")
                    .replacePeriodWithComma(NordeaCsvFields.BELOPP)
                    .replacePeriodWithComma(NordeaCsvFields.SALDO)
                    .extractNameFromColumn(NordeaCsvFields.TRANSACTION)
                    .build();
        }).orElse(Optional.empty());
    }

    @Override
    public Optional<Transactions> executeLogic(List<CsvRecordWrapper> records) {

        TransactionExtractor extractor = new NordeaTransactionExtractor();
        Set<TransactionWrapper> createTransactions = new LinkedHashSet<>(extractor.createTransactions(records));

        List<TransactionWrapper> duplicateTransactions = createTransactions.stream()
                .filter(transaction -> TransactionHandler.getInstance().transactionExists(transaction.getTransactionDto()))
                .collect(Collectors.toList());
        createTransactions.removeAll(duplicateTransactions);

        List<TransactionWrapper> invalidTransactions = createTransactions.stream()
                .filter(transaction -> transaction.getTransactionDto().getSum() == null)
                .collect(Collectors.toList());
        createTransactions.removeAll(invalidTransactions);

        List<TransactionWrapper> validTransactions = createTransactions.stream()
                .filter(transaction -> transaction.getTransactionDto().getCompany() != null)
                .collect(Collectors.toList());
        createTransactions.removeAll(validTransactions);

        List<TransactionWrapper> editableTransactions = createTransactions.stream()
                .filter(transaction -> transaction.getTransactionDto().getCompany() == null)
                .collect(Collectors.toList());
        createTransactions.removeAll(editableTransactions);

        if (!createTransactions.isEmpty()) {
            log.error("Some transactions are unhandeled during import AMOUNT {}", createTransactions.size());
        }

        Transactions transactions = new Transactions();
        if (!duplicateTransactions.isEmpty()) {
            transactions.setDuplicateTransactions(new HashSet<>(duplicateTransactions));
        }

        if (!invalidTransactions.isEmpty()) {
            transactions.setInvalidTransactions(new HashSet<>(invalidTransactions));
        }

        if (!validTransactions.isEmpty()) {
            transactions.setvValidTransactionsForInsert(new HashSet<>(validTransactions));
        }

        if (!editableTransactions.isEmpty()) {
            transactions.setNewTransactionsToEdit(new HashSet<>(editableTransactions));
        }

        log.error("Duplicate transactions {}", duplicateTransactions.size());
        log.error("Invalid transactions {}", invalidTransactions.size());
        log.error("Editable transactions {}", editableTransactions.size());
        log.error("Vaild transactions for insert {}", validTransactions.size());

        return Optional.ofNullable(transactions);
    }

}

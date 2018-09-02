/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.importer;

import com.backede.fileutils.csv.parser.CsvImporter;
import com.backede.fileutils.csv.parser.CsvRecordWrapper;
import com.backede.fileutils.csv.parser.Normalizer;
import java.io.IOException;
import java.util.Collection;
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
 * @param <TransactionTypes>
 */
@Slf4j
public class NordeaCsvTransactionImporter implements CsvImporter<Transactions> {

    @Override
    public Optional<List<CsvRecordWrapper>> modifyCsvRecords(Optional<Normalizer> records) throws IOException {
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

        Transactions transactions = new Transactions();
        TransactionExtractor extractor = new NordeaTransactionExtractor();

        Set<TransactionWrapper> createTransactions = new LinkedHashSet<>(extractor.createTransactions(records));

        transactions.getDuplicateTransactions().addAll(createTransactions.stream()
                .filter(transaction -> TransactionHandler.getInstance().transactionExists(transaction.getTransactionDto()))
                .collect(Collectors.toList())
        );

        transactions.getInvalidTransactions().addAll(createTransactions.stream()
                .filter(transaction -> transaction.getTransactionDto().getSum() == null)
                .collect(Collectors.toList())
        );

        transactions.getNewTransactionsToEdit().addAll(createTransactions.stream()
                .filter(transaction -> transaction.getTransactionDto().getCompany() == null)
                .collect(Collectors.toList())
        );

        createTransactions.removeAll(transactions.getDuplicateTransactions());
        createTransactions.removeAll(transactions.getValidTransactionsForInsert());
        createTransactions.removeAll(transactions.getInvalidTransactions());

        transactions.getNewTransactionsToEdit().addAll(createTransactions);

        return Optional.ofNullable(transactions);
    }
}

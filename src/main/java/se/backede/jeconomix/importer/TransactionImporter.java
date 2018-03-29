/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.importer;

import com.backede.fileutils.csv.parser.Normalizer;
import com.backede.fileutils.csv.reader.CsvReaderHandler;
import com.backede.fileutils.exception.BeckedeFileException;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.swing.JFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import se.backede.jeconomix.database.CompanyHandler;
import se.backede.jeconomix.database.TransactionHandler;
import se.backede.jeconomix.database.entity.extractor.TransactionExtractor;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.event.events.Events;
import se.backede.jeconomix.forms.importexport.ImportBill;
import se.backede.jeconomix.forms.importexport.ImportExpense;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class TransactionImporter {

    private static final TransactionImporter INSTANCE = new TransactionImporter();

    protected TransactionImporter() {
    }

    public static final TransactionImporter getInstance() {
        return INSTANCE;
    }

    public void importExpensesFromCSV(String filePath, JFrame parent) {
        new Thread(() -> {
            try {
                CsvReaderHandler handler = new CsvReaderHandler(filePath, Boolean.TRUE);
                Iterable<CSVRecord> build = new Normalizer(handler.getRecords())
                        .removePeriod("Belopp")
                        .removePeriod("Saldo")
                        .removeWord("Transaktion", "Kortköp")
                        .removeWord("Transaktion", "Reservation")
                        .removeLeadingSpaces("Transaktion")
                        .removeWordStartingWith("Transaktion", "18", 7)
                        .removeWordStartingWith("Transaktion", "17", 7)
                        .removeTrailingAndLeadingSpaces("Transaktion")
                        .replaceComma("Belopp")
                        .replaceComma("Saldo")
                        .removeMinus("Belopp")
                        .build(handler.getHeaderMap());

                Map<String, Set<TransactionDto>> companyTransactions = new LinkedHashMap<>();

                Events.getInstance().fireProgressMaxValueEvent(((Collection<?>) build).size());
                for (CSVRecord cSVRecord : build) {

                    Optional<CompanyDto> company = CompanyHandler.getInstance().getCompanyByName(cSVRecord.get("Transaktion"));

                    if (company.isPresent()) {
                        TransactionDto transaction = TransactionExtractor.createTransaction(cSVRecord);
                        company.get().getTransactions().add(transaction);
                        transaction.setCompany(company.get());
                        TransactionHandler.getInstance().createTransaction(transaction);
                    } else {

                        TransactionDto transaction = TransactionExtractor.createTransaction(cSVRecord);

                        if (companyTransactions.containsKey(cSVRecord.get("Transaktion"))) {
                            companyTransactions.get(cSVRecord.get("Transaktion")).add(transaction);
                        } else {
                            Set<TransactionDto> transactionList = new LinkedHashSet<>();
                            transactionList.add(transaction);
                            companyTransactions.put(cSVRecord.get("Transaktion"), transactionList);
                        }

                    }
                    Events.getInstance().fireProgressIncreaseValueEvent(1, cSVRecord.get("Transaktion"));
                }

                if (!companyTransactions.isEmpty()) {
                    LinkedList<CompanyDto> companiesToAdd = new LinkedList<>();
                    for (String companyName : companyTransactions.keySet()) {
                        CompanyDto companyToAdd = new CompanyDto();
                        companyToAdd.setName(companyName);
                        companyToAdd.setTransactions(companyTransactions.get(companyName));
                        companiesToAdd.add(companyToAdd);
                    }

                    new ImportExpense(parent, true, companiesToAdd).setVisible(true);
                }

                Events.getInstance().fireProgressDoneEvent();

            } catch (BeckedeFileException | IOException ex) {
                log.error("Error when importing expenses", ex);
            }
        }).start();
    }

    public void importBillsFromCSV(String filePath, JFrame parent) {
        new Thread(() -> {
            try {
                CsvReaderHandler handler = new CsvReaderHandler(filePath, Boolean.TRUE);
                Iterable<CSVRecord> build = new Normalizer(handler.getRecords())
                        .removePeriod("Belopp")
                        .removePeriod("Saldo")
                        .removeWord("Transaktion", "Kortköp")
                        .removeWord("Transaktion", "Reservation")
                        .removeLeadingSpaces("Transaktion")
                        .removeWordStartingWith("Transaktion", "18", 7)
                        .removeWordStartingWith("Transaktion", "17", 7)
                        .removeTrailingAndLeadingSpaces("Transaktion")
                        .replaceComma("Belopp")
                        .replaceComma("Saldo")
                        .removeMinus("Belopp")
                        .build(handler.getHeaderMap());

                Map<String, Set<TransactionDto>> companyTransactions = new LinkedHashMap<>();

                Events.getInstance().fireProgressMaxValueEvent(((Collection<?>) build).size());
                for (CSVRecord cSVRecord : build) {

                    Optional<CompanyDto> company = CompanyHandler.getInstance().getCompanyByName(cSVRecord.get("Transaktion"));

                    if (company.isPresent()) {
                        TransactionDto transaction = TransactionExtractor.createTransaction(cSVRecord);
                        company.get().getTransactions().add(transaction);
                        transaction.setCompany(company.get());
                        TransactionHandler.getInstance().createTransaction(transaction);
                    } else {

                        TransactionDto transaction = TransactionExtractor.createTransaction(cSVRecord);

                        if (companyTransactions.containsKey(cSVRecord.get("Transaktion"))) {
                            companyTransactions.get(cSVRecord.get("Transaktion")).add(transaction);
                        } else {
                            Set<TransactionDto> transactionList = new LinkedHashSet<>();
                            transactionList.add(transaction);
                            companyTransactions.put(cSVRecord.get("Transaktion"), transactionList);
                        }

                    }
                    Events.getInstance().fireProgressIncreaseValueEvent(1, cSVRecord.get("Transaktion"));
                }

                if (!companyTransactions.isEmpty()) {
                    LinkedList<CompanyDto> companiesToAdd = new LinkedList<>();
                    for (String companyName : companyTransactions.keySet()) {
                        CompanyDto companyToAdd = new CompanyDto();
                        companyToAdd.setName(companyName);
                        companyToAdd.setTransactions(companyTransactions.get(companyName));
                        companiesToAdd.add(companyToAdd);
                    }

                    new ImportBill(parent, true, companiesToAdd).setVisible(true);
                }

                Events.getInstance().fireProgressDoneEvent();

            } catch (BeckedeFileException | IOException ex) {
                log.error("Error when importing expenses", ex);
            }
        }).start();
    }

}

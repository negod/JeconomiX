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
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.swing.JFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import se.backede.jeconomix.database.CompanyHandler;
import se.backede.jeconomix.database.TransactionHandler;
import se.backede.jeconomix.database.entity.extractor.TransactionExtractor;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.event.events.Events;
import se.backede.jeconomix.forms.importexport.Importer;
import se.backede.jeconomix.importer.CategoryDecider.CategoryDecider;

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

    public void importNordeaTransactionsFromCsv(String filePath, JFrame parent) {
        new Thread(() -> {
            try {
                CsvReaderHandler handler = new CsvReaderHandler(filePath, Boolean.TRUE);
                Iterable<CSVRecord> build = new Normalizer(handler.getRecords())
                        .removePeriod("Belopp")
                        .removePeriod("Saldo")
                        .removeWord("Transaktion", "Kortköp")
                        .removeWord("Transaktion", "Reservation")
                        .removeWord("Transaktion", "Autogiro")
                        .replaceComma("Belopp")
                        .replaceComma("Saldo")
                        .removeMinus("Belopp")
                        .extractName("Transaktion")
                        .build(handler.getHeaderMap());
                
                handleNormalizedImports(build, parent);
                
            } catch (BeckedeFileException | IOException ex) {
                log.error("Error when importing expenses", ex);
            }
        }).start();
    }
    
    private void handleNormalizedImports(Iterable<CSVRecord> records, JFrame parent) {
        Map<String, Set<TransactionDto>> companyTransactions = new LinkedHashMap<>();
        Set<TransactionDto> duplicateTransactions = new LinkedHashSet<>();
        Set<TransactionDto> invalidTransactions = new LinkedHashSet<>();
        
        Events.getInstance().fireProgressMaxValueEvent(((Collection<?>) records).size());
        for (CSVRecord cSVRecord : records) {
            
            Optional<CompanyDto> company = CompanyHandler.getInstance().getCompanyByName(cSVRecord.get("Transaktion").toUpperCase());
            
            if (company.isPresent()) {
                TransactionDto transaction = TransactionExtractor.createTransaction(cSVRecord);
                company.get().getTransactions().add(transaction);
                transaction.setCompany(company.get());
                
                boolean transactionExists = TransactionHandler.getInstance().transactionExists(transaction);
                if (transactionExists) {
                    duplicateTransactions.add(transaction);
                } else {
                    if (transaction.getSaldo() == null || transaction.getSum() == null) {
                        invalidTransactions.add(transaction);
                    } else {
                        TransactionHandler.getInstance().createTransaction(transaction);
                    }
                }
                
            } else {
                
                TransactionDto transaction = TransactionExtractor.createTransaction(cSVRecord);
                
                if (transaction.getSum() != null) {
                    if (companyTransactions.containsKey(cSVRecord.get("Transaktion"))) {
                        companyTransactions.get(cSVRecord.get("Transaktion")).add(transaction);
                    } else {
                        Set<TransactionDto> transactionList = new LinkedHashSet<>();
                        transactionList.add(transaction);
                        companyTransactions.put(cSVRecord.get("Transaktion"), transactionList);
                    }
                } else {
                    invalidTransactions.add(transaction);
                }
                
            }
            Events.getInstance().fireProgressIncreaseValueEvent(1, cSVRecord.get("Transaktion"));
        }
        
        if (!duplicateTransactions.isEmpty()) {
            
        }
        
        if (!companyTransactions.isEmpty()) {
            LinkedList<CompanyDto> companiesToAdd = new LinkedList<>();
            
            for (String companyName : companyTransactions.keySet()) {
                Optional<CategoryDto> decideCactegory = CategoryDecider.getInstance().decideCactegory(companyName);
                CompanyDto companyToAdd = new CompanyDto();
                companyToAdd.setName(companyName.toUpperCase());
                companyToAdd.setTransactions(companyTransactions.get(companyName));
                
                if (decideCactegory.isPresent()) {
                    companyToAdd.setCategory(decideCactegory.get());
                    CompanyHandler.getInstance().createCompany(companyToAdd);
                } else {
                    companiesToAdd.add(companyToAdd);
                }
            }
            
            new Importer(parent, true, companiesToAdd).setVisible(true);
        }
        
        Events.getInstance().fireProgressDoneEvent(
                ((Collection<?>) records).size(),
                duplicateTransactions.size(),
                invalidTransactions.size()
        );
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.entity.extractor;

import com.backede.fileutils.csv.parser.CsvColumn;
import com.backede.fileutils.csv.parser.CsvRecordWrapper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.database.AccociatedCompanyHandler;
import se.backede.jeconomix.database.CompanyHandler;
import se.backede.jeconomix.importer.TransactionWrapper;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public abstract class TransactionExtractor {

    public List<TransactionWrapper> createTransactions(List<CsvRecordWrapper> csvRecords) {
        return csvRecords.stream()
                .map(csvRecord -> createTransaction(csvRecord))
                .collect(Collectors.toList());
    }

    public void setTransactionOriginalValue(TransactionWrapper transaction, CsvColumn column) {
        transaction.getCsvRecord().getColumn(column).ifPresent(value -> {
            try {
                transaction.getTransactionDto().setOriginalValue(value);
            } catch (NumberFormatException e) {
                log.error("Error when getting origianlValue from CSVField {}", column.getColumnName());
            }
        });
    }

    public void setTransactionSum(TransactionWrapper transaction, CsvColumn column) {
        transaction.getCsvRecord().getColumn(column).ifPresent(value -> {
            try {
                BigDecimal valueOf = BigDecimal.valueOf(Double.parseDouble(value));
                BigDecimal bigDecimalValue = valueOf.setScale(2, RoundingMode.HALF_UP);
                transaction.getTransactionDto().setSum(bigDecimalValue);
            } catch (NumberFormatException e) {
                log.error("Error when parsing transaction sum from CSVField {}", column.getColumnName());
            }
        });
    }

    public void setTransactionSaldo(TransactionWrapper transaction, CsvColumn column) {
        transaction.getTransactionDto().setSaldo(BigDecimal.valueOf(0.00));

        transaction.getCsvRecord().getColumn(column).ifPresent(value -> {
            try {
                BigDecimal valueOf = BigDecimal.valueOf(Double.parseDouble(value));
                transaction.getTransactionDto().setSaldo(valueOf);
            } catch (NumberFormatException e) {
                log.error("Error when parsing transaction saldo from CSVField {}", column.getColumnName());
            }
        });
    }

    public void setTransactionDate(TransactionWrapper transaction, CsvColumn column) {
        transaction.getCsvRecord().getColumn(column).ifPresent(value -> {
            try {
                Date valueOf = Date.valueOf(value);
                transaction.getTransactionDto().setTransDate(valueOf);
            } catch (NumberFormatException e) {
                log.error("Error when parsing transaction date from CSVField {}", column.getColumnName());
            }
        });
    }

    public void setTransactionCompany(TransactionWrapper transaction, CsvColumn column) {
        transaction.getCsvRecord().getColumn(column).ifPresent(value -> {

            AccociatedCompanyHandler.getInstance().getAccociatedCompanyByName(value).ifPresent(accComp -> {
                transaction.getTransactionDto().setAscociatedCompany(accComp);
                transaction.getTransactionDto().setCompany(accComp.getCompany());
            });

            if (transaction.getTransactionDto().getCompany() == null) {
                CompanyHandler.getInstance().getCompanyByName(value).ifPresent(company -> {
                    System.out.println(company.toString());
                    transaction.getTransactionDto().setCompany(company);
                });
            }

        });
    }

    public abstract TransactionWrapper createTransaction(CsvRecordWrapper csvRecord);

}

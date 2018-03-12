/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.entity.extractor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import se.backede.jeconomix.dto.TransactionDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class TransactionExtractor {

    public static TransactionDto createTransaction(CSVRecord cSVRecord) {
        TransactionDto transaction = new TransactionDto();

        log.error("TRANSDATE:" + cSVRecord.get("Datum").toString());

        try {
            if (cSVRecord.get("Belopp") != null && !cSVRecord.get("Belopp").isEmpty()) {
                BigDecimal valueOf = BigDecimal.valueOf(Double.parseDouble(cSVRecord.get("Belopp")));
                valueOf.setScale(2, RoundingMode.HALF_UP);
                transaction.setSum(valueOf);
            }
        } catch (NumberFormatException | NullPointerException e) {
            log.error("Failure when parsing field Belopp to BigDecimal");
        }

        try {
            if (cSVRecord.get("Saldo") != null && !cSVRecord.get("Saldo").isEmpty()) {
                BigDecimal valueOf = BigDecimal.valueOf(Double.parseDouble(cSVRecord.get("Saldo")));
                transaction.setSaldo(valueOf);
            }
        } catch (NumberFormatException | NullPointerException e) {
            log.error("Failure when parsing field Saldo to BigDecimal");
        }

        try {
            if (cSVRecord.get("Datum") != null && !cSVRecord.get("Datum").isEmpty()) {
                Date valueOf = Date.valueOf(cSVRecord.get("Datum"));
                transaction.setTransDate(valueOf);
            }
        } catch (Exception e) {
            log.error("Failure when parsing field Date to BigDecimal");
        }

        return transaction;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.importer;

import com.backede.fileutils.csv.parser.CsvRecordWrapper;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.constants.NordeaCsvFields;
import se.backede.jeconomix.database.entity.extractor.TransactionExtractor;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class NordeaTransactionExtractor extends TransactionExtractor {

    @Override
    public TransactionWrapper createTransaction(CsvRecordWrapper csvRecord) {

        TransactionWrapper wrapper = new TransactionWrapper(csvRecord);

        super.setTransactionSum(wrapper, NordeaCsvFields.BELOPP);
        super.setTransactionSaldo(wrapper, NordeaCsvFields.SALDO);
        super.setTransactionDate(wrapper, NordeaCsvFields.DATE);
        super.setTransactionOriginalValue(wrapper, NordeaCsvFields.ORIGINAL_VALUE);
        super.setTransactionCompany(wrapper, NordeaCsvFields.TRANSACTION);

        log.debug("Transaction created {} ", wrapper.getTransactionDto().toString());

        return wrapper;
    }

}

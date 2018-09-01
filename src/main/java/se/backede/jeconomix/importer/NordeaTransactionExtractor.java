/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.importer;

import com.backede.fileutils.csv.parser.CsvRecordWrapper;
import se.backede.jeconomix.constants.NordeaCsvFields;
import se.backede.jeconomix.database.entity.extractor.TransactionExtractor;
import se.backede.jeconomix.dto.TransactionDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class NordeaTransactionExtractor extends TransactionExtractor {

    @Override
    public TransactionWrapper createTransaction(CsvRecordWrapper csvRecord) {
        TransactionWrapper wrapper = new TransactionWrapper(csvRecord, new TransactionDto());

        setTransactionSum(wrapper, NordeaCsvFields.BELOPP);
        setTransactionSaldo(wrapper, NordeaCsvFields.SALDO);
        setTransactionDate(wrapper, NordeaCsvFields.DATE);
        setTransactionCompany(wrapper, NordeaCsvFields.TRANSACTION);
        setTransactionOriginalValue(wrapper, NordeaCsvFields.TRANSACTION);

        return wrapper;
    }

}

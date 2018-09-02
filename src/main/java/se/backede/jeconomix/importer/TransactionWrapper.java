/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.importer;

import com.backede.fileutils.csv.parser.CsvRecordWrapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.EqualsAndHashCode;
import se.backede.jeconomix.dto.TransactionDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@RequiredArgsConstructor()
@EqualsAndHashCode
public class TransactionWrapper {

    private final CsvRecordWrapper csvRecord;
    private TransactionDto transactionDto = new TransactionDto();

}

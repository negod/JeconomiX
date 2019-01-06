/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import se.backede.jeconomix.database.entity.Transaction;
import se.backede.jeconomix.dto.TransactionDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Mapper
public interface TransactionMapper {

    List<Transaction> mapToTransactionList(List<TransactionDto> transactions);

    List<TransactionDto> mapToTransactionDtoList(List<Transaction> transactions);

    Transaction mapToTransaction(TransactionDto source);

    TransactionDto mapToTransactionDto(Transaction destination);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.mappers;

import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import se.backede.jeconomix.database.entity.Transaction;
import se.backede.jeconomix.dto.TransactionDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Mapper
public interface TransactionCompanyMapper {

    TransactionCompanyMapper INSTANCE = Mappers.getMapper(TransactionCompanyMapper.class);

    List<Transaction> mapToTransactionList(List<TransactionDto> transactions);

    List<TransactionDto> mapToTransactionDtoList(List<Transaction> transactions);

    @Mappings({
        @Mapping(target = "company", ignore = true)
    })
    Transaction mapToTransaction(TransactionDto transaction);

    @Mappings({
        @Mapping(target = "company", ignore = true)
    })
    TransactionDto mapToTransactionDto(Transaction transaction);
}

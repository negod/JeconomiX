/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.export.mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.dto.budget.BudgetDto;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;
import se.backede.jeconomix.dto.export.BudgetExpenseExportDto;
import se.backede.jeconomix.dto.export.BudgetExportDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class BudgetExportMapper {

    public static Optional<BudgetExportDto> mapToExportDto(BudgetDto input) {
        try {
            BudgetExportDto dto = new BudgetExportDto();
            dto.setId(input.getId());
            dto.setUpdatedDate(input.getUpdatedDate());
            dto.setMonth(input.getMonth());
            dto.setYear(input.getYear());

            dto.setBudgetExpenseSet(
                    input.getBudgetExpenseSet()
                            .stream()
                            .map(budgetDto -> mapToBudgetExpenseExportDto(budgetDto))
                            .collect(Collectors.toSet())
            );

            return Optional.ofNullable(dto);
        } catch (Exception e) {
            log.error("Error when maping to BudgetExportDto {}", input.toString(), e);
            return Optional.empty();
        }
    }

    public static BudgetExpenseExportDto mapToBudgetExpenseExportDto(BudgetExpenseDto dto) {
        BudgetExpenseExportDto retVal = new BudgetExpenseExportDto();
        retVal.setId(dto.getId());
        retVal.setUpdatedDate(dto.getUpdatedDate());
        retVal.setEstimatedsum(dto.getEstimatedsum());
        retVal.setCategory(CategoryExportMapper.mapToExportDto(dto.getCategory()));
        return retVal;
    }

    public static Optional<BudgetDto> mapToDto(BudgetExportDto input) {
        try {
            BudgetDto dto = new BudgetDto();
            dto.setId(input.getId());
            dto.setUpdatedDate(input.getUpdatedDate());
            dto.setMonth(input.getMonth());
            dto.setYear(input.getYear());

            dto.setBudgetExpenseSet(
                    input.getBudgetExpenseSet()
                            .stream()
                            .map(budgetDto -> mapToBudgetExpenseDto(budgetDto))
                            .collect(Collectors.toSet())
            );

            return Optional.ofNullable(dto);
        } catch (Exception e) {
            log.error("Error when maping to BudgetExportDto {}", input.toString(), e);
            return Optional.empty();
        }
    }

    public static BudgetExpenseDto mapToBudgetExpenseDto(BudgetExpenseExportDto dto) {
        BudgetExpenseDto retVal = new BudgetExpenseDto();
        retVal.setId(dto.getId());
        retVal.setUpdatedDate(dto.getUpdatedDate());
        retVal.setEstimatedsum(dto.getEstimatedsum());
        retVal.setCategory(CategoryExportMapper.mapToDto(dto.getCategory()));
        return retVal;
    }

    public static List<BudgetExportDto> mapToExportDto(List<BudgetDto> dtoList) {
        return dtoList
                .stream()
                .map(budgetDto -> mapToExportDto(budgetDto))
                .filter(data -> data.isPresent())
                .map(dto -> dto.get())
                .collect(Collectors.toList());
    }

    public static List<BudgetDto> mapToDto(List<BudgetExportDto> dtoList) {
        return dtoList
                .stream()
                .map(budgetDto -> mapToDto(budgetDto))
                .filter(data -> data.isPresent())
                .map(dto -> dto.get())
                .collect(Collectors.toList());
    }

}

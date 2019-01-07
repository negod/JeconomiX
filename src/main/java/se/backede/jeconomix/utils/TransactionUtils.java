/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.utils;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.dto.TransactionDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )s
 */
public class TransactionUtils {

    public static Optional<Map<Month, List<TransactionDto>>> getTransactionsFilteredByMonth(List<TransactionDto> transactions) {
        return Optional.ofNullable(transactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getBudgetMonth())));
    }

    public static Optional<Map<Month, Map<CategoryTypeEnum, Integer>>> getCalculatedTransactionsByMonth(Map<Month, List<TransactionDto>> budgets) {
        Map<Month, Map<CategoryTypeEnum, Integer>> calculated = new HashMap<>();
        budgets.forEach((month, dtoList) -> {
            Map<CategoryTypeEnum, List<TransactionDto>> categoryFilteredTransactions = getCategoryFilteredTransactions(dtoList);
            calculated.put(month, getSumsFromTransactions(categoryFilteredTransactions));
        });
        return Optional.ofNullable(calculated);
    }

    public static Map<CategoryTypeEnum, List<TransactionDto>> getCategoryFilteredTransactions(List<TransactionDto> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getCompany().getCategory().getCategoryType().getType()));
    }

    public static Map<CategoryTypeEnum, Integer> getSumsFromTransactions(Map<CategoryTypeEnum, List<TransactionDto>> transactions) {
        Map<CategoryTypeEnum, Integer> sums = new HashMap<>();
        transactions.forEach((category, list) -> {
            sums.put(category, getSumsFromTransactions(list));
        });
        return sums;
    }

    public static Integer getSumsFromTransactions(List<TransactionDto> budgetLines) {
        return budgetLines.stream()
                .map((dto) -> dto.getSum().abs().intValue())
                .reduce((dto, y) -> dto + y).get();
    }

}

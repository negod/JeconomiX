/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.dto.TransactionReportDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class TransactionUtils {

    /**
     *
     * @param transactions
     * @return All transactions filtered by the budgetmonth for the transaction
     */
    public static Optional<Map<Month, List<TransactionDto>>> filterTransactionByMonth(List<TransactionDto> transactions) {
        return Optional.ofNullable(transactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getBudgetMonth())));
    }

    /**
     *
     * @param budgets
     * @return
     */
    public static Optional<Map<Month, Map<CategoryTypeEnum, Integer>>> getCalculatedTransactionsByMonth(Map<Month, List<TransactionDto>> budgets) {
        Map<Month, Map<CategoryTypeEnum, Integer>> calculated = new HashMap<>();
        budgets.forEach((month, dtoList) -> {
            Map<CategoryTypeEnum, List<TransactionDto>> categoryFilteredTransactions = filterTransactionByCategory(dtoList);
            calculated.put(month, getSumsFromTransactions(categoryFilteredTransactions));
        });
        return Optional.ofNullable(calculated);
    }

    /**
     *
     * @param transactions
     * @return
     */
    public static Map<CategoryTypeEnum, List<TransactionDto>> filterTransactionByCategory(List<TransactionDto> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getCompany().getCategory().getCategoryType().getType()));
    }

    /**
     *
     * @param transactions
     * @return
     */
    public static Map<String, List<TransactionReportDto>> filterTransactionReportByCategory(List<TransactionReportDto> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getCategory()));
    }

    public static List<TransactionReportDto> extractTransactionReportDtos(Map<String, List<TransactionReportDto>> dtoList) {
        return dtoList.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param transactions
     * @return
     */
    public static Map<CategoryTypeEnum, Integer> getSumsFromTransactions(Map<CategoryTypeEnum, List<TransactionDto>> transactions) {
        Map<CategoryTypeEnum, Integer> sums = new HashMap<>();
        transactions.forEach((category, list) -> {
            sums.put(category, getSumsFromTransactions(list));
        });
        return sums;
    }

    /**
     *
     * @param budgetLines
     * @return
     */
    public static Integer getSumsFromTransactions(List<TransactionDto> budgetLines) {
        return budgetLines.stream()
                .map((dto) -> dto.getSum().abs().intValue())
                .reduce((dto, y) -> dto + y).get();
    }

    public static BigDecimal calculateTotalSum(List<TransactionDto> transactions) {
        return transactions.stream()
                .map(TransactionDto::getSum)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal calculateAverageSum(List<TransactionDto> transactions, Integer devideBy) {
        BigDecimal allSums = transactions.stream()
                .map(TransactionDto::getSum)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return allSums.divide(new BigDecimal(devideBy), RoundingMode.UP);
    }

    public static BigDecimal calculateAverageSumWithBigDecimals(List<BigDecimal> transactions, Integer devideBy) {
        BigDecimal allSums = transactions
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return allSums.divide(new BigDecimal(devideBy), RoundingMode.UP);
    }

    /**
     *
     * @param reports
     * @return
     */
    public static Map<Month, BigDecimal> calculateTotalSumByMonth(List<TransactionReportDto> reports) {
        EnumMap<Month, BigDecimal> map = new EnumMap<>(Month.class);
        for (TransactionReportDto report : reports) {
            for (Month month : Month.values()) {
                BigDecimal monthSum = report.getMonthReport().getOrDefault(month, BigDecimal.ZERO);
                BigDecimal current = map.getOrDefault(month, BigDecimal.ZERO);

                if (monthSum.abs().doubleValue() > 0) {
                    map.put(month, monthSum.add(current));
                }
            }
        }
        return map;
    }

    /**
     *
     * @param reports
     * @return
     */
    public static EnumMap<Month, BigDecimal> calculateAverageSumForAllTransactions(List<TransactionReportDto> reports) {

        Map<Month, BigDecimal> calculateTotalSumByMonth = calculateTotalSumByMonth(reports);

        List<BigDecimal> collect = calculateTotalSumByMonth
                .values()
                .stream()
                .collect(Collectors.toList())
                .stream()
                .filter(x -> x.abs().doubleValue() > 0)
                .collect(Collectors.toList());

        BigDecimal average = collect.size() > 0 ? calculateAverageSumWithBigDecimals(collect, collect.size()) : BigDecimal.ZERO;

        EnumMap<Month, BigDecimal> averagePerMonth = new EnumMap<>(Month.class);
        for (Month month : Month.values()) {
            averagePerMonth.put(month, average);
        }

        return averagePerMonth;

    }

    /**
     *
     * @param transactionList
     * @return
     */
    public static List<TransactionReportDto> extractTransactionReportList(Map<String, List<TransactionDto>> transactionList) {
        List<TransactionReportDto> transactionReports = new ArrayList<>();

        transactionList.forEach((category, transactions) -> {

            filterTransactionByMonth(transactions).map(filteredTransactions -> {

                EnumMap<Month, BigDecimal> totals = new EnumMap<>(Month.class);

                filteredTransactions.forEach((month, transactionListByMonth) -> {
                    BigDecimal totalSum = calculateTotalSum(transactionListByMonth);
                    totals.put(month, totalSum);
                });

                BigDecimal totalSumForCategory = calculateTotalSum(transactions);

                return TransactionReportDto.builder()
                        .category(category)
                        .transctions(transactions)
                        .monthReport(totals)
                        .sum(totalSumForCategory)
                        .build();

            }).ifPresent(transactionDto -> {
                transactionReports.add(transactionDto);
            });

        });

        return transactionReports;
    }

}

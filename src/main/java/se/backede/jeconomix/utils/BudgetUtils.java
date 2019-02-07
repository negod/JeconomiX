/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.BudgetHandler;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.dto.budget.BudgetCalculationDto;
import se.backede.jeconomix.dto.budget.BudgetDto;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BudgetUtils {

    private static final BudgetUtils INSTANCE = new BudgetUtils();

    protected BudgetUtils() {
    }

    public static final BudgetUtils getInstance() {
        return INSTANCE;
    }

    public Optional<Set<BudgetExpenseDto>> createBudgetFromBudget(YearMonth budgetMonth) {
        return BudgetHandler.getInstance().getBudgetByYear(budgetMonth).map(budget -> {
            return budget.getBudgetExpenseSet();
        });
    }

    public static Optional<Map<CategoryTypeEnum, List<BudgetExpenseDto>>> createBudgetFromTransaction(List<TransactionDto> transactions) {
        Map<CategoryTypeEnum, List<BudgetExpenseDto>> categorizedTransactions = new HashMap<>();

        for (TransactionDto transactionDto : transactions) {
            if (categorizedTransactions.containsKey(transactionDto.getCompany().getCategory().getCategoryType().getType())) {
                categorizedTransactions.get(transactionDto.getCompany().getCategory().getCategoryType().getType()).add(mapToBudgetExpenseDto(transactionDto));
            } else {
                categorizedTransactions.put(transactionDto.getCompany().getCategory().getCategoryType().getType(), Stream.of(mapToBudgetExpenseDto(transactionDto)).collect(Collectors.toList()));
            }
        }
        return Optional.of(categorizedTransactions);
    }

    public static BudgetCalculationDto mapToBudgetCalculationDto(List<TransactionDto> transactions, YearMonth yearMonth) {

        Map<CategoryDto, List<BudgetExpenseDto>> OrderedByCategory = transactions.stream()
                .map(transaction -> mapToBudgetExpenseDto(transaction))
                .collect(Collectors.groupingBy(expense -> expense.getCategory()));

        List<BudgetExpenseDto> orderedBudgetExpenses = groupBudgetExpenseByCategory(OrderedByCategory);

        Map<CategoryTypeEnum, List<BudgetExpenseDto>> budgetExpense = orderedBudgetExpenses.stream()
                .collect(Collectors.groupingBy(expense -> expense.getCategory().getCategoryType().getType()));

        Map<CategoryTypeEnum, Integer> sumsFromBudgetExpense = BudgetUtils.getSumsFromBudgetExpense(budgetExpense);

        return BudgetCalculationDto.builder()
                .budgetExpense(budgetExpense)
                .yearMonth(yearMonth)
                .budgetSums(sumsFromBudgetExpense)
                .build();
    }

    private static List<BudgetExpenseDto> groupBudgetExpenseByCategory(Map<CategoryDto, List<BudgetExpenseDto>> orderedByCategory) {
        List<BudgetExpenseDto> budgets = new ArrayList<>();
        orderedByCategory.forEach((category, expenseList) -> {
            BudgetExpenseDto dto = new BudgetExpenseDto();
            dto.setEstimatedsum(getSumsFromBudgetExpense(expenseList, category).abs());
            dto.setCategory(category);
            budgets.add(dto);
        });
        return budgets;
    }

    private static BudgetExpenseDto mapToBudgetExpenseDto(TransactionDto transaction) {
        BudgetExpenseDto dto = new BudgetExpenseDto();
        dto.setCategory(transaction.getCompany().getCategory());

        Double rounded = round(transaction.getSum().doubleValue(), -2, RoundingMode.UP);
        dto.setEstimatedsum(BigDecimal.valueOf(rounded));
        return dto;
    }

    public static BigDecimal roundBigDecimal(Double number, int precision, RoundingMode roundingMode) {
        return BigDecimal.valueOf(round(number, precision, roundingMode));
    }

    public static double round(Double number, int precision, RoundingMode roundingMode) {
        BigDecimal bd = null;
        try {
            bd = BigDecimal.valueOf(number);
        } catch (NumberFormatException e) {
            return number;
        }
        bd = bd.setScale(precision, roundingMode);
        return bd.doubleValue();
    }

    public static BigDecimal getSumsFromBudgetExpense(List<BudgetExpenseDto> budgetLines, CategoryTypeEnum category) {
        return budgetLines.stream()
                .filter(dto -> dto.getCategory().getCategoryType().getType().equals(category))
                .map((dto) -> dto.getEstimatedsum())
                .reduce((dto, y) -> dto.add(y)).get();
    }

    public static BigDecimal getSumsFromBudgetExpense(List<BudgetExpenseDto> budgetLines, CategoryDto category) {
        return budgetLines.stream()
                .filter(dto -> dto.getCategory().equals(category))
                .map((dto) -> dto.getEstimatedsum())
                .reduce((dto, y) -> dto.add(y)).get();
    }

    public static Map<CategoryTypeEnum, List<BudgetExpenseDto>> getCategoryFilteredBudgetExpense(Set<BudgetExpenseDto> budgetLines) {
        return budgetLines.stream()
                .collect(Collectors.groupingBy(expense -> expense.getCategory().getCategoryType().getType()));
    }

    public static Optional<Map<Month, BudgetDto>> getBudgetFilteredByMonth(List<BudgetDto> budgetLines) {
        return Optional.ofNullable(budgetLines.stream()
                .collect(Collectors.toMap(BudgetDto::getMonth, c -> c)));
    }

    public static Optional<Map<Month, BudgetCalculationDto>> getCalculatedBudgetsByMonth(Map<Month, BudgetDto> budgets) {

        Map<Month, BudgetCalculationDto> calculated = new HashMap<>();
        budgets.forEach((month, dto) -> {
            Map<CategoryTypeEnum, List<BudgetExpenseDto>> categoryFilteredBudgetExpense = getCategoryFilteredBudgetExpense(dto.getBudgetExpenseSet());
            BudgetCalculationDto build = BudgetCalculationDto.builder()
                    .yearMonth(YearMonth.of(dto.getYear(), month))
                    .budgetExpense(categoryFilteredBudgetExpense)
                    .budgetSums(BudgetUtils.getSumsFromBudgetExpense(categoryFilteredBudgetExpense))
                    .build();
            calculated.put(month, build);
        });

        return Optional.ofNullable(calculated);
    }

    public static Map<CategoryTypeEnum, Integer> getSumsFromBudgetExpense(Map<CategoryTypeEnum, List<BudgetExpenseDto>> budgetLines) {
        Map<CategoryTypeEnum, Integer> sums = new HashMap<>();
        budgetLines.forEach((category, list) -> {
            sums.put(category, getSumsFromBudgetExpense(list));
        });
        return sums;
    }

    public static Integer getSumsFromBudgetExpense(List<BudgetExpenseDto> budgetLines) {
        return budgetLines.stream()
                .map((dto) -> dto.getEstimatedsum().abs().intValueExact())
                .reduce((dto, y) -> dto + y).get();
    }

}

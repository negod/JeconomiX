/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.BudgetHandler;
import se.backede.jeconomix.database.TransactionHandler;
import se.backede.jeconomix.dto.TransactionDto;
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

    public Optional<List<BudgetExpenseDto>> createBudgetFromBudget(YearMonth budgetMonth) {
        return BudgetHandler.getInstance().getBudget(budgetMonth).map(budget -> {
            return budget.getBudgetExpenseSet();
        });
    }

    public Optional<Map<CategoryTypeEnum, List<BudgetExpenseDto>>> createBudgetFromTransaction(YearMonth budgetMonth) {
        return TransactionHandler.getInstance().getTransactionsByBudgetMonth(budgetMonth).map(transactionList -> {

            Map<CategoryTypeEnum, List<BudgetExpenseDto>> categorizedTransactions = new HashMap<>();

            for (TransactionDto transactionDto : transactionList) {
                if (categorizedTransactions.containsKey(transactionDto.getCompany().getCategory().getCategoryType().getType())) {
                    categorizedTransactions.get(transactionDto.getCompany().getCategory().getCategoryType().getType()).add(mapToBudgetExpenseDto(transactionDto));
                } else {
                    categorizedTransactions.put(transactionDto.getCompany().getCategory().getCategoryType().getType(), Stream.of(mapToBudgetExpenseDto(transactionDto)).collect(Collectors.toList()));
                }
            }
            return categorizedTransactions;
        });
    }

    private BudgetExpenseDto mapToBudgetExpenseDto(TransactionDto transaction) {
        BudgetExpenseDto dto = new BudgetExpenseDto();
        dto.setCategory(transaction.getCompany().getCategory());

        Double rounded = round(transaction.getSum().doubleValue(), -2, RoundingMode.UP);
        dto.setEstimatedsum(BigDecimal.valueOf(rounded));
        return dto;
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

}

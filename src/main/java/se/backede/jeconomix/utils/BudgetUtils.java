/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.utils;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.TransactionHandler;
import se.backede.jeconomix.database.entity.budget.BudgetExpense;
import se.backede.jeconomix.dto.CategoryDto;
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

    public List<BudgetExpense> createBudgetFromBudget(YearMonth budgetMonth) {

        return null;
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
        dto.setEstimatedsum(transaction.getSum());
        return dto;
    }

}

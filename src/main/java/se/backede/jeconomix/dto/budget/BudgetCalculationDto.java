/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.budget;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import se.backede.jeconomix.constants.CategoryTypeEnum;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Builder
public class BudgetCalculationDto {

    YearMonth yearMonth;
    Map<CategoryTypeEnum, List<BudgetExpenseDto>> budgetExpense = new EnumMap<>(CategoryTypeEnum.class);
    Map<CategoryTypeEnum, Integer> budgetSums = new EnumMap<>(CategoryTypeEnum.class);
    //HashMap<CategoryTypeEnum, BigDecimal> accumulatedSum = new HashMap<>();

}

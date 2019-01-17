/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.StringUtils;
import org.jfree.data.category.DefaultCategoryDataset;
import se.backede.jeconomix.dto.TransactionReportDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class ReportUtils {

    private static final BigDecimal ZERO = BigDecimal.valueOf(0.00);

    /**
     *
     * @param reports
     * @return
     */
    public static EnumMap<Month, BigDecimal> calculateTotalSumsPerMonth(List<TransactionReportDto> reports) {
        EnumMap<Month, BigDecimal> sums = new EnumMap<>(Month.class);

        for (Month month : Month.values()) {
            BigDecimal currentSum = sums.getOrDefault(month, ZERO);
            for (TransactionReportDto report : reports) {
                BigDecimal reportSum = report.getMonthReport().getOrDefault(month, ZERO);
                if (reportSum != null) {
                    double abs = Math.abs(reportSum.doubleValue());
                    currentSum = currentSum.add(BigDecimal.valueOf(abs));
                    sums.put(month, currentSum);
                }
            }
        }
        return sums;

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Month;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
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
     * Gets tha average sum for all reports. Takes in to consideration if a sum
     * is 0.00.
     *
     * @param reports
     * @return
     */
    public static Map<Month, BigDecimal> calculateAvg(List<TransactionReportDto> reports) {
        Map<Month, BigDecimal> calculatedSums = calculateTotalSumsPerMonth(reports);
        Set<Month> presentMonths = getPresentMonths(reports);

        BigDecimal average = BigDecimal.valueOf(0.00);
        for (Month presentMonth : presentMonths) {
            average = average.add(calculatedSums.getOrDefault(presentMonth, ZERO));
        }

        if (!presentMonths.isEmpty()) {
            BigDecimal divide = average.divide(BigDecimal.valueOf(presentMonths.size()), MathContext.DECIMAL128);

            for (Month month : presentMonths) {
                calculatedSums.put(month, divide);
            }
        }

        return calculatedSums;
    }

    /**
     * Gets all months where the total sum for the month is above 0
     *
     * @param reports
     * @return
     */
    private static Set<Month> getPresentMonths(List<TransactionReportDto> reports) {
        Set<Month> presentMonths = new HashSet<>();
        for (TransactionReportDto report : reports) {
            for (Month month : report.getMonthReport().keySet()) {
                if (report.getMonthReport().get(month) != BigDecimal.valueOf(0.00)) {
                    presentMonths.add(month);
                }
            }
        }
        return presentMonths;
    }

    /**
     * Creates the dataset
     *
     * @param reports the reports
     * @param avg if an average calculation should be done
     * @return
     */
    public static DefaultCategoryDataset createDataset(Map<String, List<TransactionReportDto>> reports, Boolean avg) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, List<TransactionReportDto>> entry : reports.entrySet()) {
            addNewDataset(dataset, entry.getKey(), entry.getValue(), Boolean.FALSE);
        }

        if (avg) {
            for (Map.Entry<String, List<TransactionReportDto>> entry : reports.entrySet()) {
                addNewDataset(dataset, "AVERAGE " + entry.getKey(), entry.getValue(), Boolean.TRUE);
            }
        }
        return dataset;
    }

    public static Map<Month, BigDecimal> calculateTotalSumsPerMonth(List<TransactionReportDto> reports) {
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

    private static void addNewDataset(DefaultCategoryDataset dataset, String lineTitle, List<TransactionReportDto> reports, Boolean average) {
        Map<Month, BigDecimal> calculateSums = new HashMap<>();
        if (average) {
            calculateSums = calculateAvg(reports);
        } else {
            calculateSums = calculateTotalSumsPerMonth(reports);
        }

        dataset.addValue(calculateSums.getOrDefault(Month.JANUARY, ZERO), lineTitle, "JAN");
        dataset.addValue(calculateSums.getOrDefault(Month.FEBRUARY, ZERO), lineTitle, "FEB");
        dataset.addValue(calculateSums.getOrDefault(Month.MARCH, ZERO), lineTitle, "MAR");
        dataset.addValue(calculateSums.getOrDefault(Month.APRIL, ZERO), lineTitle, "APR");
        dataset.addValue(calculateSums.getOrDefault(Month.MAY, ZERO), lineTitle, "MAY");
        dataset.addValue(calculateSums.getOrDefault(Month.JUNE, ZERO), lineTitle, "JUN");
        dataset.addValue(calculateSums.getOrDefault(Month.JULY, ZERO), lineTitle, "JUL");
        dataset.addValue(calculateSums.getOrDefault(Month.AUGUST, ZERO), lineTitle, "AUG");
        dataset.addValue(calculateSums.getOrDefault(Month.SEPTEMBER, ZERO), lineTitle, "SEP");
        dataset.addValue(calculateSums.getOrDefault(Month.OCTOBER, ZERO), lineTitle, "OCT");
        dataset.addValue(calculateSums.getOrDefault(Month.NOVEMBER, ZERO), lineTitle, "NOV");
        dataset.addValue(calculateSums.getOrDefault(Month.DECEMBER, ZERO), lineTitle, "DEC");
    }

}

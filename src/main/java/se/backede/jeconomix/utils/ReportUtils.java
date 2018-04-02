/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.utils;

import static java.lang.StrictMath.log;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.jfree.data.category.DefaultCategoryDataset;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.CategoryHandler;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.dto.TransactionReportDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class ReportUtils {

    public static List<TransactionReportDto> getCalculatedReport(CategoryTypeEnum type, Integer year) {
        Optional<List<CategoryDto>> categories = CategoryHandler.getInstance().getFilteredCategories(type, year);
        if (categories.isPresent()) {
            return extractTransactionReportList(categories.get(), year);
        }
        return new ArrayList<>();
    }

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
            BigDecimal oldSum = average;
            average = average.add(calculatedSums.get(presentMonth));
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

    private static List<TransactionReportDto> extractTransactionReportList(List<CategoryDto> allBillCategories, Integer year) {
        List<TransactionReportDto> transactionReports = new ArrayList<>();

        for (CategoryDto categoryDto : allBillCategories) {
            TransactionReportDto report = new TransactionReportDto();
            Set<CompanyDto> company = categoryDto.getCompany();

            BigDecimal sum = BigDecimal.valueOf(0);
            for (CompanyDto companyDto : company) {
                for (TransactionDto transaction : companyDto.getTransactions()) {

                    if (transaction.getBudgetYear().equals(year)) {

                        report.getTransctions().add(transaction);

                        //Add value to Month
                        Month month = transaction.getBudgetMonth();

                        if (report.getMonthReport().containsKey(month)) {

                            if (transaction.getSum() != null) {
                                BigDecimal currentSum = report.getMonthReport().get(month);
                                BigDecimal newSum = currentSum.add(transaction.getSum());
                                report.getMonthReport().put(month, newSum);
                            }
                        } else {
                            report.getMonthReport().put(month, transaction.getSum());
                        }

                        //Calculate total sum
                        BigDecimal addedSUm = sum.add(transaction.getSum(), MathContext.DECIMAL32);
                        sum = addedSUm;
                    }
                }

            }
            report.setSum(sum);
            report.setCategory(categoryDto.getName());
            transactionReports.add(report);
        }
        return transactionReports;
    }

    private static Map<Month, BigDecimal> calculateTotalSumsPerMonth(List<TransactionReportDto> reports) {
        Map<Month, BigDecimal> sums = new HashMap<>();

        for (Month month : Month.values()) {
            sums.put(month, BigDecimal.valueOf(0.00));
        }

        for (Month month : Month.values()) {
            BigDecimal currentSum = sums.get(month);
            for (TransactionReportDto report : reports) {
                BigDecimal reportSum = report.getMonthReport().get(month);
                if (reportSum != null) {
                    BigDecimal oldSum = currentSum;
                    currentSum = currentSum.add(report.getMonthReport().get(month));
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

        dataset.addValue(calculateSums.get(Month.JANUARY), lineTitle, "JAN");
        dataset.addValue(calculateSums.get(Month.FEBRUARY), lineTitle, "FEB");
        dataset.addValue(calculateSums.get(Month.MARCH), lineTitle, "MAR");
        dataset.addValue(calculateSums.get(Month.APRIL), lineTitle, "APR");
        dataset.addValue(calculateSums.get(Month.MAY), lineTitle, "MAY");
        dataset.addValue(calculateSums.get(Month.JUNE), lineTitle, "JUN");
        dataset.addValue(calculateSums.get(Month.JULY), lineTitle, "JUL");
        dataset.addValue(calculateSums.get(Month.AUGUST), lineTitle, "AUG");
        dataset.addValue(calculateSums.get(Month.SEPTEMBER), lineTitle, "SEP");
        dataset.addValue(calculateSums.get(Month.OCTOBER), lineTitle, "OCT");
        dataset.addValue(calculateSums.get(Month.NOVEMBER), lineTitle, "NOV");
        dataset.addValue(calculateSums.get(Month.DECEMBER), lineTitle, "DEC");
    }

}

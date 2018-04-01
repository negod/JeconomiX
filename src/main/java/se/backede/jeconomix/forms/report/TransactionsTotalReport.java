/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.report;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.database.CategoryHandler;
import se.backede.jeconomix.dto.TransactionReportDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class TransactionsTotalReport extends javax.swing.JDialog {

    /**
     * Creates new form expenseReport
     */
    public TransactionsTotalReport(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        List<TransactionReportDto> calculatedBillReport = getCalculatedReport(CategoryTypeEnum.BILL);
        List<TransactionReportDto> calculatedExpenseReport = getCalculatedReport(CategoryTypeEnum.EXPENSE);
        List<TransactionReportDto> calculatedIncomeReport = getCalculatedReport(CategoryTypeEnum.INCOME);

        List<TransactionReportDto> aggregatedExpenseReport = new LinkedList<>();
        aggregatedExpenseReport.addAll(calculatedBillReport);
        aggregatedExpenseReport.addAll(calculatedExpenseReport);

        Map<String, List<TransactionReportDto>> reports = new HashMap<>();
        reports.put("Income", calculatedIncomeReport);
        reports.put("Expense", aggregatedExpenseReport);

        addLineChart(reports);
    }

    public void addLineChart(Map<String, List<TransactionReportDto>> reports) {
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Total",
                "Month", "Kronor",
                createDataset(reports),
                PlotOrientation.VERTICAL,
                true, true, true);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(lineChartPanel.getWidth(), lineChartPanel.getHeight()));
        lineChartPanel.setLayout(new BorderLayout());
        lineChartPanel.add(chartPanel, BorderLayout.NORTH);
    }

    private Map<Month, BigDecimal> calculateSums(List<TransactionReportDto> reports) {
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

    private DefaultCategoryDataset createDataset(Map<String, List<TransactionReportDto>> reports) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, List<TransactionReportDto>> entry : reports.entrySet()) {
            addNewDataset(dataset, entry.getKey(), entry.getValue());
        }
        return dataset;
    }

    private void addNewDataset(DefaultCategoryDataset dataset, String lineTitle, List<TransactionReportDto> reports) {
        Map<Month, BigDecimal> calculateSums = calculateSums(reports);
        dataset.addValue(calculateSums.get(Month.JANUARY), lineTitle, "Jan");
        dataset.addValue(calculateSums.get(Month.FEBRUARY), lineTitle, "Feb");
        dataset.addValue(calculateSums.get(Month.MARCH), lineTitle, "Mar");
        dataset.addValue(calculateSums.get(Month.APRIL), lineTitle, "Apr");
        dataset.addValue(calculateSums.get(Month.MAY), lineTitle, "May");
        dataset.addValue(calculateSums.get(Month.JUNE), lineTitle, "Jun");
        dataset.addValue(calculateSums.get(Month.JULY), lineTitle, "Jul");
        dataset.addValue(calculateSums.get(Month.AUGUST), lineTitle, "Aug");
        dataset.addValue(calculateSums.get(Month.SEPTEMBER), lineTitle, "Sep");
        dataset.addValue(calculateSums.get(Month.OCTOBER), lineTitle, "Oct");
        dataset.addValue(calculateSums.get(Month.NOVEMBER), lineTitle, "Nov");
        dataset.addValue(calculateSums.get(Month.DECEMBER), lineTitle, "Dec");
    }

    public List<TransactionReportDto> extractTransactionReportList(List<CategoryDto> allBillCategories) {
        List<TransactionReportDto> transactionReports = new ArrayList<>();

        for (CategoryDto categoryDto : allBillCategories) {
            TransactionReportDto report = new TransactionReportDto();
            Set<CompanyDto> company = categoryDto.getCompany();

            BigDecimal sum = BigDecimal.valueOf(0);
            for (CompanyDto companyDto : company) {
                for (TransactionDto transaction : companyDto.getTransactions()) {

                    report.getTransctions().add(transaction);

                    //Add value to Month
                    Month month = transaction.getTransDate().toLocalDate().getMonth();

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
                    BigDecimal addedSUm = sum.add(transaction.getSum());
                    sum = addedSUm;

                }
            }
            report.setSum(sum);
            report.setCategory(categoryDto.getName());
            transactionReports.add(report);
        }
        return transactionReports;
    }

    public List<TransactionReportDto> getCalculatedReport(CategoryTypeEnum type) {
        Optional<List<CategoryDto>> categories = CategoryHandler.getInstance().getFilteredCategories(type);
        if (categories.isPresent()) {
            return extractTransactionReportList(categories.get());
        }
        return new ArrayList<>();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lineChartPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lineChartPanel.setBackground(new java.awt.Color(255, 255, 255));
        lineChartPanel.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout lineChartPanelLayout = new javax.swing.GroupLayout(lineChartPanel);
        lineChartPanel.setLayout(lineChartPanelLayout);
        lineChartPanelLayout.setHorizontalGroup(
            lineChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1010, Short.MAX_VALUE)
        );
        lineChartPanelLayout.setVerticalGroup(
            lineChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 404, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lineChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lineChartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel lineChartPanel;
    // End of variables declaration//GEN-END:variables
}

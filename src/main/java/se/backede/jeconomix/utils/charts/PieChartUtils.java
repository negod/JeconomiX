/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.utils.charts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Year;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.utils.TransactionUtils;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class PieChartUtils {

    public static void addPieChart(List<TransactionDto> reports, JPanel parent, Year year) {

        Map<CategoryTypeEnum, List<TransactionDto>> filterTransactionByCategory = TransactionUtils.filterTransactionByCategory(reports);

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Categories",
                createDataset(filterTransactionByCategory),
                false, false, false);

        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new Dimension(parent.getWidth(), parent.getHeight()));

        pieChart.setTitle(
                new org.jfree.chart.title.TextTitle("Year total: " + year.toString(),
                        new java.awt.Font("Courier New", java.awt.Font.PLAIN, 12)
                )
        );

        setPieChartPlot(pieChart);
        parent.setLayout(new BorderLayout());
        parent.add(chartPanel, BorderLayout.NORTH);
    }

    public static void setPieChartPlot(JFreeChart pieChart) {

        PiePlot plot = (PiePlot) pieChart.getPlot();
//        plot.setSectionPaint(KEY1, Color.green);
//        plot.setSectionPaint(KEY2, Color.red);
//        plot.setExplodePercent(KEY1, 0.10);
        plot.setSimpleLabels(false);

        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
                "{0}: ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        plot.setLabelGenerator(gen);

    }

    public static PieDataset createDataset(Map<CategoryTypeEnum, List<TransactionDto>> reports) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        reports.forEach((categoryType, transactionList) -> {

            switch (categoryType) {
                case INCOME:
                    break;
                case EXPENSE:
                case BILL:
                case LOAN:
                case CREDIT_CARD:
                    BigDecimal calculateTotalSum = TransactionUtils.calculateTotalSum(transactionList);
                    dataset.setValue(categoryType.name(), calculateTotalSum.abs());
                    break;
                case SAVING:
                    break;
                case TRANSFER:
                    break;
                case POCKET_MONEY:
                    break;
                default:
                    throw new AssertionError();
            }

        });

        return dataset;
    }

}

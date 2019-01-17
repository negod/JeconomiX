/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.utils.charts;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;
import java.time.Month;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JPanel;
import org.codehaus.plexus.util.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import se.backede.jeconomix.dto.TransactionReportDto;
import se.backede.jeconomix.utils.ReportUtils;
import se.backede.jeconomix.utils.TransactionUtils;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class LineChartUtils {

    public static void addLineChart(List<TransactionReportDto> reports, JPanel parent) {

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Year total",
                "", "",
                createDataset(reports, Boolean.TRUE),
                PlotOrientation.VERTICAL,
                false, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(parent.getPreferredSize());

        lineChart.setTitle(
                new org.jfree.chart.title.TextTitle("Year total",
                        new java.awt.Font("Courier New", java.awt.Font.PLAIN, 12)
                )
        );

        configureRenderer(lineChart);

        parent.setLayout(new BorderLayout());
        parent.add(chartPanel, BorderLayout.NORTH);
    }

    private static void configureRenderer(JFreeChart lineChart) {

        CategoryItemRenderer renderer = lineChart.getCategoryPlot().getRenderer();

        ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.HALF_ASCENT_CENTER, TextAnchor.BOTTOM_CENTER, 0);
        renderer.setBasePositiveItemLabelPosition(position);

        renderer.setBaseItemLabelGenerator(new CategoryItemLabelGenerator() {
            @Override
            public String generateLabel(CategoryDataset dataset, int series, int category) {
                if (series == 0) {
                    Number value = dataset.getValue(series, category);
                    String result = value.toString(); // could apply formatting here
                    return result;
                } else {
                    return "";
                }
            }

            @Override
            public String generateRowLabel(CategoryDataset cd, int i) {
                return null;
            }

            @Override
            public String generateColumnLabel(CategoryDataset cd, int i) {
                return null;
            }
        });
        renderer.setBaseItemLabelsVisible(true);
    }

    public static DefaultCategoryDataset createDataset(Map<String, List<TransactionReportDto>> reports, Boolean avg) {
        List<TransactionReportDto> collect = reports.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return createDataset(collect, avg);
    }

    public static DefaultCategoryDataset createDataset(List<TransactionReportDto> reports, Boolean avg) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        EnumMap<Month, BigDecimal> calculateTotalSumsPerMonth = ReportUtils.calculateTotalSumsPerMonth(reports);
        EnumMap<Month, BigDecimal> calculateAveragePerMonth = TransactionUtils.calculateAverageSumForAllTransactions(reports);

        addNewDataset(dataset, "Total", calculateTotalSumsPerMonth);

        if (avg) {
            addNewDataset(dataset, "Average", calculateAveragePerMonth);
        }
        return dataset;
    }

    private static void addNewDataset(DefaultCategoryDataset dataset, String lineTitle, EnumMap<Month, BigDecimal> reports) {
        for (Month month : Month.values()) {
            dataset.addValue(reports.getOrDefault(month, ZERO).abs(),
                    lineTitle,
                    StringUtils.capitalizeFirstLetter(
                            month.name().substring(0, 3).toLowerCase()
                    ));
        }
    }
}

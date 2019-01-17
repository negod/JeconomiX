/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.report;

import java.awt.BorderLayout;
import java.time.Year;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import lombok.extern.slf4j.Slf4j;
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
import org.jfree.ui.TextAnchor;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.TransactionHandler;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.dto.TransactionReportDto;
import se.backede.jeconomix.models.table.TransactionReportModel;
import se.backede.jeconomix.utils.charts.LineChartUtils;
import se.backede.jeconomix.utils.ReportUtils;
import se.backede.jeconomix.utils.TransactionUtils;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class TransactionReport extends javax.swing.JDialog {

    Integer CURRENT_YEAR = YearMonth.now().getYear();
    private final CategoryTypeEnum CATEGORY;
    private final Boolean AVERAGE;

    /**
     * Creates new form expenseReport
     *
     * @param parent
     * @param modal
     * @param type
     * @param average
     */
    public TransactionReport(java.awt.Frame parent, boolean modal, CategoryTypeEnum type, Boolean average) {
        super(parent, modal);
        initComponents();
        initData(type, average);
        this.CATEGORY = type;
        this.AVERAGE = average;
    }

    public final void initData(CategoryTypeEnum type, Boolean average) {
        yearLabel.setText(CURRENT_YEAR.toString());

        TransactionHandler.getInstance().getTransactionsByYearAndCategory(Year.of(CURRENT_YEAR), type).ifPresent(transactions -> {

            Map<String, List<TransactionDto>> filteredByCategoryName = transactions.stream()
                    .collect(Collectors.groupingBy(transaction -> transaction.getCompany().getCategory().getName()));

            List<TransactionReportDto> extractTransactionReportList = TransactionUtils.extractTransactionReportList(filteredByCategoryName);
            setTableData(extractTransactionReportList);

            Map<String, List<TransactionReportDto>> filterTransactionReportByCategory = TransactionUtils.filterTransactionReportByCategory(extractTransactionReportList);
            addLineChart(filterTransactionReportByCategory, average);

        });

    }

    public void addLineChart(Map<String, List<TransactionReportDto>> reports, Boolean average) {

        List<TransactionReportDto> collect = reports.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        LineChartUtils.addLineChart(collect, lineChartPanel);

//        JFreeChart lineChart = ChartFactory.createLineChart(
//                "TOTAL",
//                "MONTH", "Kr",
//                ReportUtils.createDataset(reports, average),
//                PlotOrientation.VERTICAL,
//                true, true, true);
//
//        ChartPanel chartPanel = new ChartPanel(lineChart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(lineChartPanel.getWidth(), lineChartPanel.getHeight()));
//
//        CategoryAxis axis = lineChart.getCategoryPlot().getDomainAxis();
//        CategoryItemRenderer renderer = lineChart.getCategoryPlot().getRenderer();
//
//        ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.HALF_ASCENT_CENTER, TextAnchor.BOTTOM_CENTER, 0);
//        renderer.setBasePositiveItemLabelPosition(position);
//
//        renderer.setBaseItemLabelGenerator(new CategoryItemLabelGenerator() {
//
//            @Override
//            public String generateLabel(CategoryDataset dataset, int series, int category) {
//                if (average) {
//                    if (series == 0) {
//                        Number value = dataset.getValue(series, category);
//                        String result = value.toString(); // could apply formatting here
//                        return result;
//                    }
//                } else {
//                    Number value = dataset.getValue(series, category);
//                    String result = value.toString(); // could apply formatting here
//                    return result;
//                }
//                return null;
//            }
//
//            @Override
//            public String generateRowLabel(CategoryDataset cd, int i) {
//                return null;
//            }
//
//            @Override
//            public String generateColumnLabel(CategoryDataset cd, int i) {
//                return null;
//            }
//        });
//        renderer.setBaseItemLabelsVisible(true);
//        lineChartPanel.setLayout(new BorderLayout());
//        lineChartPanel.add(chartPanel, BorderLayout.NORTH);
    }

    public void setTableData(List<TransactionReportDto> calculatedReport) {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        TransactionReportModel model = new TransactionReportModel(calculatedReport);
        reportTable.setModel(model);

        for (int i = 1; i < 14; i++) {
            reportTable.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        reportTable = new javax.swing.JTable();
        lineChartPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        prevYearBtn = new javax.swing.JButton();
        nextYearBtn = new javax.swing.JButton();
        yearLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        reportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Category", "Jan", "Feb", "Mar", "Apr", "Maj", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        reportTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(reportTable);
        if (reportTable.getColumnModel().getColumnCount() > 0) {
            reportTable.getColumnModel().getColumn(0).setResizable(false);
            reportTable.getColumnModel().getColumn(0).setPreferredWidth(300);
            reportTable.getColumnModel().getColumn(1).setResizable(false);
            reportTable.getColumnModel().getColumn(1).setPreferredWidth(70);
            reportTable.getColumnModel().getColumn(2).setResizable(false);
            reportTable.getColumnModel().getColumn(3).setResizable(false);
            reportTable.getColumnModel().getColumn(4).setResizable(false);
            reportTable.getColumnModel().getColumn(5).setResizable(false);
            reportTable.getColumnModel().getColumn(6).setResizable(false);
            reportTable.getColumnModel().getColumn(7).setResizable(false);
            reportTable.getColumnModel().getColumn(8).setResizable(false);
            reportTable.getColumnModel().getColumn(9).setResizable(false);
            reportTable.getColumnModel().getColumn(10).setResizable(false);
            reportTable.getColumnModel().getColumn(11).setResizable(false);
            reportTable.getColumnModel().getColumn(12).setResizable(false);
            reportTable.getColumnModel().getColumn(13).setResizable(false);
            reportTable.getColumnModel().getColumn(13).setPreferredWidth(90);
        }

        lineChartPanel.setBackground(new java.awt.Color(255, 255, 255));
        lineChartPanel.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout lineChartPanelLayout = new javax.swing.GroupLayout(lineChartPanel);
        lineChartPanel.setLayout(lineChartPanelLayout);
        lineChartPanelLayout.setHorizontalGroup(
            lineChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        lineChartPanelLayout.setVerticalGroup(
            lineChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 292, Short.MAX_VALUE)
        );

        prevYearBtn.setText("Prev year");
        prevYearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevYearBtnActionPerformed(evt);
            }
        });

        nextYearBtn.setText("Next year");
        nextYearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextYearBtnActionPerformed(evt);
            }
        });

        yearLabel.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        yearLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yearLabel.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(444, 444, 444)
                .addComponent(prevYearBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yearLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nextYearBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prevYearBtn)
                    .addComponent(nextYearBtn)
                    .addComponent(yearLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1211, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lineChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void reportTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportTableMouseClicked
        int selectedRow = reportTable.getSelectedRow();
        TransactionReportModel model = (TransactionReportModel) reportTable.getModel();
        TransactionReportDto transactionAt = model.getTransactionAt(selectedRow);
        new SingleTransactionReport(null, true, transactionAt).setVisible(true);
    }//GEN-LAST:event_reportTableMouseClicked

    private void prevYearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevYearBtnActionPerformed
        CURRENT_YEAR -= 1;
        initData(CATEGORY, AVERAGE);
    }//GEN-LAST:event_prevYearBtnActionPerformed

    private void nextYearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextYearBtnActionPerformed
        CURRENT_YEAR += 1;
        initData(CATEGORY, AVERAGE);
    }//GEN-LAST:event_nextYearBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel lineChartPanel;
    private javax.swing.JButton nextYearBtn;
    private javax.swing.JButton prevYearBtn;
    private javax.swing.JTable reportTable;
    private javax.swing.JLabel yearLabel;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.report;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.TransactionReportDto;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.database.CategoryHandler;
import se.backede.jeconomix.dto.TransactionReportDto;
import se.backede.jeconomix.models.table.TransactionReportModel;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class TransactionReport extends javax.swing.JDialog {

    /**
     * Creates new form expenseReport
     */
    public TransactionReport(java.awt.Frame parent, boolean modal, CategoryTypeEnum type) {
        super(parent, modal);
        initComponents();

        List<TransactionReportDto> calculatedReport = getCalculatedReport(type);

        setTableData(calculatedReport);
        addLineChart(calculatedReport);
    }

    public void addLineChart(List<TransactionReportDto> reports) {
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Total bills",
                "Month", "Kronor",
                createDataset(reports),
                PlotOrientation.VERTICAL,
                false, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(998, 135));
        lineChartPanel.setLayout(new BorderLayout());
        lineChartPanel.add(chartPanel, BorderLayout.NORTH);
    }

    private DefaultCategoryDataset createDataset(List<TransactionReportDto> reports) {

        String sumLineTitle = "Total Kr";
        String averageLineTitle = "Average Kr";
        BigDecimal averageCaclulated = BigDecimal.valueOf(0.00);

        Map<Month, BigDecimal> sums = new HashMap<>();

        List<Month> monthList = new LinkedList<>(Arrays.asList(Month.values()));
        Set<Month> presentMonths = new LinkedHashSet<>();

        for (Month month : monthList) {
            sums.put(month, BigDecimal.valueOf(0.00));
        }

        if (reports != null || !reports.isEmpty()) {

            for (Month month : monthList) {
                BigDecimal currentSum = sums.get(month);
                for (TransactionReportDto report : reports) {
                    BigDecimal get = report.getMonthReport().get(month);
                    if (get != null) {
                        presentMonths.add(month);
                        BigDecimal oldSum = currentSum;
                        currentSum = currentSum.add(report.getMonthReport().get(month));
                        sums.put(month, currentSum);
                    }
                }
            }

            BigDecimal average = BigDecimal.valueOf(0.00);
            for (Month presentMonth : presentMonths) {
                BigDecimal oldSum = average;
                average = average.add(sums.get(presentMonth));
            }
            averageCaclulated = average.divide(BigDecimal.valueOf(presentMonths.size()), MathContext.DECIMAL128);

        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(sums.get(Month.JANUARY), sumLineTitle, "Jan");
        dataset.addValue(sums.get(Month.FEBRUARY), sumLineTitle, "Feb");
        dataset.addValue(sums.get(Month.MARCH), sumLineTitle, "Mar");
        dataset.addValue(sums.get(Month.APRIL), sumLineTitle, "Apr");
        dataset.addValue(sums.get(Month.MAY), sumLineTitle, "May");
        dataset.addValue(sums.get(Month.JUNE), sumLineTitle, "Jun");
        dataset.addValue(sums.get(Month.JULY), sumLineTitle, "Jul");
        dataset.addValue(sums.get(Month.AUGUST), sumLineTitle, "Aug");
        dataset.addValue(sums.get(Month.SEPTEMBER), sumLineTitle, "Sep");
        dataset.addValue(sums.get(Month.OCTOBER), sumLineTitle, "Oct");
        dataset.addValue(sums.get(Month.NOVEMBER), sumLineTitle, "Nov");
        dataset.addValue(sums.get(Month.DECEMBER), sumLineTitle, "Dec");

        dataset.addValue(averageCaclulated, averageLineTitle, "Jan");
        dataset.addValue(averageCaclulated, averageLineTitle, "Feb");
        dataset.addValue(averageCaclulated, averageLineTitle, "Mar");
        dataset.addValue(averageCaclulated, averageLineTitle, "Apr");
        dataset.addValue(averageCaclulated, averageLineTitle, "May");
        dataset.addValue(averageCaclulated, averageLineTitle, "Jun");
        dataset.addValue(averageCaclulated, averageLineTitle, "Jul");
        dataset.addValue(averageCaclulated, averageLineTitle, "Aug");
        dataset.addValue(averageCaclulated, averageLineTitle, "Sep");
        dataset.addValue(averageCaclulated, averageLineTitle, "Oct");
        dataset.addValue(averageCaclulated, averageLineTitle, "Nov");
        dataset.addValue(averageCaclulated, averageLineTitle, "Dec");

        return dataset;
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

    public List<TransactionReportDto> getCalculatedReport(CategoryTypeEnum type) {

        List<TransactionReportDto> transactionReports = new LinkedList<>();
        Optional<List<CategoryDto>> allBillCategories = CategoryHandler.getInstance().getFilteredCategories(type);
        if (allBillCategories.isPresent()) {

            for (CategoryDto billCategoryDto : allBillCategories.get()) {
                TransactionReportDto report = new TransactionReportDto();
                Set<CompanyDto> company = billCategoryDto.getCompany();

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
                report.setCategory(billCategoryDto.getName());
                transactionReports.add(report);
            }
        }
        return transactionReports;
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
        ));
        reportTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(reportTable);

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
            .addGap(0, 135, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1010, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lineChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TransactionReport.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransactionReport.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransactionReport.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransactionReport.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TransactionReport dialog = new TransactionReport(new javax.swing.JFrame(), true, CategoryTypeEnum.EXPENSE);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel lineChartPanel;
    private javax.swing.JTable reportTable;
    // End of variables declaration//GEN-END:variables
}

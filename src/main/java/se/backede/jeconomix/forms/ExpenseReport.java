/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
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
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.ExpenseCategoryDto;
import se.backede.jeconomix.dto.ExpenseReportDto;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.models.table.ExpenseReportModel;
import se.backede.jeconomix.database.ExpenseCategoryHandler;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class ExpenseReport extends javax.swing.JDialog {

    /**
     * Creates new form expenseReport
     */
    public ExpenseReport(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        List<ExpenseReportDto> calculatedReport = getCalculatedReport();

        setTableData(calculatedReport);
        addLineChart(calculatedReport);
    }

    public void addLineChart(List<ExpenseReportDto> reports) {
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Total expenses",
                "Month", "Kronor",
                createDataset(reports),
                PlotOrientation.VERTICAL,
                false, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(998, 135));
        lineChartPanel.setLayout(new BorderLayout());
        lineChartPanel.add(chartPanel, BorderLayout.NORTH);
    }

    private DefaultCategoryDataset createDataset(List<ExpenseReportDto> reports) {

        String lineTitle = "Kronor";

        Map<Month, BigDecimal> sums = new HashMap<>();

        List<Month> monthList = new LinkedList<>(Arrays.asList(Month.values()));

        for (Month month : monthList) {
            sums.put(month, BigDecimal.valueOf(0.00));
        }

        for (Month month : monthList) {
            BigDecimal currentSum = sums.get(month);
            for (ExpenseReportDto report : reports) {
                BigDecimal get = report.getMonthReport().get(month);
                if (get != null) {
                    BigDecimal oldSum = currentSum;
                    currentSum = currentSum.add(report.getMonthReport().get(month));
                    sums.put(month, currentSum);
                }
            }
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(sums.get(Month.JANUARY), lineTitle, "Jan");
        dataset.addValue(sums.get(Month.FEBRUARY), lineTitle, "Feb");
        dataset.addValue(sums.get(Month.MARCH), lineTitle, "Mar");
        dataset.addValue(sums.get(Month.APRIL), lineTitle, "Apr");
        dataset.addValue(sums.get(Month.MAY), lineTitle, "May");
        dataset.addValue(sums.get(Month.JUNE), lineTitle, "Jun");
        dataset.addValue(sums.get(Month.JULY), lineTitle, "Jul");
        dataset.addValue(sums.get(Month.AUGUST), lineTitle, "Aug");
        dataset.addValue(sums.get(Month.SEPTEMBER), lineTitle, "Sep");
        dataset.addValue(sums.get(Month.OCTOBER), lineTitle, "Oct");
        dataset.addValue(sums.get(Month.NOVEMBER), lineTitle, "Nov");
        dataset.addValue(sums.get(Month.DECEMBER), lineTitle, "Dec");
        return dataset;
    }

    public void setTableData(List<ExpenseReportDto> calculatedReport) {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        ExpenseReportModel model = new ExpenseReportModel(calculatedReport);
        reportTable.setModel(model);

        for (int i = 1; i < 14; i++) {
            reportTable.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }

    }

    public List<ExpenseReportDto> getCalculatedReport() {

        List<ExpenseReportDto> transactionReports = new LinkedList<>();
        Optional<List<ExpenseCategoryDto>> allExpenseCategories = ExpenseCategoryHandler.getInstance().getAllExpenseCategories();
        if (allExpenseCategories.isPresent()) {

            for (ExpenseCategoryDto expenseCategoryDto : allExpenseCategories.get()) {
                ExpenseReportDto report = new ExpenseReportDto();
                Set<CompanyDto> company = expenseCategoryDto.getCompany();

                if (!expenseCategoryDto.getName().equals("Överföringar")) {
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
                    report.setCategory(expenseCategoryDto.getName());
                    transactionReports.add(report);
                }
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        reportTable = new javax.swing.JTable();
        lineChartPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        reportTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
                },
                new String[]{
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
    }// </editor-fold>                        

    private void reportTableMouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = reportTable.getSelectedRow();
        ExpenseReportModel model = (ExpenseReportModel) reportTable.getModel();
        ExpenseReportDto transactionAt = model.getTransactionAt(selectedRow);
        new SingleExpenseReport(null, true, transactionAt).setVisible(true);
    }

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
            java.util.logging.Logger.getLogger(ExpenseReport.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExpenseReport.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExpenseReport.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExpenseReport.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ExpenseReport dialog = new ExpenseReport(new javax.swing.JFrame(), true);
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

    // Variables declaration - do not modify                     
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel lineChartPanel;
    private javax.swing.JTable reportTable;
    // End of variables declaration                   
}

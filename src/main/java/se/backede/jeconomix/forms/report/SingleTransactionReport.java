/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.report;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.TransactionReportDto;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.models.combobox.CompanyComboBoxModel;
import se.backede.jeconomix.models.table.TransactionCompanyModel;
import se.backede.jeconomix.renderer.combobox.CompanyComboBoxRenderer;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class SingleTransactionReport extends javax.swing.JDialog {

    /**
     * Creates new form SingleTransactionReport
     */
    TransactionReportDto reports;
    Set<CompanyDto> companyList = new HashSet<CompanyDto>();
    Set<Integer> years = new HashSet<Integer>();
    SortedSet<Month> months = new TreeSet<Month>();

    private final String ALL_MONTHS = "All months";
    private final String ALL_YEARS = "All years";
    private final String ALL_COMPANIES = "All companies";

    public SingleTransactionReport(java.awt.Frame parent, boolean modal, TransactionReportDto reports) {
        super(parent, modal);
        this.reports = reports;
        initComponents();
        setTableData();
        addLineChart(reports);

        extractCompaniesFromTransactoins();
        extractYears();
        extractMonths();

        setYearComboBox();
        setMonthComboBox();
        setCompanyComboBox();
    }

    public void extractCompaniesFromTransactoins() {
        for (TransactionDto transction : reports.getTransctions()) {
            companyList.add(transction.getCompany());
        }
    }

    public void extractYears() {
        for (TransactionDto transction : reports.getTransctions()) {
            years.add(transction.getBudgetYear());
        }
    }

    public void extractMonths() {
        for (TransactionDto transction : reports.getTransctions()) {
            months.add(transction.getBudgetMonth());
        }
    }

    public void setYearComboBox() {
        yearComboBox.addItem(ALL_YEARS);
        for (Integer year : years) {
            yearComboBox.addItem(year.toString());
        }
    }

    public void setMonthComboBox() {
        monthComboBox.addItem(ALL_MONTHS);
        for (Month month : months) {
            monthComboBox.addItem(month.name().toString());
        }
    }

    public void setCompanyComboBox() {
        LinkedList companies = new LinkedList<>();
        CompanyDto blancCompany = new CompanyDto();
        blancCompany.setName(ALL_COMPANIES);
        companies.add(blancCompany);

        companies.addAll(companyList);
        CompanyComboBoxModel model = new CompanyComboBoxModel(new LinkedList<CompanyDto>(companies));
        model.setSelectedItem(companies.getFirst());
        companyComboBox.setModel(model);
        companyComboBox.setRenderer(new CompanyComboBoxRenderer());
    }

    public void addLineChart(TransactionReportDto reports) {
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Total expenses",
                "Month", "Kronor",
                createDataset(reports),
                PlotOrientation.VERTICAL,
                false, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(lineChartPanel.getWidth(), lineChartPanel.getHeight()));
        lineChartPanel.setLayout(new BorderLayout());
        lineChartPanel.add(chartPanel, BorderLayout.NORTH);
    }

    public void filter() {

        companyComboBox.setEnabled(false);
        yearComboBox.setEnabled(false);
        monthComboBox.setEnabled(false);

        LinkedList<TransactionDto> allTrasactions = new LinkedList<TransactionDto>(reports.getTransctions());

        CompanyDto company = (CompanyDto) companyComboBox.getSelectedItem();

        String yearString = (String) yearComboBox.getSelectedItem();
        Integer year = Integer.parseInt("0");
        if (!yearString.equals(ALL_YEARS)) {
            year = Integer.parseInt(yearString);
        }

        String monthString = (String) monthComboBox.getSelectedItem();
        Month month = null;
        if (monthString != null) {
            if (!monthString.equals(ALL_MONTHS)) {
                month = Month.valueOf(monthString);
            }
        }

        LinkedList<TransactionDto> filteredCompanies = new LinkedList<TransactionDto>(reports.getTransctions());

        if (company != null) {
            if (!company.getName().equals(ALL_COMPANIES)) {
                filteredCompanies = allTrasactions.stream()
                        .filter(line -> line.getCompany().equals(company))
                        .collect(Collectors.toCollection(LinkedList::new));
            }
        }

        LinkedList<TransactionDto> filteredByYear = (LinkedList) filteredCompanies.clone();
        if (yearString != null) {
            if (!yearString.equals(ALL_YEARS)) {
                for (TransactionDto filteredTransaction : filteredCompanies) {
                    if (filteredTransaction.getBudgetYear() != year) {
                        filteredByYear.remove(filteredTransaction);
                    }
                }
            }
        }

        LinkedList<TransactionDto> filteredByMonth = (LinkedList) filteredByYear.clone();;
        if (monthString != null) {
            if (!monthString.equals(ALL_MONTHS)) {
                for (TransactionDto filteredTransaction : filteredByYear) {
                    if (filteredTransaction.getBudgetMonth() != month) {
                        filteredByMonth.remove(filteredTransaction);
                    }
                }
            }
        }

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();

        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        TransactionCompanyModel transModel = new TransactionCompanyModel(new HashSet<TransactionDto>(filteredByMonth));

        transactionTable.setModel(transModel);

        transactionTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);

        transactionSumLabel.setText(transModel.getSum().toString().concat(" Kr"));
        categoryNameLabel.setText(reports.getCategory());

        companyComboBox.setEnabled(true);
        yearComboBox.setEnabled(true);
        monthComboBox.setEnabled(true);

    }

    private DefaultCategoryDataset createDataset(TransactionReportDto reports) {

        String lineTitle = "Kronor";

        Map<Month, BigDecimal> sums = new HashMap<>();

        List<Month> monthList = new LinkedList<>(Arrays.asList(Month.values()));

        for (Month month : monthList) {
            sums.put(month, BigDecimal.valueOf(0));
        }

        for (TransactionDto transaction : reports.getTransctions()) {
            for (Month month : monthList) {
                if (transaction.getBudgetMonth().equals(month)) {
                    BigDecimal currentSum = sums.get(month);
                    if (transaction.getSum() != null) {
                        BigDecimal newSum = currentSum.add(transaction.getSum());
                        sums.put(month, newSum);
                    }
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

    public void setTableData() {

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

        TransactionCompanyModel transModel = new TransactionCompanyModel(new HashSet<TransactionDto>(reports.getTransctions()));
        transactionTable.setModel(transModel);
        transactionTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);

        transactionSumLabel.setText(transModel.getSum().toString().concat(" Kr"));
        categoryNameLabel.setText(reports.getCategory());
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
        transactionTable = new javax.swing.JTable();
        lineChartPanel = new javax.swing.JPanel();
        sumLabel = new javax.swing.JLabel();
        transactionSumLabel = new javax.swing.JLabel();
        monthComboBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        companyComboBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        yearComboBox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        categoryNameLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        transactionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(transactionTable);

        lineChartPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout lineChartPanelLayout = new javax.swing.GroupLayout(lineChartPanel);
        lineChartPanel.setLayout(lineChartPanelLayout);
        lineChartPanelLayout.setHorizontalGroup(
            lineChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        lineChartPanelLayout.setVerticalGroup(
            lineChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 153, Short.MAX_VALUE)
        );

        sumLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        sumLabel.setText("Sum:");

        transactionSumLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        transactionSumLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        transactionSumLabel.setText("jLabel1");

        monthComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthComboBoxActionPerformed(evt);
            }
        });

        jLabel1.setText("Select Month");

        companyComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                companyComboBoxActionPerformed(evt);
            }
        });

        jLabel2.setText("Select Company");

        yearComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearComboBoxActionPerformed(evt);
            }
        });

        jLabel3.setText("Select year");

        categoryNameLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        categoryNameLabel.setText("jLabel4");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("Filter");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(categoryNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(sumLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(transactionSumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(monthComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addComponent(companyComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)))
                .addContainerGap())
            .addComponent(lineChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(categoryNameLabel)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(monthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(9, 9, 9)
                        .addComponent(companyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sumLabel)
                    .addComponent(transactionSumLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lineChartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void yearComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearComboBoxActionPerformed
        filter();
    }//GEN-LAST:event_yearComboBoxActionPerformed

    private void monthComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthComboBoxActionPerformed
        filter();
    }//GEN-LAST:event_monthComboBoxActionPerformed

    private void companyComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_companyComboBoxActionPerformed
        filter();
    }//GEN-LAST:event_companyComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel categoryNameLabel;
    private javax.swing.JComboBox<String> companyComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel lineChartPanel;
    private javax.swing.JComboBox<String> monthComboBox;
    private javax.swing.JLabel sumLabel;
    private javax.swing.JLabel transactionSumLabel;
    private javax.swing.JTable transactionTable;
    private javax.swing.JComboBox<String> yearComboBox;
    // End of variables declaration//GEN-END:variables
}

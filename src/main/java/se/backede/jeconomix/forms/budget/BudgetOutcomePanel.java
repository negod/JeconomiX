/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.budget;

import java.awt.BorderLayout;
import java.time.YearMonth;
import javax.swing.JPanel;
import org.apache.commons.lang3.text.WordUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.models.table.BudgetOutcomeModel;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BudgetOutcomePanel extends javax.swing.JPanel {

    /**
     * Creates new form BudgetOutcomePanel
     *
     */
    public BudgetOutcomePanel() {
        initComponents();
    }

    public void init(final CategoryTypeEnum category, final YearMonth budgetMonth) {
        BudgetOutcomeModel model = new BudgetOutcomeModel(category, budgetMonth);
        valueTable.setModel(model);
        setChart(chartPanel, model, category);

        totalLabel.setText(model.getTotalBudgetSum().subtract(model.getTotalOutcomeSum().abs()).toPlainString().concat(" Kr"));

    }

    private void setChart(JPanel panel, BudgetOutcomeModel model, CategoryTypeEnum category) {
        JFreeChart barChart = ChartFactory.createBarChart(
                category.name() + " budget vs outcome",
                "",
                "",
                createDataset(model),
                PlotOrientation.HORIZONTAL,
                true, true, false);

        barChart.setTitle(
                new org.jfree.chart.title.TextTitle(WordUtils.capitalizeFully(category.name()) + " budget vs outcome",
                        new java.awt.Font("Courier New", java.awt.Font.PLAIN, 12)
                )
        );

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(panel.getWidth(), panel.getHeight()));
        panel.setLayout(new BorderLayout());
        panel.add(chartPanel, BorderLayout.NORTH);
    }

    private CategoryDataset createDataset(BudgetOutcomeModel model) {
        final String budget = "Budget";
        final String outcome = "Outcome";
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(model.getTotalBudgetSum().abs(), budget, "Kr");
        dataset.addValue(model.getTotalOutcomeSum().abs(), outcome, "Kr");

        return dataset;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator2 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        totalLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        valueTable = new javax.swing.JTable();
        chartPanel = new javax.swing.JPanel();

        totalLabel.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        totalLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalLabel.setText("Total");

        valueTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(valueTable);

        javax.swing.GroupLayout chartPanelLayout = new javax.swing.GroupLayout(chartPanel);
        chartPanel.setLayout(chartPanelLayout);
        chartPanelLayout.setHorizontalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        chartPanelLayout.setVerticalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 157, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
            .addComponent(jSeparator2)
            .addComponent(jSeparator1)
            .addComponent(totalLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chartPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel totalLabel;
    private javax.swing.JTable valueTable;
    // End of variables declaration//GEN-END:variables
}

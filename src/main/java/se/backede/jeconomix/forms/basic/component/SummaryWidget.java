/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.basic.component;

import java.awt.Color;
import java.math.BigDecimal;
import java.time.YearMonth;
import org.apache.commons.lang3.StringUtils;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.events.UiEvent;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class SummaryWidget extends javax.swing.JPanel {

    boolean hidden = false;
    YearMonth CURRENT = YearMonth.now();

    /**
     * Creates new form SummaryWidget
     */
    public SummaryWidget() {
        initComponents();
    }

    public void init(CategoryTypeEnum category, BigDecimal sum, BigDecimal budget, YearMonth current) {

        this.CURRENT = current;
        budgetProgressBar.setMaximum(budget.intValueExact());
        budgetProgressBar.setValue(sum.intValueExact());
        budgetProgressBar.setString(String.valueOf(sum.intValueExact()).concat(" Kr"));

        titleLabel.setText(StringUtils.capitalize(category.name().toLowerCase()));
        sumLabel.setText(sum.toPlainString());

        switch (category) {
            case INCOME:
            case SAVING:
                titlePanel.setBackground(Color.GREEN);
                sumLabel.setBackground(Color.GREEN);
                break;
            case EXPENSE:
            case BILL:
            case CREDIT_CARD:
            case LOAN:
                titlePanel.setBackground(Color.RED);
                sumLabel.setBackground(Color.RED);
                break;
            case TRANSFER:
            case POCKET_MONEY:
                titlePanel.setBackground(Color.YELLOW);
                sumLabel.setBackground(Color.YELLOW);
                break;
            default:
                throw new AssertionError();
        }

    }

    public void showOrHide() {
        EventController.getInstance().notifyObservers(hidden ? UiEvent.SHOW : UiEvent.HIDE, () -> CURRENT);
        hidden = !hidden;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        titlePanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        sumPanel = new javax.swing.JPanel();
        sumLabel = new javax.swing.JLabel();
        progressPanel = new javax.swing.JPanel();
        budgetProgressBar = new javax.swing.JProgressBar();

        setMinimumSize(new java.awt.Dimension(400, 25));
        setPreferredSize(new java.awt.Dimension(500, 25));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {20};
        layout.rowHeights = new int[] {1};
        setLayout(layout);

        titlePanel.setPreferredSize(new java.awt.Dimension(150, 25));
        titlePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                titlePanelMouseClicked(evt);
            }
        });
        titlePanel.setLayout(new java.awt.GridBagLayout());

        titleLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        titleLabel.setText("Transaction type");
        titleLabel.setPreferredSize(new java.awt.Dimension(150, 25));
        titleLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                titleLabelMouseClicked(evt);
            }
        });
        titlePanel.add(titleLabel, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(titlePanel, gridBagConstraints);

        sumPanel.setBackground(new java.awt.Color(255, 255, 255));
        sumPanel.setPreferredSize(new java.awt.Dimension(100, 25));
        sumPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sumPanelMouseClicked(evt);
            }
        });
        sumPanel.setLayout(new java.awt.GridBagLayout());

        sumLabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        sumLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sumLabel.setText("Budget sum");
        sumLabel.setPreferredSize(new java.awt.Dimension(100, 25));
        sumLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sumLabelMouseClicked(evt);
            }
        });
        sumPanel.add(sumLabel, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(sumPanel, gridBagConstraints);

        progressPanel.setBackground(new java.awt.Color(255, 255, 255));
        progressPanel.setMinimumSize(new java.awt.Dimension(175, 25));
        progressPanel.setPreferredSize(new java.awt.Dimension(150, 25));
        progressPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SummaryWidget.this.mouseClicked(evt);
            }
        });
        progressPanel.setLayout(new java.awt.GridBagLayout());

        budgetProgressBar.setBorderPainted(false);
        budgetProgressBar.setMaximumSize(new java.awt.Dimension(32767, 32767));
        budgetProgressBar.setMinimumSize(new java.awt.Dimension(0, 0));
        budgetProgressBar.setPreferredSize(new java.awt.Dimension(150, 25));
        budgetProgressBar.setString("0 Kr");
        budgetProgressBar.setStringPainted(true);
        budgetProgressBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                budgetProgressBarMouseClicked(evt);
            }
        });
        progressPanel.add(budgetProgressBar, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(progressPanel, gridBagConstraints);
        progressPanel.getAccessibleContext().setAccessibleDescription("");
        progressPanel.getAccessibleContext().setAccessibleParent(progressPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void titlePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titlePanelMouseClicked
        showOrHide();
    }//GEN-LAST:event_titlePanelMouseClicked

    private void titleLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titleLabelMouseClicked
        showOrHide();
    }//GEN-LAST:event_titleLabelMouseClicked

    private void sumLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sumLabelMouseClicked
        showOrHide();
    }//GEN-LAST:event_sumLabelMouseClicked

    private void sumPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sumPanelMouseClicked
        showOrHide();
    }//GEN-LAST:event_sumPanelMouseClicked

    private void budgetProgressBarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_budgetProgressBarMouseClicked
        showOrHide();
    }//GEN-LAST:event_budgetProgressBarMouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        showOrHide();
    }//GEN-LAST:event_formMouseClicked

    private void mouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseClicked
        showOrHide();
    }//GEN-LAST:event_mouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar budgetProgressBar;
    private javax.swing.JPanel progressPanel;
    private javax.swing.JLabel sumLabel;
    private javax.swing.JPanel sumPanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel titlePanel;
    // End of variables declaration//GEN-END:variables
}
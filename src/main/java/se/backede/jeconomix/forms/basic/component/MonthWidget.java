/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.basic.component;

import java.awt.Dimension;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.events.UiEvent;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class MonthWidget extends javax.swing.JPanel {

    YearMonth CURRENT_YEAR_MONTH;

    /**
     * Creates new form MonthWidget
     */
    public MonthWidget() {
        initComponents();
    }

    public void setVisible() {

    }

    public void init(YearMonth currentYearMonth) {
        CURRENT_YEAR_MONTH = currentYearMonth;

        summaryWidget1.init(CategoryTypeEnum.BILL, BigDecimal.ONE, BigDecimal.TEN, currentYearMonth);

        Consumer<YearMonth> setVisible = (dto) -> {
            if (dto.getMonth().equals(CURRENT_YEAR_MONTH.getMonth()) && dto.getYear() == CURRENT_YEAR_MONTH.getYear()) {
                this.add(budgetTable1);
                this.revalidate();
                // this.repaint();
                log.error("SHOWING - CurrentSize = {}", this.getSize().toString());
            }
        };
        EventController.getInstance().addObserver(UiEvent.SHOW, setVisible);

        Consumer<YearMonth> hide = (dto) -> {
            if (dto.getMonth().equals(CURRENT_YEAR_MONTH.getMonth()) && dto.getYear() == CURRENT_YEAR_MONTH.getYear()) {
                this.remove(budgetTable1);
                this.setSize(new Dimension(600, 100));
                this.revalidate();
                //this.repaint();
                log.error("HIDING - CurrentSize = {}", this.getSize().toString());
            }
        };
        EventController.getInstance().addObserver(UiEvent.HIDE, hide);

    }

//
//    public void removeBudgetExpense(JTable table) {
//        BudgetModel model = (BudgetModel) table.getModel();
//        model.removeBudgetExpenseAt(table.getSelectedRow());
//    }
//
//    private void addBudgetLine(CategoryTypeEnum category) {
//        AddBudgetLine budgetLine = new AddBudgetLine(CURRENT_YEAR_MONTH);
//        budgetLine.init(category);
//        budgetLine.setVisible(true);
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        summaryWidget1 = new se.backede.jeconomix.forms.basic.component.SummaryWidget();
        budgetTable1 = new se.backede.jeconomix.forms.budget.BudgetTable();

        setMaximumSize(new java.awt.Dimension(600, 400));
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(600, 400));
        add(summaryWidget1);
        add(budgetTable1);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private se.backede.jeconomix.forms.budget.BudgetTable budgetTable1;
    private se.backede.jeconomix.forms.basic.component.SummaryWidget summaryWidget1;
    // End of variables declaration//GEN-END:variables
}

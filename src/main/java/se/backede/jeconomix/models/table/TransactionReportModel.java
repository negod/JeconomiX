/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.models.table;

import java.math.BigDecimal;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import se.backede.jeconomix.dto.TransactionReportDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class TransactionReportModel extends AbstractTableModel {

    private LinkedList<TransactionReportDto> reports;
    private BigDecimal sum = BigDecimal.valueOf(0);

    public TransactionReportModel(List<TransactionReportDto> reports) {
        this.reports = new LinkedList<TransactionReportDto>(reports);
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Category";
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            case 13:
                return "Sum";
        }
        return "";
    }

    @Override
    public int getRowCount() {
        return reports.size();
    }

    @Override
    public int getColumnCount() {
        return 14;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "";
        TransactionReportDto report = reports.get(rowIndex);
        switch (columnIndex) {
            case 0:
                value = report.getCategory();
                break;
            case 1:
                if (report.getMonthReport().containsKey(Month.JANUARY)) {
                    value = report.getMonthReport().get(Month.JANUARY).toString() + " Kr";
                } else {
                    value = "";
                }
                break;
            case 2:
                if (report.getMonthReport().containsKey(Month.FEBRUARY)) {
                    value = report.getMonthReport().get(Month.FEBRUARY).toString() + " Kr";
                } else {
                    value = "";
                }
                break;
            case 3:
                if (report.getMonthReport().containsKey(Month.MARCH)) {
                    value = report.getMonthReport().get(Month.MARCH).toString() + " Kr";
                } else {
                    value = "";
                }
                break;
            case 4:
                if (report.getMonthReport().containsKey(Month.APRIL)) {
                    value = report.getMonthReport().get(Month.APRIL).toString() + " Kr";
                } else {
                    value = "";
                }
                break;
            case 5:
                if (report.getMonthReport().containsKey(Month.MAY)) {
                    value = report.getMonthReport().get(Month.MAY).toString() + " Kr";
                } else {
                    value = "";
                }
                break;
            case 6:
                if (report.getMonthReport().containsKey(Month.JUNE)) {
                    value = report.getMonthReport().get(Month.JUNE).toString() + " Kr";
                } else {
                    value = "";
                }
                break;
            case 7:
                if (report.getMonthReport().containsKey(Month.JULY)) {
                    value = report.getMonthReport().get(Month.JULY).toString() + " Kr";
                } else {
                    value = "";
                }
                break;
            case 8:
                if (report.getMonthReport().containsKey(Month.AUGUST)) {
                    value = report.getMonthReport().get(Month.AUGUST).toString() + " Kr";
                } else {
                    value = "";
                }
                break;
            case 9:
                if (report.getMonthReport().containsKey(Month.SEPTEMBER)) {
                    value = report.getMonthReport().get(Month.SEPTEMBER).toString() + " Kr";
                } else {
                    value = "";
                }
                break;
            case 10:
                if (report.getMonthReport().containsKey(Month.OCTOBER)) {
                    value = report.getMonthReport().get(Month.OCTOBER).toString() + " Kr";
                } else {
                    value = "";
                }
                break;
            case 11:
                if (report.getMonthReport().containsKey(Month.NOVEMBER)) {
                    value = report.getMonthReport().get(Month.NOVEMBER).toString() + " Kr";
                } else {
                    value = "";
                }
                break;
            case 12:
                if (report.getMonthReport().containsKey(Month.DECEMBER)) {
                    value = report.getMonthReport().get(Month.DECEMBER).toString() + " Kr";
                } else {
                    value = "";
                }
                break;
            case 13:
                value = report.getSum().toString() + " Kr";
                break;
        }
        return value;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return String.class;
            case 6:
                return String.class;
            case 7:
                return String.class;
            case 8:
                return String.class;
            case 9:
                return String.class;
            case 10:
                return String.class;
            case 11:
                return String.class;
            case 12:
                return String.class;
            case 13:
                return String.class;

        }
        return null;
    }

    public BigDecimal getSum() {
        return sum;
    }

    /* Override this if you want the values to be editable...
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //....
    }
     */
    /**
     * This will return the report at the specified row...
     *
     * @param row
     * @return
     */
    public TransactionReportDto getTransactionAt(int row) {
        return reports.get(row);
    }

}

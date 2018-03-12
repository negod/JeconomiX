/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.models.table;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Set;
import javax.swing.table.AbstractTableModel;
import se.backede.jeconomix.dto.TransactionDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class TransactionModel extends AbstractTableModel {

    private LinkedList<TransactionDto> transactions;
    private BigDecimal sum = BigDecimal.valueOf(0);

    public TransactionModel(Set<TransactionDto> transactions) {
        this.transactions = new LinkedList<TransactionDto>(transactions);

        for (TransactionDto transaction : transactions) {
            BigDecimal result = sum.add(transaction.getSum());
            sum = result;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Date";
            case 1:
                return "Sum";
        }
        return "";
    }

    @Override
    public int getRowCount() {
        return transactions.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "";
        TransactionDto transaction = transactions.get(rowIndex);
        switch (columnIndex) {
            case 0:
                value = transaction.getTransDate();
                break;
            case 1:
                value = transaction.getSum().toString() + " Kr";
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
                return BigDecimal.class;
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
     * This will return the transaction at the specified row...
     *
     * @param row
     * @return
     */
    public TransactionDto getCompanyAt(int row) {
        return transactions.get(row);
    }

}

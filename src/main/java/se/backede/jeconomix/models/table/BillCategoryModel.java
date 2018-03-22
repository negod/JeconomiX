/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.models.table;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import se.backede.jeconomix.dto.BillCategoryDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BillCategoryModel extends AbstractTableModel {

    private LinkedList<BillCategoryDto> billCategories;
    private BigDecimal sum = BigDecimal.valueOf(0);

    public BillCategoryModel(List<BillCategoryDto> billCategories) {
        this.billCategories = new LinkedList<BillCategoryDto>(billCategories);
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Category";
            case 1:
                return "Type";
        }
        return "";
    }

    @Override
    public int getRowCount() {
        return billCategories.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "";
        BillCategoryDto billCategory = billCategories.get(rowIndex);
        switch (columnIndex) {
            case 0:
                value = billCategory.getName();
                break;
            case 1:
                if (billCategory.getCategoryType() != null) {
                    if (billCategory.getCategoryType().getType() != null) {
                        value = billCategory.getCategoryType().getType().name();
                    }
                } else {
                    value = "";
                }
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
        }
        return null;
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
    public BillCategoryDto getBillCategoryAt(int row) {
        return billCategories.get(row);
    }

}

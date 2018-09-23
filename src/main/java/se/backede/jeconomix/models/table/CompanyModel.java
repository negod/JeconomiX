/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.models.table;

import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import se.backede.jeconomix.dto.CompanyDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CompanyModel extends AbstractTableModel {

    private LinkedList<CompanyDto> companies;

    public CompanyModel(List<CompanyDto> companies) {
        this.companies = new LinkedList<CompanyDto>(companies);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        switch (col) {
            case 0:
                return false;
            case 1:
                return true;
            case 2:
                return false;
            default:
                return false;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Company";
            case 1:
                return "Category";
            case 2:
                return "Category type";
        }
        return "";
    }

    @Override
    public int getRowCount() {
        return companies.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "";
        CompanyDto company = companies.get(rowIndex);
        switch (columnIndex) {
            case 0:
                value = company.getName();
                break;
            case 1:
                if (company.getCategory() != null) {
                    value = company.getCategory().getName();
                }
                break;
            case 2:
                if (company.getCategory() != null) {
                    if (company.getCategory().getCategoryType() != null) {
                        value = company.getCategory().getCategoryType().getType().name();
                    }
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
            case 2:
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
     * This will return the company at the specified row...
     *
     * @param row
     * @return
     */
    public CompanyDto getCompanyAt(int row) {
        return companies.get(row);
    }

}

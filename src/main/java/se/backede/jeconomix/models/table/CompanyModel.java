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
import se.backede.jeconomix.database.entity.Company;

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
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Company";
            case 1:
                return "Expense category";
        }
        return "";
    }

    @Override
    public int getRowCount() {
        return companies.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
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
                if (company.getExpenseCategory() != null) {
                    value = company.getExpenseCategory().getName();
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
     * This will return the company at the specified row...
     *
     * @param row
     * @return
     */
    public CompanyDto getCompanyAt(int row) {
        return companies.get(row);
    }

}

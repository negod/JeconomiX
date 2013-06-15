/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.model;

import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.negod.DB.Entities.budget.Company;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Joakim
 */
public class CompanyTableModel extends ListDataModel<Company> implements SelectableDataModel<Company>, Serializable{
    
      public CompanyTableModel(List<Company> data) {
        super(data);
    }

    @Override
    public Object getRowKey(Company data) {
        return data.getCompanyName();
    }

    @Override
    public Company getRowData(String rowKey) {
        List<Company> entities = (List<Company>) getWrappedData();
        for (Company data : entities) {
            if (data.getCompanyName().equals(rowKey)) {
                return data;
            }
        }
        return null;
    }
    
}

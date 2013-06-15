/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.model;

import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.negod.DB.Entities.budget.TransactionType;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Joakim
 */
public class TransactionTypeTableModel extends ListDataModel<TransactionType> implements SelectableDataModel<TransactionType>, Serializable{
    
      public TransactionTypeTableModel(List<TransactionType> data) {
        super(data);
    }

    @Override
    public Object getRowKey(TransactionType data) {
        return data.getTransactionName();
    }

    @Override
    public TransactionType getRowData(String rowKey) {
        List<TransactionType> entities = (List<TransactionType>) getWrappedData();
        for (TransactionType data : entities) {
            if (data.getTransactionName().equals(rowKey)) {
                return data;
            }
        }
        return null;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.model;

import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.negod.DB.Entities.userhandling.Groups;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Joakim
 */
public class UserGroupTableModel extends ListDataModel<Groups> implements SelectableDataModel<Groups>, Serializable {

    public UserGroupTableModel(List<Groups> data) {
        super(data);
    }

    @Override
    public Object getRowKey(Groups data) {
        return data.getGroupName();
    }

    @Override
    public Groups getRowData(String rowKey) {
        List<Groups> entities = (List<Groups>) getWrappedData();
        for (Groups data : entities) {
            if (data.getGroupName().equals(rowKey)) {
                return data;
            }
        }
        return null;
    }
}

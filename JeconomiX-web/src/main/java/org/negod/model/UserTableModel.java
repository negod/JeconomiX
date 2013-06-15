/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.model;

import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.negod.DB.Entities.userhandling.Users;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Joakim
 */
public class UserTableModel extends ListDataModel<Users> implements SelectableDataModel<Users>, Serializable {

    public UserTableModel(List<Users> data) {
        super(data);
    }

    @Override
    public Object getRowKey(Users data) {
        return data.getUsername();
    }

    @Override
    public Users getRowData(String rowKey) {
        List<Users> entities = (List<Users>) getWrappedData();
        for (Users data : entities) {
            if (data.getUsername().equals(rowKey)) {
                return data;
            }
        }
        return null;
    }
}

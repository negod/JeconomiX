/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.backing;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.negod.DB.Entities.budget.TransactionType;
import org.negod.DB.Enums.TransactionTypeEnum;
import org.negod.backing.managedbeans.UserMb;
import org.negod.ejb.budgethandling.TransactionHandling;
import org.negod.model.TransactionTypeTableModel;
import org.negod.utils.FacesUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Joakim
 */
@ManagedBean(name = "transactionTypeBacking")
@ViewScoped
public class TransactionTypeBacking implements Serializable {

    @ManagedProperty(value = "#{userMb}")
    private UserMb user;
    @EJB
    TransactionHandling db;
    private List<TransactionType> allData;
    private List<TransactionType> filteredData;
    private TransactionType selectedData;
    private TransactionType newData;
    private TransactionTypeTableModel tableModel;
    private TransactionTypeEnum transType[];

    @PostConstruct
    public void setup() {
        try {
            newData = new TransactionType();
            newData.setTransactionType(TransactionTypeEnum.expense);
            allData = db.getAllTransactionTypes(user.getUsername());
            tableModel = new TransactionTypeTableModel(allData);
        } catch (Exception ex) {
            Logger.getLogger(UsersBacking.class.getName()).log(Level.SEVERE, "Failed to get data from DB (setup)", ex);
        }
        if (!allData.isEmpty()) {
            setSelectedTableData(tableModel.getRowData(allData.get(0).getTransactionName()));
        } else {
            setSelectedTableData(newData);
        }
    }

    public UserMb getUser() {
        return user;
    }

    public void setUser(UserMb user) {
        this.user = user;
    }

    public TransactionTypeEnum[] getTransType() {
        return TransactionTypeEnum.values();
    }

    public void setTransType(TransactionTypeEnum[] transType) {
        this.transType = transType;
    }

    public TransactionTypeTableModel getTableModel() {
        return this.tableModel;
    }

    public void setTableModel(TransactionTypeTableModel model) {
        this.tableModel = model;
    }

    public void setSelectedTableData(TransactionType selectedData) {
        this.selectedData = selectedData;
    }

    public TransactionType getSelectedTableData() {
        return this.selectedData;
    }

    public List<TransactionType> getFilteredTableData() {
        return this.filteredData;
    }

    public void setFilteredTableData(List<TransactionType> filteredUserss) {
        this.filteredData = filteredUserss;
    }

    public TransactionType getNewData() {
        return newData;
    }

    public void setNewData(TransactionType newUsers) {
        this.newData = newUsers;
    }

    public void onRowSelect(SelectEvent event) {
    }

    public void onRowUnselect(UnselectEvent event) {
    }

    public void updateData(ActionEvent actionEvent) {
        try {
            db.updateTransactinType(getSelectedTableData());
            FacesUtils.addInfoMessage(FacesUtils.ResourceBundles.facesMessages, "transactionTypeUpdated", null);
        } catch (Exception ex) {
            Logger.getLogger(UsersBacking.class.getName()).log(Level.SEVERE, "Failed to update TransactionType (updateUsers)", ex);
        }

    }

    public void deleteData(ActionEvent actionEvent) {
    }

    public void createData(ActionEvent actionEvent) {
        try {
            getNewData().setUser(this.getUser().getUser());
            db.addTransactionType(getNewData());
            newData = new TransactionType();
            newData.setTransactionType(TransactionTypeEnum.expense);
            FacesUtils.addInfoMessage(FacesUtils.ResourceBundles.facesMessages, "transactionTypeAdded", null);
        } catch (Exception e) {
        }
    }

    private TransactionType addData(TransactionType data) {
        return new TransactionType();
    }
}

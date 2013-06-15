/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.negod.DB.Entities.budget.Company;
import org.negod.DB.Entities.budget.TransactionType;
import org.negod.backing.managedbeans.UserMb;
import org.negod.ejb.budgethandling.CompanyHandling;
import org.negod.ejb.budgethandling.TransactionHandling;
import org.negod.model.CompanyTableModel;
import org.negod.utils.FacesUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Joakim
 */
@ManagedBean(name = "companyBacking")
@ViewScoped
public class CompanyBacking implements Serializable {

    @EJB
    CompanyHandling db;
    @EJB
    TransactionHandling transactionDb;
    private List<Company> allData;
    private List<Company> filteredData;
    private Company selectedData;
    private Company newData;
    private CompanyTableModel tableModel;
    private List<TransactionType> transactionTypes;
    private DualListModel<TransactionType> dualListModel;
    private DualListModel<TransactionType> selectedDualListModel;
    @ManagedProperty(value = "#{userMb}")
    private UserMb user;

    @PostConstruct
    public void setup() {
        try {
            allData = db.getAllCompanies(user.getUsername());
            tableModel = new CompanyTableModel(allData);
            transactionTypes = transactionDb.getAllTransactionTypes("admin");
            selectedData = new Company();
            selectedData.setTransactionTypes(Collections.EMPTY_LIST);
            refreshSelectedDualList();
            refreshDualList();
        } catch (Exception ex) {
            Logger.getLogger(UsersBacking.class.getName()).log(Level.SEVERE, "Failed to get data from DB (setup)", ex);
        }
        if (!allData.isEmpty()) {
            setSelectedTableData(tableModel.getRowData(allData.get(0).getCompanyName()));
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

    public DualListModel<TransactionType> getSelectedDualListModel() {
        return selectedDualListModel;
    }

    public void setSelectedDualListModel(DualListModel<TransactionType> selectedDualListModel) {
        this.selectedDualListModel = selectedDualListModel;
    }

    public DualListModel<TransactionType> getDualListModel() {
        return this.dualListModel;
    }

    public void setDualListModel(DualListModel<TransactionType> model) {
        this.dualListModel = model;
    }

    private void refreshSelectedDualList() {
        Set<TransactionType> filteredSet = new HashSet<TransactionType>(getTransactionTypes());

        for (TransactionType actual : selectedData.getTransactionTypes()) {
            if (filteredSet.contains(actual)) {
                filteredSet.remove(actual);
            }
        }

        List<TransactionType> source = new ArrayList<TransactionType>(filteredSet);
        List<TransactionType> target = this.selectedData.getTransactionTypes();
        this.selectedDualListModel = new DualListModel<TransactionType>(source, target);
    }

    private void refreshDualList() {
        newData = new Company();
        newData.setTransactionTypes(Collections.EMPTY_LIST);
        List<TransactionType> source = this.getTransactionTypes();
        List<TransactionType> target = this.newData.getTransactionTypes();
        this.dualListModel = new DualListModel<TransactionType>(source, target);
    }

    public List<TransactionType> getTransactionTypes() {
        return transactionTypes;
    }

    public CompanyTableModel getTableModel() {
        return this.tableModel;
    }

    public void setTableModel(CompanyTableModel model) {
        this.tableModel = model;
    }

    public void setSelectedTableData(Company selectedData) {
        this.selectedData = selectedData;
    }

    public Company getSelectedTableData() {
        return this.selectedData;
    }

    public List<Company> getFilteredTableData() {
        return this.filteredData;
    }

    public void setFilteredTableData(List<Company> filteredUserss) {
        this.filteredData = filteredUserss;
    }

    public Company getNewData() {
        newData.setUser(user.getUser());
        newData.setTransactionTypes(dualListModel.getTarget());
        return newData;
    }

    public void setNewData(Company newUsers) {
        this.newData = newUsers;
    }

    public void onRowSelect(SelectEvent event) {
        refreshSelectedDualList();
    }

    public void updateData(ActionEvent actionEvent) {
        try {
            getSelectedTableData().setTransactionTypes(selectedDualListModel.getTarget());
            db.updateCompany(getSelectedTableData());
            FacesUtils.addInfoMessage(FacesUtils.ResourceBundles.facesMessages, "companyUpdated", null);
        } catch (Exception ex) {
            Logger.getLogger(UsersBacking.class.getName()).log(Level.SEVERE, "Failed to update Company (updateUsers)", ex);
        }

    }

    public void deleteData(ActionEvent actionEvent) {
    }

    public void createData(ActionEvent actionEvent) {
        try {
            db.addCompany(getNewData());
            refreshDualList();
            setNewData(new Company());
            FacesUtils.addInfoMessage(FacesUtils.ResourceBundles.facesMessages, "companyAdded", null);
        } catch (Exception e) {
            Logger.getLogger(UsersBacking.class.getName()).log(Level.SEVERE, "Failed to add Company (updateUsers)", e);
        }
    }

    public void onTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems()) {
            builder.append(((TransactionType) item).getTransactionName()).append("<br />");
        }
    }
}

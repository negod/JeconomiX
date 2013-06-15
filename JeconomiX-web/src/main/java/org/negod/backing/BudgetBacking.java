/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.NoResultException;
import org.negod.DB.Dto.BudgetDto;
import org.negod.DB.Entities.budget.Budget;
import org.negod.DB.Entities.budget.BudgetRow;
import org.negod.DB.Entities.budget.Company;
import org.negod.DB.Entities.budget.TransactionType;
import org.negod.DB.Enums.MonthEnum;
import org.negod.DB.Enums.TransactionTypeEnum;
import org.negod.backing.managedbeans.UserMb;
import org.negod.ejb.budgethandling.BudgetHandling;
import org.negod.ejb.budgethandling.CompanyHandling;
import org.negod.utils.FacesUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author Joakim
 */
@ManagedBean(name = "budgetBacking")
@ViewScoped
public class BudgetBacking implements Serializable {

    @EJB
    BudgetHandling db;
    @EJB
    CompanyHandling companyDb;
    @ManagedProperty(value = "#{userMb}")
    private UserMb user;
    private BudgetDto currentBudget;
    private MonthEnum months[];
    private MonthEnum selectedMonth = MonthEnum.January;
    //Create Budget
    private List<Company> companies;
    private Company selectedCompany;
    private TransactionType selectedTransactionType;
    private List<TransactionType> transactionTypes;
    //BudgetRows
    private BudgetRow newBudgetRow;

    @PostConstruct
    public void setup() {
        try {
            currentBudget = new BudgetDto(db.getBudgetByMonth(selectedMonth, user.getUsername()));
            companies = companyDb.getAllCompanies(user.getUsername());
            newBudgetRow = new BudgetRow();
            newBudgetRow.setTotalSum(0);
        } catch (Exception ex) {
            Logger.getLogger(UsersBacking.class.getName()).log(Level.SEVERE, "Failed to get data from DB (setup)", ex);
        }
    }

    public BudgetRow getNewBudgetRow() {
        return newBudgetRow;
    }

    public void setNewBudgetRow(BudgetRow newBudgetRow) {
        this.newBudgetRow = newBudgetRow;
    }

    public List<TransactionType> getTransactionTypes() {
        return transactionTypes;
    }

    public void setTransactionTypes(List<TransactionType> transactionTypes) {
        this.transactionTypes = transactionTypes;
    }

    public TransactionType getSelectedTransactionType() {
        return selectedTransactionType;
    }

    public void setSelectedTransactionType(TransactionType selectedTransactionType) {
        this.selectedTransactionType = selectedTransactionType;
    }

    public Company getSelectedCompany() {
        return selectedCompany;
    }

    public void setSelectedCompany(Company selectedCompany) {
        this.selectedCompany = selectedCompany;
    }

    public void handleCompanySelect(SelectEvent event) {
        newBudgetRow.setCompany((Company) event.getObject());
        transactionTypes = newBudgetRow.getCompany().getTransactionTypes();
    }

    public List<Company> completeCompany(String query) {
        List<Company> result = new ArrayList<Company>();
        for (Company c : companies) {
            if (c.getCompanyName().startsWith(query)) {
                result.add(c);
            }
        }
        if (result.size() == 1) {
            transactionTypes = result.get(0).getTransactionTypes();
            selectedTransactionType = transactionTypes.get(0);
        }
        return result;
    }

    public List<TransactionType> completeTransactionType(String query) {
        List<TransactionType> result = new ArrayList<TransactionType>();
        for (TransactionType c : transactionTypes) {
            if (c.getTransactionName().startsWith(query)) {
                result.add(c);
            }
        }
        return result;
    }

    public void setBudgetBySelectedMonth() {
        try {
            Budget b = db.getBudgetByMonth(selectedMonth, user.getUsername());
            currentBudget = new BudgetDto(b);
        } catch (NoResultException e) {
            currentBudget = new BudgetDto(new Budget());
            currentBudget.setMonthInYear(selectedMonth);
        } catch (Exception e) {
            currentBudget = new BudgetDto(new Budget());
            currentBudget.setMonthInYear(selectedMonth);
        }
    }

    public void setCurrentMonth(TabChangeEvent event) {
        selectedMonth = (MonthEnum) event.getData();
        setBudgetBySelectedMonth();
    }

    public MonthEnum[] getMonths() {
        return MonthEnum.values();
    }

    public void setMonths(MonthEnum[] months) {
        this.months = months;
    }

    public MonthEnum getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(MonthEnum selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public BudgetDto getCurrentBudget() {
        return currentBudget;
    }

    public void setCurrentBudget(BudgetDto currentBudget) {
        this.currentBudget = currentBudget;
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

    public void updateData(ActionEvent actionEvent) {
        try {
            db.updateBudget(currentBudget.getBudget());
            FacesUtils.addInfoMessage(FacesUtils.ResourceBundles.facesMessages, "transactionTypeUpdated", null);
        } catch (Exception ex) {
            Logger.getLogger(UsersBacking.class.getName()).log(Level.SEVERE, "Failed to update BudgetDto (updateUsers)", ex);
        }
    }

    public void deleteData(ActionEvent actionEvent) {
    }

    public void addBudgetRow(ActionEvent actionEvent) {
        try {
            newBudgetRow.setUsers(user.getUser());
            currentBudget.getBudget().getBudgetRow().add(newBudgetRow);
            db.updateBudget(currentBudget.getBudget());
            FacesUtils.addInfoMessage(FacesUtils.ResourceBundles.facesMessages, "transactionTypeAdded", null);
        } catch (Exception e) {
        }
        newBudgetRow = new BudgetRow();
        newBudgetRow.setTotalSum(0);
    }

    private BudgetDto addData(BudgetDto data) {
        //return new BudgetDto();
        return null;
    }
}

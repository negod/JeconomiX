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
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.negod.DB.Entities.userhandling.Users;
import org.negod.DB.Entities.userhandling.Groups;
import org.negod.ejb.userhandling.UserHandling;
import org.negod.model.UserTableModel;
import org.negod.utils.FacesUtils;
import org.negod.utils.FacesUtils.ResourceBundles;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Joakim
 */
@ManagedBean(name = "usersBacking")
@ViewScoped
public class UsersBacking implements Serializable{

    private List<Users> users;
    private List<Groups> userGroups;
    private List<Users> filteredUserss;
    private Users selectedUsers;
    private Users newUser;
    private UserTableModel userTableModel;
    @EJB
    UserHandling db;

    @PostConstruct
    public void setup() {
        try {
            users = db.getAllUserss();
            userGroups = db.getAllGroups();
            userTableModel = new UserTableModel(db.getAllUserss());

        } catch (Exception ex) {
            Logger.getLogger(UsersBacking.class.getName()).log(Level.SEVERE, "Failed to get data from DB (setup)", ex);
        }
        if (!users.isEmpty()) {
            setSelectedTableData(userTableModel.getRowData(users.get(0).getUsername()));
        } else {
            setSelectedTableData(newUser);
        }
    }

    public UserTableModel getTableModel() {
        return this.userTableModel;
    }

    public void setTableModel(UserTableModel model) {
        this.userTableModel = model;
    }

    public void setSelectedTableData(Users selectedData) {
        this.selectedUsers = selectedData;
    }

    public Users getSelectedTableData() {
        return this.selectedUsers;
    }

    public List<Users> getFilteredTableData() {
        return this.filteredUserss;
    }

    public void setFilteredTableData(List<Users> filteredUserss) {
        this.filteredUserss = filteredUserss;
    }

    public List<Groups> getGroups() {
        try {
            return db.getAllGroups();
        } catch (Exception ex) {
            Logger.getLogger(UsersBacking.class.getName()).log(Level.SEVERE, "Failed to get data from DB (getGroupss)", ex);
            return null;
        }
    }

    public Users getNewUser() {
        return newUser;
    }

    public void setNewUser(Users newUsers) {
        this.newUser = newUsers;
    }

    public UserTableModel getUsersModel() {
        return userTableModel;
    }

    public void setUsersModel(UserTableModel usersModel) {
        this.userTableModel = usersModel;
    }

    public void onRowSelect(SelectEvent event) {
    }

    public void onRowUnselect(UnselectEvent event) {
    }

    public void updateUsers(ActionEvent actionEvent) {
        try {
            db.updateUsers(getSelectedTableData());
            FacesUtils.addInfoMessage(ResourceBundles.facesMessages, "userUpdated", null);
        } catch (Exception ex) {
            Logger.getLogger(UsersBacking.class.getName()).log(Level.SEVERE, "Failed to update Users (updateUsers)", ex);
        }

    }

    public void deleteUsers(ActionEvent actionEvent) {
    }

    public void createUsers(ActionEvent actionEvent) {
        try {
            db.addUsers(getNewUser());
        } catch (Exception e) {
        }
    }

    private Users addUsers(Users data) {
        return new Users();
    }
}

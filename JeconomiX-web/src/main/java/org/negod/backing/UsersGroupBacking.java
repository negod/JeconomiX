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
import org.negod.DB.Entities.userhandling.Groups;
import org.negod.ejb.userhandling.UserHandling;
import org.negod.model.UserGroupTableModel;
import org.negod.utils.FacesUtils;
import org.negod.utils.FacesUtils.ResourceBundles;

/**
 *
 * @author Joakim
 */
@ManagedBean(name = "usersGroupBacking")
@ViewScoped
public class UsersGroupBacking implements Serializable{

    @EJB
    UserHandling db;
    private List<Groups> userGroups;
    private List<Groups> filteredGroupss;
    private Groups selectedGroups;
    private UserGroupTableModel userGroupModel;
    private Groups newUserGroup;

    @PostConstruct
    public void init() {
        try {
            userGroups = db.getAllGroups();
            userGroupModel = new UserGroupTableModel(userGroups);
            newUserGroup = new Groups();
            if (!userGroups.isEmpty()) {
                setSelectedTableData(userGroupModel.getRowData(userGroups.get(0).getGroupName()));
            } else {
                setSelectedTableData(newUserGroup);
            }
        } catch (Exception ex) {
            Logger.getLogger(UsersGroupBacking.class.getName()).log(Level.SEVERE, "Failed to get Groupss (init)", ex);
        }
    }

    public Groups getNewUserGroup() {
        return newUserGroup;
    }

    public void setNewUserGroup(Groups newGroups) {
        this.newUserGroup = newGroups;
    }

    public void createUserGroup(ActionEvent actionEvent) {
        try {
            db.addGroups(getNewUserGroup());
        } catch (Exception ex) {
            Logger.getLogger(UsersGroupBacking.class.getName()).log(Level.SEVERE, "Failed to add user (createGroups)", ex);
            FacesUtils.addErrorMessage(ResourceBundles.facesMessages, "userGroupCouldNotBeCreated", null);
        }
        try {
            userGroupModel = new UserGroupTableModel(db.getAllGroups());
            setSelectedTableData(userGroupModel.getRowData(userGroups.get(0).getGroupName()));
            setNewUserGroup(new Groups());
        } catch (Exception ex) {
            Logger.getLogger(UsersGroupBacking.class.getName()).log(Level.SEVERE, "Failed to get Usergroups (createGroups)", ex);
            FacesUtils.addErrorMessage(ResourceBundles.facesMessages, "userGroupCouldNotBeRetrieved", null);
        }
        FacesUtils.addInfoMessage(ResourceBundles.facesMessages, "groupAdded", null);
    }

    public void deleteGroups(ActionEvent actionEvent) {
        try {
            db.deleteGroups(getSelectedTableData());
            userGroupModel = new UserGroupTableModel(db.getAllGroups());
            setSelectedTableData(userGroupModel.getRowData(userGroups.get(0).getGroupName()));
        } catch (Exception ex) {
            Logger.getLogger(UsersGroupBacking.class.getName()).log(Level.SEVERE, "Failed to delete Usergroups (deleteGroups)", ex);
            FacesUtils.addErrorMessage(ResourceBundles.facesMessages, "userGroupCouldNotBeDeleted", null);
        }
        FacesUtils.addInfoMessage(ResourceBundles.facesMessages, "userDeleted", null);
    }

    public void updateGroups(ActionEvent actionEvent) {
        try {
            db.updateGroups(getSelectedTableData());
        } catch (Exception ex) {
            Logger.getLogger(UsersGroupBacking.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtils.addInfoMessage(ResourceBundles.facesMessages, "userGroupCouldNotBeUpdated", null);
        }
        try {
            userGroupModel = new UserGroupTableModel(db.getAllGroups());
            setSelectedTableData(userGroupModel.getRowData(userGroups.get(0).getGroupName()));
        } catch (Exception ex) {
            Logger.getLogger(UsersGroupBacking.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtils.addInfoMessage(ResourceBundles.facesMessages, "userGroupCouldNotBeRetrieved", null);
        }
        FacesUtils.addErrorMessage(ResourceBundles.facesMessages, "groupUpdated", null);
    }

    public UserGroupTableModel getTableModel() {
        return this.userGroupModel;
    }

    public void setTableModel(UserGroupTableModel model) {
        this.userGroupModel = model;
    }

    public void setSelectedTableData(Groups selectedData) {
        this.selectedGroups = selectedData;
    }

    public Groups getSelectedTableData() {
        return this.selectedGroups;
    }

    public List<Groups> getFilteredTableData() {
        return this.filteredGroupss;
    }

    public void setFilteredTableData(List<Groups> filteredUsers) {
        this.filteredGroupss = filteredUsers;
    }
}

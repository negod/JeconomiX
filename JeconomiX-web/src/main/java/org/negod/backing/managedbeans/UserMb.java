/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.backing.managedbeans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.negod.DB.Entities.userhandling.UserGroup;
import org.negod.DB.Entities.userhandling.Users;
import org.negod.ejb.userhandling.UserHandling;
import org.negod.utils.FacesUtils;

/**
 *
 * @author Joakim
 */
@SessionScoped
@ManagedBean(name = "userMb")
public class UserMb implements Serializable{

    private Users user;
    @EJB
    UserHandling db;

    @PostConstruct
    public void init() {
        user = db.getUserByUserName(FacesUtils.getRemoteUser());
    }

    public Users getUser() {
        return user;
    }
    
    public String getUsername() {
        return user.getUsername();
    }

    public boolean belongsToGroup(String groupName) {
        for (UserGroup group : user.getGroups()) {
            if (group.getGroupname().getGroupName().equals(groupName)) {
                return true;
            }
        }
        return false;
    }
}

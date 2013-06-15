/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.DB.Entities.userhandling;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Joakim
 */
@Entity
@Table(name = "UserGroup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserGroup.findAll", query = "SELECT g FROM UserGroup g"),
    @NamedQuery(name = "UserGroup.findByUsername", query = "SELECT g FROM UserGroup g WHERE g.users.username = :username"),
    @NamedQuery(name = "UserGroup.findByGroupname", query = "SELECT g FROM UserGroup g WHERE g.groups.groupName = :groupname")})
public class UserGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idGroup;
    @JoinColumn(name = "username")
    private Users users;
    @JoinColumn(name = "groupname")
    private Groups groups;

    public UserGroup() {
    }

    public long getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(long idGroup) {
        this.idGroup = idGroup;
    }

    public Users getUser() {
        return users;
    }

    public void setUser(Users users) {
        this.users = users;
    }

    public Groups getGroupname() {
        return groups;
    }

    public void setGroupname(Groups groupname) {
        this.groups = groupname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groups != null ? groups.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserGroup)) {
            return false;
        }
        UserGroup other = (UserGroup) object;
        if ((this.groups == null && other.groups != null) || (this.groups != null && !this.groups.equals(other.groups))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.negod.DB.Entities.userhandling.Groups[ groupname=" + groups + " ]";
    }
}

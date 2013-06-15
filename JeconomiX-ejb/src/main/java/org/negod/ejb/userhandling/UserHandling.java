/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.ejb.userhandling;

import java.util.List;
import javax.ejb.Local;
import org.negod.DB.Entities.userhandling.Users;
import org.negod.DB.Entities.userhandling.Groups;

/**
 *
 * @author Joakim
 */
@Local
public interface UserHandling {

    public List<Users> getAllUserss() throws Exception;

    public void addUsers(Users user) throws Exception;

    public void updateUsers(Users user) throws Exception;

    public void deleteUsers(Users user) throws Exception;

    public List<Groups> getAllGroups() throws Exception;

    public void addGroups(Groups userGroup) throws Exception;

    public void updateGroups(Groups userGroup) throws Exception;

    public void deleteGroups(Groups userGroup) throws Exception;
    
    public Users getUserByUserName(String username);
}

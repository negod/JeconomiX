/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

import com.negod.generics.persistence.entity.EntityRegistry;
import javax.persistence.EntityManager;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CacheInitializer extends EntityRegistry {

    @Override
    public EntityManager getEntityManager() {
        return PersistenceHandler.getEntityManager();
    }

    public CacheInitializer() {
        super.registerEnties();
        super.registerSearchFields();
        super.registerSearchFieldCaches();
    }

}

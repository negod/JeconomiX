/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database.dao;

import com.negod.generics.persistence.entity.EntityRegistry;
import javax.persistence.EntityManager;
import se.backede.jeconomix.database.PersistenceHandler;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class TestCacheInitializer extends EntityRegistry {

    @Override
    public EntityManager getEntityManager() {
        return PersistenceHandler.getInstance().getEntityManager("TestPu");
    }

    public TestCacheInitializer() {
        super.registerEnties();
        super.registerSearchFields();
        super.registerSearchFieldCaches();
    }

}

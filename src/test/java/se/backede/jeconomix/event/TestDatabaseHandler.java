/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.event;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.h2.tools.DeleteDbFiles;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class TestDatabaseHandler {

    private static final TestDatabaseHandler INSTANCE = new TestDatabaseHandler();

    protected TestDatabaseHandler() {
    }

    public static TestDatabaseHandler getInstance() {
        return INSTANCE;
    }

    public Connection deleteAndCreateNewDatabase() {
        try {
            java.sql.Connection connection = openConnection(); //your openConnection logic here
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new liquibase.Liquibase("liquibase/changelog/db-changelog-1.0.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.dropAll();
            liquibase.update(new Contexts(), new LabelExpression());
            return connection;
        } catch (DatabaseException ex) {
            Logger.getLogger(TestDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LiquibaseException ex) {
            Logger.getLogger(TestDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Connection openConnection() {
        try {
            DeleteDbFiles.execute("testDb", "test", true);
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:./testDb/test");
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TestDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}

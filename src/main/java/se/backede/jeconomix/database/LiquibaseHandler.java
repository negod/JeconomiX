/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.database;

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
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.DeleteDbFiles;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class LiquibaseHandler {

    private static final LiquibaseHandler INSTANCE = new LiquibaseHandler();

    protected LiquibaseHandler() {
    }

    public static LiquibaseHandler getInstance() {
        return INSTANCE;
    }

    public void deleteAndCreateNewDatabase(String url) {
        try {
            java.sql.Connection connection = openConnection(url); //your openConnection logic here
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new liquibase.Liquibase("liquibase/changelog/db-changelog-1.0.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (DatabaseException ex) {
            log.error("", ex);
        } catch (LiquibaseException ex) {
            log.error("", ex);
        }
    }

    public void updateDatabase(String url) {
        try {
            java.sql.Connection connection = openConnection(url); //your openConnection logic here
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new liquibase.Liquibase("liquibase/changelog/db-changelog-1.0.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (DatabaseException ex) {
            log.error("", ex);
        } catch (LiquibaseException ex) {
            log.error("", ex);
        }
    }

    private Connection openConnection(String url) {
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:./".concat(url));
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            log.error("", ex);
        }
        return null;
    }

}

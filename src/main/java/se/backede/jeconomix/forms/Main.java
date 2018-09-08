/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms;

import se.backede.jeconomix.database.CacheInitializer;
import se.backede.jeconomix.database.LiquibaseHandler;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class Main {

    public static void main(String[] args) {

        LiquibaseHandler.getInstance().updateDatabase("db/JeconomiX");
        CacheInitializer cache = new CacheInitializer();

        java.awt.EventQueue.invokeLater(() -> {
            new MainForm().setVisible(true);
        });
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.database.CacheInitializer;
import se.backede.jeconomix.database.LiquibaseHandler;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class Main {

    public static void main(String[] args) {

        LiquibaseHandler.getInstance().updateDatabase("db/JeconomiX");
        CacheInitializer cache = new CacheInitializer();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            log.error("Error in startup when setting look and feel", e);
        }

        Font font = UIManager.getFont("TableHeader.font");
        font = font.deriveFont(11f);

        UIManager.put("TableHeader.font", font);

        java.awt.EventQueue.invokeLater(() -> {
//            MainForm frame = new MainForm();

            MonthOverview frame = new MonthOverview();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }

}

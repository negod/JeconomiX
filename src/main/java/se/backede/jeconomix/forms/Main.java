package se.backede.jeconomix.forms;

import com.backede.fileutils.exception.BeckedeFileException;
import com.backede.fileutils.listener.FolderListener;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.CacheInitializer;
import se.backede.jeconomix.database.LiquibaseHandler;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class Main {

    static CategoryTypeEnum[] CATEGORIES = {
        CategoryTypeEnum.INCOME,
        CategoryTypeEnum.BILL,
        CategoryTypeEnum.EXPENSE
    };

    public static void main(String[] args) {

        LiquibaseHandler.getInstance().updateDatabase("db/JeconomiX");
        CacheInitializer cache = new CacheInitializer();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            log.error("Error in startup when setting look and feel", e);
        }

        final Consumer<File> CONSUMER = file -> {
            System.out.println("Got a file!");
        };

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new FolderListener(Paths.get("imports"), CONSUMER, true).start();
                } catch (BeckedeFileException | IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

//        try {
//            new FolderListener(Paths.get("imports"), 1000, CONSUMER, true).run();
//        } catch (IOException | BeckedeFileException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
        Font font = UIManager.getFont("TableHeader.font");
        font = font.deriveFont(11f);

        UIManager.put("TableHeader.font", font);

        EventQueue.invokeLater(() -> {
            MainForm frame = new MainForm();
            //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });

    }

}

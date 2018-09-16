/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.importexport;

import java.util.Optional;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class FileChooser {

    private static final String XML = "xml";
    private static final String CSV = "csv";
    private static final String FOLDER = "folder";

    private static final FileChooser INSTANCE = new FileChooser();

    protected FileChooser() {
    }

    public static final FileChooser getInstance() {
        return INSTANCE;
    }

    private Optional<String> getPath(Integer fileChooserType, String fileType) {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        switch (fileChooserType) {
            case JFileChooser.DIRECTORIES_ONLY:
                jfc.setDialogTitle("Select destinationfolder");
                jfc.setDialogType(JFileChooser.SAVE_DIALOG);
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                break;
            case JFileChooser.FILES_AND_DIRECTORIES:
                jfc.setDialogTitle("Select file to import");
                jfc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter(fileType.toUpperCase().concat(" files"), fileType);
                jfc.addChoosableFileFilter(filter);
                break;
            default:
                log.error("Filechooser type not supported!");
                return Optional.empty();
        }

        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return Optional.ofNullable(jfc.getSelectedFile().getPath());
        }
        return Optional.empty();
    }

    public Optional<String> getXmlFilePath(Integer fileChooserType) {
        return getPath(fileChooserType, XML);
    }

    public Optional<String> getCsvFilePath(Integer fileChooserType) {
        return getPath(fileChooserType, CSV);
    }

}

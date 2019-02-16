/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.exporter;

import com.backede.fileutils.xml.writer.XmlWriter;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import se.backede.jeconomix.utils.ImportExportUtils;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 * @param <T>
 */
public final class Exporter<T> extends ImportExportUtils<T> {
    
    private final ExportListData<T> data;
    private final String fileName;
    
    public Exporter(String fileName, List<T> data) {
        this.fileName = fileName;
        this.data = createExportListData(data);
    }
    
    public ExportListData createExportListData(List<T> data) {
        ExportListData exportList = new ExportListData(data);
        return exportList;
    }
    
    public void export() {
        super.extractClasses(this.data).ifPresent(classes -> {
            data.setExported(Date.from(Instant.now()));
            new Thread(() -> {
                XmlWriter.writeXml(fileName, this.data, classes.toArray(new Class<?>[]{}));
            }).start();
        });
    }
    
}

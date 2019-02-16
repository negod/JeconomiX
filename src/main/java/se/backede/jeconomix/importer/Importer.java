/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.importer;

import com.backede.fileutils.xml.reader.XmlReader;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;
import se.backede.jeconomix.exporter.ExportListData;
import se.backede.jeconomix.utils.ImportExportUtils;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
public class Importer<T> extends ImportExportUtils<T> {

    private final Class<?> clazz;
    private final String filePath;
    ExportListData<T> importData;

    public Importer(String filePath, Class<?> clazz) {
        this.filePath = filePath;
        this.clazz = clazz;
    }

    public Optional<List<T>> importData() {

        Set<Class<?>> classes = super.extractClassesFromObject(clazz);
        classes.add(ExportListData.class);

        XmlReader<ExportListData<T>> reader = new XmlReader<>();
        return reader.readXml(filePath, classes.toArray(new Class<?>[]{})).map(listData -> {
            return listData.getData();
        });
    }

}

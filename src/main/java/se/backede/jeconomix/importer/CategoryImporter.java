/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.importer;

import com.backede.fileutils.xml.reader.XmlReader;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.database.CategoryHandler;
import se.backede.jeconomix.dto.ProgressDto;
import se.backede.jeconomix.dto.export.Categories;
import se.backede.jeconomix.dto.export.mapper.CategoryExportMapper;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.events.ProgressEvent;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class CategoryImporter {

    private static final CategoryImporter INSTANCE = new CategoryImporter();
    private final XmlReader<Categories> READER = new XmlReader<>();

    protected CategoryImporter() {
    }

    public static final CategoryImporter getInstance() {
        return INSTANCE;
    }

    public void importCategories(String filePath) {
        new Thread(() -> {
            READER.readXml(filePath, Categories.class).ifPresent(categories -> {
                EventController.getInstance().notifyObservers(ProgressEvent.SET_MAX_VALUE, () -> new ProgressDto(categories.getCategory().size(), "Importing Categories"));

                categories.getCategory().stream().map((categoryDto) -> CategoryExportMapper.mapToDto(categoryDto)).forEachOrdered((dto) -> {
                    EventController.getInstance().notifyObservers(ProgressEvent.INCREASE, () -> new ProgressDto(1, "Importing Category: ".concat(dto.getName())));
                    CategoryHandler.getInstance().createCategory(dto);
                });

                EventController.getInstance().notifyObservers(ProgressEvent.DONE, () -> new ProgressDto(categories.getCategory().size(), "Done importing Categories"));
            });
        }).start();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.importer;

import com.backede.fileutils.xml.reader.XmlReader;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.database.CategoryHandler;
import se.backede.jeconomix.dto.export.Categories;
import se.backede.jeconomix.dto.export.mapper.CategoryExportMapper;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class CategoryImporter {

    private static CategoryImporter INSTANCE = new CategoryImporter();
    private final XmlReader<Categories> READER = new XmlReader<>();

    protected CategoryImporter() {
    }

    public static final CategoryImporter getInstance() {
        return INSTANCE;
    }

    public void importCategories(String filePath) {
        new Thread(() -> {
            Optional<Categories> importedCategories = READER.readXml(filePath, Categories.class);
            if (importedCategories.isPresent()) {
                importedCategories.get().getCategory()
                        .stream().map((categoryDto) -> CategoryExportMapper.mapToDto(categoryDto)).forEachOrdered((dto) -> {
                    CategoryHandler.getInstance().createCategory(dto);
                });
            }
        }).start();
    }

}

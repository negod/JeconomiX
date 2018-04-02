/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.importer;

import com.backede.fileutils.xml.reader.XmlReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.database.entity.search.CategoryModelDto;
import se.backede.jeconomix.dto.CategoryModelListDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class CategoryModelmporter {

    private static CategoryModelmporter INSTANCE = new CategoryModelmporter();
    private final XmlReader<CategoryModelListDto> READER = new XmlReader<>();
    private Optional<Map<String, String>> MODEL = Optional.empty();

    protected CategoryModelmporter() {
    }

    public static final CategoryModelmporter getInstance() {
        return INSTANCE;
    }

    public Optional<Map<String, String>> getCategoryModel(String filePath) {

        if (MODEL.isPresent()) {
            return MODEL;
        }

        Optional<CategoryModelListDto> readXml = READER.readXml(filePath, CategoryModelListDto.class);
        if (readXml.isPresent()) {
            Map<String, String> models = new HashMap<>();
            for (CategoryModelDto model : readXml.get().getModels()) {
                log.error("WORD {} ID {}", model.getWord(), model.getCategoryId());
                models.put(model.getWord(), model.getCategoryId());
            }
            MODEL = Optional.ofNullable(models);
            return MODEL;
        }
        return Optional.empty();
    }

}

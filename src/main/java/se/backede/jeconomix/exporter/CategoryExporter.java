/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.exporter;

import com.backede.fileutils.xml.writer.XmlWriter;
import java.util.List;
import java.util.Optional;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.database.CategoryHandler;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.export.Categories;
import se.backede.jeconomix.dto.export.CategoryExportDto;
import se.backede.jeconomix.dto.export.mapper.BillCategoryMapper;
import se.backede.jeconomix.event.events.Events;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CategoryExporter {

    private static CategoryExporter INSTANCE = new CategoryExporter();

    protected CategoryExporter() {
    }

    public static final CategoryExporter getInstance() {
        return INSTANCE;
    }

    public void exportCategories(String fileName) {
        new Thread(() -> {
            Optional<List<CategoryDto>> allBillCategories = CategoryHandler.getInstance().getFilteredCategories(CategoryTypeEnum.BILL);
            if (allBillCategories.isPresent()) {
                Events.getInstance().fireProgressMaxValueEvent(3);
                Categories expenseCategories = new Categories();
                Events.getInstance().fireProgressIncreaseValueEvent(1, "Mapping categories");
                List<CategoryExportDto> mapToExportDto = BillCategoryMapper.mapToExportDto(allBillCategories.get());
                expenseCategories.setBillCategory(mapToExportDto);
                Events.getInstance().fireProgressIncreaseValueEvent(2, "Exporting file");
                XmlWriter.writeXml(fileName, Categories.class, expenseCategories);
            } else {

            }
        }).start();
    }

}

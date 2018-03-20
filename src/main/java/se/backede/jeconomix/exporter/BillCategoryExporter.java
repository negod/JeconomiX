/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.exporter;

import com.backede.fileutils.xml.writer.XmlWriter;
import java.util.List;
import java.util.Optional;
import se.backede.jeconomix.database.BillCategoryHandler;
import se.backede.jeconomix.dto.BillCategoryDto;
import se.backede.jeconomix.dto.export.BillCategories;
import se.backede.jeconomix.dto.export.BillCategoryExportDto;
import se.backede.jeconomix.dto.export.mapper.BillCategoryMapper;
import se.backede.jeconomix.event.events.Events;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BillCategoryExporter {

    private static BillCategoryExporter INSTANCE = new BillCategoryExporter();

    protected BillCategoryExporter() {
    }

    public static final BillCategoryExporter getInstance() {
        return INSTANCE;
    }

    public void exportBillCategories(String fileName) {
        new Thread(() -> {
            Optional<List<BillCategoryDto>> allBillCategories = BillCategoryHandler.getInstance().getAllBillCategories();
            if (allBillCategories.isPresent()) {
                Events.getInstance().fireProgressMaxValueEvent(3);
                BillCategories expenseCategories = new BillCategories();
                Events.getInstance().fireProgressIncreaseValueEvent(1, "Mapping bill categories");
                List<BillCategoryExportDto> mapToExportDto = BillCategoryMapper.mapToExportDto(allBillCategories.get());
                expenseCategories.setBillCategory(mapToExportDto);
                Events.getInstance().fireProgressIncreaseValueEvent(2, "Exporting file");
                XmlWriter.writeXml(fileName, BillCategories.class, expenseCategories);
            } else {

            }
        }).start();
    }

}

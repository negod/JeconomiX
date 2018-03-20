/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.importer;

import com.backede.fileutils.xml.reader.XmlReader;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.database.BillCategoryHandler;
import se.backede.jeconomix.dto.export.BillCategories;
import se.backede.jeconomix.dto.export.mapper.BillCategoryMapper;
import se.backede.jeconomix.event.events.Events;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class BillCategoryImporter {

    private static BillCategoryImporter INSTANCE = new BillCategoryImporter();
    private final XmlReader<BillCategories> READER = new XmlReader<>();

    protected BillCategoryImporter() {
    }

    public static final BillCategoryImporter getInstance() {
        return INSTANCE;
    }

    public void importBillCategories(String filePath) {
        new Thread(() -> {
            Optional<BillCategories> importedBillCategories = READER.readXml(filePath, BillCategories.class);
            Events.getInstance().fireProgressMaxValueEvent(importedBillCategories.get().getBillCategory().size());

            if (importedBillCategories.isPresent()) {
                importedBillCategories.get().getBillCategory().stream().map((companyExportDto) -> BillCategoryMapper.mapToDto(companyExportDto)).forEachOrdered((dto) -> {
                    BillCategoryHandler.getInstance().createBillCategory(dto);
                    Events.getInstance().fireProgressIncreaseValueEvent(1, dto.getName());
                });
                Events.getInstance().fireProgressDoneEvent();
            } else {
                Events.getInstance().fireErrorEvent();
            }
        }).start();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.importer;

import com.backede.fileutils.xml.reader.XmlReader;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.database.ExpenseCategoryHandler;
import se.backede.jeconomix.dto.export.ExpenseCategories;
import se.backede.jeconomix.dto.export.mapper.ExpenseCategoryMapper;
import se.backede.jeconomix.event.events.Events;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class ExpenseCategoryImporter {

    private static ExpenseCategoryImporter INSTANCE = new ExpenseCategoryImporter();
    private final XmlReader<ExpenseCategories> READER = new XmlReader<>();

    protected ExpenseCategoryImporter() {
    }

    public static final ExpenseCategoryImporter getInstance() {
        return INSTANCE;
    }

    public void importExpenseCategories(String filePath) {
        new Thread(() -> {
            Optional<ExpenseCategories> importedExpenseCategories = READER.readXml(filePath, ExpenseCategories.class);
            Events.getInstance().fireProgressMaxValueEvent(importedExpenseCategories.get().getExpenseCategory().size());

            if (importedExpenseCategories.isPresent()) {
                importedExpenseCategories.get().getExpenseCategory().stream().map((companyExportDto) -> ExpenseCategoryMapper.mapToDto(companyExportDto)).forEachOrdered((dto) -> {
                    ExpenseCategoryHandler.getInstance().createExpenseCategory(dto);
                    Events.getInstance().fireProgressIncreaseValueEvent(1, dto.getName());
                });
                Events.getInstance().fireProgressDoneEvent();
            } else {
                Events.getInstance().fireErrorEvent();
            }
        }).start();
    }

}

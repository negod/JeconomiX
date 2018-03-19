/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.exporter;

import com.backede.fileutils.xml.writer.XmlWriter;
import java.util.List;
import java.util.Optional;
import se.backede.jeconomix.database.ExpenseCategoryHandler;
import se.backede.jeconomix.dto.ExpenseCategoryDto;
import se.backede.jeconomix.dto.export.Companies;
import se.backede.jeconomix.dto.export.ExpenseCategories;
import se.backede.jeconomix.dto.export.ExpenseCategoryExportDto;
import se.backede.jeconomix.dto.export.mapper.ExpenseCategoryMapper;
import se.backede.jeconomix.event.events.Events;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class ExpenseCategoryExporter {

    private static ExpenseCategoryExporter INSTANCE = new ExpenseCategoryExporter();

    protected ExpenseCategoryExporter() {
    }

    public static final ExpenseCategoryExporter getInstance() {
        return INSTANCE;
    }

    public void exportExpenseCategories(String fileName) {
        new Thread(() -> {
            Optional<List<ExpenseCategoryDto>> allExpenseCategories = ExpenseCategoryHandler.getInstance().getAllExpenseCategories();
            if (allExpenseCategories.isPresent()) {
                Events.getInstance().fireProgressMaxValueEvent(3);
                ExpenseCategories expenseCategories = new ExpenseCategories();
                Events.getInstance().fireProgressIncreaseValueEvent(1, "Mapping expense categories");
                List<ExpenseCategoryExportDto> mapToExportDto = ExpenseCategoryMapper.mapToExportDto(allExpenseCategories.get());
                expenseCategories.setExpenseCategory(mapToExportDto);
                Events.getInstance().fireProgressIncreaseValueEvent(2, "Exporting file");
                XmlWriter.writeXml(fileName, ExpenseCategories.class, expenseCategories);
            } else {

            }
        }).start();
    }

}

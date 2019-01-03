/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.importer;

import com.backede.fileutils.xml.reader.XmlReader;
import java.util.Optional;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.database.CompanyHandler;
import se.backede.jeconomix.database.CategoryHandler;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.ProgressDto;
import se.backede.jeconomix.dto.budget.BudgetDto;
import se.backede.jeconomix.dto.export.BudgetExportDto;
import se.backede.jeconomix.dto.export.Budgets;
import se.backede.jeconomix.dto.export.mapper.BudgetExportMapper;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.events.ProgressEvent;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class BudgetImporter {

    private static BudgetImporter INSTANCE = new BudgetImporter();
    private final XmlReader<Budgets> READER = new XmlReader<>();

    protected BudgetImporter() {
    }

    public static final BudgetImporter getInstance() {
        return INSTANCE;
    }

    public void importBudgets(String filePath) {
        new Thread(() -> {
//            Optional<Budgets> importedBudgets = READER.readXml(filePath, Budgets.class);
//            if (importedBudgets.isPresent()) {
//
//                Supplier<ProgressDto> setMaxValue = () -> new ProgressDto(importedBudgets.get().getBudget().size(), "Importing companies");
//                EventController.getInstance().notifyObservers(ProgressEvent.SET_MAX_VALUE, setMaxValue);
//
//                for (BudgetExportDto budgetExportDto : importedBudgets.get().getBudget()) {
//                    BudgetDto dto = BudgetExportMapper.mapToDto(budgetExportDto).get();
//
//                    //Hämta budget.
//                    //Om den inte finns -> skapa
//                    // OM den finns gör en check så man inte lägger in samma expenserow igen
//                    //Lägg till budgetexpense
//                    Optional<CategoryDto> expCat = CategoryHandler.getInstance().getCategoryById(budgetExportDto.getCategory());
//                    if (expCat.isPresent()) {
//                        dto.setCategory(expCat.get());
//                        CompanyHandler.getInstance().createCompany(dto);
//
//                        Supplier<ProgressDto> increaseValue = () -> new ProgressDto(1, dto.getName());
//                        EventController.getInstance().notifyObservers(ProgressEvent.INCREASE, increaseValue);
//
//                    } else {
//                        log.error("Could not get Expense category for company {} aborting insert if this company", dto.getName());
//                    }
//                }
//                Supplier<ProgressDto> doneEvent = () -> new ProgressDto(1, "Done importing Companies");
//                EventController.getInstance().notifyObservers(ProgressEvent.DONE, doneEvent);
//            } else {
//                Supplier<ProgressDto> errorEvent = () -> new ProgressDto(1, "Error when importing Companies");
//                EventController.getInstance().notifyObservers(ProgressEvent.ERROR, errorEvent);
//                return;
//            }

            return;
        }).start();
    }
}

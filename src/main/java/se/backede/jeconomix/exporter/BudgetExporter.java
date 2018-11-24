/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.exporter;

import com.backede.fileutils.xml.writer.XmlWriter;
import java.util.List;
import se.backede.jeconomix.database.BudgetHandler;
import se.backede.jeconomix.dto.export.BudgetExportDto;
import se.backede.jeconomix.dto.export.Budgets;
import se.backede.jeconomix.dto.export.mapper.BudgetExportMapper;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BudgetExporter {

    private static BudgetExporter INSTANCE = new BudgetExporter();

    protected BudgetExporter() {
    }

    public static final BudgetExporter getInstance() {
        return INSTANCE;
    }

    public void exportBudgets(String fileName) {
        new Thread(() -> {

            BudgetHandler.getInstance().getAllAsDto().ifPresent(budgetDtoList -> {
                Budgets allBudgets = new Budgets();
                List<BudgetExportDto> mapToExportDto = BudgetExportMapper.mapToExportDto(budgetDtoList);
                allBudgets.setBudget(mapToExportDto);
                XmlWriter.writeXml(fileName, Budgets.class, allBudgets);
            });

        }).start();
    }

}

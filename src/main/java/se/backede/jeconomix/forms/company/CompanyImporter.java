/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.company;

import com.backede.fileutils.xml.reader.XmlReader;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.database.CompanyHandler;
import se.backede.jeconomix.database.CategoryHandler;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.export.Companies;
import se.backede.jeconomix.dto.export.CompanyExportDto;
import se.backede.jeconomix.dto.export.mapper.CompanyMapper;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class CompanyImporter {

    private static CompanyImporter INSTANCE = new CompanyImporter();
    private final XmlReader<Companies> READER = new XmlReader<>();

    protected CompanyImporter() {
    }

    public static final CompanyImporter getInstance() {
        return INSTANCE;
    }

    public void importCompanies(String filePath) {
        new Thread(() -> {
            Optional<Companies> importedCompanies = READER.readXml(filePath, Companies.class);
            if (importedCompanies.isPresent()) {
//                Events.getInstance().fireProgressMaxValueEvent(importedCompanies.get().getCompany().size());

                for (CompanyExportDto companyExportDto : importedCompanies.get().getCompany()) {
                    CompanyDto dto = CompanyMapper.mapToDto(companyExportDto);
                    Optional<CategoryDto> expCat = CategoryHandler.getInstance().getById(companyExportDto.getCategory());
                    if (expCat.isPresent()) {
                        dto.setCategory(expCat.get());
                        CompanyHandler.getInstance().createCompany(dto);

//                        Events.getInstance().fireProgressIncreaseValueEvent(1, dto.getName());
                    } else {
                        log.error("Could not get Expense category for company {} aborting insert if this company", dto.getName());
                    }
                }
//                Events.getInstance().fireProgressDoneEvent();
            } else {
//                Events.getInstance().fireErrorEvent();
                return;
            }
        }).start();
    }
}

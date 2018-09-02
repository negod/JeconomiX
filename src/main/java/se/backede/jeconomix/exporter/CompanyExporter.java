/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.exporter;

import com.backede.fileutils.xml.writer.XmlWriter;
import java.util.List;
import java.util.Optional;
import se.backede.jeconomix.database.CompanyHandler;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.export.Companies;
import se.backede.jeconomix.dto.export.CompanyExportDto;
import se.backede.jeconomix.dto.export.mapper.CompanyMapper;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CompanyExporter {

    private static final CompanyExporter INSTANCE = new CompanyExporter();

    protected CompanyExporter() {
    }

    public static final CompanyExporter getInstance() {
        return INSTANCE;
    }

    public void exportCompanies(String filename) {
        new Thread(() -> {
            Optional<List<CompanyDto>> allCompanies = CompanyHandler.getInstance().getAllCompanies();
            if (allCompanies.isPresent()) {
//                Events.getInstance().fireProgressMaxValueEvent(3);
                Companies companies = new Companies();
//                Events.getInstance().fireProgressIncreaseValueEvent(1, "Mapping companies");
                List<CompanyExportDto> mapToExportDto = CompanyMapper.mapToExportDto(allCompanies.get());
                companies.setCompany(mapToExportDto);
//                Events.getInstance().fireProgressIncreaseValueEvent(2, "Exporting file");
                XmlWriter.writeXml(filename, Companies.class, companies);
            } else {
//                Events.getInstance().fireErrorEvent();
                return;
            }
//            Events.getInstance().fireProgressDoneEvent();

        }).start();
    }
}

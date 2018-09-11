/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.exporter;

import com.backede.fileutils.xml.writer.XmlWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import se.backede.jeconomix.database.CompanyHandler;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.ProgressDto;
import se.backede.jeconomix.dto.export.Companies;
import se.backede.jeconomix.dto.export.CompanyExportDto;
import se.backede.jeconomix.dto.export.mapper.CompanyMapper;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.events.ProgressEvent;

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
                EventController.getInstance().notifyObservers(ProgressEvent.SET_MAX_VALUE, () -> new ProgressDto(allCompanies.get().size(), "Exporting companies.."));
                Companies companies = new Companies();
                List<CompanyExportDto> exportList = new ArrayList<>();

                for (CompanyDto companyDto : allCompanies.get()) {
                    CompanyExportDto exportDto = CompanyMapper.mapToExportDto(companyDto);
                    exportList.add(exportDto);
                    EventController.getInstance().notifyObservers(ProgressEvent.INCREASE, () -> new ProgressDto(1, "Exporting company: ".concat(exportDto.getName())));
                }
                companies.setCompany(exportList);
                XmlWriter.writeXml(filename, Companies.class, companies);
                EventController.getInstance().notifyObservers(ProgressEvent.DONE, () -> new ProgressDto(exportList.size(), String.valueOf(exportList.size()).concat(" companies successfully exported!")));
            } else {
                EventController.getInstance().notifyObservers(ProgressEvent.DONE, () -> new ProgressDto(0, "No companies exported"));

            }
        }).start();
    }
}

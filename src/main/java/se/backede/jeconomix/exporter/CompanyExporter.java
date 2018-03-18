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

    private static CompanyExporter INSTANCE = new CompanyExporter();

    protected CompanyExporter() {
    }

    public static final CompanyExporter getInstance() {
        return INSTANCE;
    }

    public void exportCompanies(String filename) {
        Optional<List<CompanyDto>> allCompanies = CompanyHandler.getInstance().getAllCompanies();
        if (allCompanies.isPresent()) {
            Companies companies = new Companies();
            List<CompanyExportDto> mapToExportDto = CompanyMapper.getInstance().mapToExportDto(allCompanies.get());
            companies.setCompany(mapToExportDto);
            XmlWriter.writeXml(filename, Companies.class, companies);
        }
    }

}

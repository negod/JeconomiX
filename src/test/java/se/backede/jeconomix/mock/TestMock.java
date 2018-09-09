/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.mock;

import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.CompanyDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class TestMock {

    public static CategoryDto getCategory(String name) {
        CategoryDto dto = new CategoryDto();
        dto.setName(name);
        return dto;
    }

    public static CompanyDto getCompany(String name) {
        CompanyDto dto = new CompanyDto(name);
        return dto;
    }

}

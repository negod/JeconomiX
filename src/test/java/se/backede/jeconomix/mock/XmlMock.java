/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.mock;

import se.backede.jeconomix.mock.dto.AddresDto;
import se.backede.jeconomix.mock.dto.CompanyDto;
import se.backede.jeconomix.mock.dto.PersonDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class XmlMock {

    /**
     * First level of Dto hierarchy
     *
     * @return
     */
    public static PersonDto getPerson() {
        return PersonDto.builder()
                .name("person")
                .company(getCompany())
                .build();

    }

    /**
     * Second level of DTO hierarcy
     *
     * @return
     */
    public static CompanyDto getCompany() {
        return CompanyDto.builder()
                .name("company")
                .address(getAddress()).build();
    }

    /**
     * Third level of DTO hirearchy
     *
     * @return
     */
    public static AddresDto getAddress() {
        return AddresDto.builder().name("adress").build();
    }

}

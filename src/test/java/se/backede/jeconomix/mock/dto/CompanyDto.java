/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.mock.dto;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Setter
@Builder
@XmlRootElement
public class CompanyDto {

    String name;
    AddresDto address;

}

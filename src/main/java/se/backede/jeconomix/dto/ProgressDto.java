/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@RequiredArgsConstructor
public class ProgressDto {

    private final Integer value;
    private final String text;

    private Integer duplecateRecords;
    private Integer invalidRecords;
    private Integer totalRecords;
}

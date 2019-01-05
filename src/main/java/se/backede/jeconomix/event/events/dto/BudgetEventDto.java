/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.event.events.dto;

import java.time.YearMonth;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import se.backede.jeconomix.constants.CategoryTypeEnum;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Builder
@EqualsAndHashCode
public class BudgetEventDto {

    YearMonth yearMonth;
    CategoryTypeEnum category;

}

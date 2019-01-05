/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.constants;

import java.time.Month;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public enum BudgetQuarterEnum {

    QUARTER1(new Month[]{Month.JANUARY, Month.FEBRUARY, Month.MARCH}),
    QUARTER2(new Month[]{Month.APRIL, Month.MAY, Month.JUNE}),
    QUARTER3(new Month[]{Month.JULY, Month.AUGUST, Month.SEPTEMBER}),
    QUARTER4(new Month[]{Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER});

    private final Month[] months;

    private BudgetQuarterEnum(Month[] s) {
        months = s;
    }

    public Month[] months() {
        return months;
    }

    public Month firstMonth() {
        return months[0];
    }

    public Month secondMonth() {
        return months[1];
    }

    public Month thirdMonth() {
        return months[2];
    }

}

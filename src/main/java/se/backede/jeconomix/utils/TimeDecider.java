/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.utils;

import java.time.Year;
import java.time.YearMonth;
import se.backede.jeconomix.constants.BudgetQuarterEnum;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class TimeDecider {

    public static BudgetQuarterEnum getCurrenQuarter() {
        YearMonth month = YearMonth.now();
        switch (month.getMonth()) {
            case JANUARY:
            case FEBRUARY:
            case MARCH:
                return BudgetQuarterEnum.QUARTER1;
            case APRIL:
            case MAY:
            case JUNE:
                return BudgetQuarterEnum.QUARTER2;
            case JULY:
            case AUGUST:
            case SEPTEMBER:
                return BudgetQuarterEnum.QUARTER3;
            case OCTOBER:
            case NOVEMBER:
            case DECEMBER:
                return BudgetQuarterEnum.QUARTER4;
            default:
                throw new AssertionError();
        }
    }

    public static Year getCurrentYear() {
        return Year.of(YearMonth.now().getYear());
    }

}

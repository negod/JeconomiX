/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto;

import java.math.BigDecimal;
import java.time.Month;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Builder
public class TransactionReportDto {

    String category;
    Map<Month, BigDecimal> monthReport = new HashMap<Month, BigDecimal>();
    List<TransactionDto> transctions = new LinkedList<>();
    BigDecimal sum;

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.export;

import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Setter
@XmlRootElement
@ToString
public class BudgetExpenseExportDto {

    private static final long serialVersionUID = 1L;

    private String id;
    private Date updatedDate;
    private BigDecimal estimatedsum;
    private CategoryExportDto category;

}

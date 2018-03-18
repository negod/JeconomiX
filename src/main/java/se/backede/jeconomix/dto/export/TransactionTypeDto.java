/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.dto.export;

import se.backede.jeconomix.dto.*;
import com.negod.generics.persistence.dto.GenericDto;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@Setter
public class TransactionTypeDto extends GenericDto {

    private String name;

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.constants;

import com.backede.fileutils.csv.parser.CsvColumn;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Getter
@RequiredArgsConstructor
public enum NordeaCsvFields implements CsvColumn {

    TRANSACTION("Transaktion"),
    BELOPP("Belopp"),
    SALDO("Saldo"),
    DATE("Datum"),
    CATEGORY("Kategori"),
    ORIGINAL_VALUE("origanalValue");

    private final String columnName;

}

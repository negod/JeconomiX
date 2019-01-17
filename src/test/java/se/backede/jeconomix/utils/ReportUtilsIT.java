/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.utils;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.dto.TransactionReportDto;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class ReportUtilsIT {

    public ReportUtilsIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    public TransactionDto getTransaction(Integer year, Month month, BigDecimal sum) {
        TransactionDto dto = new TransactionDto();
        dto.setBudgetMonth(month);
        dto.setBudgetYear(year);
        dto.setSum(sum);
        return dto;
    }

    public CompanyDto getCompany(CategoryDto category, Set<TransactionDto> transactions) {
        CompanyDto dto = new CompanyDto("TEST");
        dto.setCategory(category);
        dto.setTransactions(transactions);
        return dto;
    }

    private CategoryDto getCategory() {
        CategoryDto dto = new CategoryDto();
        dto.setName("TEST");
        return dto;
    }

}

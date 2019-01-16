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

    /**
     * Test of calculateAvg method, of class ReportUtils.
     */
    @Test
    public void testCalculateAvg() {
        System.out.println("calculateAvg");
        List<TransactionReportDto> reports = null;
        Map<Month, BigDecimal> expResult = null;
        Map<Month, BigDecimal> result = ReportUtils.calculateAvg(reports);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createDataset method, of class ReportUtils.
     */
    @Test
    public void testCreateDataset() {
        System.out.println("createDataset");
        Map<String, List<TransactionReportDto>> reports = null;
        Boolean avg = null;
        DefaultCategoryDataset expResult = null;
        DefaultCategoryDataset result = ReportUtils.createDataset(reports, avg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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

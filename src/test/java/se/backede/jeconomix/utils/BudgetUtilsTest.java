/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;
import se.backede.jeconomix.mock.TestMock;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BudgetUtilsTest {

    public BudgetUtilsTest() {
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
     * Test of createBudgetFromBudget method, of class BudgetUtils.
     */
    @Test
    public void testCreateBudgetFromBudget() {
        System.out.println("createBudgetFromBudget");
    }

    /**
     * Test of createBudgetFromTransaction method, of class BudgetUtils.
     */
    @Test
    public void testCreateBudgetFromTransaction() {
        System.out.println("createBudgetFromTransaction");
    }

    /**
     * Test of getSumsFromBudgetExpense method, of class BudgetUtils.
     */
    @Test
    public void testGetPositiveSumsFromBudgetExpense_SameCategoryType() {
        System.out.println("testGetPositiveSumsFromBudgetExpense_SameCategoryType");

        List<BudgetExpenseDto> budgetList = new ArrayList<>();
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(100.00)));

        BigDecimal sumsFromBudgetExpense = BudgetUtils.getSumsFromBudgetExpense(budgetList, CategoryTypeEnum.INCOME);

        assertEquals(BigDecimal.valueOf(500.00), sumsFromBudgetExpense);

    }

    @Test
    public void testGetPositiveSumsFromBudgetExpense_DifferentCategoryType() {
        System.out.println("testGetPositiveSumsFromBudgetExpense_DifferentCategoryType");

        List<BudgetExpenseDto> budgetList = new ArrayList<>();
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.BILL, BigDecimal.valueOf(200.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.BILL, BigDecimal.valueOf(200.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.EXPENSE, BigDecimal.valueOf(100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.EXPENSE, BigDecimal.valueOf(100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.TRANSFER, BigDecimal.valueOf(100.00)));

        BigDecimal sumsFromBudgetIncome = BudgetUtils.getSumsFromBudgetExpense(budgetList, CategoryTypeEnum.INCOME);
        BigDecimal sumsFromBudgetBill = BudgetUtils.getSumsFromBudgetExpense(budgetList, CategoryTypeEnum.BILL);
        BigDecimal sumsFromBudgetExpense = BudgetUtils.getSumsFromBudgetExpense(budgetList, CategoryTypeEnum.EXPENSE);

        assertEquals(BigDecimal.valueOf(500.00), sumsFromBudgetIncome);
        assertEquals(BigDecimal.valueOf(400.00), sumsFromBudgetBill);
        assertEquals(BigDecimal.valueOf(200.00), sumsFromBudgetExpense);

    }

    @Test
    public void testGetNegativeSumsFromBudgetExpense_SameCategoryType() {
        System.out.println("testGetNegativeSumsFromBudgetExpense_SameCategoryType");

        List<BudgetExpenseDto> budgetList = new ArrayList<>();
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(-100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(-100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(-100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(-100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(-100.00)));

        BigDecimal sumsFromBudgetExpense = BudgetUtils.getSumsFromBudgetExpense(budgetList, CategoryTypeEnum.INCOME);

        assertEquals(BigDecimal.valueOf(-500.00), sumsFromBudgetExpense);

    }

    @Test
    public void testGetNegativeSumsFromBudgetExpense_DifferentCategoryType() {
        System.out.println("testGetNegativeSumsFromBudgetExpense_DifferentCategoryType");

        List<BudgetExpenseDto> budgetList = new ArrayList<>();
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(-100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(-100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(-100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(-100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(-100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.BILL, BigDecimal.valueOf(-200.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.BILL, BigDecimal.valueOf(-200.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.EXPENSE, BigDecimal.valueOf(-100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.EXPENSE, BigDecimal.valueOf(-100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.TRANSFER, BigDecimal.valueOf(-100.00)));

        BigDecimal sumsFromBudgetIncome = BudgetUtils.getSumsFromBudgetExpense(budgetList, CategoryTypeEnum.INCOME);
        BigDecimal sumsFromBudgetBill = BudgetUtils.getSumsFromBudgetExpense(budgetList, CategoryTypeEnum.BILL);
        BigDecimal sumsFromBudgetExpense = BudgetUtils.getSumsFromBudgetExpense(budgetList, CategoryTypeEnum.EXPENSE);

        assertEquals(BigDecimal.valueOf(-500.00), sumsFromBudgetIncome);
        assertEquals(BigDecimal.valueOf(-400.00), sumsFromBudgetBill);
        assertEquals(BigDecimal.valueOf(-200.00), sumsFromBudgetExpense);

    }

    @Test
    public void testGetMIxedSumsFromBudgetExpense_DifferentCategoryType() {
        System.out.println("testGetMIxedSumsFromBudgetExpense_DifferentCategoryType");

        List<BudgetExpenseDto> budgetList = new ArrayList<>();
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.INCOME, BigDecimal.valueOf(-100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.BILL, BigDecimal.valueOf(-200.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.BILL, BigDecimal.valueOf(-200.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.EXPENSE, BigDecimal.valueOf(-100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.EXPENSE, BigDecimal.valueOf(-100.00)));
        budgetList.add(TestMock.getBudgetExpense(CategoryTypeEnum.TRANSFER, BigDecimal.valueOf(-100.00)));

        BigDecimal sumsFromBudgetIncome = BudgetUtils.getSumsFromBudgetExpense(budgetList, CategoryTypeEnum.INCOME);
        BigDecimal sumsFromBudgetBill = BudgetUtils.getSumsFromBudgetExpense(budgetList, CategoryTypeEnum.BILL);
        BigDecimal sumsFromBudgetExpense = BudgetUtils.getSumsFromBudgetExpense(budgetList, CategoryTypeEnum.EXPENSE);

        assertEquals(BigDecimal.valueOf(300.00), sumsFromBudgetIncome);
        assertEquals(BigDecimal.valueOf(-400.00), sumsFromBudgetBill);
        assertEquals(BigDecimal.valueOf(-200.00), sumsFromBudgetExpense);

    }

}

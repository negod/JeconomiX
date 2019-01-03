/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.budget;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.constants.ComboBoxRenderer;
import se.backede.jeconomix.database.BudgetExpenseHandler;
import se.backede.jeconomix.dto.budget.BudgetDto;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.events.BudgetEvent;
import se.backede.jeconomix.forms.basic.NegodPanel;
import se.backede.jeconomix.models.combobox.CategoryComboBoxModel;
import se.backede.jeconomix.models.table.BudgetModel;
import se.backede.jeconomix.renderer.combobox.CategoryItemRenderer;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class BudgetMonth extends NegodPanel {

    private static final long serialVersionUID = 1L;

    private YearMonth CURRENT_YEAR_MONTH;

    /**
     * Creates new form BudgetMonth
     */
    public BudgetMonth() {
        initComponents();
        setEvents();
    }

    public void setMonth(YearMonth yearMonth) {
        this.CURRENT_YEAR_MONTH = yearMonth;
        monthLabel.setText(yearMonth.getMonth().name());

        setInitData(billTable, yearMonth, totalBillLbl, CategoryTypeEnum.BILL);
        setInitData(expenseTable, yearMonth, totalExpenseLbl, CategoryTypeEnum.EXPENSE);
        setInitData(incomeTable, yearMonth, totalIncomeLbl, CategoryTypeEnum.INCOME);
        setInitData(creditCardTable, yearMonth, totalIncomeLbl, CategoryTypeEnum.CREDIT_CARD);
        setInitData(loanTable, yearMonth, totalIncomeLbl, CategoryTypeEnum.LOAN);
        setInitData(savingsTable, yearMonth, totalIncomeLbl, CategoryTypeEnum.SAVING);

        setTotalSumLabel();
    }

    public void setInitData(JTable table, YearMonth budgetMonth, JLabel lbl, CategoryTypeEnum category) {
        table.setModel(new BudgetModel(budgetMonth, category));
        setUpDropDownColumn(table.getColumnModel().getColumn(0), category);
        BudgetModel model = (BudgetModel) table.getModel();
        lbl.setText(model.getTotalSum().toString());
    }

    public void setEvents() {

        Consumer<BudgetExpenseDto> createBudgetExpense = (dto) -> {

            if (dto.getBudget().getMonth().equals(CURRENT_YEAR_MONTH.getMonth()) && dto.getBudget().getYear() == CURRENT_YEAR_MONTH.getYear()) {

                switch (dto.getCategory().getCategoryType().getType()) {

                    case INCOME:
                        BudgetModel icomeModel = (BudgetModel) incomeTable.getModel();
                        icomeModel.addBudgetExpence(dto);
                        totalIncomeLbl.setText(icomeModel.getTotalSum().toString());
                        break;
                    case EXPENSE:
                        BudgetModel expenseModel = (BudgetModel) expenseTable.getModel();
                        expenseModel.addBudgetExpence(dto);
                        totalExpenseLbl.setText(expenseModel.getTotalSum().toString());
                        break;
                    case BILL:
                        BudgetModel billModel = (BudgetModel) billTable.getModel();
                        billModel.addBudgetExpence(dto);
                        totalBillLbl.setText(billModel.getTotalSum().toString());
                        break;
                    case TRANSFER:
                        break;
                    case LOAN:
                        BudgetModel loanModel = (BudgetModel) loanTable.getModel();
                        loanModel.addBudgetExpence(dto);
                        totalBillLbl.setText(loanModel.getTotalSum().toString());
                        break;
                    case SAVING:
                        BudgetModel savingsModel = (BudgetModel) savingsTable.getModel();
                        savingsModel.addBudgetExpence(dto);
                        totalBillLbl.setText(savingsModel.getTotalSum().toString());
                        break;
                    case CREDIT_CARD:
                        BudgetModel creditCardModel = (BudgetModel) creditCardTable.getModel();
                        creditCardModel.addBudgetExpence(dto);
                        totalBillLbl.setText(creditCardModel.getTotalSum().toString());
                        break;
                    case POCKET_MONEY:
                        break;
                    default:
                        throw new AssertionError(dto.getCategory().getCategoryType().getType().name());

                }

            }
            setTotalSumLabel();
        };
        EventController.getInstance().addObserver(BudgetEvent.ADD_BUDGET_ROW, createBudgetExpense);

        Consumer<BudgetDto> clearAllTables = (dto) -> {

            if (dto.getMonth().equals(CURRENT_YEAR_MONTH.getMonth()) && dto.getYear() == CURRENT_YEAR_MONTH.getYear()) {

                BudgetModel icomeModel = (BudgetModel) incomeTable.getModel();
                BudgetModel expenseModel = (BudgetModel) expenseTable.getModel();
                BudgetModel billModel = (BudgetModel) billTable.getModel();

                List<BudgetExpenseDto> allBudgetExpenses = new ArrayList<>();
                allBudgetExpenses.addAll(icomeModel.getAll());
                allBudgetExpenses.addAll(expenseModel.getAll());
                allBudgetExpenses.addAll(billModel.getAll());

                allBudgetExpenses.forEach((budgetExpense) -> {
                    BudgetExpenseHandler.getInstance().deleteBudgetExpense(budgetExpense);
                });

                icomeModel.clearAll();
                expenseModel.clearAll();
                billModel.clearAll();
            }
        };

        EventController.getInstance().addObserver(BudgetEvent.CLEAR_BUDGET_LISTS, clearAllTables);

    }

    public void setTotalSumLabel() {
        BudgetModel icomeModel = (BudgetModel) incomeTable.getModel();
        BudgetModel expenseModel = (BudgetModel) expenseTable.getModel();
        BudgetModel billModel = (BudgetModel) billTable.getModel();
        BudgetModel loanModel = (BudgetModel) loanTable.getModel();
        BudgetModel creditModel = (BudgetModel) creditCardTable.getModel();
        BudgetModel savingsModel = (BudgetModel) savingsTable.getModel();

        BigDecimal incomeSum = icomeModel.getTotalSum();
        BigDecimal expenseSum = expenseModel.getTotalSum();
        BigDecimal billSum = billModel.getTotalSum();
        BigDecimal loanSum = loanModel.getTotalSum();
        BigDecimal creditSum = creditModel.getTotalSum();
        BigDecimal savingsSum = savingsModel.getTotalSum();

        BigDecimal subtract = incomeSum.subtract(expenseSum);
        BigDecimal subtract1 = subtract.subtract(billSum);
        BigDecimal subtract2 = subtract1.subtract(loanSum);
        BigDecimal subtract3 = subtract2.subtract(creditSum);
        BigDecimal subtract4 = subtract3.subtract(savingsSum);

        totalSumLabel.setText(subtract4.toString());

    }

    public void setUpDropDownColumn(TableColumn column, CategoryTypeEnum type) {
        JComboBox comboBox = new JComboBox();

        comboBox.setModel(new CategoryComboBoxModel(type));
        comboBox.setRenderer(new CategoryItemRenderer(ComboBoxRenderer.SINGLE));
        column.setCellEditor(new DefaultCellEditor(comboBox));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        column.setCellRenderer(renderer);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane6 = new javax.swing.JScrollPane();
        savingsTable = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        creditCardTable = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        loanTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        incomeTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        billTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        expenseTable = new javax.swing.JTable();
        monthLabel = new javax.swing.JLabel();
        addIncomeBtn = new javax.swing.JButton();
        removeIncomeBtn = new javax.swing.JButton();
        removeBillBtn = new javax.swing.JButton();
        addBillBtn = new javax.swing.JButton();
        addExpenseBtn = new javax.swing.JButton();
        removeExpenseBtn = new javax.swing.JButton();
        budgetSuggestionBtn = new javax.swing.JButton();
        totalIncomeLbl = new javax.swing.JLabel();
        totalBillLbl = new javax.swing.JLabel();
        totalExpenseLbl = new javax.swing.JLabel();
        totalSumLabel = new javax.swing.JLabel();
        outcomeButton = new javax.swing.JButton();
        addLoanBtn = new javax.swing.JButton();
        removeLoanBtn = new javax.swing.JButton();
        addSavingBtn = new javax.swing.JButton();
        removeSavingBtn = new javax.swing.JButton();
        totalLoanLbl = new javax.swing.JLabel();
        totalSAvingLbl = new javax.swing.JLabel();
        addCreditCardBtn = new javax.swing.JButton();
        removeCreditCardBtn = new javax.swing.JButton();
        totalCreditCardLbl = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        savingsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Savings", "Sum"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(savingsTable);
        if (savingsTable.getColumnModel().getColumnCount() > 0) {
            savingsTable.getColumnModel().getColumn(0).setResizable(false);
        }

        creditCardTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Credit card", "Sum"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(creditCardTable);
        if (creditCardTable.getColumnModel().getColumnCount() > 0) {
            creditCardTable.getColumnModel().getColumn(0).setResizable(false);
        }

        loanTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Loan", "Sum"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(loanTable);
        if (loanTable.getColumnModel().getColumnCount() > 0) {
            loanTable.getColumnModel().getColumn(0).setResizable(false);
        }

        incomeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Income", "Sum"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        incomeTable.setFocusCycleRoot(true);
        jScrollPane1.setViewportView(incomeTable);
        if (incomeTable.getColumnModel().getColumnCount() > 0) {
            incomeTable.getColumnModel().getColumn(0).setResizable(false);
            incomeTable.getColumnModel().getColumn(1).setResizable(false);
        }

        billTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bill", "Sum"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(billTable);
        if (billTable.getColumnModel().getColumnCount() > 0) {
            billTable.getColumnModel().getColumn(0).setResizable(false);
            billTable.getColumnModel().getColumn(1).setResizable(false);
        }

        expenseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Expense", "Sum"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(expenseTable);
        if (expenseTable.getColumnModel().getColumnCount() > 0) {
            expenseTable.getColumnModel().getColumn(0).setResizable(false);
        }

        monthLabel.setBackground(new java.awt.Color(255, 255, 255));
        monthLabel.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        monthLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        monthLabel.setText("JANUARY");

        addIncomeBtn.setBackground(new java.awt.Color(255, 255, 255));
        addIncomeBtn.setText("+");
        addIncomeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addIncomeBtnActionPerformed(evt);
            }
        });

        removeIncomeBtn.setBackground(new java.awt.Color(255, 255, 255));
        removeIncomeBtn.setText("-");
        removeIncomeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeIncomeBtnActionPerformed(evt);
            }
        });

        removeBillBtn.setBackground(new java.awt.Color(255, 255, 255));
        removeBillBtn.setText("-");
        removeBillBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBillBtnActionPerformed(evt);
            }
        });

        addBillBtn.setBackground(new java.awt.Color(255, 255, 255));
        addBillBtn.setText("+");
        addBillBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBillBtnActionPerformed(evt);
            }
        });

        addExpenseBtn.setBackground(new java.awt.Color(255, 255, 255));
        addExpenseBtn.setText("+");
        addExpenseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addExpenseBtnActionPerformed(evt);
            }
        });

        removeExpenseBtn.setBackground(new java.awt.Color(255, 255, 255));
        removeExpenseBtn.setText("-");
        removeExpenseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeExpenseBtnActionPerformed(evt);
            }
        });

        budgetSuggestionBtn.setBackground(new java.awt.Color(255, 255, 255));
        budgetSuggestionBtn.setText("Create budget");
        budgetSuggestionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                budgetSuggestionBtnActionPerformed(evt);
            }
        });

        totalIncomeLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalIncomeLbl.setText("0.00");

        totalBillLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalBillLbl.setText("0.00");

        totalExpenseLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalExpenseLbl.setText("0.00");

        totalSumLabel.setText("jLabel1");

        outcomeButton.setText("$");
        outcomeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outcomeButtonActionPerformed(evt);
            }
        });

        addLoanBtn.setBackground(new java.awt.Color(255, 255, 255));
        addLoanBtn.setText("+");
        addLoanBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLoanBtnActionPerformed(evt);
            }
        });

        removeLoanBtn.setBackground(new java.awt.Color(255, 255, 255));
        removeLoanBtn.setText("-");
        removeLoanBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeLoanBtnActionPerformed(evt);
            }
        });

        addSavingBtn.setBackground(new java.awt.Color(255, 255, 255));
        addSavingBtn.setText("+");
        addSavingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSavingBtnActionPerformed(evt);
            }
        });

        removeSavingBtn.setBackground(new java.awt.Color(255, 255, 255));
        removeSavingBtn.setText("-");
        removeSavingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeSavingBtnActionPerformed(evt);
            }
        });

        totalLoanLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalLoanLbl.setText("0.00");

        totalSAvingLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalSAvingLbl.setText("0.00");

        addCreditCardBtn.setBackground(new java.awt.Color(255, 255, 255));
        addCreditCardBtn.setText("+");
        addCreditCardBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCreditCardBtnActionPerformed(evt);
            }
        });

        removeCreditCardBtn.setBackground(new java.awt.Color(255, 255, 255));
        removeCreditCardBtn.setText("-");
        removeCreditCardBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeCreditCardBtnActionPerformed(evt);
            }
        });

        totalCreditCardLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalCreditCardLbl.setText("0.00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(budgetSuggestionBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalSumLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalBillLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totalCreditCardLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totalSAvingLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(addIncomeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(addBillBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(outcomeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(removeIncomeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(removeBillBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addCreditCardBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeCreditCardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addSavingBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeSavingBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addLoanBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeLoanBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(totalLoanLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(totalIncomeLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(totalExpenseLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addExpenseBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeExpenseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(budgetSuggestionBtn)
                    .addComponent(totalSumLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(outcomeButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addIncomeBtn)
                            .addComponent(removeIncomeBtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalIncomeLbl))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addBillBtn)
                            .addComponent(removeBillBtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalBillLbl))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addExpenseBtn)
                            .addComponent(removeExpenseBtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalExpenseLbl)
                        .addGap(94, 94, 94)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(addLoanBtn)
                                    .addComponent(removeLoanBtn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalLoanLbl))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(addSavingBtn)
                                    .addComponent(removeSavingBtn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalSAvingLbl)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(addCreditCardBtn)
                                    .addComponent(removeCreditCardBtn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalCreditCardLbl))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public void removeBudgetExpense(JTable table) {
        BudgetModel model = (BudgetModel) table.getModel();
        model.removeBudgetExpenseAt(table.getSelectedRow());
        setTotalSumLabel();
    }

    private void addBudgetLine(CategoryTypeEnum category) {
        AddBudgetLine budgetLine = new AddBudgetLine(CURRENT_YEAR_MONTH);
        budgetLine.init(category);
        budgetLine.setVisible(true);
    }

    private void addIncomeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addIncomeBtnActionPerformed
        addBudgetLine(CategoryTypeEnum.INCOME);
    }//GEN-LAST:event_addIncomeBtnActionPerformed

    private void removeIncomeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeIncomeBtnActionPerformed
        removeBudgetExpense(incomeTable);
    }//GEN-LAST:event_removeIncomeBtnActionPerformed

    private void removeExpenseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeExpenseBtnActionPerformed
        removeBudgetExpense(expenseTable);
    }//GEN-LAST:event_removeExpenseBtnActionPerformed

    private void addBillBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBillBtnActionPerformed
        addBudgetLine(CategoryTypeEnum.BILL);
    }//GEN-LAST:event_addBillBtnActionPerformed

    private void removeBillBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBillBtnActionPerformed
        removeBudgetExpense(billTable);
    }//GEN-LAST:event_removeBillBtnActionPerformed

    private void addExpenseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addExpenseBtnActionPerformed
        addBudgetLine(CategoryTypeEnum.EXPENSE);
    }//GEN-LAST:event_addExpenseBtnActionPerformed

    private void budgetSuggestionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_budgetSuggestionBtnActionPerformed
        new BudgetSuggestion(null, true, CURRENT_YEAR_MONTH).setVisible(true);
    }//GEN-LAST:event_budgetSuggestionBtnActionPerformed

    private void outcomeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outcomeButtonActionPerformed
        new BudgetOutcome(CURRENT_YEAR_MONTH).setVisible(true);
    }//GEN-LAST:event_outcomeButtonActionPerformed

    private void addLoanBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLoanBtnActionPerformed
        addBudgetLine(CategoryTypeEnum.LOAN);
    }//GEN-LAST:event_addLoanBtnActionPerformed

    private void removeLoanBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeLoanBtnActionPerformed
        removeBudgetExpense(loanTable);
    }//GEN-LAST:event_removeLoanBtnActionPerformed

    private void addSavingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSavingBtnActionPerformed
        addBudgetLine(CategoryTypeEnum.SAVING);
    }//GEN-LAST:event_addSavingBtnActionPerformed

    private void removeSavingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSavingBtnActionPerformed
        removeBudgetExpense(savingsTable);
    }//GEN-LAST:event_removeSavingBtnActionPerformed

    private void addCreditCardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCreditCardBtnActionPerformed
        addBudgetLine(CategoryTypeEnum.CREDIT_CARD);
    }//GEN-LAST:event_addCreditCardBtnActionPerformed

    private void removeCreditCardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeCreditCardBtnActionPerformed
        removeBudgetExpense(creditCardTable);
    }//GEN-LAST:event_removeCreditCardBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBillBtn;
    private javax.swing.JButton addCreditCardBtn;
    private javax.swing.JButton addExpenseBtn;
    private javax.swing.JButton addIncomeBtn;
    private javax.swing.JButton addLoanBtn;
    private javax.swing.JButton addSavingBtn;
    private javax.swing.JTable billTable;
    private javax.swing.JButton budgetSuggestionBtn;
    private javax.swing.JTable creditCardTable;
    private javax.swing.JTable expenseTable;
    private javax.swing.JTable incomeTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable loanTable;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JButton outcomeButton;
    private javax.swing.JButton removeBillBtn;
    private javax.swing.JButton removeCreditCardBtn;
    private javax.swing.JButton removeExpenseBtn;
    private javax.swing.JButton removeIncomeBtn;
    private javax.swing.JButton removeLoanBtn;
    private javax.swing.JButton removeSavingBtn;
    private javax.swing.JTable savingsTable;
    private javax.swing.JLabel totalBillLbl;
    private javax.swing.JLabel totalCreditCardLbl;
    private javax.swing.JLabel totalExpenseLbl;
    private javax.swing.JLabel totalIncomeLbl;
    private javax.swing.JLabel totalLoanLbl;
    private javax.swing.JLabel totalSAvingLbl;
    private javax.swing.JLabel totalSumLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void init() {
    }

}

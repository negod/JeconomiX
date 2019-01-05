/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.budget;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.time.YearMonth;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.constants.ComboBoxRenderer;
import se.backede.jeconomix.database.BudgetExpenseHandler;
import se.backede.jeconomix.database.BudgetHandler;
import se.backede.jeconomix.database.CategoryHandler;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.budget.BudgetExpenseDto;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.events.BudgetEvent;
import se.backede.jeconomix.event.events.dto.BudgetEventDto;
import se.backede.jeconomix.event.events.dto.BudgetExpenseEventDto;
import se.backede.jeconomix.renderer.combobox.CategoryItemRenderer;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class AddBudgetLine extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    private final YearMonth BUDGET_MONTH;

    public AddBudgetLine(YearMonth month) {
        this.BUDGET_MONTH = month;
        initComponents();

        PlainDocument doc = (PlainDocument) sumTextField.getDocument();
        installNumberCharacters(doc);

        this.getRootPane().setDefaultButton(okBtn);

        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                categoryComboBox.requestFocus();
            }
        });

    }

    public void init(CategoryTypeEnum type) {
        categoryTypeLabel.setText(type.name());

        ObjectToStringConverter converter = new ObjectToStringConverter() {
            @Override
            public String getPreferredStringForItem(Object o) {
                if (o instanceof CategoryDto) {
                    CategoryDto dto = (CategoryDto) o;
                    return dto.getName();
                }
                return o.toString();
            }
        };

        CategoryHandler.getInstance().getFilteredCategories(type).ifPresent(categiryList -> {
            categiryList.forEach(category -> {
                categoryComboBox.addItem(category);
            });
        });

        categoryComboBox.setRenderer(new CategoryItemRenderer(ComboBoxRenderer.SINGLE));
        AutoCompleteDecorator.decorate(categoryComboBox, converter);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        categoryTypeLabel = new javax.swing.JLabel();
        okBtn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        sumTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentTextArea = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        categoryComboBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add budget line");
        setAlwaysOnTop(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
        jLabel1.setText("Type:");

        categoryTypeLabel.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
        categoryTypeLabel.setText("INCOME");

        okBtn.setText("OK");
        okBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okBtnActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        jLabel3.setLabelFor(sumTextField);
        jLabel3.setText("Sum:");

        commentTextArea.setEditable(false);
        commentTextArea.setColumns(20);
        commentTextArea.setRows(5);
        commentTextArea.setEnabled(false);
        jScrollPane1.setViewportView(commentTextArea);

        jLabel4.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        jLabel4.setLabelFor(commentTextArea);
        jLabel4.setText("Comment:");

        categoryComboBox.setEditable(true);

        jLabel5.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        jLabel5.setLabelFor(categoryComboBox);
        jLabel5.setText("Category:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(categoryTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jButton2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(okBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                            .addComponent(sumTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(categoryComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(categoryTypeLabel))
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(categoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sumTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okBtn)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed
        BudgetHandler.getInstance().getBudget(BUDGET_MONTH).ifPresent(budget -> {
            BudgetExpenseDto dto = new BudgetExpenseDto();
            dto.setBudget(budget);
            dto.setCategory(categoryComboBox.getItemAt(categoryComboBox.getSelectedIndex()));
            Double valueOf = Double.valueOf(sumTextField.getText());
            dto.setEstimatedsum(BigDecimal.valueOf(valueOf));
            BudgetExpenseHandler.getInstance().upsertBudgetExpense(dto).ifPresent(budgetExpense -> {

                BudgetExpenseEventDto build = BudgetExpenseEventDto.builder()
                        .budgetExpense(budgetExpense)
                        .budgetEvent(
                                BudgetEventDto.builder()
                                        .category(budgetExpense.getCategory().getCategoryType().getType())
                                        .yearMonth(BUDGET_MONTH)
                                        .build()
                        )
                        .build();

                EventController.getInstance().notifyObservers(BudgetEvent.ADD_BUDGET_ROW, () -> build);
                this.dispose();
            });
        });
    }//GEN-LAST:event_okBtnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<CategoryDto> categoryComboBox;
    private javax.swing.JLabel categoryTypeLabel;
    private javax.swing.JTextArea commentTextArea;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton okBtn;
    private javax.swing.JTextField sumTextField;
    // End of variables declaration//GEN-END:variables

    public static void installNumberCharacters(AbstractDocument document) {

        document.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                try {
                    if (string.equals(".") && !fb.getDocument().getText(0, fb.getDocument().getLength()).contains(".")) {
                        super.insertString(fb, offset, string, attr);
                        return;
                    }
                    Double.parseDouble(string);
                    super.insertString(fb, offset, string, attr);
                } catch (NumberFormatException | BadLocationException e) {
                    Toolkit.getDefaultToolkit().beep();
                }

            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                try {
                    if (text.equals(".") && !fb.getDocument().getText(0, fb.getDocument().getLength()).contains(".")) {
                        super.insertString(fb, offset, text, attrs);
                        return;
                    }
                    Double.parseDouble(text);
                    super.replace(fb, offset, length, text, attrs);
                } catch (NumberFormatException | BadLocationException e) {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
    }

}

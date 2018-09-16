/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.category;

import java.util.function.Consumer;
import java.util.function.Supplier;
import se.backede.jeconomix.constants.CategoryTypeEnum;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.events.CategoryEvent;
import se.backede.jeconomix.forms.basic.NegodPanel;
import se.backede.jeconomix.forms.basic.component.ComboBoxWrapper;
import se.backede.jeconomix.models.combobox.CategoryComboBoxModel;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class CategoryChooser extends NegodPanel {

    ComboBoxWrapper<CategoryDto, CategoryComboBoxModel> categoryCB;

    /**
     * Creates new form CategoryChooser
     */
    public CategoryChooser() {
        super();
        initComponents();

        Consumer<CategoryDto> setSelectedCompany = category -> {
            setRadioButton(category.getCategoryType().getType());
            categoryCB.setComboBoxModel(new CategoryComboBoxModel(category.getCategoryType().getType()));
            categoryCB.setSelectedItem(category);
        };
        EventController.getInstance().addObserver(CategoryEvent.SET_SELECTED, setSelectedCompany);

        Consumer<CategoryDto> createCategory = category -> {
            categoryCB.getComboBoxModel().addElement(category);
            categoryCB.setSelectedItem(category);
        };
        EventController.getInstance().addObserver(CategoryEvent.CREATE, createCategory);

        Consumer<CategoryDto> getSelectedCategory = category -> {
            EventController.getInstance().notifyObservers(CategoryEvent.SELECTED, categoryCB::getSelectedItem);
        };
        EventController.getInstance().addObserver(CategoryEvent.GET_SELECTED, getSelectedCategory);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        categoryButtonGroup = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        categoryComboBox = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        incomeRadioButton = new javax.swing.JRadioButton();
        billRadioButton = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        expenseRadioButton = new javax.swing.JRadioButton();
        transferRadionButton = new javax.swing.JRadioButton();
        addCategoryBtn = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        categoryComboBox.setBackground(new java.awt.Color(255, 255, 255));
        categoryComboBox.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        categoryComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        categoryComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                categoryComboBoxItemStateChanged(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        incomeRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        categoryButtonGroup.add(incomeRadioButton);
        incomeRadioButton.setText("Income");
        incomeRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incomeRadioButtonActionPerformed(evt);
            }
        });

        billRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        categoryButtonGroup.add(billRadioButton);
        billRadioButton.setText("Bill");
        billRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billRadioButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("Choose Category!");

        expenseRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        categoryButtonGroup.add(expenseRadioButton);
        expenseRadioButton.setText("Expense");
        expenseRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expenseRadioButtonActionPerformed(evt);
            }
        });

        transferRadionButton.setBackground(new java.awt.Color(255, 255, 255));
        categoryButtonGroup.add(transferRadionButton);
        transferRadionButton.setText("Transfer");
        transferRadionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transferRadionButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(incomeRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(expenseRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(billRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(transferRadionButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(incomeRadioButton)
                    .addComponent(expenseRadioButton)
                    .addComponent(billRadioButton)
                    .addComponent(transferRadionButton))
                .addContainerGap())
        );

        addCategoryBtn.setBackground(new java.awt.Color(255, 255, 255));
        addCategoryBtn.setText("Create  new category");
        addCategoryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCategoryBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 29, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(categoryComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(addCategoryBtn)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(categoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addCategoryBtn)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void categoryComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_categoryComboBoxItemStateChanged
        if (evt.getItem() != null) {
            Supplier<CategoryDto> getCategory = () -> (CategoryDto) evt.getItem();
            EventController.getInstance().notifyObservers(CategoryEvent.SELECTED, getCategory);
        }
    }//GEN-LAST:event_categoryComboBoxItemStateChanged

    private void addCategoryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCategoryBtnActionPerformed
        new AddCategory(null, true).setVisible(true);
    }//GEN-LAST:event_addCategoryBtnActionPerformed

    private void incomeRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incomeRadioButtonActionPerformed
        categoryCB.setComboBoxModel(new CategoryComboBoxModel(CategoryTypeEnum.INCOME));
        EventController.getInstance().notifyObservers(CategoryEvent.CATEGORY_TYPE_SELECTED, () -> CategoryTypeEnum.INCOME);
    }//GEN-LAST:event_incomeRadioButtonActionPerformed

    private void transferRadionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transferRadionButtonActionPerformed
        categoryCB.setComboBoxModel(new CategoryComboBoxModel(CategoryTypeEnum.TRANSFER));
        EventController.getInstance().notifyObservers(CategoryEvent.CATEGORY_TYPE_SELECTED, () -> CategoryTypeEnum.TRANSFER);
    }//GEN-LAST:event_transferRadionButtonActionPerformed

    private void billRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billRadioButtonActionPerformed
        categoryCB.setComboBoxModel(new CategoryComboBoxModel(CategoryTypeEnum.BILL));
        EventController.getInstance().notifyObservers(CategoryEvent.CATEGORY_TYPE_SELECTED, () -> CategoryTypeEnum.BILL);
    }//GEN-LAST:event_billRadioButtonActionPerformed

    private void expenseRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expenseRadioButtonActionPerformed
        categoryCB.setComboBoxModel(new CategoryComboBoxModel(CategoryTypeEnum.EXPENSE));
        EventController.getInstance().notifyObservers(CategoryEvent.CATEGORY_TYPE_SELECTED, () -> CategoryTypeEnum.EXPENSE);
    }//GEN-LAST:event_expenseRadioButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCategoryBtn;
    private javax.swing.JRadioButton billRadioButton;
    private javax.swing.ButtonGroup categoryButtonGroup;
    private javax.swing.JComboBox<String> categoryComboBox;
    private javax.swing.JRadioButton expenseRadioButton;
    private javax.swing.JRadioButton incomeRadioButton;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton transferRadionButton;
    // End of variables declaration//GEN-END:variables

    private void setRadioButton(CategoryTypeEnum type) {
        switch (type) {
            case INCOME:
                categoryButtonGroup.setSelected(incomeRadioButton.getModel(), true);
                break;
            case EXPENSE:
                categoryButtonGroup.setSelected(expenseRadioButton.getModel(), true);
                break;
            case BILL:
                categoryButtonGroup.setSelected(billRadioButton.getModel(), true);
                break;
            case TRANSFER:
                categoryButtonGroup.setSelected(transferRadionButton.getModel(), true);
                break;
            default:
                throw new AssertionError();
        }
    }

    @Override
    public void init() {
        categoryButtonGroup.setSelected(incomeRadioButton.getModel(), true);
        categoryCB = new ComboBoxWrapper(categoryComboBox);
        categoryCB.setComboBoxModel(new CategoryComboBoxModel(CategoryTypeEnum.INCOME));
        categoryCB.setSelectedItem(categoryCB.getComboBoxModel().getItems().get(0));
    }
}

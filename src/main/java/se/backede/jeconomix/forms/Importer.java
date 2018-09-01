/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms;

import com.backede.fileutils.csv.parser.CsvColumn;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.EventObserver;
import se.backede.jeconomix.event.NegodEvent;
import se.backede.jeconomix.event.events.TransactionEvent;
import se.backede.jeconomix.models.table.TransactionModel;
import se.backede.jeconomix.utils.GenericIterator;
import se.backede.jeconomix.database.CompanyHandler;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.event.dto.Dto;
import se.backede.jeconomix.event.events.CategoryEvent;
import se.backede.jeconomix.event.events.CompanyEvent;
import se.backede.jeconomix.event.events.fields.CategoryValues;
import se.backede.jeconomix.event.events.fields.CompanyValues;
import se.backede.jeconomix.forms.basic.NegodDialog;
import se.backede.jeconomix.importer.TransactionWrapper;
import se.backede.jeconomix.importer.Transactions;

@Getter
@Setter
class CompanyIteratorState {

    Boolean newCompany = Boolean.FALSE;
    CompanyDto parentCompany;

}

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class Importer extends NegodDialog implements EventObserver {

    private final GenericIterator<CompanyDto> companyIterator;
    HashMap<CompanyDto, CompanyIteratorState> companyState = new HashMap<>();

    public Importer(java.awt.Frame parent, boolean modal, Transactions transactions, CsvColumn companyNameColumn) {
        super(parent, modal);

        HashMap<String, List<TransactionDto>> companies = new HashMap<>();

        for (TransactionWrapper transactionWrapper : transactions.getNewTransactionsToEdit()) {
            transactionWrapper.getCsvRecord().getColumn(companyNameColumn).ifPresent(companyName -> {
                if (companies.containsKey(companyName)) {
                    companies.get(companyName).add(transactionWrapper.getTransactionDto());
                } else {
                    companies.put(companyName, Arrays.asList(transactionWrapper.getTransactionDto()));
                }
            });
        }

        List<CompanyDto> companyList = new ArrayList<>();
        for (String companyName : companies.keySet()) {
            CompanyDto company = new CompanyDto(companyName);
            company.getTransactions().addAll(companies.get(companyName));
            companyState.put(company, new CompanyIteratorState());
            companyList.add(company);
        }

        companyIterator = new GenericIterator(new LinkedList<>(companyList));

        initComponents();

        setToggleButtonData();
        progressBar.setMaximum(companyIterator.getAll().size());
        setValues(companyIterator.first());

        categoryChooser1.init();
        companyChooser1.init();

        setToggleButtonData();
    }

    @SuppressWarnings("unchecked")
    public final void setToggleButtonData() {
        CardLayout card = (CardLayout) choosePanel.getLayout();

        if (categoryToggleBtn.isSelected()) {
            categoryToggleBtn.setText("Choose company for association");
            card.show(choosePanel, "companyCard");
        } else {
            categoryToggleBtn.setText("Choose category");
            card.show(choosePanel, "categoryCard");
        }

    }

    private final void setValues(CompanyDto company) {
        counterLabel.setText((progressBar.getValue() + 1) + " of " + progressBar.getMaximum());

        companyName.setText(company.getName());

        TransactionModel transactionModel = new TransactionModel(company.getTransactions());
        transactionsTable.setModel(transactionModel);

        if (company.getCategory() != null) {
            Dto dto = new Dto(CategoryValues.class);
            dto.set(CategoryValues.CATEGORY_DTO, company.getCategory());
            EventController.getInstance().notifyObservers(CategoryEvent.SET_SELECTED, dto);
        }

        categoryToggleBtn.setSelected(companyState.get(company).newCompany);
        setToggleButtonData();

        if (categoryToggleBtn.isSelected()) {
            Dto dto = new Dto(CompanyValues.class);
            dto.set(CompanyValues.COMPANY_DTO, companyState.get(company).parentCompany);
            EventController.getInstance().notifyObservers(CompanyEvent.SET_SELECTED, dto);
        }

        if (!companyIterator.hasNext()) {
            nextButton.setText("Save changes");
        } else {
            nextButton.setText("Next");
        }

        if (!companyIterator.hasPrevious()) {
            prevButton.setEnabled(false);
        } else {
            prevButton.setEnabled(true);
        }
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
        progressBar = new javax.swing.JProgressBar();
        companyName = new javax.swing.JLabel();
        companyLabel = new javax.swing.JLabel();
        categoryToggleBtn = new javax.swing.JToggleButton();
        transactionsScrollPane = new javax.swing.JScrollPane();
        transactionsTable = new javax.swing.JTable();
        lowerButtonPanel = new javax.swing.JPanel();
        prevButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        transactionListLbl = new javax.swing.JLabel();
        counterLabel = new javax.swing.JLabel();
        choosePanel = new javax.swing.JPanel();
        companyChooser1 = new se.backede.jeconomix.forms.company.CompanyChooser();
        categoryChooser1 = new se.backede.jeconomix.forms.category.CategoryChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Import transactions");
        setAlwaysOnTop(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        progressBar.setBackground(new java.awt.Color(255, 255, 255));

        companyName.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        companyName.setText("company");

        companyLabel.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        companyLabel.setText("Company name:");

        categoryToggleBtn.setText("jToggleButton1");
        categoryToggleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryToggleBtnActionPerformed(evt);
            }
        });

        transactionsTable.setBackground(new java.awt.Color(255, 255, 255));
        transactionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1"
            }
        ));
        transactionsScrollPane.setViewportView(transactionsTable);

        lowerButtonPanel.setBackground(new java.awt.Color(255, 255, 255));

        prevButton.setBackground(new java.awt.Color(255, 255, 255));
        prevButton.setText("<- Previous");
        prevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevButtonActionPerformed(evt);
            }
        });

        nextButton.setBackground(new java.awt.Color(255, 255, 255));
        nextButton.setText("Next ->");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout lowerButtonPanelLayout = new javax.swing.GroupLayout(lowerButtonPanel);
        lowerButtonPanel.setLayout(lowerButtonPanelLayout);
        lowerButtonPanelLayout.setHorizontalGroup(
            lowerButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lowerButtonPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(prevButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nextButton)
                .addGap(34, 34, 34))
        );
        lowerButtonPanelLayout.setVerticalGroup(
            lowerButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lowerButtonPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(lowerButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prevButton)
                    .addComponent(nextButton))
                .addContainerGap())
        );

        transactionListLbl.setText("Transactions");

        counterLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        counterLabel.setText("1 of 25");

        choosePanel.setLayout(new java.awt.CardLayout());
        choosePanel.add(companyChooser1, "companyCard");
        choosePanel.add(categoryChooser1, "categoryCard");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(transactionListLbl)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(companyLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(companyName, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(transactionsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(progressBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(choosePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
                        .addComponent(counterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(categoryToggleBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lowerButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(companyLabel)
                    .addComponent(companyName))
                .addGap(27, 27, 27)
                .addComponent(categoryToggleBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(choosePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(transactionListLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(transactionsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(counterLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lowerButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //TODO Fix company if it has been associated
    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        if (companyIterator.hasNext()) {

            CompanyDto atCurrentIndex = companyIterator.getAtCurrentIndex();
            progressBar.setValue(progressBar.getValue() + 1);
            setValues(companyIterator.next());

        } else {

            for (CompanyDto company : companyIterator.getAll()) {

                if (company.getCategory() == null) {
                    JOptionPane.showMessageDialog(rootPane, "Company: " + company.getName() + " must have a category set!", "close", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

            }

            for (CompanyDto company : companyIterator.getAll()) {

                for (TransactionDto transaction : company.getTransactions()) {
                    transaction.setCompany(company);
                }

                //Has the company been created already?
                if (company.getId() == null) {
                    Optional<CompanyDto> persist = CompanyHandler.getInstance().createCompany(company);
                } else {
                    Optional<CompanyDto> updateCompany = CompanyHandler.getInstance().updateCompany(company);
                }

            }

            EventController.getInstance().notifyObservers(TransactionEvent.CREATE, null);
            this.dispose();
        }
    }//GEN-LAST:event_nextButtonActionPerformed

    private void prevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevButtonActionPerformed
        if (companyIterator.hasPrevious()) {
            progressBar.setValue(progressBar.getValue() - 1);
            setValues(companyIterator.previous());
        }
    }//GEN-LAST:event_prevButtonActionPerformed

    private void categoryToggleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryToggleBtnActionPerformed
        setToggleButtonData();
    }//GEN-LAST:event_categoryToggleBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private se.backede.jeconomix.forms.category.CategoryChooser categoryChooser1;
    private javax.swing.JToggleButton categoryToggleBtn;
    private javax.swing.JPanel choosePanel;
    private se.backede.jeconomix.forms.company.CompanyChooser companyChooser1;
    private javax.swing.JLabel companyLabel;
    private javax.swing.JLabel companyName;
    private javax.swing.JLabel counterLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel lowerButtonPanel;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton prevButton;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel transactionListLbl;
    private javax.swing.JScrollPane transactionsScrollPane;
    private javax.swing.JTable transactionsTable;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onEvent(NegodEvent event) {
        if (event.equalsEvent(CategoryEvent.SELECTED)) {
            event.getValues().get(CategoryValues.CATEGORY_DTO).getObject().ifPresent(category -> {
                companyIterator.getAtCurrentIndex().setCategory((CategoryDto) category);
            });
        }

        if (event.equalsEvent(CompanyEvent.SELECTED)) {
            event.getValues().get(CompanyValues.COMPANY_DTO).getObject().ifPresent(company -> {
                CompanyIteratorState state = new CompanyIteratorState();
                state.newCompany = Boolean.TRUE;
                state.parentCompany = (CompanyDto) company;
                companyState.put(companyIterator.getAtCurrentIndex(), state);
            });
        }

    }

}

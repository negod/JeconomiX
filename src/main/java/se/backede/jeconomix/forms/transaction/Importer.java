/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.transaction;

import com.backede.fileutils.csv.parser.CsvColumn;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JOptionPane;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.database.CompanyAccociationHandler;
import se.backede.jeconomix.dto.CompanyDto;
import se.backede.jeconomix.dto.TransactionDto;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.models.table.TransactionModel;
import se.backede.jeconomix.utils.GenericIterator;
import se.backede.jeconomix.database.CompanyHandler;
import se.backede.jeconomix.database.TransactionHandler;
import se.backede.jeconomix.dto.CategoryDto;
import se.backede.jeconomix.dto.CompanyAccociationDto;
import se.backede.jeconomix.dto.ProgressDto;
import se.backede.jeconomix.event.events.CategoryEvent;
import se.backede.jeconomix.event.events.CompanyEvent;
import se.backede.jeconomix.event.events.ProgressEvent;
import se.backede.jeconomix.forms.ProgressDialog;
import se.backede.jeconomix.forms.basic.NegodDialog;
import se.backede.jeconomix.importer.TransactionWrapper;

@Getter
@Setter
class CompanyIteratorState {

    private Optional<CompanyDto> parentCompany = Optional.empty();
}

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class Importer extends NegodDialog {

    private static final long serialVersionUID = 1L;

    private final GenericIterator<CompanyDto> companyIterator;
    private HashMap<String, CompanyIteratorState> companyState = new HashMap<>();

    public Importer(java.awt.Frame parent, boolean modal, Set<TransactionWrapper> transactions, CsvColumn companyNameColumn) {
        super(parent, modal);

        companyIterator = buildCompanyIterator(transactions, companyNameColumn);

        initComponents();
        categoryChooser1.init();
        companyChooser1.init();

        setToggleButtonData();
        setEvents();

        progressBar.setMaximum(companyIterator.getAll().size());
        setValues(companyIterator.first());

    }

    private GenericIterator<CompanyDto> buildCompanyIterator(Set<TransactionWrapper> transactions, CsvColumn companyNameColumn) {

        HashMap<String, List<TransactionDto>> companies = new HashMap<>();
        transactions.forEach((transactionWrapper) -> {
            transactionWrapper.getCsvRecord().getColumn(companyNameColumn).ifPresent(company -> {
                if (companies.containsKey(company)) {
                    companies.get(company).add(transactionWrapper.getTransactionDto());
                } else {
                    companies.put(company, Stream.of(transactionWrapper.getTransactionDto()).collect(Collectors.toList()));
                }
            });
        });

        List<CompanyDto> companyList = new ArrayList<>();
        companies.keySet().forEach((companyNameKey) -> {
            CompanyDto company = new CompanyDto(companyNameKey);
            company.getTransactions().addAll(companies.get(companyNameKey));
            companyState.put(companyNameKey, new CompanyIteratorState());
            companyList.add(company);
        });

        return new GenericIterator<>(new LinkedList<>(companyList));
    }

    private void setEvents() {

        Consumer<CategoryDto> setSelectedCategory = category -> {
            if (category != null) {
                companyIterator.getAtCurrentIndex().setCategory(category);
            }
        };
        EventController.getInstance().addObserver(CategoryEvent.SELECTED, setSelectedCategory);

        Consumer<CompanyDto> setSelectedCompany = company -> {
            companyState.get(companyIterator.getAtCurrentIndex().getName()).setParentCompany(Optional.ofNullable(company));
        };
        EventController.getInstance().addObserver(CompanyEvent.SELECTED, setSelectedCompany);
    }

    @SuppressWarnings("unchecked")
    public final void setToggleButtonData() {
        CardLayout card = (CardLayout) choosePanel.getLayout();

        if (categoryCompanyToggleBtn.isSelected()) {
            categoryCompanyToggleBtn.setText("Change to category");
            card.show(choosePanel, "companyCard");
        } else {
            categoryCompanyToggleBtn.setText("Change to associate company");
            card.show(choosePanel, "categoryCard");
        }

    }

    private void setValues(CompanyDto company) {

        counterLabel.setText((progressBar.getValue() + 1) + " of " + progressBar.getMaximum());
        companyName.setText(company.getName());

        TransactionModel transactionModel = new TransactionModel(company.getTransactions());
        transactionsTable.setModel(transactionModel);

        if (!categoryCompanyToggleBtn.isSelected()) {
            if (company.getCategory() != null) {
                EventController.getInstance().notifyObservers(CategoryEvent.SET_SELECTED, () -> company.getCategory());
            } else {
                EventController.getInstance().notifyObservers(CategoryEvent.GET_SELECTED, () -> new CategoryDto());
            }
        }

        categoryCompanyToggleBtn.setSelected(companyState.get(company.getName()).getParentCompany().isPresent());

        setToggleButtonData();

        if (categoryCompanyToggleBtn.isSelected()) {
            Supplier<Optional<CompanyDto>> getCompany = () -> companyState.get(company.getName()).getParentCompany();
            EventController.getInstance().notifyObservers(CompanyEvent.SET_SELECTED, getCompany);
        }

        nextButton.setText(companyIterator.hasNext() ? "Next" : "Save changes");
        prevButton.setEnabled(companyIterator.hasPrevious());

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
        categoryCompanyToggleBtn = new javax.swing.JToggleButton();
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

        categoryCompanyToggleBtn.setText("jToggleButton1");
        categoryCompanyToggleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryCompanyToggleBtnActionPerformed(evt);
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
                    .addComponent(categoryCompanyToggleBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(categoryCompanyToggleBtn)
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
            progressBar.setValue(progressBar.getValue() + 1);
            setValues(companyIterator.next());
        } else {

            for (CompanyDto company : companyIterator.getAll()) {
                if (company.getCategory() == null) {
                    JOptionPane.showMessageDialog(rootPane, "Company: " + company.getName() + " must have a category set!", "close", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }

            ProgressDialog progressBar = new ProgressDialog(null, false, ProgressDialog.IMPORT);
            progressBar.setLocationRelativeTo(this);
            progressBar.setVisible(true);
            progressBar.setAlwaysOnTop(true);

            Supplier<ProgressDto> setMaxValue = () -> new ProgressDto(companyIterator.getAll().size(), "Creating companies and tranasctions");
            EventController.getInstance().notifyObservers(ProgressEvent.SET_MAX_VALUE, setMaxValue);

            for (CompanyDto company : companyIterator.getAll()) {

                Optional<CompanyAccociationDto> accComp = companyState.get(company.getName()).getParentCompany().map(parent -> {
                    CompanyAccociationDto accDto = new CompanyAccociationDto(company.getName());
                    accDto.setCompany(parent);
                    return CompanyAccociationHandler.getInstance().createAccociatedCompany(accDto).get();
                });

                Optional<CompanyDto> createCompany = Optional.empty();
                if (!accComp.isPresent()) {
                    createCompany = CompanyHandler.getInstance().createCompany(company);
                }
                final Optional<CompanyDto> finalCompanyForLambda = createCompany;

                company.getTransactions().stream().map((transaction) -> {

                    // Set the original value for update later
                    company.setOriginalName(transaction.getOriginalValue());

                    accComp.ifPresentOrElse(accCompany -> {
                        transaction.setAscociatedCompany(accCompany);
                        transaction.setCompany(companyState.get(company.getName()).getParentCompany().get());
                    }, () -> {
                        finalCompanyForLambda.ifPresent(persistedCompany -> {
                            transaction.setCompany(persistedCompany);
                        });
                    });

                    return transaction;

                }).forEachOrdered((transaction) -> {
                    TransactionHandler.getInstance().createTransaction(transaction);
                });

                // Update company with original value
                CompanyHandler.getInstance().updateCompany(company);

                Supplier<ProgressDto> increaseValue = () -> new ProgressDto(1, company.getName());
                EventController.getInstance().notifyObservers(ProgressEvent.INCREASE, increaseValue);

            }

            EventController.getInstance().notifyObservers(ProgressEvent.DONE_AND_CLOSE, () -> new ProgressDto(companyIterator.getAll().size(), "Done creating transactions"));
            this.dispose();
        }
    }//GEN-LAST:event_nextButtonActionPerformed

    private void prevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevButtonActionPerformed
        if (companyIterator.hasPrevious()) {
            progressBar.setValue(progressBar.getValue() - 1);
            setValues(companyIterator.previous());
        }
    }//GEN-LAST:event_prevButtonActionPerformed

    private void categoryCompanyToggleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryCompanyToggleBtnActionPerformed
        setToggleButtonData();
    }//GEN-LAST:event_categoryCompanyToggleBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private se.backede.jeconomix.forms.category.CategoryChooser categoryChooser1;
    private javax.swing.JToggleButton categoryCompanyToggleBtn;
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

}

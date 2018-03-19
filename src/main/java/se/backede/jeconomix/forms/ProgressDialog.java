/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms;

import com.backede.fileutils.xml.reader.XmlReader;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import se.backede.jeconomix.dto.export.Companies;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.EventObserver;
import se.backede.jeconomix.event.NegodEvent;
import se.backede.jeconomix.event.events.ProgressEvent;
import se.backede.jeconomix.event.events.fields.ProgressEventValues;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
class ProgressDialog extends javax.swing.JDialog implements EventObserver {

    private final XmlReader<Companies> READER = new XmlReader<>();

    public static final int IMPORT = 1;
    public static final int EXPORT = 2;

    public static final String IMPORT_LABEL_TEXT = "Importing progress";
    public static final String EXPORT_LABEL_TEXT = "Exporting progress";

    /**
     * Creates new form ImportProgressbar
     *
     * @param parent
     * @param modal
     */
    public ProgressDialog(java.awt.Frame parent, boolean modal, Integer importExport) {
        super(parent, modal);
        registerAsObserver();
        initComponents();
        
        switch (importExport) {
            case ProgressDialog.IMPORT:
                importLabel.setText(IMPORT_LABEL_TEXT);
                break;
            case ProgressDialog.EXPORT:
                importLabel.setText(EXPORT_LABEL_TEXT);
                break;
            default:
                log.error("Could not determin progressbar type setting to IMPORT as default");
                importLabel.setText(IMPORT_LABEL_TEXT);
        }

        okButton.setEnabled(false);
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
        importLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JToggleButton();
        nameLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        importLabel.setText("Importing in progress..");

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        nameLabel.setText("Item importing...");
        nameLabel.setMaximumSize(new java.awt.Dimension(213, 16));
        nameLabel.setMinimumSize(new java.awt.Dimension(213, 16));
        nameLabel.setPreferredSize(new java.awt.Dimension(213, 16));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(okButton)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(importLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(importLabel)
                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(okButton)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProgressDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProgressDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProgressDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProgressDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ProgressDialog dialog = new ProgressDialog(new javax.swing.JFrame(), true, ProgressDialog.IMPORT);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel importLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JToggleButton okButton;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(NegodEvent event) {
        if (event.equalsEvent(ProgressEvent.SET_MAX_VALUE)) {
            Optional<Integer> integer = event.getValues().get(ProgressEventValues.MAX_VALUE).getInteger();
            if (integer.isPresent()) {
                progressBar.setMaximum(integer.get());
            }
        }
        if (event.equalsEvent(ProgressEvent.INCREASE)) {
            Optional<Integer> integer = event.getValues().get(ProgressEventValues.INCREASE_VALUE).getInteger();
            if (integer.isPresent()) {
                int currentValue = progressBar.getValue();
                progressBar.setValue(currentValue + integer.get());
            }
            Optional<String> itemName = event.getValues().get(ProgressEventValues.ITEM_NAME).getString();
            if (itemName.isPresent()) {
                nameLabel.setText(itemName.get());
            }
        }
        if (event.equalsEvent(ProgressEvent.DONE)) {
            progressBar.setValue(progressBar.getMaximum());
            nameLabel.setText("");
            importLabel.setText("Processing Done!");
            okButton.setEnabled(true);
        }
        if (event.equalsEvent(ProgressEvent.ERROR)) {
            progressBar.setValue(progressBar.getMaximum());
            nameLabel.setText("");
            importLabel.setText("Error when processing data");
            okButton.setEnabled(true);
        }
    }

    @Override
    public void registerAsObserver() {
        EventController.getInstance().addObserver(this);
    }
}

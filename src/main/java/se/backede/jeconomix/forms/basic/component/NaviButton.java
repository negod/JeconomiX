/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms.basic.component;

import java.awt.Color;
import java.time.Month;
import java.util.Optional;
import se.backede.jeconomix.event.EventController;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
public class NaviButton extends javax.swing.JPanel {

    /**
     * Creates new form NaviButton
     */
    public NaviButton() {
        initComponents();
        setBackground(Color.GRAY);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        janBtn = new javax.swing.JButton();
        febBtn = new javax.swing.JButton();
        marBtn = new javax.swing.JButton();
        aprBtn = new javax.swing.JButton();
        mayBtn = new javax.swing.JButton();
        junBtn = new javax.swing.JButton();
        julBtn = new javax.swing.JButton();
        augBtn = new javax.swing.JButton();
        sepBtn = new javax.swing.JButton();
        octBtn = new javax.swing.JButton();
        novBtn = new javax.swing.JButton();
        decBtn = new javax.swing.JButton();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });
        setLayout(new java.awt.GridLayout(12, 1, -1, -1));

        janBtn.setText("Januari");
        janBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                janBtnActionPerformed(evt);
            }
        });
        add(janBtn);

        febBtn.setText("Februari");
        febBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                febBtnActionPerformed(evt);
            }
        });
        add(febBtn);

        marBtn.setText("Mars");
        marBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                marBtnActionPerformed(evt);
            }
        });
        add(marBtn);

        aprBtn.setText("April");
        aprBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aprBtnActionPerformed(evt);
            }
        });
        add(aprBtn);

        mayBtn.setText("Maj");
        mayBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mayBtnActionPerformed(evt);
            }
        });
        add(mayBtn);

        junBtn.setText("Juni");
        junBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                junBtnActionPerformed(evt);
            }
        });
        add(junBtn);

        julBtn.setText("Juli");
        julBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                julBtnActionPerformed(evt);
            }
        });
        add(julBtn);

        augBtn.setText("Augusti");
        augBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                augBtnActionPerformed(evt);
            }
        });
        add(augBtn);

        sepBtn.setText("September");
        sepBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sepBtnActionPerformed(evt);
            }
        });
        add(sepBtn);

        octBtn.setText("Oktober");
        octBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                octBtnActionPerformed(evt);
            }
        });
        add(octBtn);

        novBtn.setText("November");
        novBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                novBtnActionPerformed(evt);
            }
        });
        add(novBtn);

        decBtn.setText("December");
        decBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decBtnActionPerformed(evt);
            }
        });
        add(decBtn);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        setBackground(Color.white);
    }//GEN-LAST:event_formMouseEntered

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        setBackground(Color.GRAY);
    }//GEN-LAST:event_formMouseExited

    private void febBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_febBtnActionPerformed
        EventController.getInstance().notifyObservers(Month.FEBRUARY, () -> Optional.empty());
    }//GEN-LAST:event_febBtnActionPerformed

    private void janBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_janBtnActionPerformed
        EventController.getInstance().notifyObservers(Month.JANUARY, () -> Optional.empty());
    }//GEN-LAST:event_janBtnActionPerformed

    private void marBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_marBtnActionPerformed
        EventController.getInstance().notifyObservers(Month.MARCH, () -> Optional.empty());
    }//GEN-LAST:event_marBtnActionPerformed

    private void aprBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aprBtnActionPerformed
        EventController.getInstance().notifyObservers(Month.APRIL, () -> Optional.empty());
    }//GEN-LAST:event_aprBtnActionPerformed

    private void mayBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mayBtnActionPerformed
        EventController.getInstance().notifyObservers(Month.MAY, () -> Optional.empty());
    }//GEN-LAST:event_mayBtnActionPerformed

    private void junBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_junBtnActionPerformed
        EventController.getInstance().notifyObservers(Month.JUNE, () -> Optional.empty());
    }//GEN-LAST:event_junBtnActionPerformed

    private void julBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_julBtnActionPerformed
        EventController.getInstance().notifyObservers(Month.JULY, () -> Optional.empty());
    }//GEN-LAST:event_julBtnActionPerformed

    private void augBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_augBtnActionPerformed
        EventController.getInstance().notifyObservers(Month.AUGUST, () -> Optional.empty());
    }//GEN-LAST:event_augBtnActionPerformed

    private void sepBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sepBtnActionPerformed
        EventController.getInstance().notifyObservers(Month.SEPTEMBER, () -> Optional.empty());
    }//GEN-LAST:event_sepBtnActionPerformed

    private void octBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_octBtnActionPerformed
        EventController.getInstance().notifyObservers(Month.OCTOBER, () -> Optional.empty());
    }//GEN-LAST:event_octBtnActionPerformed

    private void novBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_novBtnActionPerformed
        EventController.getInstance().notifyObservers(Month.NOVEMBER, () -> Optional.empty());
    }//GEN-LAST:event_novBtnActionPerformed

    private void decBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decBtnActionPerformed
        EventController.getInstance().notifyObservers(Month.DECEMBER, () -> Optional.empty());
    }//GEN-LAST:event_decBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aprBtn;
    private javax.swing.JButton augBtn;
    private javax.swing.JButton decBtn;
    private javax.swing.JButton febBtn;
    private javax.swing.JButton janBtn;
    private javax.swing.JButton julBtn;
    private javax.swing.JButton junBtn;
    private javax.swing.JButton marBtn;
    private javax.swing.JButton mayBtn;
    private javax.swing.JButton novBtn;
    private javax.swing.JButton octBtn;
    private javax.swing.JButton sepBtn;
    // End of variables declaration//GEN-END:variables
}

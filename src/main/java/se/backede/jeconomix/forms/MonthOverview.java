/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.forms;

import java.time.YearMonth;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.jfree.data.time.Month;
import se.backede.jeconomix.event.EventController;
import se.backede.jeconomix.event.events.UiEvent;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class MonthOverview extends javax.swing.JFrame {

    YearMonth CURRENT = YearMonth.of(2018, Month.MARCH);
    YearMonth CURRENT2 = YearMonth.of(2019, Month.MARCH);
    YearMonth CURRENT3 = YearMonth.of(2020, Month.MARCH);

    /**
     * Creates new form Test
     */
    public MonthOverview() {
        initComponents();
        monthWidget2.init(CURRENT);
        monthWidget3.init(CURRENT2);
        monthWidget4.init(CURRENT3);
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
        monthWidget4 = new se.backede.jeconomix.forms.basic.component.MonthWidget();
        monthWidget3 = new se.backede.jeconomix.forms.basic.component.MonthWidget();
        monthWidget2 = new se.backede.jeconomix.forms.basic.component.MonthWidget();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(600, 400));
        setMinimumSize(new java.awt.Dimension(600, 400));
        setPreferredSize(new java.awt.Dimension(600, 400));

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.TRAILING, 2, 2);
        flowLayout1.setAlignOnBaseline(true);
        jPanel1.setLayout(flowLayout1);
        jPanel1.add(monthWidget4);
        jPanel1.add(monthWidget3);
        jPanel1.add(monthWidget2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private se.backede.jeconomix.forms.basic.component.MonthWidget monthWidget2;
    private se.backede.jeconomix.forms.basic.component.MonthWidget monthWidget3;
    private se.backede.jeconomix.forms.basic.component.MonthWidget monthWidget4;
    // End of variables declaration//GEN-END:variables
}

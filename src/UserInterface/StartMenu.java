/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import javafx.application.*;
import Board.UserPreferences;
import Controller.Constants;
import Controller.Controller;
import Board.Factory;
import UserInterface.EventMapping.StartDelegate;
/**
 * The Doki Chess start menu
 * @author Wesley
 */
public class StartMenu extends javax.swing.JFrame {

    private static boolean dokiTheme;
    private StartDelegate startClickHandler = null;
    /**
     * Creates new form StartMenu
     */
    public StartMenu(StartDelegate handler) {
        initComponents();
        
        this.txtPlayer1Name.setText(UserPreferences.getValue(Constants.PLAYER_ONE_KEY, "Sith Rochowiak"));
        this.txtPlayer2Name.setText(UserPreferences.getValue(Constants.PLAYER_TWO_KEY, "Father Coleman"));
        
        this.dokiTheme = false;
        this.rbThemeNormal.setSelected(true);
        this.startClickHandler = handler;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        txtPlayer1Name = new javax.swing.JTextField();
        txtPlayer2Name = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        rbDoki = new javax.swing.JRadioButton();
        rbThemeNormal = new javax.swing.JRadioButton();
        btnStart = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Curlz MT", 2, 36)); // NOI18N
        jLabel1.setText("Doki Doki Chess");

        txtPlayer1Name.setText("Player1Name");

        txtPlayer2Name.setText("Player2Name");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        rbDoki.setText("Doki Doki");
        rbDoki.setToolTipText("");
        rbDoki.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbDokiActionPerformed(evt);
            }
        });

        rbThemeNormal.setText("Normal");
        rbThemeNormal.setToolTipText("");
        rbThemeNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbThemeNormalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(rbThemeNormal, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbDoki, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbDoki)
                    .addComponent(rbThemeNormal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtPlayer1Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtPlayer2Name, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(71, 71, 71))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 193, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPlayer2Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPlayer1Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(btnStart)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        String nameOfPlayerOne = txtPlayer1Name.getText();
        String nameOfPlayerTwo = txtPlayer2Name.getText();
        dokiTheme = rbDoki.isSelected();
        
        UserPreferences.setValue(Constants.PLAYER_TWO_KEY, nameOfPlayerTwo);
        UserPreferences.setValue(Constants.PLAYER_ONE_KEY, nameOfPlayerOne);
        
        UserPreferences.setValue(Constants.THEME_KEY, dokiTheme==true ? "doki" : "normal");
        this.startClickHandler.didStart(Factory.readFENFromFile("starter.fen"));
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnStartActionPerformed

    private void rbThemeNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbThemeNormalActionPerformed
        // TODO add your handling code here:
       if(this.rbDoki.isSelected()){
           this.rbDoki.setSelected(false);
       }
       this.rbThemeNormal.setSelected(true);
    }//GEN-LAST:event_rbThemeNormalActionPerformed

    private void rbDokiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbDokiActionPerformed
        // TODO add your handling code here:
         if(this.rbThemeNormal.isSelected()){
           this.rbThemeNormal.setSelected(false);
       }
       this.rbDoki.setSelected(true);
    }//GEN-LAST:event_rbDokiActionPerformed

    public static boolean getTheme(){
        return dokiTheme;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStart;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton rbDoki;
    private javax.swing.JRadioButton rbThemeNormal;
    private javax.swing.JTextField txtPlayer1Name;
    private javax.swing.JTextField txtPlayer2Name;
    // End of variables declaration//GEN-END:variables
}
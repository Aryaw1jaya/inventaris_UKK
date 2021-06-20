/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventaris_ukk;

import java.awt.Color;
import java.sql.*;
import javax.swing.JOptionPane;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class barang_br extends javax.swing.JFrame {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    static Timer timer = new Timer();
    static int seconds = 0;
    
    String u_ID = userSession.getU_ID();
    String u_Status = userSession.getU_status();
    String u_Level = userSession.getU_level();
    String date;
    public barang_br() {
        initComponents();
        try {
            conn = connection.connectFunction();
            verify();
            //tampil();
        } catch (SQLException ex) {
            Logger.getLogger(barang_br.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void date_syst(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf_comp = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date = dtf_comp.format(now);
    }
    
    //Sistem verifikasi petugas
    private void MyTimer() {
        TimerTask task;
        task = new TimerTask() {
            private final int MAX_SECONDS = 0;

            @Override
            public void run() {
                if (seconds < MAX_SECONDS) {
                    seconds++;
                } else {
                    dispose();
                    new login_adm().setVisible(true);
                    timer.cancel();
                    timer.purge();
                }
            }
        };
        timer.schedule(task, 0, 1000);
    }

    //Sistem verifikasi petugas (lanjutan)
    private void verify() {
        if (!"Admin".equals(u_Status)) {
            MyTimer();
        } else {
            if (u_Level.equals("2")) {
                tampil();
            } else {
                MyTimer();
            }
        }
    }
    
    private void tampil(){
        String statement = "SELECT * FROM inventaris";
        
        try {
            pst = conn.prepareStatement(statement);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                kodeBox.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(barang_br.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void bersih(){
        kodeBox.setSelectedIndex(0);
        brgField.setText("");
        brgField.setEnabled(true);
        merkField.setText("");
        qtySpin.setValue(1);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        kodeBox = new javax.swing.JComboBox<String>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        brgField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        merkField = new javax.swing.JTextField();
        qtySpin = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(51, 51, 255));

        jLabel3.setFont(new java.awt.Font("Nexa Bold", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 255, 255));
        jLabel3.setText("Tambah Barang Baru");

        jButton4.setBackground(new java.awt.Color(102, 255, 255));
        jButton4.setFont(new java.awt.Font("Nexa Light", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(51, 51, 255));
        jButton4.setText("Back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 210, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7))
        );

        kodeBox.setBackground(new java.awt.Color(204, 204, 204));
        kodeBox.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        kodeBox.setForeground(new java.awt.Color(51, 51, 255));
        kodeBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baru" }));
        kodeBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodeBoxActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("Kode Barang");

        jLabel2.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("Nama Barang");

        brgField.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        brgField.setForeground(new java.awt.Color(62, 135, 203));
        brgField.setDisabledTextColor(new java.awt.Color(62, 135, 203));

        jLabel4.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 255));
        jLabel4.setText("Merk Barang");

        merkField.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N

        qtySpin.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        qtySpin.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        jLabel5.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 255));
        jLabel5.setText("Jumlah");

        jButton1.setBackground(new java.awt.Color(153, 255, 255));
        jButton1.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 51, 255));
        jButton1.setText("Tambah");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(kodeBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(brgField, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(qtySpin, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(merkField, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kodeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(brgField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(merkField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5))
                            .addComponent(qtySpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
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

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new inventaris().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void kodeBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodeBoxActionPerformed
        String statement = "SELECT * FROM inventaris WHERE id_barang = ?";
        if (kodeBox.getSelectedItem().equals("Baru")) {
            brgField.setText("");
            brgField.setEnabled(true);
        } else {
            try {
                pst = conn.prepareStatement(statement);
                pst.setString(1, kodeBox.getSelectedItem().toString());
                rs = pst.executeQuery();
            
                if (rs.next()) {
                    brgField.setText(rs.getString(2));
                    brgField.setEnabled(false);
                }
            } catch (SQLException ex) {
                Logger.getLogger(barang_br.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    }//GEN-LAST:event_kodeBoxActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String statement = "SELECT * FROM inventaris WHERE id_barang = ?";
        String sub_state = "INSERT INTO inventaris(id_barang, nama_barang, jumlah) VALUES(?, ?, ?)";
        String sub_sub_state = "INSERT INTO detail_inv(id_detail, id_barang, merk, ketersediaan, keterangan, tgl_masuk) VALUES(?, ?, ?, ?, ?, ?)";
        String statement2 = "UPDATE inventaris SET jumlah = ? WHERE id_barang = ?";
        String statement3 = "SELECT * FROM detail_inv WHERE id_barang = ?";
        date_syst();
        
        if (kodeBox.getSelectedItem().equals("Baru")) {
            String kd = brgField.getText().substring(0, 3).toUpperCase();
            
            try {
                pst = conn.prepareStatement(statement);
                pst.setString(1, kd);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String kdex = brgField.getText().substring(6,9).toUpperCase();
                    String kdcombine = kd.substring(0, 1) + kdex.substring(0, 3);
                    
                    pst = conn.prepareStatement(sub_state);
                    int qty = Integer.parseInt(qtySpin.getValue().toString());
                    pst.setString(1, kdcombine);
                    pst.setString(2, brgField.getText());
                    pst.setInt(3, qty);
                    pst.execute();
                    
                    for (int row = 1; row <= qty; row++) {
                        pst = conn.prepareStatement(sub_sub_state);
                        pst.setInt(1, row);
                        pst.setString(2, kdcombine);
                        pst.setString(3, merkField.getText());
                        pst.setString(4, "1");
                        pst.setString(5, "");
                        pst.setString(6, date);
                        pst.execute();
                    }
                } else {
                    pst = conn.prepareStatement(sub_state);
                    int qty = Integer.parseInt(qtySpin.getValue().toString());
                    pst.setString(1, kd);
                    pst.setString(2, brgField.getText());
                    pst.setInt(3, qty);
                    pst.execute();
                    for (int row = 1; row <= qty; row++) {
                        pst = conn.prepareStatement(sub_sub_state);
                        pst.setInt(1, row);
                        pst.setString(2, kd);
                        pst.setString(3, merkField.getText());
                        pst.setString(4, "1");
                        pst.setString(5, "");
                        pst.setString(6, date);
                        pst.execute();
                    }
                }
                JOptionPane.showMessageDialog(null, "Input Barang Berhasil");
                bersih();
                new inventaris().setVisible(true);
                dispose();
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Input Barang Gagal");
            }
        } else {
            try {
                pst = conn.prepareStatement(statement);
                pst.setString(1, kodeBox.getSelectedItem().toString());
                rs = pst.executeQuery();
                if (rs.next()) {
                    int qty1 = Integer.parseInt(qtySpin.getValue().toString());
                    int qty = rs.getInt(3) + qty1;

                    pst = conn.prepareStatement(statement2);
                    pst.setInt(1, qty);
                    pst.setString(2, kodeBox.getSelectedItem().toString());
                    pst.execute();

                    pst = conn.prepareStatement(statement3);
                    pst.setString(1, kodeBox.getSelectedItem().toString());
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        if (rs.first() == false) {
                            int qty2 = Integer.parseInt(qtySpin.getValue().toString());
                            for (int row = 1; row <= qty2; row++) {
                                pst = conn.prepareStatement(sub_sub_state);
                                pst.setInt(1, row);
                                pst.setString(2, kodeBox.getSelectedItem().toString());
                                pst.setString(3, merkField.getText());
                                pst.setString(4, "1");
                                pst.setString(5, "");
                                pst.setString(6, date);
                                pst.execute();
                            }
                        } else {
                            rs.last();
                            int id_det = rs.getInt(1) + 1;
                            int qty2 = Integer.parseInt(qtySpin.getValue().toString());
                            for (int row = 1; row <= qty2; row++) {
                                
                                pst = conn.prepareStatement(sub_sub_state);
                                pst.setInt(1, id_det);
                                pst.setString(2, kodeBox.getSelectedItem().toString());
                                pst.setString(3, merkField.getText());
                                pst.setString(4, "1");
                                pst.setString(5, "");
                                pst.setString(6, date);
                                pst.execute();
                                id_det++;
                            }
                        }
                    } 
                } else {
                    JOptionPane.showMessageDialog(null, "tidak berhasil");
                }
                JOptionPane.showMessageDialog(null, "Input Barang Berhasil");
                bersih();
                new inventaris().setVisible(true);
                dispose();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Input Barang Gagal");
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(barang_br.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(barang_br.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(barang_br.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(barang_br.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new barang_br().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField brgField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox<String> kodeBox;
    private javax.swing.JTextField merkField;
    private javax.swing.JSpinner qtySpin;
    // End of variables declaration//GEN-END:variables
}

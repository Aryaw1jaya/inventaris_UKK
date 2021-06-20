/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventaris_ukk;

import javax.swing.*;
import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class menu_adm extends javax.swing.JFrame {
       Connection conn = null;
       ResultSet rs = null;
       PreparedStatement pst = null;
       
       static Timer timer = new Timer();
       static int seconds = 0;
       
       String u_ID = userSession.getU_ID();
       String u_password = userSession.getU_password();
       String u_Nama = userSession.getU_Nama();
       String u_alamat = userSession.getU_alamat();
       String u_telp = userSession.getU_telp();
       String u_level = userSession.getU_level();
       String u_Status = userSession.getU_status();
       
    public menu_adm() {
        initComponents();
           try {
               conn = connection.connectFunction();
               verify();
               tampilkan_data();
           } catch (SQLException ex) {
               //Logger.getLogger(menu_adm.class.getName()).log(Level.SEVERE, null, ex);
               JOptionPane.showMessageDialog(null, ex);
               verify();
           }
    }
    private void tampilkan_data(){
        String perintah="SELECT * FROM inventaris";
        
        try {
            pst = conn.prepareStatement(perintah);
            rs = pst.executeQuery();
            DefaultTableModel t_inventaris = new DefaultTableModel();
            t_inventaris.addColumn("Id_barang");
            t_inventaris.addColumn("nama_barang");
            t_inventaris.addColumn("jumlah");
            
            
            while (rs.next()){
                t_inventaris.addRow(new Object []{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3)
                });
            }
            jTable1.setModel(t_inventaris);
            
            //ini dropdown Combobox JENIS,RUANG,PETUGAS
            
            
        } catch (SQLException ex) {
            System.out.println("Data Tidak bisa ditampilkan");
        }
    }
    public void MyTimer(){
        TimerTask task;
        
        task = new TimerTask() {
            
            private final int MAX_SECONDS = 0;
            @Override
            public void run() {
                if (seconds < MAX_SECONDS) {
                    System.out.println(seconds);
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
    
    private void verify(){
        if (u_Status == "Admin") {
            String statement = "SELECT * FROM petugas WHERE id_petugas = '"+ u_ID +"' AND nama_pet = '"+ u_Nama +"' AND alamat = '"+ u_alamat +"' AND no_telp = '"+ u_telp +"' AND level = '"+ u_level +"'";
            
            try {
                pst = conn.prepareStatement(statement);
                rs = pst.executeQuery();
                if (rs.next()) {
                    if (rs.getString(6).equals("1")) {
                        butt_inv.setEnabled(false);
                        user_data.setEnabled(false);
                        butt_kembali.setEnabled(false);
                        adm_name.setText(u_Nama);
                    } if (rs.getString(6).equals("2")) {
                        butt_inv.setEnabled(true);
                        user_data.setEnabled(true);
                        adm_name.setText(u_Nama);
                    }
                } else {
                    new login_adm().setVisible(true);
                    dispose();
                    JOptionPane.showMessageDialog(null, "User tak ditemukan! Kembali ke login..");
                }
            } catch (SQLException ex) {
                //Logger.getLogger(menu_adm.class.getName()).log(Level.SEVERE, null, ex);
                dispose();
                new login_adm().setVisible(true);
            }
            
        } else {
            MyTimer();
            butt_inv.setEnabled(false);
            butt_kembali.setEnabled(false);
            butt_pinjam.setEnabled(false);
        }
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        butt_pinjam = new javax.swing.JButton();
        butt_kembali = new javax.swing.JButton();
        butt_inv = new javax.swing.JButton();
        user_data = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        title_lbl = new javax.swing.JLabel();
        adm_name = new javax.swing.JLabel();
        butt_logout = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        butt_pinjam.setBackground(new java.awt.Color(204, 204, 204));
        butt_pinjam.setFont(new java.awt.Font("Nexa Light", 1, 18)); // NOI18N
        butt_pinjam.setForeground(new java.awt.Color(255, 51, 51));
        butt_pinjam.setText("Barang Keluar");
        butt_pinjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butt_pinjamActionPerformed(evt);
            }
        });

        butt_kembali.setBackground(new java.awt.Color(204, 204, 204));
        butt_kembali.setFont(new java.awt.Font("Nexa Light", 1, 18)); // NOI18N
        butt_kembali.setForeground(new java.awt.Color(255, 51, 51));
        butt_kembali.setText("Barang Kembali");
        butt_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butt_kembaliActionPerformed(evt);
            }
        });

        butt_inv.setBackground(new java.awt.Color(204, 204, 204));
        butt_inv.setFont(new java.awt.Font("Nexa Light", 1, 18)); // NOI18N
        butt_inv.setForeground(new java.awt.Color(255, 51, 51));
        butt_inv.setText("Inventaris");
        butt_inv.setEnabled(false);
        butt_inv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butt_invActionPerformed(evt);
            }
        });

        user_data.setBackground(new java.awt.Color(204, 204, 204));
        user_data.setFont(new java.awt.Font("Nexa Light", 1, 18)); // NOI18N
        user_data.setForeground(new java.awt.Color(255, 51, 51));
        user_data.setText("User Data");
        user_data.setEnabled(false);
        user_data.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                user_dataActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(51, 51, 255));

        title_lbl.setFont(new java.awt.Font("Nexa Bold", 0, 18)); // NOI18N
        title_lbl.setForeground(new java.awt.Color(102, 255, 255));
        title_lbl.setText("Selamat Datang");

        adm_name.setFont(new java.awt.Font("Nexa Bold", 0, 18)); // NOI18N
        adm_name.setForeground(new java.awt.Color(102, 255, 255));
        adm_name.setText("nama-admin");

        butt_logout.setBackground(new java.awt.Color(204, 204, 204));
        butt_logout.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        butt_logout.setForeground(new java.awt.Color(255, 51, 51));
        butt_logout.setText("Logout");
        butt_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butt_logoutActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 255, 255));
        jLabel1.setText("MENU INVENTARIS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(adm_name)
                    .addComponent(title_lbl))
                .addGap(18, 18, 18)
                .addComponent(butt_logout)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(title_lbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adm_name)
                .addGap(26, 26, 26))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(butt_logout))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel3.setBackground(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        jLabel2.setText("Copyright@Arya wijaya");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(butt_inv, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(user_data, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(butt_pinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(butt_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(butt_inv, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(butt_pinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(butt_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(user_data, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butt_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butt_kembaliActionPerformed
        new pengembalian().setVisible(true);
        dispose();
    }//GEN-LAST:event_butt_kembaliActionPerformed

    private void butt_pinjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butt_pinjamActionPerformed
        new peminjaman().setVisible(true);
        dispose();
    }//GEN-LAST:event_butt_pinjamActionPerformed

    private void user_dataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_user_dataActionPerformed
         new userData().setVisible(true);
        dispose();
    }//GEN-LAST:event_user_dataActionPerformed

    private void butt_invActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butt_invActionPerformed
         new inventaris().setVisible(true);
        dispose();
    }//GEN-LAST:event_butt_invActionPerformed

    private void butt_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butt_logoutActionPerformed
            new login_adm().setVisible(true);
        dispose();
    }//GEN-LAST:event_butt_logoutActionPerformed

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
            java.util.logging.Logger.getLogger(menu_adm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menu_adm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menu_adm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menu_adm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menu_adm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel adm_name;
    private javax.swing.JButton butt_inv;
    private javax.swing.JButton butt_kembali;
    private javax.swing.JButton butt_logout;
    private javax.swing.JButton butt_pinjam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel title_lbl;
    private javax.swing.JButton user_data;
    // End of variables declaration//GEN-END:variables
}

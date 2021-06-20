/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventaris_ukk;

import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.util.concurrent.TimeUnit;



public class pengembalian extends javax.swing.JFrame {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    static Timer timer = new Timer();
    static int seconds = 0;

    String u_ID = userSession.getU_ID();
    String u_Status = userSession.getU_status();
    String date;
    String code;
    String code2;
    String kdbarang = null;
    String kdbarang2 = null;
    String kd = null;

    public pengembalian() {
        initComponents();
        try {
            conn = connection.connectFunction();
            //verify();
            tampil();
            returnField.setVisible(false);
            jLabel7.setVisible(false);
            dendaBox.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(pengembalian.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    //Sistem verifikasi petugas 2
    private void verify() {
        if (!"Admin".equals(u_Status)) {
            MyTimer();
        } else {
            tampil();
        }
    }

    //buat auto generate ID Pengembalian dan Tanggal pengembalian
    private void id_format() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("Emmss");
        LocalDateTime now = LocalDateTime.now();
        code = dtf.format(now);
        
        DateTimeFormatter cdtf = DateTimeFormatter.ofPattern("ummss");
        code2 = cdtf.format(now);

        DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date = time.format(now);
        
        
    }

    //Buat tampil data
    private void tampil() {
        String statement = "SELECT * FROM v_pengembalian WHERE Status = '1'";
        try {
            pst = conn.prepareStatement(statement);
            rs = pst.executeQuery();

            DefaultTableModel list = new DefaultTableModel();
            list.addColumn("ID");
            list.addColumn("ID Peminjam");
            list.addColumn("ID Barang");
            list.addColumn("ID Detail");
            list.addColumn("Peminjam");
            list.addColumn("Nama Barang");
            list.addColumn("Ruangan");
            list.addColumn("Tanggal Peminjaman");
            list.addColumn("Tanggal Kembali");
            

            while (rs.next()) {
                list.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(7),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(9),
                    rs.getString(10),
                });

            }
            borrowList.setModel(list);
            borrowList.removeColumn(borrowList.getColumnModel().getColumn(1));
            borrowList.removeColumn(borrowList.getColumnModel().getColumn(1));
            borrowList.removeColumn(borrowList.getColumnModel().getColumn(1));
            borrowList.removeColumn(borrowList.getColumnModel().getColumn(1));
        } catch (SQLException ex) {
            Logger.getLogger(pengembalian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void bersih() {
        //searchField.setText("");
        userField1.setText("");
        barangField.setText("");
        borrowField.setText("");
        returnField.setText("");
    }
    
    //Fungsi Mengembalikan Barang tidak ada denda
    private void kembali(){
        String statement = "INSERT INTO pengembalian(id_pengembalian, id_peminjaman, id_barang, id_detail, tgl_kembali, id_petugas) VALUES(?,?,?,?,?,?)";
        String sub_state = "UPDATE det_peminjaman SET Status = '0' WHERE id_detail = ? AND id_peminjaman = ? AND id_barang = ?";
        String sub_sub_state = "UPDATE detail_inv SET ketersediaan = '1' WHERE id_detail = ? AND id_barang = ?";
        id_format();
        try {
            pst = conn.prepareStatement(statement);
            pst.setString(1, code);
            pst.setString(2, kd);
            pst.setString(3, kdbarang);
            pst.setString(4, kdbarang2);
            pst.setString(5, date);
            pst.setString(6, u_ID);
            pst.execute();
            
            pst = conn.prepareStatement(sub_state);
            pst.setString(1, kdbarang2);
            pst.setString(2, kd);
            pst.setString(3, kdbarang);
            pst.executeUpdate();
            
            pst = conn.prepareStatement(sub_sub_state);
            pst.setString(1, kdbarang2);
            pst.setString(2, kdbarang);
            pst.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Barang sudah dikembalikan!");
            tampil();
            bersih();
            
        } catch (SQLException ex) {
            Logger.getLogger(pengembalian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Fungsi Mengembalikan Barang ada Denda
    private void kembaliDenda(){
         String statement = "INSERT INTO pengembalian(id_pengembalian, id_peminjaman, id_barang, id_detail, tgl_kembali, id_petugas) VALUES(?,?,?,?,?,?)";
         String sub_state = "UPDATE det_peminjaman SET Status = '2' WHERE id_detail = ? AND id_peminjaman = ? AND id_barang = ?";
         String sub_sub_state = "INSERT INTO denda(id_denda, id_pengembalian, jenis, denda, status) VALUES(?,?,?,?,?)";
         //String nigga = "UPDATE detail_inv SET keterangan = ? WHERE id_detail = ?";
         id_format();
        try {
            //Menginput data pengembalian
            pst = conn.prepareStatement(statement);
            pst.setString(1, code);
            pst.setString(2, kd);
            pst.setString(3, kdbarang);
            pst.setString(4, kdbarang2);
            pst.setString(5, date);
            pst.setString(6, u_ID);
            pst.execute();
            
            //Mengubah status detail peminjaman menjadi 2, yaitu proses denda
            pst = conn.prepareStatement(sub_state);
            pst.setString(1, kdbarang2);
            pst.setString(2, kd);
            pst.setString(3, kdbarang);
            pst.executeUpdate();
            
            //Penentuan denda
            //Panduan status: 1 denda terlambat
            //                2 denda rusak
            pst = conn.prepareStatement(sub_sub_state);
            pst.setString(1, code2);
            pst.setString(2, code);
            SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
            Date nowdate = time.parse(date);
            Date returndate = time.parse(returnField.getText());
            if (returndate.compareTo(nowdate) < 0) {
                long datecount = nowdate.getTime() - returndate.getTime();
                int denda = (int) (TimeUnit.DAYS.convert(datecount, TimeUnit.MILLISECONDS) * 50000);
                pst.setString(3, "1");
                pst.setInt(4, denda);
                pst.setString(5, "1");
            } else {
                pst.setString(3, "2");
                pst.setInt(4, 0);
                pst.setString(5, "1");
            }
            pst.execute();
            JOptionPane.showMessageDialog(null, "Barang berhasil dikembalikan");
            tampil();
            
        } catch (SQLException ex) {
            Logger.getLogger(pengembalian.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(pengembalian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        borrowList = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        barangField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        userField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        borrowField = new javax.swing.JTextField();
        returnField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        dendaBox = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(51, 51, 255));

        jLabel1.setFont(new java.awt.Font("Nexa Bold", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 255, 255));
        jLabel1.setText("(Barang Masuk) Pengembalian Barang");

        jButton2.setBackground(new java.awt.Color(102, 255, 255));
        jButton2.setFont(new java.awt.Font("Nexa Light", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 51, 255));
        jButton2.setText("Kembali");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(31, 31, 31))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        borrowList.setModel(new javax.swing.table.DefaultTableModel(
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
        borrowList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                borrowListMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(borrowList);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 255));
        jLabel4.setText("Peminjam");

        barangField.setEditable(false);
        barangField.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        barangField.setDisabledTextColor(new java.awt.Color(62, 135, 203));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 255));
        jLabel5.setText("Nama Barang");

        userField1.setEditable(false);
        userField1.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        userField1.setDisabledTextColor(new java.awt.Color(62, 135, 203));
        userField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userField1ActionPerformed(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 255));
        jLabel6.setText("Tanggal Pinjam");

        borrowField.setEditable(false);
        borrowField.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        borrowField.setDisabledTextColor(new java.awt.Color(62, 135, 203));

        returnField.setEditable(false);
        returnField.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        returnField.setDisabledTextColor(new java.awt.Color(62, 135, 203));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 255));
        jLabel7.setText("Tanggal Kembali");

        dendaBox.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        dendaBox.setForeground(new java.awt.Color(0, 204, 153));
        dendaBox.setText("Denda");

        jButton3.setBackground(new java.awt.Color(102, 255, 255));
        jButton3.setFont(new java.awt.Font("Nexa Light", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(51, 51, 255));
        jButton3.setText("Kembalikan");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("Barang Yang Dipinjam");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(returnField, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                    .addComponent(userField1)
                    .addComponent(barangField)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(dendaBox)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(borrowField))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(270, 270, 270)
                                .addComponent(jLabel2))
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(userField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(barangField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(borrowField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(returnField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dendaBox)))
                .addContainerGap(18, Short.MAX_VALUE))
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new menu_adm().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (dendaBox.isSelected()) {
            kembaliDenda();
        } else {
            kembali();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void borrowListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_borrowListMouseClicked
        int row = borrowList.rowAtPoint(evt.getPoint());
        
        String kdnama = borrowList.getModel().getValueAt(row, 1).toString();
        kdbarang = borrowList.getModel().getValueAt(row, 2).toString();
        kdbarang2 = borrowList.getModel().getValueAt(row, 3).toString();
        String status = borrowList.getModel().getValueAt(row, 4).toString();
        
        String nmbarang = borrowList.getValueAt(row, 1).toString();
        String tglpin = borrowList.getValueAt(row, 3).toString();
        String tglkem = borrowList.getValueAt(row, 4).toString();
        kd = borrowList.getValueAt(row, 0).toString();
        String det;
        if ("Petugas".equals(status)) {
                det = "id_petugas";
            } else {
               det = "id_peminjam";
            }
        
        String statement = "SELECT * FROM "+ status +" WHERE "+ det +" = ?";
        
        try {
            pst = conn.prepareStatement(statement);
            pst.setString(1, kdnama);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                if ("Petugas".equals(status)) {
                userField1.setText(rs.getString("nama_pet"));
                } else {
                userField1.setText(rs.getString("nama_pem"));
                }
            }
            
            barangField.setText(nmbarang);
            borrowField.setText(tglpin);
            returnField.setText(tglkem);
            SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
            id_format();
            Date nowdate = time.parse(date);
            Date returndate = time.parse(tglkem);
            if (returndate.compareTo(nowdate) < 0) {
                dendaBox.setSelected(true);
                dendaBox.setEnabled(false);
            } else {
                dendaBox.setSelected(false);
                dendaBox.setEnabled(true);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(pengembalian.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(pengembalian.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_borrowListMouseClicked

    private void userField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userField1ActionPerformed

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
            java.util.logging.Logger.getLogger(pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pengembalian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField barangField;
    private javax.swing.JTextField borrowField;
    private javax.swing.JTable borrowList;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JCheckBox dendaBox;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField returnField;
    private javax.swing.JTextField userField1;
    // End of variables declaration//GEN-END:variables
}

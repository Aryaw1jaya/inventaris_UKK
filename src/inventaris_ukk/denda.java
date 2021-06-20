/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inventaris_ukk;

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


public class denda extends javax.swing.JFrame {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    static Timer timer = new Timer();
    static int seconds = 0;

    String u_ID = userSession.getU_ID();
    String u_Status = userSession.getU_status();
    String date;
    String code;
    String kd;
    String kdbarang;
    String kdbarang2;
    int latefee = 0;
    
    public denda() {
        initComponents();
        try {
            conn = connection.connectFunction();
            verify();
            //tampil();
        } catch (SQLException ex) {
            Logger.getLogger(denda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void codeForm(){
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
            tampil();
        }
    }
    
    private void tampil(){
        String statement = "SELECT * FROM v_denda WHERE status = 1";
        
        try {
            pst = conn.prepareStatement(statement);
            rs = pst.executeQuery();
            
            DefaultTableModel list = new DefaultTableModel();
            list.addColumn("ID");
            list.addColumn("ID Barang");
            list.addColumn("ID Detail");
            list.addColumn("Nama Barang");
            list.addColumn("Denda");
            list.addColumn("Jenis");
            
            while (rs.next()) {
                list.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(7),
                    rs.getString(5),
                });
            }
            dendaList.setModel(list);
            dendaList.removeColumn(dendaList.getColumnModel().getColumn(0));
            dendaList.removeColumn(dendaList.getColumnModel().getColumn(0));
            dendaList.removeColumn(dendaList.getColumnModel().getColumn(0));
            dendaList.removeColumn(dendaList.getColumnModel().getColumn(2));
            addButt.setEnabled(false);
            
        } catch (SQLException ex) {
            Logger.getLogger(denda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void bayar(){
        String statement = "UPDATE denda SET keterangan = ?, jenis = ?, denda = ?, tgl_bayar = ?, id_petugas = ?, status = '0'";
        codeForm();
        try {
            pst = conn.prepareStatement(statement);
            pst.setString(1, ketArea.getText());
            
            
            if (lateBox.isSelected()) {
                pst.setInt(2, 1);
                pst.setInt(3, Integer.parseInt(dendaField.getText()));
            } else if (brokeBox.isSelected()){
                pst.setInt(2, 2);
                pst.setInt(3, Integer.parseInt(dendaField.getText()));
            } else if (goneBox.isSelected()){
                pst.setInt(2, 3);
                pst.setInt(3, Integer.parseInt(dendaField.getText()));
            } else if (lateBox.isSelected() && brokeBox.isSelected()){
                pst.setInt(2, 4);
                int totalfee = latefee + Integer.parseInt(dendaField.getText());
                pst.setInt(3, totalfee);
            }
            
            pst.setString(4, date);
            pst.setString(5, u_ID);
            
            int option = JOptionPane.YES_NO_OPTION;
            if (lateBox.isSelected() && brokeBox.isSelected()) {
                int count = latefee + Integer.parseInt(dendaField.getText());
                JOptionPane.showConfirmDialog(null, "Total anda adalah" +"\n"+ "Denda Terlambat: "+latefee +"\n"+ "Denda Rusak: " +"\n"+ dendaField.getText() +"\n" + "Total: " + count, "Validasi", option);
                if (option == 1) {
                    pst.execute();
                    kondisiUp();
                }
            } else if (lateBox.isSelected()) {
                JOptionPane.showConfirmDialog(null, "Total anda adalah" +"\n"+ "Denda Terlambat: " +dendaField.getText(), "Validasi", option);
                if (option == 1) {
                    pst.execute();
                    kondisiUp();
                }
            } else if (brokeBox.isSelected()){
                JOptionPane.showConfirmDialog(null, "Total anda adalah" +"\n"+ "Denda Rusak: " +dendaField.getText(), "Validasi", option);
                if (option == 1) {
                    pst.execute();
                    kondisiUp();
                }
            } else if (goneBox.isSelected()) {
                JOptionPane.showConfirmDialog(null, "Total anda adalah" +"\n"+ "Denda Barang Hilang: " +dendaField.getText(), "Validasi", option);
                if (option == 1) {
                    pst.execute();
                    kondisiUp();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(denda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void kondisiUp(){
        String statement = "UPDATE det_peminjaman SET status = '0' WHERE id_barang = ? AND id_detail = ? AND id_peminjaman = ?";
        String sub_state = "UPDATE detail_inv SET ketersediaan = '1', keterangan = ? WHERE id_barang = ? AND id_detail = ?";
        
        try {
            pst = conn.prepareStatement(statement);
            pst.setString(1, kdbarang);
            pst.setString(2, kdbarang2);
            pst.setString(3, kd);
            pst.execute();
            
            pst = conn.prepareStatement(sub_state);
            pst.setString(1, ketArea.getText());
            pst.setString(2, kdbarang);
            pst.setString(3, kdbarang2);
            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(denda.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     private void id_format() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("Emmss");
        LocalDateTime now = LocalDateTime.now();
        code = dtf.format(now);
        
        DateTimeFormatter cdtf = DateTimeFormatter.ofPattern("ummss");
        code = cdtf.format(now);

        DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date = time.format(now);
        
        
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dendaList = new javax.swing.JTable();
        barangField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dendaField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        ketArea = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        lateBox = new javax.swing.JCheckBox();
        brokeBox = new javax.swing.JCheckBox();
        goneBox = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        payButt = new javax.swing.JButton();
        addButt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(0, 102, 102));

        jButton4.setBackground(new java.awt.Color(0, 153, 153));
        jButton4.setFont(new java.awt.Font("Nexa Light", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Back To Menu");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Nexa Bold", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Denda");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(119, 119, 119)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        dendaList.setModel(new javax.swing.table.DefaultTableModel(
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
        dendaList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dendaListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(dendaList);

        barangField.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        barangField.setForeground(new java.awt.Color(62, 135, 203));

        jLabel4.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Barang");

        jLabel5.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 102));
        jLabel5.setText("Denda");

        dendaField.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        dendaField.setForeground(new java.awt.Color(62, 135, 203));

        ketArea.setColumns(20);
        ketArea.setFont(new java.awt.Font("Nexa Light", 0, 14)); // NOI18N
        ketArea.setRows(5);
        jScrollPane2.setViewportView(ketArea);

        jLabel6.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("Keterangan");

        lateBox.setText("Terlambat");
        lateBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lateBoxActionPerformed(evt);
            }
        });

        brokeBox.setText("Rusak");
        brokeBox.setToolTipText("");
        brokeBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brokeBoxActionPerformed(evt);
            }
        });

        goneBox.setText("Hilang");
        goneBox.setToolTipText("");
        goneBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goneBoxActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText("Rp");

        payButt.setBackground(new java.awt.Color(0, 102, 102));
        payButt.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        payButt.setForeground(new java.awt.Color(255, 255, 255));
        payButt.setText("Bayar");
        payButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payButtActionPerformed(evt);
            }
        });

        addButt.setBackground(new java.awt.Color(0, 102, 102));
        addButt.setFont(new java.awt.Font("Nexa Light", 0, 12)); // NOI18N
        addButt.setForeground(new java.awt.Color(255, 255, 255));
        addButt.setText("Tambah");
        addButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(barangField, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(dendaField)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(addButt)
                                                .addGap(0, 0, Short.MAX_VALUE)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lateBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(brokeBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(goneBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(239, 239, 239)
                        .addComponent(payButt)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lateBox)
                                .addGap(18, 18, 18)
                                .addComponent(brokeBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(goneBox))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(barangField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(dendaField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(payButt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addButt)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        new menu_adm().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void payButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payButtActionPerformed
        if (!lateBox.isSelected() && !brokeBox.isSelected() && !goneBox.isSelected()) {
            JOptionPane.showMessageDialog(null, "Alasan denda masih belum ada!" + "\n" + "Pilih checkbox disamping keterangan untuk mengisi");
        } else if (ketArea.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Keterangan denda belum ada!");
        } else if (lateBox.isSelected() && brokeBox.isSelected()) {
            if (latefee == 0) {
                JOptionPane.showMessageDialog(null, "Denda kerusakan masih belum ada!"+"\n"+" Klik tombol 'tambah' dibawah denda untuk menambahkan");
            } else {
                bayar();
                tampil();
            }
        } else if (dendaField.getText().equals("0") || dendaField.getText().equals("") || Integer.parseInt(dendaField.getText()) < 0) {
            JOptionPane.showMessageDialog(null, "Denda harus ada!");
        } else {
            bayar();
            tampil();
           
        }
    }//GEN-LAST:event_payButtActionPerformed

    private void dendaListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dendaListMouseClicked
        int row = dendaList.rowAtPoint(evt.getPoint());
        
        kd = dendaList.getModel().getValueAt(row, 0).toString();
        kdbarang = dendaList.getModel().getValueAt(row, 1).toString();
        kdbarang2 = dendaList.getModel().getValueAt(row, 2).toString();
        String jenis = dendaList.getModel().getValueAt(row, 5).toString();
        
        String nmbarang = dendaList.getValueAt(row, 0).toString();
        String denda = dendaList.getValueAt(row, 1).toString();
        
        barangField.setText(nmbarang);
        dendaField.setText(denda);
        
        
        //Penentuan denda
        //Panduan status: 1 denda terlambat
        //                2 denda rusak
        if (jenis.equals("1")) {
            lateBox.setSelected(true);
            lateBox.setEnabled(false);
            brokeBox.setSelected(false);
            brokeBox.setEnabled(true);
            goneBox.setEnabled(false);
            addButt.setEnabled(false);
        } else {
            lateBox.setSelected(false);
            lateBox.setEnabled(false);
            brokeBox.setSelected(true);
            goneBox.setEnabled(true);
            addButt.setEnabled(false);
        }
        
    }//GEN-LAST:event_dendaListMouseClicked

    private void addButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtActionPerformed
        latefee = Integer.parseInt(dendaField.getText());
        dendaField.setText("");
        addButt.setEnabled(false);
    }//GEN-LAST:event_addButtActionPerformed

    private void brokeBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brokeBoxActionPerformed
        if (lateBox.isSelected()) {
            addButt.setEnabled(true);
        }
        if (!brokeBox.isSelected()) {
            addButt.setEnabled(false);
        }
        if (!brokeBox.isSelected() && !lateBox.isSelected() && !goneBox.isSelected()) {
            JOptionPane.showMessageDialog(null, "Alasan denda tidak boleh kosong!");
            brokeBox.setSelected(true);
        }
    }//GEN-LAST:event_brokeBoxActionPerformed

    private void lateBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lateBoxActionPerformed
        
    }//GEN-LAST:event_lateBoxActionPerformed

    private void goneBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goneBoxActionPerformed
        if (goneBox.isSelected()) {
            lateBox.setEnabled(false);
            lateBox.setSelected(false);
            brokeBox.setEnabled(false);
            brokeBox.setSelected(false);
        } else if (!goneBox.isSelected()) {
            lateBox.setEnabled(false);
            lateBox.setSelected(false);
            brokeBox.setEnabled(true);
            brokeBox.setSelected(true);
        }
    }//GEN-LAST:event_goneBoxActionPerformed

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
            java.util.logging.Logger.getLogger(denda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(denda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(denda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(denda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new denda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButt;
    private javax.swing.JTextField barangField;
    private javax.swing.JCheckBox brokeBox;
    private javax.swing.JTextField dendaField;
    private javax.swing.JTable dendaList;
    private javax.swing.JCheckBox goneBox;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea ketArea;
    private javax.swing.JCheckBox lateBox;
    private javax.swing.JButton payButt;
    // End of variables declaration//GEN-END:variables

}

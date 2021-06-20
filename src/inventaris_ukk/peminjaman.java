/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventaris_ukk;

import javax.swing.*;
import java.sql.*;
import java.text.DateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime; 
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

public class peminjaman extends javax.swing.JFrame {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    static Timer timer = new Timer();
    static int seconds = 0;
    
    String code;
    String date_comp;
    String borrow_date;
    String u_ID = userSession.getU_ID();
    String u_Status = userSession.getU_status();
    public peminjaman() {
        initComponents();
        try {
            conn = connection.connectFunction();
            verify();
        } catch (SQLException ex) {
            Logger.getLogger(peminjaman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //verifikasi user system
    public void MyTimer(){
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
    //verifikasi user system 2
    private void verify(){
        if (u_Status == null) {
            MyTimer();
        } else {
            show_data();
        }
    }
    
    private void date_syst(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddmmss");
        code = dtf.format(now);
        
        DateTimeFormatter dtf_comp = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date_comp = dtf_comp.format(now);
        
        SimpleDateFormat date_tgl = new SimpleDateFormat("yyyy-MM-dd");
        borrow_date = date_tgl.format(tgl_kembali.getDate());
        
        
    }
    
    private void insert_data(){
    String statement = "INSERT INTO peminjaman(id_peminjaman, tgl_pinjam, tgl_kembali, id_petugas, id_peminjam, ruangan) VALUES(?, ?, ?, ?, ?, ?) ";
    String sub_state = "INSERT INTO det_peminjaman(id_barang, id_detail, id_peminjaman, Status) VALUES(?, ?, ?, ?)";
    String sub_sub_state = "UPDATE detail_inv SET ketersediaan = '0' WHERE id_detail = ? AND ID_Barang = ?";
    date_syst();
        try {
            pst = conn.prepareStatement(statement);
            pst.setString(1, code);
            pst.setString(2, date_comp);
            pst.setString(3, borrow_date);
            
            switch(u_Status){
                case "Admin":
                    pst.setString(4, u_ID);
                    pst.setString(5, null);
                    break;
                case "Peminjam":
                    pst.setString(4, null);
                    pst.setString(5, u_ID);
                    break;
                default:
                    pst.setString(4, null);
                    pst.setString(5, null);
            }
            
            pst.setString(6, lab_ruang.getText());
            pst.executeUpdate();
            
            for (int row = 0; row < tb_listpinjam.getRowCount(); row++) {
                pst = conn.prepareStatement(sub_state);
                String ID_Barang = tb_listpinjam.getValueAt(row, 0).toString();
                String ID = tb_listpinjam.getValueAt(row, 1).toString();
                String ID_pem = code;
                pst.setString(1, ID_Barang);
                pst.setString(2, ID);
                pst.setString(3, ID_pem);
                pst.setString(4, "1");
                pst.execute();
                
                pst = conn.prepareStatement(sub_sub_state);
                pst.setString(1, ID);
                pst.setString(2, ID_Barang);
                pst.executeUpdate();
            }
            JOptionPane.showMessageDialog(null, "Pinjaman Tercatat!");
            bersih();
            show_data();
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(peminjaman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void show_data(){
        String statement = "SELECT * FROM v_inventaris";
        System.out.println(u_Status);
        System.out.println(u_ID);
        try {
            pst = conn.prepareStatement(statement);
            rs = pst.executeQuery();
            DefaultTableModel listbarang = new DefaultTableModel();
            listbarang.addColumn("ID Barang");
            listbarang.addColumn("ID Detail");
            listbarang.addColumn("Nama Barang");
            listbarang.addColumn("Merek");
            listbarang.addColumn("Keterangan");
            
            while (rs.next()) {
                if (!rs.getString(5).equals("0")) {
                    listbarang.addRow(new Object[]{
                   rs.getString(1),
                   rs.getString(2),
                   rs.getString(3),
                   rs.getString(4),
                   rs.getString(6),
                });
                }
            }
            tb_listbarang.setModel(listbarang);
            
        } catch (SQLException ex) {
            Logger.getLogger(peminjaman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void add_row(){
        int row = tb_listbarang.getSelectedRow();
        
        try {
        String ID_Barang = tb_listbarang.getValueAt(row, 0).toString();
        String ID = tb_listbarang.getValueAt(row, 1).toString();
        String Nama = tb_listbarang.getValueAt(row, 2).toString();
        String Merek = tb_listbarang.getValueAt(row, 3).toString();
        String Keterangan = tb_listbarang.getValueAt(row, 4).toString();
        
        DefaultTableModel listpinjam = (DefaultTableModel)tb_listpinjam.getModel();
        DefaultTableModel listbarang = (DefaultTableModel)tb_listbarang.getModel();
        
        listbarang.removeRow(row);
        
        listpinjam.addRow(new Object[]{
            ID_Barang,
            ID,
            Nama,
            Merek,
            Keterangan,
        });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Klik salah satu kolom di kiri!");
        }
        
    }
    
    private void remove_row(){
        int row = tb_listpinjam.getSelectedRow();
        
        try {
            String ID_Barang = tb_listpinjam.getModel().getValueAt(row, 0).toString();
            String ID = tb_listpinjam.getValueAt(row, 0).toString();
            String Nama = tb_listpinjam.getValueAt(row, 1).toString();
            String Merek = tb_listpinjam.getValueAt(row, 2).toString();
            String Keterangan = tb_listpinjam.getValueAt(row, 3).toString();
        
            DefaultTableModel listbarang = (DefaultTableModel)tb_listbarang.getModel();
            DefaultTableModel listpinjam = (DefaultTableModel)tb_listpinjam.getModel();
        
            listpinjam.removeRow(row);
        
            listbarang.addRow(new Object[]{
                ID_Barang,
                ID,
                Nama,
                Merek,
                Keterangan,
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Klik salah satu kolom di kanan!");
        }
    }
    
    private void bersih(){
        lab_namaBar.setText("");
        lab_merk.setText("");
        lab_ket.setText("");
        
        DefaultTableModel pinjam = (DefaultTableModel)tb_listpinjam.getModel();
        pinjam.setRowCount(0);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        butt_back = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_listbarang = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        butt_tambah = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_listpinjam = new javax.swing.JTable();
        butt_kurang = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        lab_ruang = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        lab_namaBar = new javax.swing.JTextField();
        lab_ket = new javax.swing.JTextField();
        lab_merk = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tgl_kembali = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(51, 51, 255));

        jLabel1.setFont(new java.awt.Font("Nexa Bold", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 255, 255));
        jLabel1.setText("(Barang Keluar) Peminjaman Barang");

        butt_back.setBackground(new java.awt.Color(102, 255, 255));
        butt_back.setFont(new java.awt.Font("Nexa Light", 0, 14)); // NOI18N
        butt_back.setForeground(new java.awt.Color(51, 102, 255));
        butt_back.setText("Kembali");
        butt_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butt_backActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(butt_back)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(25, 25, 25))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addComponent(butt_back, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tb_listbarang.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_listbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_listbarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_listbarang);

        jLabel2.setFont(new java.awt.Font("Nexa Bold", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("Nama Barang:");

        jLabel5.setFont(new java.awt.Font("Nexa Bold", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 255));
        jLabel5.setText("Merk:");

        jLabel6.setFont(new java.awt.Font("Nexa Bold", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 255));
        jLabel6.setText("Tanggal Pengembalian");

        jLabel9.setFont(new java.awt.Font("Nexa Bold", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 255));
        jLabel9.setText("Keterangan");

        butt_tambah.setBackground(new java.awt.Color(102, 255, 255));
        butt_tambah.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        butt_tambah.setForeground(new java.awt.Color(51, 51, 255));
        butt_tambah.setText("Tambah");
        butt_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butt_tambahActionPerformed(evt);
            }
        });

        tb_listpinjam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Barang", "ID Detail", "Nama Barang", "Merek", "Keterangan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tb_listpinjam);

        butt_kurang.setBackground(new java.awt.Color(102, 255, 255));
        butt_kurang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        butt_kurang.setForeground(new java.awt.Color(51, 51, 255));
        butt_kurang.setText("Kurangi");
        butt_kurang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butt_kurangActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Nexa Bold", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 255));
        jLabel10.setText("Ruangan");

        lab_ruang.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        lab_ruang.setForeground(new java.awt.Color(51, 51, 255));

        jButton1.setBackground(new java.awt.Color(102, 255, 255));
        jButton1.setFont(new java.awt.Font("Nexa Light", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 51, 255));
        jButton1.setText("Pinjam");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lab_namaBar.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        lab_namaBar.setText("pilih");
        lab_namaBar.setBorder(null);
        lab_namaBar.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lab_namaBar.setEnabled(false);
        lab_namaBar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lab_namaBarFocusGained(evt);
            }
        });
        lab_namaBar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lab_namaBarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lab_namaBarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lab_namaBarKeyTyped(evt);
            }
        });

        lab_ket.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        lab_ket.setText("pilih");
        lab_ket.setBorder(null);
        lab_ket.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lab_ket.setEnabled(false);
        lab_ket.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lab_ketFocusGained(evt);
            }
        });

        lab_merk.setFont(new java.awt.Font("Nexa Light", 0, 18)); // NOI18N
        lab_merk.setText("pilih");
        lab_merk.setBorder(null);
        lab_merk.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lab_merk.setEnabled(false);
        lab_merk.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lab_merkFocusGained(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Nexa Bold", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 255));
        jLabel3.setText("Daftar Barang");

        jLabel4.setFont(new java.awt.Font("Nexa Bold", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 255));
        jLabel4.setText("Daftar Pinjam");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10)
                    .addComponent(lab_ket, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(lab_namaBar)
                    .addComponent(lab_merk)
                    .addComponent(lab_ruang)
                    .addComponent(tgl_kembali, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(326, 326, 326))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(butt_kurang, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(butt_tambah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(319, 319, 319))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lab_namaBar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lab_ket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lab_merk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lab_ruang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(tgl_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(167, 167, 167))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addComponent(butt_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(butt_kurang, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 535, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lab_merkFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lab_merkFocusGained
        bersih();
    }//GEN-LAST:event_lab_merkFocusGained

    private void lab_ketFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lab_ketFocusGained
        bersih();
    }//GEN-LAST:event_lab_ketFocusGained

    private void lab_namaBarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lab_namaBarKeyTyped

    }//GEN-LAST:event_lab_namaBarKeyTyped

    private void lab_namaBarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lab_namaBarKeyReleased
        String statement = "SELECT * FROM v_inventaris WHERE ketersediaan = 1";

        try {
            pst = conn.prepareStatement(statement);
            rs = pst.executeQuery();
            DefaultTableModel listbarang = new DefaultTableModel();
            listbarang.addColumn("ID Barang");
            listbarang.addColumn("Nama Barang");
            listbarang.addColumn("Merek");
            listbarang.addColumn("Kondisi");
            listbarang.addColumn("Keterangan");

            while (rs.next()) {
                if (rs.getString(5).equals("Dipinjam")) {

                } else {
                    listbarang.addRow(new Object[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                    });
                }
            }
            tb_listbarang.setModel(listbarang);
        } catch (SQLException ex) {
            Logger.getLogger(peminjaman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lab_namaBarKeyReleased

    private void lab_namaBarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lab_namaBarKeyPressed

    }//GEN-LAST:event_lab_namaBarKeyPressed

    private void lab_namaBarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lab_namaBarFocusGained
        bersih();
    }//GEN-LAST:event_lab_namaBarFocusGained

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (tb_listpinjam.getModel().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Anda belum meminjam barang!");
        } else {
            if (lab_ruang.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ruangan wajib diisi!");
            } else {
                insert_data();

            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void butt_kurangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butt_kurangActionPerformed
        remove_row();
    }//GEN-LAST:event_butt_kurangActionPerformed

    private void butt_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butt_tambahActionPerformed
        add_row();
    }//GEN-LAST:event_butt_tambahActionPerformed

    private void tb_listbarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_listbarangMouseClicked
        int row = tb_listbarang.rowAtPoint(evt.getPoint());

        String nama = tb_listbarang.getValueAt(row, 1).toString();
        String merk = tb_listbarang.getValueAt(row, 2).toString();
        String ket = tb_listbarang.getValueAt(row, 3).toString();

        lab_namaBar.setText(String.valueOf(nama));
        lab_merk.setText(String.valueOf(merk));
        lab_ket.setText(String.valueOf(ket));
    }//GEN-LAST:event_tb_listbarangMouseClicked

    private void butt_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butt_backActionPerformed
        new menu_adm().setVisible(true);
        dispose();
    }//GEN-LAST:event_butt_backActionPerformed

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
            java.util.logging.Logger.getLogger(peminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(peminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(peminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(peminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new peminjaman().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butt_back;
    private javax.swing.JButton butt_kurang;
    private javax.swing.JButton butt_tambah;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField lab_ket;
    private javax.swing.JTextField lab_merk;
    private javax.swing.JTextField lab_namaBar;
    private javax.swing.JTextField lab_ruang;
    private javax.swing.JTable tb_listbarang;
    private javax.swing.JTable tb_listpinjam;
    private com.toedter.calendar.JDateChooser tgl_kembali;
    // End of variables declaration//GEN-END:variables
}

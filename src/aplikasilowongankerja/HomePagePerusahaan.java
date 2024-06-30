/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aplikasilowongankerja;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Objects;

/**
 *
 * @author Dektu
 */
public class HomePagePerusahaan extends javax.swing.JFrame {
    String username;
    String nama;
    int idPerusahaan;
    int jumlahLowongan, nextjumlahLowongan;
    int page;
    int row, nextrow;

    int[] id_lowongan = new int[4];
    String lowongan1Title;
    String lowongan1Desc;
    String lowongan1Date;
    String lowongan1Status;
    String lowongan2Title;
    String lowongan2Desc;
    String lowongan2Date;
    String lowongan2Status;
    String lowongan3Title;
    String lowongan3Desc;
    String lowongan3Date;
    String lowongan3Status;
    String lowongan4Title;
    String lowongan4Desc;
    String lowongan4Date;
    String lowongan4Status;

    /**
     * Creates new form HomePgePerusahaan
     */
    public HomePagePerusahaan(String usernameParam, int pageParam) {
        username = usernameParam;
        page = pageParam;
        row = (page - 1) * 4;
        nextrow = page * 4;
        handleMySQL obj = new handleMySQL();
        Connection conn = obj.connect();
        try {
            String query = "SELECT nama_perusahaan FROM tb_perusahaan JOIN tb_users ON tb_perusahaan.id_user = tb_users.id_user WHERE username = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, usernameParam);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                nama = rs.getString("nama_perusahaan");
            }
            query = "SELECT id_perusahaan FROM tb_perusahaan JOIN tb_users ON tb_perusahaan.id_user = tb_users.id_user WHERE username = ?";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, usernameParam);
            rs = preparedStmt.executeQuery();
            while (rs.next()) {
                idPerusahaan = rs.getInt("id_perusahaan");
            }
            query = "SELECT COUNT(*) AS jumlah FROM (SELECT * FROM (SELECT * FROM tb_lowongan WHERE id_perusahaan = ? ORDER BY date_posted, id_lowongan DESC) AS temp LIMIT 4 OFFSET ?) AS temp2;";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, idPerusahaan);
            preparedStmt.setInt(2, row);
            rs = preparedStmt.executeQuery();
            while (rs.next()) {
                jumlahLowongan = rs.getInt("jumlah");
            }
            query = "SELECT COUNT(*) AS jumlah FROM (SELECT * FROM (SELECT * FROM tb_lowongan WHERE id_perusahaan = ? ORDER BY date_posted, id_lowongan DESC) AS temp LIMIT 4 OFFSET ?) AS temp2;";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, idPerusahaan);
            preparedStmt.setInt(2, nextrow);
            rs = preparedStmt.executeQuery();
            while (rs.next()) {
                nextjumlahLowongan = rs.getInt("jumlah");
            }

            query = "SELECT id_lowongan FROM (SELECT * FROM (SELECT * FROM tb_lowongan WHERE id_perusahaan = ? ORDER BY date_posted, id_lowongan DESC) AS temp LIMIT 4 OFFSET ?) AS temp2";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, idPerusahaan);
            preparedStmt.setInt(2, row);
            rs = preparedStmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                id_lowongan[i] = rs.getInt("id_lowongan");
                i++;
            }
            for (int j = 0; j < jumlahLowongan; j++) {
                query = "SELECT * FROM tb_lowongan WHERE id_lowongan = ?";
                preparedStmt = conn.prepareStatement(query);
                preparedStmt.setInt(1, id_lowongan[j]);
                rs = preparedStmt.executeQuery();
                while (rs.next()) {
                    if (j == 0) {
                        lowongan1Title = rs.getString("nama_lowongan");
                        lowongan1Desc = rs.getString("deskripsi");
                        lowongan1Date = rs.getString("date_posted");
                        if(Objects.equals(rs.getString("enabled"), "1")) {
                            lowongan1Status = "Aktif";
                        } else {
                            lowongan1Status = "Tidak Aktif";
                        }
                    }
                    if (j == 1) {
                        lowongan2Title = rs.getString("nama_lowongan");
                        lowongan2Desc = rs.getString("deskripsi");
                        lowongan2Date = rs.getString("date_posted");
                        if(Objects.equals(rs.getString("enabled"), "1")) {
                            lowongan2Status = "Aktif";
                        } else {
                            lowongan2Status = "Tidak Aktif";
                        }
                    }
                    if (j == 2) {
                        lowongan3Title = rs.getString("nama_lowongan");
                        lowongan3Desc = rs.getString("deskripsi");
                        lowongan3Date = rs.getString("date_posted");
                        if(Objects.equals(rs.getString("enabled"), "1")) {
                            lowongan3Status = "Aktif";
                        } else {
                            lowongan3Status = "Tidak Aktif";
                        }
                    }
                    if (j == 3) {
                        lowongan4Title = rs.getString("nama_lowongan");
                        lowongan4Desc = rs.getString("deskripsi");
                        lowongan4Date = rs.getString("date_posted");
                        if(Objects.equals(rs.getString("enabled"), "1")) {
                            lowongan4Status = "Aktif";
                        } else {
                            lowongan4Status = "Tidak Aktif";
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        initComponents();
        this.setMinimumSize(new Dimension(850, 550));
        Greeting_Label.setText("Selamat datang, " + nama + "!");
        if(jumlahLowongan == 0) {
            jLabel6.setText("Anda belum membuat lowongan kerja, silahkan membuat lowongan kerja terlebih dahulu");
        } else if (jumlahLowongan == 1) {
        } else if (jumlahLowongan == 2) {
        } else if (jumlahLowongan == 3) {
        } else {
        }
        if (page == 1) {
            SebelumnyaButton.setVisible(false);
        }
        if (nextjumlahLowongan == 0) {
            BerikutnyaButton.setVisible(false);
        }
        jPanel1.setPreferredSize(new Dimension(860, 550));
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
        Greeting_Label = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        BerikutnyaButton = new javax.swing.JToggleButton();
        SebelumnyaButton = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lowongan1edit1 = new javax.swing.JButton();
        lowongan1edit = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        lowongan1edit2 = new javax.swing.JButton();
        lowongan1edit3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));

        Greeting_Label.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        Greeting_Label.setText("Selamat datang, xxx!");

        jLabel6.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel6.setText("Lowongan Kerja Anda");

        jButton1.setText("KELUAR");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton1MouseExited(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Buat Lowongan Kerja");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton2MouseExited(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        BerikutnyaButton.setText(">>");
        BerikutnyaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BerikutnyaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BerikutnyaButtonActionPerformed(evt);
            }
        });

        SebelumnyaButton.setText("<<");
        SebelumnyaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SebelumnyaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SebelumnyaButtonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Profile Perusahaan");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplikasilowongankerja/gambar/sinaarmas.png"))); // NOI18N
        jLabel12.setText("jLabel4");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("PT Sinar Mas Agro Resources and Technology Tbk");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setText("Head Manager");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Jakarta");

        jLabel16.setText("Rp 6.000.000 ");

        jLabel17.setText("Manager (Manager pemasaran)");

        jLabel18.setText("Kadaluwarsa 5 Juli 2024");

        lowongan1edit1.setFont(new java.awt.Font("Poppins", 0, 8)); // NOI18N
        lowongan1edit1.setText("PELAMAR");
        lowongan1edit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lowongan1edit1ActionPerformed(evt);
            }
        });

        lowongan1edit.setFont(new java.awt.Font("Poppins", 0, 8)); // NOI18N
        lowongan1edit.setText("EDIT");
        lowongan1edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lowongan1editActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lowongan1edit1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lowongan1edit))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(lowongan1edit1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lowongan1edit))
                .addGap(0, 15, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplikasilowongankerja/gambar/sinaarmas.png"))); // NOI18N
        jLabel33.setText("jLabel4");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel34.setText("PT Sinar Mas Agro Resources and Technology Tbk");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel35.setText("Head Manager");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel36.setText("Jakarta");

        jLabel37.setText("Rp 6.000.000 ");

        jLabel38.setText("Manager (Manager pemasaran)");

        jLabel39.setText("Kadaluwarsa 5 Juli 2024");

        lowongan1edit2.setFont(new java.awt.Font("Poppins", 0, 8)); // NOI18N
        lowongan1edit2.setText("PELAMAR");
        lowongan1edit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lowongan1edit2ActionPerformed(evt);
            }
        });

        lowongan1edit3.setFont(new java.awt.Font("Poppins", 0, 8)); // NOI18N
        lowongan1edit3.setText("EDIT");
        lowongan1edit3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lowongan1edit3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lowongan1edit2))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lowongan1edit3))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34)
                            .addComponent(jLabel35)
                            .addComponent(jLabel36)
                            .addComponent(jLabel37))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(lowongan1edit2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(lowongan1edit3))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(32, 32, 32)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(Greeting_Label)
                            .addGap(315, 315, 315)
                            .addComponent(jLabel3)
                            .addGap(18, 18, 18)
                            .addComponent(jButton1))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jButton2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SebelumnyaButton)
                            .addGap(3, 3, 3)
                            .addComponent(BerikutnyaButton))))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Greeting_Label)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jButton1))))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(SebelumnyaButton)
                    .addComponent(BerikutnyaButton))
                .addGap(49, 49, 49))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SebelumnyaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SebelumnyaButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
        HomePagePerusahaan homePagePerusahaanFrame = new HomePagePerusahaan(username, page-1);
        homePagePerusahaanFrame.setVisible(true);
        homePagePerusahaanFrame.pack();
        homePagePerusahaanFrame.setLocationRelativeTo(null);
    }//GEN-LAST:event_SebelumnyaButtonActionPerformed

    private void BerikutnyaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BerikutnyaButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
        HomePagePerusahaan homePagePerusahaanFrame = new HomePagePerusahaan(username, page+1);
        homePagePerusahaanFrame.setVisible(true);
        homePagePerusahaanFrame.pack();
        homePagePerusahaanFrame.setLocationRelativeTo(null);
    }//GEN-LAST:event_BerikutnyaButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin keluar?", "Warning", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            this.dispose();
            login loginFrame = new login();
            loginFrame.setVisible(true);
            loginFrame.pack();
            loginFrame.setLocationRelativeTo(null);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1MouseExited

    private void jButton1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseEntered
        // TODO add your handling code here:
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jButton1MouseEntered

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        buatLowongan buatLowonganFrame = new buatLowongan(username);
        buatLowonganFrame.setVisible(true);
        buatLowonganFrame.pack();
        buatLowonganFrame.setLocationRelativeTo(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseExited

    private void jButton2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseEntered
        // TODO add your handling code here:
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jButton2MouseEntered

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        // TODO add your handling code here:
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_jLabel3MouseExited

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        // TODO add your handling code here:
        jLabel3.setForeground(new java.awt.Color(0, 100, 255));
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        this.dispose();
        Perusahaan perusahaanFrame = new Perusahaan(username);
        perusahaanFrame.setVisible(true);
        perusahaanFrame.pack();
        perusahaanFrame.setLocationRelativeTo(null);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void lowongan1edit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lowongan1edit1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        Pelamar pelamarFrame = new Pelamar(username, id_lowongan[0], "SEMUA", 1, page);
        pelamarFrame.setVisible(true);
        pelamarFrame.pack();
        pelamarFrame.setLocationRelativeTo(null);
    }//GEN-LAST:event_lowongan1edit1ActionPerformed

    private void lowongan1editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lowongan1editActionPerformed
        // TODO add your handling code here:
        this.dispose();
        editlowongan editlowonganFrame = new editlowongan(username, id_lowongan[0]);
        editlowonganFrame.setVisible(true);
        editlowonganFrame.pack();
        editlowonganFrame.setLocationRelativeTo(null);
    }//GEN-LAST:event_lowongan1editActionPerformed

    private void lowongan1edit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lowongan1edit2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lowongan1edit2ActionPerformed

    private void lowongan1edit3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lowongan1edit3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lowongan1edit3ActionPerformed

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
            java.util.logging.Logger.getLogger(HomePagePerusahaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomePagePerusahaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomePagePerusahaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePagePerusahaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePagePerusahaan("",1).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton BerikutnyaButton;
    private javax.swing.JLabel Greeting_Label;
    private javax.swing.JToggleButton SebelumnyaButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JButton lowongan1edit;
    private javax.swing.JButton lowongan1edit1;
    private javax.swing.JButton lowongan1edit2;
    private javax.swing.JButton lowongan1edit3;
    // End of variables declaration//GEN-END:variables
}

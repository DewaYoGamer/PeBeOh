/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aplikasilowongankerja;

import javax.swing.*;
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
        Greeting_Label.setText("Selamat datang, " + nama + "!");
        if(jumlahLowongan == 0) {
            jLabel6.setText("Anda belum membuat lowongan kerja, silahkan membuat lowongan kerja terlebih dahulu");
            lowongan1.setVisible(false);
            lowongan2.setVisible(false);
            lowongan3.setVisible(false);
            lowongan4.setVisible(false);
        } else if (jumlahLowongan == 1) {
            lowongan2.setVisible(false);
            lowongan3.setVisible(false);
            lowongan4.setVisible(false);
            lowongan1title.setText(lowongan1Title);
            lowongan1area.setText(lowongan1Desc);
            lowongan1date.setText(lowongan1Date);
            lowongan1status.setText(lowongan1Status);
        } else if (jumlahLowongan == 2) {
            lowongan3.setVisible(false);
            lowongan4.setVisible(false);
            lowongan1title.setText(lowongan1Title);
            lowongan1area.setText(lowongan1Desc);
            lowongan1date.setText(lowongan1Date);
            lowongan1status.setText(lowongan1Status);
            lowongan2title.setText(lowongan2Title);
            lowongan2area.setText(lowongan2Desc);
            lowongan2date.setText(lowongan2Date);
            lowongan2status.setText(lowongan2Status);
        } else if (jumlahLowongan == 3) {
            lowongan4.setVisible(false);
            lowongan1title.setText(lowongan1Title);
            lowongan1area.setText(lowongan1Desc);
            lowongan1date.setText(lowongan1Date);
            lowongan1status.setText(lowongan1Status);
            lowongan2title.setText(lowongan2Title);
            lowongan2area.setText(lowongan2Desc);
            lowongan2date.setText(lowongan2Date);
            lowongan2status.setText(lowongan2Status);
            lowongan3title.setText(lowongan3Title);
            lowongan3area.setText(lowongan3Desc);
            lowongan3date.setText(lowongan3Date);
            lowongan3status.setText(lowongan3Status);
        } else {
            lowongan1title.setText(lowongan1Title);
            lowongan1area.setText(lowongan1Desc);
            lowongan1date.setText(lowongan1Date);
            lowongan1status.setText(lowongan1Status);
            lowongan2title.setText(lowongan2Title);
            lowongan2area.setText(lowongan2Desc);
            lowongan2date.setText(lowongan2Date);
            lowongan2status.setText(lowongan2Status);
            lowongan3title.setText(lowongan3Title);
            lowongan3area.setText(lowongan3Desc);
            lowongan3date.setText(lowongan3Date);
            lowongan3status.setText(lowongan3Status);
            lowongan4title.setText(lowongan4Title);
            lowongan4area.setText(lowongan4Desc);
            lowongan4date.setText(lowongan4Date);
            lowongan4status.setText(lowongan4Status);
        }
        if (page == 1) {
            SebelumnyaButton.setVisible(false);
        }
        if (nextjumlahLowongan == 0) {
            BerikutnyaButton.setVisible(false);
        }
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
        lowongan4 = new javax.swing.JPanel();
        lowongan4title = new javax.swing.JLabel();
        lowongan4pane = new javax.swing.JScrollPane();
        lowongan4area = new javax.swing.JTextArea();
        lowongan4date = new javax.swing.JLabel();
        lowongan4status = new javax.swing.JLabel();
        lowongan4edit = new javax.swing.JButton();
        lowongan1edit2 = new javax.swing.JButton();
        lowongan3 = new javax.swing.JPanel();
        lowongan3title = new javax.swing.JLabel();
        lowongan3pane = new javax.swing.JScrollPane();
        lowongan3area = new javax.swing.JTextArea();
        lowongan3date = new javax.swing.JLabel();
        lowongan3status = new javax.swing.JLabel();
        lowongan3edit = new javax.swing.JButton();
        lowongan1edit3 = new javax.swing.JButton();
        lowongan2 = new javax.swing.JPanel();
        lowongan2title = new javax.swing.JLabel();
        lowongan2pane = new javax.swing.JScrollPane();
        lowongan2area = new javax.swing.JTextArea();
        lowongan2date = new javax.swing.JLabel();
        lowongan2status = new javax.swing.JLabel();
        lowongan2edit = new javax.swing.JButton();
        lowongan1edit4 = new javax.swing.JButton();
        lowongan1 = new javax.swing.JPanel();
        lowongan1title = new javax.swing.JLabel();
        lowongan1pane = new javax.swing.JScrollPane();
        lowongan1area = new javax.swing.JTextArea();
        lowongan1date = new javax.swing.JLabel();
        lowongan1status = new javax.swing.JLabel();
        lowongan1edit = new javax.swing.JButton();
        lowongan1edit1 = new javax.swing.JButton();
        BerikutnyaButton = new javax.swing.JToggleButton();
        SebelumnyaButton = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();

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

        lowongan4.setBackground(new java.awt.Color(255, 255, 255));
        lowongan4.setAlignmentX(0.0F);
        lowongan4.setAlignmentY(0.0F);

        lowongan4title.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lowongan4title.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lowongan4title.setText("xxx");

        lowongan4area.setEditable(false);
        lowongan4area.setColumns(20);
        lowongan4area.setRows(5);
        lowongan4area.setText("xxx");
        lowongan4area.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lowongan4area.setEnabled(false);
        lowongan4pane.setViewportView(lowongan4area);

        lowongan4date.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lowongan4date.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lowongan4date.setText("Date");

        lowongan4status.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        lowongan4status.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lowongan4status.setText("Aktif");

        lowongan4edit.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        lowongan4edit.setText("EDIT");
        lowongan4edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lowongan4editActionPerformed(evt);
            }
        });

        lowongan1edit2.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        lowongan1edit2.setText("LAMARAN");
        lowongan1edit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lowongan1edit2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout lowongan4Layout = new javax.swing.GroupLayout(lowongan4);
        lowongan4.setLayout(lowongan4Layout);
        lowongan4Layout.setHorizontalGroup(
            lowongan4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lowongan4Layout.createSequentialGroup()
                .addGroup(lowongan4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(lowongan4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lowongan4title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lowongan4date, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(lowongan4Layout.createSequentialGroup()
                        .addComponent(lowongan4pane, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(lowongan4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lowongan1edit2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(lowongan4edit, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(lowongan4status, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        lowongan4Layout.setVerticalGroup(
            lowongan4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lowongan4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lowongan4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lowongan4title)
                    .addComponent(lowongan4date))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lowongan4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lowongan4Layout.createSequentialGroup()
                        .addComponent(lowongan4status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lowongan1edit2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lowongan4edit)
                        .addContainerGap())
                    .addComponent(lowongan4pane, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)))
        );

        lowongan3.setBackground(new java.awt.Color(255, 255, 255));
        lowongan3.setAlignmentX(0.0F);
        lowongan3.setAlignmentY(0.0F);

        lowongan3title.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lowongan3title.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lowongan3title.setText("xxx");

        lowongan3area.setEditable(false);
        lowongan3area.setColumns(20);
        lowongan3area.setRows(5);
        lowongan3area.setText("xxx");
        lowongan3area.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lowongan3area.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lowongan3area.setEnabled(false);
        lowongan3pane.setViewportView(lowongan3area);

        lowongan3date.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lowongan3date.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lowongan3date.setText("Date");

        lowongan3status.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        lowongan3status.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lowongan3status.setText("Aktif");

        lowongan3edit.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        lowongan3edit.setText("EDIT");
        lowongan3edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lowongan3editActionPerformed(evt);
            }
        });

        lowongan1edit3.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        lowongan1edit3.setText("LAMARAN");
        lowongan1edit3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lowongan1edit3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout lowongan3Layout = new javax.swing.GroupLayout(lowongan3);
        lowongan3.setLayout(lowongan3Layout);
        lowongan3Layout.setHorizontalGroup(
            lowongan3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lowongan3Layout.createSequentialGroup()
                .addGroup(lowongan3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lowongan3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lowongan3title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lowongan3date, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(lowongan3Layout.createSequentialGroup()
                        .addComponent(lowongan3pane, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(lowongan3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lowongan3status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lowongan3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(lowongan3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lowongan3edit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lowongan1edit3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        lowongan3Layout.setVerticalGroup(
            lowongan3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lowongan3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lowongan3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lowongan3title)
                    .addComponent(lowongan3date))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lowongan3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lowongan3pane)
                    .addGroup(lowongan3Layout.createSequentialGroup()
                        .addComponent(lowongan3status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lowongan1edit3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lowongan3edit)
                        .addContainerGap())))
        );

        lowongan2.setBackground(new java.awt.Color(255, 255, 255));
        lowongan2.setAlignmentX(0.0F);
        lowongan2.setAlignmentY(0.0F);

        lowongan2title.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lowongan2title.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lowongan2title.setText("xxx");

        lowongan2area.setEditable(false);
        lowongan2area.setColumns(20);
        lowongan2area.setRows(5);
        lowongan2area.setText("xxx");
        lowongan2area.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lowongan2area.setEnabled(false);
        lowongan2pane.setViewportView(lowongan2area);

        lowongan2date.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lowongan2date.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lowongan2date.setText("Date");

        lowongan2status.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        lowongan2status.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lowongan2status.setText("Aktif");

        lowongan2edit.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        lowongan2edit.setText("EDIT");
        lowongan2edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lowongan2editActionPerformed(evt);
            }
        });

        lowongan1edit4.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        lowongan1edit4.setText("LAMARAN");
        lowongan1edit4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lowongan1edit4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout lowongan2Layout = new javax.swing.GroupLayout(lowongan2);
        lowongan2.setLayout(lowongan2Layout);
        lowongan2Layout.setHorizontalGroup(
            lowongan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lowongan2Layout.createSequentialGroup()
                .addGroup(lowongan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lowongan2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lowongan2title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lowongan2date, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(lowongan2Layout.createSequentialGroup()
                        .addComponent(lowongan2pane, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(lowongan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lowongan2edit, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(lowongan1edit4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(lowongan2status, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))))
                .addContainerGap())
        );
        lowongan2Layout.setVerticalGroup(
            lowongan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lowongan2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lowongan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lowongan2title)
                    .addComponent(lowongan2date))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lowongan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lowongan2pane)
                    .addGroup(lowongan2Layout.createSequentialGroup()
                        .addComponent(lowongan2status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lowongan1edit4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lowongan2edit)
                        .addContainerGap())))
        );

        lowongan1.setBackground(new java.awt.Color(255, 255, 255));
        lowongan1.setAlignmentX(0.0F);
        lowongan1.setAlignmentY(0.0F);

        lowongan1title.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lowongan1title.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lowongan1title.setText("xxx");

        lowongan1area.setEditable(false);
        lowongan1area.setColumns(20);
        lowongan1area.setRows(5);
        lowongan1area.setText("xxxx");
        lowongan1area.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lowongan1area.setDragEnabled(true);
        lowongan1area.setEnabled(false);
        lowongan1pane.setViewportView(lowongan1area);

        lowongan1date.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lowongan1date.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lowongan1date.setText("Date");

        lowongan1status.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        lowongan1status.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lowongan1status.setText("Aktif");

        lowongan1edit.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        lowongan1edit.setText("EDIT");
        lowongan1edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lowongan1editActionPerformed(evt);
            }
        });

        lowongan1edit1.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        lowongan1edit1.setText("LAMARAN");
        lowongan1edit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lowongan1edit1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout lowongan1Layout = new javax.swing.GroupLayout(lowongan1);
        lowongan1.setLayout(lowongan1Layout);
        lowongan1Layout.setHorizontalGroup(
            lowongan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lowongan1Layout.createSequentialGroup()
                .addGroup(lowongan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lowongan1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lowongan1title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lowongan1date, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(lowongan1Layout.createSequentialGroup()
                        .addComponent(lowongan1pane, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(lowongan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lowongan1status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lowongan1edit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lowongan1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lowongan1edit1)))))
                .addContainerGap())
        );
        lowongan1Layout.setVerticalGroup(
            lowongan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lowongan1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lowongan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lowongan1title)
                    .addComponent(lowongan1date))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lowongan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lowongan1pane, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                    .addGroup(lowongan1Layout.createSequentialGroup()
                        .addComponent(lowongan1status)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lowongan1edit1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lowongan1edit)
                        .addContainerGap())))
        );

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

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Greeting_Label)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lowongan1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lowongan3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(72, 72, 72)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lowongan4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lowongan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(SebelumnyaButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BerikutnyaButton)))
                        .addGap(0, 34, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Greeting_Label)
                    .addComponent(jButton1)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lowongan2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lowongan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lowongan4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lowongan3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(BerikutnyaButton)
                    .addComponent(SebelumnyaButton))
                .addGap(51, 51, 51))
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

    private void lowongan3editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lowongan3editActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lowongan3editActionPerformed

    private void lowongan1editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lowongan1editActionPerformed
        // TODO add your handling code here:
        this.dispose();
        editlowongan editlowonganFrame = new editlowongan(username, id_lowongan[0]);
        editlowonganFrame.setVisible(true);
        editlowonganFrame.pack();
        editlowonganFrame.setLocationRelativeTo(null);
    }//GEN-LAST:event_lowongan1editActionPerformed

    private void lowongan2editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lowongan2editActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lowongan2editActionPerformed

    private void lowongan4editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lowongan4editActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lowongan4editActionPerformed

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
        buatlowongan buatlowonganFrame = new buatlowongan(username);
        buatlowonganFrame.setVisible(true);
        buatlowonganFrame.pack();
        buatlowonganFrame.setLocationRelativeTo(null);
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
        jLabel3.setForeground(new java.awt.Color(0, 150, 255));
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
    }//GEN-LAST:event_lowongan1edit1ActionPerformed

    private void lowongan1edit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lowongan1edit2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lowongan1edit2ActionPerformed

    private void lowongan1edit3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lowongan1edit3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lowongan1edit3ActionPerformed

    private void lowongan1edit4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lowongan1edit4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lowongan1edit4ActionPerformed

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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel lowongan1;
    private javax.swing.JTextArea lowongan1area;
    private javax.swing.JLabel lowongan1date;
    private javax.swing.JButton lowongan1edit;
    private javax.swing.JButton lowongan1edit1;
    private javax.swing.JButton lowongan1edit2;
    private javax.swing.JButton lowongan1edit3;
    private javax.swing.JButton lowongan1edit4;
    private javax.swing.JScrollPane lowongan1pane;
    private javax.swing.JLabel lowongan1status;
    private javax.swing.JLabel lowongan1title;
    private javax.swing.JPanel lowongan2;
    private javax.swing.JTextArea lowongan2area;
    private javax.swing.JLabel lowongan2date;
    private javax.swing.JButton lowongan2edit;
    private javax.swing.JScrollPane lowongan2pane;
    private javax.swing.JLabel lowongan2status;
    private javax.swing.JLabel lowongan2title;
    private javax.swing.JPanel lowongan3;
    private javax.swing.JTextArea lowongan3area;
    private javax.swing.JLabel lowongan3date;
    private javax.swing.JButton lowongan3edit;
    private javax.swing.JScrollPane lowongan3pane;
    private javax.swing.JLabel lowongan3status;
    private javax.swing.JLabel lowongan3title;
    private javax.swing.JPanel lowongan4;
    private javax.swing.JTextArea lowongan4area;
    private javax.swing.JLabel lowongan4date;
    private javax.swing.JButton lowongan4edit;
    private javax.swing.JScrollPane lowongan4pane;
    private javax.swing.JLabel lowongan4status;
    private javax.swing.JLabel lowongan4title;
    // End of variables declaration//GEN-END:variables
}

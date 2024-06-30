/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aplikasilowongankerja;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Dektu
 */
public class Pelamar extends javax.swing.JFrame {
    String username, filter, nama_lowongan;
    int jumlahLamaran, nextJumlahLamaran, reallyJumlahLamaran;
    int page, oldPage;
    int row, nextrow;
    int id_lowongan;

    int []id_lamaran = new int[3];
    String [] status = new String[3];
    String [] nama_lengkap = new String[3];
    int [] umur = new int[3];
    String [] pengalaman = new String[3];
    /**
     * Creates new form Pelamar
     */
    public Pelamar(String usernameParam, int id_lowonganParam, String filterParam, int pageParam, int oldPageParam) {
        username = usernameParam;
        page = pageParam;
        row = (page - 1) * 3;
        nextrow = page * 3;
        filter = filterParam;
        id_lowongan = id_lowonganParam;
        oldPage = oldPageParam;
        handleMySQL obj = new handleMySQL();
        Connection conn = obj.connect();
        String query;
        try{
            query = "SELECT nama_lowongan FROM tb_lowongan WHERE id_lowongan = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id_lowongan);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()){
                nama_lowongan = rs.getString("nama_lowongan");
            }

            if (filter.equals("SEMUA")){
                query = "SELECT COUNT(*) AS jumlah FROM (SELECT * FROM (SELECT * FROM tb_lamaran WHERE id_lowongan = ? ORDER BY id_lamaran DESC) AS temp LIMIT 3 OFFSET ?) AS temp2";;
            } else if (filter.equals("Diterima")){
                query = "SELECT COUNT(*) AS jumlah FROM (SELECT * FROM (SELECT * FROM tb_lamaran WHERE id_lowongan = ? AND accepted = 1 ORDER BY id_lamaran DESC) AS temp LIMIT 3 OFFSET ?) AS temp2";
            } else if (filter.equals("Ditolak")){
                query = "SELECT COUNT(*) AS jumlah FROM (SELECT * FROM (SELECT * FROM tb_lamaran WHERE id_lowongan = ? AND accepted = 0 ORDER BY id_lamaran DESC) AS temp LIMIT 3 OFFSET ?) AS temp2";
            } else {
                query = "SELECT COUNT(*) AS jumlah FROM (SELECT * FROM (SELECT * FROM tb_lamaran WHERE id_lowongan = ? AND accepted IS NULL ORDER BY id_lamaran DESC) AS temp LIMIT 3 OFFSET ?) AS temp2";
            }
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id_lowongan);
            preparedStmt.setInt(2, row);
            rs = preparedStmt.executeQuery();
            while (rs.next()){
                jumlahLamaran = rs.getInt("jumlah");
            }

            if (filter.equals("SEMUA")){
                query = "SELECT COUNT(*) AS jumlah FROM (SELECT * FROM (SELECT * FROM tb_lamaran WHERE id_lowongan = ? ORDER BY id_lamaran DESC) AS temp LIMIT 3 OFFSET ?) AS temp2";
            } else if (filter.equals("Diterima")){
                query = "SELECT COUNT(*) AS jumlah FROM (SELECT * FROM (SELECT * FROM tb_lamaran WHERE id_lowongan = ? AND accepted = 1 ORDER BY id_lamaran DESC) AS temp LIMIT 3 OFFSET ?) AS temp2";
            } else if (filter.equals("Ditolak")){
                query = "SELECT COUNT(*) AS jumlah FROM (SELECT * FROM (SELECT * FROM tb_lamaran WHERE id_lowongan = ? AND accepted = 0 ORDER BY id_lamaran DESC) AS temp LIMIT 3 OFFSET ?) AS temp2";
            } else {
                query = "SELECT COUNT(*) AS jumlah FROM (SELECT * FROM (SELECT * FROM tb_lamaran WHERE id_lowongan = ? AND accepted IS NULL ORDER BY id_lamaran DESC) AS temp LIMIT 3 OFFSET ?) AS temp2";
            }
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id_lowongan);
            preparedStmt.setInt(2, nextrow);
            rs = preparedStmt.executeQuery();
            while (rs.next()){
                nextJumlahLamaran = rs.getInt("jumlah");
            }

            if (filter.equals("SEMUA")){
                query = "SELECT id_lamaran FROM (SELECT * FROM (SELECT * FROM tb_lamaran WHERE id_lowongan = ? ORDER BY id_lamaran DESC) AS temp LIMIT 3 OFFSET ?) AS temp2";
            } else if (filter.equals("Diterima")){
                query = "SELECT id_lamaran FROM (SELECT * FROM (SELECT * FROM tb_lamaran WHERE id_lowongan = ? AND accepted = 1 ORDER BY id_lamaran DESC) AS temp LIMIT 3 OFFSET ?) AS temp2";
            } else if (filter.equals("Ditolak")){
                query = "SELECT id_lamaran FROM (SELECT * FROM (SELECT * FROM tb_lamaran WHERE id_lowongan = ? AND accepted = 0 ORDER BY id_lamaran DESC) AS temp LIMIT 3 OFFSET ?) AS temp2";
            } else {
                query = "SELECT id_lamaran FROM (SELECT * FROM (SELECT * FROM tb_lamaran WHERE id_lowongan = ? AND accepted IS NULL ORDER BY id_lamaran DESC) AS temp LIMIT 3 OFFSET ?) AS temp2";
            }
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id_lowongan);
            preparedStmt.setInt(2, row);
            rs = preparedStmt.executeQuery();
            int i = 0;
            while (rs.next()){
                id_lamaran[i] = rs.getInt("id_lamaran");
                i++;
            }

            for (int j = 0; j < 3; j++){
                if (id_lamaran[j] != 0){
                    query = "SELECT * FROM tb_lamaran JOIN tb_pengguna ON tb_lamaran.id_pengguna = tb_pengguna.id_pengguna WHERE id_lamaran = ?";
                    preparedStmt = conn.prepareStatement(query);
                    preparedStmt.setInt(1, id_lamaran[j]);
                    rs = preparedStmt.executeQuery();
                    while (rs.next()){
                        nama_lengkap[j] = rs.getString("nama_pengguna");
                        umur[j] = rs.getInt("usia");
                        pengalaman[j] = rs.getString("pengalaman");
                        if(rs.getInt("accepted") == 1){
                            status[j] = "DITERIMA";
                        } else if (rs.getInt("accepted") == 0 && rs.wasNull()){
                            status[j] = "Pending";
                        } else {
                            status[j] = "DITOLAK";
                        }
                    }
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        initComponents();
        // make the number dynamic for every page
        int []nomor = new int[3];
        for (int i = 0; i < 3; i++){
            nomor[i] = page * 3 - 2 + i;
        }
        jComboBox1.setSelectedItem(filter);
        namaperu3.setText("Pelamar " + nama_lowongan);
        if (nextJumlahLamaran == 0){
        }
        if (page == 1){
        }
        if (jumlahLamaran > 0){
        }
        if (jumlahLamaran > 1){
        }
        if (jumlahLamaran > 2){
        }
        if (jumlahLamaran == 0){
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
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        namaperu3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));
        jPanel1.setForeground(new java.awt.Color(0, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(850, 700));

        jLabel4.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Filter");

        jComboBox1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SEMUA", "Ditolak", "Diterima", "Belum Direspon" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jButton6.setText("Terapkan");
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(204, 255, 255));

        namaperu3.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        namaperu3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        namaperu3.setText("Pelamar xxx");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(namaperu3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(namaperu3)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                { new Integer(1), "I Putu Arya Putra Raditya",  new Integer(19), "Menguasai Javascript"},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No", "Nama", "Umur", "Skill/Keahlian"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(50);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(1).setMinWidth(250);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(250);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(250);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(100);
        }

        jButton1.setText("Detail");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(452, 452, 452)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jButton1)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        Pelamar obj = new Pelamar(username, id_lowongan, jComboBox1.getSelectedItem().toString(), 1, oldPage);
        obj.setVisible(true);
        obj.pack();
        obj.setLocationRelativeTo(null);
    }//GEN-LAST:event_jButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(Pelamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pelamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pelamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pelamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pelamar("", 0, "",0, 0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel namaperu3;
    // End of variables declaration//GEN-END:variables
}

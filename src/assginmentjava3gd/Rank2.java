/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package assginmentjava3gd;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Rank2 extends javax.swing.JInternalFrame {
    DefaultTableModel model = new DefaultTableModel();
    public Rank2() {
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        loadSubjectID();
        chinhjtable();
        
    }
public void chinhjtable(){
                // Tùy chỉnh giao diện JTable
        tblRank.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // chỉnh chữ
        tblRank.setRowHeight(30);// chỉnh độ cao của bảng
        tblRank.setGridColor(new Color(230, 230, 230));
        tblRank.setBackground(new Color(245, 245, 245));
        tblRank.setForeground(new Color(50, 50, 50));
        tblRank.setSelectionBackground(new Color(0, 120, 215));
        tblRank.setSelectionForeground(Color.WHITE);

        // Tùy chỉnh header
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
        @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Giữ màu nền và màu chữ của header
                comp.setBackground(new Color(0, 153, 204)); // Màu nền của header
                comp.setForeground(Color.WHITE); // Màu chữ
                setFont(new Font("Segoe UI", Font.BOLD, 18)); // Phông chữ
                setHorizontalAlignment(JLabel.CENTER); // Căn giữa

                return comp;
            }
        };
       
    // Áp dụng renderer cho từng cột
    for (int i = 0; i < tblRank.getColumnCount(); i++) {
        tblRank.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
    }


        // Căn giữa nội dung các ô
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblRank.getColumnCount(); i++) {
            tblRank.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

    }
    
     private Connection connect() throws Exception {
        String url = "jdbc:mysql://localhost:3306/assjava3"; // Thay 'ten_database' bằng tên database
        String user = "root"; // Thay username
        String password = "0359910800"; // Thay password
        return DriverManager.getConnection(url, user, password);
    }
    private void loadSubjectID() {
        String query = getSelectSubjectCodeQuery(); // Gọi câu lệnh SELECT từ phương thức khác
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            cboMon.removeAllItems(); // Xóa tất cả các mục hiện có trong ComboBox
            while (rs.next()) {
                cboMon.addItem(rs.getString(1)); // Thêm tên lớp vào ComboBox
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách lớp.");
        }
    }
    // Phương thức để trả về câu lệnh SELECT
    private String getSelectSubjectCodeQuery() {
        return "SELECT maMon FROM MonHoc"; // Sửa câu lệnh này tùy thuộc vào cơ sở dữ liệu của bạn
    }
    
   private void loadDiemForMonHoc() throws Exception {
    String selectedMaMon = (String) cboMon.getSelectedItem();
    try (Connection conn = connect(); // Sử dụng phương thức connect để kết nối
         PreparedStatement ps = conn.prepareStatement(
                 "SELECT Diem.maDiem, Diem.maSV, SinhVien.tenSV, Diem.maMon, Diem.diemTrungBinh, Diem.xepLoai, Diem.trangThai " +
                         "FROM Diem " +
                         "JOIN SinhVien ON Diem.maSV = SinhVien.maSV " +
                         "WHERE Diem.maMon = ?")) {

        ps.setString(1, selectedMaMon);
        try (ResultSet rs = ps.executeQuery()) {
            // Tạo danh sách lưu dữ liệu từ ResultSet
            List<Object[]> dataList = new ArrayList<>();

            // Lưu dữ liệu từ ResultSet vào danh sách
            while (rs.next()) {
                String maDiem = rs.getString("maDiem");
                String maSV = rs.getString("maSV");
                String tenSV = rs.getString("tenSV");
                String maMon = rs.getString("maMon");
                double diemTrungBinh = rs.getDouble("diemTrungBinh");
                String xepLoai = rs.getString("xepLoai");
                String trangThai = rs.getString("trangThai");

                dataList.add(new Object[]{maDiem, maSV, tenSV, maMon, diemTrungBinh, xepLoai, trangThai});
            }

            // Sắp xếp danh sách theo điểm trung bình giảm dần
            dataList.sort((o1, o2) -> Double.compare((double) o2[4], (double) o1[4]));

            // Gán xếp hạng và cập nhật dữ liệu vào bảng
            DefaultTableModel model = (DefaultTableModel) tblRank.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

            int rank = 1; // Bắt đầu từ xếp hạng 1
            for (Object[] row : dataList) {
                // Thêm cột xếp hạng vào dữ liệu
                Object[] rowWithRank = new Object[row.length + 1];
                rowWithRank[0] = rank; // Cột xếp hạng
                System.arraycopy(row, 0, rowWithRank, 1, row.length);
                rank++;

                // Thêm dòng mới vào bảng
                model.addRow(rowWithRank);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cboMon = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRank = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        cboMon.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cboMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMonActionPerformed(evt);
            }
        });

        tblRank.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Rank", "Point ID", "Student ID", "Student Name", "Subject ID", "Avg Point ", "Classification", "Status"
            }
        ));
        jScrollPane1.setViewportView(tblRank);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1093, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(493, 493, 493)
                        .addComponent(cboMon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(cboMon, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMonActionPerformed
        try {
            loadDiemForMonHoc();
        } catch (Exception ex) {
            Logger.getLogger(Rank2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cboMonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboMon;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblRank;
    // End of variables declaration//GEN-END:variables
}

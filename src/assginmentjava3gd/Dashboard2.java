/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package assginmentjava3gd;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.data.general.DefaultPieDataset;


public class Dashboard2 extends javax.swing.JInternalFrame {

    /**
     * Creates new form Dashboard2
     */
    public Dashboard2() {
        initComponents();
        initComponents();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI)this.getUI();
        ui.setNorthPane(null);
        showPieChart();
    }

    public void showPieChart() {
    DefaultPieDataset pieDataset = new DefaultPieDataset();

    // Thông tin kết nối cơ sở dữ liệu
    String url = "jdbc:mysql://localhost:3306/qlsv"; // Tên database của bạn
    String user = "root"; // Username của MySQL
    String password = "tranhainam123"; // Password của MySQL

    try {
        // Kết nối cơ sở dữ liệu
        Connection con = DriverManager.getConnection(url, user, password);
        Statement stmt = con.createStatement();

        // Truy vấn dữ liệu - Đếm số lượng sinh viên theo mã môn
        String query = "SELECT maMon, COUNT(*) AS soLuongSinhVien FROM SinhVien GROUP BY maMon";
        ResultSet rs = stmt.executeQuery(query);

        // Thêm dữ liệu từ ResultSet vào dataset
        while (rs.next()) {
            String maMon = rs.getString("maMon"); // Mã môn
            int soLuong = rs.getInt("soLuongSinhVien"); // Số lượng sinh viên
            pieDataset.setValue("Môn: " + maMon, soLuong); // Thêm dữ liệu vào PieDataset
        }

        // Đóng tài nguyên
        rs.close();
        stmt.close();
        con.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    // Tạo biểu đồ hình tròn
    JFreeChart chart = ChartFactory.createPieChart(
        "Biểu đồ số lượng sinh viên theo môn học", // Tiêu đề biểu đồ
        pieDataset,                              // Dataset
        true,                                    // Hiển thị chú thích
        true,                                    // Hiển thị công cụ
        false                                    // Không sử dụng URL
    );

    // Tùy chỉnh nhãn hiển thị: hiển thị tên môn và số lượng sinh viên
    StandardPieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
        "{0}: {1} sinh viên", // {0} = Tên môn, {1} = Số lượng, {2} = Phần trăm
        new DecimalFormat("0"), // Định dạng số lượng
        new DecimalFormat("0%") // Định dạng phần trăm (không dùng nhưng cần thiết)
    );
    ((org.jfree.chart.plot.PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);

    // Hiển thị biểu đồ trong ChartPanel
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(380, 400));

    // Cập nhật biểu đồ vào panel có sẵn
    piechart.removeAll(); // Xóa nội dung hiện tại của piechart
    piechart.setLayout(new BorderLayout()); // Đặt layout cho piechart
    piechart.add(chartPanel, BorderLayout.CENTER); // Thêm biểu đồ vào piechart
    piechart.revalidate(); // Làm mới piechart
    piechart.repaint(); // Vẽ lại piechart
}
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        piechart = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(962, 482));

        javax.swing.GroupLayout piechartLayout = new javax.swing.GroupLayout(piechart);
        piechart.setLayout(piechartLayout);
        piechartLayout.setHorizontalGroup(
            piechartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 357, Short.MAX_VALUE)
        );
        piechartLayout.setVerticalGroup(
            piechartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 289, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(piechart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(587, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(piechart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(151, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel piechart;
    // End of variables declaration//GEN-END:variables
}

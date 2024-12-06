/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author ACER
 */
public class BarchartDAO2 {
    private JPanel barchart;
    
    public BarchartDAO2(){
        this.barchart = barchart;
    }
    public static void showbarchart(JPanel barchart){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Kết nối đến MySQL và lấy dữ liệu từ cơ sở dữ liệu
        String url = "jdbc:mysql://localhost:3306/assjava3"; // Thay bằng tên database của bạn
        String user = "root"; // Thay bằng username của bạn
        String password = "0359910800"; // Thay bằng password của bạn

        try {
            // Kết nối cơ sở dữ liệu
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();

            // Truy vấn dữ liệu từ bảng "ngành" theo phòng ban
            String query = "SELECT maLop , COUNT(maSV) as Sinhvien  FROM sinhvien GROUP BY maLop"; // Nhóm theo phòng ban
            ResultSet rs = stmt.executeQuery(query);

            // Thêm dữ liệu từ ResultSet vào dataset
            while (rs.next()) {
                String sinhvien = rs.getString("maLop");
                int count = rs.getInt("Sinhvien");
                dataset.addValue(count, "Số lượng sinh viên trong lop", sinhvien);
            }

            rs.close();
            stmt.close();
            con.close();


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Tạo biểu đồ hình thang từ dataset
        JFreeChart barChart = ChartFactory.createBarChart(
            "Số lượng sinh viên theo lớp", // Tiêu đề biểu đồ
            "Lớp", // Trục X
            "Số lượng sinh viên", // Trục Y
            dataset, // Dataset
            PlotOrientation.VERTICAL, // Hướng biểu đồ
            true, // Hiển thị legend
            true, // Hiển thị tooltips
            false // Hiển thị URL
        );

        // Hiển thị biểu đồ trong ChartPanel
        ChartPanel chartPanelToDisplay = new ChartPanel(barChart); // Sử dụng barChart
        chartPanelToDisplay.setPreferredSize(new Dimension(380, 400));

        // Sử dụng panel có sẵn (barchart đã được định nghĩa trước đó)
        barchart.removeAll(); // Xóa nội dung hiện tại của barchart
        barchart.setLayout(new BorderLayout()); // Đảm bảo layout BorderLayout được áp dụng
        barchart.add(chartPanelToDisplay, BorderLayout.CENTER); // Thêm biểu đồ vào panel có sẵn
        barchart.revalidate(); // Cập nhật lại panel
        barchart.repaint(); // Vẽ lại panel
    }
}

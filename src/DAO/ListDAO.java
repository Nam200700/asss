/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


import Model.Student2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class ListDAO {
     private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/assjava3"; // Đổi theo cơ sở dữ liệu của bạn
    private static final String USER = "root";
    private static final String PASSWORD = "0359910800"; // Đổi mật khẩu của bạn nếu cần

    static {
        try {
            // Đăng ký driver của MySQL
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to register MySQL driver", e);
        }
    }

    // Phương thức kết nối
    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }
    
    
    public List<Student2> searchStudents(String keyword) {
        List<Student2> students = new ArrayList<>();
        String query = "SELECT * FROM SinhVien WHERE maSV LIKE ? OR tenSV LIKE ?";
        try (Connection conn = connection();
             PreparedStatement stmt = conn.prepareStatement(query)) { // Sử dụng biến conn đã được khởi tạo
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Student2 student = new Student2(
                    rs.getString("maSV"),
                    rs.getString("tenSV"),
                    rs.getString("maMon"),
                    rs.getBoolean("gioiTinh"),
                    rs.getInt("tuoi"),
                    rs.getString("tenlop")
                );
                 student.setTenlop(rs.getString("tenlop"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
    
   
}

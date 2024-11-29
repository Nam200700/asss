/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Class2;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ACER
 */
public class ClassDAO2 {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/qlsv"; // Đổi theo cơ sở dữ liệu của bạn
    private static final String USER = "root";
    private static final String PASSWORD = "tranhainam123"; // Đổi mật khẩu của bạn nếu cần

    static {
        try {
            // Đăng ký driver của MySQL
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to register MySQL driver", e);
        }
    }

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    
    public static void insertDe(Class2 cl) {
    String sql = "INSERT INTO LopHoc (maLop,tenLop,moTa) VALUES (?, ?, ?)";
    
    try (Connection conn = connection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, cl.getMalop());            
        pstmt.setString(2, cl.getTenlop());          
        pstmt.setString(3, cl.getMota());              
       

        // Thực thi câu lệnh
        int rowsInserted = pstmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Thêm nhân viên thành công!");
        }
    } catch (SQLException e) {
        System.err.println("Lỗi khi thêm nhân viên: " + e.getMessage());
        e.printStackTrace();
    }
}
    public static void updateDe(Class2 cl) {
    String sql = "UPDATE LopHoc SET tenLop = ?, moTa = ? WHERE maLop = ?";
    
    try (Connection conn = connection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, cl.getTenlop());
        pstmt.setString(2, cl.getMota());
        pstmt.setString(3, cl.getMalop());

        // Thực thi câu lệnh
        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Cập nhật thông tin lớp học thành công!");
        }
    } catch (SQLException e) {
        System.err.println("Lỗi khi cập nhật thông tin lớp học: " + e.getMessage());
        e.printStackTrace();
    }
}       
    public static boolean deleteSub(String malop) {
        String sql = "DELETE FROM LopHoc WHERE malop = ?";

        try (Connection conn = connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, malop);  // Đặt giá trị maLop cần xóa
            int affectedRows = pstmt.executeUpdate();  // Thực hiện câu lệnh DELETE
            
            return affectedRows > 0;
            } catch (SQLException e) {
                if ("2300".equals(e.getSQLState())) {
                    System.out.println("Lỗi khóa ngoại :Không thể xóa môn học vì còn dữ liệu liên quan!");
                    
                }else{
                     System.err.println("SQL Exception: " + e.getMessage());
                }
             
           return false;
        }
    }
public static List<Class2> getAllClasses() {
    List<Class2> classes = new ArrayList<>();
    String sql = "SELECT maLop, tenLop, moTa FROM LopHoc";

    try (Connection conn = connection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            Class2 cl = new Class2();
            cl.malop = rs.getString("maLop");
            cl.tenlop = rs.getString("tenLop");
            cl.mota = rs.getString("moTa");
            classes.add(cl);
        }

    } catch (SQLException e) {
        System.err.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
        e.printStackTrace();
    }

    return classes;
}

  
    
}

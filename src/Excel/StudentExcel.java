/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class StudentExcel {
    public static void exportToExcel(JTable table) {
        // Mở hộp thoại để chọn nơi lưu file
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
        fileChooser.setDialogTitle("Save As");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xls", "xlsx");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            // Đảm bảo file có đuôi .xlsx
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }
            File file = new File(filePath);

            // Hỏi xác nhận nếu file đã tồn tại
            if (file.exists()) {
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "File đã tồn tại. Bạn có muốn ghi đè không?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            // Kiểm tra nếu JTable không có dữ liệu
            if (table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Không có dữ liệu để xuất.");
                return;
            }

            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                // Tạo một sheet mới
                XSSFSheet sheet = workbook.createSheet("Danh sách");

                // Tạo tiêu đề chính
                Row titleRow = sheet.createRow(0); // Dòng đầu tiên
                Cell titleCell = titleRow.createCell(0); // Ô đầu tiên
                titleCell.setCellValue("Danh sách sinh viên"); // Nội dung tiêu đề

                // Định dạng cho tiêu đề chính
                CellStyle titleStyle = workbook.createCellStyle();
                XSSFFont titleFont = workbook.createFont();
                titleFont.setBold(true); // In đậm
                titleFont.setFontHeightInPoints((short) 18); // Cỡ chữ
                titleStyle.setFont(titleFont);
                titleStyle.setAlignment(HorizontalAlignment.CENTER); // Căn giữa
                titleCell.setCellStyle(titleStyle);

                // Gộp các ô để tiêu đề nằm giữa
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, table.getColumnCount() - 1));

                // Tạo tiêu đề cột (header)
                Row headerRow = sheet.createRow(1); // Dòng thứ hai
                CellStyle headerStyle = workbook.createCellStyle();
                XSSFFont headerFont = workbook.createFont();
                headerFont.setBold(true); // In đậm
                headerStyle.setFont(headerFont);

                // Ghi tên các cột
                for (int col = 0; col < table.getColumnCount(); col++) {
                    Cell headerCell = headerRow.createCell(col);
                    headerCell.setCellValue(table.getColumnName(col)); // Lấy tên cột từ JTable
                    headerCell.setCellStyle(headerStyle); // Áp dụng định dạng
                }

                // Ghi dữ liệu từ JTable vào các dòng Excel
                for (int row = 0; row < table.getRowCount(); row++) {
                    Row dataRow = sheet.createRow(row + 2); // Dòng bắt đầu từ thứ 3
                    for (int col = 0; col < table.getColumnCount(); col++) {
                        Cell cell = dataRow.createCell(col);
                        Object value = table.getValueAt(row, col); // Lấy giá trị từ JTable
                        if (value instanceof Number) {
                            cell.setCellValue(((Number) value).doubleValue()); // Nếu là số
                        } else {
                            cell.setCellValue(value != null ? value.toString() : ""); // Nếu là chuỗi
                        }
                    }
                }

                // Tự động điều chỉnh chiều rộng cột theo nội dung
                for (int col = 0; col < table.getColumnCount(); col++) {
                    sheet.autoSizeColumn(col);
                }

                // Lưu file ra đĩa
                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                    JOptionPane.showMessageDialog(null, "Dữ liệu đã được xuất ra file Excel thành công!");
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi xuất file: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}

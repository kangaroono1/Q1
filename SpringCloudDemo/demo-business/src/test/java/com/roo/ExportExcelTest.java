package com.roo;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExportExcelTest {
    public static void main(String[] args) {
        Workbook workbook = new XSSFWorkbook(); // 创建.xlsx格式的工作簿
        Sheet sheet = workbook.createSheet("Sheet1"); // 创建一个名为"Sheet1"的工作表

        // 创建标题行
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Email");

        // 填充数据
        List<Data> dataList = new ArrayList<>();
        // 假设这里有一些数据被添加到dataList中...
        dataList.add(new Data(1, "Alice", "alice@example.com"));
        dataList.add(new Data(2, "Bob", "bob@example.com"));

        int rowNum = 1; // 从第二行开始填充数据（第一行是标题）
        for (Data data : dataList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(data.getId());
            row.createCell(1).setCellValue(data.getName());
            row.createCell(2).setCellValue(data.getEmail());
        }

        // 写入文件
        try (FileOutputStream outputStream = new FileOutputStream("output.xlsx")) {
            workbook.write(outputStream);
            System.out.println("Excel written successfully");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close(); // 关闭工作簿
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Getter
    @Setter
    static class Data {
        private int id;
        private String name;
        private String email;

        public Data(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        // getters and setters...
    }
}

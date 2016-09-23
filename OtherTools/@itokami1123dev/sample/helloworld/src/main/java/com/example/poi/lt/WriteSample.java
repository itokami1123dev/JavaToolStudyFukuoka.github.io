package com.example.poi.lt;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.Iterator;

public class WriteSample {
    void run() throws IOException, InvalidFormatException {
        System.out.println("resources is " + ClassLoader.getSystemResource("."));
        InputStream is = ClassLoader.getSystemResourceAsStream("Book1.xlsx");
        Workbook workbook = WorkbookFactory.create(is);
        
        // --------------------------------------------
        // シート作成
        // --------------------------------------------
        String safeName = WorkbookUtil.createSafeSheetName("hellosheet");
        Sheet newSheet = workbook.createSheet(safeName);
        
        // --------------------------------------------
        // 行追加
        // --------------------------------------------
        int rowNo = 0; // エクセルの行番号
        Row row = newSheet.createRow(rowNo);

        // --------------------------------------------
        // セル追加
        // --------------------------------------------
        CellStyle rightStyle = workbook.createCellStyle();
        rightStyle.setAlignment(CellStyle.ALIGN_RIGHT);

        Cell cellA = row.createCell(0);
        cellA.setCellStyle(rightStyle);
        cellA.setCellType(Cell.CELL_TYPE_STRING);
        cellA.setCellValue("hoge");

        Cell cellB = row.createCell(1);
        cellB.setCellStyle(rightStyle);
        cellB.setCellType(Cell.CELL_TYPE_NUMERIC);
        cellB.setCellValue(123);


        // --------------------------------------------
        // 保存
        // --------------------------------------------
        OutputStream out = new FileOutputStream("Book2.xlsx");
        workbook.write(out);
    }

}

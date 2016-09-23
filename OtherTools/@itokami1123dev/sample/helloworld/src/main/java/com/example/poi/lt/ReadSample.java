package com.example.poi.lt;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFRow;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.Iterator;

public class ReadSample {
    void run() throws IOException, InvalidFormatException {
        System.out.println("resources is " + ClassLoader.getSystemResource("."));
        InputStream is = ClassLoader.getSystemResourceAsStream("Book1.xlsx");

        Workbook workbook = WorkbookFactory.create(is);

        // --------------------------------------------
        // シート取得
        // --------------------------------------------
        int numOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            System.out.printf("\n %d/%d => %s \n", i, numOfSheets, sheet.getSheetName());
        }
        Sheet sheet = workbook.getSheet("シート1");
        System.out.printf("\n %s の名前のシートを取得 \n", sheet.getSheetName());


        // --------------------------------------------
        System.out.println("\n--\n--\n--");

        // --------------------------------------------
        // セルの読み込み
        // --------------------------------------------
        int numOfRows = sheet.getLastRowNum();
        System.out.printf("\n %d 行のデータ \n", numOfRows);

        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String colNm = CellReference.convertNumToColString(cell.getColumnIndex());
//                System.out.printf("cell info columnName=%s type=%d\n", colNm, cell.getCellType());
            }

            String date = getVal(row.getCell(0)); // A
            String name = getVal(row.getCell(1)); // B
            String unitPrice = getVal(row.getCell(2)); // C
            String num = getVal(row.getCell(3)); // D
            String price = getVal(row.getCell(4)); // E

            System.out.printf(" %s,  %s,  %s,  %s,  %s\n", date, name, unitPrice, num, price);
        }
    }


    private String getVal(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: // 数値, 日付も
                return getNumOrDate(cell);

            case Cell.CELL_TYPE_FORMULA: // 関数
                //return cell.getCellFormula();
                CreationHelper hlp = cell.getSheet().getWorkbook().getCreationHelper();
                FormulaEvaluator ev = hlp.createFormulaEvaluator();
                return getVal(ev.evaluateInCell(cell));

            case Cell.CELL_TYPE_BOOLEAN: // 真偽
                return String.valueOf(cell.getBooleanCellValue());

            case Cell.CELL_TYPE_STRING: // 文字列
                return cell.getStringCellValue();

            case Cell.CELL_TYPE_BLANK: // 空
                break;
            default:
                break;
        }

        return "";
    }

    private String getNumOrDate(Cell cell) {
        if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
            Date dt = cell.getDateCellValue();
            ZonedDateTime zdt = ZonedDateTime.ofInstant(dt.toInstant(), ZoneId.systemDefault());
            final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("uuuu/MM/dd").withResolverStyle(ResolverStyle.STRICT);
            return zdt.format(YYYY_MM_DD);
        }
        return String.valueOf(cell.getNumericCellValue());
    }
}

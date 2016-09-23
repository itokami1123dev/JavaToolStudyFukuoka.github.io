package com.example.poi.lt;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;

public class App {

    public static void main(String... args) {
        // Thread.currentThread().getContextClassLoader().getResourceAsStream()
        try {
            // InputStream file = ClassLoader.getSystemResourceAsStream("res.txt");
            System.out.println("resources is " + ClassLoader.getSystemResource("."));
            InputStream is = ClassLoader.getSystemResourceAsStream("Book1.xlsx");
            // InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("Book1.xlsx");
//            InputStream is = new FileInputStream("/home/itokami1123/Book1.xlsx");
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);
            System.out.println(sheet.getSheetName());

            System.out.println("\n\n\n\n--- ReadSample ---");
            (new ReadSample()).run();

            System.out.println("\n\n\n\n--- WriteSample ---");
            (new WriteSample()).run();
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

    }
}

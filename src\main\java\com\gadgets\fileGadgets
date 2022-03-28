package com.gadgets.fileGadgets;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtil {

    private static Map<String, String> sheet1Map = new HashMap<>();
    private static Map<String, String> ipNameMap = new HashMap<>();
    private static String NONE = "none";

    static Sheet getSheetFromFile(String path, int sheetNum) {
        try {
            System.out.println(path);
            FileInputStream is = new FileInputStream(new File(path));
            Workbook wb = WorkbookFactory.create(is);
            Sheet sheet = wb.getSheetAt(sheetNum);
            return sheet;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static Workbook getWorkbookFromFile(String path) {
        try {
            System.out.println(path);
            FileInputStream is = new FileInputStream(new File(path));
            Workbook wb = WorkbookFactory.create(is);
            return wb;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
    }

    public static void writeToExcel(Workbook wb, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            wb.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setCellValue(Row row, CellStyle tableheadStyle, int cellNum, String cellContent) {
        Cell cell = row.createCell(cellNum);
        if (null != tableheadStyle) cell.setCellStyle(tableheadStyle);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(cellContent);
    }

    public static String getStringCellValue(Cell cell) {
        if (cell == null) return null;
        cell.setCellType(Cell.CELL_TYPE_STRING);
        return cell.getStringCellValue();
    }

    public static String getCellFromRow(Row row, int num) {
        if (row == null) return null;
        Cell cell = row.getCell(num);
        if (cell == null) return null;
        cell.setCellType(Cell.CELL_TYPE_STRING);
        return cell.getStringCellValue();
    }

    public static Row getRow(Sheet sheet, int num) {
        Row row = sheet.getRow(num);
        if (null == row) {
            row = sheet.createRow(num);
        }
        return row;
    }
}

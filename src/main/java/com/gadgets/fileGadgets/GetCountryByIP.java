package com.gadgets.fileGadgets;

import com.gadgets.fileGadgets.utils.IPMaxMind;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class GetCountryByIP {

    private static void analysisSheet(Workbook readwb, Workbook writewb) {
        Sheet sheet = readwb.getSheetAt(0);
        Sheet writesheet = writewb.getSheetAt(0);
        int writeStartLine = writesheet.getLastRowNum();
        try {
            int i;
            for (i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String ip = ExcelUtil.getCellFromRow(row, 4);
                String[] data = IPMaxMind.find(ip);
                ExcelUtil.setCellValue(row, null, 13, String.valueOf(data[0]));
                System.out.println(i+".ip:" + ip+",国家："+data[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String writepath = "D:\\工作目录\\REF日本预约ip地址所属国家追踪\\Appointment_RJP_2022-05-07 13_56_03.xls";
        String readpath = "D:\\工作目录\\REF日本预约ip地址所属国家追踪\\Appointment_RJP_2022-05-07 13_56_03.xls";
        Workbook writewb = ExcelUtil.getWorkbookFromFile(writepath);
        Workbook readwb = ExcelUtil.getWorkbookFromFile(readpath);
        analysisSheet(readwb,readwb);
        ExcelUtil.writeToExcel(readwb, readpath);

    }
}

package com.gadgets.fileGadgets;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.HashMap;
import java.util.Map;

public class WorkHourCalculater {
    public static Map<String, Float> nameAndHourMap = new HashMap<>();

    public static void monthWorkHourCalculate(String workHourStr) {
        String[] workHourList = workHourStr.split("\\n");
        float sum = 0;
        for (String workHour : workHourList) {
//            System.out.println(workHour);
            workHour = workHour.trim();
            String[] nameAndHour;
            if (workHour.contains(":")) {
                nameAndHour = workHour.split(":");
            } else if (workHour.contains(" ")) {
                nameAndHour = workHour.split(" ");
            } else if (workHour.contains("：")) {
                nameAndHour = workHour.split("：");
            } else {
                System.out.println("workHour split error!");
                continue;
            }
            String name = nameAndHour[0];
            float hour = Float.parseFloat(nameAndHour[1]);
            if (!nameAndHourMap.containsKey(name)) {
                nameAndHourMap.put(name, hour);
            } else {
                nameAndHourMap.put(name, nameAndHourMap.get(name) + hour);
            }
            sum += hour;
        }
        if (sum != 4) {
            System.out.println("sum hour is not 4!");
        }
        StringBuilder sb = new StringBuilder();
        float sump = 0;
        for (Map.Entry<String, Float> nameAndHour : nameAndHourMap.entrySet()) {
            String name = nameAndHour.getKey();
            float hour = nameAndHour.getValue();
            float hourp = hour / 4;
            sb.append(hourp).append("+");
            sump+=hourp;
            System.out.println(name + ":" + hourp + "(" + hour + ")");
        }
        sb.append("=").append(sump);
        System.out.println(sb.toString());
    }

    public static void weekWorkHourCalculate(String path) {
        Workbook readwb = ExcelUtil.getWorkbookFromFile(path);
        Sheet sheet = readwb.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            String type = ExcelUtil.getCellFromRow(row, 2);
        }
    }

    public static void main(String[] args) {
        String workHourStr = "Keel发行：0.38\n" +
                "Keel发行：0.35\n" +
                "Sigmar全球：0.05\n" +
                "REF日本：0.06\n" +
                "REF日本：0.05\n" +
                "闪名新马：0.05\n" +
                "龙族东南亚：0.06\n" +
                "Keel：0.08\n" +
                "Keel发行：0.55\n" +
                "Sigmar：0.17\n" +
                "战舰全球：0.1\n" +
                "迷失童话：0.1\n" +
                "REF欧美：0.63\n" +
                "Keel：0.17\n" +
                "Keel发行：0.2 \n" +
                "梦想东南亚：0.02\n" +
                "迷失童话：0.25\n" +
                "REF欧美：0.46\n" +
                "Keel发行：0.1 \n" +
                "闪名国内：0.04\n" +
                "梦想港台：0.13";

        monthWorkHourCalculate(workHourStr);
    }
}

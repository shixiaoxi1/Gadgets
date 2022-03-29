package com.gadgets.fileGadgets;

import java.util.HashMap;
import java.util.Map;

public class WorkHourCalculater {
    public static Map<String, Float> nameAndHourMap = new HashMap<>();

    public static void workHourCalculate(String workHourStr) {
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
        for (Map.Entry<String, Float> nameAndHour : nameAndHourMap.entrySet()) {
            String name = nameAndHour.getKey();
            float hour = nameAndHour.getValue();
            System.out.println(name + ":" + hour / 4 + "(" + hour + ")");
        }
    }

    public static void main(String[] args) {
        String workHourStr = "Keel发行：0.55\n" +
                "闪名新马：0.2\n" +
                "Keel：0.1 \n" +
                "Keel发行：0.15\n" +
                "鸿图东南亚：0.15\n" +
                "Keel发行：0.78\n" +
                "闪名港台：0.02\n" +
                "Keel：0.05 \n" +
                "鸿图东南亚：0.77\n" +
                "鸿图港台：0.05\n" +
                "Keel：0.15 \n" +
                "闪名东南亚:0.03\n" +
                "梦想东南亚：0.45\n" +
                "鸿图港台：0.02\n" +
                "Keel：0.2 \n" +
                "闪名东南亚：0.08\n" +
                "sigmar:0.13\n" +
                "梦想港台：0.12";

        workHourCalculate(workHourStr);
    }
}

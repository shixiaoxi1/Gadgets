package com.gadgets.fileGadgets;

import com.gadgets.fileGadgets.demos.AbstractTextSecurityApi;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.util.*;

public class TextSecurityApiTest {

    private static void analysisSheet1(Workbook wb, Workbook writewb, String fileName) {
        Sheet sheet = wb.getSheetAt(0);
        Sheet writesheet = writewb.getSheetAt(0);
        int writeStartLine = writesheet.getLastRowNum();
        try {
            int i;
            for (i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String type = ExcelUtil.getCellFromRow(row, 2);
                assert type != null;
                if (type.contains("-")) {
                    type = type.split("-")[0];
                } else if (type.contains(":")) {
                    type = type.split(":")[0];
                }

                String content = ExcelUtil.getCellFromRow(row, 1);

                int writeLine = writeStartLine + i;
                Row writerow = writesheet.createRow(writeLine);

                ExcelUtil.setCellValue(writerow, null, 0, String.valueOf(writeLine));
                ExcelUtil.setCellValue(writerow, null, 1, String.valueOf(fileName));
                ExcelUtil.setCellValue(writerow, null, 2, String.valueOf(content));
                ExcelUtil.setCellValue(writerow, null, 3, String.valueOf(type));
            }
            System.out.println("文件行数:" + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void excelPretreatment(String writepath, String[] readpaths) {
        Workbook writewb = ExcelUtil.getWorkbookFromFile(writepath);
        if (null == writewb) return;
        for (String readpath : readpaths) {
            String fileName = readpath.split("-")[1].split("\\.")[0];
            Workbook wb = ExcelUtil.getWorkbookFromFile(readpath);
            if (null == wb) return;
            analysisSheet1(wb, writewb, fileName);
            ExcelUtil.writeToExcel(writewb, writepath);
        }
    }

    private static String textCheck(Class<? extends AbstractTextSecurityApi> abstractTextSecurityApi, String content) throws
            Exception {
        AbstractTextSecurityApi textSecurityApi = abstractTextSecurityApi.newInstance();
        String result = textSecurityApi.doCheck(content);
        return result;
    }

    private static void checkByTextSecurityApi(String className, String path, int num) {
        try {
            Class<? extends AbstractTextSecurityApi> abstractTextSecurityApi = (Class<? extends AbstractTextSecurityApi>) Class.forName(className);
            Workbook wb = ExcelUtil.getWorkbookFromFile(path);
            assert wb != null;
            Sheet sheet = wb.getSheetAt(0);
            int maxLineNum = Objects.requireNonNull(sheet).getLastRowNum();
//            int maxLineNum = 10;
            for (int i = 1; i <= maxLineNum; i++) {
                Row row = sheet.getRow(i);
                String content = ExcelUtil.getCellFromRow(row, 2);
                String result = textCheck(abstractTextSecurityApi, content);
                ExcelUtil.setCellValue(row, null, num, result);
                System.out.println(i + ".【content】:" + content + ",【result】:" + result);
            }
            ExcelUtil.writeToExcel(wb, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void resultAnalysis(String filePath, String apiName, int predictColNum, int anwserColNum, int resultColNum) {
        Workbook wb = ExcelUtil.getWorkbookFromFile(filePath);
        assert wb != null;
        Sheet sheet = wb.getSheetAt(0);
        int maxLineNum = Objects.requireNonNull(sheet).getLastRowNum();
//            int maxLineNum = 10;
        Map<String, Integer> tn = new HashMap<>();
        Map<String, Integer> fn = new HashMap<>();
        Map<String, Integer> tp = new HashMap<>();
        Map<String, Integer> fp = new HashMap<>();
        List<String> dataSourceList = new ArrayList<>();
        for (int i = 1; i <= maxLineNum; i++) {
            Row row = sheet.getRow(i);
            String predict = ExcelUtil.getCellFromRow(row, predictColNum);
            String anwser = ExcelUtil.getCellFromRow(row, anwserColNum);
            // 数据来源公司（数美，易盾）
            String dataSource = ExcelUtil.getCellFromRow(row, 1);
            if (!dataSourceList.contains(dataSource)) dataSourceList.add(dataSource);
            String result;
            // 正常文本为负样本
            if ("正常".equals(anwser)) {
                result = "N";
                if (anwser.equals(predict)) {
                    result = "T" + result;
                    if (tn.containsKey(dataSource)) {
                        tn.put(dataSource, tn.get(dataSource) + 1);
                    } else {
                        tn.put(dataSource, 1);
                    }
                } else {
                    result = "F" + result;
                    if (fn.containsKey(dataSource)) {
                        fn.put(dataSource, fn.get(dataSource) + 1);
                    } else {
                        fn.put(dataSource, 1);
                    }
                }
                // 风险文本为正样本
            } else {
                result = "P";
                assert anwser != null;
                String[] anwsers = anwser.split(",");
                Set<String> anwserSet = new HashSet<>(Arrays.asList(anwsers));
                if (anwserSet.contains(predict)) {
                    result = "T" + result;
                    if (tp.containsKey(dataSource)) {
                        tp.put(dataSource, tp.get(dataSource) + 1);
                    } else {
                        tp.put(dataSource, 1);
                    }
                } else {
                    result = "F" + result;
                    if (fp.containsKey(dataSource)) {
                        fp.put(dataSource, fp.get(dataSource) + 1);
                    } else {
                        fp.put(dataSource, 1);
                    }
                }
            }
            ExcelUtil.setCellValue(row, null, resultColNum, result);
            System.out.println(i + ".【result】:" + result);
        }
        int TN = 0, TP = 0, FN = 0, FP = 0, TNSUM = 0, TPSUM = 0, FNSUM = 0, FPSUM = 0, dataSourceNum = 0;
        sheet = wb.getSheetAt(1);
        if (!StringUtils.isEmpty(apiName)) {
            Row row = ExcelUtil.getRow(sheet, dataSourceNum++);
            ExcelUtil.setCellValue(row, null, resultColNum, apiName);
        } else {
            dataSourceNum++;
        }
        for (int i = 0; i <= dataSourceList.size(); i++) {
            String dataSource = "全部";
            if (i == dataSourceList.size()) {
                TN = TNSUM;
                TP = TPSUM;
                FN = FNSUM;
                FP = FPSUM;
            } else {
                dataSource = dataSourceList.get(i);
                TN = tn.getOrDefault(dataSource, 0);
                TP = tp.getOrDefault(dataSource, 0);
                FN = fn.getOrDefault(dataSource, 0);
                FP = fp.getOrDefault(dataSource, 0);
            }
            float accuracy = (float) (TN + TP) / (TN + FN + TP + FP) * 100;
            float precision = (float) (TP) / (TP + FP) * 100;
            float recall = (float) (TP) / (FN + TP) * 100;
            System.out.println("【" + dataSource + "】【TN=" + TN + "】【TP=" + TP + "】【FN=" + FN + "】【FP=" + FP + "】");
            // 预测和实际结果一样的/所有
            String accuracyStr = "【" + dataSource + "】【准确率(accuracy)】:" + accuracy + "%";
            // 预测是风险文本实际检测出来也是风险文本的/所有实际检测出来是风险文本的
            String precisionStr = "【" + dataSource + "】【精确率(precision)】:" + precision + "%";
            // 预测是风险文本实际检测出来也是风险文本的/预测是风险文本的
            String recallStr = "【" + dataSource + "】【召回率(recall)】:" + recall + "%";

            Row row = ExcelUtil.getRow(sheet, dataSourceNum++);
            System.out.println(accuracyStr);
            ExcelUtil.setCellValue(row, null, resultColNum, accuracyStr);

            row = ExcelUtil.getRow(sheet, dataSourceNum++);
            System.out.println(precisionStr);
            ExcelUtil.setCellValue(row, null, resultColNum, precisionStr);

            row = ExcelUtil.getRow(sheet, dataSourceNum++);
            System.out.println(recallStr);
            ExcelUtil.setCellValue(row, null, resultColNum, recallStr);
            TNSUM += TN;
            TPSUM += TP;
            FNSUM += FN;
            FPSUM += FP;
        }
        ExcelUtil.writeToExcel(wb, filePath);
    }

    public static void main(String[] args) {
        String writepath = "C:\\Users\\Administrator\\Desktop\\文件\\文本检测测试数据\\集合.xlsx";
        String[] readpaths = new String[]{"C:\\Users\\Administrator\\Desktop\\文件\\文本检测测试数据\\祖龙测试数据样本-易盾.xlsx",
                "C:\\Users\\Administrator\\Desktop\\文件\\文本检测测试数据\\祖龙游戏测试样本-数美.xlsx"};
//        excelPretreatment(writepath, readpaths);

//        String className = "com.gadgets.fileGadgets.demos.YiDunApiDemo.YiDunTextSecurityApi";
//        checkByTextSecurityApi(className, writepath, 4);
        resultAnalysis(writepath, "【易盾文本检测结果分析】", 3, 4, 5);

//        String className = "com.gadgets.fileGadgets.demos.shumeiApiDemo.ShuMeiTextSecurityApi";
//        checkByTextSecurityApi(className, writepath, 6);
        resultAnalysis(writepath, "【数美文本检测结果分析】", 3, 6, 7);

    }
}

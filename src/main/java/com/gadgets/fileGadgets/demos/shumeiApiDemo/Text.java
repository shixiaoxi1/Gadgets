package com.gadgets.fileGadgets.demos.shumeiApiDemo;


import com.gadgets.fileGadgets.utils.HttpClient4Utils;
import com.gadgets.fileGadgets.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.client.HttpClient;

import java.util.HashMap;

public class Text {
    private static final String URL = "http://api-text-bj.fengkongcloud.com/text/v4";
    private static final String ACCESS_KEY = "oaatvntY1hpeWkEgg1qt";
    private static HttpClient httpClient = HttpClient4Utils.createHttpClient(100, 20, 2000, 2000, 2000);

    public static void main(String[] args) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("accessKey", ACCESS_KEY);
        payload.put("appId", "default");
        payload.put("eventId", "groupChat");
        payload.put("type", "ALL");

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("text", "想把小穴塞满 嘿嘿嘿想把姐姐里面射满 嘿嘿嘿 sb测试测试啊啊啊毛泽东");
        data.put("tokenId", "zulong");
        payload.put("data", data);

        String strParam = JsonUtil.TransToJsonNoException(payload);
        String response = HttpClient4Utils.invokePost(httpClient, URL, strParam, Consts.UTF_8,10000,10000);

        System.out.println(decodeUnicode(response));
    }


    /*
     * unicode编码转中文
     */
    public static String decodeUnicode(final String dataStr) {
        try {
            final StringBuffer buffer = new StringBuffer(dataStr == null ? "" : dataStr);
            if (StringUtils.isNotBlank(dataStr) && dataStr.contains("\\u")) {
                buffer.delete(0, buffer.length());
                int start = 0;
                int end;
                while (start > -1) {
                    end = dataStr.indexOf("\\u", start);
                    String charStr = "";
                    if (end == -1) {
                        buffer.append(dataStr.substring(start));
                    } else {
                        buffer.append(dataStr, start, end);
                        start = end + 2;
                        end = end + 6;
                        charStr = dataStr.substring(start, end);
                        char letter = (char) Integer.parseInt(charStr.trim(), 16); // 16进制parse整形字符串。
                        buffer.append(letter);
                    }
                    start = end;
                }
            }
            return buffer.toString();
        } catch (Exception e) {
            System.out.println(dataStr + " 字符串转换失败:" + e);
        }
        return dataStr;
    }
}
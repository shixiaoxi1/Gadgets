/*
 * @(#) TextCheckAPIDemo.java 2016年2月3日
 *
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package com.gadgets.fileGadgets.demos.YiDunApiDemo;

import com.gadgets.fileGadgets.demos.AbstractTextSecurityApi;
import com.gadgets.fileGadgets.utils.JsonUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gadgets.fileGadgets.utils.HttpClient4Utils;
import com.gadgets.fileGadgets.utils.SignatureUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.client.HttpClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 调用易盾反垃圾云服务文本V5在线检测接口API示例，该示例依赖以下jar包：
 * 1. httpclient，用于发送http请求
 * 2. commons-codec，使用md5算法生成签名信息，详细见SignatureUtils.java
 * 3. gson，用于做json解析
 *
 * @author yidun
 * @version 2021年08月31日
 */
public class YiDunTextSecurityApi extends AbstractTextSecurityApi {
    /**
     * 产品密钥ID，产品标识
     */
    private final static String SECRETID = "23293896f48abee6bd2d05f5bad1de1e";
    /**
     * 产品私有密钥，服务端生成签名信息使用，请严格保管，避免泄露
     */
    private final static String SECRETKEY = "d72e76024666bb5e13d005c8293aa2fb";
    /**
     * 业务ID，易盾根据产品业务特点分配
     */
    private final static String BUSINESSID = "80e054314e892583b8e9f9db286c60ee";
    /**
     * 易盾反垃圾云服务文本在线检测接口地址
     */
    private final static String API_URL = "http://as.dun.163.com/v5/text/check";
    /**
     * 实例化HttpClient，发送http请求使用，可根据需要自行调参
     */
    private static final int RESP_CODE_SUCCESS = 200;
    private static HttpClient httpClient = HttpClient4Utils.createHttpClient(100, 20, 2000, 2000, 2000);

    private static HashMap<Integer, String> labelMap = new HashMap<>();

    static {
        labelMap.put(100, "色情");
        labelMap.put(200, "广告");
        labelMap.put(260, "广告法");
        labelMap.put(300, "暴恐");
        labelMap.put(400, "违禁");
        labelMap.put(500, "涉政");
        labelMap.put(600, "辱骂");
        labelMap.put(700, "灌水");
        labelMap.put(900, "其他");
        labelMap.put(1100, "涉价值观");
    }

    @Override
    public String doCheck(String content) {
        String response = sendRequest(content);
        String result = parseResponse(response);
        return result;
    }

    private static String sendRequest(String content) {
        try {
            Map<String, String> params = new HashMap<>();
            // 1.设置公共参数
            params.put("secretId", SECRETID);
            params.put("businessId", BUSINESSID);
            params.put("version", "v5");
            params.put("timestamp", String.valueOf(System.currentTimeMillis()));
            params.put("nonce", String.valueOf(new Random().nextInt()));

            // 2.设置私有参数
            String dataId = RandomStringUtils.randomAlphanumeric(20);
            params.put("dataId", dataId);
            params.put("content", content);

            // 3.生成签名信息
            String signature = SignatureUtils.genSignature(SECRETKEY, params);
            params.put("signature", signature);
            // 4.发送HTTP请求，这里使用的是HttpClient工具包，产品可自行选择自己熟悉的工具包发送请求
            String response = HttpClient4Utils.sendPost(httpClient, API_URL, params, Consts.UTF_8);
//            System.out.println("sendRequest:ok,Response=" + response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private String parseResponse(String strResponse) {
        StringBuilder resultSb = new StringBuilder();
        try {
            if (StringUtils.isEmpty(strResponse)) {
                resultSb = new StringBuilder("parseResponse:Exception,strResponse is Empty");
                System.out.println(resultSb);
                return resultSb.toString();
            }
            YiDunTextResponse response = JsonUtil.TransToObject(strResponse, YiDunTextResponse.class);
            // 如果返回码不是成功，则返回错误码
            if (response.getCode() != RESP_CODE_SUCCESS) {
                resultSb = new StringBuilder("parseResponse:response error,Code=" + response.getCode() + ",Message=" + response.getMsg());
                System.out.println(resultSb);
                return resultSb.toString();
            }
            YiDunTextResponse.Antispam antispam = response.getResult().getAntispam();

            List<YiDunTextResponse.Labels> labels = antispam.getLabels();
            // 如果没有识别到敏感词返回PASS
            if (null == labels || labels.size() == 0) {
                resultSb = new StringBuilder(("正常"));
                return resultSb.toString();
            }
            // 循环检测出的标签
            for (YiDunTextResponse.Labels label : labels) {
                int l = label.getLabel();
                List<YiDunTextResponse.SubLabels> sbl = label.getSubLabels();
                if (null != sbl && sbl.size() > 0 && labelMap.containsKey(l)) {
                    resultSb.append(labelMap.get(l)).append(",");
                }
            }
            String result;
            if (resultSb.toString().endsWith(",")) {
                result = resultSb.toString().substring(0, resultSb.toString().length() - 1);
            } else {
                result = resultSb.toString();
            }
            return result;
        } catch (Exception e) {
            resultSb = new StringBuilder("parseResponse:Exception,resp=" + strResponse + ",e=" + e);
            System.out.println(resultSb);
            return resultSb.toString();
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        // 1.设置公共参数
        params.put("secretId", SECRETID);
        params.put("businessId", BUSINESSID);
        params.put("version", "v5");
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("nonce", String.valueOf(new Random().nextInt()));
        params.put("signatureMethod", "MD5"); // MD5, SM3, SHA1, SHA256

        // 2.设置私有参数
        params.put("dataId", "ebfcad1c-dba1-490c-b4de-e784c2691768");
        params.put("content", "ping guo dai chong 毛泽东 xijinping");
        // params.put("dataType", "1");
        // params.put("ip", "123.115.77.137");
        // params.put("account", "java@163.com");
        // params.put("deviceType", "4");
        // params.put("deviceId", "92B1E5AA-4C3D-4565-A8C2-86E297055088");
        // params.put("callback", "ebfcad1c-dba1-490c-b4de-e784c2691768");
        // params.put("publishTime", String.valueOf(System.currentTimeMillis()));
        // 主动回调地址url,如果设置了则走主动回调逻辑
        // params.put("callbackUrl", "http://***");

        // 3.生成签名信息
        String signature = SignatureUtils.genSignature(SECRETKEY, params);
        params.put("signature", signature);

        // 4.发送HTTP请求，这里使用的是HttpClient工具包，产品可自行选择自己熟悉的工具包发送请求
        String response = HttpClient4Utils.sendPost(httpClient, API_URL, params, Consts.UTF_8);

        // 5.解析接口返回值
        JsonObject jObject = new JsonParser().parse(response).getAsJsonObject();
        int code = jObject.get("code").getAsInt();
        String msg = jObject.get("msg").getAsString();
        if (code == 200) {
            if (jObject.has("result")) {
                JsonObject resultObject = jObject.getAsJsonObject("result");
                // 内容安全结果
                if (resultObject.has("antispam")) {
                    JsonObject antispam = resultObject.getAsJsonObject("antispam");
                    if (antispam != null) {
                        String taskId = antispam.get("taskId").getAsString();
                        String dataId = antispam.get("dataId").getAsString();
                        int suggestion = antispam.get("suggestion").getAsInt();
                        int resultType = antispam.get("resultType").getAsInt();
                        int censorType = antispam.get("censorType").getAsInt();
                        boolean isRelatedHit = antispam.get("isRelatedHit").getAsBoolean();
                        JsonArray labels = antispam.get("labels").getAsJsonArray();
                        System.out.println(String.format("内容安全结果，taskId: %s，dataId: %s，suggestion: %s", taskId, dataId,
                                suggestion));
                        for (JsonElement labelElement : labels) {
                            JsonObject labelItem = labelElement.getAsJsonObject();
                            int label = labelItem.get("label").getAsInt();
                            int level = labelItem.get("level").getAsInt();
                            JsonArray subLabels = labelItem.get("subLabels").getAsJsonArray();
                            if (subLabels != null && subLabels.size() > 0) {
                                for (JsonElement subLabelElement : subLabels) {
                                    JsonObject subLabelItem = subLabelElement.getAsJsonObject();
                                    String subLabel = subLabelItem.get("subLabel").getAsString();
                                    System.out.println(String.format("内容安全分类，label: %s，subLabel: %s", label, subLabel));
                                    if (subLabelItem.has("details")) {
                                        JsonObject details = subLabelItem.get("details").getAsJsonObject();
                                        // 自定义敏感词信息
                                        if (details.has("keywords")) {
                                            JsonArray keywords = details.get("keywords").getAsJsonArray();
                                            if (keywords != null && keywords.size() > 0) {
                                                for (JsonElement keywordElement : keywords) {
                                                    JsonObject keywordItem = keywordElement.getAsJsonObject();
                                                    String word = keywordItem.get("word").getAsString();
                                                }
                                            }
                                        }
                                        // 自定义名单库信息
                                        if (details.has("libInfos")) {
                                            JsonArray libInfos = details.get("libInfos").getAsJsonArray();
                                            if (libInfos != null && libInfos.size() > 0) {
                                                for (JsonElement libInfoElement : libInfos) {
                                                    JsonObject libInfoItem = libInfoElement.getAsJsonObject();
                                                    int type = libInfoItem.get("type").getAsInt();
                                                    String entity = libInfoItem.get("entity").getAsString();
                                                }
                                            }
                                        }
                                        // 线索信息
                                        if (details.has("hitInfos")) {
                                            JsonArray hitInfos = details.get("hitInfos").getAsJsonArray();
                                            if (hitInfos != null && hitInfos.size() > 0) {
                                                for (JsonElement hitInfoElement : hitInfos) {
                                                    JsonObject hitInfoItem = hitInfoElement.getAsJsonObject();
                                                    String value = hitInfoItem.get("value").getAsString();
                                                    JsonArray positions = hitInfoItem.get("positions").getAsJsonArray();
                                                    if (positions != null && positions.size() > 0) {
                                                        for (JsonElement positionElement : positions) {
                                                            JsonObject positionItem = positionElement.getAsJsonObject();
                                                            String fieldName = positionItem.get("fieldName").getAsString();
                                                            int startPos = positionItem.get("startPos").getAsInt();
                                                            int endPos = positionItem.get("endPos").getAsInt();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        // 反作弊信息
                                        if (details.has("anticheat")) {
                                            JsonObject anticheat = details.get("anticheat").getAsJsonObject();
                                            if (anticheat != null) {
                                                int type = anticheat.get("type").getAsInt();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // 情感分析结果
                if (resultObject.has("emotionAnalysis")) {
                    JsonObject emotionAnalysis = resultObject.getAsJsonObject("emotionAnalysis");
                    if (emotionAnalysis != null) {
                        String taskId = emotionAnalysis.get("taskId").getAsString();
                        String dataId = emotionAnalysis.get("dataId").getAsString();
                        if (emotionAnalysis.has("details")) {
                            JsonArray details = emotionAnalysis.get("details").getAsJsonArray();
                            System.out.println(String.format("情感分析结果，taskId: %s，dataId: %s，details: %s", taskId, dataId,
                                    details));
                            if (details != null && details.size() > 0) {
                                for (JsonElement detailElement : details) {
                                    JsonObject detailItem = detailElement.getAsJsonObject();
                                    double positiveProb = detailItem.get("positiveProb").getAsDouble();
                                    double negativeProb = detailItem.get("negativeProb").getAsDouble();
                                    String sentiment = detailItem.get("sentiment").getAsString();
                                }
                            }
                        }
                    }
                }

                // 反作弊结果
                if (resultObject.has("anticheat")) {
                    JsonObject anticheat = resultObject.getAsJsonObject("anticheat");
                    if (anticheat != null) {
                        String taskId = anticheat.get("taskId").getAsString();
                        String dataId = anticheat.get("dataId").getAsString();
                        if (anticheat.has("details")) {
                            JsonArray details = anticheat.get("details").getAsJsonArray();
                            System.out.println(String.format("反作弊结果，taskId: %s，dataId: %s，details: %s", taskId, dataId,
                                    details));
                            if (details != null && details.size() > 0) {
                                for (JsonElement detailElement : details) {
                                    JsonObject detailItem = detailElement.getAsJsonObject();
                                    int suggestion = detailItem.get("suggestion").getAsInt();
                                    JsonArray hitInfos = detailItem.get("hitInfos").getAsJsonArray();
                                    if (hitInfos != null && hitInfos.size() > 0) {
                                        for (JsonElement hitInfoElement : hitInfos) {
                                            JsonObject hitInfoItem = hitInfoElement.getAsJsonObject();
                                            int hitType = hitInfoItem.get("hitType").getAsInt();
                                            String hitMsg = hitInfoItem.get("hitMsg").getAsString();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // 用户画像结果
                if (resultObject.has("userRisk")) {
                    JsonObject userRisk = resultObject.getAsJsonObject("userRisk");
                    if (userRisk != null) {
                        String taskId = userRisk.get("taskId").getAsString();
                        String dataId = userRisk.get("dataId").getAsString();
                        if (userRisk.has("details")) {
                            JsonArray details = userRisk.get("details").getAsJsonArray();
                            System.out.println(String.format("用户画像结果，taskId: %s，dataId: %s，details: %s", taskId, dataId,
                                    details));
                            if (details != null && details.size() > 0) {
                                for (JsonElement detailElement : details) {
                                    JsonObject detailItem = detailElement.getAsJsonObject();
                                    String account = detailItem.get("account").getAsString();
                                    int accountLevel = detailItem.get("accountLevel").getAsInt();
                                }
                            }
                        }
                    }
                }

                // 语种检测结果
                if (resultObject.has("language")) {
                    JsonObject language = resultObject.getAsJsonObject("language");
                    if (language != null) {
                        String taskId = language.get("taskId").getAsString();
                        String dataId = language.get("dataId").getAsString();
                        if (language.has("details")) {
                            JsonArray details = language.get("details").getAsJsonArray();
                            System.out.println(String.format("语种检测结果，taskId: %s，dataId: %s，details: %s", taskId, dataId,
                                    details));
                            if (details != null && details.size() > 0) {
                                for (JsonElement detailElement : details) {
                                    JsonObject detailItem = detailElement.getAsJsonObject();
                                    String type = detailItem.get("type").getAsString();
                                }
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println(String.format("ERROR: code=%s, msg=%s", code, msg));
        }
    }
}

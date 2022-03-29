package com.gadgets.fileGadgets.demos.shumeiApiDemo;

import com.gadgets.fileGadgets.demos.AbstractTextSecurityApi;
import com.gadgets.fileGadgets.demos.shumeiApiDemo.beans.AllLabelBean;
import com.gadgets.fileGadgets.demos.shumeiApiDemo.beans.RiskDetailBean;
import com.gadgets.fileGadgets.demos.shumeiApiDemo.beans.ShuMeiTextResponse;
import com.gadgets.fileGadgets.utils.HttpClient4Utils;
import com.gadgets.fileGadgets.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 腾讯云 【数美 天净-只能文本识别】接口
 * 控制台地址：https://console.ishumei.com/
 * 文档地址：https://console.ishumei.com/new/document
 */
public class ShuMeiTextSecurityApi extends AbstractTextSecurityApi {

    private static final Logger logger = LoggerFactory.getLogger(ShuMeiTextSecurityApi.class);

    private static String URL = "http://api-text-bj.fengkongcloud.com/text/v4";
    //接口认证密钥 由数美提供
    private static String accessKey = "oaatvntY1hpeWkEgg1qt";
    // 应用标识 用于区分应用，可选值如下： default：默认应用
    private static String appId = "default";
    // 事件标识 可选值如下： 论坛：comment 群聊：groupChat 私聊：message 昵称：nickname
    private static String eventId = "groupChat";
    // 检测的风险类型 可选值： DEFAULT：默认值（包含： 涉政、暴恐、违禁、色情、辱 骂、广告、灌水、无意义、隐 私、广告法、黑名单） FRUAD：网络诈骗 UNPOACH：高价值用户防挖
    // 以上type可以下划线组合， 如：DEFAULT_FRUA
    private static String type = "ALL";
    // 用户账号标识 建议使用贵司用户UID（可加 密）自行生成 , 标识用户唯一 身份用作灌水和广告等行为维 度风控。
    // 如无用户uid的场景建议使用 唯一的数据标识传值
    private static String tokenId = "zulong";

    private static final int RESP_CODE_SUCCESS = 1100;
    private static final String RISK_LEVEL_PASS = "PASS";
    private static final HttpClient httpClient = HttpClient4Utils.createHttpClient(100, 20, 2000, 2000, 2000);


    private static HashMap<String, String> labelMap = new HashMap<>();

    static {
        labelMap.put("porn", "色情");
        labelMap.put("ad", "广告");
        labelMap.put("ad_law", "广告法");
        labelMap.put("violence", "暴恐");
        labelMap.put("ban", "违禁");
        labelMap.put("politics", "涉政");
        labelMap.put("abuse", "辱骂");
        labelMap.put("spam", "灌水");
        labelMap.put("minor", "未成年");
        labelMap.put("blacklist", "黑名单");
        labelMap.put("meaningless", "无意义");
        labelMap.put("normal", "正常");
    }

    @Override
    public String doCheck(String content) {
        String strResponse = sendRequest(content);
        return parseResponse(strResponse);
    }

    public static String sendRequest(String content) {
        try {
            HashMap<String, Object> payload = new HashMap<String, Object>();
            payload.put("accessKey", accessKey);
            payload.put("appId", appId);
            payload.put("eventId", eventId);
            payload.put("type", type);

            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("text", content);
            data.put("tokenId", tokenId);

            payload.put("data", data);
            String strParam = JsonUtil.TransToJson(payload);
            String response = HttpClient4Utils.invokePost(httpClient, URL, strParam, Consts.UTF_8, 10000, 10000);
            logger.info("sendRequest:ok,Response={}", response);
            return response;
        } catch (Exception e) {
            logger.error("sendRequest:Exception,tokenId={},content={}", tokenId, content, e);
        }
        return null;
    }

    private String parseResponse(String strResponse) {
        String result = "";
        try {
            if (StringUtils.isEmpty(strResponse)) {
                result = "parseResponse:Exception,strResponse is Empty";
                System.out.println(result);
                return result;
            }
            ShuMeiTextResponse response = JsonUtil.TransToObject(strResponse, ShuMeiTextResponse.class);
            // 如果返回码不是成功，则返回错误码
            if (response.getCode() != RESP_CODE_SUCCESS) {
                result = "parseResponse:response error,Code=" + response.getCode() + ",Message=" + response.getMessage();
                System.out.println(result);
                return result;
            }
            String label = response.getRiskLabel1();
            Set<String> labelSet = new HashSet<>();
            // 如果没有识别到敏感词返回PASS
            if (RISK_LEVEL_PASS.equals(response.getRiskLevel())) {
                result = "正常";
                return result;
            }
            RiskDetailBean riskDetail = response.getRiskDetail();
            // 查看风险详细信息
            if (null == riskDetail) {
                result = "parseResponse:response RiskDetail=null,strResponse=" + strResponse;
                System.out.println(result);
                return result;
            }
            if (labelMap.containsKey(label)) labelSet.add(labelMap.get(label));
            List<AllLabelBean> allLabels = response.getAllLabels();
            for (AllLabelBean labelBean : allLabels) {
                label = labelBean.getRiskLabel1();
                if (!StringUtils.isEmpty(label)) {
                    if (labelMap.containsKey(label)) labelSet.add(labelMap.get(label));
                }
            }
            for (String labels : labelSet) {
                result += labels + ",";
            }
            if (result.endsWith(",")) {
                result = result.substring(0, result.length() - 1);
            }
            return result;
        } catch (Exception e) {
            result = "parseResponse:Exception,strResponse=" + strResponse;
            System.out.println(result);
            e.printStackTrace();
            return result;
        }
    }

}

package com.gadgets.fileGadgets.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.db.CHMCache;
import com.maxmind.db.Reader;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.util.Arrays;


public class IPMaxMind {

    private static Reader reader = null;

    private final static String splitFlag = ",";

    private static String fileNameRegion = "GeoLite2-City.mmdb";

    private static final Log loggger = LogFactory.getLog("calculate");

    private static final int DEFAULT_CAPACITY = 8192; //IP 缓存大小

    static {
        String regionPath = "";
        try {
            URL fileUrl = IPMaxMind.class.getClassLoader().getResource(fileNameRegion);
            if (fileUrl != null) {
                regionPath = fileUrl.getPath();
            }

            String localPath = "D:\\工作目录\\REF日本预约ip地址所属国家追踪\\GeoLite2-City_20220503\\" + fileNameRegion;
            File database = null;
            if (!StringUtils.isEmpty(regionPath)) {
                database = new File(regionPath);
            }
            if (database == null || !database.exists()) {
                database = new File(localPath);
            }
            if (reader == null) {
                reader = new Reader(database, new CHMCache(DEFAULT_CAPACITY));
            }
        } catch (Exception e) {
            e.printStackTrace();
            loggger.error("IPMaxMind init error," + e.getMessage() + ",regionPath=" + regionPath);
        }
    }

    public static void main(String[] args) {
        Long beginTime = System.currentTimeMillis();
        String[] ips = "212.159.196.163,1.46.207.241,109.70.100.34,165.225.112.196,199.201.67.3,183.90.37.227,110.54.194.237".split(",");
        for (String ip : ips) {
            //String ip = randomIp();
            String[] data = IPMaxMind.find(ip);
            System.out.println(data[0] + "," + ip);
        }
        Long endTime = System.currentTimeMillis();
        Long execTime = endTime - beginTime;
        System.out.println(execTime);
    }

    public static String[] find(String ip) {
        String[] entry = new String[3];
        Arrays.fill(entry, "");
        try {
            JsonNode node = reader.get(InetAddress.getByName(ip));

            if (node == null || node.get("country") == null) {
                return entry;
            }
            String country = "";
            String province = "";
            String city = "";
            if (node.get("country").get("names").get("zh-CN") != null) {
                country = node.get("country").get("names").get("zh-CN").textValue();
            } else {
                country = node.get("country").get("names").get("en").textValue();
            }

            if (node.get("subdivisions") != null && node.get("subdivisions").size() > 0) {
                if (node.get("subdivisions").get(0).get("names").get("zh-CN") != null) {
                    province = node.get("subdivisions").get(0).get("names").get("zh-CN").textValue();
                } else {
                    province = node.get("subdivisions").get(0).get("names").get("en").textValue();
                }
            }

            if (node.get("city") != null) {
                if (node.get("city").get("names").get("zh-CN") != null) {
                    city = node.get("city").get("names").get("zh-CN").textValue();
                } else {
                    city = node.get("city").get("names").get("en").textValue();
                }
            }
            String localInfo = country + splitFlag + province + splitFlag + city;
            entry = localInfo.split(splitFlag);
        } catch (Exception e) {
            e.printStackTrace();
            loggger.error("IPMaxMind find error," + e.getMessage());
            return entry;
        }
        return entry;
    }
}

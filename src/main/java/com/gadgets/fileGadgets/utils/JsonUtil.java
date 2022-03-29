package com.gadgets.fileGadgets.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static Logger logger = LoggerFactory.getLogger(com.gadgets.fileGadgets.utils.JsonUtil.class);

    public static <T> T TransToObject(String json, Class<? extends T> cls) throws IOException {
        ObjectReader or = objectMapper.readerFor(cls);
        T bean = null;
        bean = or.readValue(json);
        return bean;
    }

    public static String TransToJson(Object o) throws IOException {
        if(o == null)return null;
        ObjectWriter writer = objectMapper.writer();
        return writer.writeValueAsString(o);
    }

    public static String TransToJsonNoException(Object o){
        try {
            return TransToJson(o);
        }catch (Exception e){
            logger.error("TransToJsonNoException:exception,o={}", o);
            return null;
        }
    }

    public static <T> List<T> TransToList(String json, TypeReference<ArrayList<T>> type) throws IOException {
        List<T> bean = objectMapper.readValue(json, type);
        return bean;
    }

    public static Map<String, Object> TransToMap(String json, TypeReference<Map<String, Object>> type) throws IOException {
        return objectMapper.readValue(json, type);
    }

    public static <T> List<T> CopyList(List<T> list) throws JsonProcessingException, IOException, InstantiationException, IllegalAccessException {
        if(list == null)return null;
        if(list.isEmpty())return list.getClass().newInstance();
        String json = TransToJson(list);
        return (List<T>) TransToList(json, list.getClass(), list.get(0).getClass());
    }

    public static <T> List<T> TransToList(String json, @SuppressWarnings("rawtypes") Class<? extends List> listClass, Class<? extends T> objectClass) throws JsonProcessingException, IOException {
        return objectMapper.readValue(json, objectMapper.getTypeFactory().constructParametricType(listClass, objectClass));
    }

    public static <K,V> Map<K, V> CopyMap(Map<K, V> map, Class<? extends K> keyClass, Class<? extends V> valueClass) throws JsonProcessingException, IOException {
        String json = TransToJson(map);
        return TransToMap(json, map.getClass(), keyClass, valueClass);
    }

    public static <K,V> Map<K, V> CopyMap(Map<K, V> map) throws JsonProcessingException, IOException, InstantiationException, IllegalAccessException {
        if(map == null)return map;
        if(map.isEmpty())return map.getClass().newInstance();

        String json = TransToJson(map);
        for(Map.Entry<K, V> entry: map.entrySet()) {
            return (Map<K, V>) TransToMap(json, map.getClass(), entry.getKey().getClass(), entry.getValue().getClass());
        }
        return null;
    }

    public static <K,V> Map<K, V> TransToMap(String json, @SuppressWarnings("rawtypes") Class<? extends Map> mapClass, Class<? extends K> keyClass, Class<? extends V> valueClass) throws JsonProcessingException, IOException {
        Map<K, V> bean = objectMapper.readValue(json, objectMapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass));
        return bean;
    }

    public static <T> Set<T> CopySet(Set<T> set, Class<? extends T> objClass) throws JsonProcessingException, IOException {
        String json = TransToJson(set);
        return TransToSet(json, set.getClass(), objClass);
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> CopySet(Set<T> set) throws JsonProcessingException, IOException, InstantiationException, IllegalAccessException {
        if(set == null)return set;
        if(set.isEmpty())return (Set<T>)set.getClass().newInstance();

        String json = TransToJson(set);
        for(T t: set) {
            return (Set<T>) TransToSet(json, set.getClass(), t.getClass());
        }
        return null;
    }

    public static <T> Set<T> TransToSet(String json, @SuppressWarnings("rawtypes") Class<? extends Set> setClass, Class<? extends T> objectClass) throws JsonProcessingException, IOException {
        return objectMapper.readValue(json, objectMapper.getTypeFactory().constructParametricType(setClass, objectClass));
    }

    public static Object CopyObject(Object object) throws IOException {
        String json = TransToJson(object);
        return TransToObject(json, object.getClass());
    }

    public static void main(String[] args){
        try {
//            String a = "{\"res\":{\"type\":1}}";
//            Map<String, Object> map = TransToMap(a, new TypeReference<Map<String, Object>>(){});
//            IDIPRequestHead head = new IDIPRequestHead();
//            System.out.println(TransToJson(head));

//
            String a = "aaa";
//            System.out.println("a=" + a + ",a.json=" + JsonUtil.TransToJson(a));
//            int b = 10;
//            System.out.println("b=" + b + ",b.json=" + JsonUtil.TransToJson(b));
            Map<String, String> map = new HashMap<>();
            map.put("en", "\"ss\"");
            System.out.println(JsonUtil.TransToJson(map));

            Map<String, Object> map2 = new HashMap<>();
            map2.put("content", TransToJson(map));

            System.out.println(TransToJson(map2));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

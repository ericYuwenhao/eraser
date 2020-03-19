package com.eraser.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @user eric
 * @date 2020-03-17 16:11
 */
public class PropertiesReader {
    //设置编码格式
    /**
     * 基于InputStream读取配置文件,返回一个集合,里面存放的是分割后的数据
     * @param filename 需要读取的文件名称
     * @param keys 配置文件中的key
     * 目前暂支持UTF-8编码
     */
    public static List<String> readForStream(String filename, List<String> keys) {
        Properties prop = new Properties();
        InputStream in = Object.class.getResourceAsStream("/" + filename);
        List<String> list = null;
        try {
            prop.load(in);
            if (null != keys) {
                for (String key : keys) {
                    String data = prop.get(key).toString();
                    String[] datas = data.split(",");
                    list = Arrays.asList(datas);
                }
            }
            in.close();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("未找到相关配置文件");
            return null;
        }

    }

    public static void main(String[] args) {
        ArrayList<String> l = new ArrayList<>();
        l.add("excelRowHead");
        List<String> list = readForStream("excel.properties", l);
        list.forEach(System.out::println);
    }
}

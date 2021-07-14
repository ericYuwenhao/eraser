package com.eraser.common;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author by Eric_Yu
 * @Classname IDGenerate
 * @Description TODO 此方法用于生成各处需要的id
 * @Date 2021/7/14 10:03
 */
public class IDGenerate {

    private static SecureRandom random = new SecureRandom();
    /**
     * 默认方法，不需要传入参数，默认返回一个长度为17位的唯一ID
     * 其中前14位为年月日时分秒，后三位为随机的三个英文字母
     * @return id串
     */
    public static synchronized String generateID() {
        //获取当前时间对象
        Date date = new Date();
        //设置格式化形式
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String msg = format.format(date);
        //添加三位随机英文字母
        for (int i = 0; i < 3; i++) {
            msg += (char)(Math.random() * 26 + 'a');
        }
        return msg;
    }

    /**
     * 生成一个长度由调用者自定义的随机id，长度生成策略为 14位时间参数 + 传入的后缀尾数
     * @param length 后缀尾数长度
     * @return id
     */
    public static synchronized String generateID(int length) {

        //获取当前时间对象
        Date date = new Date();
        //设置格式化形式
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String msg = format.format(date);
        for (int i = 0; i < length; i++) {
            msg += (char) (Math.random() * 26 + 'a');
        }
        return msg;
    }

    /**
     * 随机生成 id，长度自定义
     * @param length 字符串长度
     * @return id
     */
    public static synchronized String generateUUID(int length) {
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            buffer.append(str.charAt(number));
        }
        return buffer.toString();
    }

    /**
     * 封装 jdk 自带的UUID，通过 Random 数字生成，中间无-分割
     * @return 字符串
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void main(String[] args) {
        String s = IDGenerate.uuid();
        System.out.println("s = " + s);
    }
}

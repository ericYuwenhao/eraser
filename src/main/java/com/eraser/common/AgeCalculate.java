package com.eraser.common;

import java.util.Calendar;

/**
 * @author by Eric_Yu
 * @Classname AgeCalculate
 * @Description TODO 根据年月日计算年龄
 * @Date 2021/7/14 10:37
 */
public class AgeCalculate {

    /**
     * 根据传入的时间计算年龄
     * @param birthTime 1996-02-04
     * @return 字符串格式的年龄
     */
    public static String getAgeForBirthTime(String birthTime) {
        //获取传入时间的年月日
        String[] strs = birthTime.trim().split("-");
        int year = Integer.parseInt(strs[0]);
        int month = Integer.parseInt(strs[1]);
        int day = Integer.parseInt(strs[2]);
        //获取当前年月日
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        int nowDay = calendar.get(Calendar.DATE);

        int yearMinus = nowYear - year;
        int monthMinus = nowMonth - month;
        int dayMinus = nowDay - day;

        int age = yearMinus;// 先大致赋值
        if (yearMinus < 0) {// 选了未来的年份
            age = 0;
        } else if (yearMinus == 0) {// 同年的，要么为1，要么为0
            if (monthMinus < 0) {// 选了未来的月份
                age = 0;
            } else if (monthMinus == 0) {// 同月份的
                if (dayMinus < 0) {// 选了未来的日期
                    age = 0;
                } else if (dayMinus >= 0) {
                    age = 1;
                }
            } else if (monthMinus > 0) {
                age = 1;
            }
        } else if (yearMinus > 0) {
            if (monthMinus < 0) {// 当前月>生日月
            } else if (monthMinus == 0) {// 同月份的，再根据日期计算年龄
                if (dayMinus < 0) {
                } else if (dayMinus >= 0) {
                    age = age + 1;
                }
            } else if (monthMinus > 0) {
                age = age + 1;
            }
        }
        return String.valueOf(age -1);
    }

    public static void main(String[] args) {
        System.out.println(AgeCalculate.getAgeForBirthTime("1900-02-04"));
    }
}

package com.ycitus.utils;


import com.ycitus.debug.LoggerManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    // 将一个时间与现在时间比较
    // 输入的日期在现在之后，则返回1
    // 输入的日期在现在之前，则返回-1
    // 若输入日期与现在一样，则返回0
    public static int compareDate(Calendar date) {
        return DateUtil.compareDate(date, Calendar.getInstance());
    }


    public static long getUnixTimeS() {
        return getUnixTimeMS() / 1000;
    }

    public static long getUnixTimeMS() {
        return System.currentTimeMillis();
    }

    public static int compareDate(Calendar date, Calendar date2) {

        Calendar date_clone = (Calendar) date.clone();
        Calendar date2_clone = (Calendar) date2.clone();

        DateUtil.setZero(date_clone);
        DateUtil.setZero(date2_clone);

        return date_clone.compareTo(date2_clone);
    }

    // 获取两个Date的时间差
    public static int differentDaysByMillisecond(Calendar c1, Calendar c2) {

        /** 防止前后一天计算错误 **/

        if (c1.getTime().getTime() < c2.getTime().getTime()) {
            DateUtil.setZero(c1);
            DateUtil.setMid(c2);
        } else {
            DateUtil.setZero(c2);
            DateUtil.setMid(c1);
        }

        /** 计算天数差 **/
        int days = (int) ((c2.getTime().getTime() - c1.getTime().getTime()) / (1000 * 3600 * 24));
        return days;

    }

    public static int differentDaysByMillisecond(Date d1, Date d2) {
        return differentDaysByMillisecond(
                DateUtil.translate_Date_To_Calendar(d1),
                DateUtil.translate_Date_To_Calendar(d2));
    }

    public static Calendar getDataDetail(String date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = format.parse(date);
        } catch (ParseException e) {
            LoggerManager.reportException(e);
        }

        Calendar c = Calendar.getInstance();
        c.setTime(d);

        return c;
    }

    // 计算两个日期相差多少秒
    public static long diffSeconds(Calendar big, Calendar small) {

        long nm = 1000;

        long diff = big.getTime().getTime() - small.getTime().getTime(); // 获得两个时间的毫秒时间差异

        long result = diff / nm;

        return result;
    }

    public static Calendar getDataSimple(String date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = format.parse(date);
        } catch (ParseException e) {
            LoggerManager.reportException(e);
        }

        Calendar c = Calendar.getInstance();
        c.setTime(d);

        return c;
    }

    // 输入日期。返回字符串
    public static String getDateDetail(Calendar c) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(c.getTime());
    }

    // 输入日期。返回字符串
    public static String getDateSimple(Calendar c) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(c.getTime());
    }

    public static Date getNowDate() {
        return Calendar.getInstance().getTime();
    }

    public static int getNowDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getNowHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int getNowMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    public static int getNowMonth() {
        return (Calendar.getInstance().get(Calendar.MONTH) + 1);
    }

    public static int getNowSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    public static int getNowYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static Calendar setMid(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 12);
        c.set(Calendar.MINUTE, 0);
        return c;
    }

    public static Calendar setZero(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        return c;
    }

    public static Calendar translate_Date_To_Calendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Calendar translate_TimeStamp_Ms_To_Calendar(long timestamp_Ms) {
        return translate_Date_To_Calendar(translate_TimeStamp_Ms_To_Date(timestamp_Ms));
    }

    public static Date translate_TimeStamp_Ms_To_Date(long timestamp_Ms) {
        return new Date(timestamp_Ms);
    }

}

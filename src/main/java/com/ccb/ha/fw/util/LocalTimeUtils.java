package com.ccb.ha.fw.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LocalTimeUtils {

    public static final String DEFAULT_TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
    /**
     * 标准日期格式化
     *
     * @return
     */
    public static DateTimeFormatter dateFormatter() {
        return DateTimeFormatter
                .ofPattern(DEFAULT_DATE_PATTERN);
    }

    /**
     * 标准日期格式化
     *
     * @return
     */
    public static DateTimeFormatter timeFormatter() {
        return DateTimeFormatter
                .ofPattern(DEFAULT_TIME_PATTERN);
    }

    /**
     * 标准的日期时间格式化
     *
     * @return
     */
    public static DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter
                .ofPattern(DEFAULT_DATE_TIME_PATTERN);
    }

    /**
     * 时间戳的格式化，含有毫秒值
     *
     * @return
     */
    public static DateTimeFormatter timeStampFormatter() {
        return DateTimeFormatter
                .ofPattern(DEFAULT_TIMESTAMP_PATTERN);
    }

    /**
     * 获取当前时间戳(秒)
     *
     * @author 冯志强
     * @date 2019/8/9 16:52
     */
    public static long getCurrentTimeSecond() {
        return LocalDateTime.now().toEpochSecond(
                OffsetDateTime.now().getOffset());
    }

    /**
     * 获取当前时间戳(毫秒)
     *
     * @author 冯志强
     * @date 2019/8/9 16:52
     */
    public static long getCurrentTimeMilli() {
        return LocalDateTime.now().toInstant(
                OffsetDateTime.now().getOffset()).toEpochMilli();
    }

    /**
     * 时间戳 (秒) 格式化 yyyy-MM-dd HH:mm:ss
     *
     * @author 冯志强
     * @date 2019/8/9 16:52
     */
    public static String timestampSecondFormat(long timestamp) {
        return LocalDateTime
                .ofInstant(Instant.ofEpochSecond(timestamp),
                        ZoneId.systemDefault())
                .format(LocalTimeUtils.dateTimeFormatter());
    }

    /**
     * 时间戳(毫秒) 格式化 yyyy-MM-dd HH:mm:ss.SSS
     *
     * @author 冯志强
     * @date 2019/8/9 16:52
     */
    public static String timestampMilliFormat(long timestamp) {
        return LocalDateTime
                .ofInstant(
                        Instant.ofEpochMilli(timestamp),
                        ZoneId.systemDefault())
                .format(LocalTimeUtils.timeStampFormatter());
    }


    /**
     * 2019-12-19 23:20:50.292 转 LocalDateTime
     *
     * @author 冯志强
     * @date 2019/8/9 16:52
     */
    public static LocalDateTime stringMilliToDateTime(String datetime) {
        return LocalDateTime.parse(datetime,
                LocalTimeUtils.timeStampFormatter());
    }

    /**
     * 2019-12-19 23:20:50 转 LocalDateTime
     *
     * @author 冯志强
     * @date 2019/8/9 16:52
     */
    public static LocalDateTime stringSecondToDateTime(String datetime) {
        return LocalDateTime.parse(datetime,
                LocalTimeUtils.dateTimeFormatter());
    }


    public static void main(String[] args) {
        long second = getCurrentTimeSecond();
        long milli = getCurrentTimeMilli();
        String s1 = timestampSecondFormat(second);
        String s2 = timestampMilliFormat(milli);
        LocalDateTime t1 = stringSecondToDateTime(s1);
        LocalDateTime t2 = stringMilliToDateTime(s2);
        System.out.println(String.valueOf(second) + "  :  " + s1 + "  :  " + t1);
        System.out.println(String.valueOf(milli) + "  :  " + s2 + " : " + t2);
    }
}

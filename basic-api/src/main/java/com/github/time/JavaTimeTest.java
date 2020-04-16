package com.github.time;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author hangs.zhang
 * @date 2020/04/05 12:21
 * *****************
 * function:
 */
public class JavaTimeTest {
    /**
     * LocalDate
     */
    @Test
    public void localDate() {
        // LocalDate 不可变对象
        // 获取特定时间
        LocalDate date = LocalDate.of(2018, 10, 1);
        int year = date.getYear();
        int dayOfYear = date.getDayOfYear();
        Month month = date.getMonth();
        int dayOfMonth = date.getDayOfMonth();

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        System.out.println("dayOfWeek : " + dayOfWeek);

        // 获取当前年月日
        LocalDate now = LocalDate.now();
        System.out.println("now : " + now);
        int i = now.get(ChronoField.YEAR);

        // 检查两个日期是否相等
        boolean equals = now.equals(date);

        LocalDate localDate = LocalDate.parse("2019-10-02", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        localDate = LocalDate.parse("20191002", DateTimeFormatter.BASIC_ISO_DATE);
        String formatDate = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println("localDate : " + localDate);
        System.out.println("formatDate : " + formatDate);

        // 获取一周后的时间
        LocalDate plusLocalDate = localDate.plus(1, ChronoUnit.WEEKS);
        System.out.println("plusLocalDate : " + plusLocalDate);

        // 获取一年前的时间
        LocalDate minusLocalDate = plusLocalDate.minus(1, ChronoUnit.YEARS);
        System.out.println("minusLocalDate : " + minusLocalDate);

        // 比较时间的前后
        // minusLocalDate.isAfter(plusLocalDate);
        // minusLocalDate.isBefore(plusLocalDate);
    }

    @Test
    public void timestamp() {
        // LocalDateTime转化instant
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        Instant instant = localDateTimeNow.atZone(ZoneId.systemDefault()).toInstant();
        // instant转化LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        // 获取当前时间戳
        Instant timestampNow = Instant.now();

        // instant转化date
        Date date = Date.from(timestampNow);
        System.out.println("时间戳 : " + date.getTime());
        // date转化instance
        instant = date.toInstant();


        LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalTime
     */
    @Test
    public void localTime() {
        // 1
        LocalTime time = LocalTime.of(15, 45, 20);
        int hour = time.getHour();
        int minute = time.getMinute();
        System.out.println(time);

        LocalTime parse = LocalTime.parse("12:20:30");
        // 增加两个小时
        LocalTime plusTime = parse.plusHours(2);
        System.out.println(plusTime);

    }

    /**
     * 转化，合并
     */
    @Test
    public void localDateTime() {
        String format = "yyyyMMddHHmmss";
        // 直接创建
        LocalDateTime dateTime = LocalDateTime.of(2016, 2, 1, 12, 30, 20);
        System.out.println(getTodayByFormat("yyyyMMddHHmmss"));

        // 合并date，time
        LocalTime time = LocalTime.of(15, 45, 20);
        LocalDate date = LocalDate.of(2018, 10, 1);
        LocalDateTime ldt1 = LocalDateTime.of(date, time);
        LocalDateTime ldt2 = time.atDate(date);
        LocalDateTime ldt3 = date.atTime(time);
        // 转化date，time
        LocalDate localDate = dateTime.toLocalDate();
        LocalTime localTime = dateTime.toLocalTime();

        LocalDateTime parseLocalDateTime = LocalDateTime.parse("20191005 10:05:30.256", DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss.SSS"));
        LocalDateTime parseLocalDateTime2 = LocalDateTime.parse("20191005100530", DateTimeFormatter.ofPattern(format));
        System.out.println(parseLocalDateTime);
        System.out.println(parseLocalDateTime2);
    }

    // 按照自由格式获取当前时间
    public static String getTodayByFormat(String timeFormat) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(timeFormat));
    }
}

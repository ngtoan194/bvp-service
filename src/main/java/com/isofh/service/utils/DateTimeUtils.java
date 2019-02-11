/*
 * *******************************************************************
 * Copyright (c) 2018 Isofh.com to present.
 * All rights reserved.
 *
 * Author: tuanld
 * ******************************************************************
 *
 */

package com.isofh.service.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtils {

    public static long getCurrentTimeInMilis() {
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     * Get gio hien tai
     *
     * @return
     */
    public static Date getCurrentDate() {
        return new Date();
    }


    /**
     * Add date
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDays(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime();
    }

    /**
     * Lay thoi diem bat dau trong ngay 0h0m0s
     *
     * @return
     */
    public static Long getStartDayTime() {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        Long time = calendarStart.getTimeInMillis();
        // remove ticktac
        time = time - time % 1000;
        return time;
    }

    /**
     * convert millis to string
     *
     * @param millis
     * @return
     */
    public static String toString(long millis) {
        Date date = new Date(millis);
        return date.toString();
    }

    /**
     * get current time in string
     *
     * @return
     */
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }


    /**
     * Get total minute from 0h0m to now
     *
     * @return
     */
    public static int getTotalMinute() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
    }

    /**
     * Convert LocalDateTime to time in milis
     *
     * @return
     */
    public static Long getTimeInMillis(LocalDateTime localDateTime) {
        if (localDateTime == null) return 0L;
        return localDateTime.toEpochSecond(ZoneOffset.ofHours(7)) * 1000;
    }

    /**
     * Convert LocalDateTime to time in milis
     *
     * @return
     */
    public static Long getTimeInMillis(LocalDate localDate) {
        if (localDate == null) return 0L;
        return localDate.atStartOfDay().toEpochSecond(ZoneOffset.ofHours(7)) * 1000;
    }

    /**
     * Get age from localDate
     *
     * @return
     */
    public static int getAge(LocalDate localDate) {
        if (localDate == null) return 0;
        return LocalDate.now().getYear() - localDate.getYear() + 1;
    }

    public static LocalDateTime fromMillis(long timeStamp) {
        LocalDateTime triggerTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp),
                        TimeZone.getDefault().toZoneId());
        return triggerTime;
    }


}

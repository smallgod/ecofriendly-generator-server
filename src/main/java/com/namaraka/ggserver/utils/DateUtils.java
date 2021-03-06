/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.namaraka.ggserver.utils;

import com.namaraka.ggserver.constant.NamedConstants;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author smallgod
 */
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * Pattern - yyyy/MM/dd HH:mm:ss
     *
     * @param string
     * @param dateStringFormat
     * @return
     */
    public static LocalDateTime convertStringToDateTime(String string, String dateStringFormat) {

        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateStringFormat);
        LocalDateTime dt = formatter.parseLocalDateTime(string);

        return dt;
    }

    /**
     * Pattern - yyyy/MM/dd
     *
     * @param string
     * @param dateStringFormat
     * @return
     */
    public static LocalDate convertStringToDate(String string, String dateStringFormat) {

        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateStringFormat);
        LocalDate dt = formatter.parseLocalDate(string);

        return dt;
    }

    /**
     * Get current date-time string
     *
     * @param timeZone
     * @param dateStringFormat
     * @return
     */
    //kampala time zone - 'Africa/Kampala'
    public static String getDateTimeNow(String timeZone, String dateStringFormat) {

        /*
        DateTime now = new DateTime();
        DateTimeZone kampalaTimeZone1 = DateTimeZone.forID(timeZone);
        DateTime convertedTime1 = now.toDateTime(kampalaTimeZone1);
         */
        DateTime dateNow = DateTime.now();
        DateTimeZone desiredTimeZone = DateTimeZone.forID(timeZone);
        DateTime dateTime = dateNow.toDateTime(desiredTimeZone);

        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateStringFormat);
        //DateTime dateTime = formatter.parseDateTime(dateString);
        String formattedDate = formatter.print(dateTime);

        return formattedDate;
    }

    /**
     * Get LocalDateTime now
     *
     * @return
     */
    public static LocalDateTime getDateTimeNow() {

        return LocalDateTime.now();
    }

    /**
     * Get LocalDateTime now
     *
     * @param timeZoneStr
     * @return
     */
    public static LocalDateTime getDateTimeNow(String timeZoneStr) {

        DateTimeZone desiredTimeZone = DateTimeZone.forID(timeZoneStr);
        return LocalDateTime.now(desiredTimeZone);
    }

    /**
     * parse dateTime in a formatted string - e.g. Kampala timezone is -
     * "Africa/Kampala" and a string-format can be "ddMMyyyyHHmmss"
     *
     * @param dateTimeToFormat
     * @param timeZone
     * @param dateStringFormat
     * @return
     */
    public static String formatDateTime(DateTime dateTimeToFormat, String timeZone, String dateStringFormat) {

        if (dateTimeToFormat == null) {
            logger.warn("Failed to Convert Date, DateTime to Format is NULL!!");
            return "";
        }
        DateTimeZone dateTimeZone = DateTimeZone.forID(timeZone);
        DateTime newDateTime = dateTimeToFormat.toDateTime(dateTimeZone);
        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateStringFormat);
        //DateTime dateTime = formatter.parseDateTime(dateString);
        String formattedDate = formatter.print(newDateTime);

        return formattedDate;
    }

    /**
     * Time difference between startTime and now
     *
     * @param startTime
     * @return
     */
    public static String timeTakenToNow(DateTime startTime) {

        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendYears().appendSuffix(" years ")
                .appendMonths().appendSuffix(" months ")
                .appendWeeks().appendSuffix(" weeks ")
                .appendDays().appendSuffix(" days ")
                .appendHours().appendSuffix(" hours ")
                .appendMinutes().appendSuffix(" minutes ")
                .appendSeconds().appendSuffix(" seconds ")
                .appendMillis().appendSuffix(" milliseconds ")
                //.printZeroNever() //if you don't want to print zeros
                .toFormatter();

        //DateTime myBirthDate = new DateTime(1978, 3, 26, 12, 35, 0, 0);
        DateTime now = new DateTime();
        Period period = new Period(startTime, now);

        String elapsed = formatter.print(period);

        return elapsed;
    }

    /**
     * Time difference between startTime and endTime
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static String timeTakenToNow(DateTime startTime, DateTime endTime) {

        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendYears().appendSuffix(" years  ")
                .appendMonths().appendSuffix(" months  ")
                .appendWeeks().appendSuffix(" weeks  ")
                .appendDays().appendSuffix(" days  ")
                .appendHours().appendSuffix(" hours  ")
                .appendMinutes().appendSuffix(" minutes  ")
                .appendSeconds().appendSuffix(" seconds  ")
                .appendMillis().appendSuffix(" milliseconds  ")
                //.printZeroNever() //if you don't want to print zeros
                .toFormatter();

        //DateTime myBirthDate = new DateTime(1978, 3, 26, 12, 35, 0, 0);
        Period period = new Period(startTime, endTime);

        String elapsed = formatter.print(period);

        return elapsed;
    }

    /**
     * Get Days between 2 Dates
     * @param startDate
     * @param endDate
     * @return 
     */
    public static int getDaysBetweenTwoDates(LocalDateTime startDate, LocalDateTime endDate) {
        
        Days days = Days.daysBetween(startDate.toLocalDate(), endDate.toLocalDate());

        return days.getDays();
    }

    /**
     * convert LocalDateTime to String
     *
     * @param dateTime
     * @param dateTimePattern
     * @return
     */
    public static String convertLocalDateTimeToString(LocalDateTime dateTime, String dateTimePattern) {

        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateTimePattern);
        String formattedDateTime = dateTime.toString(formatter); // "1986-04-08 12:30"

        return formattedDateTime;
    }

    public static String convertLocalDateToString(LocalDate dateTime, String dateTimePattern) {

        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateTimePattern);
        String formattedDateTime = dateTime.toString(formatter); // "1986-04-08"

        return formattedDateTime;
    }

    /**
     * Add days to LocalDateTime
     *
     * @param daysToAdd
     * @return
     */
    public static LocalDateTime addDaysToLocalDateTimeNow(int daysToAdd) {

        LocalDateTime dateTime = getDateTimeNow(NamedConstants.KAMPALA_TIME_ZONE);
        dateTime = dateTime.plusDays(daysToAdd);

        return dateTime;
    }

}
